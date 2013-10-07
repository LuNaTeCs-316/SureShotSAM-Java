package org.lunatecs316.frc2013.subsystems;

/**
 * Holds a single static instance of each subsystem
 * @author domenicpaul
 */
public class Subsystems {
    public static Drivetrain drivetrain = new Drivetrain();
    public static Climber climber = new Climber();

    public static void init() {
        drivetrain.init();
        climber.init();
    }
}
