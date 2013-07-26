package org.lunatecs316.frc2013.subsystems;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.lunatecs316.frc2013.RobotMap;
import org.lunatecs316.frc2013.commands.StopPickupArm;
import org.lunatecs316.frc2013.lib.Potentiometer;

/**
 * PickupArm Subsystem
 * @author domenicpaul
 */
public class PickupArm extends Subsystem {

    /**
     * Set the default subsystem command
     */
    protected void initDefaultCommand() {
    }
   
    /* Motors */
    private final Jaguar angleMotor = new Jaguar(RobotMap.PICKUP_ANGLE_MOTOR);
    
    /* Sensors */
    private final Potentiometer anglePot = new Potentiometer(RobotMap.PICKUP_ANGLE_POT);
    
    /* PID Controller */
    private final PIDController angleController = new PIDController(-5.0, -0.1,
            0.0, anglePot, angleMotor);
    
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
}
