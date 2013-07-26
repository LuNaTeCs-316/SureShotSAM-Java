package org.lunatecs316.frc2013.auto;

/**
 * Base class for Finite State Machine based Autonomous Modes.
 * Subclasses should create some State variables and override the smInit()
 * and smRun() functions
 * 
 * @author domenicpaul
 */
public abstract class StateMachineAuto extends AutonomousMode {

    //<editor-fold defaultstate="collapsed" desc="State 'Enum'">
    /**
     * State class acts like an enum to provide type safe autonomous states
     */
    protected static class State {
        private String name;
        private double value;
        
        // Default constructor
        public State(String name, double value) {
            this.name = name;
            this.value = value;
        }
        
        // Constructor without value parameter
        public State(String name) {
            this(name, 0);
        }
        
        /**
         * Convert the State to a string
         * @return the string value
         */
        public String toString() {
            return name;
        }
        
        /**
         * Get the value of the state
         * @return value
         */
        public double getValue() {
            return value;
        }
    };
    //</editor-fold>
    
    protected State state;      // Current state
    protected long startTime;   // Used for timing
    
    /**
     * Returns the time spent in the current state
     * @return millisecond state time
     */
    protected double ellapsedStateTime() {
        //System.out.println("Time:" + System.currentTimeMillis() + "StartTime: " + startTime);
        return System.currentTimeMillis() - startTime;
    }
    
    /**
     * Sets the state for the next loop and resets startTime
     * @param state the new state
     */
    protected void setState(State state) {
        this.state = state;
        startTime = System.currentTimeMillis();
    }
    
    public void init() {
        name = "StateMachineAuto";
        startTime = System.currentTimeMillis();
        smInit();
    }
    
    public void run() {
        smRun();
    }
    
    // Override these methods in your subclass instead of init() and run()
    protected abstract void smInit();
    protected abstract void smRun();
}
