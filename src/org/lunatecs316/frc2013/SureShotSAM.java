/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.lunatecs316.frc2013;

import org.lunatecs316.frc2013.subsystems.*;

import org.lunatecs316.frc2013.lib.LuNaDrive;
import org.lunatecs316.frc2013.lib.Potentiometer;
import org.lunatecs316.frc2013.lib.Tachometer;
import org.lunatecs316.frc2013.lib.Util;

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
    private DriverStation driverStation = DriverStation.getInstance();
    private Joystick driverController = new Joystick(RobotMap.DRIVER_JOYSTICK);
    private Joystick operatorJoystick = new Joystick(RobotMap.OPERATOR_JOYSTICK);
    
    // Shooter
    private Victor shooterMotor = new Victor(RobotMap.SHOOTER_MOTOR);
    private Tachometer shooterSpeedTach = new Tachometer(RobotMap.SHOOTER_SPEED_TACH);
    private PIDController shooterSpeedController = new PIDController(-0.005, 0.000, 0.0,
            shooterSpeedTach, shooterMotor);
    private Jaguar shooterAngleMotor = new Jaguar(RobotMap.SHOOTER_ANGLE_MOTOR);
    private Potentiometer shooterAnglePot = new Potentiometer(RobotMap.SHOOTER_ANGLE_POT);
    private PIDController shooterAngleController = new PIDController(22.5, 0.0, 0.0,
            shooterAnglePot, shooterAngleMotor);
    private Solenoid shooterSolenoid = new Solenoid(RobotMap.SHOOTER_SOLENOID);
    
    // Climbing
    private Solenoid climbingSolenoid = new Solenoid(RobotMap.CLIMBING_SOLENOID);
    
    // Compressor
    private Compressor compressor = new Compressor(RobotMap.COMPRESSOR_PRESSURE_SWITCH,
            RobotMap.COMPRESSOR_RELAY);
    
    // Robot preferences
    private Preferences preferences = Preferences.getInstance();
    
    // Variables
    private static final double SHOOTER_TOP_POSITION = 3.41;
    private static final double SHOOTER_MID_POSITION = SHOOTER_TOP_POSITION - 0.1;
    private static final double SHOOTER_LOAD_POSITION = 2.0;
    
    private int autoMode = 1;
    private int autoStep = 1;
    private long autoStartTime = 0;
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        Drivetrain.init();
        Pickup.init();
        Shooter.init();
        Climber.init();
        
        // Configure sensors
        
        
        shooterSpeedTach.start();
        
        // Start the compressor
        compressor.start();
        
        // Setup LiveWindow
        
        LiveWindow.addActuator("Shooter", "Motor", shooterMotor);
        LiveWindow.addActuator("Shooter", "SpeedController", shooterSpeedController);
        LiveWindow.addActuator("Shooter", "AngleMotor", shooterAngleMotor);
        LiveWindow.addActuator("Shooter", "AngleController", shooterAngleController);
        LiveWindow.addActuator("Shooter", "Solenoid", shooterSolenoid);
        LiveWindow.addSensor("Shooter", "AnglePot", shooterAnglePot);
        LiveWindow.addSensor("Shooter", "SpeedTach", shooterSpeedTach);
      
        System.out.println("robotInit() Done!");
    }

    /**
     * This function is called at the start of autonomous mode
     */
    public void autonomousInit() {
        autoMode = (int) driverStation.getAnalogIn(1);
        autoStep = 1;
        autoStartTime = System.currentTimeMillis();
    }
    
    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        switch (autoMode) {
            case 1:
                autoMode1();
                break;
            default:
                System.err.println("Error: invalid autonomous mode");
                break;
        }
    }
    
    private void autoMode1() {
        System.out.print("[Mode:" + autoMode + "][Step:" + autoStep + "][Time:"
                + System.currentTimeMillis() + "] ");
        switch(autoStep) {
            case 1:
                // Start the shooter motor
                shooterSpeedController.setSetpoint(4500);
                shooterSpeedController.enable();
                
                if (shooterSpeedController.getError() < 300) {
                    autoStartTime = System.currentTimeMillis();
                    autoStep++;
                }
                break;
            case 2:
                // Fire the first shot
                shooterSolenoid.set(true);
                
                if (System.currentTimeMillis() - autoStartTime >= 300) {
                    autoStartTime = System.currentTimeMillis();
                    autoStep++;
                }
                break;
            default:
                break;
        }
        System.out.println();
    }
    
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        /**********************************************************************
         * Drivetrain
         **********************************************************************/
        
        Drivetrain.arcadeDrive(driverController);
        
        /**********************************************************************
         * Pickup
         **********************************************************************/
        
        // Angle control
        if (driverController.getRawButton(5)) {
            Pickup.raise();
        } else if (driverController.getRawButton(6)) {
            Pickup.lower();
        } else {
            Pickup.stop();
        }
        
        // Belt control
        if (operatorJoystick.getRawButton(6)) {
            Pickup.setBeltState(Pickup.kBeltReverse);
        } else if (operatorJoystick.getRawButton(7)) {
            Pickup.setBeltState(Pickup.kBeltForwards);
        } else {
            Pickup.setBeltState(Pickup.kBeltOff);
        }
        
        /**********************************************************************
         * Shooter
         **********************************************************************/
        
        // Angle control
        if (operatorJoystick.getRawButton(11)) {
            shooterAngleController.setSetpoint(SHOOTER_TOP_POSITION);
            shooterAngleController.enable();
        } else if (operatorJoystick.getRawButton(10)) {
            shooterAngleController.setSetpoint(SHOOTER_MID_POSITION);
            shooterAngleController.enable();
        } else if (operatorJoystick.getRawButton(8)) {
            shooterAngleController.setSetpoint(SHOOTER_LOAD_POSITION);
            shooterAngleController.enable();
        } else {
            shooterAngleController.disable();
            shooterAngleMotor.set(Util.deadband(operatorJoystick.getY(), 0.2));
        }
        
        // Motor control
        if (operatorJoystick.getRawButton(2)) {
            shooterSpeedController.setSetpoint(3750);
            shooterSpeedController.enable();
        } else if (operatorJoystick.getRawButton(5)) {
            shooterSpeedController.disable();
            shooterMotor.set(0.5);
        } else {
            shooterSpeedController.disable();
        }
        
        // Firing control
        shooterSolenoid.set(operatorJoystick.getRawButton(1));
        
        /**********************************************************************
         * Climbing
         **********************************************************************/
        
        climbingSolenoid.set(operatorJoystick.getRawButton(4));
        
        /**********************************************************************
         * Misc.
         **********************************************************************/
        
        // Shooter Light
        
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }    
}
