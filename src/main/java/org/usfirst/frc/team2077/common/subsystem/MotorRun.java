package org.usfirst.frc.team2077.common.subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import edu.wpi.first.wpilibj2.command.Subsystem;
import org.usfirst.frc.team2077.RobotHardware;
import org.usfirst.frc.team2077.util.SmartDash.SmartDashRobotPreference;

public class MotorRun implements Subsystem {
    

    
    private final SparkMax max;
    private final SparkClosedLoopController maxPid;
    // private final Launcher launcher;
    // private final Launcher.Target target;

    public MotorRun(){
        // launcher = RobotHardware.getInstance().launcher;
        // this.target = target;
        max = new SparkMax(14, MotorType.kBrushless);
        maxPid = max.getClosedLoopController();
        SparkMaxConfig config = new SparkMaxConfig();
        config
            .inverted(false)
            .idleMode(IdleMode.kBrake);
        config.encoder
            .positionConversionFactor(1000)
            .velocityConversionFactor(1000);
        config.closedLoop
            .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
            .pid(1.0, 0.0, 0.0);
    
        max.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    public void startForward(){
        max.set(0.05);
    }

    public void startBackward(){
        max.set(-0.05);
    }

    public void stopMotor(){
        max.set(0);
    }


}
