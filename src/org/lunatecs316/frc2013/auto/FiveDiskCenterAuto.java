package org.lunatecs316.frc2013.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.lunatecs316.frc2013.commands.*;
import org.lunatecs316.frc2013.subsystems.Shooter;
/**
 * Five disk autonomous mode. Uses the StateMachineAuto template
 * @author domenicpaul
 */
public class FiveDiskCenterAuto extends CommandGroup {

    public FiveDiskCenterAuto() {
        addSequential(new ThreeDiskAuto());
        addParallel(new LowerPickup());
        addSequential(new MoveShooterToPosition(Shooter.kLoadPosition));
        addParallel(new DriveStraight(-0.5));
        addParallel(new EnablePickup());
        addSequential(new Wait(1.5));
        addParallel(new StopPickupArm());
        addParallel(new DriveStraight(0));
        addSequential(new Wait(2.75));
        addParallel(new DisablePickup());
        addParallel(new RaisePickup());
        addParallel(new MoveShooterToPosition(Shooter.kTopPosition));
        addParallel(new DriveStraight(0.5));
        addSequential(new Wait(1.5));
        addParallel(new DriveStraight(0.0));
        addParallel(new ThreeDiskAuto());
    }
}
