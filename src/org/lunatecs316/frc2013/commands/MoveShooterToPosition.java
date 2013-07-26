package org.lunatecs316.frc2013.commands;

/**
 * Move the shooter to the specified position
 * @author domenicpaul
 */
public class MoveShooterToPosition extends CommandBase {
    
    private double target;
    
    public MoveShooterToPosition(double target) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(shooter);
        this.target = target;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        shooter.moveToPosition(target);
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
    }
}