package org.lunatecs316.frc2013.auto;

import org.lunatecs316.frc2013.Logger;

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
        name = "ThreeDiskAuto";
        state = kStart;
        finished = false;
        shotsFired = 0;
        Logger.log("ThreeDiskAuto: Initialized");
    }

    /**
     * Run one iteration of the mode. Called from autonomousPeriodic()
     */
    public void smRun() {
        shooter.indications();

        if (!finished) {
            // Add the current state to debug output
            Logger.log("Time", stateTimer.getCurrentMs());

            // Switch through the states
            if (state == kStart) {
                // Enable the shooter and wait for it to come up to speed
                shooter.enable();

                Logger.log("ShooterAtSpeed?", shooter.atSpeed());
                if (shooter.atSpeed() || stateTimer.getCurrentMs() >= 2000) {
                    setState(kFiring);
                }
            } else if (state == kFiring) {
                // Fire a shot
                shooter.fire(true);

                // Wait 200ms
                if (stateTimer.getCurrentMs() >= 200) {
                    shotsFired++;
                    if (shotsFired >= 5) {
                        setState(kFinished);
                    } else {
                        setState(kPreparingNextShot);
                    }
                }
            } else if (state == kPreparingNextShot) {
                // Reset and wait for the shooter to come back up to speed
                shooter.fire(false);

                Logger.log("ShooterAtSpeed?", shooter.atSpeed());
                if (stateTimer.getCurrentMs() >= 500 &&
                        (shooter.atSpeed() || stateTimer.getCurrentMs() >= 2000)) {
                    setState(kFiring);
                }
            } else if (state == kFinished) {
                // Turn off the shooter
                shooter.disable();
                shooter.fire(false);
                finished = true;
            }
        } else {
            // We're done
            Logger.log("Finished");
        }
    }
}
