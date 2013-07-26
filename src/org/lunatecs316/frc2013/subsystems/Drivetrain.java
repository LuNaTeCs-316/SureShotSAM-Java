package org.lunatecs316.frc2013.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.lunatecs316.frc2013.RobotMap;
import org.lunatecs316.frc2013.commands.DriveWithXboxController;
import org.lunatecs316.frc2013.lib.SimplePIDController;
import org.lunatecs316.frc2013.lib.LuNaDrive;

/**
 * Drivetrain subsystem
 * @author domenicpaul
 */
public class Drivetrain extends Subsystem {

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
    
    private final int kEncoderTicksPerRot = 360;
    private final double kWheelDiameter = 6.0;           // in.
    private final double kWheelBaseWidth = 20.0;         // in.
    //private static final double kDistancePerRotation = 18.875;  // in.
    private final double kDistancePerRotation = kWheelDiameter * Math.PI;
    
    private double targetDistance;
    private double targetAngle;

    /**
     * Initialize the subsystem
     */
    public Drivetrain() {
        super();
        
        // Configure the encoders
        leftEncoder.start();
        rightEncoder.start();
        resetEncoders();
        
        // Configures the gyrof
        gyro.setSensitivity(0.007);
        gyro.reset();
    }
    
    /**
     * Set the default command for the subsystem
     */
    protected void initDefaultCommand() {
        setDefaultCommand(new DriveWithXboxController());
    }
    
    /**
     * Arcade drive with manual parameters
     * @param throttle forwards/reverse motion
     * @param turn turning value
     */
    public void arcadeDrive(double throttle, double turn) {
        driveMotors.drive(throttle, turn);
    }
    
    /**
     * Tank drive with manual parameters
     * @param left the value for the left motors
     * @param right the value for the right motors
     */
    public void tankDrive(double left, double right) {
        driveMotors.tankDrive(left, right);
    }
    
    /**
     * Setup the robot to drive straight to the specified distance. Negative numbers
     * will drive the robot in reverse.
     * @param inches the distance the robot should move
     */
    public void setTargetDistance(double inches) {
        
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
    public void setTargetAngle(double angle) {
        targetAngle = gyro.getAngle() + angle;
    }
    
    /**
     * Drives the robot straight until the robot reaches the set target
     */
    public void driveStraight() {
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
    public void turn() {
        double turnVal = angleController.calculate(targetAngle, gyro.getAngle());
        
        driveMotors.drive(0.0, turnVal);
    }
    
    /**
     * Check whether the robot is at the desired target yet (distance or
     * turning)
     * @return 
     */
    public boolean atTarget() {
        return (targetDistance == 0);
    }
    
    /**
     * Reset the drivetrain encoders
     */
    public void resetEncoders() {
        leftEncoder.reset();
        rightEncoder.reset();
    }
    
    /**
     * Reset the gyro
     */
    public void resetGyro() {
        gyro.reset();
    }
    
    // </editor-fold>
}
