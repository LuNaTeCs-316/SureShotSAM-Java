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
    
    // Deadband
    private double tolerance;
    private boolean atTarget = false;
    
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
        this(kP, kI, kD, 0.0);
    }
    
    /**
     * Create a PID controller, specifying P, I, and D gains, and a deadband
     * @param kP proportional gain
     * @param kI integral gain
     * @param kD derivative gain
     * @param tolerance deadband
     */
    public SimplePIDController(double kP, double kI, double kD, double tolerance) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.tolerance = tolerance;
    }
    
    /**
     * Set new values for the kP, kI, and kD
     * @param kP new proportional constant
     * @param kI new integral constant
     * @param kD new derivative constant
     */
    public void setPID(double kP, double kI, double kD) {
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
    public double calculate(double setpoint, double current) {
        
        error = setpoint - current;
        integral += error;
        double derivative = error - prevError;
        
        double correction = error * kP + integral * kI + derivative * kD;
        
        atTarget = (Util.deadband(correction, tolerance) == 0);
        
        return correction;
    }
    
    /**
     * Checks to see if the output is within the deadband
     * @return atTarget
     */
    public boolean atTarget() {
        return atTarget;
    }
}
