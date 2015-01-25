package cma_es;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import cma_es.CMAEvolutionStrategyH.*;


public class CMAExample3 {
	public static void main(String[] args) {
		testSerejaAndNumberDivision2();
	}
	
	public static class NumberDivision implements IObjectiveFunction {
		private char[] bigInt;
		private int[] target;
		
		public NumberDivision(char[] bigInt, int[] target) {
			super();
			this.bigInt = bigInt;
			this.target = target;
		}
		
		public char[] shiftArray(double[] x) {
			char[] a = bigInt.clone();
			for (int i = 0; i < x.length; i++) {
				int from = i;
				int to = (int) (i + x[i]);
				to = Math.max(0, Math.min(x.length-1, to));
				
				char t = a[from];
				a[from] = a[to];
				a[to] = t;
			}
			return a;
		}

		@Override
		public double valueOf(double[] x) {
			char[] a = shiftArray(x);
			
			int ret = 0;
			BigInteger A = new BigInteger(new String(a));
			for (int i = 0; i < target.length; i++) {
				ret += A.mod(BigInteger.valueOf(target[i])).intValue();
			}
			return ret;
		}

		@Override
		public boolean isFeasible(double[] x) {
			return true;
		}
	}
	
	static class Rosenbrock implements IObjectiveFunction { // meaning implements methods valueOf and isFeasible
		public double valueOf (double[] x) {
			double res = 0;
			for (int i = 0; i < x.length-1; ++i)
				res += 100 * (x[i]*x[i] - x[i+1]) * (x[i]*x[i] - x[i+1]) + 
				(x[i] - 1.) * (x[i] - 1.);
			return res;
		}
		public boolean isFeasible(double[] x) {return true; } // entire R^n is feasible
	}
	

	public static void testSerejaAndNumberDivision2() {
		int numTests = 1;
		double avg = 0;
		double[] ret = new double[numTests];
		for (int i = 0; i < numTests; i++) {
			long time = System.currentTimeMillis();
			ret[i] = testSerejaAndNumberDivision2Sub(new Random(i));
			avg += ret[i];
			time = System.currentTimeMillis() - time;
			//System.out.println("\t" + time);
		}
		avg /= numTests;
		
		System.out.println("avg: " + avg);
		System.out.println(Arrays.toString(ret));
	}

	public static double testSerejaAndNumberDivision2Sub(Random rand) {
		int numTests = 1;
		long y = 0;
		long min = Integer.MAX_VALUE;
		long max = 0;
		double avgY = 0;
		int maxLength = 100;
		for (int t = 0; t < numTests; t++) {
			int m = maxLength; //rand.nextInt(maxLength)+2;
			char[] a = new char[m];
			for (int i = 0; i < a.length; i++) {
				a[i] = (char) (rand.nextInt(9)+1 + '0');
			}
			int n = 100;
			int[] b = new int[n];
			int range = rand.nextInt(1000000)+1; 
			for (int i = 0; i < b.length; i++) {
				b[i] = rand.nextInt(range)+1;
			}
			char[] ret = serejaAndNumberDivision2(a, n, b, rand).toCharArray();
			int r = f(ret, b); 
			y += r;
			avgY += r; 
			min = Math.min(min, r);
			max = Math.max(max,r );
		}
		avgY /= numTests;
		System.out.println(y + "\t" + avgY + "\t" + min + "\t" + max);
		return avgY;
	}

	public static String serejaAndNumberDivision2(char[] a, int n, int[] b, Random rand) {

		NumberDivision fitfun = new NumberDivision(a, b);

		// new a CMA-ES and set some initial values
		CMAEvolutionStrategy cma = new CMAEvolutionStrategy();
		cma.readProperties(); // read options, see file CMAEvolutionStrategy.properties
		cma.setDimension(fitfun.bigInt.length-1); // overwrite some loaded properties
		cma.setInitialX(0.00); // in each dimension, also setTypicalX can be used
		cma.setInitialStandardDeviation(2.0); // also a mandatory setting 
		cma.options.stopFitness = 1e-14;       // optional setting
		cma.options.stopMaxFunEvals = 100000;

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
		
		return new String(fitfun.shiftArray(cma.getBestX()));
	}

	public static int f(char[] a, int[] b) {
		int ret = 0;
		BigInteger A = new BigInteger(new String(a));
		for (int i = 0; i < b.length; i++) {
			ret += A.mod(BigInteger.valueOf(b[i])).intValue();
		}
		return ret;
	}

	public static int f(BigInteger A, int[] b) {
		int ret = 0;
		for (int i = 0; i < b.length; i++) {
			ret += A.mod(BigInteger.valueOf(b[i])).intValue();
		}
		return ret;
	}

	public static int f(ArrayList<Character> a, int[] b, int bLength) {
		if (bLength == 0) {
			bLength = b.length;
		}
		int ret = 0;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < a.size(); i++) {
			sb.append(a.get(i));
		}
		BigInteger A = new BigInteger(sb.toString());
		for (int i = 0; i < bLength; i++) {
			ret += A.mod(BigInteger.valueOf(b[i])).intValue();
		}
		return ret;
	}

	@SuppressWarnings("unchecked")
	public static String serejaAndNumberDivision3(char[] a, int n, int[] b, Random random) {
//		if (n > -1) {
//			return new String(a);
//		}
		if (random == null) {
			random = new Random(0);
		}
		Arrays.sort(b);
//		for (int i = 0; i < b.length/2; i++) {
//			int t = b[i];
//			b[i] = b[b.length -i -1];
//			b[b.length -i -1] = t;
//		}
		
		ArrayList<Character> rand = new ArrayList<>();
		for (int i = 0; i < a.length; i++) {
			rand.add(a[i]);
		}
		int min = Integer.MAX_VALUE;
		ArrayList<Character> minA = null;

		int f = f(rand, b, 0);
		if (f < min) {
			min = f;
			minA = (ArrayList<Character>) rand.clone();
		}
		Collections.sort(rand);
		f = f(rand, b, 0);
		if (f < min) {
			min = f;
			minA = (ArrayList<Character>) rand.clone();
		}
		Collections.reverse(rand);
		f = f(rand, b, 0);
		if (f < min) {
			min = f;
			minA = (ArrayList<Character>) rand.clone();
		}
		for (int i = 0; i < 10; i++) {
			Collections.shuffle(rand, random);
			f = f(rand, b, 0);
			if (f < min) {
				min = f;
				minA = (ArrayList<Character>) rand.clone();
				//System.out.println(rand);
			}
		}

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < minA.size(); i++) {
			sb.append(minA.get(i));
		}
		return new String(sb.toString());
	}
} // class
