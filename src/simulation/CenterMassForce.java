package simulation;


import java.awt.event.KeyEvent;
import util.Location;

/**
 * @author Wayne You and Ross Cahoon
 * Calculates and tracks the force for the center of mass and
 * all information used in these calculations
 */
public class CenterMassForce extends Force {

    private static final double DEFAULT_MAGNITUDE = 10;
    private static final double DEFAULT_EXPONENT = 2;
    /**
     * The location where the force is directed.
     */
    private static Location ourCenterMassLocation;
    /**
     * The magnitude of the center of mass force.
     */
    private double myMagnitude;
    /**
     * The exponent of the center of mass force.
     */
    private double myExponent;
    /**
     * Used to construct the default CenterMassForce object.
     */
    public CenterMassForce() {
        myMagnitude = DEFAULT_MAGNITUDE;
        myExponent = DEFAULT_EXPONENT;
    }
    /**
     * Used to construct the Force object.
     * @param magnitude assigned to myMagnitude
     * @param exponent assigned to myExponent
     */
    public CenterMassForce(double magnitude, double exponent) {
        myMagnitude = magnitude;
        myExponent = exponent;
    }
    /**
     * Calculates and applies a force to the given mass.
     * @param m is the mass that the force is being applied to.
     */
    @Override
    protected util.Vector generateForce(Mass m) {
        if (ourCenterMassLocation == null) {
            return new util.Vector(0, 0);
        }
        util.Vector tVect = new util.Vector(m.getCenter(), ourCenterMassLocation);
        double tDist = Math.abs(m.getCenter().distance(ourCenterMassLocation));
        tVect.setMagnitude(myMagnitude / Math.pow(tDist, myExponent));
        return tVect;
    }

    /**
     * Toggles whether the center of mass force is active.
     * @param key The key to be checked against.
     */
    public void toggle (int key) {
        if (key == KeyEvent.VK_M) {
            toggleActiveState();
        }
    }
    
    public static void assignCenterOfMass(Location center) {
        ourCenterMassLocation = center;
    }
}
