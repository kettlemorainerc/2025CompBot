/*----------------------------------------------------------------------------*/
/* Copyright (c) 2020 FRC Team 2077. All Rights Reserved.                     */
/* Open Source Software - may be modified and shared by FRC teams.            */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team2077.common.command;


import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * A command that, once executed, knows when to end itself. It will run until it's interrupted, or until
 * {@link #isFinished()} returns true.
 * <br><br>
 * For example, a command that, upon executing, rotated the robot 5&deg;.
 * <br><br>
 * These types of commands are typically not used as drivers like to have control over when and for how long a command
 * runs.
 */
public abstract class SelfDefinedCommand extends BindableCommand {

    @Override public void bind(JoystickButton button) {
        button.onTrue(this);
    }

    /**
     * What determines when this command is done?
     *
     * @return is the command done executing
     */
    public abstract boolean isFinished();

    /**
     * Reset any state relating to {@link #isFinished()} here.
     */
    public abstract void initialize();

    /**
     * Perform the commands related actions.
     */
    public abstract void execute();

    /**
     * Stop any subsystems/motors that this command interacts with here.
     *
     * @param interrupted was the command interrupted
     */
    public abstract void end(boolean interrupted);
}
