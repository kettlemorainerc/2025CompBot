package org.usfirst.frc.team2077.subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class Elevator implements Subsystem {

    private static final TalonSRX
            elevatorMotor = new TalonSRX(1),

    public Elevator(){
        ;
    }
    /*raises the elevator w(ﾟДﾟ)w,
    all numbers are placeholders in all files made in this branch unless stated otherwise stated
    this will be placed in other places as well to remind you to remind you of this
    */

    public static void raise(){
        elevatorMotor.set(TalonSRXControlMode.PercentOutput, .5);
    }
    public static void lower(){
        elevatorMotor.set(TalonSRXControlMode.PercentOutput, -.5);
    }

    public static void stop(){
        elevatorMotor.set(ControlMode.PercentOutput, 0);
    }

}
