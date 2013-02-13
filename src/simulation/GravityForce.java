package simulation;

import java.awt.event.KeyEvent;
import util.Vector;
/**
 * @author Wayne You and Ross Cahoon
 * Calculates and tracks the gravity force.
 */
public class GravityForce extends Force {

    private static final Vector DEFAULT_GRAVITY = new Vector(90, .2);
    /**
     * The vector for gravity force.
     */
    private Vector myGravity;
    /**
     * Used to construct the default GravityForce object.
     */
    public GravityForce() {
        myGravity = DEFAULT_GRAVITY;
    }
    /**
     * Used to construct the GravityForce object.
     * @param gravity assigned to myGravity
     */
    public GravityForce(Vector gravity) {
        myGravity = gravity;
    }

    @Override
    protected Vector generateForce(Mass m) {
        Vector massGravity = new Vector(myGravity);
        massGravity.scale(m.getMass());
        return massGravity;
    }

    /**
     * Toggles whether gravity is active.
     * @param key The key pressed to be checked.
     */
    public void toggle (int key) {
        if (key == KeyEvent.VK_G) {
            toggleActiveState();
        }
    }

}