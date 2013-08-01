package org.lunatecs316.frc2013.auto;

import org.lunatecs316.frc2013.commands.WaitForShooterSpeed;
import org.lunatecs316.frc2013.commands.Shoot;
import org.lunatecs316.frc2013.commands.MoveShooterToPosition;
import org.lunatecs316.frc2013.commands.EnableShooter;
import org.lunatecs316.frc2013.commands.DisableShooter;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.lunatecs316.frc2013.Constants;

/**
 * Three disk autonomous mode. Uses the StateMachineAuto template.
 * @author domenicpaul
 */
public class ThreeDiskAuto extends CommandGroup {

    public ThreeDiskAuto() {
        addSequential(new MoveShooterToPosition(Constants.kShooterTopPosition.getValue()));
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
