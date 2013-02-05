package simulation;

import util.Vector;

public class Viscosity extends AbstractForce {
	double myViscosity;
	
	public Viscosity(double viscosity)
	{
		myViscosity=viscosity;
	}
	
	@Override
	public void ApplyForce(Mass m) {
		Vector viscosityForce=new Vector(m.getVelocity());
		viscosityForce.scale(myViscosity);
		viscosityForce.negate();
		m.applyForce(viscosityForce);
	}

}
