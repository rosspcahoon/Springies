package simulation;

import util.Vector;

public class Gravity extends AbstractForce {
	private Vector myGravity;
	
	public Gravity(Vector gravityValue)
	{
		myGravity=new Vector(gravityValue);
	}

	@Override
	public void ApplyForce(Mass m) {
		Vector massGravity=new Vector(myGravity);
		massGravity.scale(m.getMass());
		m.applyForce(massGravity);
	}

}
