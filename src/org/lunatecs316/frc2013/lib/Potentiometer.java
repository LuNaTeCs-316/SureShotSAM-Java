package org.lunatecs316.frc2013.lib;

import edu.wpi.first.wpilibj.AnalogChannel;

/**
 * Wrapper of analog channel for use with Potentiometers
 * @author domenicpaul
 */
public class Potentiometer extends AnalogChannel {
    public Potentiometer(int module, int channel) {
        super(module, channel);
    }
    
    public Potentiometer(int channel) {
        this(1, channel);
    }
    
    /**
     * For use with the PIDContoller.
     * @return - the average voltage
     */
    public double pidGet() {
        return getAverageVoltage();
    }
}
