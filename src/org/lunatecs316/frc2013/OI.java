package org.lunatecs316.frc2013;

import edu.wpi.first.wpilibj.Joystick;
import org.lunatecs316.frc2013.lib.Util;
import org.lunatecs316.frc2013.lib.XboxController;
import org.lunatecs316.frc2013.subsystems.*;

/**
 * The OI (Operator Interface) manages control of the robot during teleop
 * @author domenicpaul
 */
public class OI extends Subsystems {

    /* Joysticks */
    private XboxController driverController;
    private Joystick operatorJoystick;

    public XboxController getDriverController() {
        return driverController;
    }

    public Joystick getOperatorJoystick() {
        return operatorJoystick;
    }

    /**
     * Initialize the Operator Interface
     */
    public void init() {
        super.init();
        driverController = new XboxController(1);
        operatorJoystick = new Joystick(2);
    }

    /**
     * Run one iteration of teleop mode
     */
    public void run() {
        //
        // Drivetrain
        //
        
        // Driving
        if (driverController.getButtonA()) {
            drivetrain.driveStraight(48);
        } else if (driverController.getButtonB()) {
            drivetrain.driveStraight(-108);
        } else if (driverController.getButtonX()) {
            drivetrain.turn(90);
        } else {
            double throttle = -driverController.getLeftY();
            double turn = driverController.getRightX();

            throttle = Util.deadband(throttle, Constants.JoystickDeadband.getValue());
            turn = Util.deadband(turn, Constants.JoystickDeadband.getValue());
            drivetrain.arcadeDrive(throttle, turn);
        }

        // Gyro reset
        if (driverController.getButtonY()) {
            drivetrain.resetGyro();
        }

        //
        // Pickup
        //

        // Angle control
        if (driverController.getLeftBumper()) {
            pickup.raise();
        } else if (driverController.getRightBumper()) {
            pickup.lower();
        } else {
            pickup.stop();
        }

        // Belt control
        if (operatorJoystick.getRawButton(6)) {
            pickup.setBeltState(Pickup.BeltState.Reverse);
        } else if (operatorJoystick.getRawButton(7)) {
            pickup.setBeltState(Pickup.BeltState.Forwards);
        } else {
            pickup.setBeltState(Pickup.BeltState.Off);
        }

        //
        // Shooter
        //

        // Angle control
        double shooterMoveValue = Util.deadband(operatorJoystick.getY(), Constants.JoystickDeadband.getValue());
        if (operatorJoystick.getRawButton(11)) {
            shooter.moveToTopPosition();
        } else if (operatorJoystick.getRawButton(10)) {
            shooter.moveToPosition(Constants.ShooterMidPosition.getValue());
        } else if (operatorJoystick.getRawButton(8)) {
            shooter.moveToLoadPosition();
        } else {
            shooter.move(shooterMoveValue);
        }

        // Motor control
        if (operatorJoystick.getRawButton(2)) {
            shooter.enable();
        } else if (operatorJoystick.getRawButton(5)) {
            shooter.setSpeed(-1.0);
        } else if (operatorJoystick.getRawButton(1)) {
            shooter.autoFire();
        } else {
            shooter.disable();
        }

        // Firing control
        if (operatorJoystick.getRawButton(2) || operatorJoystick.getRawButton(5))
            shooter.fire(operatorJoystick.getRawButton(1));
        else if (!operatorJoystick.getRawButton(1))
            shooter.fire(false);

        // Indicator lights
        shooter.indications();

        //
        // Climbing
        //
        if (operatorJoystick.getRawButton(4))
            climber.extendHooks();
        else
            climber.retractHooks();
    }
}
