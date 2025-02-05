package org.usfirst.frc.team2077.subsystem;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Elevinator implements Subsystem{
    private final TalonSRX leftLifter;
    // private final TalonSRX rightLifter;
    private final DigitalInput bottomRightSwitch;
    private final DigitalInput bottomLeftSwitch;

    public Elevinator() {
        leftLifter = new TalonSRX(16);
        // rightLifter = new TalonSRX(22);
        bottomRightSwitch = new DigitalInput(0);
        bottomLeftSwitch = new DigitalInput(1);
    }

    public void raiseElevinators(){
        leftLifter.set(ControlMode.PercentOutput, 0.5);
        // rightLifter.set(ControlMode.PercentOutput, 0.5);
    }

    public void lowerElevinators(){
        leftLifter.set(ControlMode.PercentOutput, -0.5);
        // rightLifter.set(ControlMode.PercentOutput, -0.5);
    }

    public void raiseRightElevinator(){
        // rightLifter.set(ControlMode.PercentOutput, 0.5);
    }

    public void raiseLeftElevinator(){
        leftLifter.set(ControlMode.PercentOutput, 0.5);
    }

    public void lowerRightElevinator(){
        // rightLifter.set(ControlMode.PercentOutput, -0.5);
    }

    public void lowerLeftElevinator(){
        leftLifter.set(ControlMode.PercentOutput, -0.5);
    }

    public void stopElevinators(){
        leftLifter.set(ControlMode.PercentOutput, 0);
        // rightLifter.set(ControlMode.PercentOutput, 0);
    }
}
