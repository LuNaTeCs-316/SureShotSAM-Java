package org.lunatecs316.frc2013.subsystems;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import org.lunatecs316.frc2013.RobotMap;
import org.lunatecs316.frc2013.lib.Potentiometer;
import org.lunatecs316.frc2013.lib.Tachometer;

/**
 * Shooter Subsystem
 * @author domenicpaul
 */
public class Shooter {
    
    // <editor-fold defaultstate="collapsed" desc="Position Class">
    /**
     * Represents different positions the shooter can move to
     */
    public static class Position {
        
        /**
         * Position for shooting at the 3-point target
         */
        public static final Position Top = new Position(3.45);
        
        /**
         * Position for shooting at the 2-point target
         */
        public static final Position Mid = new Position(3.31);
        
        /**
         * Position for loading
         */
        public static final Position Load = new Position(2.0);
        
        private double val;     // the setpoint value
        
        // Private to prevent creation of other positions
        private Position(double val) {
            this.val = val;
        }
        
        /**
         * Get the numeric value for the PIDController
         * @return - the double value of the setpoint
         */
        public double getVal() {
            return val;
        }
    }
    //</editor-fold>
    
    /* Shooter Motor */
    private static final Victor motor = new Victor(RobotMap.SHOOTER_MOTOR);
    private static final Tachometer speedTach = new Tachometer(RobotMap.SHOOTER_SPEED_TACH);
    private static final PIDController speedController = new PIDController(-0.005,
            0.000, 0.0, speedTach, motor);
    
    /* Shooter Angle */
    private static final Jaguar angleMotor = new Jaguar(RobotMap.SHOOTER_ANGLE_MOTOR);
    private static final Potentiometer anglePot = new Potentiometer(RobotMap.SHOOTER_ANGLE_POT);
    private static final PIDController angleController = new PIDController(22.5,
            0.0, 0.0, anglePot, angleMotor);
    
    /* Shooter piston */
    private static final Solenoid solenoid = new Solenoid(RobotMap.SHOOTER_SOLENOID);
    
    /* Shooter indicator lights */
    private static boolean lightIsOn = false;
    private static int offCounter = 0;
    private static Solenoid redIndicator1 = new Solenoid(RobotMap.RED_INDICATOR_1);
    private static Solenoid redIndicator2 = new Solenoid(RobotMap.RED_INDICATOR_2);
    private static Solenoid blueIndicator = new Solenoid(RobotMap.BLUE_INDICATOR);
    
    // Private to prevent creation of an instance
    private Shooter() {
    }
    
    /**
     * Initialize the shooter subsystem
     */
    public static void init() {
        
        // Configure the PIDController for the Shooter
        speedController.setSetpoint(3975);
        speedController.setAbsoluteTolerance(350);
        
        // Start the Tachometer
        speedTach.start();
        
        // Configure LiveWindow
        LiveWindow.addActuator("Shooter", "Motor", motor);
        LiveWindow.addActuator("Shooter", "SpeedController", speedController);
        LiveWindow.addActuator("Shooter", "AngleMotor", angleMotor);
        LiveWindow.addActuator("Shooter", "AngleController", angleController);
        LiveWindow.addActuator("Shooter", "Solenoid", solenoid);
        LiveWindow.addSensor("Shooter", "AnglePot", anglePot);
        LiveWindow.addSensor("Shooter", "SpeedTach", speedTach);
    }
    
    /**
     * Move the shooter to the specified position
     * @param pos 
     */
    public static void moveToPosition(Position pos) {
        angleController.setSetpoint(pos.getVal());
        angleController.enable();
    }
    
    /**
     * Manually control the angle
     * @param val the movement speed value
     */
    public static void move(double val) {
        angleController.disable();
        angleMotor.set(val);
    }
    
    /**
     * Enable the shooter wheel
     */
    public static void enable() {
        speedController.enable();
    }
    
    /**
     * Disable the shooter wheel
     */
    public static void disable() {
        speedController.disable();
    }
    
    /**
     * Check to see if the shooter wheel is up to speed
     * @return true if the shooter is at speed, else false
     */
    public static boolean atSpeed() {
        return speedController.onTarget();
    }
    
    /**
     * Fire the shot
     * @param value whether to shoot or not
     */
    public static void fire(boolean value) {
        solenoid.set(value);
    }
    
    /**
     * Control the feedback lights for the shooter
     */
    public static void indications() {
        
        // Blue indicator light show if we are at the proper angle
        blueIndicator.set((anglePot.pidGet() >= Position.Top.getVal()));
        
        if (speedTach.getRPM() >= 3500) {
            // Red indicator lights are solid when at speed,...
            redIndicator1.set(true);
            redIndicator2.set(true);
        } else if (speedTach.getRPM() <= 500) {
            // ...off when the speed is too low,..
            redIndicator1.set(false);
            redIndicator2.set(false);
        } else {
            // ...and blink when the speed is in between	
            if (lightIsOn) { // light was turned on last pass, turn it off now
                redIndicator1.set(false);
                redIndicator2.set(false);
                lightIsOn = false;
                offCounter = 0;
            } else { // light is off, check to see if it is time to turn it on
                offCounter++;
                double temp = speedTach.getRPM();
                temp = temp - 150;
                double offTime = 71 - temp;
                if (offCounter >= offTime) {
                    redIndicator1.set(true);
                    redIndicator2.set(true);
                    lightIsOn = true;
                }
            }
	}
    }
}
