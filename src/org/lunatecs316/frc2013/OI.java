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
    private static final Joystick driverController = new Joystick(RobotMap.DRIVER_JOYSTICK);
    private static final Joystick operatorJoystick = new Joystick(RobotMap.OPERATOR_JOYSTICK);
    
    /**
     * Initialize the Operator Interface
     */
    public static void init() {
        // Call the init method for each subsystem
        Drivetrain.init();
        Pickup.init();
        Shooter.init();
        Climber.init();
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
                Drivetrain.setTargetDistance(96);
                firstPressDB3 = false;
            } else {
                Drivetrain.driveStraight();
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
