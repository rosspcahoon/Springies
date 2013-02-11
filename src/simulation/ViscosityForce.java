package simulation;

import java.awt.event.KeyEvent;
import java.util.Scanner;
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
        System.out.println("Made Viscosity Force");
        myViscosity = DEAFULT_VISCOSITY;
        this.setKeyEvent(KeyEvent.VK_V);
    }
    /**
     * Used to construct the ViscosityForce object.
     * @param line double is extracted from this and assigned to myViscosity
     */
    public ViscosityForce(Scanner line) {
        viscosityCommand(line);
        this.setKeyEvent(KeyEvent.VK_V);
    }
    /**
     * Used to construct the ViscosityForce object.
     * @param viscosity assigned to myViscosity
     */
    public ViscosityForce(final double viscosity) {
        myViscosity = viscosity;
        this.setKeyEvent(KeyEvent.VK_V);
    }

    @Override
    public void applyForce(final Mass m) {
        if (this.isForceActive()) {
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

}
