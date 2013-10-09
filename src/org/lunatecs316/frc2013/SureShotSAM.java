/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.lunatecs316.frc2013;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import org.lunatecs316.frc2013.subsystems.*;
import org.lunatecs316.frc2013.auto.*;

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

    private DriverStation driverStation = DriverStation.getInstance();
    private Compressor compressor = new Compressor(RobotMap.COMPRESSOR_PRESSURE_SWITCH,
            RobotMap.COMPRESSOR_RELAY);

    private OI oi = new OI();

    /* Autonomous Mode */
    private AutonomousMode autoMode;

    /* Data */
    private int teleopLoopCount = 0;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        // Call the OI and each subsystem's init method
        oi.init();

        // Start the compressor
        compressor.start();

        // Print for debugging purposes
        Logger.log("robotInit() Done!");

        Logger.run("RobotInit");
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
                autoMode = new FiveDiskAutoDR();
                break;
            case 4:
                autoMode = new FiveDiskCenterlineAuto();
                break;
            case 5:
                autoMode = new KinectAuto();
                break;
        }

        // Call the autoMode's init method
        autoMode.init();

        Logger.run("AutoInit");
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        // Run an iteration of the autoMode
        autoMode.run();

        Logger.run("AutoPeriodic");
    }

    /**
     * This function is called at the start of the operator control period
     */
    public void teleopInit() {
        teleopLoopCount = 0;
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        // Run the OI
        oi.run();

        // Update SmartDashboard every so often
        if (teleopLoopCount >= Constants.DashboardUpdateFrequency.getValue()) {
            Subsystems.updateSmartDashboard();
        }

        Logger.run("TeleopPeriodic");
    }

    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        // Run LiveWindow
        LiveWindow.run();

        Logger.run("TestPeriodic");
    }

    /**
     * This function is called once at the start of disabled mode
     */
    public void disabledInit() {
        // Set robot subsystems to default
        Subsystems.drivetrain.arcadeDrive(0, 0);
        Subsystems.pickup.setBeltState(Pickup.BeltState.Off);
        Subsystems.pickup.stop();
        Subsystems.shooter.disable();
        Subsystems.shooter.fire(false);
        Subsystems.climber.retractHooks();

        Logger.run("DisabledInit");
    }

    public void disabledPeriodic() {
        // Reset gyro
        if (oi.getDriverController().getRawButton(4)) {
            Subsystems.drivetrain.resetGyro();
        }

        Logger.run("DisabledPeriodic");
    }
}
