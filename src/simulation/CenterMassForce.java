package simulation;


import java.util.List;
import java.util.Scanner;
import util.Location;

/**
 * @author Wayne You and Ross Cahoon
 * Calculates and tracks the force for the center of mass and
 * all information used in these calculations
 */
public class CenterMassForce extends Force {
    
    private static boolean ourCenterMassActive = true;
    private static final double DEFAULT_MAGNITUDE = 10;
    private static final double DEFAULT_EXPONENT = 2;
    /**
     * The location where the force is directed.
     */
    private static Location ourCenterMassLocation = new Location();
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
    public final void applyForce(final Mass m) {
        if (ourCenterMassActive) {
            util.Vector tVect = new util.Vector(m.getCenter(), ourCenterMassLocation);
            double tDist = Math.abs(m.getCenter().distance(ourCenterMassLocation));
            tVect.setMagnitude(myMagnitude / Math.pow(tDist, myExponent));
            m.applyForce(tVect);
        }
    }

    // assign centerMass data from formatted data
    private void centerMassCommand(Scanner line) {
        myMagnitude = line.nextDouble();
        myExponent = line.nextDouble();

    }
    
    /**
     * Toggles whether the center of mass force is active.
     */
    public static void toggleCenterMassForce() {
        ourCenterMassActive = !ourCenterMassActive;
    }
}
