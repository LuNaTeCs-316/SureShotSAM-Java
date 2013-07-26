/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lunatecs316.frc2013.commands;

/**
 *
 * @author domenicpaul
 */
public class WaitForShooterSpeed extends CommandBase {
    
    public WaitForShooterSpeed(double timeout) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(shooter);
        setTimeout(timeout);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut() || shooter.atSpeed();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}