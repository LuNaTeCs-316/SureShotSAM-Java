/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lunatecs316.frc2013;

/**
 * Mapping of constants to named values for robot I/O
 * @author domenicpaul
 */
public class RobotMap {
    // Joystick inputs
    public static final int DRIVER_JOYSTICK = 1;
    public static final int OPERATOR_JOYSTICK = 2;
    
    // PWM outputs
    public static final int FRONT_LEFT_DRIVE_MOTOR = 1;
    public static final int FRONT_RIGHT_DRIVE_MOTOR = 2;
    public static final int REAR_LEFT_DRIVE_MOTOR = 3;
    public static final int REAR_RIGHT_DRIVE_MOTOR = 4;
    public static final int PICKUP_ANGLE_MOTOR = 5;
    public static final int SHOOTER_MOTOR = 6;
    public static final int SHOOTER_ANGLE_MOTOR = 7;
    public static final int PICKUP_BELT_MOTOR = 8;
    
    // Relay outputs
    public static final int COMPRESSOR_RELAY = 1;
    public static final int PICKUP_BELT_RELAY = 2;
    
    // Solenoid outputs
    public static final int SHOOTER_SOLENOID = 1;
    public static final int CLIMBING_SOLENOID = 2;
    
    // Analog inputs
    public static final int PICKUP_ANGLE_POT = 1;
    public static final int SHOOTER_ANGLE_POT = 2;
    
    // Digital inputs
    public static final int LEFT_DRIVE_ENCODER_A = 1;
    public static final int LEFT_DRIVE_ENCODER_B = 2;
    public static final int RIGHT_DRIVE_ENCODER_A = 3;
    public static final int RIGHT_DRIVE_ENCODER_B = 4;
    public static final int SHOOTER_SPEED_TACH = 5;
    public static final int COMPRESSOR_PRESSURE_SWITCH = 14;
}
