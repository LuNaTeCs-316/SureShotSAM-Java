package org.lunatecs316.frc2013.subsystems;

/**
 * Holds a single static instance of each subsystem. Extended by OI and
 * Autonomous modes to gain access
 * @author domenicpaul
 */
public class Subsystems {
    public static Drivetrain drivetrain = new Drivetrain();
    public static Pickup pickup = new Pickup();
    public static Shooter shooter = new Shooter();
    public static Climber climber = new Climber();

    public void init() {
        drivetrain.init();
        pickup.init();
        shooter.init();
        climber.init();
    }

    public static void updateSmartDashboard() {
        drivetrain.updateSmartDashboard();
        pickup.updateSmartDashboard();
        shooter.updateSmartDashboard();
        climber.updateSmartDashboard();
    }
}
