package simulation;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import view.Canvas;


/**
 * XXX.
 * 
 * @author Robert C. Duvall
 */
public class Model {



    private static final int KEY_N = KeyEvent.VK_N;
    private static final int KEY_C = KeyEvent.VK_C;
    private static final int KEY_G = KeyEvent.VK_G;
    private static final int KEY_V = KeyEvent.VK_V;
    private static final int KEY_M = KeyEvent.VK_M;
    private static final int KEY_1 = KeyEvent.VK_1;
    private static final int KEY_2 = KeyEvent.VK_2;
    private static final int KEY_3 = KeyEvent.VK_3;
    private static final int KEY_4 = KeyEvent.VK_4;
    private static final int KEY_DOWN = KeyEvent.VK_DOWN;
    private static final int KEY_UP = KeyEvent.VK_UP;
    private static final int KEY_RIGHT = KeyEvent.VK_RIGHT;
    private static final int KEY_LEFT = KeyEvent.VK_LEFT;
    private static final int SIZE_CHANGE_VALUE = 10;
    /**
     * The Dimension of the Canvas we are painting on, this is a hack. This
     * refuses to acknowledge the default package that main is in and be able to
     * use the constants This isn't good practice
     */
    private static final Dimension DEFAULT_DIMENSION = new Dimension(800, 600);
    private static Dimension mySize = new Dimension(DEFAULT_DIMENSION.width, DEFAULT_DIMENSION.height);
    // simulation state
    private Canvas myView;
    private List<Mass> myMasses;
    private List<Spring> mySprings;
    private Map<Integer, Force> myForces;
    private int myLastKey;
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
        myForces = new HashMap<Integer, Force>();
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
        inputHandler();
        myMouse.updateMouse(myView.getLastMousePosition(), myMasses, mySize);
        CenterMassForce.updateCenterMass(myMasses);
<<<<<<< HEAD
        //Look here to change size of walled area
        Dimension bounds = myView.getSize();
=======
>>>>>>> ross
        for (Spring s : mySprings) {
            s.update(elapsedTime, mySize);
        }
        for (Mass m : myMasses) {
            for (int f: myForces.keySet()) {
                myForces.get(f).applyForce(m);
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
        myForces.put(force.getKeyEvent(), force);
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
     * Handles input for all operations that involve
     * amending the assemblies.
     * @param key is the set of all keys that are pressed when inputForAssemblies is called
     */
    public void inputForAssemblies(int key) {
        if (key == KEY_N)  {
            myView.loadAdditionalModel();
        }
        if (key == KEY_C) {
            myMasses.clear();
            mySprings.clear();
        }
    }
    /**
     * Handles input for all operations that involve
     * amending the forces.
     * @param key is the set of all keys that are pressed when inputForForces is called
     */
    public void inputForForces(int key) {
        if (myForces.keySet().contains(key)) {
            for (int k: myForces.keySet()) {
                if (key == k) {
                    myForces.get(k).toggleForce();
                }
            }
        }
        else if (key == KEY_G) {
            add(new GravityForce());
        }
        else if (key == KEY_V) {
            add(new ViscosityForce());
        }
        else if (key == KEY_M) {
            add(new CenterMassForce());
        }
        else if ((key == KEY_1) || (key == KEY_2) || (key == KEY_3) || (key == KEY_4)) {
            add(new WallRepulsionForce(key));
        }
    }
    /**
     * Handles input for all operations that involve
     * amending the size of the Canvas.
     * @param key is the set of all keys that are pressed when inputForSizeChange is called
     */
    public void inputForSizeChange(int key) {
        if (key == KEY_DOWN) {
            myView.setSize(myView.getWidth(), myView.getHeight() + SIZE_CHANGE_VALUE);
            mySize.setSize(mySize.getWidth(), mySize.getHeight() + SIZE_CHANGE_VALUE);
        }
        if (key == KEY_UP) {
            myView.setSize(myView.getWidth(), myView.getHeight() - SIZE_CHANGE_VALUE);
            mySize.setSize(mySize.getWidth(), mySize.getHeight() - SIZE_CHANGE_VALUE);
        }
        if (key == KEY_RIGHT) {
            myView.setSize(myView.getWidth() + SIZE_CHANGE_VALUE, myView.getHeight());
            mySize.setSize(mySize.getWidth() + SIZE_CHANGE_VALUE, mySize.getHeight());
        }
        if (key == KEY_LEFT) {
            myView.setSize(myView.getWidth() - SIZE_CHANGE_VALUE, myView.getHeight());
            mySize.setSize(mySize.getWidth() - SIZE_CHANGE_VALUE, mySize.getHeight());
        }
    }
    /**
     *Returns the Dimension of the model
     */
    public static Dimension getSize() {
        return mySize;
    }
}
