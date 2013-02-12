package simulation;

import java.awt.event.KeyEvent;
import java.util.Scanner;
import util.Vector;
/**
 * @author Wayne You and Ross Cahoon
 * Calculates and tracks the viscosity force.
 */
public class ViscosityForce extends Force {

    private static boolean myViscosityActive = true;
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
     * @param viscosity assigned to myViscosity
     */
    public ViscosityForce(final double viscosity) {
        myViscosity = viscosity;
    }

    @Override
    public void applyForce(final Mass m) {
        if (myViscosityActive) {
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

    public void toggle (int key) {
        if (key == KeyEvent.VK_V) {
            myViscosityActive = !myViscosityActive;
        }
    }
}
