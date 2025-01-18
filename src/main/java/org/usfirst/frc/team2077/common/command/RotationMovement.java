package org.usfirst.frc.team2077.common.command;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.*;
import org.usfirst.frc.team2077.RobotHardware;
import org.usfirst.frc.team2077.common.*;
import org.usfirst.frc.team2077.common.control.DriveStick;
import org.usfirst.frc.team2077.common.drivetrain.*;

public class RotationMovement extends Command {
    protected final DriveStick stick;
    protected final DriveChassisIF chassis;

    public RotationMovement(DriveStick stick) {
        addRequirements(RobotHardware.getInstance().getHeading());

        this.stick = stick;
        this.chassis = RobotHardware.getInstance().getChassis();
    }

    @Override public void execute() {
        if(DriverStation.isTeleop()) chassis.setRotationPercent(stick.getRotation());
    }

    @Override
    public void end(boolean interrupted) {

    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
