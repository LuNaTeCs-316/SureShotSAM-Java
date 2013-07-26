package org.lunatecs316.frc2013.auto;

import edu.wpi.first.wpilibj.KinectStick;
import org.lunatecs316.frc2013.commands.CommandBase;

/**
 * Drive the robot around using the Kinect. Impress ALL the peoples!
 * @author domenicpaul
 */
public class KinectAuto extends CommandBase {

    private KinectStick leftStick = new KinectStick(1);
    private KinectStick rightStick = new KinectStick(2);

    protected void initialize() {
        requires(drivetrain);
    }

    protected void execute() {
        drivetrain.tankDrive(leftStick.getY(), rightStick.getY());
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        drivetrain.arcadeDrive(0, 0);
    }

    protected void interrupted() {
    }
    
}
