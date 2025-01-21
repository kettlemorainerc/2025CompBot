package org.usfirst.frc.team2077.command;

import org.usfirst.frc.team2077.common.command.RepeatedCommand;
import org.usfirst.frc.team2077.subsystem.Elevator;

public class ElevatorPositionControl extends RepeatedCommand {

    public enum ROBOTSIDE {
        LEFT,
        RIGHT,
    }

    private final ROBOTSIDE robotside;

    public ElevatorPositionControl(ROBOTSIDE robotside) {
        this.robotside = robotside;
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
