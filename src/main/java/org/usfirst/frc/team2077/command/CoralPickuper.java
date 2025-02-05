package org.usfirst.frc.team2077.command;

import org.usfirst.frc.team2077.RobotHardware;
import org.usfirst.frc.team2077.common.command.RepeatedCommand;
import org.usfirst.frc.team2077.subsystem.Extendinator;

public class CoralPickuper extends RepeatedCommand {

    private final Extendinator extendinator;

    public CoralPickuper() {
        extendinator = RobotHardware.getInstance().extendinator;
 
    }

    @Override
    public void initialize() {
        // switch (movedirection){
        //     case IN:
        //         Extendinator.retract();
        //         break;
        //     case OUT:
        //         Extendinator.extend();
        //         break;
        // }
    }

    @Override
    public void execute() {
        extendinator.goToPickup();

    }

    @Override
    public void end(boolean interrupted) {
        // Extendinator.stopHaltCease();
    }
}
