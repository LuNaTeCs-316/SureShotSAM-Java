/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lunatecs316.frc2013.commands;

import edu.wpi.first.wpilibj.Joystick;
import org.lunatecs316.frc2013.lib.Util;

/**
 * Human control of the robot via the Xbox controller.
 * @author domenicpaul
 */
public class DriveWithXboxController extends CommandBase {
    
    public DriveWithXboxController() {
        // Use requires() here to declare subsystem dependencies
        requires(drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        Joystick joy = oi.getDriverController();
        drivetrain.arcadeDrive(Util.deadband(-joy.getY(), 0.2),
                Util.deadband(joy.getRawAxis(4), 0.2));
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        drivetrain.arcadeDrive(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}