package org.usfirst.frc.team2077.command;

import org.usfirst.frc.team2077.common.command.RepeatedCommand;
import org.usfirst.frc.team2077.subsystem.Elevator;

public class ElevatorPositionControl extends RepeatedCommand {

    public enum MOVEDIRECTION {
        UP,
        DOWN
    }

    private final MOVEDIRECTION movedirection;
    private final Elevator elevator;



    public ElevatorPositionControl(MOVEDIRECTION movedirection, Elevator elevator) {
        this.movedirection = movedirection;
        this.elevator = elevator;


//I am an advocate for comments in human languages, please do not write me a paragraph describing what two lines do
    @Override
    public void initialize(){

        }

    @Override
    public void execute() {
            switch(movedirection){
                case UP:
                    elevator.raise();
                    break;
                case DOWN:
                    elevator.lower();
                    break;
            }
    }

    @Override
    public void end(boolean interrupted) {
        Elevator.stop();
    }

}
