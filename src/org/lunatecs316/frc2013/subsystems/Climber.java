package org.lunatecs316.frc2013.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.lunatecs316.frc2013.RobotMap;

/**
 * Climbing subsystem
 * @author domenicpaul
 */
public final class Climber extends Subsystem {

    private final Solenoid solenoid = new Solenoid(RobotMap.kClimbingSolenoid);

    /**
     * Set the default command for the subsystem
     */
    protected void initDefaultCommand() {}

    /**
     * Send subsystem data to the SmartDashboard
     */
    public void updateSmartDashboard() {
        SmartDashboard.putBoolean("ClimbingHooks", solenoid.get());
    }

    /**
     * Raise the climbing hooks
     */
    public void raiseHooks() {
        solenoid.set(true);
    }

    /**
     * Lower the climbing hooks
     */
    public void lowerHooks() {
        solenoid.set(false);
    }
}
