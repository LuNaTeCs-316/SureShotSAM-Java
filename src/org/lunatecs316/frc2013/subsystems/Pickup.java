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
    
    /* Belt State "Enum" */
    public static class BeltState {
        
        /**
         * Belts running forwards
         */
        public static final BeltState Forwards = new BeltState("Forwards");
        
        /**
         * Belts Running in reverse
         */
        public static final BeltState Reverse = new BeltState("Reverse");
        
        /**
         * Belts off
         */
        public static final BeltState Off = new BeltState("Off");
        
        private String name;    // String representation of the state
        
        /* Private to prevent creation of new BeltStates */
        private BeltState(String name) {
            this.name = name;
        }
        
        /**
         * Convert the value to a string
         * @return name
         */
        public String toString() {
            return name;
        }
    }
    
    public static final int kBeltForwards = 0;
    public static final int kBeltReverse = 1;
    public static final int kBeltOff = 2;
    
    /* Motors */
    private static final Jaguar angleMotor = new Jaguar(RobotMap.PICKUP_ANGLE_MOTOR);
    private static final Victor beltMotor = new Victor(RobotMap.PICKUP_BELT_MOTOR);
    
    /* Sensors */
    private static final Potentiometer anglePot = new Potentiometer(RobotMap.PICKUP_ANGLE_POT);
    
    /* PID Controller */
    private static final PIDController angleController = new PIDController(-5.0, -0.1,
            0.0, anglePot, angleMotor);
    
    // Private to prevent instantiation
    private Pickup() {
    }
    
    /**
     * Initialize the pickup subsystem
     */
    public static void init() {
        LiveWindow.addActuator("Pickup", "BeltMotor", beltMotor);
        LiveWindow.addActuator("Pickup", "AngleMotor", angleMotor);
        LiveWindow.addSensor("Pickup", "AnglePot", anglePot);
        LiveWindow.addActuator("Pickup", "AngleController", angleController);
    }
    
    /**
     * Set the state of the pickup belts
     * @param state belt state (Forwards, Reverse, or Off)
     */
    public static void setBeltState(BeltState state) {
        if (state == BeltState.Forwards) {
            beltMotor.set(1.0);
        } else if (state == BeltState.Reverse) {
            beltMotor.set(-1.0);
        } else if (state == BeltState.Off) {
            beltMotor.set(0.0);
        } else {
            System.err.println("Error: invalid belt state");
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