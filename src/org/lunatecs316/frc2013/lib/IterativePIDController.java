package org.lunatecs316.frc2013.lib;

/**
 * Custom PID controller that runs in the main loop as apposed to a thread.
 * @author domenicpaul
 */
public class IterativePIDController {
    
    private double kP;
    private double kI;
    private double kD;
    
    private double deadband;
    private boolean atTarget = false;
    
    private double error = 0.0;
    private double integral = 0.0;
    private double prevError = 0.0;
    
    public IterativePIDController(double kP, double kI, double kD) {
        this(kP, kI, kD, 0.0);
    }
    
    public IterativePIDController(double kP, double kI, double kD, double db) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.deadband = db;
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
        
        atTarget = (Util.deadband(correction, deadband) == 0);
        
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
