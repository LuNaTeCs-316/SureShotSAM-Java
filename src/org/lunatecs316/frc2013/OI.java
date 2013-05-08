package org.lunatecs316.frc2013;

import edu.wpi.first.wpilibj.Joystick;

import org.lunatecs316.frc2013.lib.Util;
import org.lunatecs316.frc2013.subsystems.*;

/**
 * Operator Interface maps controls to actions
 * @author domenicpaul
 */
public class OI {
    private static final Joystick driverController = new Joystick(RobotMap.DRIVER_JOYSTICK);
    private static final Joystick operatorJoystick = new Joystick(RobotMap.OPERATOR_JOYSTICK);
    
    public static void init() {
        
    }
    
    /**
     * Run drivetrain section
     */
    public static void runDrivetrain() {
        Drivetrain.arcadeDrive(driverController);
    }
    
    /**
     * Run pickup section
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
            Pickup.setBeltState(Pickup.kBeltReverse);
        } else if (operatorJoystick.getRawButton(7)) {
            Pickup.setBeltState(Pickup.kBeltForwards);
        } else {
            Pickup.setBeltState(Pickup.kBeltOff);
        }
    }
    
    /**
     * Run shooter section
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
    }
    
    /**
     * Run climber section
     */
    public static void runClimber() {
        Climber.climb(operatorJoystick.getRawButton(4));
    }
}
