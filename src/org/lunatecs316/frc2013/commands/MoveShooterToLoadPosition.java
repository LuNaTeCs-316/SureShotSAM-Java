package org.lunatecs316.frc2013.commands;

import org.lunatecs316.frc2013.Constants;

/**
 * Move the shooter to the top position
 * @author domenicpaul
 */
public class MoveShooterToLoadPosition extends CommandBase {
    
    public MoveShooterToLoadPosition() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(shooter);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        shooter.moveToPosition(Constants.kShooterLoadPosition.getValue());
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return shooter.atPosition();
    }

    // Called once after isFinished returns true
    protected void end() {
        shooter.move(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}