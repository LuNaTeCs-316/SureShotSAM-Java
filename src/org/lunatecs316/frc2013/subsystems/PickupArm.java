package org.lunatecs316.frc2013.subsystems;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.lunatecs316.frc2013.RobotMap;
import org.lunatecs316.frc2013.lib.Potentiometer;

/**
 * PickupArm Subsystem
 * @author domenicpaul
 */
public final class PickupArm extends Subsystem {

    private final Jaguar angleMotor = new Jaguar(RobotMap.PICKUP_ANGLE_MOTOR);
    private final Potentiometer anglePot = new Potentiometer(RobotMap.PICKUP_ANGLE_POT);
    
    /**
     * Set the default subsystem command
     */
    protected void initDefaultCommand() {}
    
    /**
     * Raise the pickup arm
     */
    public void raise() {
        angleMotor.set(1.0);
    }
    
    /**
     * Lower the pickup arm
     */
    public void lower() {
        angleMotor.set(-1.0);
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
