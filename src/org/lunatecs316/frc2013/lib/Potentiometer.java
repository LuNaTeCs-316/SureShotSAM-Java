package org.lunatecs316.frc2013.lib;

import edu.wpi.first.wpilibj.AnalogChannel;

/**
 * Wrapper of analog channel for use with Potentiometers
 * @author domenicpaul
 */
public class Potentiometer extends AnalogChannel {
    
    /**
     * Create a new Potentiometer object
     * @param module the cRIO module
     * @param channel the analog Channel
     */
    public Potentiometer(int module, int channel) {
        super(module, channel);
    }
    
    /**
     * Create a new Potentiometer object on module 1
     * @param channel the analog channel
     */
    public Potentiometer(int channel) {
        this(1, channel);
    }
    
    /**
     * For use with the PIDContoller.
     * @return the average voltage
     */
    public double pidGet() {
        return getAverageVoltage();
    }
    
    /**
     * Return the value of the potentiometer
     * @return the average voltage
     */
    public double getOutput() {
        return getAverageVoltage();
    }
}
