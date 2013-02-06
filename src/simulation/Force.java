package simulation;
/**
 * A force that acts on a mass.
 * @author Wayne You and Ross Cahoon
 *
 */
public abstract class Force {
    /**
     * Applies a force to a given mass
     * @param m the mass that the force is being applied
     */
    public abstract void applyForce(Mass m);
}
