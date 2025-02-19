package org.usfirst.frc.team2077.subsystem;

import org.usfirst.frc.team2077.common.command.RepeatedCommand;

import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj2.command.Subsystem;

public class Forkinator implements Subsystem {

    private final SparkMax motor;
    private final SparkClosedLoopController motorPid;

    public Forkinator(){
        motor = new SparkMax(6, MotorType.kBrushless);
        motorPid = motor.getClosedLoopController();
        SparkMaxConfig config = new SparkMaxConfig();
        config
            .inverted(false)
            .idleMode(IdleMode.kBrake);
        config.encoder
            .positionConversionFactor(1000)
            .velocityConversionFactor(1000);
        config.closedLoop
            .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
            .pid(1.0, 0.0, 0.0);
        
        motor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    public void raiseForkinator(){
        motor.set(0.2);
    }

    public void lowerForkinator(){
        motor.set(-0.2);
    }

    public void stopForkinator(){
        motor.set(0);
    }

}
