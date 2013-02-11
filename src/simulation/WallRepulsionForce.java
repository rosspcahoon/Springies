package simulation;

import java.util.Scanner;
import util.Vector;


/**
 * @author Wayne You and Ross Cahoon
 *         Calculates and tracks the WallRepulsionForce.
 */
public class WallRepulsionForce extends Force {
    private static final double DEFAULT_MAGNITUDE = 1;
    private static final double DEFAULT_EXPONENT = 2;
    private static final int UP = 0;
    private static final int LEFT = 90;
    private static final int DOWN = 180;
    private static final int RIGHT = 270;
    public static final int TOP_WALL_ID = 1;
    public static final int RIGHT_WALL_ID = 2;
    public static final int BOTTOM_WALL_ID = 3;
    public static final int LEFT_WALL_ID = 4;

    private static boolean[] ourActiveWalls = {true, true, true, true};

    private Vector myRepulsion;
    private double myExponent;
    private int myWallID;

    /**
     * Used to construct the WallRepulsionForce object given a line
     * 
     * @param line transforms data from the scanner to create a WallRepulsionForce object
     */
    public WallRepulsionForce (Scanner line) {
        wallRepulsionCommand(line);
    }
    
    /**
     * Default value constructor.
     * @param wallID Which wall the repulsion is from.
     */
    public WallRepulsionForce(int wallID) {
        myRepulsion = new Vector(determineAngle(wallID), DEFAULT_MAGNITUDE);
        myExponent = DEFAULT_EXPONENT;
        myWallID = wallID;
    }

    /**
     * Used to construct the WallRepulsionForce object.
     * 
     * @param wallID the ID of a wall that generates a force
     * @param magnitude the magnitude of the force
     * @param exponent the exponent of the force
     */
    public WallRepulsionForce (int wallID, double magnitude, double exponent) {
        myRepulsion = new Vector(determineAngle(wallID), magnitude);
        myExponent = exponent;
    }

    @Override
    public void applyForce (final Mass m) {
        if (!ourActiveWalls[myWallID - 1]) {
            return;
        }
        Vector scaledForce = new Vector(myRepulsion);
        double distance = 0;
        switch (myWallID) {
            case BOTTOM_WALL_ID:
                distance = Vector.distanceBetween(m.getCenter().y, Model.getSize().height);
                break;
            case LEFT_WALL_ID:
                distance = m.getCenter().x;
                break;
            case TOP_WALL_ID:
                distance = m.getCenter().y;
                break;
            case RIGHT_WALL_ID:
                distance = Vector.distanceBetween(m.getCenter().x, Model.getSize().width);
                break;
            default:
                break;
        }

        scaledForce.scale(1 / (Math.pow(distance, myExponent)));
        m.applyForce(scaledForce);
    }

    /**
     * Given a number determines angle for the force and assigns to to myAngle
     * 
     * @param wallID the id of the wall that needs a angle
     */

    public int determineAngle (int wallID) {
        switch (wallID) {
            case TOP_WALL_ID:
                return DOWN;
            case BOTTOM_WALL_ID:
                return UP;
            case RIGHT_WALL_ID:
                return LEFT;
            case LEFT_WALL_ID:
                return RIGHT;
            default:
                return -1;
        }
    }

    private void wallRepulsionCommand (Scanner line) {
        myWallID = (int) line.nextDouble();
        double angle = determineAngle(myWallID);
        double magnitude = line.nextDouble();
        myRepulsion = new Vector(angle, magnitude);
        myExponent = line.nextDouble();
    }

    /**
     * Toggled the specific wall repulsion force given a wallID
     * @param wallID The wallID of the force to toggle
     */
    public static void toggleWallRepulsion(int wallID) {
        ourActiveWalls[wallID - 1] = !ourActiveWalls[wallID - 1];
    }
}
