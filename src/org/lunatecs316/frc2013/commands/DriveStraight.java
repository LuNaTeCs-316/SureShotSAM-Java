package org.lunatecs316.frc2013.commands;

import org.lunatecs316.frc2013.lib.SimplePIDController;

/**
 * Drive the robot in a straight line
 * @author domenicpaul
 */
public class DriveStraight extends CommandBase {
    
    private double power;
    private double startAngle;
    private SimplePIDController pid = new SimplePIDController(0, 0, 0);
    
    public DriveStraight(double power) {
        // Use requires() here to declare subsystem dependencies
        requires(drivetrain);
        this.power = power;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        startAngle = drivetrain.getGyroAngle();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        double turn = pid.run(startAngle, drivetrain.getGyroAngle());
        drivetrain.arcadeDrive(power, turn);
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