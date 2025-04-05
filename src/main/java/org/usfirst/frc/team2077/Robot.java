package org.usfirst.frc.team2077;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj2.command.*;
import org.usfirst.frc.team2077.command.autonomous.AutoMove;
import org.usfirst.frc.team2077.command.autonomous.AutoRotate;
import org.usfirst.frc.team2077.command.autonomous.TimeBasedAutoMove;

public class Robot extends TimedRobot {
    private RobotHardware hardware;
    private DriveStation driveStation;
    public int autoTick;

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
    autoTick = 0;
    }
    /**
     * When you click the "Teleoperated" option in driver station
     */
    @Override public void teleopInit() {}

    /**
     * Called roughly every 1/50th second while the robot is "enabled" in "Autonomous" mode
     */
    @Override public void autonomousPeriodic() {
        autoTick++;
        if (autoTick == 1) {
            SequentialCommandGroup auto = new SequentialCommandGroup();
            // int autonomousNumber = autoDash.get().intValue();

            double d, a; //Java is very funky, and apparently I can't redeclare a variable in a seperate cases because it is the same scope.
            switch (0) {
                case 0:
                    auto.addCommands(
                        new TimeBasedAutoMove(1, 0, 4)
//                            new AutoRotate()
                    );
                    break;
            }

            auto.schedule();
        }
    }

    /**
     * Called roughly every 1/50th second while the robot is "enabled" in "Teleoperated" mode
     */
    @Override public void teleopPeriodic() {}

    @Override public void teleopExit() {}

    @Override public void disabledInit() {}

    @Override public void disabledPeriodic() {}
}