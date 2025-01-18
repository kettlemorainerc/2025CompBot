package org.usfirst.frc.team2077.drivetrain;

import org.usfirst.frc.team2077.common.VelocityDirection;
import org.usfirst.frc.team2077.common.WheelPosition;

import java.util.*;

import static java.lang.Math.sqrt;

/**
 * Kinematics that assumes wheels are located at 4 corners of a rectangle relative to the center of the bot.
 * <ul>
 * <li>FRONT_LEFT offset would be (-trackWidth / 2, wheelbase / 2)</li>
 * <li>BACK_RIGHT offset would be (trackWidth / 2, -wheelbase / 2)</li>
 * <li>etc.</li>
 * </ul>
 */
public abstract class RectangularChassisKinematics<InverseResult, ForwardArguments> implements ChassisKinematics<InverseResult, ForwardArguments> {
    /** The distance between front and rear wheels (length). Center to center */
    private double wheelbase;
    /** The distance between left and right wheels (width). Center to center */
    private double trackWidth;
    private double radius;

    public RectangularChassisKinematics(double wheelbase, double trackWidth) {
        setWheelbase(wheelbase);
        setTrackWidth(trackWidth);
    }

    public void setWheelbase(double wheelbase) {
        this.wheelbase = wheelbase;
        updateRadius();
    }

    public double getWheelbase() {
        return wheelbase;
    }

    public void setTrackWidth(double trackWidth) {
        this.trackWidth = trackWidth;
        updateRadius();
    }

    public double getTrackWidth() {
        return trackWidth;
    }

    protected void updateRadius() {
        radius = sqrt(wheelbase * wheelbase + trackWidth * trackWidth);
    }

    /** Inverse */
    public abstract Map<WheelPosition, InverseResult> targetsForVelocity(Map<VelocityDirection, Double> targetVelocities);

    /** Forward */
    public abstract Map<VelocityDirection, Double> velocityForTargets(Map<WheelPosition, ForwardArguments> targets);
}
