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
 *
 * @author domenicpaul
 */
public class Shooter {
    /**
     * Represents different positions the shooter can move to
     */
    public static class Position {
        private double val;
        
        public static final Position Top = new Position(3.41);
        public static final Position Mid = new Position(3.31);
        public static final Position Load = new Position(2.0);
        
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
    
    private static final Victor motor = new Victor(RobotMap.SHOOTER_MOTOR);
    private static final Tachometer speedTach = new Tachometer(RobotMap.SHOOTER_SPEED_TACH);
    private static final PIDController speedController = new PIDController(-0.005,
            0.000, 0.0, speedTach, motor);
    
    private static final Jaguar angleMotor = new Jaguar(RobotMap.SHOOTER_ANGLE_MOTOR);
    private static final Potentiometer anglePot = new Potentiometer(RobotMap.SHOOTER_ANGLE_POT);
    private static final PIDController angleController = new PIDController(22.5,
            0.0, 0.0, anglePot, angleMotor);
    
    private static final Solenoid solenoid = new Solenoid(RobotMap.SHOOTER_SOLENOID);
    
    // Private to prevent creation of an instance
    private Shooter() {}
    
    /**
     * Setup the subsystem
     */
    public static void init() {
        speedController.setSetpoint(3975);
        speedController.setAbsoluteTolerance(350);
        speedTach.start();
        
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
     * @param val - the movement speed value
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
    
    public static boolean atSpeed() {
        return speedController.onTarget();
    }
    
    /**
     * Fire the shot
     * @param value - whether to shoot or not
     */
    public static void fire(boolean value) {
        solenoid.set(value);
    }
}
