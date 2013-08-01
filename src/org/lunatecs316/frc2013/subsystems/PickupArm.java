package org.lunatecs316.frc2013.subsystems;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.lunatecs316.frc2013.Constants;
import org.lunatecs316.frc2013.RobotMap;
import org.lunatecs316.frc2013.lib.Potentiometer;

/**
 * PickupArm Subsystem
 * @author domenicpaul
 */
public final class PickupArm extends Subsystem {

    private final Jaguar angleMotor = new Jaguar(RobotMap.kPickupAngleMotor);
    private final Potentiometer anglePot = new Potentiometer(RobotMap.kPickupAngle);
    
    /**
     * Set the default subsystem command
     */
    protected void initDefaultCommand() {}
    
    /**
     * Raise the pickup arm
     */
    public void raise() {
        angleMotor.set(Constants.kPickupArmSpeed.getValue());
    }
    
    /**
     * Lower the pickup arm
     */
    public void lower() {
        angleMotor.set(-Constants.kPickupArmSpeed.getValue());
    }
    
    /**
     * Stop movement of the pickup arm
     */
    public void stop() {
        angleMotor.set(0.0);
    }
    
    /**
     * Get the angle of the pickup arm
     * @return the angle of the pickup arm
     */
    public double getAngle() {
        return anglePot.getAverageVoltage();
    }
}
