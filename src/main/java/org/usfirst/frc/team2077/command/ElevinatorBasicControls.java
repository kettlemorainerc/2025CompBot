package org.usfirst.frc.team2077.command;

import org.usfirst.frc.team2077.RobotHardware;
import org.usfirst.frc.team2077.common.command.RepeatedCommand;
import org.usfirst.frc.team2077.common.command.autonomous.AutoLaunch;
import org.usfirst.frc.team2077.subsystem.Elevinator;

import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ElevinatorBasicControls extends RepeatedCommand {

    public enum ElevatorDirection {
        RAISE,
        LOWER,
        POS1,
        POS2,
        POS3,
        POS4,
        POSPLAYER
    }

    // private final Extendinator extendinator;
    private final Elevinator elevinator;
    private final ElevatorDirection move;
    private final int pos1;
    private final int pos2;
    private final int pos3;
    private final int pos4;
    private final int posPlayer;


    public ElevinatorBasicControls(ElevatorDirection move) {
        elevinator = RobotHardware.getInstance().elevinator;
        this.move = move;

        pos1 = 6000;
        pos2 = 26500;
        pos3 = 74000;
        pos4 = 150000;
        posPlayer = 10000;
 
    }

    @Override
    public void initialize() {
        SequentialCommandGroup auto = new SequentialCommandGroup();
            // int autonomousNumber = autoDash.get().intValue();
        // System.out.println("START OF INIT");
        // auto.addCommands(
        //     new AutoLaunch()
        // );
        // auto.schedule();
        // System.out.println("END OF INIT");

    }

    @Override
    public void execute() {
        // extendinator.goToPickup();


        if(move == ElevatorDirection.RAISE){
            elevinator.raiseElevinator();
        }else if(move == ElevatorDirection.LOWER){
            elevinator.lowerElevinator();
        }else if(move == ElevatorDirection.POS1){
            if(elevinator.getEncoderPosition() < pos1-500){
                elevinator.raiseElevinator();
            }else if(elevinator.getEncoderPosition() > pos1+500){
                elevinator.lowerElevinator();
            }else{
                elevinator.stopElevinator();
            }
        }else if(move == ElevatorDirection.POS2){
            if(elevinator.getEncoderPosition() < pos2-1000){
                elevinator.raiseElevinator();
            }else if(elevinator.getEncoderPosition() > pos2+1000){
                elevinator.lowerElevinator();
            }else{
                elevinator.stopElevinator();
            }
        }else if(move == ElevatorDirection.POS3){
            if(elevinator.getEncoderPosition() < pos3-1000){
                elevinator.raiseElevinator();
            }else if(elevinator.getEncoderPosition() > pos3+1000){
                elevinator.lowerElevinator();
            }else{
                elevinator.stopElevinator();
            }
        }else if(move == ElevatorDirection.POS4){
            if(elevinator.getEncoderPosition() < pos4-1000){
                elevinator.raiseElevinator();
            }else if(elevinator.getEncoderPosition() > pos4+1000){
                elevinator.lowerElevinator();
            }else{
                elevinator.stopElevinator();
            }
        }else if(move == ElevatorDirection.POSPLAYER){
            if(elevinator.getEncoderPosition() < posPlayer-1000){
                elevinator.raiseElevinator();
            }else if(elevinator.getEncoderPosition() > posPlayer+1000){
                elevinator.lowerElevinator();
            }else{
                elevinator.stopElevinator();
            }
        }
        

    }

    @Override
    public void end(boolean interrupted) {
        // Extendinator.stopHaltCease();
        elevinator.stopElevinator();
    }
}
