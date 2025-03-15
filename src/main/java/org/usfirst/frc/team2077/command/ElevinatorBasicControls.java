package org.usfirst.frc.team2077.command;

import org.usfirst.frc.team2077.RobotHardware;
import org.usfirst.frc.team2077.common.command.RepeatedCommand;
import org.usfirst.frc.team2077.subsystem.Elevinator;

public class ElevinatorBasicControls extends RepeatedCommand {

    public enum ElevatorDirection {
        RAISE,
        LOWER
    }

    // private final Extendinator extendinator;
    private final Elevinator elevinator;
    private final ElevatorDirection move;

    public ElevinatorBasicControls(ElevatorDirection move) {
        elevinator = RobotHardware.getInstance().elevinator;
        this.move = move;
 
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        // extendinator.goToPickup();
        if(move == ElevatorDirection.RAISE){
            elevinator.raiseElevinator();
        }else if(move == ElevatorDirection.LOWER){
            elevinator.lowerElevinator();
        }

    }

    @Override
    public void end(boolean interrupted) {
        // Extendinator.stopHaltCease();
        elevinator.stopElevinator();
    }
}
