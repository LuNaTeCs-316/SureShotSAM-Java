package org.lunatecs316.frc2013.subsystems;

import edu.wpi.first.wpilibj.Solenoid;

import org.lunatecs316.frc2013.RobotMap;

/**
 * Climbing subsystem
 * @author domenicpaul
 */
public class Climber {
    
    private static final Solenoid solenoid = new Solenoid(RobotMap.CLIMBING_SOLENOID);
    
    /* Private Constructor to avoid instantiation */
    private Climber() {}
    
    /**
     * Initialize the subsystem
     */
    public static void init() {
        
    }
    
    /**
     * Activate the climbing system
     * @param value true => extended, false => retracted
     */
    public static void climb(boolean value) {
        solenoid.set(value);
    }
}
