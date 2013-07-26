package org.lunatecs316.frc2013.commands;

/**
 * Enable the shooter motor
 * @author domenicpaul
 */
public class EnableShooter extends CommandBase {
    
    private boolean pidControl;
    
    public EnableShooter(boolean pidControl) {
        // Use requires() here to declare subsystem dependencies
        requires(shooter);
        this.pidControl = pidControl;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        if (pidControl) {
            shooter.enable();
        } else {
            shooter.setSpeed(-1.0);
        }
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