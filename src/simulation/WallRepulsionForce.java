package simulation;

import util.Vector;
/**
 * @author Wayne You and Ross Cahoon
 * Calculates and tracks the WallRepulsionForce.
 */
public class WallRepulsionForce extends Force {
    private static final int ZERO = 0;
    private static final int NINETY = 90;
    private static final int ONE_HUNDRED_EIGHTY = 180;
    private static final int TWO_HUNDRED_SEVENTY = 270;
    private static final int THREE_HUNDRED_SIXTY = 360;

    private Vector myRepulsion;
    private double myExponent;


    /**
     * Used to construct the Force object.
     * @param wallID the ID of a wall that generates a force
     * @param magnitude the magnitude of the force
     * @param exponent the exponent of the force
     */
    public WallRepulsionForce(final int wallID, final double magnitude, final double exponent)
    {
        double angle = ((wallID + 1) * NINETY) % THREE_HUNDRED_SIXTY;
        myRepulsion = new Vector(angle, magnitude);
        myExponent = exponent;
    }
    @Override
    public void applyForce(final Mass m) {
        Vector scaledForce = new Vector(myRepulsion);
        double distance = 0;
        switch((int)scaledForce.getDirection() % THREE_HUNDRED_SIXTY) {
            case ZERO:
                distance = Vector.distanceBetween(m.getCenter().y, Model.DEFAULT_DIMENSION.height);
                break;
            case NINETY:
                distance = m.getCenter().x;
                break;
            case ONE_HUNDRED_EIGHTY:
                distance = m.getCenter().y;
                break;
            case TWO_HUNDRED_SEVENTY:
                distance = Vector.distanceBetween(m.getCenter().x, Model.DEFAULT_DIMENSION.width);
                break;
            default:
                break;
        }

        scaledForce.scale(1 / (Math.pow(distance, myExponent)));

        m.applyForce(scaledForce);
    }

}
