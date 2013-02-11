package simulation;

import java.awt.Dimension;
/**
 * Muscle is like a Spring but its length changes harmonically
 * @author Wayne You, Ross Cahoon
 *
 */
public class Muscle extends Spring {
    private double myAmplitude;
    private double myTime = 0;
    private double myStartLength;
/**
 * Given the two ends of the Muscle, the length of the muscles, its spring value
 * and the amplitude it creates a Muscle
 * @param start the starting end of the Muscle
 * @param end the ending  end of the Muscle
 * @param length the magnitude of the length of the muscle
 * @param kVal the spring value of the muscle
 * @param amplitude the magnitude of the sinusoid wave of the Muscle
 */
    public Muscle(Mass start, Mass end, double length, double kVal, double amplitude)
    {
        super(start, end, length, kVal);
        myAmplitude = amplitude;
        myStartLength = length;
    }
    private void stepLength(double changeInTime) {
        double temp = changeInTime;
        temp = changeInTime % (Math.PI * 2);
        myTime = (myTime + temp) % (Math.PI * 2);

        setLength(myStartLength + waveFunction());
    }
    protected double waveFunction() {
        return myAmplitude * Math.sin(myTime);
    }
    @Override
    public void update (double elapsedTime, Dimension bounds) {
        stepLength(elapsedTime);
        super.update(elapsedTime, bounds);
    }
}
