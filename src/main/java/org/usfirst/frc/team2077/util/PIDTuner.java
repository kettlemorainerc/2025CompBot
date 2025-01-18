package org.usfirst.frc.team2077.util;

import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import org.usfirst.frc.team2077.common.Clock;
import org.usfirst.frc.team2077.common.command.SelfDefinedCommand;

public class PIDTuner extends SelfDefinedCommand {

    private enum State{
        RUNNING, STOPPING
    }

    private final JoystickButton endButton;
    private final PIDTuneable module;
    private final double testDuration;
    private double setpoint;
    private double maxError;

    private State state;

    private double bestError = Double.MAX_VALUE;
    private double bestP, bestI, bestD;

    private double lastResetTime = 0.0;
    private double timeSinceLastUpdate = 0.0;
    private double error = 0.0;

    private int trial = 0;

    public PIDTuner(PIDTuneable module, double setpoint, double duration, JoystickButton endButton){

        this.endButton = endButton;

        this.module = module;
        this.setpoint = setpoint;
        this.testDuration = duration;

        state = State.STOPPING;

        maxError = setpoint * duration;
    }

    @Override
    public void initialize() {
        bestP = module.getP();
        bestI = module.getI();
        bestD = module.getD();

        state = State.STOPPING;
    }

    @Override
    public void execute(){
        double timeRunning = Clock.getSeconds() - lastResetTime;
        double dt = Clock.getSeconds() - timeSinceLastUpdate;
        timeSinceLastUpdate = Clock.getSeconds();


        switch (state){
            case RUNNING:
                module.tuningSet(setpoint);
                error += Math.abs(module.tuningGetError()) * dt;

                if(timeRunning > testDuration){
                    state = State.STOPPING;
                }
                break;

            case STOPPING:
                module.tuningStop();
//                System.out.println("stopping");

                if(module.tuningReady()){

//                    System.out.printf("===%s===\nP: %.15f\nI: %.15f\nD: %.15f\n", module.getName(), bestP, bestI, bestD);


                    if(trial > 0) {
                        randomWalk();
                    }

                    reset();
                }
                break;
        }
    }

    public void reset(){
        state = State.RUNNING;
        module.zeroIntegral();
        error = 0.0;
        lastResetTime = Clock.getSeconds();

        trial++;
    }

    private void randomWalk(){

        if(error < bestError){
            bestError = error;
            bestP = module.getP();
            bestI = module.getI();
            bestD = module.getD();
        }


        double v = 3 * error / maxError;
        if(v > 1) v = 1;

//        double v = 0.1;

        module.setP(vary(bestP, v));
        module.setI(vary(bestI, v));
        module.setD(vary(bestD, v));
    }

    @Override
    public boolean isFinished() {
        return endButton.getAsBoolean();
    }

    @Override
    public void end(boolean interrupted){
        module.setP(bestP);
        module.setI(bestI);
        module.setD(bestD);

        module.zeroIntegral();

        module.tuningStop();

        System.out.println("PID tuning finished");
        System.out.printf("===%s===\nP: %.5e\nI: %.5e\nD: %.5e\n", module.getName(), bestP, bestI, bestD);
    }

    public static double vary(double value, double variance){
        return value * (1 + variance * (Math.random() - 0.5));
    }

}
