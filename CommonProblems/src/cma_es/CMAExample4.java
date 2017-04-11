package cma_es;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Arrays;

import cma_es.CMAEvolutionStrategyH.*;

class TetrisBot implements IObjectiveFunction { 
	public double valueOf (double[] x) {

		String inList = "";
		for (int i = 0; i < x.length; i++) {
			inList += x[i] + " ";
		}
		System.out.println("[Input] : " + inList);

		ProcessBuilder builder = new ProcessBuilder("C:/Images/ubuntu_share/BlockBattleBot/Release/BlockBattleStarterBotCpp.exe", Double.toString(x[0]),  Double.toString(x[1]), 
				Double.toString(x[2]),  Double.toString(x[3]),  Double.toString(x[4]), Double.toString(x[5]), Double.toString(x[6]));
		//ProcessBuilder builder = new ProcessBuilder("C:/temp/TetrisBot.exe");
		//builder.directory(new File("C:/Users/Lucas/Documents/Visual Studio 2013/Projects/TetrisBot/Release"));
		builder.redirectErrorStream(true);
		Process process = null;
		try {
			process = builder.start();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));

		double ret = 0;
		
		try {
			String line;
			while ((line = br.readLine()) != null) {
				System.out.println("[Stdout]: " + line);
				ret = Double.parseDouble(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return ret;
	}
	public boolean isFeasible(double[] x) {return true; } // entire R^n is feasible
}

/**
 * 
 * @see CMAEvolutionStrategy
 * 
 * @author Nikolaus Hansen, released into public domain. 
 */
public class CMAExample4 {
	public static void main(String[] args) {
		IObjectiveFunction fitfun = new TetrisBot();
		
		double[] x = {-4.500158825082766f, 20.181268101392694f, -3.2178882868487753f, -9.348695305445199f, -7.899265427351652f, -3.3855972247263626f, 30.0f};

		// new a CMA-ES and set some initial values
		CMAEvolutionStrategy cma = new CMAEvolutionStrategy();
		cma.readProperties(); // read options, see file CMAEvolutionStrategy.properties
		cma.setDimension(x.length); // overwrite some loaded properties
		cma.setInitialX(-1.0); // in each dimension, also setTypicalX can be used
		cma.setInitialStandardDeviation(0.2); // also a mandatory setting 
		cma.options.stopFitness = 1e-14;       // optional setting
		
		cma.setInitialX(x);

		// initialize cma and get fitness array to fill in later
		double[] fitness = cma.init();  // new double[cma.parameters.getPopulationSize()];

		// initial output to files
		cma.writeToDefaultFilesHeaders(0); // 0 == overwrites old files

		// iteration loop
		while(cma.stopConditions.getNumber() == 0) {

			// --- core iteration step ---
			double[][] pop = cma.samplePopulation(); // get a new population of solutions
			for(int i = 0; i < pop.length; ++i) {    // for each candidate solution i
				// a simple way to handle constraints that define a convex feasible domain  
				// (like box constraints, i.e. variable boundaries) via "blind re-sampling" 
				// assumes that the feasible domain is convex, the optimum is  
				while (!fitfun.isFeasible(pop[i]))     //   not located on (or very close to) the domain boundary,  
					pop[i] = cma.resampleSingle(i);    //   initialX is feasible and initialStandardDeviations are  
				//   sufficiently small to prevent quasi-infinite looping here
				// compute fitness/objective value	
				fitness[i] = fitfun.valueOf(pop[i]); // fitfun.valueOf() is to be minimized
			}
			cma.updateDistribution(fitness);         // pass fitness array to update search distribution
			// --- end core iteration step ---

			// output to files and console 
			cma.writeToDefaultFiles();
			int outmod = 150;
			if (cma.getCountIter() % (15*outmod) == 1)
				cma.printlnAnnotation(); // might write file as well
			if (cma.getCountIter() % outmod == 1)
				cma.println(); 
		}
		// evaluate mean value as it is the best estimator for the optimum
		cma.setFitnessOfMeanX(fitfun.valueOf(cma.getMeanX())); // updates the best ever solution 

		// final output
		cma.writeToDefaultFiles(1);
		cma.println();
		cma.println("Terminated due to");
		for (String s : cma.stopConditions.getMessages())
			cma.println("  " + s);
		cma.println("best function value " + cma.getBestFunctionValue() 
				+ " at evaluation " + cma.getBestEvaluationNumber());
		cma.println("best X: " + Arrays.toString(cma.getBestRecentX()));

		// we might return cma.getBestSolution() or cma.getBestX()

	} // main  
} // class
