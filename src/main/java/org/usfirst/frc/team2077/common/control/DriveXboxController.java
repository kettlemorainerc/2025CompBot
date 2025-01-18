package org.usfirst.frc.team2077.common.control;

import edu.wpi.first.wpilibj.*;

/**
 * An xbox controller that can be used for driving
 */
public class DriveXboxController extends XboxController implements DriveStick {

    protected double driveDeadBand, driveExponent;
    protected double rotationDeadBand, rotationExponent;

    /**
     * Construct an instance of a controller.
     *
     * @param port The port index on the Driver Station that the controller is plugged into.
     */
    public DriveXboxController(int port) {
        super(port);
    }


    public DriveXboxController setDriveSensitivity(double deadBand, double exponent) {
        this.driveDeadBand = deadBand;
        this.driveExponent = exponent;
        return this;
    }

    public DriveXboxController setRotationSensitivity(double deadBand, double exponent) {
        this.rotationDeadBand = deadBand;
        this.rotationExponent = exponent;
        return this;
    }

    public DriveXboxController setSensitivity(double deadBand, double exponent) {
        return setDriveSensitivity(deadBand, exponent).setRotationSensitivity(deadBand, exponent);
    }


    @Override
    public double getNorth() {

        int pov = super.getPOV();
        if(pov == DPad.UNPRESSED) {
            return DriveStick.adjustInputSensitivity(super.getLeftY(), driveDeadBand, driveExponent);
        }

        if(pov == DPad.NORTH || pov == DPad.NORTH_EAST || pov == DPad.NORTH_WEST){
            return -DPad.SPEED;
        }

        if(pov == DPad.SOUTH || pov == DPad.SOUTH_EAST || pov == DPad.SOUTH_WEST){
            return DPad.SPEED;
        }

        return 0;
    }

    @Override
    public double getEast() {

        int pov = super.getPOV();
        if(pov == DPad.UNPRESSED) {
            return DriveStick.adjustInputSensitivity(super.getLeftX(), driveDeadBand, driveExponent);
        }

        if(pov == DPad.EAST || pov == DPad.NORTH_EAST || pov == DPad.SOUTH_EAST){
            return DPad.SPEED;
        }

        if(pov == DPad.WEST || pov == DPad.NORTH_WEST || pov == DPad.SOUTH_WEST){
            return -DPad.SPEED;
        }

        return 0;

    }

    @Override
    public double getRotation() {
        return DriveStick.adjustInputSensitivity(super.getRightX(), driveDeadBand, driveExponent);
    }


    
    private static class DPad {
        static final int NORTH = 0;
        static final int NORTH_EAST = 45;
        static final int EAST = 90;
        static final int SOUTH_EAST = 135;
        static final int SOUTH = 180;
        static final int SOUTH_WEST = 225;
        static final int WEST = 270;
        static final int NORTH_WEST = 315;

        static final int UNPRESSED = -1;

        static final float SPEED = 1;
    }
}
