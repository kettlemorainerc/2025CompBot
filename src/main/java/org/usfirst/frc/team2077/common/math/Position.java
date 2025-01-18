/*----------------------------------------------------------------------------*/
/* Copyright (c) 2020 FRC Team 2077. All Rights Reserved.                     */
/* Open Source Software - may be modified and shared by FRC teams.            */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team2077.common.math;

import org.usfirst.frc.team2077.common.VelocityDirection;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.usfirst.frc.team2077.common.VelocityDirection.*;

public class Position extends EnumMap<VelocityDirection, Double> {

    public Position() {
        this(new double[]{0, 0, 0});
    }

    public Position(Position from) {
        this(new double[]{from.get(FORWARD), from.get(STRAFE), from.get(ROTATION)});
    }

    public Position(double[] position) {
        super(VelocityDirection.class);
        put(FORWARD,    position[FORWARD.ordinal()]);
        put(STRAFE,     position[STRAFE.ordinal()]);
        put(ROTATION,   position[ROTATION.ordinal()]);
    }

    public void move(double forward, double strafe, double rotation) {
        compute(FORWARD,  (k, v) -> v + forward);
        compute(STRAFE,   (k, v) -> v + strafe);
        compute(ROTATION, (k, v) -> v + rotation);
    }

    public void move(Vector delta){
        compute(FORWARD,  (k, v) -> v + delta.get(k));
        compute(STRAFE,   (k, v) -> v + delta.get(k));
        compute(ROTATION, (k, v) -> v + delta.get(k));
    }

    public void move(double delta, VelocityDirection axis){
        compute(axis, (k, v) -> v + delta);
    }

    public Map<VelocityDirection, Double> distanceTo(Position origin) {
        Map<VelocityDirection, Double> distanceTo = this.clone();

        distanceTo.compute(FORWARD,  (k, v) -> v - origin.getOrDefault(FORWARD, 0d));
        distanceTo.compute(STRAFE,   (k, v) -> v - origin.getOrDefault(STRAFE,  0d));
        distanceTo.compute(ROTATION, (k, v) -> v - origin.getOrDefault(ROTATION,0d));

        return distanceTo;
    }

    public void set(double forward, double strafe, double rotation) {
        put(FORWARD,    forward);
        put(STRAFE,     strafe);
        put(ROTATION,   rotation);
    }

    public double[] get() {
        return new double[]{get(FORWARD), get(STRAFE), get(ROTATION)};
    }

    public Position copy() {
        return new Position(this);
    }

    @Override
    public String toString() {
        return Stream.of(VelocityDirection.values()).map(
                k -> String.format("%s: %.2f", k, this.get(k))
        ).collect(Collectors.joining()); //TODO: test and make sure this works

//        return "N:" + Math.round(get(FORWARD) * 10.) / 10. + "in E:" + Math.round(get(STRAFE) * 10.) / 10. + "in A:" + Math.round(get(ROTATION) * 10.) / 10. + "deg";
    }
}
