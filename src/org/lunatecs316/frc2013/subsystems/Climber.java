package org.lunatecs316.frc2013.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.lunatecs316.frc2013.RobotMap;

/**
 * Climbing subsystem
 * @author domenicpaul
 */
public class Climber extends Subsystem {
    
    private final Solenoid solenoid = new Solenoid(RobotMap.CLIMBING_SOLENOID);
    
    /**
     * Set the default command for the subsystem
     */
    protected void initDefaultCommand() {  
    }
    
    /**
     * Raise the climbing hooks
     */
    public void raiseHooks() {
        solenoid.set(true);
    }

    /**
     * Lower the climbing hooks
     */
    public void lowerHooks() {
        solenoid.set(false);
    }
}
