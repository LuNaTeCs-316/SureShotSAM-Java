/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lunatecs316.frc2013.commands;

import org.lunatecs316.frc2013.lib.SimplePIDController;
import org.lunatecs316.frc2013.subsystems.Drivetrain;

/**
 * Drive the robot straight the specified distance
 * @author domenicpaul
 */
public class DriveToDistance extends CommandBase {
    
    private double target;
    private double startAngle;
    private SimplePIDController distanceController = new SimplePIDController(0.0004, 0.00000, 0.00005, 0.05);
    private SimplePIDController angleController = new SimplePIDController(0, 0, 0);
    
    public DriveToDistance(double inches) {
        // Use requires() here to declare subsystem dependencies
        requires(drivetrain);
        
        // Calculate the target encoder tick value
        target = (inches * Drivetrain.kEncoderTicksPerRot)
                                    / Drivetrain.kDistancePerRotation;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        drivetrain.resetEncoders();
        startAngle = drivetrain.getGyroAngle();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        double output = distanceController.run(target, drivetrain.getAverageEncoderCount());
        double turn = angleController.run(startAngle, drivetrain.getGyroAngle());
        drivetrain.arcadeDrive(output, turn);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return distanceController.atTarget();
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