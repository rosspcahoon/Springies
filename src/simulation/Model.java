package simulation;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.List;
import java.util.ArrayList;
import util.Vector;

import view.Canvas;


/**
 * XXX.
 * 
 * @author Robert C. Duvall
 */
public class Model {
    // bounds and input for game
    private Canvas myView;
    // simulation state
    private List<Mass> myMasses;
    private List<Spring> mySprings;
    private Vector myGravity;
// 	  Unimplemented
//    private Vector myViscosity;
//    private Vector myCenterMass;
//    private Vector myWallRepulsion;

    /**
     * Create a game of the given size with the given display for its shapes.
     */
    public Model (Canvas canvas) {
        myView = canvas;
        myMasses = new ArrayList<Mass>();
        mySprings = new ArrayList<Spring>();
        myGravity = null;
        
    }

    /**
     * Draw all elements of the simulation.
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
     * Update simulation for this moment, given the time since the last moment.
     */
    public void update (double elapsedTime) {
        Dimension bounds = myView.getSize();
        for (Spring s : mySprings) {
            s.update(elapsedTime, bounds);
        }
        for (Mass m : myMasses) {
        	if(myGravity != null){
        		myGravity.scale(m.getMass());
        		m.applyForce(myGravity);
        	}        		
            m.update(elapsedTime, bounds);
        }
    }

    /**
     * Add given mass to this simulation.
     */
    public void add (Mass mass) {
        myMasses.add(mass);
    }

    /**
     * Add given spring to this simulation.
     */
    public void add (Spring spring) {
        mySprings.add(spring);
    }
    
    /**
     * Set gravity given the read force.
     */
    public void setGravity (Vector gravity) {
        myGravity = gravity;
    }
    
    /**
     * Set viscosity given read force.
     */
//    public void setViscosity (Vector viscosity) {
//        myViscosity = viscosity;
//    }
    
    /**
     * Set center of mass given read force.
     */
//    public void setCenterMass (Vector centermass) {
//        myCenterMass = centermass;
//    }
}
