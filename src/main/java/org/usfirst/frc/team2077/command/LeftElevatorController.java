package org.usfirst.frc.team2077.command;

import org.usfirst.frc.team2077.common.command.RepeatedCommand;
import org.usfirst.frc.team2077.subsystem.Elevator;

public class LeftElevatorController extends RepeatedCommand {

    public enum LEFTELEVATORDIRECTION {
        UP,
        DOWN,
    }

    private final LEFTELEVATORDIRECTION leftelevatordirection;


    public LeftElevatorController(LEFTELEVATORDIRECTION leftelevatordirection) {
        this.leftelevatordirection = leftelevatordirection;
    }


    @Override
    public void initialize(){
        switch(leftelevatordirection){
            case UP:
                Elevator.raiseLeft();
                break;
            case DOWN:
                Elevator.lowerLeft();
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
