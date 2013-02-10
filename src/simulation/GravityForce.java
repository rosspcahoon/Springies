package simulation;

import java.awt.event.KeyEvent;
import java.util.Scanner;
import util.Vector;
/**
 * @author Wayne You and Ross Cahoon
 * Calculates and tracks the gravity force.
 */
public class GravityForce extends Force {
    /**
     * The vector for gravity force.
     */
    private Vector myGravity;
    /**
     * Used to construct the Force object.
     * @param line transformed to a vector and assigned to myGravity
     */
    public GravityForce(Scanner line) {
        gravityCommand(line);
        this.setKeyEvent(KeyEvent.VK_G);
    }
    /**
     * Used to construct the Force object.
     * @param gravity assigned to myGravity
     */
    public GravityForce(Vector gravity) {
        myGravity = gravity;
        this.setKeyEvent(KeyEvent.VK_G);
    }

    @Override
    public final void applyForce(final Mass m) {
        if (this.isForceActive()) {
            Vector massGravity = new Vector(myGravity);
            massGravity.scale(m.getMass());
            m.applyForce(massGravity);
        }
    }

    // create gravity vector from formatted data
    private void gravityCommand(Scanner line) {
        double angle = line.nextDouble();
        double magnitude = line.nextDouble();
        myGravity = new Vector(angle, magnitude);

    }

}