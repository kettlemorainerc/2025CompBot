package org.usfirst.frc.team2077.subsystem;

import edu.wpi.first.wpilibj2.command.Subsystem;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Elevinator implements Subsystem{
    private final TalonSRX leftLifter;
    private final TalonSRX rightLifter;

    public Elevinator() {
        leftLifter = new TalonSRX(21);
        rightLifter = new TalonSRX(22);
    }
}
