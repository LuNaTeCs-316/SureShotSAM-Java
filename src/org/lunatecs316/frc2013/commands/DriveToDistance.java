package org.lunatecs316.frc2013.commands;

import org.lunatecs316.frc2013.Constants;
import org.lunatecs316.frc2013.lib.SimplePIDController;
import org.lunatecs316.frc2013.lib.Util;

/**
 * Drive the robot straight the specified distance
 * @author domenicpaul
 */
public class DriveToDistance extends CommandBase {
    
    private double target;
    private double startAngle;
    private boolean finished = false;
    private SimplePIDController distanceController =
            new SimplePIDController(Constants.kDriveEncoderP.getValue(),
                                    Constants.kDriveEncoderI.getValue(),
                                    Constants.kDriveEncoderD.getValue());
    
    private SimplePIDController angleController =
            new SimplePIDController(Constants.kDriveGyroP.getValue(),
                                    Constants.kDriveGyroI.getValue(),
                                    Constants.kDriveGyroD.getValue());
    
    public DriveToDistance(double inches) {
        // Use requires() here to declare subsystem dependencies
        requires(drivetrain);
        
        // Calculate the target encoder tick value
        target = (inches * Constants.kDriveEncoderTicksPerRot.getValue())
                   / (Constants.kDriveWheelDiameter.getValue() * Math.PI);
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
        
        // Check to see if we are done
        if (Util.deadband(output, 0.05) == 0) {
            finished = true;
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return finished;
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