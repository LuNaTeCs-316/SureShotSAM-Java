package org.lunatecs316.frc2013;

import edu.wpi.first.wpilibj.Joystick;

import org.lunatecs316.frc2013.lib.Util;
import org.lunatecs316.frc2013.subsystems.*;

/**
 * Operator Interface maps controls to actions
 * @author domenicpaul
 */
public class OI {
    
    /* Joysticks */
    private static final Joystick driverController = new Joystick(RobotMap.DRIVER_JOYSTICK);
    private static final Joystick operatorJoystick = new Joystick(RobotMap.OPERATOR_JOYSTICK);
    
    /**
     * Initialize the Operator Interface
     */
    public static void init() {
    }
    
    /**
     * Run the drivetrain section
     */
    public static void runDrivetrain() {
        Drivetrain.arcadeDrive(driverController);
    }
    
    /**
     * Run the pickup section
     */
    public static void runPickup() {
        
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
    }
    
    /**
     * Run the shooter section
     */
    public static void runShooter() {
        
        // Angle control
        if (operatorJoystick.getRawButton(11)) {
            Shooter.moveToPosition(Shooter.Position.Top);
        } else if (operatorJoystick.getRawButton(10)) {
            Shooter.moveToPosition(Shooter.Position.Mid);
        } else if (operatorJoystick.getRawButton(8)) {
            Shooter.moveToPosition(Shooter.Position.Load);
        } else {
            Shooter.move(Util.deadband(operatorJoystick.getY(), 0.2));
        }
        
        // Motor control
        if (operatorJoystick.getRawButton(2)) {
            Shooter.enable();
        } else {
            Shooter.disable();
        }
        
        // Firing control
        Shooter.fire(operatorJoystick.getRawButton(1));
        
        // Indicator lights
        Shooter.indications();
    }
    
    /**
     * Run the climber section
     */
    public static void runClimber() {
        Climber.climb(operatorJoystick.getRawButton(4));
    }
}
