package org.usfirst.frc.team2077.drivetrain;

import org.usfirst.frc.team2077.common.VelocityDirection;
import org.usfirst.frc.team2077.common.WheelPosition;

import java.util.*;

public interface ChassisKinematics<InverseResult, ForwardArgs> {
    Map<WheelPosition, InverseResult> targetsForVelocity(Map<VelocityDirection, Double> velocityTarget);

    Map<VelocityDirection, Double> velocityForTargets(Map<WheelPosition, ForwardArgs> targets);
}
