/*----------------------------------------------------------------------------*/
/* Copyright (c) 2020 FRC Team 2077. All Rights Reserved.                     */
/* Open Source Software - may be modified and shared by FRC teams.            */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team2077.common.drivetrain;

import org.usfirst.frc.team2077.common.WheelPosition;

/**
 * Wrapper for a single wheel/motor/controller/encoder assembly. The wrapper
 * should construct the underlying hardware-specific objects and perform unit
 * and sign conversion as required.
 */
public interface DriveModuleIF {

    /**
     * Maximum speed at which this wheel may be run.
     * {@link #setVelocity} should limit input values to this value (positive or negative).
     *
     * @return Maximum speed in inches/second.
     */
    double getMaximumSpeed();

    /**
     * Set velocity for this wheel.
     *
     * @param velocity In inches/second.
     *                 Positive values are robot-forward ("north"), negative backward/south.
     */
    void setVelocity(double velocity);

    WheelPosition getWheelPosition();

    /**
     * Current velocity for this wheel.
     * This should be a direct measurement from an encoder if available,
     * otherwise the set point as passed to {@link #setVelocity} or reported by
     * the motor controller.
     *
     * @return Velocity In inches/second.
     */
    double getVelocitySet();

    double getVelocityMeasured();
}