package org.usfirst.frc.team2077.command.autonomous;

import com.studica.frc.AHRS;
import edu.wpi.first.wpilibj2.command.Command;
import org.usfirst.frc.team2077.common.drivetrain.AbstractChassis;

public class AutoRotate extends Command {
     private AbstractChassis chassis;

     private double targetAngle;
     private double gyroAngle;
     private double initialAngle;
     private AHRS gyro = new AHRS(AHRS.NavXComType.kMXP_SPI);

     public AutoRotate(double angleRelative){
          targetAngle = angleRelative;
     }
     @Override
     public void initialize(){
          initialAngle = gyro.getAngle();
     }
     @Override
     public void execute(){
          gyroAngle = initialAngle - gyroAngle;

          if (isFinished()){
               this.end(false);
               chassis.setVelocity(0, 0, 0.3*Math.signum(targetAngle-initialAngle));
          }
     }
     @Override
     public void end(boolean interrupted){
          chassis.halt();
     }
     @Override
     public boolean isFinished(){
          return (((targetAngle + 0.05) >= gyroAngle) || (gyroAngle >= targetAngle - 0.05));

     }
}
