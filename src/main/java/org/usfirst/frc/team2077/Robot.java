package org.usfirst.frc.team2077;

import org.usfirst.frc.team2077.common.command.autonomous.AutoLaunch;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj2.command.*;

public class Robot extends TimedRobot {
    private RobotHardware hardware;
    private DriveStation driveStation;
    private int autoTick;

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
    @Override public void teleopInit() {

    }

    /**
     * Called roughly every 1/50th second while the robot is "enabled" in "Autonomous" mode
     */
    @Override public void autonomousPeriodic() {
        autoTick++;
        if(autoTick == 1){
            SequentialCommandGroup auto = new SequentialCommandGroup();
            // int autonomousNumber = autoDash.get().intValue();

            double d, a; //Java is very funky, and apparently I can't redeclare a variable in a seperate cases because it is the same scope.
            switch(0) {
                case 0:
                    auto.addCommands(
                        new AutoLaunch()
                    );
                    break;
                // case 1:
                //     //Moves robot straight forward
                //     auto.addCommands(
                //         new StraightenWheels(0.0),
                //         new AutoSwerveMoveOdometryBased(3, 0)
                //     );
                //     break;
                // case 5:
                //     //Shoots in Speaker when aligned with the left face of the Speaker, then drives towards the center of the field
                //     d = 5;
                //     a = -2.443;
                //     auto.addCommands(
                //         new AutoLaunch(Launcher.Target.SPEAKER),
                //         new StraightenWheels(a),
                //         new AutoSwerveMoveOdometryBased(
                //             Math.cos(a) * d, Math.sin(a) * d
                //         )
                //     );
                //     break;
                // case 2:
                //     //Shoots in Speaker when aligned with the middle face of the Speaker, then drives backwards
                //     auto.addCommands(
                //         new AutoLaunch(Launcher.Target.SPEAKER),
                //         new StraightenWheels(0.0),
                //         new AutoSwerveMoveOdometryBased(-3, 0)
                //     );
                //     break;
                // case 3:
                //     //Shoots in Speaker when aligned with the right face of the Speaker, then drives towards the center of the field.
                //     d = 5;
                //     a = 2.443;
                //     auto.addCommands(
                //         new AutoLaunch(Launcher.Target.SPEAKER),
                //         new StraightenWheels(a),
                //         new AutoSwerveMoveOdometryBased(
                //             Math.cos(a) * d, Math.sin(a) * d
                //         )
                //     );
                //     break;
                // case 8:
                //     auto.addCommands(
                //         new AutoSwerveMoveOdometryBased(Math.PI)
                //     );
                //     break;
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