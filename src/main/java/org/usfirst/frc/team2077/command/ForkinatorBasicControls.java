package org.usfirst.frc.team2077.command;

import org.usfirst.frc.team2077.RobotHardware;
import org.usfirst.frc.team2077.command.ElevinatorBasicControls.ElevatorDirection;
import org.usfirst.frc.team2077.common.command.RepeatedCommand;
import org.usfirst.frc.team2077.subsystem.Forkinator;

public class ForkinatorBasicControls extends RepeatedCommand {
    public enum ForkDirection{
        UP,
        DOWN,
        COLLECT,
        DROP,
        HOLD
    }

    private final ForkDirection direction;
    private final Forkinator forkinator;

    private final int collectPos;
    private final int dropPos;
    private final int holdPos;

    public ForkinatorBasicControls(ForkDirection direction){
        this.direction = direction;
        forkinator = RobotHardware.getInstance().forkinator;

        collectPos = 153000;
        dropPos = 90000;
        holdPos = 188000;
    }


    @Override
    public void execute() {
        if(direction == ForkDirection.UP){
            forkinator.raiseForkinator();
        }else if(direction == ForkDirection.DOWN){
            forkinator.lowerForkinator();
        }else if(direction == ForkDirection.COLLECT){
            if(forkinator.getEncoderPosition() < collectPos-500){
                forkinator.raiseForkinator();
            }else if(forkinator.getEncoderPosition() > collectPos+500){
                forkinator.lowerForkinator();
            }else{
                forkinator.stopForkinator();
            }
        }else if(direction == ForkDirection.DROP){
            if(forkinator.getEncoderPosition() < dropPos-500){
                forkinator.raiseForkinator();
            }else if(forkinator.getEncoderPosition() > dropPos+500){
                forkinator.lowerForkinator();
            }else{
                forkinator.stopForkinator();
            }
        }else if(direction == ForkDirection.HOLD){
            if(forkinator.getEncoderPosition() < holdPos-500){
                forkinator.raiseForkinator();
            }else if(forkinator.getEncoderPosition() > holdPos+500){
                forkinator.lowerForkinator();
            }else{
                forkinator.stopForkinator();
            }
        }
        System.out.println(forkinator.getEncoderPosition());
    }

    @Override
    public void end(boolean interrupted) {
        forkinator.stopForkinator();
    }
    
}
