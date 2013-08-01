package org.lunatecs316.frc2013;

import edu.wpi.first.wpilibj.Preferences;
import java.util.Vector;
import org.lunatecs316.frc2013.commands.CommandBase;

/**
 * Robot constants
 * @author domenicpaul
 */
public class Constants {

    public static final Constant kJoystickDeadband = new Constant("JoystickDeadband", 0.2);

    public static final Constant kDriveEncoderTicksPerRot = new Constant("DriveEncoderTicksPerRot", 360.0);
    public static final Constant kDriveWheelDiameter = new Constant("DriveWheelDiameter", 6.0);
    public static final Constant kDriveWheelBaseWidth = new Constant("DriveWheelBaseWidth", 20.0);

    public static final Constant kDriveGyroP = new Constant("DriveGyroP", 0.0);
    public static final Constant kDriveGyroI = new Constant("DriveGyroI", 0.0);
    public static final Constant kDriveGyroD = new Constant("DriveGyroD", 0.0);

    public static final Constant kDriveEncoderP = new Constant("DriveEncoderP", 0.0004);
    public static final Constant kDriveEncoderI = new Constant("DriveEncoderI", 0.0);
    public static final Constant kDriveEncoderD = new Constant("DriveEncoderD", 0.00005);

    public static final Constant kPickupArmSpeed = new Constant("PickupArmSpeed", 1.0);
    public static final Constant kPickupBeltSpeed = new Constant("PickupBeltWpeed", 1.0);

    public static final Constant kShooterTopPosition = new Constant("ShooterTopPosition", 3.2);
    public static final Constant kShooterMidPosition = new Constant("ShooterMidPosition", 3.1);
    public static final Constant kShooterLoadPosition = new Constant("ShooterLoadPosition", 1.85);

    public static final Constant kShooterTargetSpeed = new Constant("ShooterTargetSpeed", 3975.0);
    public static final Constant kShooterMinFiringSpeed = new Constant("ShooterMinFiringSpeed", 3600.0);

    public static final Constant kShooterSpeedP = new  Constant("ShooterSpeedP", -0.005);
    public static final Constant kShooterSpeedI = new  Constant("ShooterSpeedI", 0.0);
    public static final Constant kShooterSpeedD = new  Constant("ShooterSpeedD", 0.0);

    public static final Constant kShooterAngleP = new  Constant("ShooterAngleP", 21.5);
    public static final Constant kShooterAngleI = new  Constant("ShooterAngleI", 0.0);
    public static final Constant kShooterAngleD = new  Constant("ShooterAngleD", 0.0);

    private static Vector constants;
    private static Preferences prefs = Preferences.getInstance();

    public static void init() {
        update();
    }

    public static class Constant {
        private String m_name;
        private double m_value;

        private Constant(String name, double value) {
            m_name = name;
            m_value = value;
            constants.addElement(this);
        }

        public void setValue(double value) {
            m_value = value;
        }

        public double getValue() {
            return m_value;
        }

        public String getName() {
            return m_name;
        }
    }

    /**
     * Read the latest constants from the Dashboard
     */
    public static void update() {
        System.out.println("Updating robot constants");

        for (int i = 0; i < constants.size(); i++) {    // Loop through all constants
            // Get the current constant
            Constant constant = (Constant) constants.elementAt(i);

            String key = constant.getName();
            double oldValue = constant.getValue();

            // Check if the constant is present on SmartDashboard
            if (prefs.containsKey(key)) {
                // Update the value, use the old one if we fail
                constant.setValue(prefs.getDouble(key, oldValue));
            } else {
                System.out.println("Key '" + key + "' does not exist; creating");
                prefs.putDouble(key, oldValue);
            }
        }

        // Update constants in subsystems
        CommandBase.updateConstants();

        // Save the updated constants to disk
        System.out.println("Saving updated constants to disk");
        prefs.save();
    }
}
