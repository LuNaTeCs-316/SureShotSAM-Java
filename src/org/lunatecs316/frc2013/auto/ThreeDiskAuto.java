package org.lunatecs316.frc2013.auto;

import org.lunatecs316.frc2013.subsystems.*;

import edu.wpi.first.wpilibj.DriverStationLCD;

/**
 * Three disk autonomous mode
 * @author domenicpaul
 */
public class ThreeDiskAuto implements AutonomousMode {
    
    private static class State {
        private String name;
        private int value;
        
        private static int count = 0;
        
        public static final State kStart = new State("Start");
        public static final State kFiring = new State("Firing");
        public static final State kPreparingNextShot = new State("PreparingNextShot");
        public static final State kFinished = new State("Finished");
        
        private State(String name) {
            this.name = name;
            this.value = count;
            count++;
        }
        
        public String toString() {
            return name;
        }
    }
    
    private State state = State.kStart;
    private int shotsFired = 0;
    private long startTime;
    private boolean finished = false;
    
    private DriverStationLCD LCD = DriverStationLCD.getInstance();

    private double ellapsedStateTime() {
        return System.currentTimeMillis() - startTime;
    }
    
    private void setState(State state) {
        this.state = state;
        startTime = System.currentTimeMillis();
    }
    
    /**
     * Run any setup. Called from autonomousInit()
     */
    public void init() {
        startTime = System.currentTimeMillis();
        state = State.kStart;
        shotsFired = 0;
        LCD.println(DriverStationLCD.Line.kUser1, 1,
                "[ThreeDiskAuto][init] Autonomous Initialized");
        System.out.println("[ThreeDiskAuto][init] Autonomous Initialized");
    }
    
    /**
     * Run one iteration of the mode. Called from autonomousPeriodic()
     */
    public void run() {
        String output = "[ThreeDiskAuto][run] ";
        
        if (!finished) {
            output += "State: " + state.toString();
            
            if (state == State.kStart) {
                Shooter.enable();

                if (Shooter.atSpeed() || ellapsedStateTime() >= 2.0) {
                    setState(State.kFiring);
                }
            } else if (state == State.kFiring) {
                Shooter.fire(true);

                if (ellapsedStateTime() >= 0.2) {
                    shotsFired++;
                    if (shotsFired >= 5) {
                        setState(State.kFinished);
                    } else {
                        setState(State.kPreparingNextShot);
                    }
                }
            } else if (state == State.kPreparingNextShot) {
                Shooter.fire(false);

                if (ellapsedStateTime() >= 0.5 &&
                        (Shooter.atSpeed() || ellapsedStateTime() >= 2.0)) {
                    setState(State.kFiring);
                }
            } else if (state == State.kFinished) {
                Shooter.disable();
                finished = true;
            }
        } else {
            output += "Finished";
        }
        
        LCD.println(DriverStationLCD.Line.kUser1, 1, output);
        System.out.println(output);
    }
}
