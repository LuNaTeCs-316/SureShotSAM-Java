package org.lunatecs316.frc2013.auto;

import org.lunatecs316.frc2013.Debugger;
import org.lunatecs316.frc2013.subsystems.Drivetrain;
import org.lunatecs316.frc2013.subsystems.Pickup;
import org.lunatecs316.frc2013.subsystems.Shooter;

/**
 * Five disk autonomous mode. Uses the StateMachineAuto template
 * @author domenicpaul
 */
public class FiveDiskAuto extends StateMachineAuto {

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
        Debugger.log("FiveDiskAuto: Initialized");
    }
    
    /**
     * Run one iteration of the mode. Called from autonomousPeriodic()
     */
    protected void smRun() {
        Shooter.indications();
        
        if (!finished) {
            // Add the current state to debug output
            Debugger.log("Time", ellapsedStateTime());
            
            // Switch through the states
            if (state == kStart) {
                // Enable the shooter and wait for it to come up to speed
                Shooter.enable();
                
                Debugger.log("ShooterAtSpeed?", Shooter.atSpeed());
                if (Shooter.atSpeed() || ellapsedStateTime() >= 2000) {
                    setState(kFiring);
                }
            } else if (state == kFiring) {
                // Fire a shot
                Shooter.fire(true);

                // Wait 200ms
                if (ellapsedStateTime() >= 200) {
                    shotsFired++;
                    if (shotsFired == 4) {
                        setState(kLoweringPickup);
                    } else if (shotsFired >= 8) {
                        setState(kFinished);
                    } else {
                        setState(kPreparingNextShot);
                    }
                }
            } else if (state == kPreparingNextShot) {
                // Reset and wait for the shooter to come back up to speed
                Shooter.fire(false);

                Debugger.log("ShooterAtSpeed?", Shooter.atSpeed());
                if (ellapsedStateTime() >= 500 &&
                        (Shooter.atSpeed() || ellapsedStateTime() >= 3500)) {
                    setState(kFiring);
                }
            } else if (state == kLoweringPickup) {
                // Lower the pickup
                Shooter.fire(false);
                Shooter.disable();
                Pickup.lower();
                Shooter.moveToPosition(Shooter.kLoadPosition);
                
                if (ellapsedStateTime() >= 1000) {
                    setState(kBackingUp);
                }
            } else if (state == kBackingUp) {
                // Backup to the center line
                Drivetrain.arcadeDrive(-0.5, 0);
                Pickup.setBeltState(Pickup.BeltState.Reverse);
                
                if (ellapsedStateTime() >= 1500) {
                    setState(kWaitAtCenterLine);
                }
            } else if (state == kWaitAtCenterLine) {
                // Wait at the center line
                Drivetrain.arcadeDrive(0, 0);
                Pickup.stop();
                
                if (ellapsedStateTime() >= 2500) {
                    setState(kDriveForward);
                }
            } else if (state == kDriveForward) {
                // Wait at the center line
                Drivetrain.arcadeDrive(0.5, 0);
                Pickup.raise();
                Pickup.setBeltState(Pickup.BeltState.Off);
                Shooter.moveToPosition(Shooter.kTopPosition);
                
                if (ellapsedStateTime() >= 1750) {
                    Drivetrain.arcadeDrive(-0.075, 0);
                    setState(kWaitAtPyramid);
                }
            } else if (state == kWaitAtPyramid) {
                // Wait to settle at the pyramid before firing
                if (ellapsedStateTime() >= 750) {
                    Pickup.stop();
                    Drivetrain.arcadeDrive(0, 0);
                    Shooter.enable();
                    setState(kPreparingNextShot);
                }
            } else if (state == kFinished) {  
                // Turn off the shooter
                Shooter.disable();
                Shooter.fire(false);
                finished = true;
            }
        } else {
            // We're done
            Debugger.log("Finished");
        }
    }
    
}
