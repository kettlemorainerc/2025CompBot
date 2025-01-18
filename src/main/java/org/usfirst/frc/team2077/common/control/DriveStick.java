package org.usfirst.frc.team2077.common.control;

/**
 * An interface dictating required inputs for it to support being used to "drive" utilizing our movement related commands.
 * {@link org.usfirst.frc.team2077.common.command.CardinalMovement CardinalMovement} for North/East movement and
 * {@link org.usfirst.frc.team2077.common.command.RotationMovement RotationMovement} for Rotation related movement.
 */
public interface DriveStick {
    static double adjustInputSensitivity(double input, double deadBand, double exponent) {
        return Math.pow(Math.max(0, Math.abs(input) - deadBand) / (1 - deadBand), exponent) * Math.signum(input);
    }

    public double getNorth();

    public double getEast();

    public double getRotation();
}
