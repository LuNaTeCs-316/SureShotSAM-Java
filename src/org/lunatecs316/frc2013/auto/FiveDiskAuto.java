/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lunatecs316.frc2013.auto;

import org.lunatecs316.frc2013.subsystems.Drivetrain;
import org.lunatecs316.frc2013.subsystems.Pickup;
import org.lunatecs316.frc2013.subsystems.Shooter;

/**
 *
 * @author 316Programming
 */
public class FiveDiskAuto implements AutonomousMode {

    // <editor-fold desc="State 'Enum'" defaultstate="collapsed">
    /**
     * Represents the various states of the robot during the autonomous mode
     */
    private static class State {
        
        /**
         * Start of the Autonomous Mode
         */
        public static final State kStart = new State("Start");
        
        /**
         * Firing a shot
         */
        public static final State kFiring = new State("Firing");
        
        /**
         * Preparing to fire the next shot
         */
        public static final State kPreparingNextShot = new State("PreparingNextShot");
        
        /**
         * Lowering the pickup
         */
        public static final State kLoweringPickup = new State("LoweringPickup");
        
        /**
         * Backing up to the disks at the goal
         */
        public static final State kBackingUp = new State("BackingUp");
        
        /**
         * Wait at the center line and pickup the discs
         */
        public static final State kWaitAtCenterLine = new State("WaitAtCenterLine");
        
        /**
         * Drive forward back to the goal
         */
        public static final State kDriveForward = new State("DriveForward");
        
        /**
         * Finished the Autonomous Mode
         */
        public static final State kFinished = new State("Finished");
                
        private String name;
        
        // Private to prevent creation outside the class
        private State(String name) {
            this.name = name;
        }
        
        /**
         * Convert the State to a string
         * @return the string value
         */
        public String toString() {
            return name;
        }
    }
    // </editor-fold>
    
    private State state = State.kStart;
    private int shotsFired = 0;
    private long startTime;
    private boolean finished = false;
    
    /**
     * Returns the time spent in the current state
     * @return millisecond state time
     */
    private double ellapsedStateTime() {
        //System.out.println("Time:" + System.currentTimeMillis() + "StartTime: " + startTime);
        return System.currentTimeMillis() - startTime;
    }
    
    /**
     * Sets the state for the next loop and resets startTime
     * @param state the new state
     */
    private void setState(State state) {
        this.state = state;
        startTime = System.currentTimeMillis();
    }
    
    /**
     * Run any setup. Called from autonomousInit()
     */
    public void init() {
        startTime = System.currentTimeMillis();
        state = State.kStart;
        finished = false;
        shotsFired = 0;
        System.out.println("[FiveDiskAuto][init] Autonomous Initialized");
    }
    
    /**
     * Run one iteration of the mode. Called from autonomousPeriodic()
     */
    public void run() {
        String output = "[FiveDiskAuto][run] ";    // used for debugging
        
        if (!finished) {
            // Add the current state to debug output
            output += "State: " + state.toString() + "; ";
            output += "Time: " + ellapsedStateTime() + ";";
            
            // Switch through the states
            if (state == State.kStart) {
                // Enable the shooter and wait for it to come up to speed
                Shooter.enable();
                
                output += "Shooter.atSpeed(): " + Shooter.atSpeed() + ";";
                if (Shooter.atSpeed() || ellapsedStateTime() >= 2000) {
                    setState(State.kFiring);
                }
            } else if (state == State.kFiring) {
                // Fire a shot
                Shooter.fire(true);

                // Wait 200ms
                if (ellapsedStateTime() >= 200) {
                    shotsFired++;
                    if (shotsFired >= 4) {
                        setState(State.kLoweringPickup);
                    } else {
                        setState(State.kPreparingNextShot);
                    }
                }
            } else if (state == State.kPreparingNextShot) {
                // Reset and wait for the shooter to come back up to speed
                Shooter.fire(false);

                output += "Shooter.atSpeed(): " + Shooter.atSpeed() + ";";
                if (ellapsedStateTime() >= 500 &&
                        (Shooter.atSpeed() || ellapsedStateTime() >= 2000)) {
                    setState(State.kFiring);
                }
            } else if (state == State.kLoweringPickup) {
                // Lower the pickup
                Pickup.lower();
                
                if (ellapsedStateTime() >= 1000) {
                    setState(State.kBackingUp);
                }
            } else if (state == State.kBackingUp) {
                // Backup to the center line
                Drivetrain.arcadeDrive(-0.6, 0);
                Pickup.setBeltState(Pickup.BeltState.Forwards);
                
                if (ellapsedStateTime() >= 1000) {
                    setState(State.kWaitAtCenterLine);
                }
            } else if (state == State.kWaitAtCenterLine) {
                // Wait at the center line
                Drivetrain.arcadeDrive(0, 0);
                Pickup.stop();
                
                if (ellapsedStateTime() >= 1000) {
                    setState(State.kDriveForward);
                }
            } else if (state == State.kDriveForward) {
                // Wait at the center line
                Drivetrain.arcadeDrive(0.6, 0);
                Pickup.raise();
                
                if (ellapsedStateTime() >= 1000) {
                    Pickup.setBeltState(Pickup.BeltState.Off);
                    Pickup.stop();
                    Drivetrain.arcadeDrive(0, 0);
                    shotsFired = 0;
                    setState(State.kPreparingNextShot);
                }
            } else if (state == State.kFinished) {  
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
