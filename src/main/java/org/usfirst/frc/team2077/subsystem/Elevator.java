package org.usfirst.frc.team2077.subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class Elevator implements Subsystem {

    public static enum ROBOTSIDE {
        LEFT,
        RIGHT
    }

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

    public static void raise(ROBOTSIDE robotside){
        if (robotside == ROBOTSIDE.LEFT) {
            leftElevatorMotor.set(ControlMode.PercentOutput, 0.5);
        }else if(robotside == ROBOTSIDE.RIGHT){
            rightElevatorMotor.set(ControlMode.PercentOutput, 0.5);
        }else {
            leftElevatorMotor.set(ControlMode.PercentOutput, 0.5);
            rightElevatorMotor.set(ControlMode.PercentOutput, 0.5);
        }
    }
    public void lower(ROBOTSIDE robotside){
        if (robotside == ROBOTSIDE.LEFT) {
            leftElevatorMotor.set(ControlMode.PercentOutput, -0.5);
        }else if(robotside == ROBOTSIDE.RIGHT){
            rightElevatorMotor.set(ControlMode.PercentOutput, -0.5);
        }else {
            leftElevatorMotor.set(ControlMode.PercentOutput, -.75);
            rightElevatorMotor.set(ControlMode.PercentOutput, -.75);
        }
    }
    public void stop(){
        leftElevatorMotor.set(ControlMode.PercentOutput, 0);
        rightElevatorMotor.set(ControlMode.PercentOutput, 0);
    }

}
