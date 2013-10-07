package org.lunatecs316.frc2013.subsystems;

/**
 * Base class for all Subsystems
 * @author domenicpaul
 */
public abstract class Subsystem {
    protected String name;

    public Subsystem() {
        name = this.getClass().getName();
    }

    public Subsystem(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void init();
}
