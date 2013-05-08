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
    // DS/Joysticks
    private DriverStation driverStation = DriverStation.getInstance();
    
    // Compressor
    private Compressor compressor = new Compressor(RobotMap.COMPRESSOR_PRESSURE_SWITCH,
            RobotMap.COMPRESSOR_RELAY);
    
    // Autonomous data
    private AutonomousMode autoMode;
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        // Initialize subsystems
        OI.init();
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
        autoMode.init();
    }
    
    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        autoMode.run();
    }
    
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        OI.runDrivetrain();
        OI.runPickup();
        OI.runShooter();
        OI.runClimber();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
    
    public void disabledPeriodic() {
        // Update our current auto mode
        switch ((int) driverStation.getAnalogIn(1)) {
            default:
            case 0:
                autoMode = new DoNothingAuto();
                break;
            case 1:
                if (autoMode.getClass() != ThreeDiskAuto.class) {
                    autoMode = new ThreeDiskAuto();
                }
                break;
        }
    }
}
