package org.lunatecs316.frc2013.lib;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.PIDSource;

/**
 * Digital switch based tachometer class
 * @author domenicpaul
 */
public class Tachometer extends Counter implements PIDSource {
    
    /* Previously measured speed; used for filter */
    private double prevSpeed = 0;
    
    /**
     * Create a new tachometer object on module 1
     * @param channel the digital channel
     */
    public Tachometer(int channel) {
        this(1, channel);
    }
    
    /**
     * Create a new tachomter object
     * @param module the cRIO module
     * @param channel the digital channel
     */
    public Tachometer(int module, int channel) {
        super(module, channel);
    }
    
    /**
     * Calculate the RPM speed read by the counter
     * @return - the rotational speed in RPMs
     */
    public double getRPM() {
        double speed = (60.0 / getPeriod());
        if (speed > 5400)
            speed = prevSpeed;
        prevSpeed = speed;
        return speed;
    }
    
    /**
     * Used by PIDController
     * @return - the RPM speed of the sensor
     */
    public double pidGet() {
        return getRPM();
    }
    
    /**
     * Update data for NetworkTables/SmartDashboard
     */
    public void updateTable() {
        if (getTable() != null) {
            getTable().putNumber("Value", getRPM());
        }
    }
}
