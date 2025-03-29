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

public class Forkinator implements Subsystem {

    private final SparkMax motor;
    private final SparkClosedLoopController motorPid;
    private double max;
    private double mid;
    private double min;

    public Forkinator(){
        motor = new SparkMax(4, MotorType.kBrushless);
        min = 0;
        mid = -8000.0;
        max = 150000;
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
        // printEncoder("Start encoder position:");
    }

    public void raiseForkinator(){
        if(motor.getEncoder().getPosition() < max){
            motor.set(0.4);
        }
        // printEncoder("raise");
    }

    public void lowerForkinator(){
        // if(motor.getEncoder().getPosition() > min){
            motor.set(-0.4);
        // }
        // printEncoder("lower");
    }

    public void stopForkinator(){
        motor.set(0);
    }

    public double getEncoderPosition(){
        return(motor.getEncoder().getPosition());
    }

    // public void printEncoder(String text){
    //     System.out.println(text + " " + motor.getEncoder().getPosition());
    // }

}
