package simulation;

import java.util.List;
import util.Location;

/**
 * @author Wayne You and Ross Cahoon
 * Calculates and tracks the force for the center of mass and
 * all information used in these calculations
 */
public class CenterMassForce extends Force {
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
     * Used to construct the Force object.
     * @param magnitude assigned to myMagnitude.
     * @param exponent assigned to myExponent.
     */
    public CenterMassForce(final double magnitude, final double exponent) {
        myMagnitude = magnitude;
        myExponent = exponent;
    }
    /**
     * Calculates and applies a force to the given mass.
     * @param m is the mass that the force is being applied to.
     */
    public final void applyForce(final Mass m) {
        util.Vector tVect = new util.Vector(m.getCenter(), ourCenterMassLocation);
        double tDist = Math.abs(m.getCenter().distance(ourCenterMassLocation));
        tVect.setMagnitude(myMagnitude / Math.pow(tDist, myExponent));
        m.applyForce(tVect);
    }
    /**
     * Calculates the center of mass location given a system of masses.
     * @param masses is the system used for calculation
     */
    public static void updateCenterMass(final List<Mass> masses) {
        double totalMass = 0;
        double tX = 0;
        double tY = 0;
        for (Mass m : masses) {
            totalMass += Math.abs(m.getMass());
            tX += m.getX() * Math.abs(m.getMass());
            tY += m.getY() * Math.abs(m.getMass());
        }
        ourCenterMassLocation.setLocation(tX / totalMass, tY / totalMass);
    }
}
