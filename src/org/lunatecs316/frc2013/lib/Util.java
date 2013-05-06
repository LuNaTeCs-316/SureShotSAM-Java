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
    public static double deadband(double value, double deadband) {
        return (Math.abs(value) < deadband) ? 0.0 : value;
    }
}
