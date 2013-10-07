package org.lunatecs316.frc2013.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import org.lunatecs316.frc2013.RobotMap;

/**
 * Climbing subsystem
 * @author domenicpaul
 */
public class Climber extends Subsystem {

    private final Solenoid solenoid = new Solenoid(RobotMap.CLIMBING_SOLENOID);

    /**
     * Constructor
     */
    public Climber() {

    }

    /**
     * Initialize the subsystem
     */
    public void init() {

    }

    public void extendHooks() {
        solenoid.set(true);
    }

    public void retractHooks() {
        solenoid.set(false);
    }
}
