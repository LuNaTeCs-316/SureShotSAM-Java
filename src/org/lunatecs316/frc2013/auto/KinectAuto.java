/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lunatecs316.frc2013.auto;

import edu.wpi.first.wpilibj.KinectStick;
import org.lunatecs316.frc2013.subsystems.*;

/**
 *
 * @author Domenic
 */
public class KinectAuto implements AutonomousMode {

    private KinectStick leftStick;
    private KinectStick rightStick;
    
    public void init() {
        leftStick = new KinectStick(1);
        rightStick = new KinectStick(2);
    }

    public void run() {
        Drivetrain.tankDrive(leftStick.getY(), rightStick.getY());
    }
    
}
