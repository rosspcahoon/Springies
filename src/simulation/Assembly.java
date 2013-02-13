package simulation;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import util.Location;
/**
 * Manages groups of masses that were loaded together
 * @author Ross Cahoon and Wayne You
 *
 */

public class Assembly {
    private List<Mass> myMasses;
    private List<Spring> mySprings;
    private Location myCenterMassLocation = new Location();
    
    /**
     * Constructor that creates an Assembly that holds a Masses and Springs
     */
    public Assembly () {
        myMasses = new ArrayList<Mass>();
        mySprings = new ArrayList<Spring>();
    }

    /**
     * Updates the Masses and Springs in the Assembly
     * @param elapsedTime time elapsed since simulation started
     * @param forces forces that can act on Masses in the Assembly
     */
    public void update (double elapsedTime, List<Force> forces) {
        updateCenterMass(myMasses);
        CenterMassForce.assignCenterOfMass(myCenterMassLocation);
        for (Spring s : mySprings) {
            s.update(elapsedTime, Model.getSize());
        }
        for (Mass m : myMasses) {
            for (Force f: forces) {
                f.applyForce(m);
            }
            m.update(elapsedTime, Model.getSize());
        }
    }

    /**
     * Draw all elements of the simulation.
     * @param pen the Graphics2D used to paint with.
     */
    public void paint (Graphics2D pen) {
        for (Spring s : mySprings) {
            s.paint(pen);
        }
        for (Mass m : myMasses) {
            m.paint(pen);
        }
    }

    /**
     * Calculates the center of mass location given a system of masses.
     * @param masses is the system used for calculation
     */
    public void updateCenterMass(final List<Mass> masses) {
        double totalMass = 0;
        double tX = 0;
        double tY = 0;
        for (Mass m : masses) {
            totalMass += Math.abs(m.getMass());
            tX += m.getX() * Math.abs(m.getMass());
            tY += m.getY() * Math.abs(m.getMass());
        }
        myCenterMassLocation.setLocation(tX / totalMass, tY / totalMass);
    }
    
    /**
     * Adds a spring to the assembly
     * @param spring the spring to be added
     */
    public void add (Spring spring) {
        mySprings.add(spring); 
    }
    
    /**
     * Adds a mass to the assembly
     * @param mass the mass to be added
     */
    public void add (Mass mass) {
        myMasses.add(mass);
    }

    /**
     * Returns the masses in the Assembly
     */
    public List<Mass> getMasses() {
        return myMasses;
    }

    /**
     * Clears all masses and springs in the assembly
     */
    public void clear () {
        myMasses.clear();
        mySprings.clear();
    }
}
