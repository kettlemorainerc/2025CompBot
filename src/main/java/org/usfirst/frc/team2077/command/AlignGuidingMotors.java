package org.usfirst.frc.team2077.command;

import static org.usfirst.frc.team2077.common.WheelPosition.BACK_LEFT;
import static org.usfirst.frc.team2077.common.WheelPosition.BACK_RIGHT;
import static org.usfirst.frc.team2077.common.WheelPosition.FRONT_LEFT;
import static org.usfirst.frc.team2077.common.WheelPosition.FRONT_RIGHT;

import org.usfirst.frc.team2077.RobotHardware;
import org.usfirst.frc.team2077.common.WheelPosition;
import org.usfirst.frc.team2077.common.command.RepeatedCommand;

import com.revrobotics.spark.SparkMax;

public class AlignGuidingMotors extends RepeatedCommand{  
    
    private RobotHardware robotHardware;

    
    public AlignGuidingMotors(){
        robotHardware = RobotHardware.getInstance();
        robotHardware.getWheel(FRONT_LEFT).getGuidingMotor().alignGuidingMotors();
        robotHardware.getWheel(FRONT_RIGHT).getGuidingMotor().alignGuidingMotors();
        robotHardware.getWheel(BACK_LEFT).getGuidingMotor().alignGuidingMotors();
        robotHardware.getWheel(BACK_RIGHT).getGuidingMotor().alignGuidingMotors();
    }

    @Override
    public void initialize() {
        
    }

    @Override
    public void execute() {
        
    }

    @Override
    public void end(boolean interrupted) {
        
    }

    
}
