package org.usfirst.frc.team2077.common;

import com.studica.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj2.command.*;
import org.usfirst.frc.team2077.common.drivetrain.*;
import org.usfirst.frc.team2077.common.sensor.*;

import java.util.EnumMap;
import java.util.Map;

public abstract class HardwareRequirements<DriveModule, Chassis extends AbstractChassis<? super DriveModule>> {
    private final Subsystem heading;
    private final Subsystem position;
    private final AngleSensor angleSensor;
    private final AHRS navX;

    public HardwareRequirements() {
        heading = makeHeading();
        position = makePosition();
        angleSensor = makeAngleSensor();
        navX = makeNavX();
    }

    protected AngleSensor makeAngleSensor() {return null;}
    protected AHRS makeNavX() {return null;}
    protected Subsystem makeHeading() {return new Subsystem() {};}
    protected Subsystem makePosition() {return new Subsystem() {};}

    public Subsystem getHeading() {
        return heading;
    }

    public Subsystem getPosition() {
        return position;
    }

    public abstract Chassis getChassis();
    public abstract DriveModule getWheel(WheelPosition pos);

    public AngleSensor getAngleSensor() {
        return angleSensor;
    }

    public AHRS getNavX() {
        return navX;
    }

}
