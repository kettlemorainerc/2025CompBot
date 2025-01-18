package org.usfirst.frc.team2077.math;

import static java.lang.Math.atan2;
import static java.lang.Math.pow;

public class SwerveWheelTarget {
    private double magnitude, angle;

    public SwerveWheelTarget(double magnitude, double angle) {
        this.magnitude = magnitude;
        this.angle = angle;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public double getAngle() {
        return angle;
    }

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    @Override public String toString() {
        return String.format("[mag=%s][ang=%s]", magnitude, angle);
    }

    public static SwerveWheelTarget fromCardinal(double forward, double strafe){
        double mag = Math.hypot(forward, strafe);
        double ang = Math.atan2(forward, strafe);

        if(Double.isNaN(mag)) mag = 0;
        if(Double.isNaN(ang)) ang = 0;
        else if(ang < 0) {
            ang += 2 * Math.PI;
        }

        return new SwerveWheelTarget(mag, ang);
    }
}
