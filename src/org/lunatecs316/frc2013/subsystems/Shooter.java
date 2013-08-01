package org.lunatecs316.frc2013.subsystems;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.lunatecs316.frc2013.RobotMap;
import org.lunatecs316.frc2013.commands.MoveShooterWithJoystick;
import org.lunatecs316.frc2013.lib.Potentiometer;
import org.lunatecs316.frc2013.lib.Tachometer;

/**
 * Shooter Subsystem
 * @author domenicpaul
 */
public final class Shooter extends Subsystem {
    
    /* Shooter positions */
    public static final double kTopPosition = 3.2;
    public static final double kMidPosition = 3.1;
    public static final double kLoadPosition = 1.85;
    
    /* Shooter Motor */
    private final Victor motor = new Victor(RobotMap.kShooterMotor);
    private final Tachometer speedTach = new Tachometer(RobotMap.kShooterSpeedTach);
    private final PIDController speedController = new PIDController(-0.005,
            0.000, 0.0, speedTach, motor);
    
    /* Shooter Angle */
    private final Jaguar angleMotor = new Jaguar(RobotMap.kShooterAngleMotor);
    private final Potentiometer anglePot = new Potentiometer(RobotMap.kShooterAnglePot);
    private final PIDController angleController = new PIDController(21.5,
            0.0, 0.0, anglePot, angleMotor);
    
    /* Shooter piston */
    private final Solenoid solenoid = new Solenoid(RobotMap.kShooterSolenoid);
    
    /* Shooter indicator lights */
    private Solenoid redIndicator1 = new Solenoid(RobotMap.kRedIndicator1);
    private Solenoid redIndicator2 = new Solenoid(RobotMap.kRedIndicator2);
    private Solenoid blueIndicator = new Solenoid(RobotMap.kBlueIndicator);
  
    private boolean lightIsOn = false;
    private int offCounter = 0;
     
    /**
     * Initialize the shooter subsystem
     */
    public Shooter() {        
        super();
        
        // Configure the PIDController for the Shooter
        speedController.setSetpoint(3975);
        speedController.setAbsoluteTolerance(350);
        
        // Start the Tachometer
        speedTach.start();
    }
    
    protected void initDefaultCommand() {
        setDefaultCommand(new MoveShooterWithJoystick());
    }
    
    /**
     * Manually control the angle
     * @param val the movement speed value
     */
    public void move(double val) {
        angleController.disable();
        angleMotor.set(val);
    }
    
    /**
     * Move the shooter to the specified position
     * @param pos 
     */
    public void moveToPosition(double pos) {
        angleController.setSetpoint(pos);
        angleController.enable();
    }
    
    /**
     * @return true if the shooter is at the target angle
     */
    public boolean atPosition() {
        return angleController.onTarget();
    }
    
    /**
     * Enable the shooter wheel
     */
    public void enable() {
        speedController.enable();
    }
    
    /**
     * Disable the shooter wheel
     */
    public void disable() {
        speedController.disable();
        motor.set(0);
    }
    
    
    /**
     * Manually set the speed of the shooter motor
     * @param value 
     */
    public void setSpeed(double value) {
        speedController.disable();
        
        motor.set(value);
    }
    
    /**
     * @return the current speed of the shooter wheel
     */
    public double getSpeed() {
        return speedTach.getRPM();
    }
    
    /**
     * Check to see if the shooter wheel is up to speed
     * @return true if the shooter is at speed, else false
     */
    public boolean atSpeed() {
        return speedController.onTarget() || (speedTach.getRPM() > 3700);
    }
    
    /**
     * Fire the shot
     * @param value whether to shoot or not
     */
    public void fire(boolean value) {
        solenoid.set(value);
    }
    
    /**
     * Control the feedback lights for the shooter
     */
    public void indications() {
        
        // Blue indicator light show if we are at the proper angle
        blueIndicator.set((anglePot.getOutput() >= kTopPosition));
        
        if (speedTach.getRPM() >= 3800) {
            // Red indicator lights are solid when at speed,...
            redIndicator1.set(true);
            redIndicator2.set(true);
        } else if (speedTach.getRPM() <= 500) {
            // ...off when the speed is too low,..
            redIndicator1.set(false);
            redIndicator2.set(false);
        } else {
            // ...and blink when the speed is in between	
            if (lightIsOn) { // light was turned on last pass, turn it off now
                redIndicator1.set(false);
                redIndicator2.set(false);
                lightIsOn = false;
                offCounter = 0;
            } else { // light is off, check to see if it is time to turn it on
                offCounter++;
                double temp = speedTach.getRPM();
                temp = temp - 150;
                double offTime = 71 - temp;
                if (offCounter >= offTime) {
                    redIndicator1.set(true);
                    redIndicator2.set(true);
                    lightIsOn = true;
                }
            }
	}
    }  
    // </editor-fold>
}
