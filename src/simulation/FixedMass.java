package simulation;

import java.awt.Color;
import java.awt.Dimension;

/**
 * XXX.
 * 
 * @author Ross Cahoon
 */

public class FixedMass extends Mass {

	public FixedMass(double x, double y, double mass) {
		super(x,y,mass);
	}
	@Override
	public void update (double elapsedTime, Dimension bounds) {
	}
}
