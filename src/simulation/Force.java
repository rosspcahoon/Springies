package simulation;

import util.Vector;


/**
 * A force that acts on a mass.
 * @author Wayne You and Ross Cahoon
 *
 */
public abstract class Force {
    private boolean myIsActive = true;
    /**
     * Applies a force to a given mass
     * @param m the mass that the force is being applied
     */
    public void applyForce(Mass m) {
        if (!isActive()) {
            return;
        }
        m.applyForce(generateForce(m));
    }
    
    private boolean isActive () {
        return myIsActive;
    }

    protected abstract Vector generateForce (Mass m);

    protected abstract void toggle (int key);
    
    protected void toggleActiveState () {
        myIsActive = !myIsActive;
    }
}
