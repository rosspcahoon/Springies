package simulation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import util.Vector;


/**
 * XXX
 * 
 * @author Robert C. Duvall
 */
public class Factory {
	// data file keywords
	private static final String MASS_KEYWORD = "mass";
	private static final String SPRING_KEYWORD = "spring";
	private static final String GRAVITY_KEYWORD = "gravity";
	private static final String VISCOSITY_KEYWORD = "viscosity";
	private static final String CENTERMASS_KEYWORD = "centermass";
	private static final String WALL_REPULSION_KEYWORD = "wall";

	// mass IDs
	Map<Integer, Mass> myMasses = new HashMap<Integer, Mass>();


	/**
	 * XXX.
	 */
	public void loadModel (Model model, File modelFile) {
		try {
			Scanner input = new Scanner(modelFile);
			while (input.hasNext()) {
				Scanner line = new Scanner(input.nextLine());
				if (line.hasNext()) {
					String type = line.next();
					if (MASS_KEYWORD.equals(type)) {
						model.add(massCommand(line));
					}
					else if (SPRING_KEYWORD.equals(type)) {
						model.add(springCommand(line));
					}
				}
			}
			input.close();
		}
		catch (FileNotFoundException e) {
			// should not happen because File came from user selection
			e.printStackTrace();
		}
	}

	public void loadForces (Model model, File modelFile) {
		try {
			Scanner input = new Scanner(modelFile);
			while (input.hasNext()) {
				Scanner line = new Scanner(input.nextLine());
				if (line.hasNext()) {
					String type = line.next();
					if (GRAVITY_KEYWORD.equals(type)) {
						model.setGravity(gravityCommand(line));
					}
					else if (VISCOSITY_KEYWORD.equals(type)) {
						model.setViscosity(viscosityCommand(line));
					}
					else if (CENTERMASS_KEYWORD.equals(type)) {
						model.setCenterMass(centerMassCommand(line));
					}
					else if (WALL_REPULSION_KEYWORD.equals(type)) {
						model.setWallRepulsion(wallRepulsionCommand(line));
					}                    
				}
			}
			input.close();
		}
		catch (FileNotFoundException e) {
			// should not happen because File came from user selection
			e.printStackTrace();
		}
	}

	// create mass from formatted data
	private Mass massCommand (Scanner line) {
		int id = line.nextInt();
		double x = line.nextDouble();
		double y = line.nextDouble();
		double mass = line.nextDouble();
		Mass result = new Mass(x,y,mass);
		if(mass < 0){
			result = new FixedMass(x, y, mass);
		}
		myMasses.put(id,  result);
		return result;
	}

	// create spring from formatted data
	private Spring springCommand (Scanner line) {
		Mass m1 = myMasses.get(line.nextInt());
		Mass m2 = myMasses.get(line.nextInt());
		double restLength = line.nextDouble();
		double ks = line.nextDouble();
		return new Spring(m1, m2, restLength, ks);
	}
	// create gravity vector from formatted data
	private Vector gravityCommand (Scanner line) {
		double angle = line.nextDouble();
		double magnitude = line.nextDouble();
		return new Vector(angle, magnitude);
	}
	
	// create gravity vector from formatted data
	private double viscosityCommand (Scanner line) {
		return line.nextDouble();
	}

	private double[] centerMassCommand(Scanner line){
		double magnitude = line.nextDouble();
		double exponent = line.nextDouble();
		double[] result = new double[2];
		result[0] = magnitude;
		result[1] = exponent;	
		return result;
	}
	
	private double[] wallRepulsionCommand(Scanner line){
		double wallID = line.nextDouble();
		double magnitude = line.nextDouble();
		double exponent = line.nextDouble();
		double[] result = new double[3];
		result[0] = wallID;
		result[1] = magnitude;
		result[2] = exponent;
		return result;
	}
}
