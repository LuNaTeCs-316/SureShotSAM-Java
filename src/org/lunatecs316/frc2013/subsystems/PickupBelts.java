package org.lunatecs316.frc2013.subsystems;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.lunatecs316.frc2013.Constants;
import org.lunatecs316.frc2013.RobotMap;

/**
 * Pickup conveyer belt subsystem
 * @author domenicpaul
 */
public final class PickupBelts extends Subsystem {
    private final Victor beltMotor = new Victor(RobotMap.kPickupBeltMotor); 
    
    /**
     * Set the subsystem's default command
     */
    public void initDefaultCommand() {}
    
    /**
     * Enable the conveyer belts
     */
    public void enable() {
        beltMotor.set(Constants.kPickupBeltSpeed.getValue());
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
        beltMotor.set(-Constants.kPickupBeltSpeed.getValue());
    }
}