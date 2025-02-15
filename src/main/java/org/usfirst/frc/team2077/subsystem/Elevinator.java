package org.usfirst.frc.team2077.subsystem;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.Subsystem;

import static edu.wpi.first.units.Units.Percent;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix6.signals.SensorPhaseValue;

public class Elevinator implements Subsystem{
    private final TalonSRX leftLifter;
    private final TalonSRX rightLifter;
    private final DigitalInput rightSwitch;
    private final DigitalInput leftSwitch;
    private boolean useEncoders = false;
    //private final Encoder encoderLeft;

    public Elevinator() {
        leftLifter = new TalonSRX(16);
        rightLifter = new TalonSRX(12);
        rightSwitch = new DigitalInput(0);
        leftSwitch = new DigitalInput(1);
        //encoderLeft = new Encoder(0, 1, false, Encoder.EncodingType.k2X);


        leftLifter.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
        leftLifter.configSelectedFeedbackCoefficient(1.0, 0, 0);
        rightLifter.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
        rightLifter.configSelectedFeedbackCoefficient(1.0, 0, 0);
        // leftLifter.setSensorPhase();
    }

    public void raiseElevinators(){
        setBoth(0.5);
    }

    public void lowerElevinators(){
        setBoth(-0.5);
    }

    public void setBoth(double percentage){
        if(useEncoders == true){
            System.out.println("leftLifter encoder: " + leftLifter.getSelectedSensorPosition());

            if ((leftLifter.getSelectedSensorPosition() < 50 && percentage < 0) || (leftLifter.getSelectedSensorPosition() > 500 && percentage > 0)) {
                leftLifter.set(ControlMode.PercentOutput, 0);
            }else{
                leftLifter.set(ControlMode.PercentOutput, percentage);
            }
    
            //Figure out which lifter needs to be inverted, otherwise the robot will explode and kill everyone in the building...
            if ((rightLifter.getSelectedSensorPosition() < 50 && percentage < 0) || (rightLifter.getSelectedSensorPosition() > 500 && percentage > 0)) {
                rightLifter.set(ControlMode.PercentOutput, 0);
            }else{
                rightLifter.set(ControlMode.PercentOutput, percentage);
            }
        }else if(useEncoders == false){
            if(leftSwitch.get() && percentage < 0){
                leftLifter.set(ControlMode.PercentOutput, 0);
            }else{
                leftLifter.set(ControlMode.PercentOutput, percentage);
            }
            if(rightSwitch.get()  && percentage < 0){
                rightLifter.set(ControlMode.PercentOutput, 0);
            }else{
                rightLifter.set(ControlMode.PercentOutput, percentage);
            }
            
        }


    }

    public void stopElevinators(){
        leftLifter.set(ControlMode.PercentOutput, 0);
        rightLifter.set(ControlMode.PercentOutput, 0);
    }

}
