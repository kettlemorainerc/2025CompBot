package org.usfirst.frc.team2077.command.autonomous;

import edu.wpi.first.wpilibj2.command.Command;
import org.usfirst.frc.team2077.RobotHardware;
import org.usfirst.frc.team2077.common.Clock;
import org.usfirst.frc.team2077.common.drivetrain.AbstractChassis;

public class TimeBasedAutoMove extends Command {

    private AbstractChassis chassis;

    private final double forward;
    private final double strafe;
    private final double moveTime;
    private double lastTime;
    private double timeSinceStart;

    public TimeBasedAutoMove(double forward, double strafe, double moveTime){
        this.forward = forward;
        this.strafe = strafe;
        this.moveTime = moveTime;
    }

    @Override
    public void initialize() {
        lastTime = Clock.getSeconds();

        chassis = RobotHardware.getInstance().getChassis();
    }

    @Override
    public void execute() {
        chassis.setVelocity(forward, strafe);

        timeSinceStart = Clock.getSeconds();
    }

    @Override
    public void end(boolean interrupted) {
        chassis.halt();
    }

    @Override
    public boolean isFinished() {
        return (timeSinceStart >= (moveTime + lastTime));
    }
}
