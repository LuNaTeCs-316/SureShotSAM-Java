package org.lunatecs316.frc2013.auto;

import org.lunatecs316.frc2013.Constants;
import org.lunatecs316.frc2013.Logger;
import org.lunatecs316.frc2013.subsystems.Pickup;

/**
 * Five disk autonomous mode from the center of the pyramid. Uses sensors instead
 * of dead reckoning.
 * @author domenicpaul
 */
public class FiveDiskRightCornerAuto extends StateMachineAuto {

    // Declare states
    private final State kStart = new State("Start");
    private final State kFiring = new State("Firing");
    private final State kPreparingNextShot = new State("PreparingNextShot");
    private final State kLoweringPickup = new State("LoweringPickup");
    private final State kBackingUp = new State("BackingUp");
    private final State kWaitAtCenterLine = new State("WaitAtCenterLine");
    private final State kDriveForward = new State("DriveForward");
    private final State kWaitAtPyramid = new State("WaitAtPyramid");
    private final State kFinished = new State("Finished");

    // Other data
    private int shotsFired = 0;
    private boolean finished = false;

    /**
     * Run any setup. Called from autonomousInit()
     */
    protected void smInit() {
        state = kStart;
        finished = false;
        shotsFired = 0;
        Logger.log("FiveDiskAuto: Initialized");
    }

    /**
     * Run one iteration of the mode. Called from autonomousPeriodic()
     */
    protected void smRun() {
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
                    if (shotsFired == 3) {
                        setState(kLoweringPickup);
                    } else if (shotsFired >= 8) {
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
                        (shooter.atSpeed() || stateTimer.getCurrentMs() >= 3500)) {
                    setState(kFiring);
                }
            } else if (state == kLoweringPickup) {
                // Lower the pickup
                shooter.fire(false);
                shooter.disable();
                pickup.lower();
                shooter.moveToPosition(Constants.ShooterLoadPosition.getValue());

                Logger.log("Lowering Pickup");
                if (stateTimer.getCurrentMs() >= 900) {
                    setState(kBackingUp);
                }
            } else if (state == kBackingUp) {
                // Backup to the center line
                drivetrain.driveStraight(-125.0);
                pickup.setBeltState(Pickup.BeltState.Reverse);

                Logger.log("Backing up");
                if (drivetrain.atTarget() || stateTimer.getCurrentMs() >= 3000) {
                    setState(kWaitAtCenterLine);
                }
            } else if (state == kWaitAtCenterLine) {
                // Wait at the center line
                drivetrain.arcadeDrive(0, 0);
                pickup.stop();

                Logger.log("Waiting at the center line");
                if (stateTimer.getCurrentMs() >= 1000) {
                    setState(kDriveForward);
                }
            } else if (state == kDriveForward) {
                // Wait at the center line
                drivetrain.driveStraight(125.0);
                pickup.raise();
                pickup.setBeltState(Pickup.BeltState.Off);
                shooter.moveToPosition(Constants.ShooterTopPosition.getValue());

                Logger.log("Driving forward to the pyramid");
                if (drivetrain.atTarget() || stateTimer.getCurrentMs() >= 1350) {
                    drivetrain.arcadeDrive(-0.075, 0);
                    setState(kWaitAtPyramid);
                }
            } else if (state == kWaitAtPyramid) {
                // Wait to settle at the pyramid before firing
                if (stateTimer.getCurrentMs() >= 750) {
                    pickup.stop();
                    drivetrain.arcadeDrive(-0.10, 0);
                    shooter.enable();
                    setState(kPreparingNextShot);
                }
            } else if (state == kFinished) {
                // Turn off the shooter
                shooter.disable();
                shooter.fire(false);
                finished = true;

                // We're done
                Logger.log("Finished");
            }
        } else {
        }
    }

}
