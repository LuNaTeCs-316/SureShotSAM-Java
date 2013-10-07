package org.lunatecs316.frc2013.subsystems;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import org.lunatecs316.frc2013.RobotMap;

/**
 * Pickup Subsystem
 * @author domenicpaul
 */
public class Pickup extends Subsystem {

    // <editor-fold defaultstate="collapsed" desc="BeltState 'Enum'">
    /* Belt State "Enum" */
    public static class BeltState {

        public static final BeltState Forwards = new BeltState("Forwards");
        public static final BeltState Reverse = new BeltState("Reverse");
        public static final BeltState Off = new BeltState("Off");

        private String name;    // String representation of the state

        /* Private to prevent creation of new BeltStates */
        private BeltState(String name) {
            this.name = name;
        }

        /**
         * Convert the value to a string
         * @return name
         */
        public String toString() {
            return name;
        }
    }
    // </editor-fold>

    // <editor-fold desc="Subsystem Components">
    // Place Subsystem Components in this section

    /* Motors */
    private Jaguar angleMotor = new Jaguar(RobotMap.PICKUP_ANGLE_MOTOR);
    private Victor beltMotor = new Victor(RobotMap.PICKUP_BELT_MOTOR);

    /* Sensors */
    //private Potentiometer anglePot = new Potentiometer(RobotMap.PICKUP_ANGLE_POT);

    /* PID Controller */
    //private PIDController angleController = new PIDController(-5.0, -0.1,
    //        0.0, anglePot, angleMotor);
    // </editor-fold>

    // <editor-fold desc="Subsystem Data" defaultstate="collapsed">
    // Place Subsystem Data in this section

    // </editor-fold>

    // <editor-fold desc="Subsystem Methods">
    // Place Subsystem behavior in this section

    /**
     * Initialize the pickup subsystem
     */
    public void init() {
        LiveWindow.addActuator("Pickup", "BeltMotor", beltMotor);
        LiveWindow.addActuator("Pickup", "AngleMotor", angleMotor);
        //LiveWindow.addSensor("Pickup", "AnglePot", anglePot);
        //LiveWindow.addActuator("Pickup", "AngleController", angleController);
    }

    /**
     * Set the state of the pickup belts
     * @param state belt state (Forwards, Reverse, or Off)
     */
    public void setBeltState(BeltState state) {
        if (state == BeltState.Forwards) {
            beltMotor.set(1.0);
        } else if (state == BeltState.Reverse) {
            beltMotor.set(-1.0);
        } else if (state == BeltState.Off) {
            beltMotor.set(0.0);
        } else {
            System.err.println("Error: invalid belt state");
        }
    }

    /**
     * Raise the pickup
     */
    public void raise() {
        angleMotor.set(1.0);
    }

    /**
     * Lower the pickup
     */
    public void lower() {
        angleMotor.set(-1.0);
    }

    /**
     * Stop movement of the pickup
     */
    public void stop() {
        angleMotor.set(0.0);
    }
    // </editor-fold>
}
