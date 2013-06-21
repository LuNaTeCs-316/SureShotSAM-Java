package org.lunatecs316.frc2013.auto;

/**
 * Interface for all autonomous modes
 * @author domenicpaul
 */
public abstract class AutonomousMode {
    
    protected String name = "AutoMode";
    
    /**
     * Setup the auto mode
     */
    public void init() {
        System.out.println("Default init(); override me!");
    };
    
    /**
     * Run one iteration of the autonomous mode
     */
    public void run() {
        System.out.println("Default run(); override me!");
    }
    
    /**
     * Pretty logging to output
     * @param context the current location (init(), run(), etc.)
     * @param message message to be displayed
     */
    public void log(String context, String message) {
        System.out.println("[" + name + "][" + context + "] " + message);
    }
}
