package org.usfirst.frc.team2077.common.command;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;

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
import pabeles.concurrency.IntOperatorTask.Max;

public class NewTest extends RepeatedCommand {

    public enum Direction{
        FORWARD,
        BACKWARD
    }

    // private final Launcher launcher;
    // private final Launcher.Target target;

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
        } else {
            motorRun.startBackward();
        }

    }

    @Override
    public void end(boolean interrupted) {
        // launcher.stopLauncher();
        motorRun.stopMotor();
    }

    

    
}
