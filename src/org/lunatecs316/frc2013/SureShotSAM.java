/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.lunatecs316.frc2013;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.CommandGroup;
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
    private CommandGroup autoMode;
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        // Start the compressor
        CommandBase.compressor.start();
        
        // Initalize all subsystems
        CommandBase.init();
      
        // Print for debugging purposes
        Debugger.log("robotInit() Done!");

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
                //autoMode = new ThreeDiskAuto();
                break;
            case 2:
                //autoMode = new FiveDiskAuto();
                break;
            case 4:
                //autoMode = new TestAuto();
                break;
            case 5:
                //autoMode = new KinectAuto();
                break;
        }

        Debugger.run("AutoInit");
    }
    
    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();

        Debugger.run("AutoPeriodic");
    }
    
    /**
     * This function is called at the start of the teleop period
     */
    public void teleopInit() {
        autoMode.cancel();
    }
    
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();

        Debugger.run("TeleopPeriodic");
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        // Run LiveWindow
        LiveWindow.run();

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
        
        Debugger.run("DisabledInit");
    }
    
    public void disabledPeriodic() {
        // Reset gyro
        /*if (driverController.getRawButton(4)) {
            Drivetrain.resetGyro();
        }*/

        Debugger.run("DisabledPeriodic");
    }
}
