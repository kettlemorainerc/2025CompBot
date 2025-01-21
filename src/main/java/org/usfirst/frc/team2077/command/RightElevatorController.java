package org.usfirst.frc.team2077.command;

import org.usfirst.frc.team2077.common.command.RepeatedCommand;

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

    }

    @Override
    public void execute() {

    }

    @Override
    public void end(boolean interrupted) {

    }
}
