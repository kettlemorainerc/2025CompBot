package org.usfirst.frc.team2077.drivetrain.swerve;

import edu.wpi.first.math.util.Units;

public class SwerveConstants {

    public static final double accelerationRate = 6.0;
    public static final double deccelerationRate = 12.0;

    //Physical Constants
    public static final double wheelBaseLength = Units.inchesToMeters(29.5);
    public static final double wheelBaseWidth = Units.inchesToMeters(29.5);

    public static final double wheelDiameter = Units.inchesToMeters(2.9);
    public static final double wheelRadius = 0.5 * wheelDiameter;
    public static final double wheelCircumference = wheelDiameter * Math.PI;

    public static final double azimuthRatio = 203d / 9424d;
    public static final double driveGearReduction = (45d * 22d) / (15d * 13d/*This is the variable gear*/);

    // Motor Controller Constant
    public static final int guidingMotorCurrentLimit = 20; // amps
    public static final int drivingMotorCurrentLimit = 40; // amps

    // Module Specific Constants
    public enum MotorPosition{
        FRONT_LEFT (2, 1, 1.5, 1.05166e-01, 1.21232e-03, 4.33481e-01),
        BACK_RIGHT (6, 5, 0.5, 2.12917e-01, 1.60747e-03, 4.41048e-01),
        BACK_LEFT  (8, 7, 1,   1.93749e-01, 1.61658e-03, 5.56800e-01),
        FRONT_RIGHT(4, 3, 0,   1.49872e-01, 1.45941e-03, 3.03550e-01),
        ;

        public final int drivingCANid, guidingCANid;
        public final double angleOffset;
        public final double drivingP, drivingI, guidingP;
        MotorPosition(int drivingCANid, int guidingCANid, double angleOffset, double drivingP, double drivingI, double guidingP){
            this.drivingCANid = drivingCANid;
            this.guidingCANid = guidingCANid;
            this.angleOffset = angleOffset * Math.PI;
            this.drivingP = drivingP;
            this.drivingI = drivingI;
            this.guidingP = guidingP;
        }
    }
}
