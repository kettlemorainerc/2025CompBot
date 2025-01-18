package org.usfirst.frc.team2077.drivetrain.swerve;

import com.revrobotics.*;
import com.revrobotics.spark.SparkAbsoluteEncoder;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.SparkBase.*;

import edu.wpi.first.math.controller.PIDController;
import org.usfirst.frc.team2077.util.PIDTuneable;

public class SwerveGuidingMotor implements PIDTuneable {

    private final SwerveConstants.MotorPosition position;
    private final SwerveModule parent;

    private final AbsoluteEncoder absoluteEncoder;
    private final RelativeEncoder relativeEncoder;

    private final SparkMax motor;
    private final PIDController PID;

    private double angleOffset = 0.0;
    private double angleSet = 0.0;

    private boolean zeroVelocity = false;

    private double atAngleDeadzone = Math.PI / 12.0;

    public SwerveGuidingMotor(SwerveConstants.MotorPosition position, SwerveModule parent) {
        this.parent = parent;
        this.position = position;

        angleOffset = position.angleOffset;

        motor = new SparkMax(position.guidingCANid, SparkLowLevel.MotorType.kBrushless);
        SparkMaxConfig config = new SparkMaxConfig();
        
        config.idleMode(IdleMode.kBrake);
        config.smartCurrentLimit(SwerveConstants.guidingMotorCurrentLimit);       

        
        config.encoder.positionConversionFactor(2.0 * Math.PI);
        config.encoder.velocityConversionFactor(2.0 * Math.PI);
        
        //Note: a relative encoder has a .setInverte() method, however it causes WPIlib to brick.
        //Instead we just subtract it always ig ¯\_(ツ)_/¯
        config.inverted(false);

        //The reading from the absolute encoder only updates at about 5hz. This is why swerve for the
        //first two years was dogwater. The relative encoder updates as fast as the RIO does.
        //So far there have been no problems with reading accuracy.
        relativeEncoder = motor.getEncoder();
        absoluteEncoder = motor.getAbsoluteEncoder();

        config.encoder.positionConversionFactor(2.0 * Math.PI * SwerveConstants.azimuthRatio);
        config.encoder.velocityConversionFactor(2.0 * Math.PI * SwerveConstants.azimuthRatio);

        //This basically just means we use the absolute encoder only once on startup
        relativeEncoder.setPosition(0.0);
        angleOffset += absoluteEncoder.getPosition();

        PID = new PIDController(position.guidingP, 0.0, 0.0);

        motor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    public void update(){
        if(parent.calibrating) {
            motor.set(0.0);
            return;
        }

        double angleDiff = distanceToTarget();
        double percent = PID.calculate(angleDiff, 0.0);

        if(Math.abs(percent) < 0.005){
            percent = 0.0;
        }

        motor.set(-percent);
    }

    public double getAngle() {
        //See previous comment about setting inverted
        double angle = angleOffset - relativeEncoder.getPosition();

        angle %= 2.0 * Math.PI;
        if(angle < 0) angle += 2.0 * Math.PI;
        return angle;
    }

    public boolean atAngle(){
        return Math.abs(distanceToTarget()) <= atAngleDeadzone;
    }

    public void setAngle(double angle) {
        if(parent.calibrating){
            return;
        }

        double angleDifference = Math.abs(SwerveChassis.getAngleDifference(angle, getAngle()));

        SwerveDrivingMotor drivingMotor = parent.getDrivingMotor();
        boolean reversed = drivingMotor.getReversed();
        double velocitySet = Math.abs(drivingMotor.getVelocitySet());

        if (!zeroVelocity) {
            if (reversed) {
                angle -= Math.PI;
            }
        } else if (angleDifference > 0.5 * Math.PI) {
            angle -= Math.PI;
            reversed = true;
        } else {
            reversed = false;
        }

        drivingMotor.setReversed(reversed);

        zeroVelocity = velocitySet < 0.1;

        if(zeroVelocity){
            return;
        }

        angle %= 2.0 * Math.PI;
        if (angle < 0) angle += 2.0 * Math.PI;
        angleSet = angle;
    }

    //Use sparingly (duh)
    public void setAngleForced(double angle){
        double angleDifference = distanceToTarget();

        SwerveDrivingMotor drivingMotor = parent.getDrivingMotor();

        if (Math.abs(angleDifference) > 0.5 * Math.PI) {
            angle -= Math.PI;
            drivingMotor.setReversed(true);
        } else {
            drivingMotor.setReversed(false);
        }

        angle %= 2.0 * Math.PI;
        if (angle < 0) angle += 2.0 * Math.PI;
        angleSet = angle;
    }

    public double distanceToTarget(){
        return SwerveChassis.getAngleDifference(angleSet, getAngle());
    }

    public double getP(){ return PID.getP(); }
    public double getI(){ return PID.getI(); }
    public double getD(){ return PID.getD(); }

    public void setP(double p) { PID.setP(p); }
    public void setI(double i) { PID.setI(i); }
    public void setD(double d) { PID.setD(d); }

    private double setpoint = 0;

    @Override
    public void tuningSet(double setpoint) {
        parent.calibrating = true;

        this.setpoint = setpoint;
        double angleDiff = SwerveChassis.getAngleDifference(setpoint, getAngle());
        double p = -PID.calculate(Math.abs(angleDiff), 0.0) * Math.signum(angleDiff);

        if(Math.abs(p) < 0.001){
            p = 0.0;
        }

        motor.set(p);
    }

    @Override
    public void tuningStop() {
        parent.calibrating = true;

        motor.set(0.0);
    }

    @Override
    public void zeroIntegral() {
        angleOffset -= getAngle();
        PID.reset();
    }

    @Override
    public double tuningGetError() {
        return Math.abs(SwerveChassis.getAngleDifference(setpoint, getAngle()));
    }

    @Override
    public boolean tuningReady() {
        return Math.abs(motor.getEncoder().getVelocity()) < 0.01;
    }

    @Override
    public String getName() {
        return position.name() + "_GUIDING_MOTOR";
    }
}
