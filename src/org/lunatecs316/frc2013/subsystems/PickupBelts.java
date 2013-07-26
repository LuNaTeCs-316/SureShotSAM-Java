package org.lunatecs316.frc2013.subsystems;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.lunatecs316.frc2013.RobotMap;
import org.lunatecs316.frc2013.commands.DisablePickup;

/**
 *
 * @author domenicpaul
 */
public class PickupBelts extends Subsystem {
    private final Victor beltMotor = new Victor(RobotMap.PICKUP_BELT_MOTOR); 
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
    }
    
    /**
     * Enable the conveyer belts
     */
    public void enable() {
        beltMotor.set(1.0);
    }
    
    /**
     * Disable the conveyer belts
     */
    public void disable() {
        beltMotor.set(0.0);
    }
    
    /**
     * Run the conveyer belts in reverse
     */
    public void reverse() {
        beltMotor.set(-1.0);
    }
}