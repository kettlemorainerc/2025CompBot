package org.usfirst.frc.team2077.command;

import org.usfirst.frc.team2077.common.command.RepeatedCommand;
import org.usfirst.frc.team2077.subsystem.Elevator;

public class ElevatorPositionControl extends RepeatedCommand {

    public enum INPUTDIRECTION{
        UP,
        DOWN
    }

    private final INPUTDIRECTION direction;
    private final Elevator.ROBOTSIDE robotside;
    private final Elevator elevator;

    public ElevatorPositionControl(INPUTDIRECTION direction, Elevator.ROBOTSIDE robotside, Elevator elevator) {
        this.direction = direction;
        this.robotside = robotside;
        this.elevator = elevator;
    }

    @Override
    public void initialize(){
        switch (direction){
            case UP:
                Elevator.raise(robotside);
                break;
            case DOWN:

                break;
        }

    }

    @Override
    public void execute() {

    }

    @Override
    public void end(boolean interrupted) {

    }
}