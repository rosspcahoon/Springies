package simulation;

import java.awt.event.KeyEvent;
import java.util.Scanner;
import util.Vector;
/**
 * @author Wayne You and Ross Cahoon
 * Calculates and tracks the WallRepulsionForce.
 */
public class WallRepulsionForce extends Force {
    private static final double DEFAULT_MAGNITUDE = 1;
    private static final double DEFAULT_EXPONENT = 2;
    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int THREE = 3;
    private static final int FOUR = 4;
    private static final int NINETY = 90;
    private static final int ONE_HUNDRED_EIGHTY = 180;
    private static final int TWO_HUNDRED_SEVENTY = 270;
    private static final int THREE_HUNDRED_SIXTY = 360;

    private Vector myRepulsion;
    private double myExponent;

    /**
     * Used to construct the WallRepulsionForce object.
     * @param keyevent the ID of a wall that generates a force
     */
    public WallRepulsionForce(int keyevent) {
        int wallID = determineWallID(keyevent);
        myRepulsion = new Vector(determineAngle(wallID), DEFAULT_MAGNITUDE);
        myExponent = DEFAULT_EXPONENT;
        this.setKeyEvent(determineKeyEvent(wallID));
    }

    /**
     * Used to construct the WallRepulsionForce object given a line
     * @param line transforms data from the scanner to create a WallRepulsionForce object
     */
    public WallRepulsionForce(Scanner line) {
        wallRepulsionCommand(line);
    }
    /**
     * Used to construct the WallRepulsionForce object.
     * @param wallID the ID of a wall that generates a force
     * @param magnitude the magnitude of the force
     * @param exponent the exponent of the force
     */
    public WallRepulsionForce(int wallID, double magnitude, double exponent) {
        myRepulsion = new Vector(determineAngle(wallID), magnitude);
        myExponent = exponent;
        this.setKeyEvent(determineKeyEvent(wallID));
    }
    @Override
    public void applyForce(final Mass m) {
        if (this.isForceActive()) {
            Vector scaledForce = new Vector(myRepulsion);
            double distance = 0;
            switch((int)scaledForce.getDirection() % THREE_HUNDRED_SIXTY) {
                case ZERO: 
                    distance = Vector.distanceBetween(m.getCenter().y, Model.getSize().height);
                    break;
                case NINETY: 
                    distance = m.getCenter().x;
                    break;
                case ONE_HUNDRED_EIGHTY:
                    distance = m.getCenter().y;
                    break;
                case TWO_HUNDRED_SEVENTY:
                    distance = Vector.distanceBetween(m.getCenter().x, Model.getSize().width);
                    break;
                default:
                    break;
            }

            scaledForce.scale(1 / (Math.pow(distance, myExponent)));
            m.applyForce(scaledForce);
        }
    }
    /**
     * Given a number determines the KeyEvent the force should respond to
     * @param wallID the id of the wall that needs a KeyEvent
     * @return the appropriate KeyEvent int
     */

    public int determineKeyEvent(int wallID) {
        if (wallID == ONE) {
            return KeyEvent.VK_1;        
        }
        else if (wallID == TWO) {
            return KeyEvent.VK_2;
        }
        else if (wallID == THREE) {
            return KeyEvent.VK_3;
        }
        else if (wallID == FOUR) {
            return KeyEvent.VK_4;
        }
        else {
            return 0;
        }
    }
    /**
     * Given a KeyEvent determines the number the force 
     * @param keyevent keyevent that needs a wallId for it
     */

    public int determineWallID(int keyevent) {
        if (keyevent == KeyEvent.VK_1) {
            return ONE;        
        }
        else if (keyevent == KeyEvent.VK_2) {
            return TWO;
        }
        else if (keyevent == KeyEvent.VK_3) {
            return THREE;
        }
        else if (keyevent == KeyEvent.VK_4) {
            return FOUR;
        }
        else {
            return 0;
        }
    }

    /**
     * Given a number determines angle for the force and assigns to to myAngle
     * @param wallID the id of the wall that needs a angle
     */

    public int determineAngle(int wallID) {
        return ((wallID + 1) * NINETY) % THREE_HUNDRED_SIXTY;
    }


    private void wallRepulsionCommand(Scanner line) {
        int wallID = (int) line.nextDouble();
        double angle = determineAngle(wallID);
        double magnitude = line.nextDouble();
        myRepulsion = new Vector(angle, magnitude);
        myExponent = line.nextDouble();
        this.setKeyEvent(determineKeyEvent(wallID));
    }

}
