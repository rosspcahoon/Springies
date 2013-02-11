package simulation;

import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Scanner;
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
        this.setKeyEvent(KeyEvent.VK_M);
    }
    /**
     * Used to construct the Force object.
     * @param line assigned to myExponent and myMagnitude
     */
    public CenterMassForce(Scanner line) {
        centerMassCommand(line);
        this.setKeyEvent(KeyEvent.VK_M);
    }
    /**
     * Used to construct the Force object.
     * @param magnitude assigned to myMagnitude
     * @param exponent assigned to myExponent
     */
    public CenterMassForce(double magnitude, double exponent) {
        myMagnitude = magnitude;
        myExponent = exponent;
        this.setKeyEvent(KeyEvent.VK_M);
    }
    /**
     * Calculates and applies a force to the given mass.
     * @param m is the mass that the force is being applied to.
     */
    public final void applyForce(final Mass m) {
        if (this.isForceActive()) {
            util.Vector tVect = new util.Vector(m.getCenter(), ourCenterMassLocation);
            double tDist = Math.abs(m.getCenter().distance(ourCenterMassLocation));
            tVect.setMagnitude(myMagnitude / Math.pow(tDist, myExponent));
            m.applyForce(tVect);
        }
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
    
    // assign centerMass data from formatted data
    private void centerMassCommand(Scanner line) {
        myMagnitude = line.nextDouble();
        myExponent = line.nextDouble();

    }
}
