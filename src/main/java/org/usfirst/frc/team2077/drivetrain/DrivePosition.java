package org.usfirst.frc.team2077.drivetrain;

import org.usfirst.frc.team2077.common.WheelPosition;

public enum DrivePosition implements org.usfirst.frc.team2077.common.drivetrain.DrivePosition {
    FRONT_RIGHT(WheelPosition.FRONT_RIGHT, 2, true, Constants.WHEEL_GEAR_RATIO, Constants.WHEEL_RADIUS, Constants.MAX_WHEEL_RPM, 1e-4, 1e-6, 0),
    BACK_RIGHT(WheelPosition.BACK_RIGHT, 1, true, Constants.WHEEL_GEAR_RATIO, Constants.WHEEL_RADIUS, Constants.MAX_WHEEL_RPM, 1.1e-4, 1e-6, 0),
    BACK_LEFT(WheelPosition.BACK_LEFT, 4, false, Constants.WHEEL_GEAR_RATIO, Constants.WHEEL_RADIUS, Constants.MAX_WHEEL_RPM, 1.4e-4, 1e-6, 0),
    FRONT_LEFT(WheelPosition.FRONT_LEFT, 3, false, Constants.WHEEL_GEAR_RATIO, Constants.WHEEL_RADIUS, Constants.MAX_WHEEL_RPM, 1.4e-4, 1e-6, 0),

    SHOOTER(null, 5, false, Constants.LAUNCHER_GEAR_RATIO, Constants.LAUNCHER_WHEEL_RADIUS, Constants.MAX_SHOOTER_RPM);;

    private final double gearRatio;
    private final double radius;
    private final double maxRPM;
    private final int motorId;
    private final boolean inverse;
    private final double P, I, D;
    private final WheelPosition wheelPosition;

    DrivePosition(WheelPosition position, int id, boolean inverse, double gearRatio, double radius, double maxRPM) {
        this(position, id, inverse, gearRatio, radius, maxRPM, 1.4E-4, 1e-6, 0);
    }

    DrivePosition(WheelPosition position, int id, boolean inverse, double gearRatio, double radius, double maxRPM, double p, double i, double d) {
        wheelPosition = position;
        motorId = id;
        this.inverse = inverse;
        this.gearRatio = gearRatio;
        this.radius = radius;
        this.maxRPM = maxRPM;
        this.P = p;
        this.I = i;
        this.D = d;
    }

    public static DrivePosition forWheelPosition(WheelPosition pos) {
        for (DrivePosition drivePos : values()) if (drivePos.wheelPosition == pos) return drivePos;

        throw new IllegalArgumentException("No DrivePosition found for wheel position: " + pos);
    }

    @Override public double gearRatio() {
        return gearRatio;
    }

    @Override public double radius() {
        return radius;
    }

    @Override public double maxRpm() {
        return maxRPM;
    }

    @Override public int motorId() {
        return motorId;
    }

    @Override public boolean inverse() {
        return inverse;
    }

    @Override public boolean shouldSetPid() {
        return this == SHOOTER;
    }

    @Override public double P() {
        return P;
    }

    @Override public double I() {
        return I;
    }

    @Override public double D() {
        return D;
    }

    @Override public WheelPosition getWheelPosition() {
        return wheelPosition;
    }

    /**
     * This class only exists as we need to wrap these in a class so the constructors above can reference it.
     * Normally these should just be declared on the class using them.
     */
    private static final class Constants {
        private static final double WHEEL_GEAR_RATIO = 10.714, WHEEL_RADIUS = 4;
        private static final double LAUNCHER_GEAR_RATIO = 1, LAUNCHER_WHEEL_RADIUS = 2;
        private static final int MAX_SHOOTER_RPM = 5400, MAX_WHEEL_RPM = 4000;//4500 for MAX_WHEEL_RPM, 5400 for shooter
    }
}
