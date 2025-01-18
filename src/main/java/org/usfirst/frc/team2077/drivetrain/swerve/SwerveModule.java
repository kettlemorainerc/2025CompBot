package org.usfirst.frc.team2077.drivetrain.swerve;

import edu.wpi.first.wpilibj2.command.Subsystem;
import org.usfirst.frc.team2077.common.WheelPosition;
import org.usfirst.frc.team2077.common.drivetrain.DriveModuleIF;

public class
SwerveModule implements Subsystem, DriveModuleIF, SwerveModuleIF {

    private final SwerveConstants.MotorPosition position;

    public boolean calibrating = false;
    public boolean atAngle = false;

    public static boolean notAllAtAngle = false;

    private final SwerveDrivingMotor drivingMotor;
    private final SwerveGuidingMotor guidingMotor;

    public SwerveModule(SwerveConstants.MotorPosition position){
        this.position = position;

        drivingMotor = new SwerveDrivingMotor(position, this);
        guidingMotor = new SwerveGuidingMotor(position, this);

        this.register();
    }

    @Override
    public void periodic(){
        if(calibrating) return;


        drivingMotor.update();

        guidingMotor.update();

    }

    @Override
    public void setVelocity(double velocity) {
        drivingMotor.setVelocity(velocity);
    }

    @Override
    public void setAngle(double angle) {
        guidingMotor.setAngle(angle);
    }

    @Override
    public WheelPosition getWheelPosition() {
        return WheelPosition.valueOf(position.name());
    }

    @Override
    public double getVelocitySet() {
        return drivingMotor.getVelocitySet();
    }

    @Override
    public double getVelocityMeasured() {
        return drivingMotor.getVelocityMeasured();
    }

    @Override
    public double getAngle() {
        return guidingMotor.getAngle();
    }

    public boolean isAtAngle(){
        return guidingMotor.atAngle();
    }

    /**
     * @Returns a value between 0 and 1 that represents how close the wheel is to its target angle.
     * This is used for throttle, for what percent the driving motor should be set to.
     * */
    public double dotToAngle(){
        return Math.abs(Math.cos(guidingMotor.distanceToTarget()));
    }

    @Override
    public double getMaximumSpeed() {
        return drivingMotor.getMaximumSpeed();
    }

    public SwerveDrivingMotor getDrivingMotor(){
        return drivingMotor;
    }

    public SwerveGuidingMotor getGuidingMotor(){
        return guidingMotor;
    }

}
