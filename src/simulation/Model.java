package simulation;


import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.List;
import java.util.ArrayList;

import util.Location;
import util.Vector;

import view.Canvas;


/**
 * XXX.
 * 
 * @author Robert C. Duvall
 */
public class Model {
    //This refuses to acknowledge the default package
    //that main is in and be able to use the constants
    //This isn't good practice
    public static final Dimension SIZE = new Dimension(800, 600);
    // bounds and input for game
    private Canvas myView;
    // simulation state
    private List<Mass> myMasses;
    private List<Spring> mySprings;
    private List<Force> myForces;
    private Vector myGravity;
    private double myViscosity;
    private ArrayList<Vector> myWallRepulsion=new ArrayList<Vector>();
    private ArrayList<Double> myWallRepulsionExponents=new ArrayList<Double>();
    private boolean myCenterMass;
    private double myCenterMassMagnitude;
    private double myCenterMassExponent;
    private Location myCenterMassLocation = new Location();
    private double myTotalMass;


    /**
     * Create a game of the given size with the given display for its shapes.
     */
    public Model (Canvas canvas) {
        myView = canvas;
        myMasses = new ArrayList<Mass>();
        mySprings = new ArrayList<Spring>();
        myGravity = null;
        myCenterMass = false;
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
        updateCenterMass();
        Dimension bounds = myView.getSize();
        for (Spring s : mySprings) {
            s.update(elapsedTime, bounds);
        }
        for (Mass m : myMasses) {
            updateGravity(m);
            updateCenterMassForce(m);
            updateViscosity(m);
            updateWallRepulsion(m);
            m.update(elapsedTime, bounds);
        }
    }
    /**
     * Update force of center of mass on a given mass
     */
    public void updateCenterMassForce(Mass m){
        if(myCenterMass){
            //
            // You might want to look into if you are aliasing the vectors. I
            // don't believe he added a clone function for Vector.
            //
            util.Vector tempVector = new util.Vector(m.getCenter(), myCenterMassLocation);
            double tempDistance = Math.abs(m.getCenter().distance(myCenterMassLocation));
            tempVector.setMagnitude(myCenterMassMagnitude/Math.pow(tempDistance,myCenterMassExponent));
            m.applyForce(tempVector);
        }
    }
    /**
     * Update force of gravity on a given mass
     */

    public void updateGravity(Mass m){
        if(myGravity != null){
            Vector massGravity=new Vector(myGravity);
            massGravity.scale(m.getMass());
            m.applyForce(massGravity);
        }     
    }

    /**
     * Update force of viscosity on a given mass
     */
    //I'm not sure if this works, I have no test data, and I want to sleep
    public void updateViscosity(Mass m){
        if(myViscosity != 0){
            Vector viscosityForce=new Vector(m.getVelocity());
            viscosityForce.scale(myViscosity);
            viscosityForce.negate();
            m.applyForce(viscosityForce);
        }
    }

    /**
     * Update total mass in system
     */

    public void updateCenterMass(){
        if(myCenterMass){
            myTotalMass = 0;
            double tempX = 0;
            double tempY = 0;
            for(Mass m : myMasses){
                myTotalMass += Math.abs(m.getMass());
                tempX += m.getX()*Math.abs(m.getMass());
                tempY += m.getY()*Math.abs(m.getMass());
            }
            myCenterMassLocation.setLocation(tempX/myTotalMass, tempY/myTotalMass);
        }
    }

    /**
     * Update force of wall repulsion on a given mass
     */
    //I'm not sure if this works, I have no test data, and I want to sleep
    public void updateWallRepulsion(Mass m){
        if(!myWallRepulsion.isEmpty()){
            for(int n=0;n<myWallRepulsion.size();n++)
            {
                Vector scaledForce=new Vector(myWallRepulsion.get(n));
                double distance=0;

                switch((int)scaledForce.getDirection()%360)
                {
                    case 0:
                    {
                        distance=Vector.distanceBetween(m.getCenter().y,SIZE.height);
                        break;
                    }
                    case 90:
                    {
                        distance=m.getCenter().x;
                        break;
                    }
                    case 180:
                    {
                        distance=m.getCenter().y;
                        break;
                    }
                    case 270:
                    {
                        distance=Vector.distanceBetween(m.getCenter().x,SIZE.width);
                        break;
                    }
                }

                scaledForce.scale(1/(Math.pow(distance,myWallRepulsionExponents.get(n))));

                m.applyForce(scaledForce);

            }//End foreach
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
     * Add given force to this simulation.
     */
    public void add (Force force) {
        myForces.add(force);
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
    public void setViscosity (double viscosity) {
        myViscosity = viscosity;
    }

    /**
     * Set center of mass given read force.
     */
    public void setCenterMass (double[] centermass) {
        myCenterMassMagnitude = centermass[0];
        myCenterMassExponent = centermass[1];
        myCenterMass = true;
        System.out.println("Centermass Set");
    }

    /**
     * Set wall repulsion given a wall ID and exponent
     */
    public void setWallRepulsion(double[] wallRepulsion) {
        //Top force is 1 facing 180 down.
        //Second value is the exponent value
        myWallRepulsion.add(new Vector(90*(wallRepulsion[0]+1), wallRepulsion[1]));
        myWallRepulsionExponents.add(wallRepulsion[2]);
    }


}
