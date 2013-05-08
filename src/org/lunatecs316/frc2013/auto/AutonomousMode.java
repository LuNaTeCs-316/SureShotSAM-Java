package org.lunatecs316.frc2013.auto;

/**
 * Interface for all autonomous modes
 * @author domenicpaul
 */
public interface AutonomousMode {
    
    /**
     * Setup the auto mode
     */
    public void init();
    
    /**
     * Run one iteration of the autonomous mode
     */
    public void run();
}
