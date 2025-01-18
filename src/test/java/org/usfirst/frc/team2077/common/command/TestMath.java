package org.usfirst.frc.team2077.common.command;

import org.usfirst.frc.team2077.common.WheelPosition;
import org.usfirst.frc.team2077.common.*;
import org.usfirst.frc.team2077.drivetrain.ChassisKinematics;

import java.util.*;

import static org.usfirst.frc.team2077.common.VelocityDirection.*;

public class TestMath implements ChassisKinematics<Double, Double> {
    public static final WheelPosition FORWARD_WHEEL = WheelPosition.FRONT_LEFT;
    public static final WheelPosition STRAFE_WHEEL = WheelPosition.FRONT_RIGHT;
    public static final WheelPosition ROTATION_WHEEL = WheelPosition.BACK_LEFT;
    public static final WheelPosition UNUSED_WHEEL = WheelPosition.BACK_RIGHT;

    @Override public Map<WheelPosition, Double> targetsForVelocity(Map<VelocityDirection, Double> targetVelocities) {
        double forward = targetVelocities.get(FORWARD);
        double strafe = targetVelocities.get(STRAFE);
        double rotation = targetVelocities.get(ROTATION);

        return Map.of(
              FORWARD_WHEEL, forward,
              STRAFE_WHEEL, strafe,
              ROTATION_WHEEL, rotation,
              UNUSED_WHEEL, 0.
        );
    }

    @Override public Map<VelocityDirection, Double> velocityForTargets(Map<WheelPosition, Double> targets) {
        return Map.of(
              FORWARD, targets.get(FORWARD_WHEEL),
              STRAFE, targets.get(STRAFE_WHEEL),
              ROTATION, targets.get(ROTATION_WHEEL)
        );
    }
}
