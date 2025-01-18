package org.usfirst.frc.team2077.math;

import org.usfirst.frc.team2077.common.Clock;

public class RateLimiter {
    private final double accelRate;
    private final double decelRate;

    private double prevVal = 0;
    private double prevTime;

    /*
    Dear Dustin,

    Upon reflection, I realised that I would function better
    if a new ratelimiter class was established that could handle
    different rates for accelerating and decelerating.

    Sincerely,
        Hank
    */

    public RateLimiter(double accelRate, double decelRate) {
        this.accelRate = accelRate;
        this.decelRate = decelRate;
        this.prevTime = Clock.getSeconds();
    }

    public RateLimiter(double rate) {
        this(rate, rate);
    }

    public double calculate(double input) {
        double currentTime = Clock.getSeconds();
        double deltaTime = currentTime - this.prevTime;

//        this.m_prevVal += MathUtil.clamp(input - this.m_prevVal, this.m_negativeRateLimit * elapsedTime, this.m_positiveRateLimit * elapsedTime);
//        this.prevVal += Math.abs(input) - Math.abs(preVal)
        double rate = Math.abs(input) > Math.abs(prevVal)? accelRate : decelRate;
        double dist = input - prevVal;

        if(Math.abs(dist) < rate * deltaTime){
            this.prevVal = input;
        }else{
            this.prevVal += rate * deltaTime * Math.signum(dist);
        }

        this.prevTime = currentTime;
        return this.prevVal;
    }

    public double lastValue() {
        return this.prevVal;
    }

    public void reset(double value) {
        this.prevVal = value;
        this.prevTime = Clock.getSeconds();
    }
}
