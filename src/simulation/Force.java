package simulation;


/**
 * A force that acts on a mass.
 * @author Wayne You and Ross Cahoon
 *
 */
public class Force {
    private int myKeyEvent;
    private boolean myForceActive;
    /**
     * Applies a force to a given mass
     * @param m the mass that the force is being applied
     */
    public void applyForce(Mass m) {
        
    };
    /**
     * Checks to see if the force is being applied to assemblies
     */
    public boolean isForceActive() {
        return myForceActive;
    }
    /**
     * Toggles force
     */
    public void toggleForce() {
        myForceActive = !myForceActive;
    }
    /**
     * Returns the KeyEvent this force responds too
     */
    public int getKeyEvent() {
        return myKeyEvent;
    }
    /**
     * Returns the KeyEvent this force responds too
     */
    public void setKeyEvent(int keyevent) {
       myKeyEvent = keyevent;
    }
}
