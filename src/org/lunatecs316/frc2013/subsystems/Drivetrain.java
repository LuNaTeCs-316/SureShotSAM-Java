package org.lunatecs316.frc2013.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import org.lunatecs316.frc2013.RobotMap;
import org.lunatecs316.frc2013.lib.IterativePIDController;
import org.lunatecs316.frc2013.lib.LuNaDrive;

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
    
    /* Encoders */
    private static final Encoder leftEncoder = new Encoder(RobotMap.LEFT_DRIVE_ENCODER_A,
            RobotMap.LEFT_DRIVE_ENCODER_B);
    private static final Encoder rightEncoder = new Encoder(RobotMap.RIGHT_DRIVE_ENCODER_A,
            RobotMap.RIGHT_DRIVE_ENCODER_B);
    
    /* PID Controllers */
    private static final IterativePIDController motorSyncController =
            new IterativePIDController(0.01, 0.0, 0.0);
    
    private static final IterativePIDController distanceController =
            new IterativePIDController(0.0004, 0.00000, 0.00005);
    
    // </editor-fold>
    
    // <editor-fold desc="Subsystem Data">
    // Place Subsystem Data in this section
    
    private static final int kEncoderTicksPerRotation = 1440;       // 360 * 4 (4x encoding)
    private static final int kLeftEncoderTicksPerRot = 200;
    private static final int kRightEncoderTicksPerRot = 360;
    private static final double kWheelDiameter = 6.0;               // in.
    private static final double kWheelBaseWidth = 20.0;             // in.
    private static final double kDistancePerRotation = 18.875;
    
    private static double targetDistance;
    private static double targetSpeed;
    
    // </editor-fold>
    
    /* Private constructor to prevent instantiation */
    private Drivetrain() {}
    
    // <editor-fold desc="Subsystem Methods">
    // Place Subsystem behavior in this section
    
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
    
    public static void debug() {
        System.out.println("[Drivetrain][debug] leftEncoder: " + leftEncoder.get()
                + "; rightEncoder: " + rightEncoder.get() + ";");
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
    public static void setTargetDistance(double inches, double speed) {
        
        // Calculate the target encoder tick value
        targetDistance = (inches * kRightEncoderTicksPerRot)
                                    / kDistancePerRotation;
        
        // 360 / 3.14 * 6.0
        
        targetSpeed = speed;
        
        // Reset encoders
        leftEncoder.reset();
        rightEncoder.reset();
        
        driveMotors.drive(0, 0);
    }
    
    /**
     * Drives the robot straight until the robot reaches the set target
     */
    public static void driveStraight() {
        // Calculate right side motor speed
        double speed = distanceController.calculate(targetDistance, -rightEncoder.get());
        //double syncSpeed = motorSyncController.calculate(-rightEncoder.get(), -leftEncoder.get());
        
        if (speed > 0.75) {
            speed = 0.75;
        }
        
        frontRightMotor.set(-speed);
        rearRightMotor.set(-speed);
        frontLeftMotor.set(speed);
        rearLeftMotor.set(speed);
    }
    
    /**
     * Setup the robot to turn by the specified angle. Positive angles turn right,
     * and negative angles left.
     * @param degrees the amount by which to turn the robot
     */
    public static void setTargetAngle(double degrees, double speed) {
        double dist = ((degrees * Math.PI) / 180) * (kWheelBaseWidth / 2);
        double targetDist = (dist * kEncoderTicksPerRotation)
                                    / (Math.PI * kWheelDiameter);
    }
    
    /**
     * Turn the robot the specified amount.
     */
    public static void turn() {
        double leftSpeed = targetSpeed;
        double rightSpeed = targetSpeed;
        
        if (targetDistance > 0) {
            if (leftEncoder.get() < targetDistance) {
                frontLeftMotor.set(leftSpeed);
                rearLeftMotor.set(leftSpeed);
                frontRightMotor.set(rightSpeed);
                rearRightMotor.set(rightSpeed);
            } else {
                // At target; reset count to 0
            }
        } else if (targetDistance < 0) {
            if (leftEncoder.get() >= targetDistance) {
                // Not at target yet, keep going
                frontLeftMotor.set(-leftSpeed);
                rearLeftMotor.set(-leftSpeed);
                frontRightMotor.set(-rightSpeed);   // Reversed because motors face opposite direction
                rearRightMotor.set(-rightSpeed);
            } else {
                // At target; reset count to 0
            }
        }
    }
    
    /**
     * Check whether the robot is at the desired target yet (distance or
     * turning)
     * @return 
     */
    public boolean atTarget() {
        return (targetDistance == 0);
    }
    
    // </editor-fold>
}
