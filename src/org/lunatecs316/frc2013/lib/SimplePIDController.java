package org.lunatecs316.frc2013.lib;

/**
 * Simple PID controller
 * @author domenicpaul
 */
public class SimplePIDController {
    
    // Constants
    private double kP;
    private double kI;
    private double kD;
    
    // Data
    private double error = 0.0;
    private double integral = 0.0;
    private double prevError = 0.0;
    
    /**
     * Create a PID controller, specifying P, I, and D gains
     * @param kP proportional gain
     * @param kI integral gain
     * @param kD derivative gain
     */
    public SimplePIDController(double kP, double kI, double kD) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
    }
    
    /**
     * Run one iteration of the PID controller. Needs to be called at a regular
     * interval to function properly.
     * @param setpoint the desired target
     * @param current the current value
     * @return the correction value
     */
    public double run(double setpoint, double current) {
        
        error = setpoint - current;
        integral += error;
        double derivative = error - prevError;
        
        double correction = error * kP + integral * kI + derivative * kD;
        
        return correction;
    }
}
