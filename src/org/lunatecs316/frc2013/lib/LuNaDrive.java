package org.lunatecs316.frc2013.lib;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;

/**
 * Custom drivetrain class
 * @author domenicpaul
 */
public class LuNaDrive {
    private SpeedController m_frontLeftMotor;
    private SpeedController m_frontRightMotor;
    private SpeedController m_rearLeftMotor;
    private SpeedController m_rearRightMotor;
    
    private double m_throttleGain = 1.0;
    private double m_turnGain = 1.0;
    private double m_deadband = 0.2;
    private double m_turnBoostGain = 0.5;
    private double m_skimGain = 0.5;
    
    public LuNaDrive(SpeedController left, SpeedController right) {
        this(left, right, null, null);
    }
    
    public LuNaDrive(SpeedController frontLeft, SpeedController frontRight,
            SpeedController rearLeft, SpeedController rearRight) {
        m_frontLeftMotor = frontLeft;
        m_frontRightMotor = frontRight;
        m_rearLeftMotor = rearLeft;
        m_rearRightMotor = rearRight;
    }
    
    public void setParameters(double throttleGain, double turnGain, double deadband,
            double turnBoostGain, double skimGain) {
        m_throttleGain = throttleGain;
        m_turnGain = turnGain;
        m_deadband = deadband;
        m_turnBoostGain = turnBoostGain;
        m_skimGain = skimGain;
    }
    
    public void setThrottleGain(double throttleGain) {
        m_throttleGain = throttleGain;
    }
    
    public void setTurnGain(double turnGain) {
        m_turnGain = turnGain;
    }
    
    public void setDeadband(double deadband) {
        m_deadband = deadband;
    }
    
    public void setTurnBoostGain(double turnBoostGain) {
        m_turnBoostGain = turnBoostGain;
    }
    
    public void setSkimGain(double skimGain) {
        m_skimGain = skimGain;
    }
    
    public double getThrottleGain() {
        return m_throttleGain;
    }
    
    public double getTurnGain() {
        return m_turnGain;
    }
    
    public double getDeadband() {
        return m_deadband;
    }
    
    public double getTurnBoostGain() {
        return m_turnBoostGain;
    }
    
    public double getSkimGain() {
        return m_skimGain;
    }
    
    /**
     * Skim the excess off of the value and multiply by a constant
     * @param value - the value to be skimmed
     * @param gain - a constant gain
     * @return - the skimmed excess value
     */
    private double skim(double value, double gain) {
        if (value > 1.0) {
            return ((value - 1.0) * gain);
        } else if (value < -1.0) {
            return ((value + 1.0) * gain);
        }
        return 0.0;
    }
    
    /**
     * Custom arcade drive control scheme
     * @param joystick - the driver's joystick (XBox controller only)
     */
    public void drive(Joystick joystick) {
        double throttle = Util.deadband(-(joystick.getY()), m_deadband);
        throttle = (m_throttleGain * (throttle * throttle * throttle)) + 
                ((1 - m_throttleGain) * throttle);
        
        double turn = Util.deadband(-(joystick.getRawAxis(4)), m_deadband);
        turn = (m_turnGain * (turn * turn * turn)) + ((1 - m_turnGain) * turn);
        
        // Call the main drive function
        drive(throttle, turn);
    }
    
    /**
     * Custom arcade drive control scheme
     * @param throttle - the forward movement
     * @param turn - the turn value
     */
    public void drive(double throttle, double turn) {
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
            m_frontRightMotor.set(right);    
        }
        if (m_rearRightMotor != null) {
            m_rearRightMotor.set(right);
        }
    }
}
