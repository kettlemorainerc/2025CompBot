package org.usfirst.frc.team2077.subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class Elevator implements Subsystem {

    private static final TalonSRX
            leftElevatorMotor = new TalonSRX(1),
            rightElevatorMotor = new TalonSRX(2);

    public Elevator(){
        ;
    }
    /*raises the elevator w(ﾟДﾟ)w,
    all numbers are placeholders in all files made in this branch unless stated otherwise stated
    this will be placed in other places as well to remind you to remind you of this
    */

    public static void raiseRight(){
        rightElevatorMotor.set(TalonSRXControlMode.PercentOutput, .5);
    }
    public static void raiseLeft(){
        leftElevatorMotor.set(TalonSRXControlMode.PercentOutput, .5);
    }
    public static void lowerRight(){
        rightElevatorMotor.set(TalonSRXControlMode.PercentOutput, -.5);
    }
    public static void lowerLeft(){
        leftElevatorMotor.set(TalonSRXControlMode.PercentOutput, -.5);
    }

    public static void stop(){
        leftElevatorMotor.set(ControlMode.PercentOutput, 0);
        rightElevatorMotor.set(ControlMode.PercentOutput, 0);
    }

}
