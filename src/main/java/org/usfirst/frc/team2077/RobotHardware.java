package org.usfirst.frc.team2077;

// import edu.wpi.first.cameraserver.CameraServer;
import org.usfirst.frc.team2077.common.HardwareRequirements;
import org.usfirst.frc.team2077.common.WheelPosition;
import org.usfirst.frc.team2077.drivetrain.swerve.SwerveChassis;
// import org.usfirst.frc.team2077.subsystem.*;
import org.usfirst.frc.team2077.drivetrain.swerve.SwerveModule;
import org.usfirst.frc.team2077.subsystem.Elevinator;
import org.usfirst.frc.team2077.subsystem.Extendinator;
import org.usfirst.frc.team2077.subsystem.Forkinator;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RobotHardware extends HardwareRequirements<SwerveModule, SwerveChassis> {

    private static RobotHardware instance = null;

    public static RobotHardware getInstance() {
        if(instance == null) instance = new RobotHardware();
        return instance;
    }

    // public final MotorRun motorRun;
    // public final Elevator elevator;
    public final Forkinator forkinator;
    // public final Extendinator extendinator;
    public final Elevinator elevinator;


    private final SwerveChassis chassis;
    public final Extendinator extendinator;

    SendableChooser<Boolean> m_Chooser = new SendableChooser<>();
    public final boolean manualSelected;
    
    public RobotHardware() {
        instance = this;
// TERRIBLE BUT QUICK CODE!!!

        m_Chooser.setDefaultOption("Calabrated with tool", false);
        m_Chooser.addOption("Straight from last match", true);
        SmartDashboard.putData(m_Chooser);
        manualSelected = m_Chooser.getSelected();




//        CameraServer.startAutomaticCapture(0);
//        CameraServer.startAutomaticCapture(1);

        // extendinator = new Extendinator(RobotHardware.getInstance().motorRun);
        // motorRun = new MotorRun();
        chassis  = new SwerveChassis();
        elevinator = new Elevinator();
        forkinator = new Forkinator();
        extendinator = new Extendinator();
    }

    @Override public SwerveChassis getChassis() {
        return chassis;
    }

    @Override public SwerveModule getWheel(WheelPosition position) {
        return chassis.getWheel(position);
    }
}
