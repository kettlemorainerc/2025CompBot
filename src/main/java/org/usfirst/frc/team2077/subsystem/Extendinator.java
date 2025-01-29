package org.usfirst.frc.team2077.subsystem;

//import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class Extendinator implements Subsystem {

    // private static final CANSparkMax motor = new CANSparkMax(100, CANSparkMax.MotorType.kBrushless); // im holding place

    public Extendinator() {

    }

    public static void extend() {
        // motor.set(10);
    }

    public static void retract() {
        //motor.set(-10);
    }

    public static void stopHaltCease() {
        //motor.set(0);
    }
}
