package org.usfirst.frc.team2077.drivetrain.swerve;

import org.usfirst.frc.team2077.math.RateLimiter;
import org.usfirst.frc.team2077.util.PIDTuneable;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.ClosedLoopConfig;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.ClosedLoopConfigAccessor;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

public class SwerveDrivingMotor implements PIDTuneable {

    private static final int motorFreeSpeed = 5800; //RPM

    private final SwerveConstants.MotorPosition position;
    private final SwerveModule parent;

    private RateLimiter rateLimiter;

    private final SparkMax motor;
    // private final RelativeEncoder encoder;
    private final ClosedLoopConfigAccessor PIDAccessor;
    private final ClosedLoopConfig PIDSeter;

    private double velocitySet = 0;
    private boolean reversed = false;

    public SwerveDrivingMotor(SwerveConstants.MotorPosition position, SwerveModule parent){
        this.parent = parent;
        this.position = position;

        rateLimiter = new RateLimiter(6, 10);

        motor = new SparkMax(position.drivingCANid, SparkLowLevel.MotorType.kBrushless);
        SparkMaxConfig config = new SparkMaxConfig();
        
        config.idleMode(IdleMode.kBrake);
        config.encoder.velocityConversionFactor(SwerveConstants.wheelCircumference / SwerveConstants.driveGearReduction / 60.0);

        config.smartCurrentLimit(SwerveConstants.drivingMotorCurrentLimit);
        
        config.closedLoop.feedbackSensor(FeedbackSensor.kPrimaryEncoder).pid(position.drivingP, position.drivingI, 0.0);

        motor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        PIDSeter = config.closedLoop;
        PIDAccessor = motor.configAccessor.closedLoop;
    }

    public void update(){
        if(parent.calibrating){
            return;
        }

//        motor.set(
//                rateLimiter.calculate(
//                        velocitySet
//                ) *  position.drivingF * (reversed? -1 : 1)
//        );

        motor.getClosedLoopController().setReference(
                rateLimiter.calculate(
                    velocitySet * (reversed? -1 : 1)
                ),
            SparkMax.ControlType.kVelocity
        );
    }

    public double getVelocityMeasured(){
        return motor.getAbsoluteEncoder().getVelocity();
    }

    public double getVelocitySet() {
        return velocitySet;
    }

    public void setVelocity(double velocity) {
        if(parent.calibrating){
            return;
        }

        velocitySet = velocity;
    }

    public boolean getReversed(){
        return reversed;
    }

    public void setReversed(boolean r){
        reversed = r;
    }

    public double getMaximumSpeed(){
        return motorFreeSpeed * motor.configAccessor.encoder.getVelocityConversionFactor();
    }

    public double getP() {
        return PIDAccessor.getP();
    }
    public double getI() {
        return PIDAccessor.getI();
    }
    public double getD() {
        return PIDAccessor.getD();
    }

    public void setP(double p) {
        PIDSeter.p(p);
    }
    public void setI(double i) {
        PIDSeter.i(i);
    }
    public void setD(double d) {
        PIDSeter.d(d);
    }

    @Override
    public void tuningSet(double setpoint) {
        parent.calibrating = true;

        velocitySet = setpoint;

        motor.getClosedLoopController().setReference(
            velocitySet,
            SparkMax.ControlType.kVelocity
        );

    }

    @Override
    public void tuningStop() {
        parent.calibrating = true;

        velocitySet = 0.0;

        motor.set(0.0);
    }

    @Override
    public void zeroIntegral() {
        setVelocity(0);
        PIDSeter.i(0.0);
    }

    @Override
    public double tuningGetError() {
        return Math.abs(getVelocityMeasured() - getVelocitySet());
    }

    @Override
    public boolean tuningReady() {
        return Math.abs(getVelocityMeasured()) < 0.01;
    }

    @Override
    public String getName() {
        return position.name() + "_DRIVING_MOTOR";
    }

    public double getDrivingEncoderPosition(){
        return motor.getAbsoluteEncoder().getPosition();
    }
}
