package simulation;

import util.Vector;
/**
 * @author Wayne You and Ross Cahoon
 * Calculates and tracks the viscosity force.
 */
public class ViscosityForce extends Force {
    /**
     * The scale of the viscosity force.
     */
    private double myViscosity;
    /**
     * Used to construct the Force object.
     * @param viscosity assigned to myViscosity
     */
    public ViscosityForce(final double viscosity) {
        myViscosity = viscosity;
    }

    @Override
    public void applyForce(final Mass m) {
        Vector viscosityForce = new Vector(m.getVelocity());
        viscosityForce.scale(myViscosity);
        viscosityForce.negate();
        m.applyForce(viscosityForce);
    }

}
