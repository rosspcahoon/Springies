package simulation;

import java.awt.Dimension;
import java.awt.Point;
import java.util.List;
import view.Canvas;

/**
 * Manages any mouse interaction with the springies
 * @author Poopsy
 *
 */
public class SpringiesMouse {
    private static final double MOUSEKVAL = 0.5;
    private static final double TIMELAPSE = 0;
    //I find the naming convention to be out of place for booleans
    private boolean myWasClicked = false;
    private double mySpringLength;
    private Mass myConnectedMass;
    
    /**
     * Applies force to the nearest mass and maintains the spring length until a mouse release.
     * @param view The canvas containing the mouse data.
     * @param masses The masses to be checked against.
     */
    public void updateMouse (Point mousePosition, List<Mass> masses, Dimension bounds) {
        if(masses.isEmpty())
            return;
        
        if(mousePosition != Canvas.NO_MOUSE_PRESSED && !myWasClicked) {
            Mass mouseMass = new FixedMass(mousePosition.getX(),mousePosition.getY(),-1);
            Mass minMass = masses.get(0);
            double minDistance = minMass.distance(mouseMass);
            for(Mass m : masses) {
                double distance = mouseMass.distance(m);
                if(minDistance>distance)
                {
                    minDistance=distance;
                    minMass=m;
                }
            }
            myConnectedMass=minMass;
            mySpringLength=minDistance;
            
            Spring mouseSpring = new Spring(myConnectedMass,mouseMass,mySpringLength,MOUSEKVAL);
            mouseSpring.update(TIMELAPSE, bounds);
        }
        else if (mousePosition != Canvas.NO_MOUSE_PRESSED && myWasClicked) {
            Mass mouseMass = new FixedMass(mousePosition.getX(),mousePosition.getY(),-1);
            Spring mouseSpring = new Spring(myConnectedMass,mouseMass,mySpringLength,MOUSEKVAL);
            mouseSpring.update(TIMELAPSE, bounds);
        }
        else if(mousePosition == Canvas.NO_MOUSE_PRESSED)
        {
            myWasClicked=false;
        }
    }
    
    
}
