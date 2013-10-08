package org.lunatecs316.frc2013.subsystems;

/**
 * Base class for all Subsystems
 * @author domenicpaul
 */
public abstract class Subsystem {
    protected String name;

    public Subsystem() {
        name = this.getClass().getName();
    }

    public Subsystem(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * Setup the subsystem for use
     */
    public void init() {
    }

    /**
     * Send subsystem data to the SmartDashboard
     */
    public void updateSmartDashboard() {
    }
    
    /**
     * Called when Constants are updated. Update values that don't read directly
     * from a Constant
     */
    public void updateConstants() {
    }
}
