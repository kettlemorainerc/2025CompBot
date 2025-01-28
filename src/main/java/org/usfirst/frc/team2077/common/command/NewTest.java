package org.usfirst.frc.team2077.common.command;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;

import java.util.Arrays;
import java.util.List;

import org.usfirst.frc.team2077.RobotHardware;
import org.usfirst.frc.team2077.common.subsystem.MotorRun;

import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.util.RuntimeLoader;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.LimelightHelpers;
import pabeles.concurrency.IntOperatorTask.Max;

public class NewTest extends RepeatedCommand {

    public enum Direction{
        FORWARD,
        BACKWARD,
        AUTO
    }

    private static final int[] CORAL_APRIL_TAGS = new int[]{6, 7, 8, 9, 10, 11, 17, 18, 19, 20, 21, 22};

    private final MotorRun motorRun;
    private final Direction direction;

    public NewTest(Direction direction){
        // launcher = RobotHardware.getInstance().launcher;
        // this.target = target;
        motorRun = RobotHardware.getInstance().motorRun;
        
        this.direction = direction;
    }

    @Override
    public void execute() {
        // launcher.run(target);
        if (direction == Direction.FORWARD) {
            motorRun.startForward();
        } else if (direction == Direction.BACKWARD) {
            motorRun.startBackward();
        } else if (direction == Direction.AUTO) {
            double tx = LimelightHelpers.getTX("");
            int tid = (int) LimelightHelpers.getFiducialID("");
            if(Arrays.stream(CORAL_APRIL_TAGS).anyMatch(i -> i == tid)){
                if (tx > 2) {
                    motorRun.startForward();
                    // boolean hasTarget = LimelightHelpers.getTV("");
                    // SmartDashboard.putBoolean("Target", hasTarget);
                }else if(tx < -2){
                    motorRun.startBackward();
                }else{
                    motorRun.stopMotor();
                }
            }else{
                motorRun.startForward();
            }

        }else{
            System.out.println("Not Used Enum in NewTest");
        }

        

    }

    @Override
    public void end(boolean interrupted) {
        // launcher.stopLauncher();
        motorRun.stopMotor();
    }

    

    
}
