package org.lunatecs316.frc2013.auto;

import org.lunatecs316.frc2013.subsystems.*;

/**
 * Three disk autonomous mode
 * @author domenicpaul
 */
public class ThreeDiskAuto implements AutonomousMode {    
    private static final int kStart = 0;
    private static final int kFiring = 1;
    private static final int kPreparingNextShot = 2;
    private static final int kFinished = 3;
    
    private int state = kStart;
    private int shotsFired = 0;
    private long startTime;

    private double ellapsedStateTime() {
        return System.currentTimeMillis() - startTime;
    }
    
    private void setState(int state) {
        this.state = state;
        startTime = System.currentTimeMillis();
    }
    
    /**
     * Run any setup. Called from autonomousInit()
     */
    public void init() {
        startTime = System.currentTimeMillis();
        state = kStart;
        shotsFired = 0;
    }
    
    /**
     * Run one iteration of the mode. Called from autonomousPeriodic()
     */
    public void run() {
        switch (state) {
            case kStart:
                Shooter.enable();
                
                if (Shooter.atSpeed() || ellapsedStateTime() >= 2.0) {
                    setState(kFiring);
                }
                break;
            case kFiring:
                Shooter.fire(true);
                
                if (ellapsedStateTime() >= 0.2) {
                    shotsFired++;
                    if (shotsFired >= 5) {
                        setState(kFinished);
                    } else {
                        setState(kPreparingNextShot);
                    }
                }
                break;
            case kPreparingNextShot:
                Shooter.fire(false);
                
                if (ellapsedStateTime() >= 0.5 &&
                        (Shooter.atSpeed() || ellapsedStateTime() >= 2.0)) {
                    setState(kFiring);
                }
                break;
            case kFinished:
                Shooter.disable();
                break;
        }
    }
}
