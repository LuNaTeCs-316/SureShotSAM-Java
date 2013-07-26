/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.lunatecs316.frc2013;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import org.lunatecs316.frc2013.auto.*;
import org.lunatecs316.frc2013.commands.CommandBase;

/**
 * Main Robot class.
 * 
 * WPILib note:
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 * 
 * @author domenicpaul
 */
public class SureShotSAM extends IterativeRobot {
    
    /* DriverStation */
    private DriverStation driverStation = DriverStation.getInstance();
    
    /* Autonomous Mode */
    private Command autoMode;
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {

        // Initalize all subsystems
        CommandBase.init();

        // Start the compressor
        CommandBase.compressor.start();
      
        Debugger.log("robotInit() Done!");

        // Ouput debugging info
        Debugger.run("RobotInit");
    }

    /**
     * This function is called at the start of autonomous mode
     */
    public void autonomousInit() {
        // Update our current auto mode
        switch ((int) driverStation.getAnalogIn(1)) {
            default:
            case 0:
                autoMode = new DoNothingAuto();
                break;
            case 1:
                autoMode = new ThreeDiskAuto();
                break;
            case 2:
                autoMode = new FiveDiskCenterAuto();
                break;
            case 5:
                autoMode = new KinectAuto();
                break;
        }

        // Ouput debugging info
        Debugger.run("AutoInit");
    }
    
    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();

        // Ouput debugging info
        Debugger.run("AutoPeriodic");
    }

    /**
     * This function is called at the start of the teleop period
     */
    public void teleopInit() {
        // Make sure the autonomous program has stopped
        autoMode.cancel();

        // Start the compresser incase it was stopped during auto
        CommandBase.compressor.start();

        // Ouput debugging info
        Debugger.run("TeleopInit");

    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();

        // Ouput debugging info
        Debugger.run("TeleopPeriodic");
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        // Run LiveWindow
        LiveWindow.run();

        // Ouput debugging info
        Debugger.run("TestPeriodic");
    }
    
    /**
     * This function is called once at the start of disabled mode
     */
    public void disabledInit() {
        // Set robot subsystems to default
        CommandBase.drivetrain.arcadeDrive(0, 0);
        CommandBase.pickupBelts.disable();
        CommandBase.pickupArm.stop();
        CommandBase.shooter.disable();
        CommandBase.climber.lowerHooks();
        
        // Ouput debugging info
        Debugger.run("DisabledInit");
    }
    
    public void disabledPeriodic() {
        // Ouput debugging info
        Debugger.run("DisabledPeriodic");
    }
}
