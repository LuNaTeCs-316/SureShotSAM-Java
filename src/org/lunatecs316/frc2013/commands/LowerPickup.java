package org.lunatecs316.frc2013.commands;

/**
 * Lower the pickup arm
 * @author domenicpaul
 */
public class LowerPickup extends CommandBase {
    
    public LowerPickup() {
        // Use requires() here to declare subsystem dependencies
        requires(pickupArm);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        pickupArm.lower();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}