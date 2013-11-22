package org.lunatecs316.frc2013.subsystems;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.lunatecs316.frc2013.Constants;
import org.lunatecs316.frc2013.RobotMap;
import org.lunatecs316.frc2013.lib.Potentiometer;
import org.lunatecs316.frc2013.lib.Tachometer;

/**
 * Shooter Subsystem
 * @author domenicpaul
 */
public class Shooter extends Subsystem {

    /* Shooter Motor */
    private Victor motor = new Victor(RobotMap.SHOOTER_MOTOR);
    private Tachometer speedTach = new Tachometer(RobotMap.SHOOTER_SPEED_TACH);
    private PIDController speedController = new PIDController(-0.005,
            0.000, 0.0, speedTach, motor);

    /* Shooter Angle */
    private Jaguar angleMotor = new Jaguar(RobotMap.SHOOTER_ANGLE_MOTOR);
    private Potentiometer anglePot = new Potentiometer(RobotMap.SHOOTER_ANGLE_POT);
    private PIDController angleController = new PIDController(21.5,
            0.0, 0.0, anglePot, angleMotor);

    /* Shooter piston */
    private Solenoid solenoid = new Solenoid(RobotMap.SHOOTER_SOLENOID);

    /* Shooter indicator lights */
    private Solenoid redIndicator1 = new Solenoid(RobotMap.RED_INDICATOR_1);
    private Solenoid redIndicator2 = new Solenoid(RobotMap.RED_INDICATOR_2);
    private Solenoid blueIndicator = new Solenoid(RobotMap.BLUE_INDICATOR);

    private boolean autoMode = false;
    
    private boolean lightIsOn = false;
    private int offCounter = 0;
    private int onCounter = 0;

    public void init() {
        // Configure the PIDController for the Shooter
        speedController.setSetpoint(Constants.ShooterTargetSpeed.getValue());
        speedController.setAbsoluteTolerance(350);

        // Start the Tachometer
        speedTach.start();

        // Configure LiveWindow
        LiveWindow.addActuator("Shooter", "Motor", motor);
        LiveWindow.addActuator("Shooter", "SpeedController", speedController);
        LiveWindow.addActuator("Shooter", "AngleMotor", angleMotor);
        LiveWindow.addActuator("Shooter", "AngleController", angleController);
        LiveWindow.addActuator("Shooter", "Solenoid", solenoid);
        LiveWindow.addSensor("Shooter", "AnglePot", anglePot);
        LiveWindow.addSensor("Shooter", "SpeedTach", speedTach);
    }

    public void updateSmartDashboard() {
        SmartDashboard.putNumber("ShooterAngle", anglePot.getAverageVoltage());
        SmartDashboard.putNumber("ShooterSpeed", speedTach.getRPM());
    }

    public double getAngle() {
        return anglePot.getAverageVoltage();
    }

    /**
     * Move the shooter to the specified position
     * @param pos
     */
    public void moveToPosition(double pos) {
        angleController.setSetpoint(pos);
        angleController.enable();
    }
    
    public void moveToTopPosition() {
        moveToPosition(Constants.ShooterTopPosition.getValue());
    }
    
    public void moveToLoadPosition() {
        moveToPosition(Constants.ShooterLoadPosition.getValue());
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
     * Enable the shooter wheel
     */
    public void enable() {
        autoMode = false;
        speedController.enable();
    }

    /**
     * Disable the shooter wheel
     */
    public void disable() {
        autoMode = false;
        speedController.disable();
        motor.set(0);
    }

    /**
     * Check to see if the shooter wheel is up to speed
     * @return true if the shooter is at speed, else false
     */
    public boolean atSpeed() {
        return speedController.onTarget();
        //return speedTach.getRPM() > 3700;
    }
    
    public boolean moving() {
        return angleController.isEnable();
    }

    /**
     * Fire the shot
     * @param value whether to shoot or not
     */
    public void fire(boolean value) {
        autoMode = false;
        solenoid.set(value);
    }

    public void autoFire() {
        speedController.disable();
        motor.set(-1.0);
        solenoid.set(speedTach.getRPM() > Constants.ShooterMinFiringSpeed.getValue());
    }

    public void setSpeed(double value) {
        speedController.disable();
        motor.set(value);
    }

    /**
     * Control the feedback lights for the shooter
     */
    public void indications() {
        // Blue indicator light show if we are at the proper angle
        blueIndicator.set((anglePot.getOutput() >= Constants.ShooterTopPosition.getValue() - 0.1));

        if (speedTach.getRPM() >= Constants.ShooterMinFiringSpeed.getValue()) {
            // Red indicator lights are solid when at speed,...
            redIndicator1.set(true);
            redIndicator2.set(true);
        } else if (speedTach.getRPM() <= 250) {
            // ...off when the speed is too low..
            redIndicator1.set(false);
            redIndicator2.set(false);
        } else {
            // ...and blink when the speed is in between stepints...
            if (lightIsOn) { // light was turned on last pass, turn it off now
                onCounter++;
                if (onCounter >= Constants.ShooterLightBlinkSpeed.getValue()) {
                    redIndicator1.set(false);
                    redIndicator2.set(false);
                    lightIsOn = false;
                    offCounter = 0;
                }
            } else { // light is off, check to see if it is time to turn it on
                offCounter++;
                if (offCounter >= Constants.ShooterLightBlinkSpeed.getValue()) {
                    redIndicator1.set(true);
                    redIndicator2.set(true);
                    lightIsOn = true;
                    onCounter = 0;
                }
            }
	}
    }
}
