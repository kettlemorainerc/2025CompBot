package org.usfirst.frc.team2077.command;

import org.usfirst.frc.team2077.RobotHardware;
import org.usfirst.frc.team2077.common.command.RepeatedCommand;
import org.usfirst.frc.team2077.subsystem.Forkinator;

public class ForkinatorBasicControls extends RepeatedCommand {
    public enum Direction{
        UP,
        DOWN
    }

    private final Direction direction;
    private final Forkinator forkinator;

    public ForkinatorBasicControls(Direction direction){
        this.direction = direction;
        forkinator = RobotHardware.getInstance().forkinator;
    }

    @Override
    public void execute() {
        if(direction == Direction.UP){
            forkinator.raiseForkinator();
        }
    }

    @Override
    public void end(boolean interrupted) {
        forkinator.stopForkinator();
    }
    
}
