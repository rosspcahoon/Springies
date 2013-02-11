package simulation;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.List;
import view.Canvas;


/**
 * Manages any mouse interaction with the springies
 * 
 * @author Poopsy
 * 
 */
public class SpringiesMouse {
    private static final double MOUSEKVAL = 0.5;
    private static final double TIMELAPSE = 0;
    // I find the naming convention to be out of place for booleans
    private boolean myWasClicked = false;
    private double mySpringLength;
    private Mass myConnectedMass;
    private Spring myMouseSpring;

    /**
     * Applies force to the nearest mass and maintains the spring length until a mouse release.
     * 
     * @param mousePosition The location of the mouse right now.
     * @param masses The masses to be checked against.
     * @param bounds The current limits of the view. These aren't actually used.
     */
    public void updateMouse (Point mousePosition, List<Mass> masses, Dimension bounds) {
        if (masses.isEmpty()) {
            return;
        }

        if (mousePosition != Canvas.NO_MOUSE_PRESSED && !myWasClicked) {
            Mass mouseMass = new FixedMass(mousePosition.getX(), mousePosition.getY(), -1);
            Mass minMass = masses.get(0);
            double minDistance = minMass.distance(mouseMass);
            for (Mass m : masses) {
                double distance = mouseMass.distance(m);
                if (minDistance > distance) {
                    minDistance = distance;
                    minMass = m;
                }
            }
            myConnectedMass = minMass;
            mySpringLength = minDistance;

            myMouseSpring = new Spring(myConnectedMass, mouseMass, mySpringLength, MOUSEKVAL);
            myMouseSpring.update(TIMELAPSE, bounds);
            
            myWasClicked = true;
        }
        else if (mousePosition != Canvas.NO_MOUSE_PRESSED && myWasClicked) {
            Mass mouseMass = new FixedMass(mousePosition.getX(), mousePosition.getY(), -1);
            myMouseSpring = new Spring(myConnectedMass, mouseMass, mySpringLength, MOUSEKVAL);
            myMouseSpring.update(TIMELAPSE, bounds);
            
        }
        else if (mousePosition == Canvas.NO_MOUSE_PRESSED) {
            myWasClicked = false;
        }
    }
    
    /**
     * Paints the spring if the mouse is clicked.
     * 
     * @param pen The pen passed for painting.
     */
    public void paint(Graphics2D pen) {
        if (myWasClicked) {
            myMouseSpring.paint(pen);
        }
    }

}
