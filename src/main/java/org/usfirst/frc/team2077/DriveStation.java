/*----------------------------------------------------------------------------*/
/* Copyright (c) 2020 FRC Team 2077. All Rights Reserved.                     */
/* Open Source Software - may be modified and shared by FRC teams.            */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team2077;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj2.command.button.*;
import org.usfirst.frc.team2077.command.ElevatorPositionControl;
import org.usfirst.frc.team2077.common.control.DriveStick;
import org.usfirst.frc.team2077.common.command.*;
import org.usfirst.frc.team2077.common.command.NewTest.Direction;
import org.usfirst.frc.team2077.common.control.DriveJoystick;
import org.usfirst.frc.team2077.subsystem.Elevator;
// import org.usfirst.frc.team2077.common.control.DriveXboxController;

/**
 * This class is intended to be the center point of defining actions that can be utilized during teleop segments of
 * control. This is where we should define what USB port joysticks should be registered as in `FRC Driver Station`'s usb
 * menu. As well as define what buttons on primary/technical driver's controllers should do what.
 * */
public class DriveStation {
    // Common controller port numbers
    // Joysticks that support rotation
    private static final int DRIVE_JOYSTICK_PORT = 0;
    private static final int DRIVE_XBOX_PORT = 1;
    private static final int FLYSKY_PORT = 2;

    // Joysticks that do not support rotation
    private static final int TECHNICAL_JOYSTICK_PORT = 4;
    private static final int NUMPAD_PORT = 5;

    private final DriveStick driveStick;
    private final Joystick technicalStick;

    public DriveStation(RobotHardware hardware) {
        /* Set the driver's control method this MUST be a {@link DriveStick} implementation */
//        driveStick = getFlysky();
       driveStick = getJoystick();
        // driveStick = getXbox();

        /* Set the technical control method. This can be any {@link Joystick} implementation */
//        technicalStick = getTechnicalJoystick();
        technicalStick = getNumpad();

        bind(hardware);
    }

    /**
     * This method binds any subsystem's default command and bind commands to a user's chosen
     * control method.
     */
    public void bind(RobotHardware hardware) {
        // Setup basic robot movement commands
        // hardware.getPosition().setDefaultCommand(new CardinalMovement(hardware, driveStick));
        // hardware.getHeading().setDefaultCommand(new RotationMovement(hardware, driveStick));

        // bindDriverControl(hardware, driveStick);
        bindTechnicalControl(hardware, technicalStick);
    }

    /** Bind primary driver's button commands here */
    private static void bindDriverControl(RobotHardware hardware, DriveStick primary) {
    }

    /** Bind technical driver button commands here */
    private void bindTechnicalControl(RobotHardware hardware, Joystick secondary) {
        //2 and 6 for up down, 5 and 7 for just left and right
        new ElevatorPositionControl(ElevatorPositionControl.MOVEDIRECTION.UP, new Elevator()).bind(new JoystickButton(secondary, 1));
        new ElevatorPositionControl(ElevatorPositionControl.MOVEDIRECTION.DOWN, new Elevator()).bind(new JoystickButton(secondary, 5));


        //next thingy or mabob goes here



        new NewTest(Direction.FORWARD).bind(new JoystickButton(secondary, 11));
        new NewTest(Direction.BACKWARD).bind(new JoystickButton(secondary, 12));
    }



    /** Normal (silver/brighter) joystick that supports rotation */
    private static DriveJoystick getJoystick() {
        return new DriveJoystick(DRIVE_JOYSTICK_PORT).setDriveSensitivity(.15, 5)
                                                     .setRotationSensitivity(.1, 1);
    }

    /** Flysky Drone Controller */
    private static DriveJoystick getFlysky() {
        return new DriveJoystick(FLYSKY_PORT, 4).setDriveSensitivity(.3, 1)
                                                .setRotationSensitivity(.05, 2.5);
    }

    // private static DriveXboxController getXbox(){
    //     return new DriveXboxController(DRIVE_XBOX_PORT).setDriveSensitivity(.15,1)
    //                                                    .setRotationSensitivity(.05,1.5);
    // }

    /** Currently the darker joystick that doesn't support rotation */
    private static Joystick getTechnicalJoystick() {
        return new Joystick(TECHNICAL_JOYSTICK_PORT);
    }

    private static Joystick getNumpad() {
        return new Joystick(NUMPAD_PORT);
    }

    /** bind command to the given joystick button */
    public static void useCommand(Joystick joystick, int button, BindableCommand command) {
        command.bind(new JoystickButton(joystick, button));
    }
}
