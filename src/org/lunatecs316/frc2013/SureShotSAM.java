/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.lunatecs316.frc2013;

import org.lunatecs316.frc2013.lib.Potentiometer;
import org.lunatecs316.frc2013.lib.Tachometer;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class SureShotSAM extends IterativeRobot {
    // DS/Joysticks
    DriverStation driverStation = DriverStation.getInstance();
    Joystick driverController = new Joystick(RobotMap.DRIVER_JOYSTICK);
    Joystick operatorJoystick = new Joystick(RobotMap.OPERATOR_JOYSTICK);
    
    // Drivetrain
    Victor frontLeftDriveMotor = new Victor(RobotMap.FRONT_LEFT_DRIVE_MOTOR);
    Victor frontRightDriveMotor = new Victor(RobotMap.FRONT_RIGHT_DRIVE_MOTOR);
    Victor rearLeftDriveMotor = new Victor(RobotMap.REAR_LEFT_DRIVE_MOTOR);
    Victor rearRightDriveMotor = new Victor(RobotMap.REAR_RIGHT_DRIVE_MOTOR);
    RobotDrive drivetrain = new RobotDrive(frontLeftDriveMotor,
            frontRightDriveMotor, rearLeftDriveMotor, rearRightDriveMotor);
    Encoder leftDriveEncoder = new Encoder(RobotMap.LEFT_DRIVE_ENCODER_A,
            RobotMap.LEFT_DRIVE_ENCODER_B);
    Encoder rightDriveEncoder = new Encoder(RobotMap.RIGHT_DRIVE_ENCODER_A,
            RobotMap.RIGHT_DRIVE_ENCODER_B);
    
    // Pickup
    Relay pickupBeltRelay = new Relay(RobotMap.PICKUP_BELT_RELAY);
    Jaguar pickupAngleMotor = new Jaguar(RobotMap.PICKUP_ANGLE_MOTOR);
    Potentiometer pickupAnglePot = new Potentiometer(RobotMap.PICKUP_ANGLE_POT);
    PIDController pickupAngleController = new PIDController(-5.0, -0.1, 0.0,
            pickupAnglePot, pickupAngleMotor);
    Victor pickupBeltMotor = new Victor(RobotMap.PICKUP_BELT_MOTOR);
    
    // Shooter
    Victor shooterMotor = new Victor(RobotMap.SHOOTER_MOTOR);
    Tachometer shooterSpeedTach = new Tachometer(RobotMap.SHOOTER_SPEED_TACH);
    PIDController shooterSpeedController = new PIDController(-0.005, 0.000, 0.0,
            shooterSpeedTach, shooterMotor);
    Jaguar shooterAngleMotor = new Jaguar(RobotMap.SHOOTER_ANGLE_MOTOR);
    Potentiometer shooterAnglePot = new Potentiometer(RobotMap.SHOOTER_ANGLE_POT);
    PIDController shooterAngleController = new PIDController(22.5, 0.0, 0.0,
            shooterAnglePot, shooterAngleMotor);
    Solenoid shooterSolenoid = new Solenoid(RobotMap.SHOOTER_SOLENOID);
    
    // Climbing
    Solenoid climbingSolenoid = new Solenoid(RobotMap.CLIMBING_SOLENOID);
    
    // Compressor
    Compressor compressor = new Compressor(RobotMap.COMPRESSOR_PRESSURE_SWITCH,
            RobotMap.COMPRESSOR_RELAY);
    
    // Robot preferences
    Preferences preferences = Preferences.getInstance();
    
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        // Configure drivetrain
        drivetrain.setSafetyEnabled(false);
        drivetrain.setExpiration(0.2);
        
        // Configure sensors
        leftDriveEncoder.start();
        rightDriveEncoder.start();
        
        shooterSpeedTach.start();
        
        // Start the compressor
        compressor.start();
        
        // Setup LiveWindow
        
        
        System.out.println("robotInit() Done!");
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {

    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
    
}
