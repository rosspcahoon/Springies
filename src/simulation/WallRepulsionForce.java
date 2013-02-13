package simulation;

import java.awt.event.KeyEvent;
import util.Vector;


/**
 * @author Wayne You and Ross Cahoon
 *         Calculates and tracks the WallRepulsionForce.
 */
public class WallRepulsionForce extends Force {
    private static final double DEFAULT_MAGNITUDE = 1;
    private static final double DEFAULT_EXPONENT = 2;
    private static final int RIGHT = 0;
    private static final int DOWN = 90;
    private static final int LEFT = 180;
    private static final int UP = 270;
    public static final int TOP_WALL_ID = 1;
    public static final int RIGHT_WALL_ID = 2;
    public static final int BOTTOM_WALL_ID = 3;
    public static final int LEFT_WALL_ID = 4;

    private Vector myRepulsion;
    private double myExponent;
    private int myWallID;

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
        myWallID = wallID;
    }

    @Override
    protected Vector generateForce (Mass m) {
        Vector scaledForce = new Vector(myRepulsion);
        double distance = 0;
        switch (myWallID) {
            case BOTTOM_WALL_ID:
                distance = Math.abs(m.getCenter().y - Model.getSize().height);
                break;
            case LEFT_WALL_ID:
                distance = m.getCenter().x;
                break;
            case TOP_WALL_ID:
                distance = m.getCenter().y;
                break;
            case RIGHT_WALL_ID:
                distance = Math.abs(m.getCenter().x - Model.getSize().width);
                break;
            default:
                break;
        }

        scaledForce.scale(1 / (Math.pow(distance, myExponent)));
        return scaledForce;
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

    /**
     * Toggled the specific wall repulsion force given a wallID
     * @param key The key pressed
     */
    public void toggle (int key) {
        if (key == KeyEvent.VK_1 && myWallID == TOP_WALL_ID) {
            toggleActiveState();
        }
        else if (key == KeyEvent.VK_2 && myWallID == RIGHT_WALL_ID) {
            toggleActiveState();
        }
        else if (key == KeyEvent.VK_3 && myWallID == BOTTOM_WALL_ID) {
            toggleActiveState();
        }
        else if (key == KeyEvent.VK_4 && myWallID == LEFT_WALL_ID) {
            toggleActiveState();
        }
    }
}
