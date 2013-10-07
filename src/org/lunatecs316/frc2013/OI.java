package org.lunatecs316.frc2013;

import edu.wpi.first.wpilibj.Joystick;
import org.lunatecs316.frc2013.lib.Util;
import org.lunatecs316.frc2013.subsystems.*;

/**
 * The OI (Operator Interface) manages control of the robot during teleop
 * @author domenicpaul
 */
public class OI {

    /* Joysticks */
    private static Joystick driverController;
    private static Joystick operatorJoystick;

    private static boolean firstPressDB1 = true;
    private static boolean firstPressDB2 = true;
    private static boolean firstPressDB3 = true;

    /**
     * Initialize the Operator Interface
     */
    public static void init(Joystick js1, Joystick js2) {
        driverController = js1;
        operatorJoystick = js2;
    }

    public static void run() {
        //
        // Drivetrain
        //
        if (driverController.getRawButton(1)) {
            if (firstPressDB1) {
                Drivetrain.setTargetDistance(48);
                firstPressDB1 = false;
            } else {
                Drivetrain.driveStraight();
            }
        } else if (driverController.getRawButton(2)) {
            if (firstPressDB2) {
                Drivetrain.setTargetDistance(24);
                firstPressDB2 = false;
            } else {
                Drivetrain.driveStraight();
            }
        } else if (driverController.getRawButton(3) || driverController.getRawButton(4)) {
            if (firstPressDB3) {
                if (driverController.getRawButton(3)) Drivetrain.setTargetAngle(90);
                if (driverController.getRawButton(4)) Drivetrain.setTargetAngle(-90);
                firstPressDB3 = false;
            } else {
                Drivetrain.turn();
            }
        } else {
            firstPressDB1 = true;
            firstPressDB2 = true;
            firstPressDB3 = true;
            Drivetrain.arcadeDrive(driverController);
        }

        if (driverController.getRawButton(4)) {
            Drivetrain.resetGyro();
        }

        //
        // Pickup
        //

        // Angle control
        if (driverController.getRawButton(5)) {
            Pickup.raise();
        } else if (driverController.getRawButton(6)) {
            Pickup.lower();
        } else {
            Pickup.stop();
        }

        // Belt control
        if (operatorJoystick.getRawButton(6)) {
            Pickup.setBeltState(Pickup.BeltState.Reverse);
        } else if (operatorJoystick.getRawButton(7)) {
            Pickup.setBeltState(Pickup.BeltState.Forwards);
        } else {
            Pickup.setBeltState(Pickup.BeltState.Off);
        }

        //
        // Shooter
        //

        // Angle control
        if (operatorJoystick.getRawButton(11)) {
            Shooter.moveToPosition(Shooter.kTopPosition);
        } else if (operatorJoystick.getRawButton(10)) {
            Shooter.moveToPosition(Shooter.kMidPosition);
        } else if (operatorJoystick.getRawButton(8)) {
            Shooter.moveToPosition(Shooter.kLoadPosition);
        } else {
            Shooter.move(Util.deadband(operatorJoystick.getY(), 0.2));
        }

        // Motor control
        if (operatorJoystick.getRawButton(2)) {
            Shooter.enable();
        } else if (operatorJoystick.getRawButton(5)) {
            Shooter.setSpeed(-1.0);
        } else if (operatorJoystick.getRawButton(1)) {
            Shooter.autoFire();
        } else {
            Shooter.disable();
        }

        // Firing control
        if (operatorJoystick.getRawButton(2) || operatorJoystick.getRawButton(5))
            Shooter.fire(operatorJoystick.getRawButton(1));
        else if (!operatorJoystick.getRawButton(1))
            Shooter.fire(false);

        // Indicator lights
        Shooter.indications();

        Debugger.log("shooterAngle", Shooter.getAngle());

        //
        // Climbing
        //
        Climber.climb(operatorJoystick.getRawButton(4));
    }
}
