package org.lunatecs316.frc2013;

/**
 * Helper class for logging debugging data
 * @author domenicpaul
 */
public class Debugger {
    
    private static String m_output;
    
    /**
     * Add a string to the debug output
     * @param s the message to add
     */
    public static void log(String s) {
        m_output += " | " + s;
    }
    
    /**
     * Add a float value to the debug output
     * @param name a label for the variable
     * @param value the value to display
     */
    public static void log(String name, float value) {
        m_output += " | " + name + ": " + value;
    }
    
    /**
     * Add a double value to the debug output
     * @param name a label for the variable
     * @param value the value to display
     */
    public static void log(String name, double value) {
        m_output += " | " + name + ": " + value;
    }
    
    /**
     * Add a integer value to the debug output
     * @param name a label for the variable
     * @param value the value to display
     */
    public static void log(String name, int value) {
        m_output += " | " + name + ": " + value;
    }
    
    /**
     * Add a boolean value to the debug output
     * @param name a label for the variable
     * @param value the value to display
     */
    public static void log(String name, boolean value) {
        m_output += " | " + name + ": " + value;
    }
    
    /**
     * Print the debugging info to the console
     * @param context contextual information such as the calling scope
     */
    public static void run(String context) {
        // Write the output string to the console
        System.out.println("[" + context + "]" + m_output);
        
        // Reset the output string for the next run
        m_output = "";
    }  
}
