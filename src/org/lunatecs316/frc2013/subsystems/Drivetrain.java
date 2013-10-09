package org.lunatecs316.frc2013.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.lunatecs316.frc2013.Constants;
import org.lunatecs316.frc2013.RobotMap;
import org.lunatecs316.frc2013.lib.SimplePIDController;
import org.lunatecs316.frc2013.lib.LuNaDrive;

/**
 * Drivetrain subsystem
 * @author domenicpaul
 */
public class Drivetrain extends Subsystem {

    /* Drive Motors */
    private Victor frontLeftMotor = new Victor(RobotMap.FRONT_LEFT_DRIVE_MOTOR);
    private Victor frontRightMotor = new Victor(RobotMap.FRONT_RIGHT_DRIVE_MOTOR);
    private Victor rearLeftMotor = new Victor(RobotMap.REAR_LEFT_DRIVE_MOTOR);
    private Victor rearRightMotor = new Victor(RobotMap.REAR_RIGHT_DRIVE_MOTOR);

    /* Drive Motor Controller */
    private LuNaDrive driveMotors = new LuNaDrive(frontLeftMotor,
            frontRightMotor, rearLeftMotor, rearRightMotor);

    /* Sensors */
    private Encoder leftEncoder = new Encoder(RobotMap.LEFT_DRIVE_ENCODER_A,
            RobotMap.LEFT_DRIVE_ENCODER_B);
    private Encoder rightEncoder = new Encoder(RobotMap.RIGHT_DRIVE_ENCODER_A,
            RobotMap.RIGHT_DRIVE_ENCODER_B);
    private Gyro gyro = new Gyro(RobotMap.DRIVE_GYRO);


    /* PID Controllers */
    private SimplePIDController angleController = new SimplePIDController(-0.05, -0.0, -0.0);
    private SimplePIDController distanceController = new SimplePIDController(0.001, 0.00000, 0.00005);
    //new SimplePIDController(0.0004, 0.00000, 0.00005);

    //private static final int kEncoderTicksPerRot = 360;
    //private static final double kWheelDiameter = 6.25;           // in.
    //private static final double kWheelBaseWidth = 20.0;         // in.
    //private static final double kDistancePerRotation = 18.875;  // in.
    //private static final double kDistancePerRotation = kWheelDiameter * Math.PI;

    private double targetDistance;
    private double targetAngle;
    private boolean turning = false;
    private boolean drivingStraight = false;

    public void init() {
        // Configure the encoders
        leftEncoder.start();
        rightEncoder.start();
        resetEncoders();

        // Configures the gyro
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

    public void updateSmartDashboard() {
        SmartDashboard.putNumber("Gyro", gyro.getAngle());
        SmartDashboard.putNumber("LeftDriveEncoder", leftEncoder.get());
        SmartDashboard.putNumber("RightDriveEncoder", rightEncoder.get());
    }
    
    public void updateConstants() {
        distanceController.setPID(Constants.DriveDistanceP.getValue(),
                                  Constants.DriveDistanceI.getValue(),
                                  Constants.DriveDistanceD.getValue());
        
        angleController.setPID(Constants.DriveAngleP.getValue(), 
                               Constants.DriveAngleI.getValue(),
                               Constants.DriveAngleD.getValue());
    }

    /**
     * Arcade drive with manual parameters
     * @param throttle forwards/reverse motion
     * @param turn turning value
     */
    public void arcadeDrive(double throttle, double turn) {
        drivingStraight = false;
        turning = false;
        driveMotors.drive(throttle, turn);
    }

    /**
     * Tank drive with manual parameters
     * @param left the value for the left motors
     * @param right the value for the right motors
     */
    public void tankDrive(double left, double right) {
        drivingStraight = false;
        turning = false;
        driveMotors.tankDrive(left, right);
    }

    /**
     * Drive the robot straight to the specified distance.
     * @param inches the distance the robot should move. + = forwards, - = reverse
     * @param firstCall if this is the first call then setup
     */
    public void driveStraight(double inches) {
        if (!drivingStraight) {
            drivingStraight = true;
            turning = false;
            double distancePerRotation = Constants.DriveWheelDiameter.getValue() / Math.PI;

            // Calculate the target encoder tick value
            targetDistance = (inches * Constants.DriveEncoderTicksPerRot.getValue())
                                    / distancePerRotation;

            // Reset encoders
            leftEncoder.reset();
            rightEncoder.reset();

            driveMotors.drive(0, 0);
        }

        double power = distanceController.calculate(targetDistance, -rightEncoder.get());
        double turnVal = angleController.calculate(0, gyro.getAngle());

        if (power > 0.75) {
            power = 0.75;
        }

        driveMotors.drive(power, 0.0);
    }

    /**
     * Turn the robot by the specified angle. Positive angles turn right,
     * and negative angles left.
     * @param degrees the amount by which to turn the robot
     * @param firstCall setup if it is the first call
     */
    public void turn(double angle) {
        if (!turning) {
            turning = true;
            drivingStraight = false;
            //test to fix that angle is off by 1/6
            angle = angle + angle/6;

            gyro.reset();
            //turn ccw
            //targetAngle = angle;

            //turn to cw
            targetAngle = - angle;
        }

        double turnVal = angleController.calculate(targetAngle, gyro.getAngle());

        driveMotors.drive(0.0, turnVal);
    }

    /**
     * Check whether the robot is at the desired target yet (distance or
     * turning)
     * @return
     */
    public boolean atTarget() {
        if (drivingStraight) {
            return distanceController.atTarget();
        } else if (turning) {
            return angleController.atTarget();
        }
        
        // No target set, can't be on a non-existant target
        return false;
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
}
