package org.lunatecs316.frc2013.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.lunatecs316.frc2013.commands.*;
import org.lunatecs316.frc2013.subsystems.Shooter;

/**
 * Three disk autonomous mode. Uses the StateMachineAuto template.
 * @author domenicpaul
 */
public class ThreeDiskAuto extends CommandGroup {

    public ThreeDiskAuto() {
        addSequential(new MoveShooterToPosition(Shooter.kTopPosition));
        addSequential(new EnableShooter(false));
        addSequential(new WaitForShooterSpeed(3.0));
        addSequential(new Shoot());
        addSequential(new WaitForShooterSpeed(3.0));
        addSequential(new Shoot());
        addSequential(new WaitForShooterSpeed(3.0));
        addSequential(new Shoot());
        addSequential(new WaitForShooterSpeed(3.0));
        addSequential(new Shoot());
        addSequential(new DisableShooter());
    }
}
