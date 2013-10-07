package org.lunatecs316.frc2013.auto;

import org.lunatecs316.frc2013.Logger;

/**
 * For testing various things that need to be tested
 * @author domenicpaul
 */
public class TestAuto extends StateMachineAuto {

    private final State kStart = new State("Start");
    private final State kMovingForward = new State("MovingForward");
    
    protected void smInit() {
        Logger.log("Testing... Please stand by...");
    }

    protected void smRun() {
        
    }
    
}
