package org.usfirst.frc.team2077.util;

public interface PIDTuneable {
    double getP();
    double getI();
    double getD();

    void setP(double p);
    void setI(double i);
    void setD(double d);

    void tuningSet(double setpoint);
    void tuningStop();
    void zeroIntegral();

    double tuningGetError();
    boolean tuningReady();

    String getName();
}