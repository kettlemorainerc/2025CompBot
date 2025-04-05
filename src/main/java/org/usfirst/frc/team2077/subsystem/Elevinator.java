package org.usfirst.frc.team2077.subsystem;

import com.revrobotics.spark.config.SoftLimitConfig;
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

import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class Elevinator implements Subsystem {

    private final SparkMax motor;
    private final SparkClosedLoopController motorPid;
    // private double min;
    // private double max;

    // private final NetworkTableInstance postionPrint;
    // DoublePublisher motorPosition;

    public Elevinator(){
        motor = new SparkMax(21, MotorType.kBrushless);
        // min = 0;
        // max = 0;
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

        SoftLimitConfig limitConfig = new SoftLimitConfig();
        limitConfig.forwardSoftLimitEnabled(true);
        limitConfig.reverseSoftLimitEnabled(true);
        limitConfig.forwardSoftLimit(150000);
        limitConfig.reverseSoftLimit(6000);
        config.softLimit.apply(limitConfig);
        
        
        motor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        // postionPrint = NetworkTableInstance.getDefault();
        // postionPrint.getTable("SmartDashboard");
        // motorPosition = postionPrint.getDoubleTopic(getName()+" Position").publish();
    }

    public void raiseElevinator(){
    // if(motor.getEncoder().getPosition() < max)
        motor.set(0.6);
        // motorPosition.set(motor.getEncoder().getPosition());
        SmartDashboard.putNumber("position", getEncoderPosition());
    }

    public void lowerElevinator(){
        // if(motor.getEncoder().getPosition() > min)
        motor.set(-0.5);
        // motorPosition.set(motor.getEncoder().getPosition());
        SmartDashboard.putNumber("position", getEncoderPosition());
    }

    public void stopElevinator(){
        motor.set(0);
    }

    public double getEncoderPosition(){
        return(motor.getEncoder().getPosition());
    }

}
