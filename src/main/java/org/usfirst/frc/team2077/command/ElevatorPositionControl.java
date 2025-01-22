package org.usfirst.frc.team2077.command;

import org.usfirst.frc.team2077.common.command.RepeatedCommand;
import org.usfirst.frc.team2077.subsystem.Elevator;

public class ElevatorPositionControl extends RepeatedCommand {

    public enum ROBOTSIDE {
        LEFT,
        RIGHT,
        BOTH
    }
    public enum MOVEDIRECTION {
        UP,
        DOWN
    }

    private final ROBOTSIDE robotside;
    private final LeftElevatorController.LEFTELEVATORDIRECTION leftmoveirection;
    private final RightElevatorController.RIGHTELEVATORDIRECTION rightmovedirection;

    public ElevatorPositionControl(ROBOTSIDE robotside) {
        this.robotside = robotside;
        rightmovedirection = null;
        leftmoveirection = null;
    }
    public ElevatorPositionControl(RightElevatorController.RIGHTELEVATORDIRECTION rightelevatordirection, LeftElevatorController.LEFTELEVATORDIRECTION leftelevatordirection){
        this.leftmoveirection = leftelevatordirection;
        this.rightmovedirection = rightelevatordirection;
        robotside = ROBOTSIDE.BOTH;
    }

    @Override
    public void initialize(){
        switch(robotside){
            case LEFT:
                new LeftElevatorController(leftmoveirection);
                break;
            case RIGHT:
                new RightElevatorController(rightmovedirection);
                break;
            case BOTH:
                new LeftElevatorController(leftmoveirection);
                new RightElevatorController(rightmovedirection);
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
