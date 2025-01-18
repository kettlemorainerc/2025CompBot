package org.usfirst.frc.team2077.common.math;

import org.usfirst.frc.team2077.common.VelocityDirection;

import java.util.EnumMap;

import static org.usfirst.frc.team2077.common.VelocityDirection.*;

//This should suffice until we manage to build a robot with more than 3 degrees of freedom
public class Vector extends EnumMap<VelocityDirection, Double> {

    public Vector() {
        super(VelocityDirection.class);
        put(FORWARD,    0.0);
        put(STRAFE,     0.0);
        put(ROTATION,   0.0);
    }

    public Vector(double forward, double strafe, double rotation) {
        super(VelocityDirection.class);
        put(FORWARD,    forward);
        put(STRAFE,     strafe);
        put(ROTATION,   rotation);
    }

    public Vector(Vector to){
        super(VelocityDirection.class);
        set(to);
    }

    public void set(Vector to){
        put(FORWARD,    to.get(FORWARD));
        put(STRAFE,     to.get(STRAFE));
        put(ROTATION,   to.get(ROTATION));
    }

    /**
     * @return the magnitude or norm of the cardinal components of the vector
     * */
    public double getMagnitude() {
        return Math.hypot(get(FORWARD), get(STRAFE));
    }

    /**
     * @return the direction of the cardinal components of the vector
     * */
    public double getDirection(){
        return Math.atan2(get(FORWARD), get(STRAFE));
    }

    /**
     * @return the dot product between the two vectors this and param v
     * */
    public double dot(Vector v){
        return get(FORWARD) * v.get(FORWARD) + get(STRAFE) * v.get(STRAFE);
    }

    /**
     * @return returns true if all axes are zero
     * */
    public boolean isZero(){
        return get(FORWARD) == 0 && get(STRAFE) == 0 && get(ROTATION) == 0;
    }

    /**
     * @param a a vector to be added to the current vector
     * */
    public void add(Vector a){
        compute(FORWARD,    (k, v) -> v + a.get(k));
        compute(STRAFE,     (k, v) -> v + a.get(k));
        compute(ROTATION,   (k, v) -> v + a.get(k));
    }

    public Vector difference(Vector to){
        Vector diff = new Vector(to);
        diff.compute(FORWARD,   (k, v) -> v - get(k));
        diff.compute(STRAFE,    (k, v) -> v - get(k));
        diff.compute(ROTATION,  (k, v) -> v - get(k));
        return diff;
    }

    /**
     *  rotates the cardinal components of the vector by param angle radians
     * */
    public void rotate(double angle){
        double forward = get(STRAFE) * Math.sin(angle) + get(FORWARD) * Math.cos(angle);
        double strafe  = get(STRAFE) * Math.cos(angle) - get(FORWARD) * Math.sin(angle);

        put(FORWARD, forward);
        put(STRAFE, strafe);
    }

    /**
     * scales the cardinal components of the vector by param s
     * */
    public void scale(double s){
        compute(FORWARD, (k, v) -> v * s);
        compute(STRAFE,  (k, v) -> v * s);
    }

    /**
     * converts the vector into its normal vector
     * */
    public void normalize(){
        normalize(1);
    }

    public void normalize(double targetMagnitude){
        if(isZero()) return;

        scale(targetMagnitude / getMagnitude());
    }

    public void flip(VelocityDirection axis){
        compute(axis, (k, v) -> -v);
    }

    public void interpolate(Vector target, double rate){
        Vector diff = difference(target);

        if(diff.getMagnitude() <= rate){
            set(target);
            return;
        }

        diff.normalize(rate);
        add(diff);
    }

    /**
     * @return a deep copy of the vector
     * */
    public Vector copy(){
        return new Vector(this);
    }

}
