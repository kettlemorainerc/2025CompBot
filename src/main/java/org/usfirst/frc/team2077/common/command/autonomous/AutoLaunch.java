package org.usfirst.frc.team2077.common.command.autonomous;


import edu.wpi.first.wpilibj2.command.Command;
import org.usfirst.frc.team2077.RobotHardware;
// import org.usfirst.frc.team2077.subsystem.Launcher;
// import org.usfirst.frc.team2077.subsystem.LauncherPivot;
import org.usfirst.frc.team2077.common.drivetrain.AbstractChassis;

public class AutoLaunch extends Command {

    private enum State{
        TO_ANGLE,
        SPIN_UP,
        END
    }

    // private Launcher launcher;
    // private LauncherPivot pivot;

    // private Launcher.Target target;
    private State state = null;

    private int launchTimer;
    private AbstractChassis abstractChassis;
    private int count;
    private int max;

    public AutoLaunch(){
        abstractChassis = RobotHardware.getInstance().getChassis();
        count = 0;
        max = 150;
        // launcher = RobotHardware.getInstance().launcher;
        // pivot = RobotHardware.getInstance().pivot;
        // this.target = target;
    }

    @Override
    public void initialize(){
        count = 0;
        /*
         * Callibrated wheels turned off + practice = yay
         * uncallibrated straight wheel + practice = nuh uhh
         * Callibrate wheels turned off + practice + no zero auto = \_(:/)_/
         */
        abstractChassis.setRotation(0);
        abstractChassis.setVelocityPercent(0.3, 0);
        // updateState(State.TO_ANGLE);
        // launchTimer = 20;
    }

    @Override
    public void execute(){
        count++;
        if(count > max){
            end(false);
        }
        // switch(state){
        //     case TO_ANGLE:
        //         if(pivot.atTarget()) updateState(State.SPIN_UP);
        //         return;
        //     case SPIN_UP:
        //         if(launcher.atSpeed()) {
        //             launchTimer--;
        //             launcher.feed();
        //             if (launchTimer < 0) updateState(State.END);
        //         }
        //         return;
        // }
    }

    public void updateState(State newState){
        // if(state == newState) return;

        // state = newState;

        // switch(state){
        //     case TO_ANGLE:
        //         pivot.setTarget(target);
        //         return;
        //     case SPIN_UP:
        //         launcher.run(target);
        //         return;
        //     case END:
        //         end(false);
        // }
    }

    @Override
    public void end(boolean interrupted) {
        abstractChassis.setVelocityPercent(0, 0);
        // pivot.stop();
        // launcher.stopLauncher();
        // launcher.stopFeed();
    }

    @Override
    public boolean isFinished(){
        return state == State.END;
    }
}
