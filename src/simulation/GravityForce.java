package simulation;

import util.Vector;
/**
 * @author Wayne You and Ross Cahoon
 * Calculates and tracks the gravity force.
 */
public class GravityForce extends Force {
    /**
     * The vector for gravity force.
     */
    private Vector myGravity;
    /**
     * Used to construct the Force object.
     * @param gravityValue assigned to myGravity
     */
    public GravityForce(final Vector gravityValue) {
        myGravity = new Vector(gravityValue);
    }

    @Override
    public final void applyForce(final Mass m) {
        Vector massGravity = new Vector(myGravity);
        massGravity.scale(m.getMass());
        m.applyForce(massGravity);
    }

}