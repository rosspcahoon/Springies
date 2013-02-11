package simulation;

import java.util.List;
import util.Location;

public class Assembly {
    private List<Mass> myMasses;
    private List<Spring> mySprings;
    private Location myCenterMassLocation = new Location();
    
    public void update (double elapsedTime, List<Force> forces) {
        //We could re-assign the center of mass location before each run.
        //I'm not sure what exactly to do designwise here.
        CenterMassForce.updateCenterMass(myMasses);
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
     * Calculates the center of mass location given a system of masses.
     * @param masses is the system used for calculation
     */
    private void updateCenterMass(final List<Mass> masses) {
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
}
