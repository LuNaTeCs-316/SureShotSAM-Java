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
    
    /**
     * Initialize the Operator Interface
     */
    public static void init(Joystick js1, Joystick js2) {
        // Call the init method for each subsystem
        Drivetrain.init();
        Pickup.init();
        Shooter.init();
        Climber.init();
        
        driverController = js1;
        operatorJoystick = js2;
    }
    
    public static void run() {
        // Run each subsystem's section
        runDrivetrain();
        runPickup();
        runShooter();
        runClimber();
    }
    
    /**
     * Run the drivetrain section
     */
    private static boolean firstPressDB1 = true;
    private static boolean firstPressDB2 = true;
    private static boolean firstPressDB3 = true;
    private static void runDrivetrain() {
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
        } else if (driverController.getRawButton(3)) {
            if (firstPressDB3) {
                Drivetrain.setTargetAngle(90);
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
    }
    
    /**
     * Run the pickup section
     */
    private static void runPickup() {
        
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
    private static void runShooter() {
        
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
    private static void runClimber() {
        Climber.climb(operatorJoystick.getRawButton(4));
    }
}
