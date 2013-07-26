package org.lunatecs316.frc2013.commands;

/**
 * Wait command
 * @author domenicpaul
 */
public class Wait extends CommandBase {
    
    public Wait(double time) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        setTimeout(time);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}