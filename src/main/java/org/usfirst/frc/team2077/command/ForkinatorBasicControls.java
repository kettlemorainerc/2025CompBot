package org.usfirst.frc.team2077.command;

import org.usfirst.frc.team2077.RobotHardware;
import org.usfirst.frc.team2077.common.command.RepeatedCommand;
import org.usfirst.frc.team2077.subsystem.Forkinator;

public class ForkinatorBasicControls extends RepeatedCommand {
    public enum ForkDirection{
        UP,
        DOWN
    }

    private final ForkDirection direction;
    private final Forkinator forkinator;

    public ForkinatorBasicControls(ForkDirection direction){
        this.direction = direction;
        forkinator = RobotHardware.getInstance().forkinator;
    }


    @Override
    public void execute() {
        if(direction == ForkDirection.UP){
            forkinator.raiseForkinator();
        }else if(direction == ForkDirection.DOWN){
            forkinator.lowerForkinator();
        }
        forkinator.printEncoder("Current Encoder Position:");
    }

    @Override
    public void end(boolean interrupted) {
        forkinator.stopForkinator();
    }
    
}
