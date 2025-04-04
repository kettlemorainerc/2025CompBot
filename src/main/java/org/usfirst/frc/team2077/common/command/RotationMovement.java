package org.usfirst.frc.team2077.common.command;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.*;
import org.usfirst.frc.team2077.RobotHardware;
import org.usfirst.frc.team2077.common.*;
import org.usfirst.frc.team2077.common.control.DriveStick;
import org.usfirst.frc.team2077.common.drivetrain.*;

public class RotationMovement extends Command {
    protected final DriveStick stick;
    protected final DriveChassisIF chassis;

    private final XboxController halfSwitch;

    public RotationMovement(DriveStick stick, XboxController halfSwitch) {
        addRequirements(RobotHardware.getInstance().getHeading());

        this.stick = stick;
        this.chassis = RobotHardware.getInstance().getChassis();
        this.halfSwitch = halfSwitch;
    }

    @Override public void execute() {
        //Speed is halfed due to robot spinning out of control
        if(DriverStation.isTeleop()){ 
            if(halfSwitch.getRightTriggerAxis() > 0.8){
                chassis.setRotationPercent(stick.getRotation() / 4);
            }else{
                chassis.setRotationPercent(stick.getRotation() / 2);
            }
        }

    }

    @Override
    public void end(boolean interrupted) {

    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
