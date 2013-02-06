package simulation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import util.Location;
import util.Pixmap;
import util.Sprite;
import util.Vector;


/**
 * @author Robert C. Duvall
 */
public class Mass extends Sprite {    
    /**
     * The Dimension for the mass.
     */
    public static final Dimension DEFAULT_SIZE = new Dimension(16, 16);
    /**
     * The Pixmap for the mass.
     */
    public static final Pixmap DEFUALT_IMAGE = new Pixmap("mass.gif");

    private double myMass;
    private Vector myAcceleration;


    /**
     * The constructor for the given Mass.
     * @param x the x location for the Mass
     * @param y the y location for the Mass
     * @param mass the magnitude of mass for Mass
     */
    public Mass (final double x, final double y, final double mass) {
        super(DEFUALT_IMAGE, new Location(x, y), DEFAULT_SIZE);
        myMass = mass;
        myAcceleration = new Vector();
    }

    @Override
    public void update (double elapsedTime, Dimension bounds) {
        applyForce(getBounce(bounds));
        // convert force back into Mover's velocity
        getVelocity().sum(myAcceleration);
        myAcceleration.reset();
        // move mass by velocity
        super.update(elapsedTime, bounds);
    }

    @Override
    public void paint (Graphics2D pen) {
        pen.setColor(Color.BLACK);
        pen.fillOval((int)getLeft(), (int)getTop(), (int)getWidth(), (int)getHeight());
    }

    /**
     * Use the given force to change this mass's acceleration.
     * @param force applies the given force to the mass.
     */
    public void applyForce (Vector force) {
        myAcceleration.sum(force);
    }

    /**
     * Convenience method.
     * @param other the mass that is being used to find the distance of from current Mass.
     */
    public double distance (Mass other) {
        // this is a little awkward, so hide it
        return new Location(getX(), getY()).distance(new Location(other.getX(), other.getY()));
    }
    /**
     * Finds the magnitude of mass for the current Mass
     * @return returns the mass.
     */
    public double getMass() {
        return myMass;
    }

    // check for move out of bounds
    private Vector getBounce (Dimension bounds) {
        final double IMPULSE_MAGNITUDE = 2;
        Vector impulse = new Vector();
        if (getLeft() - 1 < 0) {
            impulse = new Vector(RIGHT_DIRECTION, IMPULSE_MAGNITUDE);
        }
        else if (getRight() + 1 > bounds.width) {
            impulse = new Vector(LEFT_DIRECTION, IMPULSE_MAGNITUDE);
        }
        if (getTop() - 1 < 0) {
            impulse = new Vector(DOWN_DIRECTION, IMPULSE_MAGNITUDE);
        }
        else if (getBottom() + 1 > bounds.height) {
            impulse = new Vector(UP_DIRECTION, IMPULSE_MAGNITUDE);
        }
        impulse.scale(getVelocity().getRelativeMagnitude(impulse));
        return impulse;
    }
}
