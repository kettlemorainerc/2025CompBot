package org.usfirst.frc.team2077.command.autonomous;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.LimelightHelpers;
import org.usfirst.frc.team2077.RobotHardware;
import org.usfirst.frc.team2077.common.Clock;
import org.usfirst.frc.team2077.common.WheelPosition;
import org.usfirst.frc.team2077.common.drivetrain.AbstractChassis;
import org.usfirst.frc.team2077.common.math.Position;

public class AutoMove extends Command {

    private AbstractChassis chassis;

    private Position from, to;
    private double remainingDistance;
    private final double SPEED_LIMITER = 0.6;

    private double forward;
    private double strafe;
    private double direction;
    private double forwardMultiplier;
    private double strafeMultiplier;

    private double lastTime;
    private double moveTime;
    private double distanceChange;
    private double[] startEncoders;
    private double[] currentEncoders;

    public AutoMove(double forward, double strafe, double moveTime){
        this.forward = forward;
        this.strafe = strafe;
        this.moveTime = moveTime;
    }




    @Override
    public void initialize() {
        lastTime = Clock.getSeconds();

        chassis = RobotHardware.getInstance().getChassis();

        from = chassis.getPosition();
        to = from.copy(); to.move(forward, strafe, 0);
        direction = Math.atan2(forward, strafe);

        remainingDistance = Math.hypot(forward, strafe);

        forwardMultiplier = Math.sin(direction);
        strafeMultiplier = Math.cos(direction);

        startEncoders = getEncoders();
    }

    @Override
    public void execute() {


        currentEncoders = getEncoders();


        distanceChange = Math.abs(currentEncoders[0]-startEncoders[0])+Math.abs(currentEncoders[1]-startEncoders[1])+Math.abs(currentEncoders[2]-startEncoders[2])+Math.abs(currentEncoders[3]-startEncoders[3]);
        distanceChange *= 0.25;

        if(isFinished()){this.end(false);}
        chassis.setVelocityPercent(forwardMultiplier * SPEED_LIMITER , strafeMultiplier * SPEED_LIMITER );
    }

    @Override
    public void end(boolean interrupted) {
        chassis.halt();
    }

    @Override public boolean isFinished(){
        return(distanceChange>=remainingDistance);
    }

    private double[] getEncoders(){
        return new double[] {RobotHardware.getInstance().getWheel(WheelPosition.FRONT_LEFT).getDrivingMotor().getDrivingEncoderPosition(),
                RobotHardware.getInstance().getWheel(WheelPosition.FRONT_RIGHT).getDrivingMotor().getDrivingEncoderPosition(),
                RobotHardware.getInstance().getWheel(WheelPosition.BACK_RIGHT).getDrivingMotor().getDrivingEncoderPosition(),
                RobotHardware.getInstance().getWheel(WheelPosition.BACK_LEFT).getDrivingMotor().getDrivingEncoderPosition()};
    }


}