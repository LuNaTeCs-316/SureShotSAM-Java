package org.lunatecs316.frc2013.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.lunatecs316.frc2013.RobotMap;
import org.lunatecs316.frc2013.commands.DriveWithXboxController;
import org.lunatecs316.frc2013.lib.LuNaDrive;

/**
 * Drivetrain subsystem
 * @author domenicpaul
 */
public final class Drivetrain extends Subsystem {

    /* Constants */
    public static final int kEncoderTicksPerRot = 360;
    public static final double kWheelDiameter = 6.0;           // in.
    public static final double kWheelBaseWidth = 20.0;         // in.
    //public static final double kDistancePerRotation = 18.875;  // in.
    public static final double kDistancePerRotation = kWheelDiameter * Math.PI;

    /* Drive Motors */
    private final Victor frontLeftMotor = new Victor(RobotMap.FRONT_LEFT_DRIVE_MOTOR);
    private final Victor frontRightMotor = new Victor(RobotMap.FRONT_RIGHT_DRIVE_MOTOR);
    private final Victor rearLeftMotor = new Victor(RobotMap.REAR_LEFT_DRIVE_MOTOR);
    private final Victor rearRightMotor = new Victor(RobotMap.REAR_RIGHT_DRIVE_MOTOR);
    
    /* Drive Motor Controller */
    private final LuNaDrive driveMotors = new LuNaDrive(frontLeftMotor,
            frontRightMotor, rearLeftMotor, rearRightMotor);
    
    /* Sensors */
    private final Encoder leftEncoder = new Encoder(RobotMap.LEFT_DRIVE_ENCODER_A,
            RobotMap.LEFT_DRIVE_ENCODER_B);
    private final Encoder rightEncoder = new Encoder(RobotMap.RIGHT_DRIVE_ENCODER_A,
            RobotMap.RIGHT_DRIVE_ENCODER_B);
    private final Gyro gyro = new Gyro(RobotMap.DRIVE_GYRO);
    
    /**
     * Drivetrain constructor
     */
    public Drivetrain() {
        // Call parent class' constructor
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
     * Arcade arcadeDrive with manual parameters
     * @param throttle forwards/reverse motion
     * @param turn turning value
     */
    public void arcadeDrive(double throttle, double turn) {
        driveMotors.arcadeDrive(throttle, turn);
    }
    
    /**
     * Tank arcadeDrive with manual parameters
     * @param left the value for the left motors
     * @param right the value for the right motors
     */
    public void tankDrive(double left, double right) {
        driveMotors.tankDrive(left, right);
    }

    /**
     * Get the average output of the left and right arcadeDrive encoders
     * @return the average encoder count
     */
    public double getAverageEncoderCount() {
        double sum = leftEncoder.get() + rightEncoder.get();
        return (sum / 2);
    }

    /**
     * Reset the drivetrain encoders
     */
    public void resetEncoders() {
        leftEncoder.reset();
        rightEncoder.reset();
    }
    
    /**
     * Get the current angle read by the gyro sensor
     * @return the current gyro angle
     */
    public double getGyroAngle() {
        return gyro.getAngle();
    }

    /**
     * Reset the gyro
     */
    public void resetGyro() {
        gyro.reset();
    }
}
