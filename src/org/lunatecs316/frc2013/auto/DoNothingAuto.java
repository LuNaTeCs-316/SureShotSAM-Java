package org.lunatecs316.frc2013.auto;

import org.lunatecs316.frc2013.Logger;

/**
 * Blank auto mode that does nothing
 * @author domenicpaul
 */
public class DoNothingAuto extends AutonomousMode {
    
    /**
     * Initialize the autonomous mode
     */
    public void init() {
        // Nothing to do
        name = "DoNothingAuto";
        Logger.log("DoNothingAuto: Automatically doing nothing");
    }
    
    /**
     * Run one iteration of the autonomous mode
     */
    public void run() {
        // Nothing to do
    }
}
