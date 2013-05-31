/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.lunatecs316.frc2013;

import org.lunatecs316.frc2013.subsystems.*;
import org.lunatecs316.frc2013.auto.*;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class SureShotSAM extends IterativeRobot {
    
    /* DriverStation */
    private DriverStation driverStation = DriverStation.getInstance();
    private DriverStationLCD LCD = DriverStationLCD.getInstance();
    
    /* Compressor */
    private Compressor compressor = new Compressor(RobotMap.COMPRESSOR_PRESSURE_SWITCH,
            RobotMap.COMPRESSOR_RELAY);
    
    /* Autonomous Mode */
    private AutonomousMode autoMode;
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        // Call the OI and each subsystem's init method
        OI.init();
        Drivetrain.init();
        Pickup.init();
        Shooter.init();
        Climber.init();
        
        // Start the compressor
        compressor.start();
      
        // Print for debugging purposes
        System.out.println("robotInit() Done!");
    }

    /**
     * This function is called at the start of autonomous mode
     */
    public void autonomousInit() {
        
        // Call the autoMode's init method
        autoMode.init();
    }
    
    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        
        // Run an iteration of the autoMode
        autoMode.run();
    }
    
    /**
     * This function is called once at the start of the teleop period
     */
    public void teleopInit() {
    }
    
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        
        // Run each of the OI sections
        OI.runDrivetrain();
        OI.runPickup();
        OI.runShooter();
        OI.runClimber();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        
        // Run LiveWindow
        LiveWindow.run();
    }
    
    /**
     * This function is called once at the start of disabled mode
     */
    public void disabledInit() {
        // Stop all robot outputs
        Drivetrain.arcadeDrive(0, 0);
        Pickup.setBeltState(Pickup.BeltState.Off);
        Pickup.stop();
        Shooter.disable();
        Shooter.fire(false);
        Climber.climb(false);
    }
    
    /**
     * This function is called periodically while the robot is disabled
     */
    public void disabledPeriodic() {
        
        // Update our current auto mode
        switch ((int) driverStation.getAnalogIn(1)) {
            default:
            case 0:
                autoMode = new DoNothingAuto();
                break;
            case 1:
                if (!(autoMode instanceof ThreeDiskAuto)) {
                    autoMode = new ThreeDiskAuto();
                }
                break;
            case 5:
                if (!(autoMode instanceof KinectAuto)) {
                    autoMode = new KinectAuto();
                }
        }
    }
}
