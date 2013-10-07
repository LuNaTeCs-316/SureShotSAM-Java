package org.lunatecs316.frc2013.subsystems;

/**
 * Holds a single static instance of each subsystem
 * @author domenicpaul
 */
public class Subsystems {
    public static Climber climber;

    public static void init() {
        climber.init();
    }
}
