package org.usfirst.frc.team2077.subsystem;

import org.usfirst.frc.team2077.common.command.RepeatedCommand;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj2.command.Subsystem;

public class Elevinator implements Subsystem {

    private final SparkMax motor;
    private final SparkClosedLoopController motorPid;
    private double min;
    private double max;

    public Elevinator(){
        motor = new SparkMax(21, MotorType.kBrushless);
        motorPid = motor.getClosedLoopController();
        SparkMaxConfig config = new SparkMaxConfig();
        config
            .inverted(true)
            .idleMode(IdleMode.kBrake);
        config.encoder
            .positionConversionFactor(1000)
            .velocityConversionFactor(1000);
        config.closedLoop
            .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
            .pid(1.0, 0.0, 0.0);
        
        motor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    public void raiseElevinator(){
    if(motor.getEncoder().getPosition() < max)
        motor.set(1);
    }

    public void lowerElevinator(){
        if(motor.getEncoder().getPosition() > min)
        motor.set(-1);
    }

    public void stopElevinator(){
        motor.set(0);
    }

}
