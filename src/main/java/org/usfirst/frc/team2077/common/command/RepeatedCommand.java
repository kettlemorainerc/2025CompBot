/*----------------------------------------------------------------------------*/
/* Copyright (c) 2020 FRC Team 2077. All Rights Reserved.                     */
/* Open Source Software - may be modified and shared by FRC teams.            */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team2077.common.command;


import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * Commands that should continuously run when holding their related button should extend this implementation of
 * bindable command. They will continuously execute until their related button is released, or
 * {@link #isFinished() returns true}.
 * <br><br>
 * These are typically commands users will interact with as they (the driver, tech or otherwise) are the ones who want
 * to control how much said command should run.
 * <br><br>
 * For example, holding a Joystick trigger spinning (a) motor(s).
 */
public abstract class RepeatedCommand extends BindableCommand {
    @Override public void bind(JoystickButton button) {
        button.whileTrue(this);
    }

    /**
     * If your command has an end point, say holding the button performs the end-game automatically, this should be
     * overridden to return true upon the action(s) being completed.
     * <p>
     * Typically, it is okay to leave this as-is.
     *
     * @return false
     * @see #initialize() If this is overridden you most likely want to reset/gauge state in the initialize function
     */
    @Override public boolean isFinished() {
        return false;
    }

    /**
     * In most instances if you override {@link #isFinished()} you should also override this.
     * <br>
     * You should reset any state related to the executing actions to their desired start-point here.
     */
    @Override public void initialize() {
    }

    /**
     * In the {@link RepeatedCommand given example} you would be setting the motor(s) RPM or percent output
     * inside this method.
     */
    @Override public abstract void execute();

    /**
     * Often times you want to deactivate subsystems you are interacting with here.
     * In the {@link RepeatedCommand given example} about the trigger spinning a motor, you would set the related
     * motor(s) RPM or Percent out to 0 within this method.
     *
     * @param interrupted was this command interrupted
     */
    @Override public abstract void end(boolean interrupted);
}
