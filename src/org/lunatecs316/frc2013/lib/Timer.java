package org.lunatecs316.frc2013.lib;

/**
 * Utility class for dealing with timing in Iterative loops
 * @author domenicpaul
 */
public class Timer {
    private double startTime;

    public Timer() {
        startTime = System.currentTimeMillis();
    }

    public double getCurrentMs() {
        return System.currentTimeMillis() - startTime;
    }

    public void reset() {
        startTime = System.currentTimeMillis();
    }
}
