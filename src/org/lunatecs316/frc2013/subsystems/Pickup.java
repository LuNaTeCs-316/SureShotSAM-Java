package org.lunatecs316.frc2013.subsystems;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import org.lunatecs316.frc2013.RobotMap;
import org.lunatecs316.frc2013.lib.Potentiometer;

/**
 * Pickup Subsystem
 * @author domenicpaul
 */
public class Pickup {
    public static final int kBeltForwards = 0;
    public static final int kBeltReverse = 1;
    public static final int kBeltOff = 2;
        
    private static final Jaguar angleMotor = new Jaguar(RobotMap.PICKUP_ANGLE_MOTOR);
    private static final Victor beltMotor = new Victor(RobotMap.PICKUP_BELT_MOTOR);
    
    private static final Potentiometer anglePot = new Potentiometer(RobotMap.PICKUP_ANGLE_POT);
    
    private static final PIDController angleController = new PIDController(-5.0, -0.1,
            0.0, anglePot, angleMotor);
    
    // Private to prevent instantiation
    private Pickup() {}
    
    public static void init() {
        LiveWindow.addActuator("Pickup", "BeltMotor", beltMotor);
        LiveWindow.addActuator("Pickup", "AngleMotor", angleMotor);
        LiveWindow.addSensor("Pickup", "AnglePot", anglePot);
        LiveWindow.addActuator("Pickup", "AngleController", angleController);
    }
    
    /**
     * Set the state of the pickup belts
     * @param state - forwards(0), reverse(1), off(2)
     */
    public static void setBeltState(int state) {
        switch (state) {
            case kBeltForwards:
                beltMotor.set(1.0);
                break;
            case kBeltReverse:
                beltMotor.set(-1.0);
                break;
            default:
                System.err.println("Error: invalid belt state");
            case kBeltOff:
                beltMotor.set(0.0);
                break;
        }
    }
    
    /**
     * Raise the pickup
     */
    public static void raise() {
        angleMotor.set(1.0);
    }
    
    /**
     * Lower the pickup
     */
    public static void lower() {
        angleMotor.set(-1.0);
    }
    
    /**
     * Stop movement of the pickup
     */
    public static void stop() {
        angleMotor.set(0.0);
    }
    
}
