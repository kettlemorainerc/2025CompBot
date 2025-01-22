package org.usfirst.frc.team2077.command;

import org.usfirst.frc.team2077.common.command.RepeatedCommand;
import org.usfirst.frc.team2077.subsystem.Elevator;

public class RightElevatorController extends RepeatedCommand {

    public enum RIGHTELEVATORDIRECTION {
        UP,
        DOWN,
    }

    private final RIGHTELEVATORDIRECTION rightelevatordirection;


    public RightElevatorController(RIGHTELEVATORDIRECTION rightelevatordirection) {
        this.rightelevatordirection = rightelevatordirection;
    }

    @Override
    public void initialize(){
        switch(rightelevatordirection){
            case UP:
                Elevator.raiseRight();
                break;
            case DOWN:
                Elevator.lowerRight();
                break;
        }
    }

    @Override
    public void execute() {

    }

    @Override
    public void end(boolean interrupted) {
        Elevator.stop();
    }
}
