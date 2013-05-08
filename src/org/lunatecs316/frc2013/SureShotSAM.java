/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.lunatecs316.frc2013;

import org.lunatecs316.frc2013.subsystems.*;

import org.lunatecs316.frc2013.lib.Util;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.Preferences;

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
    
    // Compressor
    private Compressor compressor = new Compressor(RobotMap.COMPRESSOR_PRESSURE_SWITCH,
            RobotMap.COMPRESSOR_RELAY);
    
    // Robot preferences
    private Preferences preferences = Preferences.getInstance();
    
    private int autoMode = 1;
    private int autoStep = 1;
    private long autoStartTime = 0;
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        // Initialize subsystems
        Drivetrain.init();
        Pickup.init();
        Shooter.init();
        Climber.init();
        
        // Start the compressor
        compressor.start();
      
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
            case 2:
                // Fire the first shot
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
            Shooter.moveToPosition(Shooter.Position.Top);
        } else if (operatorJoystick.getRawButton(10)) {
            Shooter.moveToPosition(Shooter.Position.Mid);
        } else if (operatorJoystick.getRawButton(8)) {
            Shooter.moveToPosition(Shooter.Position.Load);
        } else {
            Shooter.move(Util.deadband(operatorJoystick.getY(), 0.2));
        }
        
        // Motor control
        if (operatorJoystick.getRawButton(2)) {
            Shooter.enable();
        } else {
            Shooter.disable();
        }
        
        // Firing control
        Shooter.fire(operatorJoystick.getRawButton(1));
        
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
