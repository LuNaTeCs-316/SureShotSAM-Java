package org.lunatecs316.frc2013.lib;

import edu.wpi.first.wpilibj.SpeedController;

/**
 * Custom drivetrain class
 * @author domenicpaul
 */
public class LuNaDrive {
    
    /* Drive Motors */
    private SpeedController m_frontLeftMotor;
    private SpeedController m_frontRightMotor;
    private SpeedController m_rearLeftMotor;
    private SpeedController m_rearRightMotor;
    
    /* Drive Parameters */
    private double m_throttleGain = 1.0;
    private double m_turnGain = 1.0;
    private double m_deadband = 0.2;
    private double m_turnBoostGain = 0.5;
    private double m_skimGain = 0.5;
    
    /**
     * Create a new two-motor LuNaDrive
     * @param left the left motor
     * @param right the right motor
     */
    public LuNaDrive(SpeedController left, SpeedController right) {
        this(left, right, null, null);
    }
    
    /**
     * Create a new four-motor LuNaDrive
     * @param frontLeft the front left motor
     * @param frontRight the front right motor
     * @param rearLeft the rear left motor
     * @param rearRight the rear right motor
     */
    public LuNaDrive(SpeedController frontLeft, SpeedController frontRight,
            SpeedController rearLeft, SpeedController rearRight) {
        m_frontLeftMotor = frontLeft;
        m_frontRightMotor = frontRight;
        m_rearLeftMotor = rearLeft;
        m_rearRightMotor = rearRight;
    }
    
    /**
     * Convenience method to set all parameters at once
     * @param throttleGain the throttle filtering gain
     * @param turnGain the turn filtering gain
     * @param deadband the joystick deadband
     * @param turnBoostGain the turning boost gain
     * @param skimGain the skimming gain
     */
    public void setParameters(double throttleGain, double turnGain, double deadband,
            double turnBoostGain, double skimGain) {
        m_throttleGain = throttleGain;
        m_turnGain = turnGain;
        m_deadband = deadband;
        m_turnBoostGain = turnBoostGain;
        m_skimGain = skimGain;
    }
    
    /**
     * Set the throttle filtering gain
     * @param throttleGain the throttle filtering gain
     */
    public void setThrottleGain(double throttleGain) {
        m_throttleGain = throttleGain;
    }
    
    /**
     * Set the turn filtering gain
     * @param turnGain the turn filtering gain
     */
    public void setTurnGain(double turnGain) {
        m_turnGain = turnGain;
    }
    
    /**
     * Set the joystick input deadband
     * @param deadband the joystick deadband
     */
    public void setDeadband(double deadband) {
        m_deadband = deadband;
    }
    
    /**
     * Set the turn boost gain
     * @param turnBoostGain the turn boost gain
     */
    public void setTurnBoostGain(double turnBoostGain) {
        m_turnBoostGain = turnBoostGain;
    }
    
    /**
     * Set the skim gain
     * @param skimGain the skim gain
     */
    public void setSkimGain(double skimGain) {
        m_skimGain = skimGain;
    }
    
    /**
     * Skim the excess off of the value and multiply by a constant
     * @param value the value to be skimmed
     * @param gain a constant gain
     * @return the skimmed excess value
     */
    private double skim(double value, double gain) {
        if (value > 1.0) {
            return ((value - 1.0) * gain);
        } else if (value < -1.0) {
            return ((value + 1.0) * gain);
        }
        return 0.0;
    }

    public void drive(double throttle, double turn) {
        drive(throttle, turn, false);
    }
    
    /**
     * Custom arcade drive control scheme
     * @param throttle the forward movement
     * @param turn the turn value
     */
    public void drive(double throttle, double turn, boolean deadband) {
        if (deadband) {
            throttle = Util.deadband(throttle, m_deadband);
            turn = Util.deadband(turn, m_deadband);
        }
        throttle = (m_throttleGain * (throttle * throttle * throttle)) + 
                ((1 - m_throttleGain) * throttle);
        
        turn = (m_turnGain * (turn * turn * turn)) + ((1 - m_turnGain) * turn);

        if (Math.abs(throttle) > 0.5) {
            turn = turn * (m_turnBoostGain * Math.abs(throttle));
        }
        
        double t_left = throttle + turn;
        double t_right = throttle - turn;
        
        double left = t_left + skim(t_right, m_skimGain);
        double right = t_right + skim(t_left, m_skimGain);
        
        if (m_frontLeftMotor != null) {
            m_frontLeftMotor.set(left);
        }
        if (m_rearLeftMotor != null) {
            m_rearLeftMotor.set(left);        
        }
        if (m_frontRightMotor != null) {
            m_frontRightMotor.set(-right);    
        }
        if (m_rearRightMotor != null) {
            m_rearRightMotor.set(-right);
        }
    }
    
    /**
     * Tank drive control
     * @param left the left side output
     * @param right the right side output
     */
    public void tankDrive(double left, double right) {
        if (m_frontLeftMotor != null) {
            m_frontLeftMotor.set(left);
        }
        if (m_rearLeftMotor != null) {
            m_rearLeftMotor.set(left);
        }
        if (m_frontRightMotor != null) {
            m_frontRightMotor.set(right);
        }
        if (m_rearRightMotor != null) {
            m_rearRightMotor.set(right);
        }
    }
}
