package org.usfirst.frc.team2077.drivetrain.swerve;

import static org.usfirst.frc.team2077.common.WheelPosition.BACK_LEFT;

import org.usfirst.frc.team2077.math.RateLimiter;
import org.usfirst.frc.team2077.util.PIDTuneable;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTableValue;

import com.revrobotics.spark.config.ClosedLoopConfig;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.ClosedLoopConfigAccessor;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

public class SwerveDrivingMotor implements PIDTuneable {

    private static final int motorFreeSpeed = 800; //RPM

    private final SwerveConstants.MotorPosition position;
    private final SwerveModule parent;

    private RateLimiter rateLimiter;

    private final SparkMax motor;
    SparkMaxConfig config;

    // private final RelativeEncoder encoder;
    // private final ClosedLoopConfigAccessor PIDAccessor;
    // private final ClosedLoopConfig PIDSeter;

    private double velocitySet = 0;
    private boolean reversed = false;

    public SwerveDrivingMotor(SwerveConstants.MotorPosition position, SwerveModule parent){
        this.parent = parent;
        this.position = position;

        rateLimiter = new RateLimiter(2, 4);

        motor = new SparkMax(position.drivingCANid, SparkLowLevel.MotorType.kBrushless);
        config = new SparkMaxConfig();
        
        config.idleMode(IdleMode.kBrake);
        config.smartCurrentLimit(SwerveConstants.drivingMotorCurrentLimit);

        
        config.encoder.velocityConversionFactor(SwerveConstants.wheelCircumference / SwerveConstants.driveGearReduction / 60.0);

        
        config.closedLoop.feedbackSensor(FeedbackSensor.kPrimaryEncoder).pid(position.drivingP, position.drivingI, 0.0);

        motor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        // PIDSeter = config.closedLoop;
        // PIDAccessor = motor.configAccessor.closedLoop;
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

        if(position == SwerveConstants.MotorPosition.BACK_LEFT){
            // reversed = true;
        }
        motor.getClosedLoopController().setReference(


                rateLimiter.calculate(
                    velocitySet * (reversed? -1 : 1)
                ),
            SparkMax.ControlType.kVelocity
        );
    }

    public double getVelocityMeasured(){
        return motor.getEncoder().getVelocity();
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
        return motor.configAccessor.closedLoop.getP();
    }
    public double getI() {
        return motor.configAccessor.closedLoop.getI();
    }
    public double getD() {
        return motor.configAccessor.closedLoop.getD();
    }

    public void setP(double p) {
        config.closedLoop.p(p);
    }
    public void setI(double i) {
        config.closedLoop.i(i);
    }
    public void setD(double d) {
        config.closedLoop.d(d);
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
        config.closedLoop.i(0.0);
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
