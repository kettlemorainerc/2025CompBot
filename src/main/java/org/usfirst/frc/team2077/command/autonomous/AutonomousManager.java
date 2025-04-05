package org.usfirst.frc.team2077.command.autonomous;

import edu.wpi.first.wpilibj2.command.Command;
import org.usfirst.frc.team2077.common.drivetrain.AbstractChassis;

public class AutonomousManager extends Command {

    private AbstractChassis chassis;
    private boolean finished;

    public enum AUTONOMOUSTYPE{
        BASIC_MOVE,
        CORAL_GET
    }
    public AUTONOMOUSTYPE AutonomousType;

    public AutonomousManager(AUTONOMOUSTYPE autoType){
        this.AutonomousType = autoType;
    }

    @Override
    public void initialize() {
        finished = false;
    }

    @Override
    public void execute() {
        switch (AutonomousType){
            case BASIC_MOVE:
                new AutoMove(1,0);
                finished = true;
                break;
            case CORAL_GET:
                // autonomous is evil
                break;
        }
    }

    @Override
    public void end(boolean interrupted) {
        chassis.halt();
    }

    @Override
    public boolean isFinished() {
        return finished;
    }
}
