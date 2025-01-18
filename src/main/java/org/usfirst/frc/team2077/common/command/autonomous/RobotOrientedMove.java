package org.usfirst.frc.team2077.common.command.autonomous;

import edu.wpi.first.wpilibj2.command.Command;
import org.usfirst.frc.team2077.RobotHardware;
import org.usfirst.frc.team2077.common.VelocityDirection;
import org.usfirst.frc.team2077.common.drivetrain.AbstractChassis;
import org.usfirst.frc.team2077.common.math.Position;

import java.util.Map;

public class RobotOrientedMove extends Command {

    private AbstractChassis chassis;

    private Position from, to;

    public RobotOrientedMove(double forward, double strafe){
        chassis = RobotHardware.getInstance().getChassis();

        from = chassis.getPosition();
        to = from.copy(); to.move(forward, strafe, 0);
    }
    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        Map<VelocityDirection, Double> distance = chassis.getPosition().distanceTo(to);
        double max = Math.max(
                Math.abs(distance.get(VelocityDirection.FORWARD)),
                Math.abs(distance.get(VelocityDirection.STRAFE)));


//        double max = distance.values().stream().max(Comparator.naturalOrder()).get();
//        distance


    }


    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void end(boolean interrupted) {
        chassis.halt();
    }
}
