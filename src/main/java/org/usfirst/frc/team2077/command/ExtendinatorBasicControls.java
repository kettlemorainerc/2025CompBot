package org.usfirst.frc.team2077.command;

import org.usfirst.frc.team2077.RobotHardware;
import org.usfirst.frc.team2077.common.command.RepeatedCommand;
import org.usfirst.frc.team2077.subsystem.Extendinator;

public class ExtendinatorBasicControls extends RepeatedCommand {
    public enum ExtendDirection{
        OUT,
        IN,
    }

    private final ExtendDirection direction;
    private final Extendinator extendinator;

    public ExtendinatorBasicControls(ExtendDirection direction){
        this.direction = direction;
        extendinator = RobotHardware.getInstance().extendinator;
    }


    @Override
    public void execute() {
        if(direction == ExtendDirection.IN){
            extendinator.raiseExtendinator();
        }else if(direction == ExtendDirection.OUT){
            extendinator.lowerExtendinator();
        }
        // extendinator.printEncoder("Current Encoder Position:");
    }

    @Override
    public void end(boolean interrupted) {
        extendinator.stopExtendinator();
    }
    
}