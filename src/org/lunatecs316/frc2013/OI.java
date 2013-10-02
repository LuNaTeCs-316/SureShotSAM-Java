package org.lunatecs316.frc2013;

import org.lunatecs316.frc2013.commands.StopPickupArm;
import org.lunatecs316.frc2013.commands.Shoot;
import org.lunatecs316.frc2013.commands.ReversePickup;
import org.lunatecs316.frc2013.commands.RaisePickup;
import org.lunatecs316.frc2013.commands.MoveShooterToPosition;
import org.lunatecs316.frc2013.commands.LowerPickup;
import org.lunatecs316.frc2013.commands.ExtendHooks;
import org.lunatecs316.frc2013.commands.EnableShooter;
import org.lunatecs316.frc2013.commands.EnablePickup;
import org.lunatecs316.frc2013.commands.DisableShooter;
import org.lunatecs316.frc2013.commands.DisablePickup;
import org.lunatecs316.frc2013.commands.AutoFire;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import org.lunatecs316.frc2013.subsystems.*;
import org.lunatecs316.frc2013.commands.*;

/**
 * The OI (Operator Interface) manages control of the robot during teleop
 * @author domenicpaul
 */
public class OI {
    
    /* Joysticks */
    private Joystick driverController = new Joystick(1);
    private Joystick operatorJoystick = new Joystick(2);

    /* Driver Controller Buttons */
    Button resetGyroButton = new JoystickButton(driverController, 4);
    Button raisePickupButton = new JoystickButton(driverController, 5);
    Button lowerPickupButton = new JoystickButton(driverController, 6);
    
    /* Operator Joystick Buttons */
    Button fireButton = new JoystickButton(operatorJoystick, 1);
    Button enableShooterButton = new JoystickButton(operatorJoystick, 2);
    Button autoFireButton = new JoystickButton(operatorJoystick, 3);
    Button climbButton = new JoystickButton(operatorJoystick, 4);
    Button reversePickupButton = new JoystickButton(operatorJoystick, 6);
    Button enablePickupButton = new JoystickButton(operatorJoystick, 7);
    Button shooterLoadPosition = new JoystickButton(operatorJoystick, 9);
    Button shooterMidPosition = new JoystickButton(operatorJoystick, 10);
    Button shooterTopPosition = new JoystickButton(operatorJoystick, 11);

    /**
     * OI Constructor. Bind commands to button events
     */
    public OI() {
        // Pickup Arm
        raisePickupButton.whenPressed(new RaisePickup());
        lowerPickupButton.whenPressed(new LowerPickup());
        raisePickupButton.whenReleased(new StopPickupArm());
        lowerPickupButton.whenReleased(new StopPickupArm());
        
        // Pickup Belts
        enablePickupButton.whenPressed(new EnablePickup());
        reversePickupButton.whenPressed(new ReversePickup());
        enablePickupButton.whenReleased(new DisablePickup());
        reversePickupButton.whenReleased(new DisablePickup());
        
        // Shooter Positioning
        shooterTopPosition.whenPressed(new MoveShooterToTopPosition());
        shooterMidPosition.whenPressed(new MoveShooterToPosition(Constants.kShooterMidPosition.getValue()));
        shooterLoadPosition.whenPressed(new MoveShooterToLoadPosition());
        
        // Shooter Firing
        autoFireButton.whileHeld(new AutoFire());
        enableShooterButton.whenPressed(new EnableShooter(true));
        enableShooterButton.whenReleased(new DisableShooter());
        fireButton.whenPressed(new Shoot());
        
        // Climbing
        climbButton.whileHeld(new ExtendHooks());
        climbButton.whenReleased(new LowerHooks());
        
        // Misc.
        resetGyroButton.whenPressed(new ResetGyro());
    }
    
    /**
     * Get the driver controller
     * @return the driver controller
     */
    public Joystick getDriverController() {
        return driverController;
    }
    
    /**
     * Get the operator joystick
     * @return the operator joystick
     */
    public Joystick getOperatorJoystick() {
        return operatorJoystick;
    }
}
