
package simulation;

import java.awt.Dimension;

public class Muscle extends Spring {
	private double myAmplitude;
	private double myTime=0;
	private double myStartLength;
	
	public Muscle(Mass start, Mass end, double length, double kVal, double amplitude)
	{
		super(start, end, length, kVal);
		myAmplitude=amplitude;
		myStartLength=length;
	}
	
	private void stepLength(double changeInTime)
	{
		changeInTime=changeInTime%(Math.PI*2);
		
		myTime=(myTime+changeInTime)%(Math.PI*2);
		
		setMyLength(myStartLength+waveFunction());
	}
	
	protected double waveFunction()
	{
		return myAmplitude*Math.sin(myTime);
	}
	
	@Override
	public void update (double elapsedTime, Dimension bounds)
	{
		stepLength(elapsedTime);
		super.update(elapsedTime, bounds);
	}
}
