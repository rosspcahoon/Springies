package simulation;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import view.Canvas;


/**
 * XXX.
 * 
 * @author Robert C. Duvall
 */
public class Model {

    /**
     * The Dimension of the Canvas we are painting on, this is a hack. This
     * refuses to acknowledge the default package that main is in and be able to
     * use the constants This isn't good practice
     */
    public static final Dimension DEFAULT_DIMENSION = new Dimension(800, 600);
    /**
     * The distance a screen dimension shift will be.
     */
    public static final double SHIFT_DISTANCE = 10;
    private Dimension mySize = new Dimension(DEFAULT_DIMENSION.width, DEFAULT_DIMENSION.height);
    // simulation state
    private Canvas myView;
    private List<Mass> myMasses;
    private List<Spring> mySprings;
    private List<Force> myForces;
    private SpringiesMouse myMouse = new SpringiesMouse();

    /**
     * Create a game of the given size with the given display for its shapes.
     * 
     * @param canvas
     *        the Canvas that all of our assemblies will be painted on.
     */
    public Model (Canvas canvas) {
        myView = canvas;
        myMasses = new ArrayList<Mass>();
        mySprings = new ArrayList<Spring>();
        myForces = new ArrayList<Force>();
    }

    /**
     * Draw all elements of the simulation.
     * 
     * @param pen
     *        the Graphics2D used to paint with.
     */
    public void paint (Graphics2D pen) {
        for (Spring s : mySprings) {
            s.paint(pen);
        }
        for (Mass m : myMasses) {
            m.paint(pen);
        }
        
        myMouse.paint(pen);
    }

    /**
     * Update simulation for this moment, given the time since the last moment.
     * 
     * @param elapsedTime
     *        the time that has elapsed since the Model was created.
     */
    public void update (double elapsedTime) {
        myMouse.updateMouse(myView.getLastMousePosition(), myMasses, mySize);

        CenterMassForce.updateCenterMass(myMasses);
        for (Spring s : mySprings) {
            s.update(elapsedTime, mySize);
        }
        for (Mass m : myMasses) {
            for (Force f : myForces) {
                f.applyForce(m);
            }
            m.update(elapsedTime, mySize);
        }
    }

    /**
     * Add given mass to this simulation.
     * 
     * @param mass
     *        the mass that is being added to the masses in the model.
     */
    public void add (Mass mass) {
        myMasses.add(mass);
    }

    /**
     * Add given spring to this simulation.
     * 
     * @param spring
     *        the spring that is being added to the springs in the model.
     */
    public void add (Spring spring) {
        mySprings.add(spring);
    }

    /**
     * Add given force to this simulation.
     * 
     * @param force
     *        the force that is being added to the forces in the model.
     */
    public void add (Force force) {
        myForces.add(force);
    }

    private void updateBounds () {
        if (myView.getLastKeyPressed() == KeyEvent.VK_DOWN) {
            double newHeight = mySize.getHeight() - SHIFT_DISTANCE;
            if (newHeight < 0) {
                newHeight = 0;
            }
            mySize.setSize(mySize.getWidth(), newHeight);
        }
        else if (myView.getLastKeyPressed() == KeyEvent.VK_UP) {
            mySize.setSize(mySize.getWidth(), mySize.getHeight() + SHIFT_DISTANCE);
        }
        else if (myView.getLastKeyPressed() == KeyEvent.VK_LEFT) {
            double newWidth = mySize.getWidth() - SHIFT_DISTANCE;
            if (newWidth < 0) {
                newWidth = 0;
            }
            mySize.setSize(newWidth, mySize.getHeight());
        }
        else if (myView.getLastKeyPressed() == KeyEvent.VK_RIGHT) {
            mySize.setSize(mySize.getWidth() + SHIFT_DISTANCE, mySize.getHeight());
        }
    }

}
