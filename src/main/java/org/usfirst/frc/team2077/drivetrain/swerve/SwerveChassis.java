package org.usfirst.frc.team2077.drivetrain.swerve;

import com.studica.frc.AHRS;
import com.studica.frc.AHRS.NavXComType;

import edu.wpi.first.math.util.Units;
import org.usfirst.frc.team2077.common.WheelPosition;
import org.usfirst.frc.team2077.common.drivetrain.AbstractChassis;
import org.usfirst.frc.team2077.common.drivetrain.DriveModuleIF;
import org.usfirst.frc.team2077.common.math.Vector;
import org.usfirst.frc.team2077.math.SwerveMath;
import org.usfirst.frc.team2077.math.SwerveWheelTarget;

import java.util.Comparator;
import java.util.EnumMap;
import java.util.Map;

public class SwerveChassis extends AbstractChassis<SwerveModule> {

    private final SwerveMath math;
    private final AHRS gyro = new AHRS(NavXComType.kMXP_UART); //Not sure about this

    private final double maxDrivePercent = 0.65;
    private final double minDriveInputPercent = 0.001;

    private double heading = 0.0;
    private boolean fieldOriented = true;

    private static EnumMap<WheelPosition, SwerveModule> buildDriveTrain() {
        EnumMap<WheelPosition, SwerveModule> map = new EnumMap<>(WheelPosition.class);

        for(SwerveConstants.MotorPosition p : SwerveConstants.MotorPosition.values()){
            map.put(WheelPosition.valueOf(p.name()), new SwerveModule(p));
        }

        return map;
    }

    public SwerveChassis() {
        super(buildDriveTrain());

//        gyro = new ADIS16470_IMU();

        maximumSpeed = this.driveModules.values().stream().map(DriveModuleIF::getMaximumSpeed).min(Comparator.naturalOrder()).orElseThrow();

        //Dear David,
        //  I forgot what I was complaining about
        //Sincerely, Hank

        double circumference = Math.PI * Math.hypot(SwerveConstants.wheelBaseLength, SwerveConstants.wheelBaseWidth);
        double secondsPerRevolution = circumference / this.maximumSpeed;
        double radiansPerSecond = 2.0 * Math.PI / secondsPerRevolution;

        maximumRotation = radiansPerSecond;

        minimumSpeed = maximumSpeed * 0.1;

        math = new SwerveMath(SwerveConstants.wheelBaseLength, SwerveConstants.wheelBaseWidth, maximumSpeed, maximumRotation);
    }

    @Override protected void measureVelocity(){
        velocityMeasured = math.velocitiesForTargets(driveModules);
    }

    @Override protected void updateDriveModules() {

        Vector target = velocitySet.copy();
        if(fieldOriented) { //TODO, add
            double gyroOffset = Math.toRadians(gyro.getAngle());
            target.rotate(gyroOffset);
        }

        Map<WheelPosition, SwerveWheelTarget> wheelTargets = math.getWheelTargets(velocitySet, maximumSpeed, maximumRotation);

        double maxDriveSpeed = maximumSpeed * maxDrivePercent;

        wheelTargets.forEach((key, value) -> {
            SwerveModule module = this.driveModules.get(key);

            double drivePercent = Math.abs(value.getMagnitude());
            if(drivePercent > 1) drivePercent = 1;
            if(drivePercent < minDriveInputPercent) drivePercent = 0.0;

            double driveVelocity = drivePercent * maxDriveSpeed;

//            if(driveVelocity > 0.01) velocity = Math.max(velocity, minimumSpeed);

            module.setVelocity(driveVelocity);
            module.setAngle(value.getAngle());
        });
    }

    public void resetGyro(){
        gyro.reset();
    }

    public void setFieldOriented(boolean v){
        fieldOriented = v;
    }

    public static double getAngleDifference(double to, double from) {
        double diff = from - to;
        if(Math.abs(diff) > Math.PI) diff -= 2 * Math.PI * Math.signum(diff);
        return diff;
    }

    public static double getAngleDifferenceDegrees(double to, double from) {
        double diff = from - to;
        if(Math.abs(diff) > 180) diff -= 360 * Math.signum(diff);
        return diff;
    }
}