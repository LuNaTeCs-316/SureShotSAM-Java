package org.lunatecs316.frc2013.commands;

import org.lunatecs316.frc2013.Constants;

/**
 * Automatically operate the shooter
 * @author domenicpaul
 */
public class AutoFire extends CommandBase {
    
    public AutoFire() {
        // Use requires() here to declare subsystem dependencies
        requires(shooter);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        shooter.setSpeed(-1.0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        shooter.fire(shooter.getSpeed() > Constants.kShooterMinFiringSpeed.getValue());
        shooter.indications();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        shooter.disable();
        shooter.fire(false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}