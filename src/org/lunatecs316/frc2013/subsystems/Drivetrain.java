package org.lunatecs316.frc2013.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import org.lunatecs316.frc2013.RobotMap;
import org.lunatecs316.frc2013.lib.LuNaDrive;

/**
 * Drivetrain subsystem
 * @author domenicpaul
 */
public class Drivetrain {
    
    /* Drive Motors */
    private static final Victor frontLeftMotor = new Victor(RobotMap.FRONT_LEFT_DRIVE_MOTOR);
    private static final Victor frontRightMotor = new Victor(RobotMap.FRONT_RIGHT_DRIVE_MOTOR);
    private static final Victor rearLeftMotor = new Victor(RobotMap.REAR_LEFT_DRIVE_MOTOR);
    private static final Victor rearRightMotor = new Victor(RobotMap.REAR_RIGHT_DRIVE_MOTOR);
    
    /* Drive Motor Controller */
    private static final LuNaDrive driveMotors = new LuNaDrive(frontLeftMotor,
            frontRightMotor, rearLeftMotor, rearRightMotor);
    
    /* Encoders */
    private static final Encoder leftEncoder = new Encoder(RobotMap.LEFT_DRIVE_ENCODER_A,
            RobotMap.LEFT_DRIVE_ENCODER_B);
    private static final Encoder rightEncoder = new Encoder(RobotMap.RIGHT_DRIVE_ENCODER_A,
            RobotMap.RIGHT_DRIVE_ENCODER_B);
    
    /* Private constructor to prevent instantiation */
    private Drivetrain() {}
    
    /**
     * Initialize the subsystem
     */
    public static void init() {
        // Start the encoders
        leftEncoder.start();
        rightEncoder.start();
        
        // Setup LiveWindow
        LiveWindow.addActuator("Drivetrain", "FrontLeftMotor", frontLeftMotor);
        LiveWindow.addActuator("Drivetrain", "FrontRightMotor", frontRightMotor);
        LiveWindow.addActuator("Drivetrain", "RearLeftMotor", rearLeftMotor);
        LiveWindow.addActuator("Drivetrain", "RearRightMotor", rearRightMotor);
        LiveWindow.addSensor("Drivetrain", "LeftEncoder", leftEncoder);
        LiveWindow.addSensor("Drivetrain", "RightEncoder", rightEncoder);
    }
    
    /**
     * Arcade drive with a joystick
     * @param stick the joystick
     */
    public static void arcadeDrive(Joystick stick) {
        driveMotors.drive(stick);
    }
    
    /**
     * Arcade drive with manual parameters
     * @param throttle forwards/reverse motion
     * @param turn turning value
     */
    public static void arcadeDrive(double throttle, double turn) {
        driveMotors.drive(throttle, turn);
    }
}
