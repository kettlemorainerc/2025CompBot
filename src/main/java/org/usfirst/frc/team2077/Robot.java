package org.usfirst.frc.team2077;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj2.command.*;

public class Robot extends TimedRobot {
    private RobotHardware hardware;
    private DriveStation driveStation;

    @Override public void robotInit() {
        hardware = new RobotHardware();
        driveStation = new DriveStation(hardware);

        // Make sure you only configure port forwarding once in your robot code.
        // Do not place these function calls in any periodic functions
        for (int port = 5800; port <= 5809; port++) {
            edu.wpi.first.net.PortForwarder.add(port, "limelight.local", port);
        }
    }

    @Override public void robotPeriodic() {
        CommandScheduler.getInstance().run();
    }

    /**
     * When you click the "Autonomous" option in driver station
     */
    @Override public void autonomousInit() {

    }

    /**
     * When you click the "Teleoperated" option in driver station
     */
    @Override public void teleopInit() {

    }

    /**
     * Called roughly every 1/50th second while the robot is "enabled" in "Autonomous" mode
     */
    @Override public void autonomousPeriodic() {}

    /**
     * Called roughly every 1/50th second while the robot is "enabled" in "Teleoperated" mode
     */
    @Override public void teleopPeriodic() {}

    @Override public void teleopExit() {}

    @Override public void disabledInit() {}

    @Override public void disabledPeriodic() {}
}