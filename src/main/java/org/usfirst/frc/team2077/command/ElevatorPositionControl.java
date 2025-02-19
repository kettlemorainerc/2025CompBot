package org.usfirst.frc.team2077.command;

import org.usfirst.frc.team2077.common.command.RepeatedCommand;
import org.usfirst.frc.team2077.subsystem.Elevator;

public class ElevatorPositionControl extends RepeatedCommand {

    public enum MOVEDIRECTION {
        UP,
        DOWN
    }

    private final MOVEDIRECTION movedirection;



    public ElevatorPositionControl(MOVEDIRECTION movedirection) {
        this.movedirection = movedirection;


//I am an advocate for comments in human languages, please do not write me a paragraph describing what two lines do
    @Override
    public void initialize(){
        switch(MOVEDIRECTION){
            case UP:
                Elevator.raise();
                break;
            case DOWN:
                Elevator.lower();
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
