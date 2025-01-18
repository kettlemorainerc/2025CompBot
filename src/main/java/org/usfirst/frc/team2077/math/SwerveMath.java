package org.usfirst.frc.team2077.math;

import org.usfirst.frc.team2077.common.WheelPosition;
import org.usfirst.frc.team2077.common.control.DriveStick;
import org.usfirst.frc.team2077.common.math.Matrix;
import org.usfirst.frc.team2077.common.math.Vector;
import org.usfirst.frc.team2077.drivetrain.swerve.SwerveModuleIF;

import java.util.EnumMap;
import java.util.Map;

import static java.lang.Math.*;
import static org.usfirst.frc.team2077.common.VelocityDirection.*;
import static org.usfirst.frc.team2077.common.WheelPosition.*;

/**
 * Handle calculating the necessary magnitude and angle for a set of swerve wheels.
 * We may want to find a better equation or come up with our own we like better.
 * <p>
 *  Inverse kinematics based on pdf found <a href="https://www.chiefdelphi.com/uploads/default/original/3X/e/f/ef10db45f7d65f6d4da874cd26db294c7ad469bb.pdf">here</a>.
 * </p>
 * <p>
 *   Forward kinematics from <a href="https://ietresearch.onlinelibrary.wiley.com/doi/10.1049/joe.2014.0241">this paper</a>
 *   <p>
 *     Worth noting is that I'm unsure if the forward kinematics equation used actually has a hard requirement of
 *     rectangularly positioned wheels.
 *   </p>
 * </p>
 *
 * <dl>
 *     <dt>Y</dt>
 *     <dd>Equates to the value of the {@link DriveStick}'s north value. May need to be inverted</dd>
 *     <dt>X</dt>
 *     <dd>Equates to the value of the {@link DriveStick}'s east value. Shouldn't need to be inverted</dd>
 *     <dt>Z</dt>
 *     <dd>Equations the value of the {@link DriveStick}'s rotation value.</dd>
 *     <dt>L - (wheelbase)</dt>
 *     <dt>Length between the center of a front wheel and the center of the back wheel on the same side</dt>
 *     <dt>W - (trackWidth)</dt>
 *     <dt>Width between the left and right wheels in the front/back</dt>
 * </dl>
 */
public class SwerveMath {
    private static final EnumMap<WheelPosition, Multiplier> WHEEL_MULTIPLIERS = new EnumMap<>(WheelPosition.class);
    static {
        WHEEL_MULTIPLIERS.put(FRONT_LEFT, new Multiplier(-1, 1));
        WHEEL_MULTIPLIERS.put(BACK_LEFT, new Multiplier(-1, -1));

        WHEEL_MULTIPLIERS.put(FRONT_RIGHT, new Multiplier(1, 1));
        WHEEL_MULTIPLIERS.put(BACK_RIGHT, new Multiplier(1, -1));
    }

    private final double halfTrackwidth, halfWheelbase, wDenom;

    private double maxSpeed, maxRotation;
    private double length, width, diagonal;

    public SwerveMath(double length, double width, double maxSpeed, double maxRotation) {
        this.maxSpeed = maxSpeed;
        this.maxRotation = maxRotation;

        this.length = length;
        this.width = width;

        this.halfWheelbase = length / 2;
        this.halfTrackwidth = width / 2;
        this.wDenom = pow(length, 2) + pow(width, 2);

        this.diagonal = Math.hypot(length, width);
    }

    /*
     * David...
     * why
     * what was the point of the setWheelBase method?
     * updateRadius?
     * And they're all public too
     * Why would the robot dimensions ever need to be changed after init?
     * I mean, if hardware is ever ambitious enough to make a dynamic robot
     * frame, then this code might be cool i guess
     */
//    public void setWheelbase(double wheelbase) {
//        this.wheelbase = wheelbase;
//        this.halfWheelbase = wheelbase / 2;
//        updateRadius();
//    }

