package org.lunatecs316.frc2013.commands;

import org.lunatecs316.frc2013.lib.Util;

/**
 * Manual control of the shooter via joystick
 * @author domenicpaul
 */
public class MoveShooterWithJoystick extends CommandBase {
    
    public MoveShooterWithJoystick() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(shooter);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        shooter.move(Util.deadband(oi.getOperatorJoystick().getY(), 0.2));
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
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