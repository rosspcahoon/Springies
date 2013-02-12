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
 * @author Robert C. Duvall, Ross Cahoon, Wayne You
 */
public class Model {
    private static final int SIZE_CHANGE_VALUE = 10;
    /**
     * The Dimension of the Canvas we are painting on, this is a hack. This
     * refuses to acknowledge the default package that main is in and be able to
     * use the constants This isn't good practice
     */
    private static final Dimension DEFAULT_DIM = new Dimension(800, 600);
    private static Dimension ourSize = new Dimension(DEFAULT_DIM.width, DEFAULT_DIM.height);
    // simulation state
    private Canvas myView;
    private List <Assembly> myAssemblies;
    private List<Force> myForces;
    private int myLastKey;
    private SpringiesMouse myMouse = new SpringiesMouse();

    /**
     * Create a game of the given size with the given display for its shapes.
     * @param canvas the Canvas that all of our assemblies will be painted on.
     */
    public Model (Canvas canvas) {
        myView = canvas;
        myAssemblies = new ArrayList<Assembly>();
        myForces = new ArrayList<Force>();
    }

    /**
     * Draw all elements of the simulation.
     * @param pen the Graphics2D used to paint with.
     */
    public void paint (Graphics2D pen) {
        for (Assembly a: myAssemblies) {
            a.paint(pen);
        }
        myMouse.paint(pen);
    }

    /**
     * Update simulation for this moment, given the time since the last moment.
     * @param elapsedTime the time that has elapsed since the Model was created.
     */
    public void update (double elapsedTime) {
        inputHandler();
        myMouse.updateMouse(myView.getLastMousePosition(), myAssemblies, ourSize);
        for (Assembly a: myAssemblies) {
            a.update(elapsedTime, myForces);
        }
    }


    public void add (Assembly assembly) {
        myAssemblies.add(assembly);
    }

    /**
     * Add given force to this simulation.
     * @param force the force that is being added to the forces in the model.
     */
    public void add (Force force) {
        myForces.add(force);
    } 
    /**
     * Calls the input handler from the Canvas in
     * order to find out what buttons are pressed
     * and respond accordingly.
     */
    public void inputHandler() {
        int key = myView.getLastKeyPressed();       
        if (myLastKey != key) {
            inputForAssemblies(key);
            inputForForces(key);
            inputForSizeChange(key);
        }        
        myLastKey = key;
    }

    /**
     * Handles input for all operations that involve amending the assemblies.
     * @param key is the set of all keys that are pressed when inputForAssemblies is called
     */
    public void inputForAssemblies(int key) {
        if (key == KeyEvent.VK_N)  {
            myView.loadAdditionalModel();
        }
        if (key == KeyEvent.VK_C) {
            for (Assembly a: myAssemblies) {
                a.clear();
            }
        }
    }
    /**
     * Handles input for all operations that involve amending the forces.
     * @param key is the set of all keys that are pressed when inputForForces is called
     */
    public void inputForForces(int key) {
        if (key == KeyEvent.VK_G) {
            GravityForce.toggleGravity();
        }
        else if (key == KeyEvent.VK_V) {
            ViscosityForce.toggleViscosity();
        }
        else if (key == KeyEvent.VK_M) {
            CenterMassForce.toggleCenterMassForce();
        }
        else if (key == KeyEvent.VK_1) {
            WallRepulsionForce.toggleWallRepulsion(WallRepulsionForce.TOP_WALL_ID);
        }
        else if (key == KeyEvent.VK_2) {
            WallRepulsionForce.toggleWallRepulsion(WallRepulsionForce.RIGHT_WALL_ID);
        }
        else if (key == KeyEvent.VK_3) {
            WallRepulsionForce.toggleWallRepulsion(WallRepulsionForce.BOTTOM_WALL_ID);
        }
        else if (key == KeyEvent.VK_4) {
            WallRepulsionForce.toggleWallRepulsion(WallRepulsionForce.LEFT_WALL_ID);
        }
    }
    /**
     * Handles input for all operations that involve
     * amending the size of the Canvas.
     * @param key is the set of all keys that are pressed when inputForSizeChange is called
     */
    public void inputForSizeChange(int key) {
        if (key == KeyEvent.VK_DOWN) {
            myView.setSize(myView.getWidth(), myView.getHeight() + SIZE_CHANGE_VALUE);
            ourSize.setSize(ourSize.getWidth(), ourSize.getHeight() + SIZE_CHANGE_VALUE);
        }
        if (key == KeyEvent.VK_UP) {
            myView.setSize(myView.getWidth(), myView.getHeight() - SIZE_CHANGE_VALUE);
            ourSize.setSize(ourSize.getWidth(), ourSize.getHeight() - SIZE_CHANGE_VALUE);
        }
        if (key == KeyEvent.VK_RIGHT) {
            myView.setSize(myView.getWidth() + SIZE_CHANGE_VALUE, myView.getHeight());
            ourSize.setSize(ourSize.getWidth() + SIZE_CHANGE_VALUE, ourSize.getHeight());
        }
        if (key == KeyEvent.VK_LEFT) {
            myView.setSize(myView.getWidth() - SIZE_CHANGE_VALUE, myView.getHeight());
            ourSize.setSize(ourSize.getWidth() - SIZE_CHANGE_VALUE, ourSize.getHeight());
        }
    }
    /**
     *Returns the Dimension of the model
     */
    public static Dimension getSize() {
        return ourSize;
    }
}
