package org.lunatecs316.frc2013.commands;

/**
 * Fire a single shot
 * @author domenicpaul
 */
public class Shoot extends CommandBase {
    
    public Shoot() {
        // Use requires() here to declare subsystem dependencies
        requires(shooter);
        setTimeout(0.2);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        shooter.fire(true);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        shooter.indications();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
        shooter.fire(false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}