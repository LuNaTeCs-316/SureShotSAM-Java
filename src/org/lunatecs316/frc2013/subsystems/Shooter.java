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
    
    /* Shooter positions */
    public static double kTopPosition = 3.16;
    public static double kMidPosition = 3.05;
    public static double kLoadPosition = 1.75;
    
    // <editor-fold desc="Subsystem Components">
    // Place Subsystem Components in this section
    
    /* Shooter Motor */
    private static final Victor motor = new Victor(RobotMap.SHOOTER_MOTOR);
    private static final Tachometer speedTach = new Tachometer(RobotMap.SHOOTER_SPEED_TACH);
    private static final PIDController speedController = new PIDController(-0.005,
            0.000, 0.0, speedTach, motor);
    
    /* Shooter Angle */
    private static final Jaguar angleMotor = new Jaguar(RobotMap.SHOOTER_ANGLE_MOTOR);
    private static final Potentiometer anglePot = new Potentiometer(RobotMap.SHOOTER_ANGLE_POT);
    private static final PIDController angleController = new PIDController(21.5,
            0.0, 0.0, anglePot, angleMotor);
    
    /* Shooter piston */
    private static final Solenoid solenoid = new Solenoid(RobotMap.SHOOTER_SOLENOID);
    
    /* Shooter indicator lights */
    private static Solenoid redIndicator1 = new Solenoid(RobotMap.RED_INDICATOR_1);
    private static Solenoid redIndicator2 = new Solenoid(RobotMap.RED_INDICATOR_2);
    private static Solenoid blueIndicator = new Solenoid(RobotMap.BLUE_INDICATOR);
    // </editor-fold>
    
    // <editor-fold desc="Subsystem Data">
    // Place Subsystem Data in this section
    
    private static boolean lightIsOn = false;
    private static int offCounter = 0;
    private static int onCounter = 0;
    // </editor-fold>
    
    // Private to prevent creation of an instance
    private Shooter() {
    }
    
    // <editor-fold desc="Subsystem Behavior">
    // Place Subsystem methods in this section
    
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

    public static double getAngle() {
        return anglePot.getAverageVoltage();
    }
    
    /**
     * Move the shooter to the specified position
     * @param pos 
     */
    public static void moveToPosition(double pos) {
        angleController.setSetpoint(pos);
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
        motor.set(0);
    }
    
    /**
     * Check to see if the shooter wheel is up to speed
     * @return true if the shooter is at speed, else false
     */
    public static boolean atSpeed() {
        return speedController.onTarget();
        //return speedTach.getRPM() > 3700;
    }
    
    /**
     * Fire the shot
     * @param value whether to shoot or not
     */
    public static void fire(boolean value) {
        solenoid.set(value);
    }
    
    public static void autoFire() {
        speedController.disable();
        
        motor.set(-1.0);
        solenoid.set(speedTach.getRPM() > 3600);
    }
    
    public static void setSpeed(double value) {
        speedController.disable();
        
        motor.set(value);
    }
    
    /**
     * Control the feedback lights for the shooter
     */
    public static void indications() {
        
        // Blue indicator light show if we are at the proper angle
        blueIndicator.set((anglePot.getOutput() >= kTopPosition - 0.1));
        
        if (speedTach.getRPM() >= 3800) {
            // Red indicator lights are solid when at speed,...
            redIndicator1.set(true);
            redIndicator2.set(true);
        } else if (speedTach.getRPM() <= 250) {//note:was 500 before BOB
            // ...off when the speed is too low..
            redIndicator1.set(false);
            redIndicator2.set(false);
        } else {
            // ...and blink when the speed is in between stepints...	
            if (lightIsOn) { // light was turned on last pass, turn it off now
                onCounter++;
                if (onCounter >= 20) {
                    redIndicator1.set(false);
                    redIndicator2.set(false);
                    lightIsOn = false;
                    offCounter = 0;
                }
            } else { // light is off, check to see if it is time to turn it on
                offCounter++;
                //BOB changes
 //               double temp = speedTach.getRPM();
//                temp = temp - 150;
//                double offTime = 71 - temp; 
//                if (offCounter >= offTime) {
                if (offCounter >= 20) { //BOB changes
                    redIndicator1.set(true);
                    redIndicator2.set(true);
                    lightIsOn = true;
                    onCounter = 0;
                }
            }
	}
    }  
    // </editor-fold>
}
