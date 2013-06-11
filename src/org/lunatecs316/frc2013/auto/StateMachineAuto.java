/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lunatecs316.frc2013.auto;

/**
 *
 * @author Domenic
 */
public class StateMachineAuto implements AutonomousMode {

    protected static class State {
        private String name;
        private double value;
        
        public State(String name, double value) {
            this.name = name;
            this.value = value;
        }
        
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
    
    protected State state;
    protected long startTime;
    
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
    
    public void init() {
        
    }

    public void run() {
        
    }
    
}
