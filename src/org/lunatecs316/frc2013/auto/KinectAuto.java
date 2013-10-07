/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lunatecs316.frc2013.auto;

import edu.wpi.first.wpilibj.KinectStick;
import org.lunatecs316.frc2013.Logger;
import org.lunatecs316.frc2013.subsystems.*;

/**
 * Drive the robot around using the Kinect. Impress ALL the peoples!
 * @author domenicpaul
 */
public class KinectAuto extends AutonomousMode {

    private KinectStick leftStick;
    private KinectStick rightStick;

    public void init() {
        name = "KinectAuto";
        leftStick = new KinectStick(1);
        rightStick = new KinectStick(2);
    }

    public void run() {
        // Look, no hands
        double left = leftStick.getY();
        double right = rightStick.getY();
        Subsystems.drivetrain.tankDrive(left, right);
        Logger.log("left", left);
        Logger.log("right", right);
    }

}
