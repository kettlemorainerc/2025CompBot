package org.usfirst.frc.team2077.common.command;

import org.usfirst.frc.team2077.common.WheelPosition;
import org.usfirst.frc.team2077.common.drivetrain.DriveModuleIF;

public class TestDriveModule implements DriveModuleIF {
    private final double maxSpeed;
    private final WheelPosition position;
    private double currentVelocity;

    public TestDriveModule(WheelPosition position, double maxSpeed) {
        this.maxSpeed = maxSpeed;
        this.position = position;
    }

    @Override public double getMaximumSpeed() {
        return maxSpeed;
    }

    @Override public void setVelocity(double velocity) {
        this.currentVelocity = velocity;
    }

    @Override public WheelPosition getWheelPosition() {
        return position;
    }

    @Override public double getVelocity() {
        return currentVelocity;
    }

    @Override public double getDistance() {return 0;}
    @Override public void resetDistance() {}
}
