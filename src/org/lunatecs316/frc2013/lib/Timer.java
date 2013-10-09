package org.lunatecs316.frc2013.lib;

/**
 * Utility class for dealing with timing in Iterative loops
 * @author domenicpaul
 */
public class Timer {
    private double startTime;
    private double target;

    public Timer() {
        reset();
    }

    public double getCurrentMs() {
        return System.currentTimeMillis() - startTime;
    }

    public final void reset() {
        startTime = System.currentTimeMillis();
    }
    
    public void setTarget(double target) {
        reset();
        this.target = target;
    }
    
    public boolean isExpired() {
        return getCurrentMs() > target;
    }
}
