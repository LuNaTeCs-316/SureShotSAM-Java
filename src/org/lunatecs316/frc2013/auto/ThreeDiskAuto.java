package org.lunatecs316.frc2013.auto;

import org.lunatecs316.frc2013.subsystems.*;
import edu.wpi.first.wpilibj.DriverStationLCD;

/**
 * Three disk autonomous mode
 * @author domenicpaul
 */
public class ThreeDiskAuto implements AutonomousMode {
    
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
    
    private State state = State.kStart;
    private int shotsFired = 0;
    private long startTime;
    private boolean finished = false;
    
    private DriverStationLCD LCD = DriverStationLCD.getInstance();

    /**
     * Returns the time spent in the current state
     * @return the time spent in the current state
     */
    private double ellapsedStateTime() {
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
        shotsFired = 0;
        LCD.println(DriverStationLCD.Line.kUser1, 1,
                "[ThreeDiskAuto][init] Autonomous Initialized");
        System.out.println("[ThreeDiskAuto][init] Autonomous Initialized");
    }
    
    /**
     * Run one iteration of the mode. Called from autonomousPeriodic()
     */
    public void run() {
        String output = "[ThreeDiskAuto][run] ";    // used for debugging
        
        if (!finished) {
            // Add the current state to debug output
            output += "State: " + state.toString() + "; ";
            
            // Switch through the states
            if (state == State.kStart) {
                // Enable the shooter and wait for it to come up to speed
                Shooter.enable();

                if (Shooter.atSpeed() || ellapsedStateTime() >= 2.0) {
                    setState(State.kFiring);
                }
            } else if (state == State.kFiring) {
                // Fire a shot
                Shooter.fire(true);

                // Wait 200ms
                if (ellapsedStateTime() >= 0.2) {
                    shotsFired++;
                    if (shotsFired >= 5) {
                        setState(State.kFinished);
                    } else {
                        setState(State.kPreparingNextShot);
                    }
                }
            } else if (state == State.kPreparingNextShot) {
                // Reset and wait for the shooter to come back up to speed
                Shooter.fire(false);

                if (ellapsedStateTime() >= 0.5 &&
                        (Shooter.atSpeed() || ellapsedStateTime() >= 2.0)) {
                    setState(State.kFiring);
                }
            } else if (state == State.kFinished) {
                // Turn off the shooter
                Shooter.disable();
                finished = true;
            }
        } else {
            // We're done
            output += "Finished";
        }
        
        // Print debugging info
        LCD.println(DriverStationLCD.Line.kUser1, 1, output);
        System.out.println(output);
    }
}
