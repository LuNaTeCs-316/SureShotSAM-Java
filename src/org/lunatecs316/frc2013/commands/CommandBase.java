package org.lunatecs316.frc2013.commands;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.command.Command;
import org.lunatecs316.frc2013.OI;
import org.lunatecs316.frc2013.RobotMap;
import org.lunatecs316.frc2013.subsystems.*;

/**
 * The base for all commands. All atomic commands should subclass CommandBase.
 * CommandBase stores creates and stores each control system. To access a
 * subsystem elsewhere in your code in your code use CommandBase.exampleSubsystem
 * @author domenicpaul
 */
public abstract class CommandBase extends Command {
    
    public static OI oi;
    // Subsystems
    public static Drivetrain drivetrain = new Drivetrain();
    public static PickupArm pickupArm = new PickupArm();
    public static PickupBelts pickupBelts = new PickupBelts();
    public static Shooter shooter = new Shooter();
    public static Climber climber = new Climber();
    public static Compressor compressor = new Compressor(RobotMap.kCompressorPressureSwitch,
                                                         RobotMap.kCompressorRelay);
    
    public static void init() {
        // This MUST be here. If the OI creates Commands (which it very likely
        // will), constructing it during the construction of CommandBase (from
        // which commands extend), subsystems are not guaranteed to be
        // yet. Thus, their requires() statements may grab null pointers. Bad
        // news. Don't move it.
        oi = new OI();   
    }
    
    public CommandBase(String name) {
        super(name);
    }
    
    public CommandBase() {
        super();
    }
    
    public void updateConstants() {
        shooter.updateConstants();
    }
}
