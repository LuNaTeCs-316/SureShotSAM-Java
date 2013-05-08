package org.lunatecs316.frc2013.subsystems;

import edu.wpi.first.wpilibj.Solenoid;

import org.lunatecs316.frc2013.RobotMap;

/**
 * Climbing subsystem
 * @author domenicpaul
 */
public class Climber {
    // Climbing
    private static final Solenoid solenoid = new Solenoid(RobotMap.CLIMBING_SOLENOID);
        
    private Climber() {}
    
    public static void init() {
        
    }
    
    public static void climb(boolean value) {
        solenoid.set(value);
    }
}
