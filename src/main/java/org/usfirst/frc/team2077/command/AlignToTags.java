package org.usfirst.frc.team2077.command;

import java.util.Arrays;

import org.usfirst.frc.team2077.RobotHardware;
import org.usfirst.frc.team2077.common.command.RepeatedCommand;
import org.usfirst.frc.team2077.common.drivetrain.AbstractChassis;

import frc.robot.LimelightHelpers;

public class AlignToTags extends RepeatedCommand{

    private final int[] tags;
    private final AbstractChassis chassis;


    public AlignToTags(int[] tags){
        this.tags = tags;

        chassis = RobotHardware.getInstance().getChassis();
    }

    @Override
    public void execute() {
        double targetX = LimelightHelpers.getTX("");
        int targetID = (int) LimelightHelpers.getFiducialID("");
        if(Arrays.stream(tags).anyMatch(i -> i == targetID)){
            if(targetX > 2){
                chassis.setRotationPercent(0.2);
            }else if(targetX < -2){
                chassis.setRotationPercent(-0.2);
            }else{
                chassis.halt();
            }
        }
    }

    @Override
    public void end(boolean interrupted) {
        chassis.halt();
    }
    
}
