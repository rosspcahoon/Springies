package simulation;

import util.Vector;

public class ViscosityForce extends AbstractForce {
	double myViscosity;
	
	public ViscosityForce(double viscosity)
	{
		myViscosity=viscosity;
	}
	
	@Override
	public void applyForce(Mass m) {
		Vector viscosityForce=new Vector(m.getVelocity());
		viscosityForce.scale(myViscosity);
		viscosityForce.negate();
		m.applyForce(viscosityForce);
	}

}
