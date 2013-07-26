package org.lunatecs316.frc2013.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.lunatecs316.frc2013.commands.*;

/**
 * Blank auto mode that does nothing
 * @author domenicpaul
 */
public class DoNothingAuto extends CommandGroup {
    
    public DoNothingAuto() {
        addParallel(new StopPickupArm());
        addParallel(new DisablePickup());
        addParallel(new DisableShooter());
    }
    
}
