package simulation;

import java.util.Scanner;
import util.Vector;
/**
 * @author Wayne You and Ross Cahoon
 * Calculates and tracks the viscosity force.
 */
public class ViscosityForce extends Force {
    
    private static boolean ourViscosityActive = true;
    private static final double DEAFULT_VISCOSITY = .1;
    /**
     * The scale of the viscosity force.
     */
    private double myViscosity;
    /**
     * Used to construct the default ViscosityForce object.
     */
    public ViscosityForce() {
        myViscosity = DEAFULT_VISCOSITY;
    }
    /**
     * Used to construct the ViscosityForce object.
     * @param line double is extracted from this and assigned to myViscosity
     */
    public ViscosityForce(Scanner line) {
        viscosityCommand(line);
    }
    /**
     * Used to construct the ViscosityForce object.
     * @param viscosity assigned to myViscosity
     */
    public ViscosityForce(final double viscosity) {
        myViscosity = viscosity;
    }

    @Override
    public void applyForce(final Mass m) {
        if (ourViscosityActive) {
            Vector viscosityForce = new Vector(m.getVelocity());
            viscosityForce.scale(myViscosity);
            viscosityForce.negate();
            m.applyForce(viscosityForce);
        }
    }
    
    // create viscosity force from formatted data
    private void viscosityCommand(Scanner line) {
        myViscosity = line.nextDouble();        
    }

    /**
     * Toggles whether viscosity is active.
     */
    public static void toggleViscosity () {
        ourViscosityActive = !ourViscosityActive;
    }
}
