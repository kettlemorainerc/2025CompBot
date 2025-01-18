/*----------------------------------------------------------------------------*/
/* Copyright (c) 2020 FRC Team 2077. All Rights Reserved.                     */
/* Open Source Software - may be modified and shared by FRC teams.            */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team2077.common.drivetrain;

import org.usfirst.frc.team2077.common.Clock;
import org.usfirst.frc.team2077.common.VelocityDirection;
import org.usfirst.frc.team2077.common.WheelPosition;
import org.usfirst.frc.team2077.common.math.*;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.usfirst.frc.team2077.common.math.Vector;

import java.util.*;

import static org.usfirst.frc.team2077.common.VelocityDirection.*;

public abstract class AbstractChassis<DriveModule> extends SubsystemBase implements DriveChassisIF {

    public final Map<WheelPosition, DriveModule> driveModules;

    protected double maximumSpeed;
    protected double maximumRotation;
    protected double minimumSpeed;
    protected double minimumRotation;

    protected double lastTime;
    protected double deltaTime;

    protected final Position position = new Position();

    protected Vector velocitySet = new Vector();
    protected Vector velocityMeasured = new Vector();

    public AbstractChassis(Map<WheelPosition, DriveModule> driveModules) {
        this.driveModules = driveModules;

        lastTime = Clock.getSeconds();
    }

    @Override
    public void periodic() {
        double now = Clock.getSeconds();
        deltaTime = now - lastTime;
        lastTime = now;

        measureVelocity();
        updatePosition();
        updateDriveModules();
    }

    public void updatePosition(){
        Vector velocity = getVelocityMeasured();
        velocity.scale(deltaTime);
        position.move(velocity);

//        for(VelocityDirection axis : VelocityDirection.values()){
//            position.move(velocityMeasured.get(axis) * deltaTime, axis);
//        }
    }

    public Map<WheelPosition, DriveModule> getDriveModules(){
        return driveModules;
    }

    /**
     * Update drive module setpoints.
     * Called by {@link #periodic()}.
     */
    protected abstract void updateDriveModules();

    /**
     *  Updates velocity measured
     *  Called by {@Link #periodic()}.
     */
    protected abstract void measureVelocity();

    @Override
    public Vector getVelocitySet() {
        return velocitySet.copy();
    }

    @Override
    public Vector getVelocityMeasured() {
        return velocityMeasured.copy();
    }

    @Override
    public Vector getMaximumVelocity() {
        return new Vector(
            maximumSpeed,
            maximumSpeed,
            maximumRotation
        );
    }

    @Override
    public Vector getMinimumVelocity() {
        return new Vector(
            minimumSpeed,
            minimumSpeed,
            minimumRotation
        );
    }

    @Override
    public Position getPosition() {
        return position.copy();
    }

    @Override
    public void setVelocity(double forward, double strafe, double rotation){
        setVelocity(forward, strafe);
        setRotation(rotation);
    }

    @Override
    public void setVelocity(double forward, double strafe){
        velocitySet.put(FORWARD, forward);
        velocitySet.put(STRAFE, strafe);
    }

    @Override
    public void setRotation(double rotation){
        velocitySet.put(ROTATION, rotation);
    }

    @Override
    public final void setVelocityPercent(double forward, double strafe, double rotation) {
        setVelocityPercent(forward, strafe);
        setRotationPercent(rotation);
    }

    @Override
    public final void setVelocityPercent(double forward, double strafe) {
        setVelocity(forward * maximumSpeed, strafe * maximumSpeed);
    }

    @Override
    public final void setRotationPercent(double rotation) {
        setRotation(rotation * maximumRotation);
    }

    @Override
    public void halt() {
        setVelocity(0, 0, 0);
    }

    public DriveModule getWheel(WheelPosition position){
        return driveModules.get(position);
    }
}
