package org.usfirst.frc.team2077.command;

import org.usfirst.frc.team2077.RobotHardware;
import org.usfirst.frc.team2077.common.command.RepeatedCommand;
import org.usfirst.frc.team2077.subsystem.Elevinator;
import org.usfirst.frc.team2077.subsystem.Extendinator;

public class ElevinatorTest extends RepeatedCommand {

    public enum Move {
        UP,
        DOWN
    }

    // private final Extendinator extendinator;
    private final Elevinator elevinator;
    private final Move mover;

    public ElevinatorTest(Move move) {
        // extendinator = RobotHardware.getInstance().extendinator;
        elevinator = RobotHardware.getInstance().elevinator;
        mover = move;
 
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
        // extendinator.goToPickup();
        if(mover == Move.UP){
            elevinator.lowerElevinators();
        }else if(mover == Move.DOWN){
            elevinator.raiseElevinators();
        }

    }

    @Override
    public void end(boolean interrupted) {
        // Extendinator.stopHaltCease();
        elevinator.stopElevinators();
    }
}
