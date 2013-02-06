package simulation;

import java.util.List;

import util.Location;

public class CenterMassForce extends Force {
	private double myMagnitude;
	private double myExponent;
	private static Location centerMassLocation=new Location();
	
	public CenterMassForce(double magnitude, double exponent)
	{
		myMagnitude=magnitude;
		myExponent=exponent;
	}
	
	@Override
	public void applyForce(Mass m) {
		util.Vector tempVector = new util.Vector(m.getCenter(), centerMassLocation);
		double tempDistance = Math.abs(m.getCenter().distance(centerMassLocation));
		tempVector.setMagnitude(myMagnitude/Math.pow(tempDistance,myExponent));
		m.applyForce(tempVector);
	}

	public static void updateCenterMass(List<Mass> masses)
	{
		double totalMass = 0;
		double tempX = 0;
		double tempY = 0;
		for(Mass m : masses){
			totalMass += Math.abs(m.getMass());
			tempX += m.getX()*Math.abs(m.getMass());
			tempY += m.getY()*Math.abs(m.getMass());
		}
		centerMassLocation.setLocation(tempX/totalMass, tempY/totalMass);
	}
}
