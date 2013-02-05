package simulation;

import util.Vector;

public class WallRepulsion extends AbstractForce {
	Vector myRepulsion;
	double myExponent;

	public WallRepulsion(int wallID, double magnitude, double exponent)
	{
		double angle=((wallID+1)*90)%360;
		myRepulsion=new Vector(angle,magnitude);
		myExponent=exponent;
	}
	@Override
	public void ApplyForce(Mass m) {
		Vector scaledForce=new Vector(myRepulsion);
		double distance=0;
		switch((int)scaledForce.getDirection()%360)
		{
			case 0:
			{
				distance=Vector.distanceBetween(m.getCenter().y,Model.SIZE.height);
				break;
			}
			case 90:
			{
				distance=m.getCenter().x;
				break;
			}
			case 180:
			{
				distance=m.getCenter().y;
				break;
			}
			case 270:
			{
				distance=Vector.distanceBetween(m.getCenter().x,Model.SIZE.width);
				break;
			}
		}
		
		scaledForce.scale(1/(Math.pow(distance,myExponent)));
		
		m.applyForce(scaledForce);
	}

}
