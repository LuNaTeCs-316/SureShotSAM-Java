package org.lunatecs316.frc2013;

/**
 * Mapping of constants to named values for robot I/O
 * @author domenicpaul
 */
public class RobotMap {
    
    /* Joystick inputs */
    public static final int kDriverJoystick = 1;
    public static final int kOperatorJoystick = 2;
    
    /* PWM outputs */
    public static final int kFrontLeftDriveMotor = 1;
    public static final int kRearLeftDriveMotor = 2;
    public static final int kFrontRightDriveMotor = 3;
    public static final int kRearRightDriveMotor = 4;
    public static final int kPickupAngleMotor = 5;
    public static final int kShooterMotor = 6;
    public static final int kShooterAngleMotor = 7;
    public static final int kPickupBeltMotor = 8;
    
    /* Relay outputs */
    public static final int kCompressorRelay = 1;
    public static final int kPickupBeltRelay = 2;
    
    /* Solenoid outputs */
    public static final int kShooterSolenoid = 1;
    public static final int kClimbingSolenoid = 2;
    public static final int kRedIndicator1 = 6;
    public static final int kRedIndicator2 = 7;
    public static final int kBlueIndicator = 8;
    
    /* Analog inputs */
    public static final int kDriveGyro = 1;
    public static final int kPickupAngle = 2;
    public static final int kShooterAnglePot = 3;
    
    /* Digital inputs */
    public static final int kLeftDriveEncoderA = 3;
    public static final int kLeftDriveEncoderB = 4;
    public static final int kRightDriveEncoderA = 1;
    public static final int kRightDriveEncoderB = 2;
    public static final int kShooterSpeedTach = 5;
    public static final int kCompressorPressureSwitch = 14;
}
