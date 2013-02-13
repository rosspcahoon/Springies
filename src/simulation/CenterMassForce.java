package simulation;


import java.awt.event.KeyEvent;
import util.Location;

/**
 * @author Wayne You and Ross Cahoon
 * Calculates and tracks the force for the center of mass and
 * all information used in these calculations
 */
public class CenterMassForce extends Force {
    private static final double DEFAULT_MAGNITUDE = 20;
    private static final double DEFAULT_EXPONENT = 2;
    private static Location ourCenterMassLocation;
    private double myMagnitude;
    private double myExponent;
    
    /**
     * Used to construct the default CenterMassForce object.
     */
    public CenterMassForce() {
        myMagnitude = DEFAULT_MAGNITUDE;
        myExponent = DEFAULT_EXPONENT;
        toggleActiveState();
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
            System.out.println("CENTER TOGGLE");
            toggleActiveState();
        }
    }
    
    /**
     * Assign the center of mass used in calculation. Needed for multiple assemblies
     * @param center the center that the Center of Mass will be assigned to.
     */
    public static void assignCenterOfMass (Location center) {
        ourCenterMassLocation = center;
    }
}
