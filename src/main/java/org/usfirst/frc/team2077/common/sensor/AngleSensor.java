package org.usfirst.frc.team2077.common.sensor;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicReference;

import com.studica.frc.AHRS;

import org.usfirst.frc.team2077.common.HardwareRequirements;
public class AngleSensor {
	// these fields are private to the timer thread
	private final AHRS navX;
	private boolean initializing = true;
	private boolean rotating = false;
	private double stopAngle = 0;

	private AtomicReference<Double> angleREF = new AtomicReference<>(0.);

	public AngleSensor(HardwareRequirements hardware) {
		navX = hardware.getNavX();
		System.out.println("NavX:" + navX);
		System.out.println("Connected:" + navX.isConnected());
		System.out.println("Calibrating:" + navX.isCalibrating());

		(new Timer()).schedule(new TimerTask() {
			@Override
			public void run() {
				if (navX.isConnected() && !navX.isCalibrating()) {
					if (initializing) {
						navX.zeroYaw();
						initializing = false;
					}
					double angle = navX.getAngle();
					boolean rotating = navX.isRotating();
					//if ((n_++ % 100) == 0) System.out.println("Angle:" + angle + " R:" + rotating);
					if (!rotating) {
						if (AngleSensor.this.rotating) {
							stopAngle = angle;
						}
						navX.setAngleAdjustment(navX.getAngleAdjustment() - (angle - stopAngle));
						angle = stopAngle;
						AngleSensor.this.rotating = false;
					}
					AngleSensor.this.rotating = rotating;
					angleREF.set(angle);
				}
			}
		}, 200, 18);
	}

	public double getAngle() {
		return angleREF.get();
	}
}

