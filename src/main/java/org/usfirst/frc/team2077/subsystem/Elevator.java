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
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Subsystem;
import org.usfirst.frc.team2077.util.PIDTuneable;
import org.usfirst.frc.team2077.util.SmartDash.SmartDashNumber;

public class Elevator implements Subsystem {

    private final double encoder;
    public SmartDashNumber elevatorP = new SmartDashNumber("Elevator P",1d, false);
    public SmartDashNumber elevatorI = new SmartDashNumber("Elevator I",0d, false);
    public SmartDashNumber elevatorD = new SmartDashNumber("Elevator D",0d, false);

    public SmartDashNumber topPosition;
    public SmartDashNumber middlePosition;
    public SmartDashNumber bottomPosition;

    private final SparkMax elevatorMotor;
    private final PIDController pid = new PIDController(elevatorP.get(), elevatorI.get(),elevatorD.get());



    public Elevator(){
        elevatorMotor = new SparkMax(1, SparkLowLevel.MotorType.kBrushless);
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

        encoder = elevatorMotor.getEncoder().getPosition();

        System.out.println(encoder);
    }
    /*raises the elevator w(ﾟДﾟ)w,
    all numbers are placeholders in all files made in this branch unless stated otherwise stated
    this will be placed in other places as well to remind you to remind you of this
    */

    public void raise(){elevatorMotor.set(0.01);}
    public void lower(){elevatorMotor.set(-0.01);}

    public void moveToTop(){
        elevatorMotor.set(pid.calculate(topPosition.get()-encoder, topPosition.get()));
        }

    public void moveToMiddle(){
        elevatorMotor.set(pid.calculate(middlePosition.get()-encoder, middlePosition.get()));
    }
    public void moveToBottom(){
        elevatorMotor.set(pid.calculate(bottomPosition.get()-encoder, bottomPosition.get()));
    }

    public void stop(){elevatorMotor.set(0);}
}
