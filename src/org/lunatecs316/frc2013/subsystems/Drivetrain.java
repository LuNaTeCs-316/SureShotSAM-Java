package org.lunatecs316.frc2013.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import org.lunatecs316.frc2013.RobotMap;
import org.lunatecs316.frc2013.lib.SimplePIDController;
import org.lunatecs316.frc2013.lib.LuNaDrive;
import org.lunatecs316.frc2013.lib.Util;

/**
 * Drivetrain subsystem
 * @author domenicpaul
 */
public class Drivetrain {
    
    // <editor-fold desc="Subsystem Components">
    // Place Subsystem Components in this section

    /* Drive Motors */
    private static final Victor frontLeftMotor = new Victor(RobotMap.FRONT_LEFT_DRIVE_MOTOR);
    private static final Victor frontRightMotor = new Victor(RobotMap.FRONT_RIGHT_DRIVE_MOTOR);
    private static final Victor rearLeftMotor = new Victor(RobotMap.REAR_LEFT_DRIVE_MOTOR);
    private static final Victor rearRightMotor = new Victor(RobotMap.REAR_RIGHT_DRIVE_MOTOR);
    
    /* Drive Motor Controller */
    private static final LuNaDrive driveMotors = new LuNaDrive(frontLeftMotor,
            frontRightMotor, rearLeftMotor, rearRightMotor);
    
    /* Sensors */
    private static final Encoder leftEncoder = new Encoder(RobotMap.LEFT_DRIVE_ENCODER_A,
            RobotMap.LEFT_DRIVE_ENCODER_B);
    private static final Encoder rightEncoder = new Encoder(RobotMap.RIGHT_DRIVE_ENCODER_A,
            RobotMap.RIGHT_DRIVE_ENCODER_B);
    private static final Gyro gyro = new Gyro(RobotMap.DRIVE_GYRO);
    
    
    /* PID Controllers */
    private static final SimplePIDController angleController =
            new SimplePIDController(-0.005, -0.0, -0.0);
    
    private static final SimplePIDController distanceController =
            new SimplePIDController(0.0004, 0.00000, 0.00005);
    
    // </editor-fold>
    
    // <editor-fold desc="Subsystem Data">
    // Place Subsystem Data in this section
    
    private static final int kEncoderTicksPerRot = 360;
    private static final double kWheelDiameter = 6.0;           // in.
    private static final double kWheelBaseWidth = 20.0;         // in.
    //private static final double kDistancePerRotation = 18.875;  // in.
    private static final double kDistancePerRotation = kWheelDiameter * Math.PI;
    
    private static double targetDistance;
    private static double targetAngle;
    
    // </editor-fold>
    
    /* Private constructor to prevent instantiation */
    private Drivetrain() {}
    
    // <editor-fold desc="Subsystem Methods">
    // Place Subsystem behavior in this section
    
    /**
     * Initialize the subsystem
     */
    public static void init() {
        // Configure the encoders
        leftEncoder.start();
        rightEncoder.start();
        resetEncoders();
        
        // Configures the gyrof
        gyro.setSensitivity(0.007);
        gyro.reset();
        
        // Setup LiveWindow
        LiveWindow.addActuator("Drivetrain", "FrontLeftMotor", frontLeftMotor);
        LiveWindow.addActuator("Drivetrain", "FrontRightMotor", frontRightMotor);
        LiveWindow.addActuator("Drivetrain", "RearLeftMotor", rearLeftMotor);
        LiveWindow.addActuator("Drivetrain", "RearRightMotor", rearRightMotor);
        LiveWindow.addSensor("Drivetrain", "LeftEncoder", leftEncoder);
        LiveWindow.addSensor("Drivetrain", "RightEncoder", rightEncoder);
        LiveWindow.addSensor("Drivetrain", "Gyro", gyro);
    }
    
    public static void debug() {
        System.out.println("[Drivetrain][debug] gyro: " + gyro.getAngle()
                + "; leftEncoder: " + leftEncoder.get()
                + "; rightEncoder: " + rightEncoder.get());
    }
    
    /**
     * Arcade drive with a joystick
     * @param stick the joystick
     */
    public static void arcadeDrive(Joystick stick) {
        double throttle = -stick.getY();
        double turn = stick.getRawAxis(4);
        
        throttle = Util.deadband(throttle, 0.2);
        turn = Util.deadband(turn, 0.2);
        
        driveMotors.drive(throttle, turn);
    }
    
    /**
     * Arcade drive with manual parameters
     * @param throttle forwards/reverse motion
     * @param turn turning value
     */
    public static void arcadeDrive(double throttle, double turn) {
        driveMotors.drive(throttle, turn);
    }
    
    /**
     * Tank drive with manual parameters
     * @param left the value for the left motors
     * @param right the value for the right motors
     */
    public static void tankDrive(double left, double right) {
        driveMotors.tankDrive(left, right);
    }
    
    /**
     * Setup the robot to drive straight to the specified distance. Negative numbers
     * will drive the robot in reverse.
     * @param inches the distance the robot should move
     */
    public static void setTargetDistance(double inches) {
        
        // Calculate the target encoder tick value
        targetDistance = (inches * kEncoderTicksPerRot)
                                    / kDistancePerRotation;
        
        // Reset encoders
        leftEncoder.reset();
        rightEncoder.reset();
        
        driveMotors.drive(0, 0);
    }
    
    /**
     * Setup the robot to turn by the specified angle. Positive angles turn right,
     * and negative angles left.
     * @param degrees the amount by which to turn the robot
     */
    public static void setTargetAngle(double angle) {
        targetAngle = gyro.getAngle() + angle;
    }
    
    /**
     * Drives the robot straight until the robot reaches the set target
     */
    public static void driveStraight() {
        double power = distanceController.calculate(targetDistance, -rightEncoder.get());
        double turnVal = angleController.calculate(0, gyro.getAngle());
        
        if (power > 0.75) {
            power = 0.75;
        }
        
        driveMotors.drive(power, 0.0);
        //driveMotors.drive(speed, turnVal);
    }
    
    /**
     * Turn the robot the specified amount.
     */
    public static void turn() {
        double turnVal = angleController.calculate(targetAngle, gyro.getAngle());
        
        driveMotors.drive(0.0, turnVal);
    }
    
    /**
     * Check whether the robot is at the desired target yet (distance or
     * turning)
     * @return 
     */
    public static boolean atTarget() {
        return (targetDistance == 0);
    }
    
    /**
     * Reset the drivetrain encoders
     */
    public static void resetEncoders() {
        leftEncoder.reset();
        rightEncoder.reset();
    }
    
    /**
     * Reset the gyro
     */
    public static void resetGyro() {
        gyro.reset();
    }
    
    // </editor-fold>
}
