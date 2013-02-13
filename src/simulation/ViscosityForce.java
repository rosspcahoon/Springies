package simulation;

import java.awt.event.KeyEvent;
import util.Vector;
/**
 * @author Wayne You and Ross Cahoon
 * Calculates and tracks the viscosity force.
 */
public class ViscosityForce extends Force {

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
        toggleActiveState();
    }
    /**
     * Used to construct the ViscosityForce object.
     * @param viscosity assigned to myViscosity
     */
    public ViscosityForce(final double viscosity) {
        myViscosity = viscosity;
    }

    @Override
    protected Vector generateForce(final Mass m) {
        Vector viscosityForce = new Vector(m.getVelocity());
        viscosityForce.scale(myViscosity);
        viscosityForce.negate();
        return viscosityForce;
    }

    /**
     * Potentially toggles the active state of the force given a key press.
     * @param key The key pressed to be checked
     */
    public void toggle (int key) {
        if (key == KeyEvent.VK_V) {
            System.out.println("VIS TOGGLE");
            toggleActiveState();
        }
    }
}
