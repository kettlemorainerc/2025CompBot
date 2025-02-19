package org.usfirst.frc.team2077.subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.ClosedLoopConfig;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class Elevator implements Subsystem {

    private final SparkMax elevatorMotor;
    private final SparkClosedLoopController maxPID;



    public Elevator(){
        elevatorMotor = new SparkMax(1, SparkLowLevel.MotorType.kBrushless);
        maxPID = elevatorMotor.getClosedLoopController();
        SparkMaxConfig config = new SparkMaxConfig();
        config
                .inverted(false)
                .idleMode(SparkBaseConfig.IdleMode.kBrake);
        config.encoder
                .positionConversionFactor(1000)
                .velocityConversionFactor(1000);
        config.closedLoop
                .feedbackSensor(ClosedLoopConfig.FeedbackSensor.kPrimaryEncoder)
                .pid(1.0,0.0,0.0);
        elevatorMotor.configure(config, SparkBase.ResetMode.kResetSafeParameters, SparkBase.PersistMode.kPersistParameters);
    }
    /*raises the elevator w(ﾟДﾟ)w,
    all numbers are placeholders in all files made in this branch unless stated otherwise stated
    this will be placed in other places as well to remind you to remind you of this
    */

    public void raise(){elevatorMotor.set(0.01);}
    public void lower(){elevatorMotor.set(-0.01);}

    public void stop(){elevatorMotor.set(0);}
}
