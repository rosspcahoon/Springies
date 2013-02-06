package simulation;

import util.Vector;

public class GravityForce extends AbstractForce {
	private Vector myGravity;
	
	public GravityForce(Vector gravityValue)
	{
		myGravity=new Vector(gravityValue);
	}

	@Override
	public void applyForce(Mass m) {
		Vector massGravity=new Vector(myGravity);
		massGravity.scale(m.getMass());
		m.applyForce(massGravity);
	}

}
