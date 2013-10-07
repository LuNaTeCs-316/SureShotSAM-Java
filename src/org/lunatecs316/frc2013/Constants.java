package org.lunatecs316.frc2013;

import edu.wpi.first.wpilibj.Preferences;
import java.util.Vector;

/**
 * Robot constants manager
 *
 * @author domenicpaul
 */
public class Constants {

    static {
        constants = new Vector();
    }

    public static final Constant JoystickDeadband = new Constant("JoystickDeadband", 0.2);

    public static final Constant DriveEncoderTicksPerRot = new Constant("DriveEncoderTicksPerRot", 360.0);
    public static final Constant DriveWheelDiameter = new Constant("DriveWheelDiameter", 6.0);
    public static final Constant DriveWheelBaseWidth = new Constant("DriveWheelBaseWidth", 20.0);

    public static final Constant DriveGyroP = new Constant("DriveGyroP", 0.0);
    public static final Constant DriveGyroI = new Constant("DriveGyroI", 0.0);
    public static final Constant DriveGyroD = new Constant("DriveGyroD", 0.0);

    public static final Constant DriveEncoderP = new Constant("DriveEncoderP", 0.0004);
    public static final Constant DriveEncoderI = new Constant("DriveEncoderI", 0.0);
    public static final Constant DriveEncoderD = new Constant("DriveEncoderD", 0.00005);

    public static final Constant PickupArmSpeed = new Constant("PickupArmSpeed", 1.0);
    public static final Constant PickupBeltSpeed = new Constant("PickupBeltSpeed", 1.0);

    public static final Constant ShooterTopPosition = new Constant("ShooterTopPosition", 3.2);
    public static final Constant ShooterMidPosition = new Constant("ShooterMidPosition", 3.1);
    public static final Constant ShooterLoadPosition = new Constant("ShooterLoadPosition", 1.85);

    public static final Constant ShooterTargetSpeed = new Constant("ShooterTargetSpeed", 3975.0);
    public static final Constant ShooterMinFiringSpeed = new Constant("ShooterMinFiringSpeed", 3600.0);
    public static final Constant ShooterLightBlinkSpeed = new Constant("ShooterLightBlinkSpeed", 20);

    public static final Constant ShooterSpeedP = new Constant("ShooterSpeedP", -0.005);
    public static final Constant ShooterSpeedI = new Constant("ShooterSpeedI", 0.0);
    public static final Constant ShooterSpeedD = new Constant("ShooterSpeedD", 0.0);

    public static final Constant ShooterAngleP = new Constant("ShooterAngleP", 21.5);
    public static final Constant ShooterAngleI = new Constant("ShooterAngleI", 0.0);
    public static final Constant ShooterAngleD = new Constant("ShooterAngleD", 0.0);

    public static final Constant DashboardUpdateFrequency = new Constant("DashboardUpdateFrequency", 10);

    public static Vector constants;
    private static Preferences prefs = Preferences.getInstance();

    public static class Constant {
        private String m_name;
        private double m_value;

        private Constant(String name, double value) {
            m_name = name;
            m_value = value;

            Constants.constants.addElement(this);
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
     * Setup the constant manager for usage in the program
     */
    public static void init() {
       update();
    }

    /**
     * Read the latest constants from the Dashboard
     */
    public static void update() {
        System.out.println("Updating robot constants");

        boolean modified = false;
        for (int i = 0; i < constants.size(); i++) {    // Loop through all constants
            // Get the current constant
            Constant constant = (Constant) constants.elementAt(i);

            String key = constant.getName();
            double oldValue = constant.getValue();

            // Check if the constant is present on SmartDashboard
            if (prefs.containsKey(key)) {
                // Check to see if the value has changed before updating
                double newValue = prefs.getDouble(key, oldValue);
                if (oldValue != newValue) {
                    // Update the value, use the old one if we fail
                    constant.setValue(prefs.getDouble(key, oldValue));
                    modified = true;
                }
            } else {
                System.out.println("Key '" + key + "' does not exist; creating");
                prefs.putDouble(key, oldValue);
            }
        }

        // Save the updated constants to disk
        if (modified) {
            System.out.println("Saving updated constants to disk");
            prefs.save();
        }
    }
}
