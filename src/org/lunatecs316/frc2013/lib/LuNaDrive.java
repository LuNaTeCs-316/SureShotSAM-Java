/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
        double throttle = -(joystick.getY());
        if (Math.abs(throttle) < m_deadband) {
            throttle = 0;
        } else {
            throttle = (m_throttleGain * (throttle * throttle * throttle)) +
                    ((1 - m_throttleGain) * throttle);
        }
        
        double turn = -(joystick.getRawAxis(4));
        if (Math.abs(turn) < m_deadband) {
            turn = 0;
        } else {
            turn = (m_turnGain * (turn * turn * turn)) + ((1 - m_turnGain) * turn);
        }
        
        if (Math.abs(throttle) > 0.5) {
            turn = turn * (m_turnBoostGain * Math.abs(throttle));
        }
        
        double t_left = throttle + turn;
        double t_right = throttle - turn;
        
        double left = t_left + skim(t_right, m_skimGain);
        double right = t_right + skim(t_left, m_skimGain);
        
        m_frontLeftMotor.set(left);
        m_rearLeftMotor.set(left);
        m_frontRightMotor.set(right);
        m_rearRightMotor.set(right);
    }
    
    public void drive(double throttle, double turn) {
        if (Math.abs(throttle) > 0.5) {
            turn = turn * (m_turnBoostGain * Math.abs(throttle));
        }
        
        double t_left = throttle + turn;
        double t_right = throttle - turn;
        
        double left = t_left + skim(t_right, m_skimGain);
        double right = t_right + skim(t_left, m_skimGain);
        
        m_frontLeftMotor.set(left);
        m_rearLeftMotor.set(left);
        m_frontRightMotor.set(right);
        m_rearRightMotor.set(right);
    }
}
