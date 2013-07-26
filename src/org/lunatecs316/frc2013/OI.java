package org.lunatecs316.frc2013;

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

    Button resetGyroButton = new JoystickButton(driverController, 4);
    Button raisePickupButton = new JoystickButton(driverController, 5);
    Button lowerPickupButton = new JoystickButton(driverController, 6);
    
    Button fireButton = new JoystickButton(operatorJoystick, 1);
    Button enableShooterButton = new JoystickButton(operatorJoystick, 2);
    Button autoFireButton = new JoystickButton(operatorJoystick, 3);
    Button climbButton = new JoystickButton(operatorJoystick, 4);
    Button reversePickupButton = new JoystickButton(operatorJoystick, 6);
    Button enablePickupButton = new JoystickButton(operatorJoystick, 7);
    Button shooterLoadPosition = new JoystickButton(operatorJoystick, 9);
    Button shooterMidPosition = new JoystickButton(operatorJoystick, 10);
    Button shooterTopPosition = new JoystickButton(operatorJoystick, 11);

    
    public OI() {
        raisePickupButton.whenPressed(new RaisePickup());
        lowerPickupButton.whenPressed(new LowerPickup());
        raisePickupButton.whenReleased(new StopPickupArm());
        lowerPickupButton.whenReleased(new StopPickupArm());
        
        enablePickupButton.whenPressed(new EnablePickup());
        reversePickupButton.whenPressed(new ReversePickup());
        enablePickupButton.whenReleased(new DisablePickup());
        reversePickupButton.whenReleased(new DisablePickup());
        
        shooterTopPosition.whenPressed(new MoveShooterToPosition(Shooter.kTopPosition));
        shooterMidPosition.whenPressed(new MoveShooterToPosition(Shooter.kMidPosition));
        shooterLoadPosition.whenPressed(new MoveShooterToPosition(Shooter.kLoadPosition));
        
        autoFireButton.whileHeld(new AutoFire());
        enableShooterButton.whenPressed(new EnableShooter(true));
        enableShooterButton.whenReleased(new DisableShooter());
        fireButton.whenPressed(new Shoot());
        
        climbButton.whileHeld(new ExtendHooks());
        
        resetGyroButton.whenPressed(new ResetGyro());
    }
    
    public Joystick getDriverController() {
        return driverController;
    }
    
    public Joystick getOperatorJoystick() {
        return operatorJoystick;
    }
}
