package org.lunatecs316.frc2013.auto;

import org.lunatecs316.frc2013.subsystems.*;

/**
 * Three disk autonomous mode. Uses the StateMachineAuto template.
 * @author domenicpaul
 */
public class ThreeDiskAuto extends StateMachineAuto {
    
    // Define states
    private final State kStart = new State("Start");
    private final State kFiring = new State("Firing");
    private final State kPreparingNextShot = new State("PreparingNextShot");
    private final State kFinished = new State("Finished");
    
    // Other data
    private int shotsFired = 0;
    private boolean finished = false;
    
    /**
     * Run any setup. Called from autonomousInit()
     */
    public void smInit() {
        state = kStart;
        finished = false;
        shotsFired = 0;
        System.out.println("[ThreeDiskAuto][init] Autonomous Initialized");
    }
    
    /**
     * Run one iteration of the mode. Called from autonomousPeriodic()
     */
    public void smRun() {
        String output = "[ThreeDiskAuto][run] ";    // used for debugging
        Shooter.indications();
        
        if (!finished) {
            // Add the current state to debug output
            output += "State: " + state.toString() + "; ";
            output += "Time: " + ellapsedStateTime() + ";";
            
            // Switch through the states
            if (state == kStart) {
                // Enable the shooter and wait for it to come up to speed
                Shooter.enable();
                
                output += "Shooter.atSpeed(): " + Shooter.atSpeed() + ";";
                if (Shooter.atSpeed() || ellapsedStateTime() >= 2000) {
                    setState(kFiring);
                }
            } else if (state == kFiring) {
                // Fire a shot
                Shooter.fire(true);

                // Wait 200ms
                if (ellapsedStateTime() >= 200) {
                    shotsFired++;
                    if (shotsFired >= 5) {
                        setState(kFinished);
                    } else {
                        setState(kPreparingNextShot);
                    }
                }
            } else if (state == kPreparingNextShot) {
                // Reset and wait for the shooter to come back up to speed
                Shooter.fire(false);

                output += "Shooter.atSpeed(): " + Shooter.atSpeed() + ";";
                if (ellapsedStateTime() >= 500 &&
                        (Shooter.atSpeed() || ellapsedStateTime() >= 2000)) {
                    setState(kFiring);
                }
            } else if (state == kFinished) {
                // Turn off the shooter
                Shooter.disable();
                Shooter.fire(false);
                finished = true;
            }
        } else {
            // We're done
            output += "Finished";
        }
        
        // Print debugging info
        System.out.println(output);
    }
}
