package org.lunatecs316.frc2013.lib;

/**
 * "Latches" boolean variables. Used with buttons to detect first press.
 * @author domenicpaul
 */
public class Latch {
    private boolean lastValue;

    /**
     * Check for a rising edge (transition from false to true).
     * @param value the value to check
     * @return true if value is true and was not true the previous call
     */
    public boolean risingEdge(boolean value) {
        boolean retVal = false;

        if (value && !lastValue)
            retVal = true;

        lastValue = value;
        return retVal;
    }

    public boolean fallingEdge(boolean value) {
        boolean retVal = false;

        if (!value && lastValue)
            retVal = true;

        lastValue = value;
        return retVal;
    }
}
