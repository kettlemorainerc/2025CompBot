package org.usfirst.frc.team2077.command;

import org.usfirst.frc.team2077.common.command.RepeatedCommand;
import org.usfirst.frc.team2077.subsystem.Extendinator;

public class ExtendController extends RepeatedCommand {

    public enum MOVEDIRECTION {
        IN,
        OUT,
    }

    private final MOVEDIRECTION movedirection;

    public ExtendController(MOVEDIRECTION movedirection) {
        this.movedirection = movedirection;

    }

    @Override
    public void initialize() {
        switch (movedirection){
            case IN:
                Extendinator.retract();
                break;
            case OUT:
                Extendinator.extend();
                break;
        }
    }

    @Override
    public void execute() {

    }

    @Override
    public void end(boolean interrupted) {
        Extendinator.stopHaltCease();
    }
}
