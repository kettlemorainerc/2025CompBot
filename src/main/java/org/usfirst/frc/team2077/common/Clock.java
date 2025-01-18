/*----------------------------------------------------------------------------*/
/* Copyright (c) 2020 FRC Team 2077. All Rights Reserved.                     */
/* Open Source Software - may be modified and shared by FRC teams.            */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team2077.common;

import edu.wpi.first.wpilibj.*;

import java.util.concurrent.*;

/**
 * Timing clock. Wraps {@link Timer#getFPGATimestamp} if available,
 * or {@link System#nanoTime} otherwise, to support testing code
 * without RoboRio hardware.
 */
public final class Clock {
    private static final double NS_PER_SECOND = TimeUnit.SECONDS.toNanos(1);
    private static final long nanoTimeBase = System.nanoTime();

    private Clock() {} // Use static API only.

    /**
     * @return Seconds since an indeterminate but consistent point in time.
     */
    public static double getSeconds() {
        if (DriverStation.isDSAttached()) return Timer.getFPGATimestamp();

        return (System.nanoTime() - nanoTimeBase) / NS_PER_SECOND;
    }
}
