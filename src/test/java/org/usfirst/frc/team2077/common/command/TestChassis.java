package org.usfirst.frc.team2077.common.command;

import org.usfirst.frc.team2077.common.WheelPosition;
import org.usfirst.frc.team2077.common.*;
import org.usfirst.frc.team2077.common.drivetrain.AbstractChassis;
import org.usfirst.frc.team2077.common.math.*;

import java.util.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static org.usfirst.frc.team2077.common.VelocityDirection.*;

public class TestChassis extends AbstractChassis<TestDriveModule> {
    public static final double MAX_WHEEL_VELOCITY = 10; // in/s/s
    public static final double WHEELBASE = 10; // in
    public static final double TRACKWIDTH = 10; // in
    public static final double WHEEL_RADIUS = 2; // in

    private static final Map<WheelPosition, TestDriveModule> DRIVE_MODULES = new EnumMap<>(
          WheelPosition.class);

    static {
        for(var pos : WheelPosition.values()) {
            DRIVE_MODULES.put(pos, new TestDriveModule(pos, MAX_WHEEL_VELOCITY));
        }
    }

    private final TestMath math;

    private static EnumMap<WheelPosition, TestDriveModule> makeModules(
          HardwareRequirements<TestDriveModule, ?> hardware
    ) {
        var map = new EnumMap<WheelPosition, TestDriveModule>(WheelPosition.class);
        for(var pos : WheelPosition.values()) {
            map.put(pos, hardware.getWheel(pos));
        }

        return map;
    }

    public TestChassis(
          HardwareRequirements<TestDriveModule, TestChassis> hardware,
          double wheelbase,
          double trackWidth,
          double wheelRadius,
          Supplier<Double> getSeconds
    ) {
        super(makeModules(hardware), wheelbase, trackWidth, wheelRadius, getSeconds);
        math = new TestMath();

        maximumSpeed = minimumSpeed = 10;
        maximumRotation = minimumRotation = 50;


        this.accelerationLimits = new AccelerationLimits(100, 100, this);
    }

    public TestChassis(
          HardwareRequirements<TestDriveModule, TestChassis> chassis,
          Supplier<Double> getSeconds
    ) {
        this(chassis, WHEELBASE, TRACKWIDTH, WHEEL_RADIUS, getSeconds);
    }

    public Position getActualPosition() {
        return this.positionSet;
    }

    public Position getActualPositionMeasured() {
        return this.positionMeasured;
    }

    @Override public void setVelocity(
          double north,
          double east,
          double clockwise,
          AccelerationLimits accelerationLimits
    ) {
        setVelocity(north, east, accelerationLimits);
        setRotation(clockwise, accelerationLimits);
    }

    @Override public void setVelocity(
          double north,
          double east,
          AccelerationLimits accelerationLimits
    ) {
        velocitySet.put(FORWARD, north);
        velocitySet.put(STRAFE, east);

        this.accelerationLimits.set(FORWARD, accelerationLimits.get(FORWARD));
        this.accelerationLimits.set(STRAFE, accelerationLimits.get(STRAFE));
    }

    @Override public void setRotation(
          double clockwise,
          AccelerationLimits accelerationLimits
    ) {
        velocitySet.put(ROTATION, clockwise);
        this.accelerationLimits.set(ROTATION, accelerationLimits.get(ROTATION));
    }

    @Override protected void updatePosition() {
        velocitySet = getVelocityCalculated();

        var wheelVelocities = this.driveModules.entrySet()
              .stream()
              .map((entry) -> new SimpleEntry<>(entry.getKey(),
                    entry.getValue()
                          .getVelocity()
              ))
              .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        velocityMeasured = math.velocityForTargets(wheelVelocities);

        positionSet.moveRelative(velocitySet, timeSinceLastUpdate);
        positionMeasured.moveRelative(velocityMeasured, timeSinceLastUpdate);
    }

    private void setWheelVelocity(
          WheelPosition position,
          Double velocity
    ) {
        this.driveModules.get(position)
              .setVelocity(velocity);
    }

    @Override protected void updateDriveModules() {
        math.targetsForVelocity(velocitySet)
              .forEach(this::setWheelVelocity);
    }
}
