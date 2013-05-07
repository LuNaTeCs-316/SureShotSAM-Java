/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lunatecs316.frc2013.lib;

/**
 * Utility functions for the robot program
 * @author domenicpaul
 */
public class Util {
    // Prevent this class from being instantiated
    private Util() {
    }
    
    /**
     * Apply a deadband to the value
     * @param value
     * @param deadband
     * @return - 0 if the value is in the deadband, else the value
     */
    public static double deadband(double value, double deadband) {
        return (Math.abs(value) < deadband) ? 0.0 : value;
    }
}
