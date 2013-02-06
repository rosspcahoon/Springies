package simulation;

import java.awt.Dimension;

/**
 * XXX.
 * 
 * @author Ross Cahoon
 */

public class FixedMass extends Mass {
    /**
     * Constructs a FixedMass given x, y, and mass
     * @param x the x coordinate for a FixedMass
     * @param y the y coordinate for FixedMass
     * @param mass the magnitude of mass for FixedMass
     */
    public FixedMass(final double x, final double y, final double mass) {
        super(x, y, mass);
    }
    @Override
    public void update(final double elapsedTime, final Dimension bounds) {
    }
}
