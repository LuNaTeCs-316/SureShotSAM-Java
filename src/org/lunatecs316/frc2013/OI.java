package org.lunatecs316.frc2013;

import edu.wpi.first.wpilibj.Joystick;
import org.lunatecs316.frc2013.lib.Latch;
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

    private Latch buttonALatch = new Latch();
    private Latch buttonBLatch = new Latch();
    private Latch buttonXLatch = new Latch();

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
        boolean buttonAVal = driverController.getButtonA();
        boolean buttonBVal = driverController.getButtonB();
        boolean buttonXVal = driverController.getButtonX();

        // Driving
        if (buttonAVal || buttonBVal) {
            if (buttonALatch.risingEdge(buttonAVal))
                drivetrain.setTargetDistance(48);
            else if (buttonBLatch.risingEdge(buttonBVal))
                drivetrain.setTargetDistance(24);
            else
                drivetrain.driveStraight();
        } if (buttonXVal) {
            if (buttonXLatch.risingEdge(buttonXVal))
                drivetrain.setTargetAngle(90);
            else
                drivetrain.turn();
        } else {
            drivetrain.arcadeDrive(driverController);
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
        if (operatorJoystick.getRawButton(11)) {
            shooter.moveToPosition(Constants.ShooterTopPosition.getValue());
        } else if (operatorJoystick.getRawButton(10)) {
            shooter.moveToPosition(Constants.ShooterMidPosition.getValue());
        } else if (operatorJoystick.getRawButton(8)) {
            shooter.moveToPosition(Constants.ShooterLoadPosition.getValue());
        } else {
            shooter.move(Util.deadband(operatorJoystick.getY(), Constants.JoystickDeadband.getValue()));
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

        Logger.log("shooterAngle", shooter.getAngle());

        //
        // Climbing
        //
        if (operatorJoystick.getRawButton(4))
            climber.extendHooks();
        else
            climber.retractHooks();
    }
}