    public Map<WheelPosition, SwerveWheelTarget> getWheelTargets(double forward, double strafe, double rotation) {
        EnumMap<WheelPosition, SwerveWheelTarget> wheelTargets = new EnumMap<>(WheelPosition.class);

        double back  = strafe - rotation * length / diagonal;
        double front = strafe + rotation * length / diagonal;
        double right = forward - rotation * width / diagonal;
        double left  = forward + rotation * width / diagonal;

        wheelTargets.put(FRONT_LEFT,    SwerveWheelTarget.fromCardinal(front, left));
        wheelTargets.put(FRONT_RIGHT,   SwerveWheelTarget.fromCardinal(front, right));
        wheelTargets.put(BACK_LEFT,     SwerveWheelTarget.fromCardinal(back,  left));
        wheelTargets.put(BACK_RIGHT,    SwerveWheelTarget.fromCardinal(back,  right));

        double max = wheelTargets.values().stream().mapToDouble(SwerveWheelTarget::getMagnitude).max().orElse(0d);
        if(max > 1) wheelTargets.values().forEach(val -> val.setMagnitude(val.getMagnitude() / max));
//
        return wheelTargets;
    }

    public Map<WheelPosition, SwerveWheelTarget> getWheelTargets(
            Vector target, double maxSpeed, double maxRotation
    ) {
       return getWheelTargets(
           target.get(FORWARD) / maxSpeed,
           target.get(STRAFE) / maxSpeed,
           target.get(ROTATION) / maxRotation
       );
    }

    /**
     * Handles solving for <strong>W<sub>i</sub></strong>.<br>
     * In regards to<br>
     * <code>
     *     W<sub>i</sub> = (-y<sup>r</sup><sub>wi</sub>cos(&delta;<sub>i</sub>) + x<sup>r</sup><sub>wi</sub>sin(&delta;<sub>i</sub>)) / 4((x<sup>r</sup><sub>wi</sub>)<sup>2</sup> + (y<sup>r</sup><sub>wi</sub>)<sup>2</sup>)
     * </code>
     * <br><br>
     * Given <br>
     * "y<sup>r</sup><sub>wi</sub>" is the Y offset of wheel I<br>
     * "x<sup>r</sup><sub>wi</sub>" is the X offset of wheel I<br>
     * "&delta;<sub>i</sub>" is the Angle wheel I<br>
     *
     * @param p The position we're calculating for
     * @param cos cos(&delta;<sub>i</sub>)
     * @param sin sin(&delta;<sub>i</sub>)
     * @return W<sub>i</sub>
     */
    private double getWFor(WheelPosition p, double cos, double sin) {
        var mults = WHEEL_MULTIPLIERS.get(p);

        return (
                ((mults.y * -halfWheelbase * cos) + (mults.x * halfTrackwidth * sin)) /
                wDenom
        );
    }

    public Vector velocitiesForTargets( //TODO: potentially rename to something regarding forward kinematics
        Map<WheelPosition, ? extends SwerveModuleIF> targets
    ) {
        //I don't understand any of this so I'm not gonna touch it
        //However, I think this needs to be looked into more as the rotation does not seem to be accuracy
        SwerveModuleIF fl = targets.get(FRONT_LEFT);
        SwerveModuleIF bl = targets.get(BACK_LEFT);
        SwerveModuleIF br = targets.get(BACK_RIGHT);
        SwerveModuleIF fr = targets.get(FRONT_RIGHT);

        double flA = fl.getAngle(),
                blA = bl.getAngle(),
                brA = br.getAngle(),
                frA = fr.getAngle();

        double flC = cos(flA), flS = sin(flA);
        double blC = cos(blA), blS = sin(blA);
        double brC = cos(brA), brS = sin(brA);
        double frC = cos(frA), frS = sin(frA);

        Matrix pseudoPDotX = new Matrix(new double[][] {
                {flC / 4, blC / 4, brC / 4, frC / 4},
                {flS / 4, blS / 4, brS / 4, frS / 4},
                {getWFor(FRONT_LEFT, flC, flS), getWFor(BACK_LEFT, blC, blS), getWFor(BACK_RIGHT, brC, brS), getWFor(FRONT_RIGHT, frC, frS)},
        });

        double flV = fl.getVelocityMeasured(),
                blV = bl.getVelocityMeasured(),
                brV = br.getVelocityMeasured(),
                frV = fr.getVelocityMeasured();

        Matrix velocities = new Matrix(new double[][] {
                {flV},
                {blV},
                {brV},
                {frV},
        });

        Matrix result = pseudoPDotX.multiply(velocities);

        return new Vector(
            result.get(0, 0),
            result.get(0, 1),
            result.get(0, 2)
        );
    }

    private static class Multiplier {
        int x, y;
        Multiplier(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
