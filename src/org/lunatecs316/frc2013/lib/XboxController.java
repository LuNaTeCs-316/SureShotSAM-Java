package org.lunatecs316.frc2013.lib;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Wrapper class for the Xbox Controller
 * @author domenicpaul
 */
public class XboxController extends Joystick {

    public XboxController(final int port) {
        super(port);
    }

    public double getLeftX() {
        return getX();
    }

    public double getLeftY() {
        return getY();
    }

    public double getRightX() {
        return getRawAxis(4);
    }

    public double getRightY() {
        return getRawAxis(5);
    }

    public boolean getButtonA() {
        return getRawButton(1);
    }

    public boolean getButtonB() {
        return getRawButton(2);
    }

    public boolean getButtonX() {
        return getRawButton(3);
    }

    public boolean getButtonY() {
        return getRawButton(4);
    }

    public boolean getLeftBumper() {
        return getRawButton(5);
    }

    public boolean getRightBumper() {
        return getRawButton(6);
    }
}
