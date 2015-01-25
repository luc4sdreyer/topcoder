package cma_es;

import java.text.DecimalFormatSymbols;
import java.util.*; // Properties, Arrays.sort, Formatter not needed anymore

public class CMAEvolutionStrategyH {
	/** extending from this abstract class implements a generic isFeasible method and adds the  
	 * IObjectiveFunctionParallel interface to a class that implements 
	 * the interface IObjectiveFunction */
	public static abstract class AbstractObjectiveFunction implements 
	IObjectiveFunction,
	IObjectiveFunctionParallel  { 
		abstract public double valueOf(double[] x);
		public double [] valuesOf(double[][] pop) {
			double [] res = new double[pop.length];
			for (int i = 0; i < pop.length; ++i)
				res[i] = valueOf(pop[i]);
			return res;
		}
		public boolean isFeasible(double[] x) {
			return true;
		}
	}

	/* 
Copyright 1996, 2003, 2005, 2007 Nikolaus Hansen 
e-mail: hansen .AT. lri.fr

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License, version 3,
as published by the Free Software Foundation.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.

log of changes: 
 o updateDistribution(double[][], double[], int) introduced,
   for the time being
   updateDistribution(double[][], double[]) evaluates to
   updateDistribution(double[][], double[], 0), but it might become
   updateDistribution(double[][], double[], popsize)
 o init() cannot be called twice anymore, it's saver like this 
 o warning() and error() print also to display-file
 o checkEigenSystem() call is now an option, gives warnings, not
   errors, and has its precision criteria adapted to Java.
 o 06/08 fix: error for negative eigenvalues did not show up 
 o 09/08: diagonal option included
 o updateDistribution(double[][], double[]) is available, which 
   implements an interface, independent of samplePopulation(). 
 o variable locked is set at the end of supplementRemainders, 
   instead at the beginning (09/03/08)
 o bestever is set anew, if its current fitness is NaN (09/03/08)
 o getBestRecentX() now returns really the recent best (10/03/17) 
   (thanks to Markus Kemmerling for reporting this problem)
 o 2010/12/02: merge of r762 (diagonal option) and r2462 which were 
   subbranches since r752
 o test() uses flgdiag to get internally time linear

WISH LIST:
 o test and consider refinement of 
   updateDistribution(double[][], double[]) that
   implements a "saver" interface, 
   independent of samplePopulation
   for example updateDistribution(ISolutionPoint[] pop)
 o save all input parameters as output-properties file
 o explicit control of data writing behavior in terms of iterations
   to wait until the next writing?
 o clean up sorting of eigenvalues/vectors which is done repeatedly
 o implement a good boundary handling
 o check Java random number generator and/or implement a private one. 
 o implement a general initialize_with_evaluated_points method, which
   estimates a good mean and covariance matrix either from all points
   or only from the lambda best points (actually mu best points then).
   cave about outlier points. 
 o implement a CMA-ES-specific feed points method for initialization. It should
   accept a population of evaluated points iteratively. It 
   just needs to call updateDistribution with a population as input. 
 o save z instead of recomputing it? 
 o improve error management to reasonable standard 
 o provide output writing for given evaluation numbers and/or given fitness values
 o better use the class java.lang.Object.Date to handle elapsed times?  

Last change: $Date: 2011-06-23 $     
	 */

	/** 
	 * implements the Covariance Matrix Adaptation Evolution Strategy (CMA-ES)
	 * for non-linear, non-convex, non-smooth, global function minimization. The CMA-Evolution Strategy
	 * (CMA-ES) is a reliable stochastic optimization method which should be applied,
	 * if derivative based methods, e.g. quasi-Newton BFGS or conjugate
	 * gradient, fail due to a rugged search landscape (e.g. noise, local
	 * optima, outlier, etc.)  of the objective function. Like a
	 * quasi-Newton method the CMA-ES learns and applies a variable metric
	 * of the underlying search space. Unlike a quasi-Newton method the
	 * CMA-ES does neither estimate nor use gradients, making it considerably more
	 * reliable in terms of finding a good, or even close to optimal, solution, finally.
	 *
	 * <p>In general, on smooth objective functions the CMA-ES is roughly ten times
	 * slower than BFGS (counting objective function evaluations, no gradients provided). 
	 * For up to <math>N=10</math> variables also the derivative-free simplex
	 * direct search method (Nelder & Mead) can be faster, but it is
	 * far less reliable than CMA-ES. 
	 *
	 * <p>The CMA-ES is particularly well suited for non-separable 
	 * and/or badly conditioned problems. 
	 * To observe the advantage of CMA compared to a conventional
	 * evolution strategy, it will usually take about 30&#215;<math>N</math> function
	 * evaluations. On difficult problems the complete
	 * optimization (a single run) is expected to take <em>roughly</em>  between
	 * <math>30&#215;N</math> and <math>300&#215;N<sup>2</sup></math>
	 * function evaluations.  
	 *  
	 * <p>The main functionality is provided by the methods <code>double[][] {@link #samplePopulation()}</code> and 
	 * <code>{@link #updateDistribution(double[])}</code> or <code>{@link #updateDistribution(double[][], double[])}</code>. 
	 * Here is an example code snippet, see file 
	 * <tt>CMAExample1.java</tt> for a similar example, and 
	 *   <tt>CMAExample2.java</tt> for a more extended example with multi-starts implemented.
	 *   <pre>
    // new a CMA-ES and set some initial values
    CMAEvolutionStrategy cma = new CMAEvolutionStrategy();
    cma.readProperties(); // read options, see file CMAEvolutionStrategy.properties
    cma.setDimension(10); // overwrite some loaded properties
    cma.setTypicalX(0.5); // in each dimension, setInitialX can be used as well
    cma.setInitialStandardDeviation(0.2); // also a mandatory setting 
    cma.opts.stopFitness = 1e-9;          // optional setting

    // initialize cma and get fitness array to fill in later
    double[] fitness = cma.init();  // new double[cma.parameters.getPopulationSize()];

    // initial output to files
    cma.writeToDefaultFilesHeaders(0); // 0 == overwrites old files

    // iteration loop
    while(cma.stopConditions.getNumber() == 0) {

        // core iteration step 
        double[][] pop = cma.samplePopulation(); // get a new population of solutions
        for(int i = 0; i < pop.length; ++i) {    // for each candidate solution i
            fitness[i] = fitfun.valueOf(pop[i]); //    compute fitness value, where fitfun
        }                                        //    is the function to be minimized
        cma.updateDistribution(fitness);         // use fitness array to update search distribution

        // output to files
        cma.writeToDefaultFiles();
        ...in case, print output to console, eg. cma.println(), 
           or process best found solution, getBestSolution()...
    } // while 

    // evaluate mean value as it is the best estimator for the optimum
    cma.setFitnessOfMeanX(fitfun.valueOf(cma.getMeanX())); // updates the best ever solution 
    ...retrieve best solution, termination criterion via stopConditions etc...    

    return cma.getBestX(); // best evaluated search point 

	 *   </pre>
	 *   The output generated by the function <code>writeToDefaultFiles</code> can be 
	 *   plotted in Matlab or Scilab using <tt>plotcmaesdat.m</tt> or
	 *   <tt>plotcmaesdat.sci</tt> respectively, see {@link #writeToDefaultFiles()}. 
	 *     
</P>     
<P> The implementation follows very closely <a name=HK2004>[3]</a>. It supports small and large 
population sizes, the latter by using the rank-&micro;-update [2],
together with weighted recombination for the covariance matrix, an
improved parameter setting for large populations [3] and an (initially) diagonal covariance matrix [5]. 
The latter is particularly useful for large dimension, e.g. larger 100. 
The default population size is small [1]. An
independent restart procedure with increasing population size [4]
is implemented in class <code>{@link cmaes.examples.CMAExample2}</code>.</P>

	 * <P><B>Practical hint</B>: In order to solve an optimization problem in reasonable time it needs to be 
	 * reasonably encoded. In particular the domain width of variables should be 
	 * similar for all objective variables (decision variables), 
	 * such that the initial standard deviation can be chosen the same
	 * for each variable. For example, an affine-linear transformation could be applied to
	 * each variable, such that its typical domain becomes the interval [0,10]. 
	 * For positive variables a log-encoding or a square-encoding 
	 * should be considered, to avoid the need to set a hard boundary at zero, 
	 * see <A href="http://www.lri.fr/~hansen/cmaes_inmatlab.html#practical">here for a few more details</A>.
	 * </P>

<P><B>References</B>
<UL>
<LI>[1] Hansen, N. and A. Ostermeier (2001). Completely
Derandomized Self-Adaptation in Evolution Strategies. <I>Evolutionary
Computation</I>, 9(2), pp. 159-195. 
</LI>
<LI>[2] Hansen, N., S.D. M&uuml;ller and
P. Koumoutsakos (2003). Reducing the Time Complexity of the
Derandomized Evolution Strategy with Covariance Matrix Adaptation
(CMA-ES). <I>Evolutionary Computation</I>, 11(1), pp. 1-18.

<LI>[3] Hansen and Kern (2004). Evaluating the CMA Evolution
Strategy on Multimodal Test Functions. In <I> Eighth International
Conference on Parallel Problem Solving from Nature PPSN VIII,
Proceedings</I>, pp. 282-291, Berlin: Springer.
</LI>
<LI>[4]
Auger, A, and Hansen, N. (2005). A Restart CMA Evolution Strategy
With Increasing Population Size.</A> In <I>Proceedings of the IEEE
Congress on Evolutionary Computation, CEC 2005</I>, pp.1769-1776.
</LI>
<LI>[5]
Ros, R. and N. Hansen (2008). A Simple
Modification in CMA-ES Achieving Linear Time and Space Complexity.
In Rudolph et al. (eds.) <I>Parallel Problem Solving from Nature, PPSN X,
Proceedings</I>, pp. 296-305, Springer.
</LI>
</UL>
</P>

	 * @see #samplePopulation()
	 * @see #updateDistribution(double[])
	 * @author Nikolaus Hansen, 1996, 2003, 2005, 2007
	 */
	public static class CMAEvolutionStrategy implements java.io.Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 2918241407634253526L;

		/**
		 * 
		 */
		public final String versionNumber = new String("0.99.40"); 

		/** Interface to whether and which termination criteria are satisfied 
		 */
		public class StopCondition { 
			int index = 0; // number of messages collected == index where to write next message
			String[] messages = new String[]{""}; // Initialisation with empty string
			double lastcounteval;
			/** true whenever a termination criterion was met. clear() 
			 * re-sets this value to false. 
			 * @see #clear() 
			 */
			public boolean isTrue() {
				return test() > 0;
			}
			/** evaluates to NOT isTrue(). 
			 * @see #isTrue()
			 */
			public boolean isFalse() {
				return !isTrue();
			}
			/** greater than zero whenever a termination criterion was satisfied, zero otherwise. 
			 * clear() re-sets this value to zero. 
			 * @return number of generated termination condition messages */
			public int getNumber() {
				return test();
			}

			/**
			 * get description messages of satisfied termination criteria.
			 * The messages start with one of "Fitness:", "TolFun:", "TolFunHist:", 
			 * "TolX:", "TolUpX:", "MaxFunEvals:", "MaxIter:", "ConditionNumber:", 
			 * "NoEffectAxis:", "NoEffectCoordinate:". 
			 * @return String[] s with messages of termination conditions.
			 *   s[0].equals("") is true if no termination condition is satisfied yet
			 */
			public String[] getMessages() { 
				test();
				return messages; /* first string might be empty */
			}                   
			/** remove all earlier termination condition messages 
			 */
			public void clear() {
				messages = new String[]{""}; 
				index = 0;
			}

			private void appendMessage(String s) { 
				// could be replaced by ArrayList<String> or Vector<String>
				// but also String[] can be iterated easily since version 1.5 
				String [] mold = messages;
				messages = new String[index + 1];

				/* copy old messages */
				for (int i = 0; i < index; ++i) 
					messages[i] = mold[i];

				messages[index++] = s + " (iter=" + countiter + ",eval=" + counteval + ")";
			}

			/**
			 * Tests termination criteria and evaluates to  greater than zero when a
			 * termination criterion is satisfied. Repeated tests append the met criteria repeatedly, 
			 * only if the evaluation count has changed. 
			 * @return number of termination criteria satisfied
			 */
			int test() { 
				if (state < 0)
					return 0;  // not yet initialized
				if (index > 0 && (counteval == lastcounteval 
						|| counteval == lastcounteval+1)) // one evaluation for xmean is ignored
					return index;  // termination criterion already met

				lastcounteval = counteval; 

				/* FUNCTION VALUE */
				if ((countiter > 1 || state >= 3) && bestever_fit <= options.stopFitness)
					appendMessage("Fitness: Objective function value dropped below the target function value " +
							options.stopFitness);

				/* #Fevals */
				if (counteval >= options.stopMaxFunEvals)
					appendMessage("MaxFunEvals: maximum number of function evaluations " + options.stopMaxFunEvals + " reached");

				/* #iterations */
				if (countiter >= options.stopMaxIter)
					appendMessage("MaxIter: maximum number of iterations reached");

				/* TOLFUN */
				if ((countiter > 1 || state >= 3)
						&& Math.max(math.max(fit.history), fit.fitness[fit.fitness.length-1].val)
						- Math.min(math.min(fit.history),fit.fitness[0].val) <= options.stopTolFun) 
					appendMessage("TolFun: function value changes below stopTolFun=" + options.stopTolFun);

				/* TOLFUNHIST */
				if (options.stopTolFunHist >= 0 && countiter > fit.history.length) {
					if (math.max(fit.history) - math.min(fit.history) <= options.stopTolFunHist) 
						appendMessage("TolFunHist: history of function value changes below stopTolFunHist=" + options.stopTolFunHist);
				}

				/* TOLX */
				double tolx = Math.max(options.stopTolX, options.stopTolXFactor * minstartsigma);
				if (sigma * maxsqrtdiagC < tolx 
						&& sigma * math.max(math.abs(pc)) < tolx)
					appendMessage("TolX or TolXFactor: standard deviation below " + tolx);

				/* TOLXUP */
				if (sigma * maxsqrtdiagC > options.stopTolUpXFactor * maxstartsigma)
					appendMessage("TolUpX: standard deviation increased by more than stopTolUpXFactor=" + 
							options.stopTolUpXFactor + 
							", larger initial standard deviation recommended");

				/* STOPNOW */
				if (options.stopnow)
					appendMessage("Manual: flag Options.stopnow set or stop now in .properties file");

				/* Internal (numerical) stopping termination criteria */

				/* Test each principal axis i, whether x == x + 0.1 * sigma * rgD[i] * B[i] */
				for (int iAchse = 0; iAchse < N; ++iAchse) {
					int iKoo;
					int l = flgdiag ? iAchse : 0;
					int u = flgdiag ? iAchse+1 : N;
					double fac = 0.1 * sigma * diagD[iAchse];
					for (iKoo = l; iKoo < u; ++iKoo) { 
						if (xmean[iKoo] != xmean[iKoo] + fac * B[iKoo][iAchse])
							break; // is OK for this iAchse
					}
					if (iKoo == u) // no break, therefore no change for axis iAchse
						appendMessage("NoEffectAxis: Mutation " + 0.1*sigma*diagD[iAchse] +
								" in a principal axis " + iAchse + " has no effect");
				} /* for iAchse */

				/* Test whether one component of xmean is stuck */
				for (int iKoo = 0; iKoo < N; ++iKoo) {
					if (xmean[iKoo] == xmean[iKoo] + 0.2*sigma*Math.sqrt(C[iKoo][iKoo]))
						appendMessage("NoEffectCoordinate: Mutation of size " + 
								0.2*sigma*Math.sqrt(C[iKoo][iKoo]) +
								" in coordinate " + iKoo + " has no effect");
				} /* for iKoo */

				/* Condition number */
				if (math.min(diagD) <= 0)
					appendMessage("ConditionNumber: smallest eigenvalue smaller or equal zero");
				else if (math.max(diagD)/math.min(diagD) > 1e7)
					appendMessage("ConditionNumber: condition number of the covariance matrix exceeds 1e14");
				return index; // call to appendMessage increments index
			}
		} // StopCondtion


		void testAndCorrectNumerics() { // not much left here

			/* Flat Fitness, Test if function values are identical */
			if (getCountIter() > 1 || (getCountIter() == 1 && state >= 3))
				if (fit.fitness[0].val == fit.fitness[Math.min(sp.getLambda()-1, sp.getLambda()/2+1) - 1].val) {
					warning("flat fitness landscape, consider reformulation of fitness, step-size increased");
					sigma *= Math.exp(0.2+sp.getCs()/sp.getDamps());
				}

			/* Align (renormalize) scale C (and consequently sigma) */
			/* e.g. for infinite stationary state simulations (noise
			 * handling needs to be introduced for that) */
			double fac = 1;
			if (math.max(diagD) < 1e-6) 
				fac = 1./math.max(diagD);
			else if (math.min(diagD) > 1e4)
				fac = 1./math.min(diagD);

			if (fac != 1.) {
				sigma /= fac;
				for(int i = 0; i < N; ++i) {
					pc[i] *= fac;
					diagD[i] *= fac;
					for (int j = 0; j <= i; ++j)
						C[i][j] *= fac*fac;
				}
			}
		} // Test...

		/** options that can be changed (fields can be assigned) at any time to control 
		 * the running behavior
		 * */
		public CMAOptions options = new CMAOptions();

		private CMAParameters sp = new CMAParameters(); // alias for inside use
		/** strategy parameters that can be set initially 
		 * */
		public CMAParameters parameters = sp; // for outside use also

		/** permits access to whether and which termination conditions were satisfied */
		public StopCondition stopConditions = new StopCondition(); 

		int N;
		long seed = System.currentTimeMillis();
		Random rand = new Random(seed); // Note: it also Serializable

		final MyMath math = new MyMath();
		double axisratio; 
		long counteval;
		long countiter;

		long bestever_eval; // C style, TODO:  better make use of class CMASolution?
		double[] bestever_x;
		double bestever_fit = Double.NaN; 
		// CMASolution bestever; // used as output variable

		double sigma = 0.0;
		double[] typicalX; // eventually used to set initialX
		double[] initialX; // set in the end of init()
		double[] LBound, UBound;    // bounds
		double[] xmean;
		double xmean_fit = Double.NaN;
		double[] pc;
		double[] ps;
		double[][] C;
		double maxsqrtdiagC;
		double minsqrtdiagC;
		double[][] B;
		double[] diagD;
		boolean flgdiag; // 0 == full covariance matrix

		/* init information */
		double[] startsigma;
		double maxstartsigma;
		double minstartsigma;

		boolean iniphase;

		/**
		 * state (postconditions):
		 *  -1 not yet initialized
		 *   0 initialized init()
		 *   0.5 reSizePopulation
		 *   1 samplePopulation, sampleSingle, reSampleSingle
		 *   2.5 updateSingle
		 *   3 updateDistribution
		 */
		double state = -1;
		long citerlastwritten = 0;
		long countwritten = 0;
		int lockDimension = 0;
		int mode = 0;
		final int SINGLE_MODE = 1; // not in use anymore, keep for later developements?
		final int PARALLEL_MODE = 2;


		long countCupdatesSinceEigenupdate;

		/* fitness information */
		class FitnessCollector {
			double history[];
			IntDouble[] fitness;   // int holds index for respective arx
			IntDouble[] raw; // sorted differently than fitness!
			/** history of delta fitness / sigma^2. Here delta fitness is the minimum of 
			 * fitness value differences with distance lambda/2 in the ranking.  */
			double[] deltaFitHist = new double[5];
			int idxDeltaFitHist = 0;
		}
		protected FitnessCollector fit = new FitnessCollector();

		double recentFunctionValue; 
		double recentMaxFunctionValue;
		double recentMinFunctionValue;
		int idxRecentOffspring; 

		double[][] arx;
		/** recent population, no idea whether this is useful to be public */
		public double[][] population; // returned not as a copy
		double[] xold;

		double[] BDz;
		double[] artmp;

		String propertiesFileName = new String("CMAEvolutionStrategy.properties");
		/** postpones most initialization. For initialization use setInitial... 
		 * methods or set up a properties file, see file "CMAEvolutionStrategy.properties". */
		public CMAEvolutionStrategy() {
			state = -1;
		}

		/** retrieves options and strategy parameters from properties input, see file <tt>CMAEvolutionStrategy.properties</tt> 
		 *  for valid properties */
		public CMAEvolutionStrategy(Properties properties) {
			setFromProperties(properties); 
			state = -1;
		}
		/** reads properties (options, strategy parameter settings) from 
		 * file <code>propertiesFileName</code>
		 * */
		public CMAEvolutionStrategy(String propertiesFileName) {
			this.propertiesFileName = propertiesFileName; 
			state = -1;
		}

		/** @param dimension    search space dimension, dimension of the
		 *       objective functions preimage, number of variables
		 */
		public CMAEvolutionStrategy(int dimension) {
			setDimension(dimension);
			state = -1;
		}

		/** initialization providing all mandatory input arguments at once. The following two 
		 * is equivalent
		 * <PRE>
      cma.init(N, X, SD);
		 * </PRE> and
		 * <PRE>
      cma.setInitalX(X);  // 
      cma.setInitialStandardDeviations(SD);
      cma.init(N);
		 * </PRE> 
		 *
		 * The call to <code>init</code> is a point of no return for parameter 
		 * settings, and demands all mandatory input be set. <code>init</code> then forces the 
		 * setting up of everything and calls 
		 * <code>parameters.supplementRemainders()</code>. If <code>init</code> was not called before, it is called once in
		 * <code>samplePopulation()</code>. The return value is only provided for sake of convenience. 
		 * 
		 * @param dimension
		 * @param initialX double[] can be of size one, where all variables are set to the 
		 * same value, or of size dimension
		 * @param initialStandardDeviations can be of size one, where all standard
		 * deviations are set to the same value, or of size dimension
		 * 
		 * @return <code>double[] fitness</code> of length population size lambda to assign and pass
		 * objective function values to <code>{@link #updateDistribution(double[])}</code>
		 * 
		 * @see #init()
		 * @see #init(int)
		 * @see #setInitialX(double[])
		 * @see #setTypicalX(double[])
		 * @see #setInitialStandardDeviations(double[])
		 * @see #samplePopulation()
		 * @see CMAParameters#supplementRemainders(int, CMAOptions)
		 */
		public double[] init(int dimension, double[] initialX, double[] initialStandardDeviations) { 
			setInitialX(initialX);
			setInitialStandardDeviations(initialStandardDeviations);
			return init(dimension);
		}

		private double[] getArrayOf(double x, int dim) {
			double[] res = new double[dim];
			for (int i = 0; i < dim; ++i)
				res[i] = x;
			return res;
		}
		/** 
		 * 
		 * @param x null or x.length==1 or x.length==dim, only for the second case x is expanded
		 * @param dim
		 * @return <code>null</code> or <code>double[] x</code> with <code>x.length==dim</code>
		 */
		private double[] expandToDimension(double[] x, int dim) {
			if (x == null)
				return null;
			if (x.length == dim)
				return x;
			if (x.length != 1)
				error("x must have length one or length dimension");

			return getArrayOf(x[0], dim);
		}

		/**
		 * @param dimension search space dimension 
		 * @see #init(int, double[], double[])
		 * */
		public double[] init(int dimension) { 
			setDimension(dimension);
			return init();
		}
		/** 
		 * @see #init(int, double[], double[])
		 * */
		public double[] init() {
			int i;
			if (N <= 0)
				error("dimension needs to be determined, use eg. setDimension() or setInitialX()");
			if (state >= 0)
				error("init() cannot be called twice");
			if (state == 0) // less save variant 
				return new double[sp.getLambda()]; 
			if (state > 0)  
				error("init() cannot be called after the first population was sampled");

			sp = parameters; /* just in case the user assigned parameters */
			if (sp.supplemented == 0) // a bit a hack
				sp.supplementRemainders(N, options);
			sp.locked = 1; // lambda cannot be changed anymore

			diagD = new double[N];
			for (i = 0; i < N; ++i)
				diagD[i] = 1;

			/* expand Boundaries */
			LBound = expandToDimension(LBound, N);
			if (LBound == null) {
				LBound = new double[N];
				for (i = 0; i < N; ++i)
					LBound[i] = Double.NEGATIVE_INFINITY;
			}

			UBound = expandToDimension(UBound, N);
			if (UBound == null) {
				UBound = new double[N];
				for (i = 0; i < N; ++i)
					UBound[i] = Double.POSITIVE_INFINITY;
			}

			/* Initialization of sigmas */
			if (startsigma != null) { // 
				if (startsigma.length == 1) {
					sigma = startsigma[0];
				} else if (startsigma.length == N) {
					sigma = math.max(startsigma);
					if (sigma <= 0)
						error("initial standard deviation sigma must be positive");
					for (i = 0; i < N; ++i) {
						diagD[i] = startsigma[i]/sigma;
					}
				} else
					assert false;
			} else {
				// we might use boundaries here to find startsigma, but I prefer to have stddevs mandatory 
				error("no initial standard deviation specified, use setInitialStandardDeviations()");
				sigma = 0.5;
			}

			if (sigma <= 0 || math.min(diagD) <= 0) {
				error("initial standard deviations not specified or non-positive, " + 
						"use setInitialStandarddeviations()"); 
				sigma = 1;
			}
			/* save initial standard deviation */
			if (startsigma == null || startsigma.length == 1) { 
				startsigma = new double[N];
				for (i = 0; i < N; ++i) {
					startsigma[i] = sigma * diagD[i];
				}
			}
			maxstartsigma = math.max(startsigma);
			minstartsigma = math.min(startsigma);
			axisratio = maxstartsigma / minstartsigma; // axis parallel distribution

			/* expand typicalX, might still be null afterwards */
			typicalX = expandToDimension(typicalX, N);

			/* Initialization of xmean */
			xmean = expandToDimension(xmean, N);
			if (xmean == null) { 
				/* set via typicalX */
				if (typicalX != null) {
					xmean = typicalX.clone();
					for (i = 0; i < N; ++i)
						xmean[i] += sigma*diagD[i] * rand.nextGaussian();
					/* set via boundaries, is depriciated */
				} else if (math.max(UBound) < Double.MAX_VALUE
						&& math.min(LBound) > -Double.MAX_VALUE) {
					error("no initial search point (solution) X or typical X specified");
					xmean = new double[N];
					for (i = 0; i < N; ++i) { /* TODO: reconsider this algorithm to set X0 */
						double offset = sigma*diagD[i];
						double range = (UBound[i] - LBound[i] - 2*sigma*diagD[i]); 
						if (offset > 0.4 * (UBound[i] - LBound[i])) {
							offset = 0.4 * (UBound[i] - LBound[i]);
							range = 0.2 * (UBound[i] - LBound[i]);
						}
						xmean[i] = LBound[i] + offset + rand.nextDouble() * range;
					}
				} else {
					error("no initial search point (solution) X or typical X specified");
					xmean = new double[N];
					for (i = 0; i < N; ++i)
						xmean[i] = rand.nextDouble();
				}
			}

			assert xmean != null;
			assert sigma > 0; 

			/* interpret missing option value */
			if (options.diagonalCovarianceMatrix < 0) // necessary for hello world message
				options.diagonalCovarianceMatrix = 1 * 150 * N / sp.lambda; // cave: duplication below

			/* non-settable parameters */
			pc = new double[N];
			ps = new double[N];
			B = new double[N][N];
			C = new double[N][N]; // essentially only i <= j part is used

			xold = new double[N];
			BDz = new double[N];
			bestever_x = xmean.clone();
			// bestever = new CMASolution(xmean);
			artmp = new double[N];


			fit.deltaFitHist = new double[5];
			fit.idxDeltaFitHist = -1;
			for (i = 0; i < fit.deltaFitHist.length; ++i)
				fit.deltaFitHist[i] = 1.;

			// code to be duplicated in reSizeLambda
			fit.fitness = new IntDouble[sp.getLambda()];   // including penalties, used yet
			fit.raw = new IntDouble[sp.getLambda()];       // raw function values
			fit.history = new double[10+30*N/sp.getLambda()];	

			arx = new double[sp.getLambda()][N];
			population = new double[sp.getLambda()][N];

			for (i = 0; i < sp.getLambda(); ++i) {
				fit.fitness[i] = new IntDouble();
				fit.raw[i] = new IntDouble();
			}

			// initialization
			for (i = 0; i < N; ++i) {
				pc[i] = 0;
				ps[i] = 0;
				for (int j = 0; j < N; ++j) {
					B[i][j] = 0;
				}
				for (int j = 0; j < i; ++j) {
					C[i][j] = 0;
				}
				B[i][i] = 1;
				C[i][i] = diagD[i] * diagD[i];
			}
			maxsqrtdiagC = Math.sqrt(math.max(math.diag(C)));
			minsqrtdiagC = Math.sqrt(math.min(math.diag(C)));
			countCupdatesSinceEigenupdate = 0;
			iniphase = false; // obsolete

			/* Some consistency check */
			for (i = 0; i < N; ++i) {
				if (LBound[i] > UBound[i])
					error("lower bound is greater than upper bound");
				if (typicalX != null) {
					if (LBound[i] > typicalX[i])
						error("lower bound '" + LBound[i] + "'is greater than typicalX" + typicalX[i]);
					if (UBound[i] < typicalX[i])
						error("upper bound '" + UBound[i] + "' is smaller than typicalX " + typicalX[i]);
				}
			}
			String[] s = stopConditions.getMessages();
			if(!s[0].equals(""))
				warning("termination condition satisfied at initialization: \n  " + s[0]);

			initialX = xmean.clone(); // keep finally chosen initialX

			timings.start = System.currentTimeMillis();
			timings.starteigen = System.currentTimeMillis();

			state = 0;
			if(options.verbosity > -1)
				printlnHelloWorld();

			return new double[sp.getLambda()];

		} // init()

		/** get default parameters in new CMAParameters instance, dimension must 
		 * have been set before calling getDefaults
		 * 
		 * @see CMAParameters#getDefaults(int)
		 */
		public CMAParameters getParameterDefaults() {
			return sp.getDefaults(N);
		}

		/** get default parameters in new CMAParameters instance
		 * 
		 * @see CMAParameters#getDefaults(int)
		 */
		public CMAParameters getParameterDefaults(int N) {
			return sp.getDefaults(N);
		}

		/** reads properties from default
		 * input file CMAEvolutionStrategy.properties and
		 * sets options and strategy parameter settings
		 * accordingly. Options values can be changed at any time using this function. 
		 */
		public Properties readProperties() {
			return readProperties(propertiesFileName);
		}
		Properties properties = new Properties();
		/** reads properties from fileName and sets strategy parameters and options
		 * accordingly
		 * @param fileName of properties file
		 */
		public Properties readProperties(String fileName) {
			this.propertiesFileName = fileName;
			//    if (fileName.equals(""))
			//        return properties;
			try {
				java.io.FileInputStream fis = new java.io.FileInputStream(fileName);
				properties.load(fis);
				fis.close();
			} 
			catch(java.io.IOException e) { 
				warning("File '" + fileName + "' not found, no options read");
				// e.printStackTrace();
			}
			setFromProperties(properties);
			return properties;
		}

		/** reads properties from Properties class 
		 * input and sets options and parameters accordingly
		 * 
		 * @param properties java.util.Properties key-value hash table
		 * @see #readProperties()
		 */
		public void setFromProperties(Properties properties) {
			String s;

			options.setOptions(properties);

			if (state >= 0) // only options can be changed afterwards
				return;     // defaults are already supplemented 

			//    if (properties.containsKey("boundaryLower") &&
			//            properties.containsKey("boundaryUpper")) {
			//        setBoundaries(parseDouble(getAllToken(properties.getProperty("boundaryLower"))), 
			//                parseDouble(getAllToken(properties.getProperty("boundaryUpper"))));
			if ((s = properties.getProperty("typicalX")) != null) {
				setTypicalX(options.parseDouble(options.getAllToken(s))); 
			}
			if ((s = properties.getProperty("initialX")) != null) {
				setInitialX(options.parseDouble(options.getAllToken(s))); 
			}
			if ((s = properties.getProperty("initialStandardDeviations")) != null) {
				setInitialStandardDeviations(options.parseDouble(options.getAllToken(s)));
			}
			if ((s = properties.getProperty("dimension")) != null) { // parseInt does not ignore trailing spaces
				setDimension(Integer.parseInt(options.getFirstToken(s)));
			}
			if ((s = properties.getProperty("randomSeed")) != null) {
				setSeed(Long.parseLong(options.getFirstToken(s)));
			}
			if ((s = properties.getProperty("populationSize")) != null) {
				sp.setPopulationSize(Integer.parseInt(options.getFirstToken(s)));
			}
			if ((s = properties.getProperty("cCov")) != null) {
				sp.setCcov(Double.parseDouble(options.getFirstToken(s)));
			}

		}
		//private void infoVerbose(String s) {
		//    println(" CMA-ES info: " + s);
		//}

		private void warning(String s) {
			println(" CMA-ES warning: " + s);
		}
		private void error(String s) { // somehow a relict from the C history of this code
			println(" CMA-ES error: " + s);
			//e.printStackTrace();            // output goes to System.err
			//e.printStackTrace(System.out);  // send trace to stdout

			throw new CMAException(" CMA-ES error: " + s);
			//      System.exit(-1); 
		}

		/** some simple math utilities */
		class MyMath { // implements java.io.Serializable {
			int itest;

			double square(double d) {
				return d*d;
			}
			double prod(double []ar) {
				double res = 1.0;
				for(int i = 0; i < ar.length; ++i)
					res *= ar[i];
				return res;
			}

			public double median(double ar[]) {
				// need a copy of ar
				double [] ar2 = new double[ar.length];
				for (int i = 0; i < ar.length; ++i)
					ar2[i] = ar[i];
				Arrays.sort(ar2);
				if (ar2.length % 2 == 0)
					return (ar2[ar.length/2] + ar2[ar.length/2-1]) / 2.;
				else    
					return ar2[ar.length/2];
			}

			/** @return Maximum value of 1-D double array */
			public double max(double ar[]) {
				int i;
				double m;
				m = ar[0];
				for (i = 1; i < ar.length; ++i) {
					if (m < ar[i])
						m = ar[i];
				}
				return m;
			}

			/** sqrt(a^2 + b^2) without under/overflow. **/
			public double hypot(double a, double b) {
				double r  = 0;
				if (Math.abs(a) > Math.abs(b)) {
					r = b/a;
					r = Math.abs(a)*Math.sqrt(1+r*r);
				} else if (b != 0) {
					r = a/b;
					r = Math.abs(b)*Math.sqrt(1+r*r);
				}
				return r;
			}
			/** @return index of minium value of 1-D double array */
			public int minidx(double ar[]) {
				return minidx(ar, ar.length-1);
			}

			/** @return index of minium value of 1-D double 
			 *   array between index 0 and maxidx 
			 * @param ar double[] 
			 * @param maxidx last index to be considered */
			public int minidx(double[] ar, int maxidx) {
				int i, idx;
				idx = 0;
				for (i = 1; i < maxidx; ++i) {
					if (ar[idx] > ar[i])
						idx = i;
				}
				return idx;
			}

			/** @return index of minium value of 1-D double 
			 *   array between index 0 and maxidx 
			 * @param ar double[] 
			 * @param maxidx last index to be considered */
			protected int minidx(IntDouble[] ar, int maxidx) {
				int i, idx;
				idx = 0;
				for (i = 1; i < maxidx; ++i) {
					if (ar[idx].val > ar[i].val)
						idx = i;
				}
				return idx;
			}

			/** @return index of maximum value of 1-D double array */
			public int maxidx(double ar[]) {
				int i, idx;
				idx = 0;
				for (i = 1; i < ar.length; ++i) {
					if (ar[idx] < ar[i])
						idx = i;
				}
				return idx;
			}
			/** @return Minimum value of 1-D double array */
			public double min(double ar[]) {
				int i;
				double m;
				m = ar[0];
				for (i = 1; i < ar.length; ++i) {
					if (m > ar[i])
						m = ar[i];
				}
				return m;
			}

			/** @return Maximum value of 1-D Object array where the object implements Comparator 
			 *    Example: max(Double arx, arx[0]) */
			public Double max(Double ar[], Comparator<Double> c) {
				int i;
				Double m;
				m = ar[0];
				for (i = 1; i < ar.length; ++i) {
					if (c.compare(m, ar[i]) > 0)
						m = ar[i];
				}
				return m;
			}

			/** @return Maximum value of 1-D IntDouble array */
			public IntDouble max(IntDouble ar[]) {
				int i;
				IntDouble m;
				m = ar[0];
				for (i = 1; i < ar.length; ++i) {
					if (m.compare(m, ar[i]) < 0)
						m = ar[i];
				}
				return m;
			}

			/** @return Minimum value of 1-D IntDouble array */
			public IntDouble min(IntDouble ar[]) {
				int i;
				IntDouble m;
				m = ar[0];
				for (i = 1; i < ar.length; ++i) {
					if (m.compare(m, ar[i]) > 0)
						m = ar[i];
				}
				return m;
			}

			/** @return Minimum value of 1-D Object array defining a Comparator */
			public Double min(Double ar[], Comparator<Double> c) {
				int i;
				Double m;
				m = ar[0];
				for (i = 1; i < ar.length; ++i) {
					if (c.compare(m, ar[i]) < 0)
						m = ar[i];
				}
				return m;
			}

			/**
			 * @return Diagonal of an 2-D double array
			 */
			public double[] diag(double ar[][]) {
				int i;
				double[] diag = new double[ar.length];
				for (i = 0; i < ar.length && i < ar[i].length; ++i)
					diag[i] = ar[i][i];
				return diag;
			}

			/**
			 * @return 1-D double array of absolute values of an 1-D double array
			 */
			public double[] abs(double v[]) {
				double res[] = new double[v.length];
				for(int i = 0; i < v.length; ++i)
					res[i] = Math.abs(v[i]);
				return res;
			}
		} // MyMath

		class Timing {
			Timing(){
				birth = System.currentTimeMillis();
				start = birth; // on the save side 
			}
			long birth; // time at construction, not really in use
			long start; // time at end of init()
			long starteigen; // time after flgdiag was turned off, ie when calls to eigen() start
			long eigendecomposition = 0; // spent time in eigendecomposition
			long writedefaultfiles = 0;        // spent time in writeToDefaultFiles
		}
		Timing timings = new Timing();

		/* flgforce == 1 force independent of time measurments, 
		 * flgforce == 2 force independent of uptodate-status
		 */
		void eigendecomposition(int flgforce) {
			/* Update B and D, calculate eigendecomposition */
			int i, j;

			if (countCupdatesSinceEigenupdate == 0 && flgforce < 2)
				return;

			//           20% is usually better in terms of running *time* (only on fast to evaluate functions)
			if (!flgdiag && flgforce <= 0 && 
					(timings.eigendecomposition > 1000 + options.maxTimeFractionForEigendecomposition 
							* (System.currentTimeMillis() - timings.starteigen)
							|| countCupdatesSinceEigenupdate < 1. / sp.getCcov() / N / 5.)) 
				return;

			if (flgdiag) {
				for (i = 0; i < N; ++i) {
					diagD[i] = Math.sqrt(C[i][i]);
				}
				countCupdatesSinceEigenupdate = 0;
				timings.starteigen = System.currentTimeMillis(); // reset starting time
				timings.eigendecomposition = 0;             // not really necessary
			} else {
				// set B <- C
				for (i = 0; i < N; ++i)
					for (j = 0; j <= i; ++j)
						B[i][j] = B[j][i] = C[i][j];

				// eigendecomposition
				double [] offdiag = new double[N];
				long firsttime = System.currentTimeMillis();
				tred2(N, B, diagD, offdiag);
				tql2(N, diagD, offdiag, B);
				timings.eigendecomposition += System.currentTimeMillis() - firsttime;

				if (options.checkEigenSystem > 0)
					checkEigenSystem( N,  C, diagD, B); // for debugging 

				// assign diagD to eigenvalue square roots
				for (i = 0; i < N; ++i) {
					if (diagD[i] < 0) // numerical problem?
						error("an eigenvalue has become negative");
					diagD[i] = Math.sqrt(diagD[i]);
				}
				countCupdatesSinceEigenupdate = 0;
			} // end Update B and D
			if (math.min(diagD) == 0) // error management is done elsewhere
				axisratio = Double.POSITIVE_INFINITY;
			else
				axisratio = math.max(diagD) / math.min(diagD);

		} // eigendecomposition


		/* ========================================================= */
		int
		checkEigenSystem( int N,  double C[][], double diag[], double Q[][]) 
		/* 
   exhaustive test of the output of the eigendecomposition
   needs O(n^3) operations 

   produces error  
   returns number of detected inaccuracies 
		 */
		{
			/* compute Q diag Q^T and Q Q^T to check */
			int i, j, k, res = 0;
			double cc, dd; 
			String s;

			for (i=0; i < N; ++i)
				for (j=0; j < N; ++j) {
					for (cc=0.,dd=0., k=0; k < N; ++k) {
						cc += diag[k] * Q[i][k] * Q[j][k];
						dd += Q[i][k] * Q[j][k];
					}
					/* check here, is the normalization the right one? */
					if (Math.abs(cc - C[i>j?i:j][i>j?j:i])/Math.sqrt(C[i][i]*C[j][j]) > 1e-10 
							&& Math.abs(cc - C[i>j?i:j][i>j?j:i]) > 1e-9) { /* quite large */
						s = " " + i + " " + j + " " + cc + " " + C[i>j?i:j][i>j?j:i] + " " + (cc-C[i>j?i:j][i>j?j:i]);
						warning("cmaes_t:Eigen(): imprecise result detected " + s);
						++res; 
					}
					if (Math.abs(dd - (i==j?1:0)) > 1e-10) {
						s = i + " " + j + " " + dd;
						warning("cmaes_t:Eigen(): imprecise result detected (Q not orthog.) " + s);
						++res;
					}
				}
			return res; 
		}



		// Symmetric Householder reduction to tridiagonal form, taken from JAMA package.

		private void tred2 (int n, double V[][], double d[], double e[]) {

			//  This is derived from the Algol procedures tred2 by
			//  Bowdler, Martin, Reinsch, and Wilkinson, Handbook for
			//  Auto. Comp., Vol.ii-Linear Algebra, and the corresponding
			//  Fortran subroutine in EISPACK.

			for (int j = 0; j < n; j++) {
				d[j] = V[n-1][j];
			}

			// Householder reduction to tridiagonal form.

			for (int i = n-1; i > 0; i--) {

				// Scale to avoid under/overflow.

				double scale = 0.0;
				double h = 0.0;
				for (int k = 0; k < i; k++) {
					scale = scale + Math.abs(d[k]);
				}
				if (scale == 0.0) {
					e[i] = d[i-1];
					for (int j = 0; j < i; j++) {
						d[j] = V[i-1][j];
						V[i][j] = 0.0;
						V[j][i] = 0.0;
					}
				} else {

					// Generate Householder vector.

					for (int k = 0; k < i; k++) {
						d[k] /= scale;
						h += d[k] * d[k];
					}
					double f = d[i-1];
					double g = Math.sqrt(h);
					if (f > 0) {
						g = -g;
					}
					e[i] = scale * g;
					h = h - f * g;
					d[i-1] = f - g;
					for (int j = 0; j < i; j++) {
						e[j] = 0.0;
					}

					// Apply similarity transformation to remaining columns.

					for (int j = 0; j < i; j++) {
						f = d[j];
						V[j][i] = f;
						g = e[j] + V[j][j] * f;
						for (int k = j+1; k <= i-1; k++) {
							g += V[k][j] * d[k];
							e[k] += V[k][j] * f;
						}
						e[j] = g;
					}
					f = 0.0;
					for (int j = 0; j < i; j++) {
						e[j] /= h;
						f += e[j] * d[j];
					}
					double hh = f / (h + h);
					for (int j = 0; j < i; j++) {
						e[j] -= hh * d[j];
					}
					for (int j = 0; j < i; j++) {
						f = d[j];
						g = e[j];
						for (int k = j; k <= i-1; k++) {
							V[k][j] -= (f * e[k] + g * d[k]);
						}
						d[j] = V[i-1][j];
						V[i][j] = 0.0;
					}
				}
				d[i] = h;
			}

			// Accumulate transformations.

			for (int i = 0; i < n-1; i++) {
				V[n-1][i] = V[i][i];
				V[i][i] = 1.0;
				double h = d[i+1];
				if (h != 0.0) {
					for (int k = 0; k <= i; k++) {
						d[k] = V[k][i+1] / h;
					}
					for (int j = 0; j <= i; j++) {
						double g = 0.0;
						for (int k = 0; k <= i; k++) {
							g += V[k][i+1] * V[k][j];
						}
						for (int k = 0; k <= i; k++) {
							V[k][j] -= g * d[k];
						}
					}
				}
				for (int k = 0; k <= i; k++) {
					V[k][i+1] = 0.0;
				}
			}
			for (int j = 0; j < n; j++) {
				d[j] = V[n-1][j];
				V[n-1][j] = 0.0;
			}
			V[n-1][n-1] = 1.0;
			e[0] = 0.0;
		} 

		// Symmetric tridiagonal QL algorithm, taken from JAMA package.

		private void tql2 (int n, double d[], double e[], double V[][]) {

			//  This is derived from the Algol procedures tql2, by
			//  Bowdler, Martin, Reinsch, and Wilkinson, Handbook for
			//  Auto. Comp., Vol.ii-Linear Algebra, and the corresponding
			//  Fortran subroutine in EISPACK.

			for (int i = 1; i < n; i++) {
				e[i-1] = e[i];
			}
			e[n-1] = 0.0;

			double f = 0.0;
			double tst1 = 0.0;
			double eps = Math.pow(2.0,-52.0);
			for (int l = 0; l < n; l++) {

				// Find small subdiagonal element

				tst1 = Math.max(tst1,Math.abs(d[l]) + Math.abs(e[l]));
				int m = l;
				while (m < n) {
					if (Math.abs(e[m]) <= eps*tst1) {
						break;
					}
					m++;
				}

				// If m == l, d[l] is an eigenvalue,
				// otherwise, iterate.

				if (m > l) {
					int iter = 0;
					do {
						iter = iter + 1;  // (Could check iteration count here.)

						// Compute implicit shift

						double g = d[l];
						double p = (d[l+1] - g) / (2.0 * e[l]);
						double r = math.hypot(p,1.0);
						if (p < 0) {
							r = -r;
						}
						d[l] = e[l] / (p + r);
						d[l+1] = e[l] * (p + r);
						double dl1 = d[l+1];
						double h = g - d[l];
						for (int i = l+2; i < n; i++) {
							d[i] -= h;
						}
						f = f + h;

						// Implicit QL transformation.

						p = d[m];
						double c = 1.0;
						double c2 = c;
						double c3 = c;
						double el1 = e[l+1];
						double s = 0.0;
						double s2 = 0.0;
						for (int i = m-1; i >= l; i--) {
							c3 = c2;
							c2 = c;
							s2 = s;
							g = c * e[i];
							h = c * p;
							r = math.hypot(p,e[i]);
							e[i+1] = s * r;
							s = e[i] / r;
							c = p / r;
							p = c * d[i] - s * g;
							d[i+1] = h + s * (c * g + s * d[i]);

							// Accumulate transformation.

							for (int k = 0; k < n; k++) {
								h = V[k][i+1];
								V[k][i+1] = s * V[k][i] + c * h;
								V[k][i] = c * V[k][i] - s * h;
							}
						}
						p = -s * s2 * c3 * el1 * e[l] / dl1;
						e[l] = s * p;
						d[l] = c * p;

						// Check for convergence.

					} while (Math.abs(e[l]) > eps*tst1);
				}
				d[l] = d[l] + f;
				e[l] = 0.0;
			}

			// Sort eigenvalues and corresponding vectors.

			for (int i = 0; i < n-1; i++) {
				int k = i;
				double p = d[i];
				for (int j = i+1; j < n; j++) {
					if (d[j] < p) { // NH find smallest k>i
						k = j;
						p = d[j];
					}
				}
				if (k != i) {
					d[k] = d[i]; // swap k and i 
					d[i] = p;   
					for (int j = 0; j < n; j++) {
						p = V[j][i];
						V[j][i] = V[j][k];
						V[j][k] = p;
					}
				}
			}
		} // tql2

		/** not really in use so far, just clones and copies
		 * 
		 * @param popx genotype
		 * @param popy phenotype, repaired
		 * @return popy
		 */
		double[][] genoPhenoTransformation(double[][] popx, double[][] popy) {
			if (popy == null || popy == popx || popy.length != popx.length) 
				popy = new double[popx.length][];

			for (int i = 0; i < popy.length; ++i)
				popy[i] = genoPhenoTransformation(popx[i], popy[i]);

			return popy;
		}
		/** not really in use so far, just clones and copies
		 * 
		 * @param popx genotype
		 * @param popy phenotype, repaired
		 * @return popy
		 */
		double[][] phenoGenoTransformation(double[][] popx, double[][] popy) {
			if (popy == null || popy == popx || popy.length != popx.length) 
				popy = new double[popx.length][];

			for (int i = 0; i < popy.length; ++i)
				popy[i] = phenoGenoTransformation(popx[i], popy[i]);

			return popy;
		}

		/** not really in use so far, just clones and copies
		 * 
		 * @param x genotype
		 * @param y phenotype
		 * @return y
		 */
		double[] genoPhenoTransformation(double[] x, double[] y) {
			if (y == null || y == x || y.length != x.length) {
				y = x.clone();
				return y; // for now return an identical copy
			}
			for(int i = 0; i < N; ++i)
				y[i] = x[i];
			return y;		
		}
		/** not really in use so far, just clones and copies
		 * 
		 * @param x genotype
		 * @param y phenotype
		 * @return y
		 */
		double[] phenoGenoTransformation(double[] x, double[] y) {
			if (y == null || y == x || y.length != x.length) {
				y = x.clone();
				return y; // for now return an identical copy
			}
			for(int i = 0; i < N; ++i)
				y[i] = x[i];
			return y;		
		}

		/**
		 * Samples the recent search distribution lambda times
		 * @return double[][] population, lambda times dimension array of sampled solutions, 
		 *   where <code>lambda == parameters.getPopulationSize()</code> 
		 * @see #resampleSingle(int)
		 * @see #updateDistribution(double[])
		 * @see CMAParameters#getPopulationSize()
		 */
		public double[][] samplePopulation() {
			int i, j, iNk;
			double sum;

			if (state < 0)
				init();
			else if (state < 3 && state > 2)
				error("mixing of calls to updateSingle() and samplePopulation() is not possible");
			else    
				eigendecomposition(0); // latest possibility to generate B and diagD

			if (state != 1)
				++countiter; 
			state = 1; // can be repeatedly called without problem
			idxRecentOffspring = sp.getLambda() - 1; // not really necessary at the moment


			// ensure maximal and minimal standard deviations
			if (options.lowerStandardDeviations != null && options.lowerStandardDeviations.length > 0)
				for (i = 0; i < N; ++i) {
					double d = options.lowerStandardDeviations[Math.min(i,options.lowerStandardDeviations.length-1)]; 
					if(d > sigma * minsqrtdiagC) 
						sigma = d / minsqrtdiagC;
				}
			if (options.upperStandardDeviations != null && options.upperStandardDeviations.length > 0)
				for (i = 0; i < N; ++i) {
					double d = options.upperStandardDeviations[Math.min(i,options.upperStandardDeviations.length-1)]; 
					if (d < sigma * maxsqrtdiagC) 
						sigma = d / maxsqrtdiagC;
				}

			testAndCorrectNumerics();

			/* sample the distribution */
			for (iNk = 0; iNk < sp.getLambda(); ++iNk) { /*
			 * generate scaled
			 * random vector (D * z)
			 */

				// code duplication from resampleSingle because of possible future resampling before GenoPheno
				/* generate scaled random vector (D * z) */
				if (flgdiag)  
					for (i = 0; i < N; ++i)
						arx[iNk][i] = xmean[i] + sigma * diagD[i] * rand.nextGaussian();
				else {
					for (i = 0; i < N; ++i) 
						artmp[i] = diagD[i] * rand.nextGaussian();

					/* add mutation (sigma * B * (D*z)) */
					for (i = 0; i < N; ++i) {
						for (j = 0, sum = 0; j < N; ++j)
							sum += B[i][j] * artmp[j];
						arx[iNk][i] = xmean[i] + sigma * sum;
					}
				}
				// redo this while isOutOfBounds(arx[iNk])
			}

			// I am desperately missing a const/readonly/visible qualifier. 
			return population = genoPhenoTransformation(arx, population);

		} // end samplePopulation()

		/** re-generate the <code>index</code>-th solution. After getting lambda
		 * solution points with samplePopulation() the i-th point,
		 * i=0...lambda-1, can be sampled anew by resampleSingle(i). 
		 * 
		 * <PRE>
		 * double[][] pop = cma.samplePopulation();
		 * // check some stuff, i-th solution went wrong, therefore
		 * pop[i] = cma.resampleSingle(i); // assignment to keep the population consistent
		 * for (i = 0,...)
		 *   fitness[i] = fitfun.valueof(pop[i]);
		 * </PRE>
		 *
		 * @see #samplePopulation()
		 */
		public double[] resampleSingle(int index) {
			int i,j;
			double sum;
			if (state != 1)
				error("call samplePopulation before calling resampleSingle(int index)");

			/* sample the distribution */
			/* generate scaled random vector (D * z) */
			if (flgdiag)
				for (i = 0; i < N; ++i)
					arx[index][i] = xmean[i] + sigma * diagD[i] * rand.nextGaussian();
			else {
				for (i = 0; i < N; ++i) 
					artmp[i] = diagD[i] * rand.nextGaussian();

				/* add mutation (sigma * B * (D*z)) */
				for (i = 0; i < N; ++i) {
					for (j = 0, sum = 0; j < N; ++j)
						sum += B[i][j] * artmp[j];
					arx[index][i] = xmean[i] + sigma * sum;
				}
			}
			return population[index] = genoPhenoTransformation(arx[index], population[index]); 
		} // resampleSingle

		/** compute Mahalanobis norm of x - mean w.r.t. the current distribution 
		 * (using covariance matrix times squared step-size for the inner product). 
		 * TODO: to be tested. 
		 * @param x
		 * @param mean
		 * @return Malanobis norm of x - mean: sqrt((x-mean)' C^-1 (x-mean)) / sigma
		 */
		public double mahalanobisNorm(double[] x, double[] mean) {
			double yi, snorm = 0;
			int i, j; 
			// snorm = (x-mean)' Cinverse (x-mean) = (x-mean)' (BD^2B')^-1 (x-mean)
			//       = (x-mean)' B'^-1 D^-2 B^-1 (x-mean) 
			//       = (x-mean)' B D^-1 D^-1 B' (x-mean)
			//       = (D^-1 B' (x-mean))' * (D^-1 B' (x-mean))
			/* calculate z := D^(-1) * B^(-1) * BDz into artmp, we could have stored z instead */
			for (i = 0; i < N; ++i) {
				for (j = 0, yi = 0.; j < N; ++j)
					yi += B[j][i] * (x[j]-mean[j]);
				// yi = i-th component of B' (x-mean)
				snorm += yi * yi / diagD[i] / diagD[i];
			}
			return Math.sqrt(snorm) / sigma;
		}

		/** update of the search distribution from a population and its 
		 * function values, see {@link #updateDistribution(double[][], double[], 0)}. 
		 * This might become updateDistribution(double[][], double[], popsize)
		 * in future. 
		 * 
		 * @param population  double[lambda][N], lambda solutions
		 * @param functionValues  double[lambda], respective objective values of population
		 * 
		 * @see #samplePopulation()
		 * @see #updateDistribution(double[])
		 * @see #updateDistribution(double[][], double[], int)
		 */
		public void updateDistribution(double[][] population, double[] functionValues) {
			updateDistribution(population, functionValues, 0);
		}

		/** update of the search distribution from a population and its 
		 * function values, an alternative interface for
		 * {@link #updateDistribution(double[] functionValues)}. functionValues is used to establish an 
		 * ordering of the elements in population. The first nInjected elements do not need to originate 
		 * from #samplePopulation() or can have been modified (TODO: to be tested). 
		 * 
		 * @param population  double[lambda][N], lambda solutions
		 * @param functionValues  double[lambda], respective objective values of population
		 * @param nInjected  int, first nInjected solutions of population were not sampled by 
		 * samplePopulation() or modified afterwards
		 * 
		 * @see #samplePopulation()
		 * @see #updateDistribution(double[])
		 */
		public void updateDistribution(double[][] population, double[] functionValues, int nInjected) {
			// TODO: Needs to be tested yet for nInjected > 0
			// pass first input argument
			arx = phenoGenoTransformation(population, null); // TODO should still be tested
			for (int i = 0; i < nInjected; ++i) {
				warning("TODO: checking of injected solution has not yet been tested");
				// if (mahalanobisNorm(arx[0], xmean) > Math.sqrt(N) + 2) // testing: seems fine
				//     System.out.println(mahalanobisNorm(arx[i], xmean)/Math.sqrt(N));
				double upperLength = Math.sqrt(N) + 2. * N / (N+2.);  // should become an interfaced parameter? 
				double fac = upperLength / mahalanobisNorm(arx[i], xmean); 
				if (fac < 1)
					for (int j = 0; j < N; ++j)
						arx[i][j] = xmean[j] + fac * (arx[i][j] - xmean[j]);
			}
			updateDistribution(functionValues);
		}

		/** update of the search distribution after samplePopulation(). functionValues 
		 * determines the selection order (ranking) for the solutions in the previously sampled 
		 * population. This is just a different interface for updateDistribution(double[][], double[]).  
		 * @see #samplePopulation()
		 * @see #updateDistribution(double[][], double[])
		 */
		public void updateDistribution(double[] functionValues) {
			if (state == 3) {
				error("updateDistribution() was already called");
			}
			if (functionValues.length != sp.getLambda())
				error("argument double[] funcionValues.length=" + functionValues.length 
						+ "!=" + "lambda=" + sp.getLambda());

			/* pass input argument */
			for (int i = 0; i < sp.getLambda(); ++i) {
				fit.raw[i].val = functionValues[i];
				fit.raw[i].i = i;
			}

			counteval += sp.getLambda();
			recentFunctionValue = math.min(fit.raw).val;
			recentMaxFunctionValue = math.max(fit.raw).val;
			recentMinFunctionValue = math.min(fit.raw).val;
			updateDistribution();
		}

		//private IntDouble[] computePenalties() {
		//	int i, j, iNk;
		//	/* penalize repairment, eg. for boundaries */
		//	// TODO: figure out whether the change of penalty is too large or fast which can disturb selection
		//	//       this depence in particular on the length of fit.medianDeltaFit
		//	if (true || countiter < fit.deltaFitHist.length || countiter % 1*(N+2) == 0) {
		//		// minimum of differences with distance lambda/2, better the 25%tile?
		//		// assumes sorted array!! 
		//		int ii = (sp.getLambda()) / 2;
		//		double medianDeltaFit = Math.abs(fit.funValues[ii].d - fit.funValues[0].d);
		//		for (i = 1; i + ii < sp.getLambda(); ++i) 
		//			// minimum because of outliers 
		//			medianDeltaFit = Math.min(medianDeltaFit, Math.abs(fit.funValues[ii+i].d - fit.funValues[i].d));
		//		medianDeltaFit /= sigma * sigma; // should be somehow constant, because dfit depends on sigma (verified on sphere)
		//		if (medianDeltaFit > 0) {
		////			System.out.println("set" + medianDeltaFit + " " + math.median(fit.medianDeltaFit));
		//			if (fit.idxDeltaFitHist == -1) // first time: write all fields
		//				for (i = 0; i < fit.deltaFitHist.length; ++i)
		//					fit.deltaFitHist[i] = medianDeltaFit;
		//			if (++fit.idxDeltaFitHist == fit.deltaFitHist.length)
		//				fit.idxDeltaFitHist = 0;
		//			// save last five values in fit.medianDeltaFit
		//			fit.deltaFitHist[fit.idxDeltaFitHist] = medianDeltaFit;
		//		}                
		//	}
		//	/* calculate fitness by adding function value and repair penalty */
		//	double penfactor = 1. * 5. * math.median(fit.deltaFitHist);
		//	for (iNk = 0; iNk < sp.getLambda(); ++iNk) {
		//		double sqrnorm = 0;
		//		double prod = Math.pow(math.prod(diagD), 1.0/(double)N);
		//		/* calculate C^-1-norm of Delta x: norm(D^(-1) * B^(-1) * (Delta x))^2 */
		//		for (i = 0; i < N; ++i) {
		//			double sum = 0.0;
		//			for (j = 0, sum = 0.; j < N; ++j)
		//				sum += B[j][i] * ((arxrepaired[fit.funValues[iNk].i][j] - arx[fit.funValues[iNk].i][j]));
		//			sqrnorm += math.square(sum / (Math.pow(diagD[i], 0.9) * Math.pow(prod, 0.10))); // regularization to I
		//		}
		//		// sqrnorm/N equals approximately 1/sigma^2
		//		fit.fitness[iNk].d = fit.funValues[iNk].d + penfactor * sqrnorm / (N+2); // / (sigma * sigma);
		//		fit.fitness[iNk].i = fit.funValues[iNk].i;
		//		// System.out.println(math.median(fit.medianDeltaFit) + " " + sqrnorm / (N+2)); // / (sigma * sigma));
		//	}
		////	if (countiter % 10 == 1)
		////	System.out.println(math.median(fit.medianDeltaFit) + " " + sqrnorm);
		//	return fit.fitness;
		//
		//}

		private void updateDistribution() {

			int i, j, k, iNk, hsig;
			double sum;
			double psxps;

			if (state == 3) {
				error("updateDistribution() was already called");
			}

			/* sort function values */
			Arrays.sort(fit.raw, fit.raw[0]);

			for (iNk = 0; iNk < sp.getLambda(); ++iNk) {
				fit.fitness[iNk].val = fit.raw[iNk].val; // superfluous at time
				fit.fitness[iNk].i = fit.raw[iNk].i;
			}

			/* update fitness history */ 
			for (i = fit.history.length - 1; i > 0; --i)
				fit.history[i] = fit.history[i - 1];
			fit.history[0] = fit.raw[0].val;

			/* save/update bestever-value */
			updateBestEver(arx[fit.raw[0].i], fit.raw[0].val, 
					counteval - sp.getLambda() + fit.raw[0].i + 1);

			/* re-calculate diagonal flag */
			flgdiag = (options.diagonalCovarianceMatrix == 1 || options.diagonalCovarianceMatrix >= countiter); 
			if (options.diagonalCovarianceMatrix == -1) // options might have been re-read
				flgdiag = (countiter <= 1 * 150 * N / sp.lambda);  // CAVE: duplication of "default"

			/* calculate xmean and BDz~N(0,C) */
			for (i = 0; i < N; ++i) {
				xold[i] = xmean[i];
				xmean[i] = 0.;
				for (iNk = 0; iNk < sp.getMu(); ++iNk)
					xmean[i] += sp.getWeights()[iNk] * arx[fit.fitness[iNk].i][i];
				BDz[i] = Math.sqrt(sp.getMueff()) * (xmean[i] - xold[i]) / sigma;
			}

			/* cumulation for sigma (ps) using B*z */
			if (flgdiag) {
				/* given B=I we have B*z = z = D^-1 BDz  */
				for (i = 0; i < N; ++i) {
					ps[i] = (1. - sp.getCs()) * ps[i]
							+ Math.sqrt(sp.getCs() * (2. - sp.getCs())) 
							* BDz[i] / diagD[i];
				}
			} else {
				/* calculate z := D^(-1) * B^(-1) * BDz into artmp, we could have stored z instead */
				for (i = 0; i < N; ++i) {
					for (j = 0, sum = 0.; j < N; ++j)
						sum += B[j][i] * BDz[j];
					artmp[i] = sum / diagD[i];
				}
				/* cumulation for sigma (ps) using B*z */
				for (i = 0; i < N; ++i) {
					for (j = 0, sum = 0.; j < N; ++j)
						sum += B[i][j] * artmp[j];
					ps[i] = (1. - sp.getCs()) * ps[i]
							+ Math.sqrt(sp.getCs() * (2. - sp.getCs())) * sum;
				}
			}

			/* calculate norm(ps)^2 */
			psxps = 0;
			for (i = 0; i < N; ++i)
				psxps += ps[i] * ps[i];

			/* cumulation for covariance matrix (pc) using B*D*z~N(0,C) */
			hsig = 0;
			if (Math.sqrt(psxps)
					/ Math.sqrt(1. - Math.pow(1. - sp.getCs(), 2. * countiter))
					/ sp.chiN < 1.4 + 2. / (N + 1.)) {
				hsig = 1;
			}
			for (i = 0; i < N; ++i) {
				pc[i] = (1. - sp.getCc()) * pc[i] + hsig
						* Math.sqrt(sp.getCc() * (2. - sp.getCc())) * BDz[i];
			}

			/* stop initial phase, not in use anymore as hsig does the job */
			if (iniphase
					&& countiter > Math.min(1 / sp.getCs(), 1 + N / sp.getMucov()))
				if (psxps / sp.getDamps()
						/ (1. - Math.pow((1. - sp.getCs()), countiter)) < N * 1.05)
					iniphase = false;

			/* this, it is harmful in a dynamic environment
			 * remove momentum in ps, if ps is large and fitness is getting worse */
			//    if (1 < 3 && psxps / N > 1.5 + 10 * Math.sqrt(2. / N)
			//    		&& fit.history[0] > fit.history[1] && fit.history[0] > fit.history[2]) {
			//      double tfac;
			//
			//    	infoVerbose(countiter + ": remove momentum " + psxps / N + " "
			//    			+ ps[0] + " " + sigma);
			//
			//    	tfac = Math.sqrt((1 + Math.max(0, Math.log(psxps / N))) * N / psxps);
			//    	for (i = 0; i < N; ++i)
			//    		ps[i] *= tfac;
			//    	psxps *= tfac * tfac;
			//    }

			/* update of C */
			if (sp.getCcov() > 0 && iniphase == false) {

				++countCupdatesSinceEigenupdate;

				/* update covariance matrix */
				for (i = 0; i < N; ++i)
					for (j = (flgdiag ? i : 0); 
					j <= i; ++j) {
						C[i][j] = (1 - sp.getCcov(flgdiag))
								* C[i][j]
										+ sp.getCcov()
										* (1. / sp.getMucov())
										* (pc[i] * pc[j] + (1 - hsig) * sp.getCc()
												* (2. - sp.getCc()) * C[i][j]);
						for (k = 0; k < sp.getMu(); ++k) { /*
						 * additional rank mu
						 * update
						 */
						 C[i][j] += sp.getCcov() * (1 - 1. / sp.getMucov())
								 * sp.getWeights()[k]
										 * (arx[fit.fitness[k].i][i] - xold[i])
										 * (arx[fit.fitness[k].i][j] - xold[j]) / sigma
										 / sigma;
						}
					}
				maxsqrtdiagC = Math.sqrt(math.max(math.diag(C)));
				minsqrtdiagC = Math.sqrt(math.min(math.diag(C)));
			} // update of C

			/* update of sigma */
			sigma *= Math.exp(((Math.sqrt(psxps) / sp.chiN) - 1) * sp.getCs()
					/ sp.getDamps());

			state = 3;

		} // updateDistribution()

		/** assigns lhs to a different instance with the same values, 
		 * sort of smart clone, but it may be that clone is as smart already 
		 * 
		 * @param rhs
		 * @param lhs
		 * @return
		 */
		double[] assignNew(double[] rhs, double[] lhs) {
			assert rhs != null; // will produce an error anyway
			if(lhs != null && lhs != rhs && lhs.length == rhs.length)
				for(int i = 0; i < lhs.length; ++i)
					lhs[i] = rhs[i];
			else
				lhs = rhs.clone();
			return lhs;
		}
		void updateBestEver(double[] x, double fitness, long eval) {
			if (fitness < bestever_fit || Double.isNaN(bestever_fit)) {  // countiter == 1 not needed anymore
				bestever_fit = fitness;
				bestever_eval = eval;
				bestever_x = assignNew(x, bestever_x); // save (hopefully) efficient assignment
			}
		}

		/** ratio between length of longest and shortest axis 
		 * of the distribution ellipsoid, which is the square root
		 * of the largest divided by the smallest eigenvalue of the covariance matrix 
		 */
		public double getAxisRatio() {
			return axisratio;
		}

		/** get best evaluated solution found so far. 
		 * Remark that the distribution mean was not evaluated 
		 * but is expected to have an even better function value. 
		 * <p>Example: getBestSolution 
		 * @return best solution (search point) found so far 
		 * @see #getBestRecentSolution() 
		 * @see #getBestX() 
		 * @see #getMeanX() */
		public CMASolution getBestSolution() {
			return new CMASolution(bestever_x, bestever_fit, bestever_eval);
		}

		/** eventually replaces the best-ever solution 
		 * 
		 * @param fitness function value computed for the solution {@link #getMeanX()}
		 * @return best-ever solution
		 */
		public CMASolution setFitnessOfMeanX(double fitness) {
			xmean_fit = fitness;
			++counteval;
			updateBestEver(xmean, fitness, counteval);
			return new CMASolution(bestever_x, bestever_fit, bestever_eval);
		}

		/** get best evaluated search point found so far. 
		 * Remark that the distribution mean was not evaluated 
		 * but is expected to have an even better function value. 
		 * @return best search point found so far as double[]
		 * @see #getMeanX() */
		public double[] getBestX() {
			if (state < 0)
				return null;
			return bestever_x.clone();
		}

		/** objective function value of best solution found so far.
		 * @return objective function value of best solution found so far
		 * @see #getBestSolution()
		 */
		public double getBestFunctionValue() {
			if (state < 0)
				return Double.NaN;
			return bestever_fit;
		}
		/* * evaluation count when the best solution was found
		 * 
		 */
		public long getBestEvaluationNumber() {
			return bestever_eval;
		}

		/** Get best evaluated solution of the last (recent) iteration. 
		 * This solution is supposed to be more robust than the 
		 * best ever solution in particular in possible case of 
		 * mis-attributed good fitness values.
		 * Remark that the distribution mean was not evaluated 
		 * but is expected to have an better function value. 
		 * @return best solution (search point) in recent iteration 
		 * @see #getBestSolution() 
		 * @see #getBestRecentX() 
		 * @see #getMeanX() */
		public ISolutionPoint getBestRecentSolution() {
			return new CMASolution(genoPhenoTransformation(arx[fit.raw[0].i], null), 
					fit.raw[0].val, 
					counteval - sp.getLambda() + fit.raw[0].i + 1);
		}

		/** best search point of the recent iteration. 
		 * @return Returns the recentFunctionValue.
		 * @see #getBestRecentFunctionValue()
		 */
		public double[] getBestRecentX() {
			return genoPhenoTransformation(arx[fit.raw[0].i], null);
		}

		/** objective function value of the,
		 * best solution in the 
		 * recent iteration (population)
		 * @return Returns the recentFunctionValue.
		 * @see #getBestEvaluationNumber()
		 * @see #getBestFunctionValue()
		 */
		public double getBestRecentFunctionValue() {
			return recentMinFunctionValue;
		}

		/** objective function value of the, 
		 * worst solution of the recent iteration.
		 * @return Returns the recentMaxFunctionValue.
		 */
		public double getWorstRecentFunctionValue() {
			return recentMaxFunctionValue;
		}

		/** Get mean of the current search distribution. The mean should
		 * be regarded as the best estimator for the global
		 * optimimum at the given iteration. In particular for noisy
		 * problems the distribution mean is the solution of choice
		 * preferable to the best or recent best. The return value is 
		 * <em>not</em> a copy. Therefore it should not be change it, without 
		 * deep knowledge of the code (the effect of a mean change depends on
		 * the chosen transscription/implementation of the algorithm). 
		 * @return mean value of the current search distribution
		 * @see #getBestX() 
		 * @see #getBestRecentX() 
		 */
		public double[] getMeanX() {
			return xmean.clone();
		}

		public int getDimension() {
			return N;
		}

		/**
		 * number of objective function evaluations counted so far
		 */
		public long getCountEval() {
			return counteval;
		}

		/**
		 * number of iterations conducted so far 
		 */
		public long getCountIter() {
			return countiter;
		}

		/** the final setting of initial <code>x</code> can 
		 * be retrieved only after <code>init()</code> was called
		 * 
		 * @return <code>double[] initialX</code> start point chosen for 
		 * distribution mean value <code>xmean</code>
		 */ 
		public double[] getInitialX() {
			if (state < 0)
				error("initiaX not yet available, init() must be called first");
			return initialX.clone();
		}



		/** get used random number generator instance */
		public Random getRand() {
			return rand;
		}

		/** get properties previously read from a property file.
		 * 
		 * @return java.util.Properties key-value hash table
		 * @see #readProperties()
		 */
		public Properties getProperties() {
			return properties;
		}

		/**@see #setSeed(long) */
		public long getSeed() {
			return seed;
		}
		///** Set lower and upper boundary in all variables 
		// * 
		// * @param xlow
		// * @param xup
		// */
		//public void setBoundaries(double xlow, double xup) {
		//    int len = 1;
		//    if (N > 0)
		//        len = N;
		//    LBound = new double[len];
		//    UBound = new double[len];
		//    for (int i= 0; i < len; ++i) {
		//        LBound[i] = xlow;
		//        UBound[i] = xup;
		//    }
		//}
		///** sets lower and upper boundaries in all variables. 
		// * 
		// * @param xlow lower boundary double[], can be 1-D or of length of the number of variables (dimension). 
		// * @param xup see xlow
		// */
		//public void setBoundaries(double[] xlow, double[] xup) {
		//    if( xlow == null || xup ==  null)
		//        error("boundaries cannot be null");
		//    if (xlow.length == 1 && xup.length == 1) {
		//        setBoundaries(xlow[0], xup[0]);
		//        return;
		//    }
		//    if ((N > 0 && (N != xlow.length || N != xup.length)) 
		//        || (xlow.length != xup.length))
		//        error("dimensions of boundaries do not match");
		//    this.LBound = xlow;
		//    this.UBound = xup;
		//    N = xlow.length; // changes N only if N was 0
		//}

		/**
		 * number of objective function evaluations counted so far
		 */
		public long setCountEval(long c) {
			return counteval = c;
		}

		/** search space dimensions must be set before the optimization is started. */
		public void setDimension(int n) {
			if ((lockDimension > 0 || state >= 0) && N != n)
				error("dimension cannot be changed anymore or contradicts to initialX");
			N = n;
		}

		/** sets typicalX value, the same value in each coordinate
		 * @see #setTypicalX(double[])
		 */
		public void setTypicalX(double x) {
			if (state >= 0)
				error("typical x cannot be set anymore");
			typicalX = new double[]{x}; // allows "late binding" of dimension
		}

		/** sets typicalX value, which will be overwritten by initialX setting from properties 
		 * or {@link #setInitialX(double[])} function call. 
		 * Otherwise the initialX is sampled normally distributed from typicalX with initialStandardDeviations
		 * 
		 * @see #setTypicalX(double)
		 * @see #setInitialX(double[])
		 * @see #setInitialStandardDeviations(double[])
		 */
		public void setTypicalX(double[] x) {
			if (state >= 0)
				error("typical x cannot be set anymore");
			if (x.length == 1) { // to make properties work
				setTypicalX(x[0]);
				return;
			}
			if (N < 1)
				setDimension(x.length);
			if (N != x.length)
				error("dimensions N=" + N + " and input x.length=" + x.length + "do not agree");
			typicalX = new double[N];
			for (int i = 0; i < N; ++i)
				typicalX[i] = x[i];
			lockDimension = 1;
		}

		public void setInitialStandardDeviation(double startsigma) {
			if (state >= 0)
				error("standard deviations cannot be set anymore");
			this.startsigma = new double[]{startsigma};
		}

		public void setInitialStandardDeviations(double[] startsigma) {
			// assert startsigma != null; // assert should not be used for public arg check
			if (state >= 0)
				error("standard deviations cannot be set anymore");
			if (startsigma.length == 1) { // to make properties work
				setInitialStandardDeviation(startsigma[0]);
				return;
			}
			if (N > 0 && N != startsigma.length)
				error("dimensions N=" + N + " and input startsigma.length=" 
						+ startsigma.length + "do not agree");
			if (N == 0)
				setDimension(startsigma.length);
			assert N == startsigma.length;
			this.startsigma = startsigma.clone();
			lockDimension = 1;
		}

		/** sets <code>initialX</code> to the same value in each coordinate
		 * 
		 * @param x value
		 * @see #setInitialX(double[])
		 */
		public void setInitialX(double x) {
			if (state >= 0)
				error("initial x cannot be set anymore");
			xmean = new double[]{x}; // allows "late binding" of dimension N
		}

		/** set initial seach point <code>xmean</code> coordinate-wise uniform 
		 * between <code>l</code> and <code>u</code>, 
		 * dimension needs to have been set before
		 * 
		 * @param l double lower value
		 * @param u double upper value 
		 * @see #setInitialX(double[])
		 * @see #setInitialX(double[], double[])
		 * */
		public void setInitialX(double l, double u) {
			if (state >= 0)
				error("initial x cannot be set anymore");
			if (N < 1)
				error("dimension must have been specified before"); 
			xmean = new double[N];
			for (int i = 0; i < xmean.length; ++i)
				xmean[i] = l + (u-l) * rand.nextDouble();
			lockDimension = 1;
		}

		/** set initial seach point <code>x</code> coordinate-wise uniform 
		 * between <code>l</code> and <code>u</code>, 
		 * dimension needs to have been set before
		 * @param l double lower value
		 * @param u double upper value */
		public void setInitialX(double[] l, double[] u) {
			if (state >= 0)
				error("initial x cannot be set anymore");
			if (l.length != u.length)
				error("length of lower and upper values disagree");
			setDimension(l.length);
			xmean = new double[N];
			for (int i = 0; i < xmean.length; ++i)
				xmean[i] = l[i] + (u[i]-l[i]) * rand.nextDouble();
			lockDimension = 1;
		}

		/** set initial search point to input value <code>x</code>. <code>x.length==1</code> is possible, otherwise 
		 * the search space dimension is set to <code>x.length</code> irrevocably
		 * 
		 * @param x double[] initial point
		 * @see #setInitialX(double)
		 * @see #setInitialX(double, double) 
		 */
		public void setInitialX(double[] x) {
			if (state >= 0)
				error("initial x cannot be set anymore");
			if (x.length == 1) { // to make properties work
				setInitialX(x[0]);
				return;
			}
			if (N > 0 && N != x.length)
				error("dimensions do not match");
			if (N == 0)
				setDimension(x.length);
			assert N == x.length;
			xmean = new double[N];
			for (int i = 0; i < N; ++i)
				xmean[i] = x[i];
			lockDimension = 1; // because xmean is set up
		}

		public void setRand(Random rand) {
			this.rand = rand;
		}

		/** Setter for the seed for the random number generator
		 * java.util.Random(seed). Changing the seed will only take
		 * effect before {@link #init()} was called.
		 *@param seed a long value to initialize java.util.Random(seed) 
		 */ 
		public void setSeed(long seed) { 
			if (state >= 0)
				warning("setting seed has no effect at this point");
			else {
				if (seed <= 0) 
					seed = System.currentTimeMillis();
				this.seed = seed;
				rand.setSeed(seed);
			}
		}

		/** printing output in a viewable formatting style. The printing  
		 * <pre>
		 * Iteration,#Fevals: rb Function Value Delta( best ,worst) |idx: Max SD idx: Min SD  | minsigD  sigma Axisratio | time, in eig
		 *   164( 8),   1638: 5.5061568003892640e-08 (-4e-08,3e-08) |  0: 3.3e-05  8: 1.5e-05 | 1.4e-05 5.6e-05   2.34   |  0.1  0.0 
		 *</pre>
		 * shows the value of getPrintAnnotation() in the first line and in the second line
		 * <li>164 iteration number 
		 * <li>( 8) recently sampled search point in this iteration, 
		 * <li>1638: number of function evaluations
		 * <li>5.5061568003892640e-08 objective function value F of the best point 
		 * in the recent generation
		 * <li>(-4e-08, difference between the best ever evaluated function value to F,
		 * <li>3e-08) | difference between the worst function value of the recent generation to F
		 * <li>0: index of coordinate with largest standard deviation
		 * <li>3.3e-05 respective standard deviation
		 * <li>8: index of coordinate with smallest standard deviation
		 * <li>1.5e-05 | respective standard deviation
		 * <li>index of coordinate with smallest standard deviation: respective standard deviation
		 * <li>| 1.4e-05 standard deviation in smallest principal axis direction
		 * <li> 5.6e-05 sigma
		 * <li> 2.34 axisratio, ie. quotient between the standard deviations in largest an 
		 * smallest principal axis directions, ie. square root of the quotient between largest 
		 * and smallest eigenvalue of covariance matrix C
		 * <li> 0.1 time, overall elapsed time in seconds
		 * <li> 0.0 in eig, overall time spent within eigendecompostion
		 * @see #getPrintAnnotation()
		 * */
		public String getPrintLine() {
			/* 				   String.format(Locale.US, " %1$4d(%2$2d): %3$5d ", 
         new Object[]{
         new Long(countiter),
         new Integer(idxRecentOffspring),
         new Long(counteval)
         })  
         + String.format(Locale.US, "%1$.16e (%2$+.0e %3$.0e)", 
         new Object[]{
         new Double(recentFunctionValue),
         new Double(getBestFunctionValue() - recentFunctionValue),
         new Double(recentMaxFunctionValue - recentFunctionValue)
         })  
         + String.format(Locale.US, "%1$7.2f ", 
         new Object[]{
         new Double(axisratio)
         }) 
         + String.format(Locale.US, "%1$2d:%2$8.1e %3$2d:%4$8.1e", 
         new Object[]{
         new Integer(math.minidx(math.diag(C))), 
         new Double(sigma * 
         Math.sqrt(math.min(math.diag(C)))),
         new Integer(math.maxidx(math.diag(C))), 
         new Double(sigma * 
         Math.sqrt(math.max(math.diag(C))))
         })
			 */
			String s;
			if (state < 0)
				s = new String(
						new PrintfFormat(Locale.US, " %4d").sprintf(countiter) +
						new PrintfFormat(Locale.US, "(%2d), ").sprintf(0) + 
						new PrintfFormat(Locale.US, "%6.0d: ").sprintf(counteval));
			else    
				s = new String(
						new PrintfFormat(Locale.US, " %4d").sprintf(countiter) +
						new PrintfFormat(Locale.US, "(%2d), ").sprintf(idxRecentOffspring+1) + 
						new PrintfFormat(Locale.US, "%6.0d: ").sprintf(counteval) + 
						new PrintfFormat(Locale.US, "%.16e ").sprintf(recentFunctionValue) + 
						new PrintfFormat(Locale.US, "(%+.0e,").sprintf(getBestFunctionValue() - recentFunctionValue) + 
						new PrintfFormat(Locale.US, "%.0e) | ").sprintf(recentMaxFunctionValue - recentFunctionValue) + 
						new PrintfFormat(Locale.US, "%2d:").sprintf(math.maxidx(math.diag(C))) + 
						new PrintfFormat(Locale.US, "%8.1e ").sprintf(sigma * maxsqrtdiagC) +
						new PrintfFormat(Locale.US, "%2d:").sprintf(math.minidx(math.diag(C))) + 
						new PrintfFormat(Locale.US, "%8.1e ").sprintf(sigma * minsqrtdiagC) + 
						new PrintfFormat(Locale.US, "| %6.1e ").sprintf(sigma*math.min(diagD)) +
						new PrintfFormat(Locale.US, "%6.1e ").sprintf(sigma) + 
						new PrintfFormat(Locale.US, "%6.2f").sprintf(axisratio) +
						new PrintfFormat(Locale.US, "   | %4.1f ").sprintf((System.currentTimeMillis()-timings.start) / 1000.) +
						new PrintfFormat(Locale.US, "%4.1f ").sprintf(timings.eigendecomposition / 1000.) 
						);

			return s;

			/*
         return new String(
         new Long(countiter) 
         + " " +						   new Integer(idxRecentOffspring)
         + " " +						   new Long(counteval)
         + " " +						   new Double(recentFunctionValue)
         //				+ " " +  						   new Double(FunctionValue() - recentFunctionValue)
          //				+ " " +  						   new Double(recentMaxFunctionValue - recentFunctionValue)
           + " " +  						   new Double(axisratio)
           + " " +  						   new Integer(math.minidx(math.diag(C))) 
           + " " +  						   new Double(sigma * 
           Math.sqrt(math.min(math.diag(C))))
           + " " +						   new Integer(math.maxidx(math.diag(C))) 
           + " " +						   new Double(sigma * 
           Math.sqrt(math.max(math.diag(C))))
           );
			 */
			/* formatting template
         String.format(Locale.US, "%1$6.2e %2$+.0e", 
         new Object[]{
         new Double(),
         new Double()
         })  

			 */		   
			//		   out.print(math.min(diagD));
			//      out.print(" ");
			//      new DecimalFormat("0.00E0").format((3.34)) + " " + 
			//      (cma.fit.fitness[(cma.parameters.getLambda()/2)].d 
			//      - cma.fit.fitness[0].d) + "," +
			//      cma.fit.fitness[cma.parameters.getLambda()-1].d + ") | " +

		}

		/** returns an annotation string for the printings of method println(). */
		public String getPrintAnnotation() {
			String s = new String(
					"Iteration,#Fevals: rb Function Value Delta( best ,worst) |idx: Max SD idx: Min SD  | minsigD  sigma Axisratio | time, in eig");
			//         491( 3),   3924: 1.1245467061992267e+00 (-2e-01,4e-01)  9: 7.8e-05  2: 5.0e-02 | 5.9e-03 1.3e-02 660.41 

			return s;
		}

		/** returns an informative initial message of the CMA-ES optimizer */
		public String helloWorld() {
			String s = new String(
					"(" + sp.getMu() + "," + sp.getLambda() 
					+ ")-CMA-ES(mu_eff=" + Math.round(10.*sp.getMueff())/10. + "), Ver=\"" 
					+ versionNumber 
					+ "\", dimension=" + N 
					+ ", " + options.diagonalCovarianceMatrix + " diagonal iter." 
					+ ", randomSeed=" + seed
					+ " (" + new Date().toString() + ")");
			return s;

		}
		/** calls System.out.println(s) and writes s to the file outcmaesdisp.dat 
		 * by default, if writeDisplayToFile option is > 0
		 * @see #getPrintLine() 
		 */
		public void println(String s) {
			System.out.println(s);
			if (options.writeDisplayToFile > 0)
				writeToFile(options.outputFileNamesPrefix + "disp" + ".dat", s, 1);
		}

		/** calls println(getPrintLine()) 
		 * @see #getPrintLine() 
		 */
		public void println() {
			println(getPrintLine());
		}

		/** @see #getPrintAnnotation() */
		public void printlnAnnotation() {
			println(getPrintAnnotation());
		}

		/** calls println(helloWorld()) 
		 * @see #helloWorld() 
		 * @see #println(String)
		 */
		public void printlnHelloWorld() {
			println(helloWorld());
		}

		public String getDataRowFitness() {
			String s = new String();    
			s = countiter + " " + counteval + " " + sigma + " " + axisratio + " "
					+ bestever_fit + " ";
			if (mode == SINGLE_MODE)
				s += recentFunctionValue + " ";
			else  {
				s += fit.raw[0].val + " ";
				s += fit.raw[sp.getLambda()/2].val + " ";
				s += fit.raw[sp.getLambda()-1].val + " ";
				s += math.min(diagD) + " "  
						+ (math.maxidx(math.diag(C))+1) + " " + sigma*maxsqrtdiagC + " " 
						+ (math.minidx(math.diag(C))+1) + " "  + sigma*minsqrtdiagC;
				//for (int i = 0; i < sp.getLambda(); ++i) {
				//    s += fit.funValues[i].d + " ";
				//}
			}
			return s;
		}

		public String getDataRowXRecentBest() {
			int idx = 0;
			if (mode == SINGLE_MODE)
				idx = idxRecentOffspring; 
			String s = new String();    
			s = countiter + " " + counteval + " " + sigma + " 0 " 
					+ (state == 1 ? Double.NaN : fit.raw[idx].val) + " ";
			for (int i = 0; i < N; ++i) {
				s += arx[fit.raw[idx].i][i] + " ";
			}
			return s;
		}

		public String getDataRowXMean() {
			String s = new String();    
			s = countiter + " " + counteval + " " + sigma + " 0 0 ";
			for (int i = 0; i < N; ++i) {
				s += xmean[i] + " ";
			}
			return s;
		}
		/** 6-th to last column are sorted axis lengths axlen */
		public String getDataRowAxlen() {
			String s = new String();    
			s = countiter + " " + counteval + " " + sigma + " " + axisratio + " " 
					+ maxsqrtdiagC/minsqrtdiagC + " "; 
			double[] tmp = (double[]) diagD.clone();
			java.util.Arrays.sort(tmp);
			for (int i = 0; i < N; ++i) {
				s += tmp[i] + " ";
			}
			return s;
		}
		public String getDataRowStddev() {
			String s = new String();    
			s = countiter + " " + counteval + " " + sigma + " " 
					+ (1+math.maxidx(math.diag(C))) + " " + (1+math.minidx(math.diag(C))) + " ";
			for (int i = 0; i < N; ++i) {
				s += sigma * Math.sqrt(C[i][i]) + " ";
			}
			return s;
		}
		/** correlations and covariances of the search distribution. The
		 * first, '%#'-commented row contains itertation number,
		 * evaluation number, and sigma. In the remaining rows the upper
		 * triangular part contains variances and covariances
		 * sigma*sigma*c_ij. The lower part contains correlations c_ij /
		 * sqrt(c_ii * c_jj).  */
		public String getDataC() {
			int i, j;
			String s = new String();    
			s = "%# " + countiter + " " + counteval + " " + sigma + "\n";
			for (i = 0; i < N; ++i) {
				for (j = 0; j < i; ++j) // ouput correlation in the lower half
					s += C[i][j] / Math.sqrt(C[i][i] * C[j][j]) + " ";
				for (j = i; j < N; ++j) 
					s += sigma * sigma * C[i][j] + " ";
				s += "\n";
			}
			return s;
		}

		private String[] fileswritten = new String[]{""}; // also (re-)initialized in init()
		/** writes a string to a file, overwrites first, appends afterwards. 
		 * <p>Example: cma.writeToFile("cmaescorr.dat", cma.writeC()); 
		 * @param filename is a String giving the name of the file to be written
		 * @param data is a String of text/data to be written
		 * @param flgAppend for flgAppend>0 old data are not overwritten
		 */
		public void writeToFile(String filename, String data, int flgAppend) {
			boolean appendflag = flgAppend > 0;
			for (int i = 0; !appendflag && i < fileswritten.length; ++i)
				if(filename.equals(fileswritten[i])) {
					appendflag = true;
				}
			java.io.PrintWriter out = null;
			try {
				out = new java.io.PrintWriter(new java.io.FileWriter(filename, appendflag));
				out.println(data);
				out.flush(); // no idea whether this makes sense
				out.close();
			} catch (java.io.FileNotFoundException e) {
				warning("Could not find file '" + filename  + "'(FileNotFoundException)");
			} catch (java.io.IOException e) {
				warning("Could not open/write to file " + filename);
				//e.printStackTrace();            // output goes to System.err
				//e.printStackTrace(System.out);  // send trace to stdout
			} finally {
				if (out != null)
					out.close();
			}
			// if first time written
			// append filename to fileswritten
			if (appendflag == false) { 
				String s[] = fileswritten;
				fileswritten = new String[fileswritten.length+1];
				for (int i = 0; i < s.length; ++i)
					fileswritten[i] = s[i];
				fileswritten[fileswritten.length-1] = new String(filename);
			}
		}
		/** writes data output to default files. Uses opts.outputFileNamesPrefix to create filenames. 
		 * Columns 1-2 are iteration number and function evaluation count,  
		 * columns 6- are the data according to the filename. Maximum time spent
		 * for writing can be controlled in the properties file. 
		 * 
		 * <p>The output is written to files that can be printed in Matlab or Scilab (a free
		 * and easy to install Matlab "clone").</p>
		 * <p>
		 * Matlab:
		 * <pre>
      cd 'directory_where_outfiles_and_plotcmaesdat.m_file_are'
      plotcmaesdat;
		 * </pre>
		 * Scilab:
		 * <pre>
      cd 'directory_where_outfiles_and_plotcmaesdat.sci_file_are'
      getf('plotcmaesdat.sci');
      plotcmaesdat;
		 * </pre>
		 * </p>
		 * @see #writeToDefaultFiles(String fileNamePrefix)
		 * @see #writeToDefaultFiles(int)
		 *  */
		public void writeToDefaultFiles() {
			writeToDefaultFiles(options.outputFileNamesPrefix);
		}
		/** writes data output to default files. Maximum time spent
		 * for writing can be controlled in the properties file. For negative values
		 * no writing takes place, overruling the <code>flgForce</code> input parameter below.
		 *  
		 * @param flgForce 0==write depending on time spent with writing, 
		 * 1==write if the iteration count has changed, 
		 * 2==write always, overruled by negative values of maxTimeFractionForWriteToDefaultFiles property
		 *  
		 * @see #writeToDefaultFiles() */
		public void writeToDefaultFiles(int flgForce) {
			if (flgForce > 0 && countiter != citerlastwritten) 
				citerlastwritten = -1; // force writing if something new is there
			if (flgForce >= 2)
				citerlastwritten = -1; // force writing 
			writeToDefaultFiles(options.outputFileNamesPrefix);
		}
		/** 
		 * writes data to files <tt>fileNamePrefix</tt>fit.dat, ...xmean.dat
		 * ...xbest.dat, ...std.dat, ...axlen.dat.
		 * @see #writeToDefaultFiles() 
		 * @param fileNamePrefix prefix String for filenames created to write data */
		public void writeToDefaultFiles(String fileNamePrefix) {

			if (options.maxTimeFractionForWriteToDefaultFiles < 0) // overwrites force flag
				return;
			if (citerlastwritten >= 0) { // negative value forces writing
				if (state < 1)
					return;
				if (countiter == citerlastwritten)
					return; 
				if (options.maxTimeFractionForWriteToDefaultFiles <= 0)
					return;
				if (countiter > 4 && stopConditions.index == 0  // has no effect if stopCondition.test() was not called
						// iteration gap is less than two times of the average gap, to not have large data holes
						// spoils the effect of reducing the timeFraction late in the run
						&& countiter - citerlastwritten - 1 < 2.*(countiter - countwritten + 1.) / (countwritten + 1.)
						// allowed time is exhausted
						&& timings.writedefaultfiles > options.maxTimeFractionForWriteToDefaultFiles 
						* (System.currentTimeMillis() - timings.start)) 
					return;
			}

			long firsttime = System.currentTimeMillis();
			writeToFile(fileNamePrefix + "fit.dat", getDataRowFitness(), 1);
			writeToFile(fileNamePrefix + "xmean.dat", getDataRowXMean(), 1);
			writeToFile(fileNamePrefix + "xrecentbest.dat", getDataRowXRecentBest(), 1);
			writeToFile(fileNamePrefix + "stddev.dat", getDataRowStddev(), 1); // sigma*sqrt(diag(C))
			writeToFile(fileNamePrefix + "axlen.dat", getDataRowAxlen(), 1);
			timings.writedefaultfiles += System.currentTimeMillis() - firsttime;
			//    System.out.println(timings.writedefaultfiles + " " 
			//            + (System.currentTimeMillis()-timings.start)  + " " + opts.maxTimeFractionForWriteToDefaultFiles);
			if (countiter < 3)
				timings.writedefaultfiles = 0;

			++countwritten; 
			citerlastwritten = countiter;
		}
		/** writes header lines to the default files. Could become XML if needed. 
		 * 
		 * @param flgAppend == 0 means overwrite files,  == 1 means append to files
		 */
		public void writeToDefaultFilesHeaders(int flgAppend) {
			writeToDefaultFilesHeaders(options.outputFileNamesPrefix, flgAppend);
		}
		/** 
		 * Writes headers (column annotations) to files <prefix>fit.dat, ...xmean.dat
		 * ...xbest.dat, ...std.dat, ...axlen.dat, and in case the first data
		 * line, usually with the initial values. 
		 * @param fileNamePrefix String for filenames created to write data */
		public void writeToDefaultFilesHeaders(String fileNamePrefix, int flgAppend) {
			if (options.maxTimeFractionForWriteToDefaultFiles < 0) // overwrites force flag
				return;
			String s = "(randomSeed=" + seed + ", " + new Date().toString() + ")\n";
			writeToFile(fileNamePrefix + "fit.dat", 
					"%# iteration evaluations sigma axisratio fitness_of(bestever best median worst) mindii "
							+ "idxmaxSD maxSD idxminSD minSD " 
							+ s, flgAppend);
			writeToFile(fileNamePrefix + "xmean.dat", 
					"%# iteration evaluations sigma void void mean(1...dimension) " + s, flgAppend);
			if (state == 0)
				writeToFile(fileNamePrefix + "xmean.dat", getDataRowXMean(), 1);
			writeToFile(fileNamePrefix + "xrecentbest.dat", 
					"%# iteration evaluations sigma void fitness_of_recent_best x_of_recent_best(1...dimension) " 
							+ s, flgAppend);
			writeToFile(fileNamePrefix + "stddev.dat", 
					"%# iteration evaluations sigma idxmaxSD idxminSD SDs=sigma*sqrt(diag(C)) " 
							+ s, flgAppend);
			if (state == 0)
				writeToFile(fileNamePrefix + "stddev.dat", getDataRowStddev(), 1);
			writeToFile(fileNamePrefix + "axlen.dat", 
					"%# iteration evaluations sigma axisratio stddevratio sort(diag(D)) (square roots of eigenvalues of C) " 
							+ s, flgAppend);
			if (state == 0)
				writeToFile(fileNamePrefix + "axlen.dat", getDataRowAxlen(), 1);
		}

		/** very provisional error handling. Methods of the class
		 * CMAEvolutionStrategy might throw the CMAException, that
		 * need not be catched, because it extends the "unchecked"
		 * RuntimeException class */ 
		public class CMAException extends RuntimeException {
			private static final long serialVersionUID = 1L;

			CMAException(String s) {
				super(s);
			}
		}
	}

	static class IntDouble implements Comparator<IntDouble> {
		int i;    // unique integer value, useful after sorting
		double val; // double value
		public IntDouble(double d, int i) {
			this.val = d;
			this.i = i;
		}
		public IntDouble(double d) {
			this.val = d;
		}
		public IntDouble() {
		}
		public int compare(IntDouble o1, IntDouble o2) {
			if (o1.val < o2.val)
				return -1;
			if (o1.val > o2.val)
				return 1;
			if (o1.i < o2.i)
				return -1;
			if (o1.i > o2.i)
				return 1;
			return 0;
		}

		public boolean equals(IntDouble o1, IntDouble o2) {
			if (o1.compare(o1, o2) == 0) // && o1.hashCode() == o2.hashCode()
				return true;
			return false;
		}
	} // IntDouble



	/*
    Copyright 2003, 2005, 2007 Nikolaus Hansen 
    e-mail: hansen .AT. bionik.tu-berlin.de
            hansen .AT. lri.fr

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License, version 3,
    as published by the Free Software Foundation.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.

  Last change: $Date: 2010-12-02 23:57:21 +0100 (Thu, 02 Dec 2010) $
	 */

	/** Simple container of (mostly generic) options for the
	 * optimization, like the maximum number of objective
	 * function evaluations, see class fields.  No explicit setting of 
	 * options is needed to 
	 * initialize the CMA-ES ({@link CMAEvolutionStrategy#init()}) 
	 * and options of the CMA-ES can be set
	 * and changed any time, either via a property file and the method
	 * {@link CMAEvolutionStrategy#readProperties()}, or new values can simply be 
	 * assigned to the fields of the public <code>opts</code> field of 
	 * the class <code>CMAEvolutionStrategy</code> (yeah, I know, not exactly Java style).
	 * 
	 */
	public static class CMAOptions implements java.io.Serializable {
		// needs to be public to make sure that a using class can excess Options.
		// Therefore, if not nested, needs to move into a separate file

		private static final long serialVersionUID = 2255162105325585121L;

		/** number of initial iterations with diagonal covariance matrix, where
		 * 1 means always. Default is 
		 * diagonalCovarianceMatrix=0, but this will presumably change in future. 
		 * As long as iterations<=diagonalCovarianceMatrix 
		 * the internal time complexity is linear in the search space dimensionality
		 * (memory requirements remain quadratic). 
		 */
		public long diagonalCovarianceMatrix = 0; // -1; 

		/** lower bound for standard deviations (step sizes). The
		 * Array can be of any length. The i-th entry corresponds to
		 * the i-th variable. If length&#60;dim the last entry is recycled for
		 * all remaining variables. Zero entries mean, naturally, no
		 * lower bound. <P>CAVE: there is an interference with stopTolX (and stopTolXFactor):
		 * if lowerStdDev is larger than stopTolX, the termination criterion
		 * can never be satisfied.</P> 
		 * <p>Example:
		 * <pre> CMAEvolutionStrategy es = new CMAEvolutionStrategy(); 
		 * es.options.lowerStandardDeviations = new double[]{1e-4,1e-8}; // 1e-8 for all but first variable
		 * </pre> 
		 * @see #stopTolX
		 * @see #stopTolXFactor
		 * */
		public double[] lowerStandardDeviations;
		/** upper bound for standard deviations (step lengths). 
		 * Zero entries mean no upper
		 * bound. Be aware of the interference with option stopTolUpXFactor. 
		 * @see #lowerStandardDeviations
		 * @see #stopTolUpXFactor
		 * */
		public double[] upperStandardDeviations;

		/** stop if function value drops below the target 
		 * function value stopFitness. Default = <code>Double.MIN_VALUE</code> */ 
		public double stopFitness = Double.MIN_VALUE; 
		/** stop if the 
		 *  maximum function value difference of all iteration-best 
		 * solutions of the last 10 +
		 * 30*N/lambda iterations 
		 * and all solutions of the recent iteration 
		 * become <= stopTolFun. Default = 1e-12. 
		 * */
		public double stopTolFun = 1e-12; 
		/** stop if the maximum function value difference of all iteration-best 
		 * solutions of the last 10 +
		 * 30*N/lambda iterations become smaller than
		 * stopTolFunHist. Default = 1e-13. The measured objective
		 * function value differences do not include repair
		 * penalties. */
		public double stopTolFunHist = 1e-13; // used if non-null
		/** stop if search steps become smaller than stopTolX. Default = 0 */
		public double stopTolX = 0.0; 
		/** stop if search steps become smaller than stopTolXFactor * initial step size. 
		 * Default = 1e-11. */
		public double stopTolXFactor = 1e-11; // used if TolX is null
		/** stop if search steps become larger than stopTolUpXFactor
		 * * initial step size. Default = 1e3. When this termination
		 * criterion applies on a static objective function, the initial 
		 * step-size was chosen far too
		 * small (or divergent behavior is observed). */
		public double stopTolUpXFactor = 1e3; // multiplier for initial sigma
		/** stop if the number of objective function evaluations exceed stopMaxFunEvals */
		public long stopMaxFunEvals = Long.MAX_VALUE; // it is not straight forward to set a dimension dependent
		// default as the user can first set stopMaxFunEvals
		// and afterwards the dimension
		/** stop if the number of iterations (generations) exceed stopMaxIter */
		public long stopMaxIter = Long.MAX_VALUE;
		/** if true stopping message "Manual:..." is generated */
		public boolean stopnow = false; 

		/** flag used by methods iterate(), whether to write output to files. 
		 * Methods write an output file if flgWriteFile&#62;0. 
		 */

		/** determines whether CMA says hello after initialization. 
		 * @see CMAEvolutionStrategy#helloWorld()
		 *  */
		public int verbosity = 1;
		/** Output files written will have the names outputFileNamesPrefix*.dat */
		public String outputFileNamesPrefix = "outcmaes"; 
		/** if chosen > 0 the console output from functions <code>print...</code> is saved 
		 * additionally into a file, by default <tt>outcmaesdisp.dat</tt> */
		public int writeDisplayToFile = 1;

		/** only for >= 1 results are always exactly reproducible, as otherwise the update of the 
		 * eigensystem is conducted depending on time measurements, defaut is 0.2 */
		public double maxTimeFractionForEigendecomposition = 0.2;
		/** default is 0.1 
		 */
		public double maxTimeFractionForWriteToDefaultFiles = 0.1;

		/** checks eigendecomposition mainly for debugging purpose, default is 0==no-check; 
		 * the function checkEigenSystem requires O(N^3) operations. 
		 */
		public int checkEigenSystem = 0;

		/** This is the only place where the reading of a new option needs to be declared 
		 * 
		 * @param properties
		 */
		void setOptions(Properties properties) {
			String s;
			diagonalCovarianceMatrix = getFirstToken(properties.getProperty("diagonalCovarianceMatrix"), diagonalCovarianceMatrix);
			if((s = properties.getProperty("stopFitness")) != null)
				stopFitness = Double.valueOf(getFirstToken(s));
			stopTolFun = getFirstToken(properties.getProperty("stopTolFun"), stopTolFun);
			stopTolFunHist = getFirstToken(properties.getProperty("stopTolFunHist"), stopTolFunHist);
			stopTolX = getFirstToken(properties.getProperty("stopTolX"), stopTolX);
			stopTolXFactor = getFirstToken(properties.getProperty("stopTolXFactor"), stopTolXFactor);
			stopTolUpXFactor = getFirstToken(properties.getProperty("stopTolUpXFactor"), stopTolUpXFactor);
			stopMaxFunEvals = getFirstToken(properties.getProperty("stopMaxFunEvals"), stopMaxFunEvals);
			stopMaxIter = getFirstToken(properties.getProperty("stopMaxIter"), stopMaxIter);
			if ((s = properties.getProperty("upperStandardDeviations")) != null && !s.equals(""))
				upperStandardDeviations = parseDouble(getAllToken(s));
			if ((s = properties.getProperty("lowerStandardDeviations")) != null && !s.equals(""))
				lowerStandardDeviations = parseDouble(getAllToken(s));
			outputFileNamesPrefix = properties.getProperty("outputFileNamesPrefix", outputFileNamesPrefix).split("\\s")[0];
			maxTimeFractionForEigendecomposition = 
					getFirstToken(properties.getProperty("maxTimeFractionForEigendecomposition"), 
							maxTimeFractionForEigendecomposition);
			maxTimeFractionForWriteToDefaultFiles = 
					getFirstToken(properties.getProperty("maxTimeFractionForWriteToDefaultFiles"), 
							maxTimeFractionForWriteToDefaultFiles);
			stopnow = "now".equals(getFirstToken(properties.getProperty("stop")));
			writeDisplayToFile = getFirstToken(properties.getProperty("writeDisplayToFile"), writeDisplayToFile);
			checkEigenSystem = getFirstToken(properties.getProperty("checkEigenSystem"), checkEigenSystem);
		}

		/** Returns the double value of the first token of a string s or the default, 
		 *  if the string is null or empty. This method should become generic with respect to the
		 *  type of second argument.  
		 *  @param s string where the first token is read from
		 *  @param def double default value, in case the string is empty*/
		public Double getFirstToken(String s, Double def) {
			if (s == null)
				return def;
			String[] ar = s.split("\\s+");
			if (ar[0].equals("")) 
				return def;
			return Double.valueOf(ar[0]);
		}

		/** should become generic with type argument?  */
		public String getFirstToken(String s) {
			if (s == null)
				return ""; 
			String[] ar = s.split(new String("\\s+"));
			return ar[0];
		}

		/** Returns the Integer value of the first token of a string s or the default, 
		 *  if the string is null or empty. This method should become generic with respect to the
		 *  type of second argument.  
		 *  @param s string where the first token is read from
		 *  @param def Integer default value, in case the string is empty*/
		public Integer getFirstToken(String s, Integer def) {
			if (s == null)
				return def;
			String[] ar = s.split("\\s+");
			if (ar[0].equals("")) 
				return def;
			return Integer.valueOf(ar[0]);
		}

		//    public <T> T getFirstToken(String s, T def) {
		//        if (s == null)
		//            return def;
		//        String[] ar = s.split("\\s+");
		//        if (ar[0].equals("")) 
		//            return def;
		//        return (T)(ar[0]); /* this fails */
		//    }

		private String removeComments(String s) {
			int i;
			// remove trailing comments
			i = s.indexOf("#");
			if (i >= 0)
				s = s.substring(0,i);
			i = s.indexOf("!");
			if (i >= 0)
				s = s.substring(0,i);
			i = s.indexOf("%");
			if (i >= 0)
				s = s.substring(0,i);
			i = s.indexOf("//");
			if (i >= 0)
				s = s.substring(0,i);
			return s;
		}

		/** Returns def if s==null or empty, code dublicate, should become generic */
		private Long getFirstToken(String s, Long def) {
			if (s == null)
				return def;
			String[] ar = removeComments(s).split("\\s+");
			if (ar[0].equals("")) 
				return def;
			return Long.valueOf(ar[0]);
		}

		String[] getAllToken(String s) {
			// split w.r.t. white spaces regexp \s+
			return removeComments(s).split("\\s+");
		}

		double[] parseDouble(String[] ars) {
			double[] ard = new double[ars.length];
			for(int i = 0; i < ars.length; ++i) {
				ard[i] = Double.parseDouble(ars[i]);
			}
			return ard;
		}
	}
	/*
Copyright 2003, 2005, 2007 Nikolaus Hansen 
e-mail: hansen .AT. bionik.tu-berlin.de
        hansen .AT. lri.fr

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License, version 3,
as published by the Free Software Foundation.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.

Last change: $Date: 2010-12-02 23:57:21 +0100 (Thu, 02 Dec 2010) $
	 */

	/**
	 * Interface to strategy parameters for the CMA Evolution
	 * Strategy, most importantly the population size lambda, while the change
	 * of other parameters is discouraged. 
	 * The class CMAParameters processes the
	 * strategy parameters, like population size and learning rates, for
	 * the class {@link CMAEvolutionStrategy} where the public field <code>parameters</code> of 
	 * type <code>CMAParameters</code> can
	 * be used to set the parameter values. The method {@link #supplementRemainders(int, CMAOptions)}
	 * supplements those parameters that were not explicitly given, 
	 * regarding dependencies
	 * (eg, the parent number, mu, cannot be larger than the
	 * population size lambda) and does a respective consistency checking via method 
	 * {@link #check()}. 
	 * Parameters cannot be changed after CMAEvolutionStrategy method init()
	 * was called. 
	 * <P> Example code snippet:</P>
	 * <PRE>
    CMAEvolutionStrategy cma = new CMAEvolutionStrategy();
    cma.parameters.setPopulationSize(33); // set lambda
    int mu = cma.parameters.getMu(); // will fail as mu was not set and missing 
                                     // parameters were not supplemented yet 
    cma.readProperties();         // read necessary initial values, might overwrite lambda
    mu = cma.parameters.getMu();  // might still fail		
    cma.init();                   // finalize initialization, supplement missing parameters
    mu = cma.parameters.getMu();  // OK now
    cma.parameters.setMu(4);      // runtime error, parameters cannot be changed after init()
	 * </PRE>
	 * 
	 *  <P>Most commonly, the offspring population size lambda can be changed 
	 *  (increased) from its default value via setPopulationSize to improve the 
	 *  global search capability, see file CMAExample2.java. It is recommended to use the default 
	 *  values first! </P>
	 *  
	 *  @see CMAEvolutionStrategy#readProperties() 
	 */
	public static class CMAParameters implements java.io.Serializable { 
		/**
		 * 
		 */
		private static final long serialVersionUID = -1305062342816588003L;
		int supplemented; // after supplementation it is undecidable whether a parameter was 
		// explicitly set from outside, therefore another supplementation is not advisable
		int locked; // lock when lambda is used to new data structures
		int lambda;          /* -> mu, <- N */
		int mu;              /* -> weights, (lambda) */
		double mucov;        /* -> ccov */
		double mueff;        /* <- weights */
		double[] weights;    /* <- mu, -> mueff, mucov, ccov */
		double damps;        /* <- cs, maxeval, lambda */
		double cs;           /* -> damp, <- N */
		double cc;           /* <- N */
		double ccov;         /* <- mucov, <- N, <- diagonalcov */
		double ccovsep;      /* <- ccov */

		double chiN;

		public CMAParameters() { 
			mucov = -1;
			ccov = -1; 
		}

		/**
		 *  Checks strategy parameter setting with respect to principle 
		 *  consistency. Returns a string with description of the first
		 *  error found, otherwise an empty string "".  
		 *  */
		public String check() {
			if (lambda <= 1)
				return "offspring population size lambda must be greater than onem is " + lambda;
			if (mu < 1)
				return "parent number mu must be greater or equal to one, is " + mu;
			if (mu > lambda)
				return "parent number mu " + mu + " must be smaller or equal to offspring population size lambda " + lambda;
			if (weights.length != mu)
				return "number of recombination weights " + weights.length + " disagrees with parent number mu " + mu; 

			if (cs <= 0 || cs > 1)
				return "0 < cs <= 1 must hold for step-size cumulation parameter cs, is " + cs;
			if (damps <= 0)
				return "step-size damping parameter damps must be greater than zero, is " + damps; 
			if (cc <= 0 || cc > 1)
				return "0 < cc <= 1 must hold for cumulation parameter cc, is " + cc;
			if (mucov < 0)
				return "mucov >= 0 must hold, is " + mucov; 
			if (ccov < 0)
				return "learning parameter ccov >= 0 must hold, is " + ccov;
			return "";
		}
		/** get default parameter setting depending on given dimension N
		 * 
		 * @param N dimension
		 * @return default parameter setting
		 * @see #getDefaults(int, int)
		 */
		public CMAParameters getDefaults(int N) {
			if (N == 0)
				error("default parameters needs dimension been set");

			CMAParameters p = new CMAParameters();
			p.supplementRemainders(N, new CMAOptions()); 
			return p; 
		}

		/** get default parameter setting depending on dimension N and 
		 * population size lambda. Code snippet to get, for example, the default parent
		 * number value mu (weighted recombination is default): 
		 * 
		 * <PRE>
		 * int default_mu_for_dimension_42 = new CMAParameters().getDefaults(42).getMu();
		 * 
		 * CMAEvolutionStrategy cma = new CMAEvolutionStrategy(42);
		 * int the_same_most_convenient = cma.getParameterDefaults().getMu(); 
		 * int also_the_same = cma.getParameterDefaults(42).getMu();
		 * </PRE>
		 * 
		 * @param N
		 * @param lambda
		 * @return default parameter setting
		 * @see #getDefaults(int, int)
		 */
		public CMAParameters getDefaults(int N, int lambda) {
			CMAParameters p = new CMAParameters();
			p.setLambda(lambda);
			p.supplementRemainders(N, new CMAOptions()); 
			return p;
		}

		/**
		 * Supplements all default parameter values that were not explicitly set already. 
		 * Also checks whether the values that were already explicitly set are fine. 
		 * @param N search space dimension
		 * @param opts {@link CMAOptions} where stopMaxFunEvals and 
		 * stopMaxIter are used to set step-size damping parameter damps. This is of minor relevance.
		 */
		public void supplementRemainders(int N, CMAOptions opts) {
			// parameters that can be zero were initialized to -1
			if (supplemented > 0)
				error("defaults cannot be supplemented twice");
			if (N == 0)
				error("dimension must be greater than zero");

			supplemented = 1;
			locked = 1;

			chiN = Math.sqrt(N)
					* (1.0 - 1.0 / (4.0 * N) + 1.0 / (21.0 * N * N));

			// set parameters to their default if they were not set before
			if (lambda <= 0)
				lambda = (int) (4.0 + 3.0 * Math.log(N));
			if (mu <= 0)
				mu = (int) Math.floor(lambda/2.);

			if (weights == null)
				setWeights(mu, recombinationType);
			else if (weights.length == 0)
				setWeights(mu, recombinationType);

			if (cs <= 0)
				cs = (mueff+2) / (N+mueff+3);

			if (damps <= 0)
				damps = 
				(1 + 2 * Math.max(0, Math.sqrt((mueff - 1.) / (N + 1.)) - 1))
				* Math.max(0.3, 1 -                         /* modification for short runs */ 
						N / (1e-6+Math.min(opts.stopMaxIter, 
								opts.stopMaxFunEvals/lambda)))	
								+ cs ;                                      /* minor increment */

			if (cc <= 0)
				cc = 4.0 / (N + 4.0);

			if (mucov < 0)
				mucov = mueff;

			if (ccov < 0) { // TODO: setting should depend on gendiagonalcov 
				ccov = 2.0 / (N + 1.41) / (N + 1.41) / mucov
						+ (1 - (1.0 / mucov))
						* Math.min(1, (2 * mueff - 1) / (mueff + (N + 2) * (N + 2)));
				ccovsep = Math.min(1, ccov * (N + 1.5) / 3.0);
			}

			// check everything
			String s = check();
			if (s == null)
				;
			else if (s.equals(""))
				;
			else
				error(s); // if any prior setting does not work

		} // supplementRemainders

		/**
		 * Getter for property mu.
		 * 
		 * @return Value of property mu.
		 * 
		 */
		public int getMu() {
			return mu;
		}

		/**
		 * Setter for parent number mu, be aware of the recombinationType when setting mu 
		 * 
		 * @param mu
		 *            New value for the number of parents mu.
		 * @see #setRecombination(int, CMAParameters.RecombinationType)
		 * @see #setRecombinationWeights(CMAParameters.RecombinationType)
		 */
		public void setMu(int mu) {
			if (locked != 0) // needed because of recombination weights
				error("parameters are locked");
			this.mu = mu;
		}

		/**
		 * Getter for offspring population size lambda, no check, whether lambda was already set properly
		 * 
		 * @return Value of lambda
		 * 
		 */
		public int getLambda() {
			return lambda;
		}

		int flgLambdaChanged = 0; // not in use yet
		/**
		 * Setter for offspring population size alias sample size
		 * alias lambda, use setPopulationSize() for outside use.
		 * 
		 * @param lambda  set population size
		 * @see #setPopulationSize() 
		 */
		void setLambda(int lambda) {
			if (locked != 0)
				error("parameters cannot be set anymore");
			this.lambda = lambda; 
		}
		/** @see #getLambda() */
		public int getPopulationSize() {
			return getLambda();
		}

		/**
		 * Setter for offspring population size (lambda). If (only) lambda is 
		 * set, other parameters, eg. mu and recombination weights and
		 * subsequently learning rates for the covariance matrix etc. are
		 * chosen accordingly  
		 * 
		 * @param lambda is the offspring population size
		 */
		public void setPopulationSize(int lambda) {
			setLambda(lambda);
		}

		public enum RecombinationType {superlinear, linear, equal};
		RecombinationType recombinationType = RecombinationType.superlinear; // otherwise null
		/**
		 * Getter for property weights.
		 * 
		 * @return Value of property weights.
		 * 
		 */
		public double[] getWeights() {
			return this.weights;
		}

		/**
		 * Recombination weights can be equal, linearly 
		 * decreasing, or super-linearly decreasing (default). The respective parameter value is 
		 * in enum RecombinationType. 
		 * @param recombinationType
		 * @see #setRecombination 
		 * @see #setMu
		 */
		public void setRecombinationWeights(RecombinationType recombinationType) {
			if (locked != 0)
				error("parameters cannot be set anymore");
			this.recombinationType = recombinationType;
		}

		/**
		 * Sets parent number mu and the policy for choosing the recombination weights. 
		 * Recombination weights can be equal, linearly 
		 * decreasing, or super-linearly decreasing (default). The respective parameter value is 
		 * The respective parameter value is 
		 * in enum RecombinationType. 
		 * For equal recombination weights mu=lambda/4 is appropriate, otherwise mu=lambda/2. 
		 * @param mu
		 * @param recombinationType
		 */
		public void setRecombination(int mu, RecombinationType recombinationType) {
			if (locked != 0)
				error("parameters are locked");
			this.mu = mu; 
			this.recombinationType = recombinationType;
		}

		/**
		 * Setter for recombination weights
		 *
		 * @param mu is the number of parents, number of weights > 0 
		 */
		private void setWeights(int mu, RecombinationType recombinationType) {
			double[] w = new double[mu];
			if (recombinationType == RecombinationType.equal)
				for (int i = 0; i < mu; ++i) 
					w[i] = 1;
			else if (recombinationType == RecombinationType.linear)
				for (int i = 0; i < mu; ++i) 
					w[i] = mu - i;
			else // default, seems as enums can be null
				for (int i = 0; i < mu; ++i) 	
					w[i] = (Math.log(mu + 1) - Math.log(i + 1));

			setWeights(w);
		}

		/** normalizes recombination weights vector and sets mueff **/
		protected void setWeights(double[] weights) {
			assert locked == 0;
			double sum = 0;
			for (int i = 0; i < weights.length; ++i)
				sum += weights[i];
			for (int i = 0; i < weights.length; ++i)
				weights[i] /= sum;
			this.weights = weights;
			// setMu(weights.length);
			double sum1 = 0;
			double sum2 = 0;
			for (int i = 0; i < mu; ++i) {
				sum1 += weights[i];
				sum2 += weights[i] * weights[i];
			}
			this.mueff = sum1 * sum1 / sum2;
		}

		/**
		 * Getter for property mueff, the "variance effective selection mass".
		 * 
		 * @return Value of property mueff.
		 * 
		 */
		public double getMueff() {
			return mueff;
		}

		/**
		 * Getter for property mucov. mucov determines the
		 * mixing between rank-one and rank-mu update. For
		 * mucov = 1, no rank-mu updated takes place. 
		 * 
		 * @return Value of property mucov.
		 * 
		 */
		public double getMucov() {
			return mucov;
		}

		/**
		 * Setter for mucov.
		 * 
		 * @param mucov
		 *            New value of mucov.
		 * @see #getMucov()  
		 */
		public void setMucov(double mucov) {
			if (locked != 0) // on the save side as mucov -> ccov, but in principle not essential
				error("parameters cannot be set anymore");
			this.mucov = mucov; // can be set anytime
		}

		/**
		 * Getter for property covariance matrix learning rate ccov
		 * 
		 * @param flgdiag 
		 *        boolean, true for getting the learning rate when 
		 *        only the diagonal of the covariance matrix is updated
		 * @return Value of property ccov.
		 * 
		 */
		public double getCcov(boolean flgdiag) {
			if (flgdiag)
				return ccovsep;
			return ccov;
		}
		/**
		 * Getter for property covariance matrix learning rate ccov
		 * 
		 * @return Value of property ccov.
		 * 
		 */
		public double getCcov() {
			return ccov;
		}


		/**
		 * Setter for covariance matrix learning rate ccov. For ccov=0 no covariance
		 * matrix adaptation takes place and only <EM>Cumulation Step-Size 
		 * Adaptation (CSA)</EM> is conducted, also know as <EM>Path Length Control</EM>.
		 * 
		 * @param ccov
		 *            New value of property ccov.
		 * @see #getCcov()
		 */
		public void setCcov(double ccov) {
			this.ccov = ccov; // can be set anytime, cave: switching from diagonal to full cov
		}

		/**
		 * Getter for step-size damping damps.  The damping damps
		 * determines the amount of step size change. 
		 * 
		 * @return Value of damps.
		 * 
		 */
		public double getDamps() {
			return damps;
		}

		/**
		 * Setter for damps.
		 * 
		 * @param damps
		 *            New value of damps.
		 * @see #getDamps()
		 */
		public void setDamps(double damps) {
			if (locked != 0) // not really necessary!?
				error("parameters cannot be set anymore");
			this.damps = damps;
		}

		/**
		 * Getter for backward time horizon parameter cc for
		 * distribution cumulation (for evolution path
		 * p<sub>c</sub>).
		 * 
		 * @return Value of cc.
		 * 
		 */
		public double getCc() {
			return cc;
		}

		/**
		 * Setter for cc to default value.
		 * 
		 */
		public void setCc(double cc) {
			this.cc = cc;
		}

		/**
		 * Getter for cs, parameter for the backward time horizon for the cumulation for sigma.
		 * 
		 * @return Value of property cs.
		 * 
		 */
		public double getCs() {
			return cs;
		}

		/**
		 * Setter for cs to default value.
		 * @see #getCs()
		 */
		public void setCs(double cs) {
			if (locked != 0)
				error("parameters cannot be set anymore");
			this.cs = cs;
		}
		private void error(String s) { // somehow a relict from the C history of this code
			System.out.println(" CMA-ES error: " + s);
			//e.printStackTrace();            // output goes to System.err
			//e.printStackTrace(System.out);  // send trace to stdout
			throw new CMAEvolutionStrategy().new CMAException(" CMA-ES error: " + s); // TODO this looks like a real hack
			//      System.exit(-1); 
		}

	}
	/** solution point in search space. Rather plain implementation of the interface ISolutionPoint. 
	 * 
	 * @see ISolutionPoint
	 * */
	public static class CMASolution implements ISolutionPoint, java.io.Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 6257830429350615236L;

		public CMASolution() {
		}

		public CMASolution(double[] x, double fitnessValue, long evaluation) {
			// super(); // cave: default values for fields overwrite super()
			this.functionValue = fitnessValue;
			this.x = x.clone(); // deep copy, see http://java.sun.com/docs/books/jls/third_edition/html/arrays.html 10.7
			this.evaluation = evaluation;
		}

		/* * as I do not know how to inherit clone in a decent way
		 * and clone even might produce shallow copies
		 */
		public CMASolution deepCopy() {
			return new CMASolution(x, functionValue, evaluation); 
		}

		public CMASolution(double[] x) {
			this.x = x;
		}
		// getter functions
		public double getFitness() { return functionValue; }
		public long getEvaluationNumber() { return evaluation; }
		public double[] getX() { return x.clone(); }

		// setter functions
		public void setFitness(double f) { functionValue = f; }
		public void setEvaluationNumber(long e) { evaluation = e; }
		public void setX(double[] x_in) 
		{ 
			x = new double[x_in.length];
			for (int i = 0; i < x.length; ++i)
				x[i] = x_in[i];
		}

		/** objective function value of x */ 
		private double functionValue = Double.NaN; 

		/** argument to objective function to be optimized */ 
		private double[] x; 

		/** count when the solution was evaluated */
		private long evaluation = 0;
	}

	/** one can access the desired fitness function by giving its number
	 * in the constructor method. Refer to the source code for the
	 * numbers. This class is a stub (and hack) so far.
	 * 
	 */
	public static class FunctionCollector extends AbstractObjectiveFunction {

		public FunctionCollector (double function_number, 
				int flgRotate, 
				double axisratio) {

			actFun = (int) (function_number);
			rotate = flgRotate; 
			scaling = axisratio == 0 ? 1. : axisratio;

			if (actFun > maxFuncNumber)
				actFun = 1; /* sphere */

			// assign all functions by number here
			funs[0]  = new RandFun();
			funs[10]  = new Sphere();

			// convex-quadratic
			funs[30]  = new Cigar(axisratio == 0 ? 1e3 : scaling); 
			funs[40]  = new Tablet(axisratio == 0 ? 1e3 : scaling);
			funs[50]  = new Elli(axisratio == 0 ? 1e3 : scaling);
			funs[60]  = new CigTab(axisratio == 0 ? 1e4 : scaling);
			funs[70]  = new TwoAxes(axisratio == 0 ? 1e3 : scaling);

			// uni-modal, well, essentially 
			funs[80]  = new Rosen();
			funs[90]  = new DiffPow();
			funs[91]  = new ssDiffPow();

			// multi-modal
			funs[150] = new Rastrigin(scaling, 10); 
			funs[160] = new Ackley(scaling);

			//      funs[999]  = new Experimental();
			//      funs[]  = new ();
			//      funs[]  = new ();

		}
		final int maxFuncNumber = 999;
		IObjectiveFunction[] funs = new IObjectiveFunction[maxFuncNumber+1];
		int actFun = 0;
		int rotate = 0;
		double scaling = 1;
		Basis B = new Basis();

		/** implements the fitness function evaluation according to interface {@link IObjectiveFunction}
		 * 
		 */ 
		@Override
		public double valueOf(double[] x) {
			x = x.clone(); // regard input as imutable, not really Java philosophy
			if (rotate > 0)     // rotate
				x = B.Rotate(x);
			if (scaling != 1) { // scale 
				for (int i = 0; i < x.length; ++i)
					x[i] = Math.pow(10, i/(x.length -1.)) * x[i];
			}
			return funs[actFun] == null ? funs[0].valueOf(x) : funs[actFun].valueOf(x);
		}
		public boolean isFeasible(double x[]) { // unfortunate code duplication
			//int i;
			//for (i = 0; i < x.length; ++i)
			//	if (x[i] < 0.01)
			//		return false;
			//return true;
			return funs[actFun].isFeasible(x);
		}
	}

	/** provides rotation of a search point, basis is chosen with constant seed.
	 * 
	 */
	static class RandFun extends AbstractObjectiveFunction {
		java.util.Random rand = new java.util.Random(0);
		@Override
		public double valueOf (double[] x) {
			double res = rand.nextDouble();
			return res;
		}
	}
	static class Sphere extends AbstractObjectiveFunction {
		@Override
		public double valueOf (double[] x) {
			double res = 0;
			for (int i = 0; i < x.length; ++i)
				res += x[i] * x[i];
			return res;
		}
		public boolean isFeasible(double[] x) {
			//int i;
			//for (i = 0; i < x.length; ++i)
			//	if (x[i] < 0.01)
			//		return false;
			return true;
		}
	}

	static class Cigar extends AbstractObjectiveFunction {
		Cigar() {
			this(1e3);
		}
		Cigar(double axisratio) {
			factor = axisratio * axisratio;
		}
		public double factor = 1e6;
		@Override
		public double valueOf (double[] x) {
			double res = x[0] * x[0];
			for (int i = 1; i < x.length; ++i)
				res += factor * x[i] * x[i];
			return res;
		}
	}
	static class Tablet extends AbstractObjectiveFunction {
		Tablet() {
			this(1e3);
		}
		Tablet(double axisratio) {
			factor = axisratio * axisratio;
		}
		public double factor = 1e6;
		@Override
		public double valueOf (double[] x) {
			double res = factor * x[0] * x[0];
			for (int i = 1; i < x.length; ++i)
				res += x[i] * x[i];
			return res;
		}
	}
	static class CigTab extends AbstractObjectiveFunction {
		CigTab() {
			this(1e4);
		}
		CigTab(double axisratio) {
			factor = axisratio;
		}
		public double factor = 1e6;
		@Override
		public double valueOf (double[] x) {
			int end = x.length-1;
			double res = x[0] * x[0] / factor + factor * x[end] * x[end];
			for (int i = 1; i < end; ++i)
				res += x[i] * x[i];
			return res;
		}
	}
	static class TwoAxes extends AbstractObjectiveFunction {
		public double factor = 1e6;
		TwoAxes() {
		}
		TwoAxes(double axisratio) {
			factor = axisratio * axisratio;
		}
		@Override
		public double valueOf (double[] x) {
			double res = 0;
			for (int i = 0; i < x.length; ++i)
				res += (i < x.length/2 ? factor : 1) * x[i] * x[i];
			return res;
		}
	}
	static class ElliRotated extends AbstractObjectiveFunction {
		ElliRotated() {
			this(1e3);
		}
		ElliRotated(double axisratio) {
			factor = axisratio * axisratio;
		}
		public Basis B = new Basis();
		public double factor = 1e6;
		@Override
		public double valueOf (double[] x) {
			x = B.Rotate(x);
			double res = 0;
			for (int i = 0; i < x.length; ++i)
				res += Math.pow(factor,i/(x.length-1.)) * x[i] * x[i]; 
			return res;
		}
	}
	/** dimensionality must be larger than one */
	static class Elli extends AbstractObjectiveFunction {
		Elli() {
			this(1e3);
		}
		Elli(double axisratio) {
			factor = axisratio * axisratio;
		}
		public double factor = 1e6;
		@Override
		public double valueOf (double[] x) {
			double res = 0;
			for (int i = 0; i < x.length; ++i)
				res += Math.pow(factor,i/(x.length-1.)) * x[i] * x[i]; 
			return res;
		}
		//    public boolean isFeasible(double x[]) {
		//    	int i;
		//    	for (i = 0; i < x.length; ++i) {
		//    		if (x[i] < -0.20 || x[i] > 80) 
		//    			return false;
		//    	}
		//    	return true;
		//    }

	}/** dimensionality must be larger than one */

	static class DiffPow extends AbstractObjectiveFunction {
		@Override
		public double valueOf (double[] x) {
			double res = 0;
			for (int i = 0; i < x.length; ++i)
				res += Math.pow(Math.abs(x[i]),2.+10*(double)i/(x.length-1.)); 
			return res;
		}

	}
	
	static class ssDiffPow extends AbstractObjectiveFunction {
		@Override
		public double valueOf (double[] x) {
			return Math.pow(new DiffPow().valueOf(x), 0.25);
		}

	}
	static class Rosen extends AbstractObjectiveFunction {
		@Override
		public double valueOf (double[] x) {
			double res = 0;
			for (int i = 0; i < x.length-1; ++i)
				res += 1e2 * (x[i]*x[i] - x[i+1]) * (x[i]*x[i] - x[i+1]) + 
				(x[i] - 1.) * (x[i] - 1.);
			return res;
		}
	}

	static class Ackley extends AbstractObjectiveFunction {
		double axisratio = 1.;
		Ackley(double axra) {
			axisratio = axra;
		}
		public Ackley() {
		}
		@Override
		public double valueOf (double[] x) {
			double res = 0;
			double res2 = 0;
			double fac = 0;
			for (int i = 0; i < x.length; ++i) {
				fac = Math.pow(axisratio, (i-1.)/(x.length-1.));
				res += fac * fac * x[i]*x[i];
				res2 += Math.cos(2. * Math.PI * fac * x[i]);
			}
			return (20. - 20. * Math.exp(-0.2 * Math.sqrt(res/x.length)) 
					+ Math.exp(1.) - Math.exp(res2/x.length));
		}
	}
	static class Rastrigin extends AbstractObjectiveFunction {
		Rastrigin() {
			this(1, 10);
		}
		Rastrigin(double axisratio, double amplitude) {
			this.axisratio = axisratio;
			this.amplitude = amplitude;
		}
		public double axisratio = 1;
		public double amplitude = 10;
		@Override
		public double valueOf (double[] x) {
			double fac;
			double res = 0;
			for (int i = 0; i < x.length; ++i) {
				fac = Math.pow(axisratio,(i-1.)/(x.length-1.));
				if (i == 0 && x[i] < 0)
					fac *= 1.;
				res +=  fac * fac * x[i] * x[i]
						+ amplitude * (1. - Math.cos(2.*Math.PI * fac * x[i])); 
			}
			return res;
		}
	}
	/* Template fitness function 
class fff extends AbstractObjectiveFunction {
    public double valueOf(double[] x) {
        double res = 0;
        for (int i = 0; i < x.length; ++i) {
        }
        return res;
    }
}
	 */

	static class Basis {
		double [][] B; // usually field names should be lower case
		Random rand = new Random(2); // use not always the same basis

		double[] Rotate(double[] x) {
			GenBasis(x.length);
			double[] y = new double[x.length];
			for (int i = 0; i < x.length; ++i) {
				y[i] = 0;
				for (int j = 0; j < x.length; ++j)
					y[i] += B[i][j] * x[j]; 
			}
			return y;
		}
		double[][] Rotate(double[][] pop) {
			double[][] y = new double[pop.length][];
			for (int i = 0; i < pop.length; ++i) {
				y[i] = Rotate(pop[i]);
			}
			return y;
		}

		void GenBasis(int DIM)  
		{
			if (B != null ? B.length == DIM : false)
				return;

			double sp;
			int i,j,k;

			/* generate orthogonal basis */
			B = new double[DIM][DIM];
			for (i = 0; i < DIM; ++i) {
				/* sample components gaussian */
				for (j = 0; j < DIM; ++j) 
					B[i][j] = rand.nextGaussian();
				/* substract projection of previous vectors */
				for (j = i-1; j >= 0; --j) {
					for (sp = 0., k = 0; k < DIM; ++k)
						sp += B[i][k]*B[j][k]; /* scalar product */
					for (k = 0; k < DIM; ++k)
						B[i][k] -= sp * B[j][k]; /* substract */
				}
				/* normalize */
				for (sp = 0., k = 0; k < DIM; ++k)
					sp += B[i][k]*B[i][k]; /* squared norm */
				for (k = 0; k < DIM; ++k)
					B[i][k] /= Math.sqrt(sp); 
			}
		}
	}

	/** Minimalistic interface of a single-objective function (fitness function) to be minimized. 
	 */
	public static interface IObjectiveFunction {
		/** @param x  a point (candidate solution) in the pre-image of the objective function 
        @return  objective function value of the input search point  
		 */
		double valueOf(double x[]);
		boolean isFeasible(double x[]);
	}

	/** Interface to a single-objective function to be minimized, 
	 * that accepts an array
	 * of points double[][], a population to be evaluated within one call to method valuesOf().
	 */
	public static interface IObjectiveFunctionParallel {

		/** 
		 * @param pop is an array of search points to be evaluated, where
		 * pop[i] is the i-th point. 
		 * @return array of objective function values. The i-th value 
		 * is the objective function value of pop[i].
		 * */ 
		double[] valuesOf(double pop[][]);
	}

	/** solution point in search space, single-objective case
	 *  
	 * */
	public static interface ISolutionPoint {
		/** objective function value (fitness) of the search point x */ 
		public double getFitness();  
		/** count at what evaluation number the search point x was evaluated */
		public long getEvaluationNumber();
		/** value of the point in search space, that is in the 
		 * preimage of the objective function to be optimized */ 
		public double[] getX();

		/** objective function value (fitness) of the search point x */ 
		public void setFitness( double fitness); // TODO better FunctionValue than Fitness ? 
		/** count at what evaluation number the search point x was evaluated */
		public void setEvaluationNumber( long evaluation);
		/** value of the solution point in search space, the 
		 * preimage of the objective function to be optimized */ 
		public void setX(double[] x);
	}


	/**
	 * PrintfFormat allows the formatting of an array of
	 * objects embedded within a string.  Primitive types
	 * must be passed using wrapper types.  The formatting
	 * is controlled by a control string.
	 *<p>
	 * A control string is a Java string that contains a
	 * control specification.  The control specification
	 * starts at the first percent sign (%) in the string,
	 * provided that this percent sign
	 *<ol>
	 *<li>is not escaped protected by a matching % or is
	 * not an escape % character,
	 *<li>is not at the end of the format string, and
	 *<li>precedes a sequence of characters that parses as
	 * a valid control specification.
	 *</ol>
	 *</p><p>
	 * A control specification usually takes the form:
	 *<pre> % ['-+ #0]* [0..9]* { . [0..9]* }+
	 *                { [hlL] }+ [idfgGoxXeEcs]
	 *</pre>
	 * There are variants of this basic form that are
	 * discussed below.</p>
	 *<p>
	 * The format is composed of zero or more directives
	 * defined as follows:
	 *<ul>
	 *<li>ordinary characters, which are simply copied to
	 * the output stream;
	 *<li>escape sequences, which represent non-graphic
	 * characters; and
	 *<li>conversion specifications,  each of which
	 * results in the fetching of zero or more arguments.
	 *</ul></p>
	 *<p>
	 * The results are undefined if there are insufficient
	 * arguments for the format.  Usually an unchecked
	 * exception will be thrown.  If the format is
	 * exhausted while arguments remain, the excess
	 * arguments are evaluated but are otherwise ignored.
	 * In format strings containing the % form of
	 * conversion specifications, each argument in the
	 * argument list is used exactly once.</p>
	 * <p>
	 * Conversions can be applied to the <code>n</code>th
	 * argument after the format in the argument list,
	 * rather than to the next unused argument.  In this
	 * case, the conversion characer % is replaced by the
	 * sequence %<code>n</code>$, where <code>n</code> is
	 * a decimal integer giving the position of the
	 * argument in the argument list.</p>
	 * <p>
	 * In format strings containing the %<code>n</code>$
	 * form of conversion specifications, each argument
	 * in the argument list is used exactly once.</p>
	 *
	 *<h4>Escape Sequences</h4>
	 *<p>
	 * The following table lists escape sequences and
	 * associated actions on display devices capable of
	 * the action.
	 *<table>
	 *<tr><th align=left>Sequence</th>
	 *    <th align=left>Name</th>
	 *    <th align=left>Description</th></tr>
	 *<tr><td>\\</td><td>backlash</td><td>None.
	 *</td></tr>
	 *<tr><td>\a</td><td>alert</td><td>Attempts to alert
	 *          the user through audible or visible
	 *          notification.
	 *</td></tr>
	 *<tr><td>\b</td><td>backspace</td><td>Moves the
	 *          printing position to one column before
	 *          the current position, unless the
	 *          current position is the start of a line.
	 *</td></tr>
	 *<tr><td>\f</td><td>form-feed</td><td>Moves the
	 *          printing position to the initial 
	 *          printing position of the next logical
	 *          page.
	 *</td></tr>
	 *<tr><td>\n</td><td>newline</td><td>Moves the
	 *          printing position to the start of the
	 *          next line.
	 *</td></tr>
	 *<tr><td>\r</td><td>carriage-return</td><td>Moves
	 *          the printing position to the start of
	 *          the current line.
	 *</td></tr>
	 *<tr><td>\t</td><td>tab</td><td>Moves the printing
	 *          position to the next implementation-
	 *          defined horizontal tab position.
	 *</td></tr>
	 *<tr><td>\v</td><td>vertical-tab</td><td>Moves the
	 *          printing position to the start of the
	 *          next implementation-defined vertical
	 *          tab position.
	 *</td></tr>
	 *</table></p>
	 *<h4>Conversion Specifications</h4>
	 *<p>
	 * Each conversion specification is introduced by
	 * the percent sign character (%).  After the character
	 * %, the following appear in sequence:</p>
	 *<p>
	 * Zero or more flags (in any order), which modify the
	 * meaning of the conversion specification.</p>
	 *<p>
	 * An optional minimum field width.  If the converted
	 * value has fewer characters than the field width, it
	 * will be padded with spaces by default on the left;
	 * t will be padded on the right, if the left-
	 * adjustment flag (-), described below, is given to
	 * the field width.  The field width takes the form
	 * of a decimal integer.  If the conversion character
	 * is s, the field width is the the minimum number of
	 * characters to be printed.</p>
	 *<p>
	 * An optional precision that gives the minumum number
	 * of digits to appear for the d, i, o, x or X
	 * conversions (the field is padded with leading
	 * zeros); the number of digits to appear after the
	 * radix character for the e, E, and f conversions,
	 * the maximum number of significant digits for the g
	 * and G conversions; or the maximum number of
	 * characters to be written from a string is s and S
	 * conversions.  The precision takes the form of an
	 * optional decimal digit string, where a null digit
	 * string is treated as 0.  If a precision appears
	 * with a c conversion character the precision is
	 * ignored.
	 * </p>
	 *<p>
	 * An optional h specifies that a following d, i, o,
	 * x, or X conversion character applies to a type 
	 * short argument (the argument will be promoted
	 * according to the integral promotions and its value
	 * converted to type short before printing).</p>
	 *<p>
	 * An optional l (ell) specifies that a following
	 * d, i, o, x, or X conversion character applies to a
	 * type long argument.</p>
	 *<p>
	 * A field width or precision may be indicated by an
	 * asterisk (*) instead of a digit string.  In this
	 * case, an integer argument supplised the field width
	 * precision.  The argument that is actually converted
	 * is not fetched until the conversion letter is seen,
	 * so the the arguments specifying field width or
	 * precision must appear before the argument (if any)
	 * to be converted.  If the precision argument is
	 * negative, it will be changed to zero.  A negative
	 * field width argument is taken as a - flag, followed
	 * by a positive field width.</p>
	 * <p>
	 * In format strings containing the %<code>n</code>$
	 * form of a conversion specification, a field width
	 * or precision may be indicated by the sequence
	 * *<code>m</code>$, where m is a decimal integer
	 * giving the position in the argument list (after the
	 * format argument) of an integer argument containing
	 * the field width or precision.</p>
	 * <p>
	 * The format can contain either numbered argument
	 * specifications (that is, %<code>n</code>$ and
	 * *<code>m</code>$), or unnumbered argument
	 * specifications (that is % and *), but normally not
	 * both.  The only exception to this is that %% can
	 * be mixed with the %<code>n</code>$ form.  The
	 * results of mixing numbered and unnumbered argument
	 * specifications in a format string are undefined.</p>
	 *
	 *<h4>Flag Characters</h4>
	 *<p>
	 * The flags and their meanings are:</p>
	 *<dl>
	 * <dt>'<dd> integer portion of the result of a
	 *      decimal conversion (%i, %d, %f, %g, or %G) will
	 *      be formatted with thousands' grouping
	 *      characters.  For other conversions the flag
	 *      is ignored.  The non-monetary grouping
	 *      character is used.
	 * <dt>-<dd> result of the conversion is left-justified
	 *      within the field.  (It will be right-justified
	 *      if this flag is not specified).</td></tr>
	 * <dt>+<dd> result of a signed conversion always
	 *      begins with a sign (+ or -).  (It will begin
	 *      with a sign only when a negative value is
	 *      converted if this flag is not specified.)
	 * <dt>&lt;space&gt;<dd> If the first character of a
	 *      signed conversion is not a sign, a space
	 *      character will be placed before the result.
	 *      This means that if the space character and +
	 *      flags both appear, the space flag will be
	 *      ignored.
	 * <dt>#<dd> value is to be converted to an alternative
	 *      form.  For c, d, i, and s conversions, the flag
	 *      has no effect.  For o conversion, it increases
	 *      the precision to force the first digit of the
	 *      result to be a zero.  For x or X conversion, a
	 *      non-zero result has 0x or 0X prefixed to it,
	 *      respectively.  For e, E, f, g, and G
	 *      conversions, the result always contains a radix
	 *      character, even if no digits follow the radix
	 *      character (normally, a decimal point appears in
	 *      the result of these conversions only if a digit
	 *      follows it).  For g and G conversions, trailing
	 *      zeros will not be removed from the result as
	 *      they normally are.
	 * <dt>0<dd> d, i, o, x, X, e, E, f, g, and G
	 *      conversions, leading zeros (following any
	 *      indication of sign or base) are used to pad to
	 *      the field width;  no space padding is
	 *      performed.  If the 0 and - flags both appear,
	 *      the 0 flag is ignored.  For d, i, o, x, and X
	 *      conversions, if a precision is specified, the
	 *      0 flag will be ignored. For c conversions,
	 *      the flag is ignored.
	 *</dl>
	 *
	 *<h4>Conversion Characters</h4>
	 *<p>
	 * Each conversion character results in fetching zero
	 * or more arguments.  The results are undefined if
	 * there are insufficient arguments for the format.
	 * Usually, an unchecked exception will be thrown.
	 * If the format is exhausted while arguments remain,
	 * the excess arguments are ignored.</p>
	 *
	 *<p>
	 * The conversion characters and their meanings are:
	 *</p>
	 *<dl>
	 * <dt>d,i<dd>The int argument is converted to a
	 *        signed decimal in the style [-]dddd.  The
	 *        precision specifies the minimum number of
	 *        digits to appear;  if the value being
	 *        converted can be represented in fewer
	 *        digits, it will be expanded with leading
	 *        zeros.  The default precision is 1.  The
	 *        result of converting 0 with an explicit
	 *        precision of 0 is no characters.
	 * <dt>o<dd> The int argument is converted to unsigned
	 *        octal format in the style ddddd.  The
	 *        precision specifies the minimum number of
	 *        digits to appear;  if the value being
	 *        converted can be represented in fewer
	 *        digits, it will be expanded with leading
	 *        zeros.  The default precision is 1.  The
	 *        result of converting 0 with an explicit
	 *        precision of 0 is no characters.
	 * <dt>x<dd> The int argument is converted to unsigned
	 *        hexadecimal format in the style dddd;  the
	 *        letters abcdef are used.  The precision
	 *        specifies the minimum numberof digits to
	 *        appear; if the value being converted can be
	 *        represented in fewer digits, it will be
	 *        expanded with leading zeros.  The default
	 *        precision is 1.  The result of converting 0
	 *        with an explicit precision of 0 is no
	 *        characters.
	 * <dt>X<dd> Behaves the same as the x conversion
	 *        character except that letters ABCDEF are
	 *        used instead of abcdef.
	 * <dt>f<dd> The floating point number argument is
	 *        written in decimal notation in the style
	 *        [-]ddd.ddd, where the number of digits after
	 *        the radix character (shown here as a decimal
	 *        point) is equal to the precision
	 *        specification.  A Locale is used to determine
	 *        the radix character to use in this format.
	 *        If the precision is omitted from the
	 *        argument, six digits are written after the
	 *        radix character;  if the precision is
	 *        explicitly 0 and the # flag is not specified,
	 *        no radix character appears.  If a radix
	 *        character appears, at least 1 digit appears
	 *        before it.  The value is rounded to the
	 *        appropriate number of digits.
	 * <dt>e,E<dd>The floating point number argument is
	 *        written in the style [-]d.ddde{+-}dd
	 *        (the symbols {+-} indicate either a plus or
	 *        minus sign), where there is one digit before
	 *        the radix character (shown here as a decimal
	 *        point) and the number of digits after it is
	 *        equal to the precision.  A Locale is used to
	 *        determine the radix character to use in this
	 *        format.  When the precision is missing, six
	 *        digits are written after the radix character;
	 *        if the precision is 0 and the # flag is not
	 *        specified, no radix character appears.  The
	 *        E conversion will produce a number with E
	 *        instead of e introducing the exponent.  The
	 *        exponent always contains at least two digits.
	 *        However, if the value to be written requires
	 *        an exponent greater than two digits,
	 *        additional exponent digits are written as
	 *        necessary.  The value is rounded to the
	 *        appropriate number of digits.
	 * <dt>g,G<dd>The floating point number argument is
	 *        written in style f or e (or in sytle E in the
	 *        case of a G conversion character), with the
	 *        precision specifying the number of
	 *        significant digits.  If the precision is
	 *        zero, it is taken as one.  The style used
	 *        depends on the value converted:  style e
	 *        (or E) will be used only if the exponent
	 *        resulting from the conversion is less than
	 *        -4 or greater than or equal to the precision.
	 *        Trailing zeros are removed from the result.
	 *        A radix character appears only if it is
	 *        followed by a digit.
	 * <dt>c,C<dd>The integer argument is converted to a
	 *        char and the result is written.
	 *
	 * <dt>s,S<dd>The argument is taken to be a string and
	 *        bytes from the string are written until the
	 *        end of the string or the number of bytes 
	 *        indicated by the precision specification of
	 *        the argument is reached.  If the precision
	 *        is omitted from the argument, it is taken to
	 *        be infinite, so all characters up to the end
	 *        of the string are written.
	 * <dt>%<dd>Write a % character;  no argument is
	 *        converted.
	 *</dl>
	 *<p>
	 * If a conversion specification does not match one of
	 * the above forms, an IllegalArgumentException is
	 * thrown and the instance of PrintfFormat is not
	 * created.</p>
	 *<p>
	 * If a floating point value is the internal
	 * representation for infinity, the output is
	 * [+]Infinity, where Infinity is either Infinity or
	 * Inf, depending on the desired output string length.
	 * Printing of the sign follows the rules described
	 * above.</p>
	 *<p>
	 * If a floating point value is the internal
	 * representation for "not-a-number," the output is
	 * [+]NaN.  Printing of the sign follows the rules
	 * described above.</p>
	 *<p>
	 * In no case does a non-existent or small field width
	 * cause truncation of a field;  if the result of a
	 * conversion is wider than the field width, the field
	 * is simply expanded to contain the conversion result.
	 *</p>
	 *<p>
	 * The behavior is like printf.  One exception is that
	 * the minimum number of exponent digits is 3 instead
	 * of 2 for e and E formats when the optional L is used
	 * before the e, E, g, or G conversion character.  The
	 * optional L does not imply conversion to a long long
	 * double. </p>
	 * <p>
	 * The biggest divergence from the C printf
	 * specification is in the use of 16 bit characters.
	 * This allows the handling of characters beyond the
	 * small ASCII character set and allows the utility to
	 * interoperate correctly with the rest of the Java
	 * runtime environment.</p>
	 *<p>
	 * Omissions from the C printf specification are
	 * numerous.  All the known omissions are present
	 * because Java never uses bytes to represent
	 * characters and does not have pointers:</p>
	 *<ul>
	 * <li>%c is the same as %C.
	 * <li>%s is the same as %S.
	 * <li>u, p, and n conversion characters. 
	 * <li>%ws format.
	 * <li>h modifier applied to an n conversion character.
	 * <li>l (ell) modifier applied to the c, n, or s
	 * conversion characters.
	 * <li>ll (ell ell) modifier to d, i, o, u, x, or X
	 * conversion characters.
	 * <li>ll (ell ell) modifier to an n conversion
	 * character.
	 * <li>c, C, d,i,o,u,x, and X conversion characters
	 * apply to Byte, Character, Short, Integer, Long
	 * types.
	 * <li>f, e, E, g, and G conversion characters apply
	 * to Float and Double types.
	 * <li>s and S conversion characters apply to String
	 * types.
	 * <li>All other reference types can be formatted
	 * using the s or S conversion characters only.
	 *</ul>
	 * <p>
	 * Most of this specification is quoted from the Unix
	 * man page for the sprintf utility.</p>
	 *
	 * @author Allan Jacobs
	 * @version 1
	 * Release 1: Initial release.
	 * Release 2: Asterisk field widths and precisions    
	 *            %n$ and *m$
	 *            Bug fixes
	 *              g format fix (2 digits in e form corrupt)
	 *              rounding in f format implemented
	 *              round up when digit not printed is 5
	 *              formatting of -0.0f
	 *              round up/down when last digits are 50000...
	 */
	public static class PrintfFormat {
		/**
		 * Constructs an array of control specifications
		 * possibly preceded, separated, or followed by
		 * ordinary strings.  Control strings begin with
		 * unpaired percent signs.  A pair of successive
		 * percent signs designates a single percent sign in
		 * the format.
		 * @param fmtArg  Control string.
		 * @exception IllegalArgumentException if the control
		 * string is null, zero length, or otherwise
		 * malformed.
		 */
		public PrintfFormat(String fmtArg)
				throws IllegalArgumentException {
			this(Locale.getDefault(),fmtArg);
		}
		/**
		 * Constructs an array of control specifications
		 * possibly preceded, separated, or followed by
		 * ordinary strings.  Control strings begin with
		 * unpaired percent signs.  A pair of successive
		 * percent signs designates a single percent sign in
		 * the format.
		 * @param fmtArg  Control string.
		 * @exception IllegalArgumentException if the control
		 * string is null, zero length, or otherwise
		 * malformed.
		 */
		public PrintfFormat(Locale locale,String fmtArg)
				throws IllegalArgumentException {
			dfs = new DecimalFormatSymbols(locale);
			int ePos=0;
			ConversionSpecification sFmt=null;
			String unCS = this.nonControl(fmtArg,0);
			if (unCS!=null) {
				sFmt = new ConversionSpecification();
				sFmt.setLiteral(unCS);
				vFmt.addElement(sFmt);
			}
			while(cPos!=-1 && cPos<fmtArg.length()) {
				for (ePos=cPos+1; ePos<fmtArg.length();
						ePos++) {
					char c=0;
					c = fmtArg.charAt(ePos);
					if (c == 'i') break;
					if (c == 'd') break;
					if (c == 'f') break;
					if (c == 'g') break;
					if (c == 'G') break;
					if (c == 'o') break;
					if (c == 'x') break;
					if (c == 'X') break;
					if (c == 'e') break;
					if (c == 'E') break;
					if (c == 'c') break;
					if (c == 's') break;
					if (c == '%') break;
				}
				ePos=Math.min(ePos+1,fmtArg.length());
				sFmt = new ConversionSpecification(
						fmtArg.substring(cPos,ePos));
				vFmt.addElement(sFmt);
				unCS = this.nonControl(fmtArg,ePos);
				if (unCS!=null) {
					sFmt = new ConversionSpecification();
					sFmt.setLiteral(unCS);
					vFmt.addElement(sFmt);
				}
			}
		}
		/**
		 * Return a substring starting at
		 * <code>start</code> and ending at either the end
		 * of the String <code>s</code>, the next unpaired
		 * percent sign, or at the end of the String if the
		 * last character is a percent sign.
		 * @param s  Control string.
		 * @param start Position in the string
		 *     <code>s</code> to begin looking for the start
		 *     of a control string.
		 * @return the substring from the start position
		 *     to the beginning of the control string.
		 */
		private String nonControl(String s,int start) {
			String ret="";
			cPos=s.indexOf("%",start);
			if (cPos==-1) cPos=s.length();
			return s.substring(start,cPos);
		}
		/**
		 * Format an array of objects.  Byte, Short,
		 * Integer, Long, Float, Double, and Character
		 * arguments are treated as wrappers for primitive
		 * types.
		 * @param o The array of objects to format.
		 * @return  The formatted String.
		 */
		public String sprintf(Object[] o) {
			Enumeration e = vFmt.elements();
			ConversionSpecification cs = null;
			char c = 0;
			int i=0;
			StringBuffer sb=new StringBuffer();
			while (e.hasMoreElements()) {
				cs = (ConversionSpecification)
						e.nextElement();
				c = cs.getConversionCharacter();
				if (c=='\0') sb.append(cs.getLiteral());
				else if (c=='%') sb.append("%");
				else {
					if (cs.isPositionalSpecification()) {
						i=cs.getArgumentPosition()-1;
						if (cs.isPositionalFieldWidth()) {
							int ifw=cs.getArgumentPositionForFieldWidth()-1;
							cs.setFieldWidthWithArg(((Integer)o[ifw]).intValue());
						}
						if (cs.isPositionalPrecision()) {
							int ipr=cs.getArgumentPositionForPrecision()-1;
							cs.setPrecisionWithArg(((Integer)o[ipr]).intValue());
						}
					}
					else {
						if (cs.isVariableFieldWidth()) {
							cs.setFieldWidthWithArg(((Integer)o[i]).intValue());
							i++;
						}
						if (cs.isVariablePrecision()) {
							cs.setPrecisionWithArg(((Integer)o[i]).intValue());
							i++;
						}
					}
					if (o[i] instanceof Byte)
						sb.append(cs.internalsprintf(
								((Byte)o[i]).byteValue()));
					else if (o[i] instanceof Short)
						sb.append(cs.internalsprintf(
								((Short)o[i]).shortValue()));
					else if (o[i] instanceof Integer)
						sb.append(cs.internalsprintf(
								((Integer)o[i]).intValue()));
					else if (o[i] instanceof Long)
						sb.append(cs.internalsprintf(
								((Long)o[i]).longValue()));
					else if (o[i] instanceof Float)
						sb.append(cs.internalsprintf(
								((Float)o[i]).floatValue()));
					else if (o[i] instanceof Double)
						sb.append(cs.internalsprintf(
								((Double)o[i]).doubleValue()));
					else if (o[i] instanceof Character)
						sb.append(cs.internalsprintf(
								((Character)o[i]).charValue()));
					else if (o[i] instanceof String)
						sb.append(cs.internalsprintf(
								(String)o[i]));
					else
						sb.append(cs.internalsprintf(
								o[i]));
					if (!cs.isPositionalSpecification())
						i++;
				}
			}
			return sb.toString();
		}
		/**
		 * Format nothing.  Just use the control string.
		 * @return  the formatted String.
		 */
		public String sprintf() {
			Enumeration e = vFmt.elements();
			ConversionSpecification cs = null;
			char c = 0;
			StringBuffer sb=new StringBuffer();
			while (e.hasMoreElements()) {
				cs = (ConversionSpecification)
						e.nextElement();
				c = cs.getConversionCharacter();
				if (c=='\0') sb.append(cs.getLiteral());
				else if (c=='%') sb.append("%");
			}
			return sb.toString();
		}
		/**
		 * Format an int.
		 * @param x The int to format.
		 * @return  The formatted String.
		 * @exception IllegalArgumentException if the
		 *     conversion character is f, e, E, g, G, s,
		 *     or S.
		 */
		public String sprintf(int x)
				throws IllegalArgumentException {
			Enumeration e = vFmt.elements();
			ConversionSpecification cs = null;
			char c = 0;
			StringBuffer sb=new StringBuffer();
			while (e.hasMoreElements()) {
				cs = (ConversionSpecification)
						e.nextElement();
				c = cs.getConversionCharacter();
				if (c=='\0') sb.append(cs.getLiteral());
				else if (c=='%') sb.append("%");
				else sb.append(cs.internalsprintf(x));
			}
			return sb.toString();
		}
		/**
		 * Format an long.
		 * @param x The long to format.
		 * @return  The formatted String.
		 * @exception IllegalArgumentException if the
		 *     conversion character is f, e, E, g, G, s,
		 *     or S.
		 */
		public String sprintf(long x)
				throws IllegalArgumentException {
			Enumeration e = vFmt.elements();
			ConversionSpecification cs = null;
			char c = 0;
			StringBuffer sb=new StringBuffer();
			while (e.hasMoreElements()) {
				cs = (ConversionSpecification)
						e.nextElement();
				c = cs.getConversionCharacter();
				if (c=='\0') sb.append(cs.getLiteral());
				else if (c=='%') sb.append("%");
				else sb.append(cs.internalsprintf(x));
			}
			return sb.toString();
		}
		/**
		 * Format a double.
		 * @param x The double to format.
		 * @return  The formatted String.
		 * @exception IllegalArgumentException if the
		 *     conversion character is c, C, s, S,
		 *     d, d, x, X, or o.
		 */
		public String sprintf(double x)
				throws IllegalArgumentException {
			Enumeration e = vFmt.elements();
			ConversionSpecification cs = null;
			char c = 0;
			StringBuffer sb=new StringBuffer();
			while (e.hasMoreElements()) {
				cs = (ConversionSpecification)
						e.nextElement();
				c = cs.getConversionCharacter();
				if (c=='\0') sb.append(cs.getLiteral());
				else if (c=='%') sb.append("%");
				else sb.append(cs.internalsprintf(x));
			}
			return sb.toString();
		}
		/**
		 * Format a String.
		 * @param x The String to format.
		 * @return  The formatted String.
		 * @exception IllegalArgumentException if the
		 *   conversion character is neither s nor S.
		 */
		public String sprintf(String x)
				throws IllegalArgumentException {
			Enumeration e = vFmt.elements();
			ConversionSpecification cs = null;
			char c = 0;
			StringBuffer sb=new StringBuffer();
			while (e.hasMoreElements()) {
				cs = (ConversionSpecification)
						e.nextElement();
				c = cs.getConversionCharacter();
				if (c=='\0') sb.append(cs.getLiteral());
				else if (c=='%') sb.append("%");
				else sb.append(cs.internalsprintf(x));
			}
			return sb.toString();
		}
		/**
		 * Format an Object.  Convert wrapper types to
		 * their primitive equivalents and call the
		 * appropriate internal formatting method. Convert
		 * Strings using an internal formatting method for
		 * Strings. Otherwise use the default formatter
		 * (use toString).
		 * @param x the Object to format.
		 * @return  the formatted String.
		 * @exception IllegalArgumentException if the
		 *    conversion character is inappropriate for
		 *    formatting an unwrapped value.
		 */
		public String sprintf(Object x)
				throws IllegalArgumentException {
			Enumeration e = vFmt.elements();
			ConversionSpecification cs = null;
			char c = 0;
			StringBuffer sb=new StringBuffer();
			while (e.hasMoreElements()) {
				cs = (ConversionSpecification)
						e.nextElement();
				c = cs.getConversionCharacter();
				if (c=='\0') sb.append(cs.getLiteral());
				else if (c=='%') sb.append("%");
				else {
					if (x instanceof Byte)
						sb.append(cs.internalsprintf(
								((Byte)x).byteValue()));
					else if (x instanceof Short)
						sb.append(cs.internalsprintf(
								((Short)x).shortValue()));
					else if (x instanceof Integer)
						sb.append(cs.internalsprintf(
								((Integer)x).intValue()));
					else if (x instanceof Long)
						sb.append(cs.internalsprintf(
								((Long)x).longValue()));
					else if (x instanceof Float)
						sb.append(cs.internalsprintf(
								((Float)x).floatValue()));
					else if (x instanceof Double)
						sb.append(cs.internalsprintf(
								((Double)x).doubleValue()));
					else if (x instanceof Character)
						sb.append(cs.internalsprintf(
								((Character)x).charValue()));
					else if (x instanceof String)
						sb.append(cs.internalsprintf(
								(String)x));
					else
						sb.append(cs.internalsprintf(x));
				}
			}
			return sb.toString();
		}
		/**
		 *<p>
		 * ConversionSpecification allows the formatting of
		 * a single primitive or object embedded within a
		 * string.  The formatting is controlled by a
		 * format string.  Only one Java primitive or
		 * object can be formatted at a time.
		 *<p>
		 * A format string is a Java string that contains
		 * a control string.  The control string starts at
		 * the first percent sign (%) in the string,
		 * provided that this percent sign
		 *<ol>
		 *<li>is not escaped protected by a matching % or
		 *     is not an escape % character,
		 *<li>is not at the end of the format string, and
		 *<li>precedes a sequence of characters that parses
		 *     as a valid control string.
		 *</ol>
		 *<p>
		 * A control string takes the form:
		 *<pre> % ['-+ #0]* [0..9]* { . [0..9]* }+
		 *                { [hlL] }+ [idfgGoxXeEcs]
		 *</pre>
		 *<p>
		 * The behavior is like printf.  One (hopefully the
		 * only) exception is that the minimum number of
		 * exponent digits is 3 instead of 2 for e and E
		 * formats when the optional L is used before the
		 * e, E, g, or G conversion character.  The 
		 * optional L does not imply conversion to a long
		 * long double.
		 */
		private class ConversionSpecification {
			/**
			 * Constructor.  Used to prepare an instance
			 * to hold a literal, not a control string.
			 */
			ConversionSpecification() { }
			/**
			 * Constructor for a conversion specification.
			 * The argument must begin with a % and end
			 * with the conversion character for the
			 * conversion specification.
			 * @param fmtArg  String specifying the
			 *     conversion specification.
			 * @exception IllegalArgumentException if the
			 *     input string is null, zero length, or
			 *     otherwise malformed.
			 */
			ConversionSpecification(String fmtArg)
					throws IllegalArgumentException {
				if (fmtArg==null)
					throw new NullPointerException();
				if (fmtArg.length()==0)
					throw new IllegalArgumentException(
							"Control strings must have positive"+
							" lengths.");
				if (fmtArg.charAt(0)=='%') {
					fmt = fmtArg;
					pos=1;
					setArgPosition();
					setFlagCharacters();
					setFieldWidth();
					setPrecision();
					setOptionalHL();
					if (setConversionCharacter()) {
						if (pos==fmtArg.length()) {
							if(leadingZeros&&leftJustify)
								leadingZeros=false;
							if(precisionSet&&leadingZeros){
								if(conversionCharacter=='d'
										||conversionCharacter=='i'
										||conversionCharacter=='o'
										||conversionCharacter=='x')
								{
									leadingZeros=false;
								}
							}
						}
						else
							throw new IllegalArgumentException(
									"Malformed conversion specification="+
											fmtArg);
					}
					else
						throw new IllegalArgumentException(
								"Malformed conversion specification="+
										fmtArg);
				}
				else
					throw new IllegalArgumentException(
							"Control strings must begin with %.");
			}
			/**
			 * Set the String for this instance.
			 * @param s the String to store.
			 */
			void setLiteral(String s) {
				fmt = s;
			}
			/**
			 * Get the String for this instance.  Translate
			 * any escape sequences.
			 *
			 * @return s the stored String.
			 */
			String getLiteral() {
				StringBuffer sb=new StringBuffer();
				int i=0;
				while (i<fmt.length()) {
					if (fmt.charAt(i)=='\\') {
						i++;
						if (i<fmt.length()) {
							char c=fmt.charAt(i);
							switch(c) {
							case 'a':
								sb.append((char)0x07);
								break;
							case 'b':
								sb.append('\b');
								break;
							case 'f':
								sb.append('\f');
								break;
							case 'n':
								sb.append(System.getProperty("line.separator"));
								break;
							case 'r':
								sb.append('\r');
								break;
							case 't':
								sb.append('\t');
								break;
							case 'v':
								sb.append((char)0x0b);
								break;
							case '\\':
								sb.append('\\');
								break;
							}
							i++;
						}
						else
							sb.append('\\');
					}
					else
						i++;
				}
				return fmt;
			}
			/**
			 * Get the conversion character that tells what
			 * type of control character this instance has.
			 *
			 * @return the conversion character.
			 */
			char getConversionCharacter() {
				return conversionCharacter;
			}
			/**
			 * Check whether the specifier has a variable
			 * field width that is going to be set by an
			 * argument.
			 * @return <code>true</code> if the conversion
			 *   uses an * field width; otherwise
			 *   <code>false</code>.
			 */
			boolean isVariableFieldWidth() {
				return variableFieldWidth;
			}
			/**
			 * Set the field width with an argument.  A
			 * negative field width is taken as a - flag
			 * followed by a positive field width.
			 * @param fw the field width.
			 */
			void setFieldWidthWithArg(int fw) {
				if (fw<0) leftJustify = true;
				fieldWidthSet = true;
				fieldWidth = Math.abs(fw);
			}
			/**
			 * Check whether the specifier has a variable
			 * precision that is going to be set by an
			 * argument.
			 * @return <code>true</code> if the conversion
			 *   uses an * precision; otherwise
			 *   <code>false</code>.
			 */
			boolean isVariablePrecision() {
				return variablePrecision;
			}
			/**
			 * Set the precision with an argument.  A
			 * negative precision will be changed to zero.
			 * @param pr the precision.
			 */
			void setPrecisionWithArg(int pr) {
				precisionSet = true;
				precision = Math.max(pr,0);
			}
			/**
			 * Format an int argument using this conversion
			 * specification.
			 * @param s the int to format.
			 * @return the formatted String.
			 * @exception IllegalArgumentException if the
			 *     conversion character is f, e, E, g, or G.
			 */
			String internalsprintf(int s)
					throws IllegalArgumentException {
				String s2 = "";
				switch(conversionCharacter) {
				case 'd':
				case 'i':
					if (optionalh)
						s2 = printDFormat((short)s);
					else if (optionall)
						s2 = printDFormat((long)s);
					else
						s2 = printDFormat(s);
					break;
				case 'x':
				case 'X':
					if (optionalh)
						s2 = printXFormat((short)s);
					else if (optionall)
						s2 = printXFormat((long)s);
					else
						s2 = printXFormat(s);
					break;
				case 'o':
					if (optionalh)
						s2 = printOFormat((short)s);
					else if (optionall)
						s2 = printOFormat((long)s);
					else
						s2 = printOFormat(s);
					break;
				case 'c':
				case 'C':
					s2 = printCFormat((char)s);
					break;
				default:
					throw new IllegalArgumentException(
							"Cannot format a int with a format using a "+
									conversionCharacter+
							" conversion character.");
				}
				return s2;
			}
			/**
			 * Format a long argument using this conversion
			 * specification.
			 * @param s the long to format.
			 * @return the formatted String.
			 * @exception IllegalArgumentException if the
			 *     conversion character is f, e, E, g, or G.
			 */
			String internalsprintf(long s)
					throws IllegalArgumentException {
				String s2 = "";
				switch(conversionCharacter) {
				case 'd':
				case 'i':
					if (optionalh)
						s2 = printDFormat((short)s);
					else if (optionall)
						s2 = printDFormat(s);
					else
						s2 = printDFormat((int)s);
					break;
				case 'x':
				case 'X':
					if (optionalh)
						s2 = printXFormat((short)s);
					else if (optionall)
						s2 = printXFormat(s);
					else
						s2 = printXFormat((int)s);
					break;
				case 'o':
					if (optionalh)
						s2 = printOFormat((short)s);
					else if (optionall)
						s2 = printOFormat(s);
					else
						s2 = printOFormat((int)s);
					break;
				case 'c':
				case 'C':
					s2 = printCFormat((char)s);
					break;
				default:
					throw new IllegalArgumentException(
							"Cannot format a long with a format using a "+
									conversionCharacter+" conversion character.");
				}
				return s2;
			}
			/**
			 * Format a double argument using this conversion
			 * specification.
			 * @param s the double to format.
			 * @return the formatted String.
			 * @exception IllegalArgumentException if the
			 *     conversion character is c, C, s, S, i, d,
			 *     x, X, or o.
			 */
			String internalsprintf(double s)
					throws IllegalArgumentException {
				String s2 = "";
				switch(conversionCharacter) {
				case 'f':
					s2 = printFFormat(s);
					break;
				case 'E':
				case 'e':
					s2 = printEFormat(s);
					break;
				case 'G':
				case 'g':
					s2 = printGFormat(s);
					break;
				default:
					throw new IllegalArgumentException("Cannot "+
							"format a double with a format using a "+
							conversionCharacter+" conversion character.");
				}
				return s2;
			}
			/**
			 * Format a String argument using this conversion
			 * specification.
			 * @param s the String to format.
			 * @return the formatted String.
			 * @exception IllegalArgumentException if the
			 *   conversion character is neither s nor S.
			 */
			String internalsprintf(String s)
					throws IllegalArgumentException {
				String s2 = "";
				if(conversionCharacter=='s'
						|| conversionCharacter=='S')
					s2 = printSFormat(s);
				else
					throw new IllegalArgumentException("Cannot "+
							"format a String with a format using a "+
							conversionCharacter+" conversion character.");
				return s2;
			}
			/**
			 * Format an Object argument using this conversion
			 * specification.
			 * @param s the Object to format.
			 * @return the formatted String.
			 * @exception IllegalArgumentException if the
			 *     conversion character is neither s nor S.
			 */
			String internalsprintf(Object s) {
				String s2 = "";
				if(conversionCharacter=='s'
						|| conversionCharacter=='S')
					s2 = printSFormat(s.toString());
				else
					throw new IllegalArgumentException(
							"Cannot format a String with a format using"+
									" a "+conversionCharacter+
							" conversion character.");
				return s2;
			}
			/**
			 * For f format, the flag character '-', means that
			 * the output should be left justified within the
			 * field.  The default is to pad with blanks on the
			 * left.  '+' character means that the conversion
			 * will always begin with a sign (+ or -).  The
			 * blank flag character means that a non-negative
			 * input will be preceded with a blank.  If both
			 * a '+' and a ' ' are specified, the blank flag
			 * is ignored.  The '0' flag character implies that
			 * padding to the field width will be done with
			 * zeros instead of blanks.
			 *
			 * The field width is treated as the minimum number
			 * of characters to be printed.  The default is to
			 * add no padding.  Padding is with blanks by
			 * default.
			 *
			 * The precision, if set, is the number of digits
			 * to appear after the radix character.  Padding is
			 * with trailing 0s.
			 */
			private char[] fFormatDigits(double x) {
				// int defaultDigits=6;
				String sx,sxOut;
				int i,j,k;
				int n1In,n2In;
				int expon=0;
				boolean minusSign=false;
				if (x>0.0)
					sx = Double.toString(x);
				else if (x<0.0) {
					sx = Double.toString(-x);
					minusSign=true;
				}
				else {
					sx = Double.toString(x);
					if (sx.charAt(0)=='-') {
						minusSign=true;
						sx=sx.substring(1);
					}
				}
				int ePos = sx.indexOf('E');
				int rPos = sx.indexOf('.');
				if (rPos!=-1) n1In=rPos;
				else if (ePos!=-1) n1In=ePos;
				else n1In=sx.length();
				if (rPos!=-1) {
					if (ePos!=-1) n2In = ePos-rPos-1;
					else n2In = sx.length()-rPos-1;
				}
				else
					n2In = 0;
				if (ePos!=-1) {
					int ie=ePos+1;
					expon=0;
					if (sx.charAt(ie)=='-') {
						for (++ie; ie<sx.length(); ie++)
							if (sx.charAt(ie)!='0') break;
						if (ie<sx.length())
							expon=-Integer.parseInt(sx.substring(ie));
					}
					else {
						if (sx.charAt(ie)=='+') ++ie;
						for (; ie<sx.length(); ie++)
							if (sx.charAt(ie)!='0') break;
						if (ie<sx.length())
							expon=Integer.parseInt(sx.substring(ie));
					}
				}
				int p;
				if (precisionSet) p = precision;
				else p = defaultDigits-1;
				char[] ca1 = sx.toCharArray();
				char[] ca2 = new char[n1In+n2In];
				char[] ca3,ca4,ca5;
				for (j=0; j<n1In; j++)
					ca2[j] = ca1[j];
				i = j+1;
				for (k=0; k<n2In; j++,i++,k++)
					ca2[j] = ca1[i];
				if (n1In+expon<=0) {
					ca3 = new char[-expon+n2In];
					for (j=0,k=0; k<(-n1In-expon); k++,j++)
						ca3[j]='0';
					for (i=0; i<(n1In+n2In); i++,j++)
						ca3[j]=ca2[i];
				}
				else
					ca3 = ca2;
				boolean carry=false;
				if (p<-expon+n2In) {
					if (expon<0) i = p;
					else i = p+n1In;
					carry=checkForCarry(ca3,i);
					if (carry)
						carry=startSymbolicCarry(ca3,i-1,0);
				}
				if (n1In+expon<=0) {
					ca4 = new char[2+p];
					if (!carry) ca4[0]='0';
					else ca4[0]='1';
					if(alternateForm||!precisionSet||precision!=0){
						ca4[1]='.';
						for(i=0,j=2;i<Math.min(p,ca3.length);i++,j++)
							ca4[j]=ca3[i];
						for (; j<ca4.length; j++) ca4[j]='0';
					}
				}
				else {
					if (!carry) {
						if(alternateForm||!precisionSet
								||precision!=0)
							ca4 = new char[n1In+expon+p+1];
						else
							ca4 = new char[n1In+expon];
						j=0;
					}
					else {
						if(alternateForm||!precisionSet
								||precision!=0)
							ca4 = new char[n1In+expon+p+2];
						else
							ca4 = new char[n1In+expon+1];
						ca4[0]='1';
						j=1;
					}
					for (i=0; i<Math.min(n1In+expon,ca3.length); i++,j++)
						ca4[j]=ca3[i];
					for (; i<n1In+expon; i++,j++)
						ca4[j]='0';
					if(alternateForm||!precisionSet||precision!=0){
						ca4[j]='.'; j++;
						for (k=0; i<ca3.length && k<p; i++,j++,k++)
							ca4[j]=ca3[i];
						for (; j<ca4.length; j++) ca4[j]='0';
					}
				}
				int nZeros=0;
				if (!leftJustify && leadingZeros) {
					int xThousands=0;
					if (thousands) {
						int xlead=0;
						if (ca4[0]=='+'||ca4[0]=='-'||ca4[0]==' ')
							xlead=1;
						int xdp=xlead;
						for (; xdp<ca4.length; xdp++)
							if (ca4[xdp]=='.') break;
						xThousands=(xdp-xlead)/3;
					}
					if (fieldWidthSet)
						nZeros = fieldWidth-ca4.length;
					if ((!minusSign&&(leadingSign||leadingSpace))||minusSign)
						nZeros--;
					nZeros-=xThousands;
					if (nZeros<0) nZeros=0;
				}
				j=0;
				if ((!minusSign&&(leadingSign||leadingSpace))||minusSign) {
					ca5 = new char[ca4.length+nZeros+1];
					j++;
				}
				else
					ca5 = new char[ca4.length+nZeros];
				if (!minusSign) {
					if (leadingSign) ca5[0]='+';
					if (leadingSpace) ca5[0]=' ';
				}
				else
					ca5[0]='-';
				for (i=0; i<nZeros; i++,j++)
					ca5[j]='0';
				for (i=0; i<ca4.length; i++,j++) ca5[j]=ca4[i];

				int lead=0;
				if (ca5[0]=='+'||ca5[0]=='-'||ca5[0]==' ')
					lead=1;
				int dp=lead;
				for (; dp<ca5.length; dp++)
					if (ca5[dp]=='.') break;
				int nThousands=(dp-lead)/3;
				// Localize the decimal point.
				if (dp<ca5.length)
					ca5[dp]=dfs.getDecimalSeparator();
				char[] ca6 = ca5;
				if (thousands && nThousands>0) {
					ca6 = new char[ca5.length+nThousands+lead];
					ca6[0]=ca5[0];
					for (i=lead,k=lead; i<dp; i++) {
						if (i>0 && (dp-i)%3==0) {
							// ca6[k]=',';
							ca6[k]=dfs.getGroupingSeparator();
							ca6[k+1]=ca5[i];
							k+=2;
						}
						else {
							ca6[k]=ca5[i]; k++;
						}
					}
					for (; i<ca5.length; i++,k++) {
						ca6[k]=ca5[i];
					}
				}
				return ca6;
			}
			/**
			 * An intermediate routine on the way to creating
			 * an f format String.  The method decides whether
			 * the input double value is an infinity,
			 * not-a-number, or a finite double and formats
			 * each type of input appropriately.
			 * @param x the double value to be formatted.
			 * @return the converted double value.
			 */
			private String fFormatString(double x) {
				boolean noDigits=false;
				char[] ca6,ca7;
				if (Double.isInfinite(x)) {
					if (x==Double.POSITIVE_INFINITY) {
						if (leadingSign) ca6 = "+Inf".toCharArray();
						else if (leadingSpace)
							ca6 = " Inf".toCharArray();
						else ca6 = "Inf".toCharArray();
					}
					else
						ca6 = "-Inf".toCharArray();
					noDigits = true;
				}
				else if (Double.isNaN(x)) {
					if (leadingSign) ca6 = "+NaN".toCharArray();
					else if (leadingSpace)
						ca6 = " NaN".toCharArray();
					else ca6 = "NaN".toCharArray();
					noDigits = true;
				}
				else
					ca6 = fFormatDigits(x);
				ca7 = applyFloatPadding(ca6,false);
				return new String(ca7);
			}
			/**
			 * For e format, the flag character '-', means that
			 * the output should be left justified within the
			 * field.  The default is to pad with blanks on the
			 * left.  '+' character means that the conversion
			 * will always begin with a sign (+ or -).  The
			 * blank flag character means that a non-negative
			 * input will be preceded with a blank.  If both a
			 * '+' and a ' ' are specified, the blank flag is
			 * ignored.  The '0' flag character implies that
			 * padding to the field width will be done with
			 * zeros instead of blanks.
			 *
			 * The field width is treated as the minimum number
			 * of characters to be printed.  The default is to
			 * add no padding.  Padding is with blanks by
			 * default.
			 *
			 * The precision, if set, is the minimum number of
			 * digits to appear after the radix character.
			 * Padding is with trailing 0s.
			 *
			 * The behavior is like printf.  One (hopefully the
			 * only) exception is that the minimum number of
			 * exponent digits is 3 instead of 2 for e and E
			 * formats when the optional L is used before the
			 * e, E, g, or G conversion character. The optional
			 * L does not imply conversion to a long long
			 * double.
			 */
			private char[] eFormatDigits(double x,char eChar) {
				char[] ca1,ca2,ca3;
				// int defaultDigits=6;
				String sx,sxOut;
				int i,j,k,p;
				int n1In,n2In;
				int expon=0;
				int ePos,rPos,eSize;
				boolean minusSign=false;
				if (x>0.0)
					sx = Double.toString(x);
				else if (x<0.0) {
					sx = Double.toString(-x);
					minusSign=true;
				}
				else {
					sx = Double.toString(x);
					if (sx.charAt(0)=='-') {
						minusSign=true;
						sx=sx.substring(1);
					}
				}
				ePos = sx.indexOf('E');
				if (ePos==-1) ePos = sx.indexOf('e');
				rPos = sx.indexOf('.');
				if (rPos!=-1) n1In=rPos;
				else if (ePos!=-1) n1In=ePos;
				else n1In=sx.length();
				if (rPos!=-1) {
					if (ePos!=-1) n2In = ePos-rPos-1;
					else n2In = sx.length()-rPos-1;
				}
				else
					n2In = 0;
				if (ePos!=-1) {
					int ie=ePos+1;
					expon=0;
					if (sx.charAt(ie)=='-') {
						for (++ie; ie<sx.length(); ie++)
							if (sx.charAt(ie)!='0') break;
						if (ie<sx.length())
							expon=-Integer.parseInt(sx.substring(ie));
					}
					else {
						if (sx.charAt(ie)=='+') ++ie;
						for (; ie<sx.length(); ie++)
							if (sx.charAt(ie)!='0') break;
						if (ie<sx.length())
							expon=Integer.parseInt(sx.substring(ie));
					}
				}
				if (rPos!=-1) expon += rPos-1;
				if (precisionSet) p = precision;
				else p = defaultDigits-1;
				if (rPos!=-1 && ePos!=-1)
					ca1=(sx.substring(0,rPos)+
							sx.substring(rPos+1,ePos)).toCharArray();
				else if (rPos!=-1)
					ca1 = (sx.substring(0,rPos)+
							sx.substring(rPos+1)).toCharArray();
				else if (ePos!=-1)
					ca1 = sx.substring(0,ePos).toCharArray();
				else
					ca1 = sx.toCharArray();
				boolean carry=false;
				int i0=0;
				if (ca1[0]!='0')
					i0 = 0;
				else
					for (i0=0; i0<ca1.length; i0++)
						if (ca1[i0]!='0') break;
				if (i0+p<ca1.length-1) {
					carry=checkForCarry(ca1,i0+p+1);
					if (carry)
						carry = startSymbolicCarry(ca1,i0+p,i0);
					if (carry) {
						ca2 = new char[i0+p+1];
						ca2[i0]='1';
						for (j=0; j<i0; j++) ca2[j]='0';
						for (i=i0,j=i0+1; j<p+1; i++,j++)
							ca2[j] = ca1[i];
						expon++;
						ca1 = ca2;
					}
				}
				if (Math.abs(expon)<100 && !optionalL) eSize=4;
				else eSize=5;
				if (alternateForm||!precisionSet||precision!=0)
					ca2 = new char[2+p+eSize];
				else
					ca2 = new char[1+eSize];
				if (ca1[0]!='0') {
					ca2[0] = ca1[0];
					j=1;
				}
				else {
					for (j=1; j<(ePos==-1?ca1.length:ePos); j++)
						if (ca1[j]!='0') break;
					if ((ePos!=-1 && j<ePos)||
							(ePos==-1 && j<ca1.length)) {
						ca2[0] = ca1[j];
						expon -= j;
						j++;
					}
					else {
						ca2[0]='0';
						j=2;
					}
				}
				if (alternateForm||!precisionSet||precision!=0) {
					ca2[1] = '.';
					i=2;
				}
				else
					i=1;
				for (k=0; k<p && j<ca1.length; j++,i++,k++)
					ca2[i] = ca1[j];
				for (;i<ca2.length-eSize; i++)
					ca2[i] = '0';
				ca2[i++] = eChar;
				if (expon<0) ca2[i++]='-';
				else ca2[i++]='+';
				expon = Math.abs(expon);
				if (expon>=100) {
					switch(expon/100) {
					case 1: ca2[i]='1'; break;
					case 2: ca2[i]='2'; break;
					case 3: ca2[i]='3'; break;
					case 4: ca2[i]='4'; break;
					case 5: ca2[i]='5'; break;
					case 6: ca2[i]='6'; break;
					case 7: ca2[i]='7'; break;
					case 8: ca2[i]='8'; break;
					case 9: ca2[i]='9'; break;
					}
					i++;
				}
				switch((expon%100)/10) {
				case 0: ca2[i]='0'; break;
				case 1: ca2[i]='1'; break;
				case 2: ca2[i]='2'; break;
				case 3: ca2[i]='3'; break;
				case 4: ca2[i]='4'; break;
				case 5: ca2[i]='5'; break;
				case 6: ca2[i]='6'; break;
				case 7: ca2[i]='7'; break;
				case 8: ca2[i]='8'; break;
				case 9: ca2[i]='9'; break;
				}
				i++;
				switch(expon%10) {
				case 0: ca2[i]='0'; break;
				case 1: ca2[i]='1'; break;
				case 2: ca2[i]='2'; break;
				case 3: ca2[i]='3'; break;
				case 4: ca2[i]='4'; break;
				case 5: ca2[i]='5'; break;
				case 6: ca2[i]='6'; break;
				case 7: ca2[i]='7'; break;
				case 8: ca2[i]='8'; break;
				case 9: ca2[i]='9'; break;
				}
				int nZeros=0;
				if (!leftJustify && leadingZeros) {
					int xThousands=0;
					if (thousands) {
						int xlead=0;
						if (ca2[0]=='+'||ca2[0]=='-'||ca2[0]==' ')
							xlead=1;
						int xdp=xlead;
						for (; xdp<ca2.length; xdp++)
							if (ca2[xdp]=='.') break;
						xThousands=(xdp-xlead)/3;
					}
					if (fieldWidthSet)
						nZeros = fieldWidth-ca2.length;
					if ((!minusSign&&(leadingSign||leadingSpace))||minusSign)
						nZeros--;
					nZeros-=xThousands;
					if (nZeros<0) nZeros=0;
				}
				j=0;
				if ((!minusSign&&(leadingSign || leadingSpace))||minusSign) {
					ca3 = new char[ca2.length+nZeros+1];
					j++;
				}
				else
					ca3 = new char[ca2.length+nZeros];
				if (!minusSign) {
					if (leadingSign) ca3[0]='+';
					if (leadingSpace) ca3[0]=' ';
				}
				else
					ca3[0]='-';
				for (k=0; k<nZeros; j++,k++)
					ca3[j]='0';
				for (i=0; i<ca2.length && j<ca3.length; i++,j++)
					ca3[j]=ca2[i];

				int lead=0;
				if (ca3[0]=='+'||ca3[0]=='-'||ca3[0]==' ')
					lead=1;
				int dp=lead;
				for (; dp<ca3.length; dp++)
					if (ca3[dp]=='.') break;
				int nThousands=dp/3;
				// Localize the decimal point.
				if (dp < ca3.length)
					ca3[dp] = dfs.getDecimalSeparator();
				char[] ca4 = ca3;
				if (thousands && nThousands>0) {
					ca4 = new char[ca3.length+nThousands+lead];
					ca4[0]=ca3[0];
					for (i=lead,k=lead; i<dp; i++) {
						if (i>0 && (dp-i)%3==0) {
							// ca4[k]=',';
							ca4[k]=dfs.getGroupingSeparator();
							ca4[k+1]=ca3[i];
							k+=2;
						}
						else {
							ca4[k]=ca3[i]; k++;
						}
					}
					for (; i<ca3.length; i++,k++)
						ca4[k]=ca3[i];
				}
				return ca4;
			}
			/**
			 * Check to see if the digits that are going to
			 * be truncated because of the precision should
			 * force a round in the preceding digits.
			 * @param ca1 the array of digits
			 * @param icarry the index of the first digit that
			 *     is to be truncated from the print
			 * @return <code>true</code> if the truncation forces
			 *     a round that will change the print
			 */
			private boolean checkForCarry(char[] ca1,int icarry) {
				boolean carry=false;
				if (icarry<ca1.length) {
					if (ca1[icarry]=='6'||ca1[icarry]=='7'
							||ca1[icarry]=='8'||ca1[icarry]=='9') carry=true;
					else if (ca1[icarry]=='5') {
						int ii=icarry+1;
						for (;ii<ca1.length; ii++)
							if (ca1[ii]!='0') break;
						carry=ii<ca1.length;
						if (!carry&&icarry>0) {
							carry=(ca1[icarry-1]=='1'||ca1[icarry-1]=='3'
									||ca1[icarry-1]=='5'||ca1[icarry-1]=='7'
									||ca1[icarry-1]=='9');
						}
					}
				}
				return carry;
			}
			/**
			 * Start the symbolic carry process.  The process
			 * is not quite finished because the symbolic
			 * carry may change the length of the string and
			 * change the exponent (in e format).
			 * @param cLast index of the last digit changed
			 *     by the round
			 * @param cFirst index of the first digit allowed
			 *     to be changed by this phase of the round
			 * @return <code>true</code> if the carry forces
			 *     a round that will change the print still
			 *     more
			 */
			private boolean startSymbolicCarry(
					char[] ca,int cLast,int cFirst) {
				boolean carry=true;
				for (int i=cLast; carry && i>=cFirst; i--) {
					carry = false;
					switch(ca[i]) {
					case '0': ca[i]='1'; break;
					case '1': ca[i]='2'; break;
					case '2': ca[i]='3'; break;
					case '3': ca[i]='4'; break;
					case '4': ca[i]='5'; break;
					case '5': ca[i]='6'; break;
					case '6': ca[i]='7'; break;
					case '7': ca[i]='8'; break;
					case '8': ca[i]='9'; break;
					case '9': ca[i]='0'; carry=true; break;
					}
				}
				return carry;
			}
			/**
			 * An intermediate routine on the way to creating
			 * an e format String.  The method decides whether
			 * the input double value is an infinity,
			 * not-a-number, or a finite double and formats
			 * each type of input appropriately.
			 * @param x the double value to be formatted.
			 * @param eChar an 'e' or 'E' to use in the
			 *     converted double value.
			 * @return the converted double value.
			 */
			private String eFormatString(double x,char eChar) {
				boolean noDigits=false;
				char[] ca4,ca5;
				if (Double.isInfinite(x)) {
					if (x==Double.POSITIVE_INFINITY) {
						if (leadingSign) ca4 = "+Inf".toCharArray();
						else if (leadingSpace)
							ca4 = " Inf".toCharArray();
						else ca4 = "Inf".toCharArray();
					}
					else
						ca4 = "-Inf".toCharArray();
					noDigits = true;
				}
				else if (Double.isNaN(x)) {
					if (leadingSign) ca4 = "+NaN".toCharArray();
					else if (leadingSpace)
						ca4 = " NaN".toCharArray();
					else ca4 = "NaN".toCharArray();
					noDigits = true;
				}
				else
					ca4 = eFormatDigits(x,eChar);
				ca5 = applyFloatPadding(ca4,false);
				return new String(ca5);
			}
			/**
			 * Apply zero or blank, left or right padding.
			 * @param ca4 array of characters before padding is
			 *     finished
			 * @param noDigits NaN or signed Inf
			 * @return a padded array of characters
			 */
			private char[] applyFloatPadding(
					char[] ca4,boolean noDigits) {
				char[] ca5 = ca4;
				if (fieldWidthSet) {
					int i,j,nBlanks;
					if (leftJustify) {
						nBlanks = fieldWidth-ca4.length;
						if (nBlanks > 0) {
							ca5 = new char[ca4.length+nBlanks];
							for (i=0; i<ca4.length; i++)
								ca5[i] = ca4[i];
							for (j=0; j<nBlanks; j++,i++)
								ca5[i] = ' ';
						}
					}
					else if (!leadingZeros || noDigits) {
						nBlanks = fieldWidth-ca4.length;
						if (nBlanks > 0) {
							ca5 = new char[ca4.length+nBlanks];
							for (i=0; i<nBlanks; i++)
								ca5[i] = ' ';
							for (j=0; j<ca4.length; i++,j++)
								ca5[i] = ca4[j];
						}
					}
					else if (leadingZeros) {
						nBlanks = fieldWidth-ca4.length;
						if (nBlanks > 0) {
							ca5 = new char[ca4.length+nBlanks];
							i=0; j=0;
							if (ca4[0]=='-') { ca5[0]='-'; i++; j++; }
							for (int k=0; k<nBlanks; i++,k++)
								ca5[i] = '0';
							for (; j<ca4.length; i++,j++)
								ca5[i] = ca4[j];
						}
					}
				}
				return ca5;
			}
			/**
			 * Format method for the f conversion character.
			 * @param x the double to format.
			 * @return the formatted String.
			 */
			private String printFFormat(double x) {
				return fFormatString(x);
			}
			/**
			 * Format method for the e or E conversion
			 * character.
			 * @param x the double to format.
			 * @return the formatted String.
			 */
			private String printEFormat(double x) {
				if (conversionCharacter=='e')
					return eFormatString(x,'e');
				else
					return eFormatString(x,'E');
			}
			/**
			 * Format method for the g conversion character.
			 *
			 * For g format, the flag character '-', means that
			 *  the output should be left justified within the
			 * field.  The default is to pad with blanks on the
			 * left.  '+' character means that the conversion
			 * will always begin with a sign (+ or -).  The
			 * blank flag character means that a non-negative
			 * input will be preceded with a blank.  If both a
			 * '+' and a ' ' are specified, the blank flag is
			 * ignored.  The '0' flag character implies that
			 * padding to the field width will be done with
			 * zeros instead of blanks.
			 *
			 * The field width is treated as the minimum number
			 * of characters to be printed.  The default is to
			 * add no padding.  Padding is with blanks by
			 * default.
			 *
			 * The precision, if set, is the minimum number of
			 * digits to appear after the radix character.
			 * Padding is with trailing 0s.
			 * @param x the double to format.
			 * @return the formatted String.
			 */
			private String printGFormat(double x) {
				String sx,sy,sz,ret;
				int savePrecision=precision;
				int i;
				char[] ca4,ca5;
				boolean noDigits=false;
				if (Double.isInfinite(x)) {
					if (x==Double.POSITIVE_INFINITY) {
						if (leadingSign) ca4 = "+Inf".toCharArray();
						else if (leadingSpace)
							ca4 = " Inf".toCharArray();
						else ca4 = "Inf".toCharArray();
					}
					else
						ca4 = "-Inf".toCharArray();
					noDigits = true;
				}
				else if (Double.isNaN(x)) {
					if (leadingSign) ca4 = "+NaN".toCharArray();
					else if (leadingSpace)
						ca4 = " NaN".toCharArray();
					else ca4 = "NaN".toCharArray();
					noDigits = true;
				}
				else {
					if (!precisionSet) precision=defaultDigits;
					if (precision==0) precision=1;
					int ePos=-1;
					if (conversionCharacter=='g') {
						sx = eFormatString(x,'e').trim();
						ePos=sx.indexOf('e');
					}
					else {
						sx = eFormatString(x,'E').trim();
						ePos=sx.indexOf('E');
					}
					i=ePos+1;
					int expon=0;
					if (sx.charAt(i)=='-') {
						for (++i; i<sx.length(); i++)
							if (sx.charAt(i)!='0') break;
						if (i<sx.length())
							expon=-Integer.parseInt(sx.substring(i));
					}
					else {
						if (sx.charAt(i)=='+') ++i;
						for (; i<sx.length(); i++)
							if (sx.charAt(i)!='0') break;
						if (i<sx.length())
							expon=Integer.parseInt(sx.substring(i));
					}
					// Trim trailing zeros.
					// If the radix character is not followed by
					// a digit, trim it, too.
					if (!alternateForm) {
						if (expon>=-4 && expon<precision)
							sy = fFormatString(x).trim();
						else
							sy = sx.substring(0,ePos);
						i=sy.length()-1;
						for (; i>=0; i--)
							if (sy.charAt(i)!='0') break;
						if (i>=0 && sy.charAt(i)=='.') i--;
						if (i==-1) sz="0";
						else if (!Character.isDigit(sy.charAt(i)))
							sz=sy.substring(0,i+1)+"0";
						else sz=sy.substring(0,i+1);
						if (expon>=-4 && expon<precision)
							ret=sz;
						else
							ret=sz+sx.substring(ePos);
					}
					else {
						if (expon>=-4 && expon<precision)
							ret = fFormatString(x).trim();
						else
							ret = sx;
					}
					// leading space was trimmed off during
					// construction
					if (leadingSpace) if (x>=0) ret = " "+ret;
					ca4 = ret.toCharArray();
				}
				// Pad with blanks or zeros.
				ca5 = applyFloatPadding(ca4,false);
				precision=savePrecision;
				return new String(ca5);
			}
			/**
			 * Format method for the d conversion specifer and
			 * short argument.
			 *
			 * For d format, the flag character '-', means that
			 * the output should be left justified within the
			 * field.  The default is to pad with blanks on the
			 * left.  A '+' character means that the conversion
			 * will always begin with a sign (+ or -).  The
			 * blank flag character means that a non-negative
			 * input will be preceded with a blank.  If both a
			 * '+' and a ' ' are specified, the blank flag is
			 * ignored.  The '0' flag character implies that
			 * padding to the field width will be done with
			 * zeros instead of blanks.
			 *
			 * The field width is treated as the minimum number
			 * of characters to be printed.  The default is to
			 * add no padding.  Padding is with blanks by
			 * default.
			 *
			 * The precision, if set, is the minimum number of
			 * digits to appear.  Padding is with leading 0s.
			 * @param x the short to format.
			 * @return the formatted String.
			 */
			private String printDFormat(short x) {
				return printDFormat(Short.toString(x));
			}
			/**
			 * Format method for the d conversion character and
			 * long argument.
			 *
			 * For d format, the flag character '-', means that
			 * the output should be left justified within the
			 * field.  The default is to pad with blanks on the
			 * left.  A '+' character means that the conversion
			 * will always begin with a sign (+ or -).  The
			 * blank flag character means that a non-negative
			 * input will be preceded with a blank.  If both a
			 * '+' and a ' ' are specified, the blank flag is
			 * ignored.  The '0' flag character implies that
			 * padding to the field width will be done with
			 * zeros instead of blanks.
			 *
			 * The field width is treated as the minimum number
			 * of characters to be printed.  The default is to
			 * add no padding.  Padding is with blanks by
			 * default.
			 *
			 * The precision, if set, is the minimum number of
			 * digits to appear.  Padding is with leading 0s.
			 * @param x the long to format.
			 * @return the formatted String.
			 */
			private String printDFormat(long x) {
				return printDFormat(Long.toString(x));
			}
			/**
			 * Format method for the d conversion character and
			 * int argument.
			 *
			 * For d format, the flag character '-', means that
			 * the output should be left justified within the
			 * field.  The default is to pad with blanks on the
			 * left.  A '+' character means that the conversion
			 * will always begin with a sign (+ or -).  The
			 * blank flag character means that a non-negative
			 * input will be preceded with a blank.  If both a
			 * '+' and a ' ' are specified, the blank flag is
			 * ignored.  The '0' flag character implies that
			 * padding to the field width will be done with
			 * zeros instead of blanks.
			 *
			 * The field width is treated as the minimum number
			 * of characters to be printed.  The default is to
			 * add no padding.  Padding is with blanks by
			 * default.
			 *
			 * The precision, if set, is the minimum number of
			 * digits to appear.  Padding is with leading 0s.
			 * @param x the int to format.
			 * @return the formatted String.
			 */
			private String printDFormat(int x) {
				return printDFormat(Integer.toString(x));
			}
			/**
			 * Utility method for formatting using the d
			 * conversion character.
			 * @param sx the String to format, the result of
			 *     converting a short, int, or long to a
			 *     String.
			 * @return the formatted String.
			 */
			private String printDFormat(String sx) {
				int nLeadingZeros=0;
				int nBlanks=0,n=0;
				int i=0,jFirst=0;
				boolean neg = sx.charAt(0)=='-';
				if (sx.equals("0")&&precisionSet&&precision==0)
					sx="";
				if (!neg) {
					if (precisionSet && sx.length() < precision)
						nLeadingZeros = precision-sx.length();
				}
				else {
					if (precisionSet&&(sx.length()-1)<precision)
						nLeadingZeros = precision-sx.length()+1;
				}
				if (nLeadingZeros<0) nLeadingZeros=0;
				if (fieldWidthSet) {
					nBlanks = fieldWidth-nLeadingZeros-sx.length();
					if (!neg&&(leadingSign||leadingSpace))
						nBlanks--;
				}
				if (nBlanks<0) nBlanks=0;
				if (leadingSign) n++;
				else if (leadingSpace) n++;
				n += nBlanks;
				n += nLeadingZeros;
				n += sx.length();
				char[] ca = new char[n];
				if (leftJustify) {
					if (neg) ca[i++] = '-';
					else if (leadingSign) ca[i++] = '+';
					else if (leadingSpace) ca[i++] = ' ';
					char[] csx = sx.toCharArray();
					jFirst = neg?1:0;
					for (int j=0; j<nLeadingZeros; i++,j++) 
						ca[i]='0';
					for (int j=jFirst; j<csx.length; j++,i++)
						ca[i] = csx[j];
					for (int j=0; j<nBlanks; i++,j++)
						ca[i] = ' ';
				}
				else {
					if (!leadingZeros) {
						for (i=0; i<nBlanks; i++)
							ca[i] = ' ';
						if (neg) ca[i++] = '-';
						else if (leadingSign) ca[i++] = '+';
						else if (leadingSpace) ca[i++] = ' ';
					}
					else {
						if (neg) ca[i++] = '-';
						else if (leadingSign) ca[i++] = '+';
						else if (leadingSpace) ca[i++] = ' ';
						for (int j=0; j<nBlanks; j++,i++)
							ca[i] = '0';
					}
					for (int j=0; j<nLeadingZeros; j++,i++)
						ca[i] = '0';
					char[] csx = sx.toCharArray();
					jFirst = neg?1:0;
					for (int j=jFirst; j<csx.length; j++,i++)
						ca[i] = csx[j];
				}
				return new String(ca);
			}
			/**
			 * Format method for the x conversion character and
			 * short argument.
			 *
			 * For x format, the flag character '-', means that
			 * the output should be left justified within the
			 * field.  The default is to pad with blanks on the
			 * left.  The '#' flag character means to lead with
			 * '0x'.
			 *
			 * The field width is treated as the minimum number
			 * of characters to be printed.  The default is to
			 * add no padding.  Padding is with blanks by
			 * default.
			 *
			 * The precision, if set, is the minimum number of
			 * digits to appear.  Padding is with leading 0s.
			 * @param x the short to format.
			 * @return the formatted String.
			 */
			private String printXFormat(short x) {
				String sx=null;
				if (x == Short.MIN_VALUE)
					sx = "8000";
				else if (x < 0) {
					String t;
					if (x==Short.MIN_VALUE)
						t = "0";
					else {
						t = Integer.toString(
								(~(-x-1))^Short.MIN_VALUE,16);
						if (t.charAt(0)=='F'||t.charAt(0)=='f')
							t = t.substring(16,32);
					}
					switch (t.length()) {
					case 1:
						sx = "800"+t;
						break;
					case 2:
						sx = "80"+t;
						break;
					case 3:
						sx = "8"+t;
						break;
					case 4:
						switch (t.charAt(0)) {
						case '1':
							sx = "9"+t.substring(1,4);
							break;
						case '2':
							sx = "a"+t.substring(1,4);
							break;
						case '3':
							sx = "b"+t.substring(1,4);
							break;
						case '4':
							sx = "c"+t.substring(1,4);
							break;
						case '5':
							sx = "d"+t.substring(1,4);
							break;
						case '6':
							sx = "e"+t.substring(1,4);
							break;
						case '7':
							sx = "f"+t.substring(1,4);
							break;
						}
						break;
					}
				}
				else
					sx = Integer.toString((int)x,16);
				return printXFormat(sx);
			}
			/**
			 * Format method for the x conversion character and
			 * long argument.
			 *
			 * For x format, the flag character '-', means that
			 * the output should be left justified within the
			 * field.  The default is to pad with blanks on the
			 * left.  The '#' flag character means to lead with
			 * '0x'.
			 *
			 * The field width is treated as the minimum number
			 * of characters to be printed.  The default is to
			 * add no padding.  Padding is with blanks by
			 * default.
			 *
			 * The precision, if set, is the minimum number of
			 * digits to appear.  Padding is with leading 0s.
			 * @param x the long to format.
			 * @return the formatted String.
			 */
			private String printXFormat(long x) {
				String sx=null;
				if (x == Long.MIN_VALUE)
					sx = "8000000000000000";
				else if (x < 0) {
					String t = Long.toString(
							(~(-x-1))^Long.MIN_VALUE,16);
					switch (t.length()) {
					case 1:
						sx = "800000000000000"+t;
						break;
					case 2:
						sx = "80000000000000"+t;
						break;
					case 3:
						sx = "8000000000000"+t;
						break;
					case 4:
						sx = "800000000000"+t;
						break;
					case 5:
						sx = "80000000000"+t;
						break;
					case 6:
						sx = "8000000000"+t;
						break;
					case 7:
						sx = "800000000"+t;
						break;
					case 8:
						sx = "80000000"+t;
						break;
					case 9:
						sx = "8000000"+t;
						break;
					case 10:
						sx = "800000"+t;
						break;
					case 11:
						sx = "80000"+t;
						break;
					case 12:
						sx = "8000"+t;
						break;
					case 13:
						sx = "800"+t;
						break;
					case 14:
						sx = "80"+t;
						break;
					case 15:
						sx = "8"+t;
						break;
					case 16:
						switch (t.charAt(0)) {
						case '1':
							sx = "9"+t.substring(1,16);
							break;
						case '2':
							sx = "a"+t.substring(1,16);
							break;
						case '3':
							sx = "b"+t.substring(1,16);
							break;
						case '4':
							sx = "c"+t.substring(1,16);
							break;
						case '5':
							sx = "d"+t.substring(1,16);
							break;
						case '6':
							sx = "e"+t.substring(1,16);
							break;
						case '7':
							sx = "f"+t.substring(1,16);
							break;
						}
						break;
					}
				}
				else
					sx = Long.toString(x,16);
				return printXFormat(sx);
			}
			/**
			 * Format method for the x conversion character and
			 * int argument.
			 *
			 * For x format, the flag character '-', means that
			 * the output should be left justified within the
			 * field.  The default is to pad with blanks on the
			 * left.  The '#' flag character means to lead with
			 * '0x'.
			 *
			 * The field width is treated as the minimum number
			 * of characters to be printed.  The default is to
			 * add no padding.  Padding is with blanks by
			 * default.
			 *
			 * The precision, if set, is the minimum number of
			 * digits to appear.  Padding is with leading 0s.
			 * @param x the int to format.
			 * @return the formatted String.
			 */
			private String printXFormat(int x) {
				String sx=null;
				if (x == Integer.MIN_VALUE)
					sx = "80000000";
				else if (x < 0) {
					String t = Integer.toString(
							(~(-x-1))^Integer.MIN_VALUE,16);
					switch (t.length()) {
					case 1:
						sx = "8000000"+t;
						break;
					case 2:
						sx = "800000"+t;
						break;
					case 3:
						sx = "80000"+t;
						break;
					case 4:
						sx = "8000"+t;
						break;
					case 5:
						sx = "800"+t;
						break;
					case 6:
						sx = "80"+t;
						break;
					case 7:
						sx = "8"+t;
						break;
					case 8:
						switch (t.charAt(0)) {
						case '1':
							sx = "9"+t.substring(1,8);
							break;
						case '2':
							sx = "a"+t.substring(1,8);
							break;
						case '3':
							sx = "b"+t.substring(1,8);
							break;
						case '4':
							sx = "c"+t.substring(1,8);
							break;
						case '5':
							sx = "d"+t.substring(1,8);
							break;
						case '6':
							sx = "e"+t.substring(1,8);
							break;
						case '7':
							sx = "f"+t.substring(1,8);
							break;
						}
						break;
					}
				}
				else
					sx = Integer.toString(x,16);
				return printXFormat(sx);
			}
			/**
			 * Utility method for formatting using the x
			 * conversion character.
			 * @param sx the String to format, the result of
			 *     converting a short, int, or long to a
			 *     String.
			 * @return the formatted String.
			 */
			private String printXFormat(String sx) {
				int nLeadingZeros = 0;
				int nBlanks = 0;
				if (sx.equals("0")&&precisionSet&&precision==0)
					sx="";
				if (precisionSet)
					nLeadingZeros = precision-sx.length();
				if (nLeadingZeros<0) nLeadingZeros=0;
				if (fieldWidthSet) {
					nBlanks = fieldWidth-nLeadingZeros-sx.length();
					if (alternateForm) nBlanks = nBlanks - 2;
				}
				if (nBlanks<0) nBlanks=0;
				int n=0;
				if (alternateForm) n+=2;
				n += nLeadingZeros;
				n += sx.length();
				n += nBlanks;
				char[] ca = new char[n];
				int i=0;
				if (leftJustify) {
					if (alternateForm) {
						ca[i++]='0'; ca[i++]='x';
					}
					for (int j=0; j<nLeadingZeros; j++,i++)
						ca[i]='0';
					char[] csx = sx.toCharArray();
					for (int j=0; j<csx.length; j++,i++)
						ca[i] = csx[j];
					for (int j=0; j<nBlanks; j++,i++)
						ca[i] = ' ';
				}
				else {
					if (!leadingZeros)
						for (int j=0; j<nBlanks; j++,i++)
							ca[i] = ' ';
					if (alternateForm) {
						ca[i++]='0'; ca[i++]='x';
					}
					if (leadingZeros)
						for (int j=0; j<nBlanks; j++,i++)
							ca[i] = '0';
					for (int j=0; j<nLeadingZeros; j++,i++)
						ca[i]='0';
					char[] csx = sx.toCharArray();
					for (int j=0; j<csx.length; j++,i++)
						ca[i] = csx[j];
				}
				String caReturn=new String(ca);
				if (conversionCharacter=='X')
					caReturn = caReturn.toUpperCase();
				return caReturn;
			}
			/**
			 * Format method for the o conversion character and
			 * short argument.
			 *
			 * For o format, the flag character '-', means that
			 * the output should be left justified within the
			 * field.  The default is to pad with blanks on the 
			 * left.  The '#' flag character means that the
			 * output begins with a leading 0 and the precision
			 * is increased by 1.
			 *
			 * The field width is treated as the minimum number
			 * of characters to be printed.  The default is to
			 * add no padding.  Padding is with blanks by
			 * default.
			 *
			 * The precision, if set, is the minimum number of
			 * digits to appear.  Padding is with leading 0s.
			 * @param x the short to format.
			 * @return the formatted String.
			 */
			private String printOFormat(short x) {
				String sx=null;
				if (x == Short.MIN_VALUE)
					sx = "100000";
				else if (x < 0) {
					String t = Integer.toString(
							(~(-x-1))^Short.MIN_VALUE,8);
					switch (t.length()) {
					case 1:
						sx = "10000"+t;
						break;
					case 2:
						sx = "1000"+t;
						break;
					case 3:
						sx = "100"+t;
						break;
					case 4:
						sx = "10"+t;
						break;
					case 5:
						sx = "1"+t;
						break;
					}
				}
				else
					sx = Integer.toString((int)x,8);
				return printOFormat(sx);
			}
			/**
			 * Format method for the o conversion character and
			 * long argument.
			 *
			 * For o format, the flag character '-', means that
			 * the output should be left justified within the
			 * field.  The default is to pad with blanks on the 
			 * left.  The '#' flag character means that the
			 * output begins with a leading 0 and the precision
			 * is increased by 1.
			 *
			 * The field width is treated as the minimum number
			 * of characters to be printed.  The default is to
			 * add no padding.  Padding is with blanks by
			 * default.
			 *
			 * The precision, if set, is the minimum number of
			 * digits to appear.  Padding is with leading 0s.
			 * @param x the long to format.
			 * @return the formatted String.
			 */
			private String printOFormat(long x) {
				String sx=null;
				if (x == Long.MIN_VALUE)
					sx = "1000000000000000000000";
				else if (x < 0) {
					String t = Long.toString(
							(~(-x-1))^Long.MIN_VALUE,8);
					switch (t.length()) {
					case 1:
						sx = "100000000000000000000"+t;
						break;
					case 2:
						sx = "10000000000000000000"+t;
						break;
					case 3:
						sx = "1000000000000000000"+t;
						break;
					case 4:
						sx = "100000000000000000"+t;
						break;
					case 5:
						sx = "10000000000000000"+t;
						break;
					case 6:
						sx = "1000000000000000"+t;
						break;
					case 7:
						sx = "100000000000000"+t;
						break;
					case 8:
						sx = "10000000000000"+t;
						break;
					case 9:
						sx = "1000000000000"+t;
						break;
					case 10:
						sx = "100000000000"+t;
						break;
					case 11:
						sx = "10000000000"+t;
						break;
					case 12:
						sx = "1000000000"+t;
						break;
					case 13:
						sx = "100000000"+t;
						break;
					case 14:
						sx = "10000000"+t;
						break;
					case 15:
						sx = "1000000"+t;
						break;
					case 16:
						sx = "100000"+t;
						break;
					case 17:
						sx = "10000"+t;
						break;
					case 18:
						sx = "1000"+t;
						break;
					case 19:
						sx = "100"+t;
						break;
					case 20:
						sx = "10"+t;
						break;
					case 21:
						sx = "1"+t;
						break;
					}
				}
				else
					sx = Long.toString(x,8);
				return printOFormat(sx);
			}
			/**
			 * Format method for the o conversion character and
			 * int argument.
			 *
			 * For o format, the flag character '-', means that
			 * the output should be left justified within the
			 * field.  The default is to pad with blanks on the
			 * left.  The '#' flag character means that the
			 * output begins with a leading 0 and the precision
			 * is increased by 1.
			 *
			 * The field width is treated as the minimum number
			 * of characters to be printed.  The default is to
			 * add no padding.  Padding is with blanks by
			 * default.
			 *
			 * The precision, if set, is the minimum number of
			 * digits to appear.  Padding is with leading 0s.
			 * @param x the int to format.
			 * @return the formatted String.
			 */
			private String printOFormat(int x) {
				String sx=null;
				if (x == Integer.MIN_VALUE)
					sx = "20000000000";
				else if (x < 0) {
					String t = Integer.toString(
							(~(-x-1))^Integer.MIN_VALUE,8);
					switch (t.length()) {
					case 1:
						sx = "2000000000"+t;
						break;
					case 2:
						sx = "200000000"+t;
						break;
					case 3:
						sx = "20000000"+t;
						break;
					case 4:
						sx = "2000000"+t;
						break;
					case 5:
						sx = "200000"+t;
						break;
					case 6:
						sx = "20000"+t;
						break;
					case 7:
						sx = "2000"+t;
						break;
					case 8:
						sx = "200"+t;
						break;
					case 9:
						sx = "20"+t;
						break;
					case 10:
						sx = "2"+t;
						break;
					case 11:
						sx = "3"+t.substring(1);
						break;
					}
				}
				else
					sx = Integer.toString(x,8);
				return printOFormat(sx);
			}
			/**
			 * Utility method for formatting using the o
			 * conversion character.
			 * @param sx the String to format, the result of
			 *     converting a short, int, or long to a
			 *     String.
			 * @return the formatted String.
			 */
			private String printOFormat(String sx) {
				int nLeadingZeros = 0;
				int nBlanks = 0;
				if (sx.equals("0")&&precisionSet&&precision==0)
					sx="";
				if (precisionSet)
					nLeadingZeros = precision-sx.length();
				if (alternateForm) nLeadingZeros++;
				if (nLeadingZeros<0) nLeadingZeros=0;
				if (fieldWidthSet)
					nBlanks = fieldWidth-nLeadingZeros-sx.length();
				if (nBlanks<0) nBlanks=0;
				int n=nLeadingZeros+sx.length()+nBlanks;
				char[] ca = new char[n];
				int i;
				if (leftJustify) {
					for (i=0; i<nLeadingZeros; i++) ca[i]='0';
					char[] csx = sx.toCharArray();
					for (int j=0; j<csx.length; j++,i++)
						ca[i] = csx[j];
					for (int j=0; j<nBlanks; j++,i++) ca[i] = ' ';
				}
				else {
					if (leadingZeros)
						for (i=0; i<nBlanks; i++) ca[i]='0';
					else
						for (i=0; i<nBlanks; i++) ca[i]=' ';
					for (int j=0; j<nLeadingZeros; j++,i++)
						ca[i]='0';
					char[] csx = sx.toCharArray();
					for (int j=0; j<csx.length; j++,i++)
						ca[i] = csx[j];
				}
				return new String(ca);
			}
			/**
			 * Format method for the c conversion character and
			 * char argument.
			 *
			 * The only flag character that affects c format is
			 * the '-', meaning that the output should be left
			 * justified within the field.  The default is to
			 * pad with blanks on the left.
			 *
			 * The field width is treated as the minimum number
			 * of characters to be printed.  Padding is with
			 * blanks by default.  The default width is 1.
			 *
			 * The precision, if set, is ignored.
			 * @param x the char to format.
			 * @return the formatted String.
			 */
			private String printCFormat(char x) {
				int nPrint = 1;
				int width = fieldWidth;
				if (!fieldWidthSet) width = nPrint;
				char[] ca = new char[width];
				int i=0;
				if (leftJustify) {
					ca[0] = x;
					for (i=1; i<=width-nPrint; i++) ca[i]=' ';
				}
				else {
					for (i=0; i<width-nPrint; i++) ca[i]=' ';
					ca[i] = x;
				}
				return new String(ca);
			}
			/**
			 * Format method for the s conversion character and
			 * String argument.
			 *
			 * The only flag character that affects s format is
			 * the '-', meaning that the output should be left
			 * justified within the field.  The default is to
			 * pad with blanks on the left.
			 *
			 * The field width is treated as the minimum number
			 * of characters to be printed.  The default is the
			 * smaller of the number of characters in the the
			 * input and the precision.  Padding is with blanks
			 * by default.
			 *
			 * The precision, if set, specifies the maximum
			 * number of characters to be printed from the
			 * string.  A null digit string is treated
			 * as a 0.  The default is not to set a maximum
			 * number of characters to be printed.
			 * @param x the String to format.
			 * @return the formatted String.
			 */
			private String printSFormat(String x) {
				int nPrint = x.length();
				int width = fieldWidth;
				if (precisionSet && nPrint>precision)
					nPrint=precision;
				if (!fieldWidthSet) width = nPrint;
				int n=0;
				if (width>nPrint) n+=width-nPrint;
				if (nPrint>=x.length()) n+= x.length();
				else n+= nPrint;
				char[] ca = new char[n];
				int i=0;
				if (leftJustify) {
					if (nPrint>=x.length()) {
						char[] csx = x.toCharArray();
						for (i=0; i<x.length(); i++) ca[i]=csx[i];
					}
					else {
						char[] csx =
								x.substring(0,nPrint).toCharArray();
						for (i=0; i<nPrint; i++) ca[i]=csx[i];
					}
					for (int j=0; j<width-nPrint; j++,i++)
						ca[i]=' ';
				}
				else {
					for (i=0; i<width-nPrint; i++) ca[i]=' ';
					if (nPrint>=x.length()) {
						char[] csx = x.toCharArray();
						for (int j=0; j<x.length(); i++,j++)
							ca[i]=csx[j];
					}
					else {
						char[] csx =
								x.substring(0,nPrint).toCharArray();
						for (int j=0; j<nPrint; i++,j++)
							ca[i]=csx[j];
					}
				}
				return new String(ca);
			}
			/**
			 * Check for a conversion character.  If it is
			 * there, store it.
			 * @param x the String to format.
			 * @return <code>true</code> if the conversion
			 *     character is there, and
			 *     <code>false</code> otherwise.
			 */
			private boolean setConversionCharacter() {
				/* idfgGoxXeEcs */
				boolean ret = false;
				conversionCharacter='\0';
				if (pos < fmt.length()) {
					char c = fmt.charAt(pos);
					if (c=='i'||c=='d'||c=='f'||c=='g'||c=='G'
							|| c=='o' || c=='x' || c=='X' || c=='e'
							|| c=='E' || c=='c' || c=='s' || c=='%') {
						conversionCharacter = c;
						pos++;
						ret = true;
					}
				}
				return ret;
			}
			/**
			 * Check for an h, l, or L in a format.  An L is
			 * used to control the minimum number of digits
			 * in an exponent when using floating point
			 * formats.  An l or h is used to control
			 * conversion of the input to a long or short,
			 * respectively, before formatting.  If any of
			 * these is present, store them.
			 */
			private void setOptionalHL() {
				optionalh=false;
				optionall=false;
				optionalL=false;
				if (pos < fmt.length()) {
					char c = fmt.charAt(pos);
					if (c=='h') { optionalh=true; pos++; }
					else if (c=='l') { optionall=true; pos++; }
					else if (c=='L') { optionalL=true; pos++; }
				}
			}
			/**
			 * Set the precision.
			 */
			private void setPrecision() {
				int firstPos = pos;
				precisionSet = false;
				if (pos<fmt.length()&&fmt.charAt(pos)=='.') {
					pos++;
					if ((pos < fmt.length())
							&& (fmt.charAt(pos)=='*')) {
						pos++;
						if (!setPrecisionArgPosition()) {
							variablePrecision = true;
							precisionSet = true;
						}
						return;
					}
					else {
						while (pos < fmt.length()) {
							char c = fmt.charAt(pos);
							if (Character.isDigit(c)) pos++;
							else break;
						}
						if (pos > firstPos+1) {
							String sz = fmt.substring(firstPos+1,pos);
							precision = Integer.parseInt(sz);
							precisionSet = true;
						}
					}
				}
			}
			/**
			 * Set the field width.
			 */
			private void setFieldWidth() {
				int firstPos = pos;
				fieldWidth = 0;
				fieldWidthSet = false;
				if ((pos < fmt.length())
						&& (fmt.charAt(pos)=='*')) {
					pos++;
					if (!setFieldWidthArgPosition()) {
						variableFieldWidth = true;
						fieldWidthSet = true;
					}
				}
				else {
					while (pos < fmt.length()) {
						char c = fmt.charAt(pos);
						if (Character.isDigit(c)) pos++;
						else break;
					}
					if (firstPos<pos && firstPos < fmt.length()) {
						String sz = fmt.substring(firstPos,pos);
						fieldWidth = Integer.parseInt(sz);
						fieldWidthSet = true;
					}
				}
			}
			/**
			 * Store the digits <code>n</code> in %n$ forms.
			 */
			private void setArgPosition() {
				int xPos;
				for (xPos=pos; xPos<fmt.length(); xPos++) {
					if (!Character.isDigit(fmt.charAt(xPos)))
						break;
				}
				if (xPos>pos && xPos<fmt.length()) {
					if (fmt.charAt(xPos)=='$') {
						positionalSpecification = true;
						argumentPosition=
								Integer.parseInt(fmt.substring(pos,xPos));
						pos=xPos+1;
					}
				}
			}
			/**
			 * Store the digits <code>n</code> in *n$ forms.
			 */
			private boolean setFieldWidthArgPosition() {
				boolean ret=false;
				int xPos;
				for (xPos=pos; xPos<fmt.length(); xPos++) {
					if (!Character.isDigit(fmt.charAt(xPos)))
						break;
				}
				if (xPos>pos && xPos<fmt.length()) {
					if (fmt.charAt(xPos)=='$') {
						positionalFieldWidth = true;
						argumentPositionForFieldWidth=
								Integer.parseInt(fmt.substring(pos,xPos));
						pos=xPos+1;
						ret=true;
					}
				}
				return ret;
			}
			/**
			 * Store the digits <code>n</code> in *n$ forms.
			 */
			private boolean setPrecisionArgPosition() {
				boolean ret=false;
				int xPos;
				for (xPos=pos; xPos<fmt.length(); xPos++) {
					if (!Character.isDigit(fmt.charAt(xPos)))
						break;
				}
				if (xPos>pos && xPos<fmt.length()) {
					if (fmt.charAt(xPos)=='$') {
						positionalPrecision = true;
						argumentPositionForPrecision=
								Integer.parseInt(fmt.substring(pos,xPos));
						pos=xPos+1;
						ret=true;
					}
				}
				return ret;
			}
			boolean isPositionalSpecification() {
				return positionalSpecification;
			}
			int getArgumentPosition() { return argumentPosition; }
			boolean isPositionalFieldWidth() {
				return positionalFieldWidth;
			}
			int getArgumentPositionForFieldWidth() {
				return argumentPositionForFieldWidth;
			}
			boolean isPositionalPrecision() {
				return positionalPrecision;
			}
			int getArgumentPositionForPrecision() {
				return argumentPositionForPrecision;
			}
			/**
			 * Set flag characters, one of '-+#0 or a space.
			 */
			private void setFlagCharacters() {
				/* '-+ #0 */
				thousands = false;
				leftJustify = false;
				leadingSign = false;
				leadingSpace = false;
				alternateForm = false;
				leadingZeros = false;
				for ( ; pos < fmt.length(); pos++) {
					char c = fmt.charAt(pos);
					if (c == '\'') thousands = true;
					else if (c == '-') {
						leftJustify = true;
						leadingZeros = false;
					}
					else if (c == '+') {
						leadingSign = true;
						leadingSpace = false;
					}
					else if (c == ' ') {
						if (!leadingSign) leadingSpace = true;
					}
					else if (c == '#') alternateForm = true;
					else if (c == '0') {
						if (!leftJustify) leadingZeros = true;
					}
					else break;
				}
			}
			/**
			 * The integer portion of the result of a decimal
			 * conversion (i, d, u, f, g, or G) will be
			 * formatted with thousands' grouping characters.
			 * For other conversions the flag is ignored.
			 */
			private boolean thousands = false;
			/**
			 * The result of the conversion will be
			 * left-justified within the field.
			 */
			private boolean leftJustify = false;
			/**
			 * The result of a signed conversion will always
			 * begin with a sign (+ or -).
			 */
			private boolean leadingSign = false;
			/**
			 * Flag indicating that left padding with spaces is
			 * specified.
			 */
			private boolean leadingSpace = false;
			/**
			 * For an o conversion, increase the precision to
			 * force the first digit of the result to be a
			 * zero.  For x (or X) conversions, a non-zero
			 * result will have 0x (or 0X) prepended to it.
			 * For e, E, f, g, or G conversions, the result
			 * will always contain a radix character, even if
			 * no digits follow the point.  For g and G
			 * conversions, trailing zeros will not be removed
			 * from the result.
			 */
			private boolean alternateForm = false;
			/**
			 * Flag indicating that left padding with zeroes is
			 * specified.
			 */
			private boolean leadingZeros = false;
			/**
			 * Flag indicating that the field width is *.
			 */
			private boolean variableFieldWidth = false;
			/**
			 * If the converted value has fewer bytes than the
			 * field width, it will be padded with spaces or
			 * zeroes.
			 */
			private int fieldWidth = 0;
			/**
			 * Flag indicating whether or not the field width
			 * has been set.
			 */
			private boolean fieldWidthSet = false;
			/**
			 * The minimum number of digits to appear for the
			 * d, i, o, u, x, or X conversions.  The number of
			 * digits to appear after the radix character for
			 * the e, E, and f conversions.  The maximum number
			 *  of significant digits for the g and G 
			 * conversions.  The maximum number of bytes to be
			 * printed from a string in s and S conversions.
			 */
			private int precision = 0;
			/** Default precision. */
			private final static int defaultDigits=6; 
			/**
			 * Flag indicating that the precision is *.
			 */
			private boolean variablePrecision = false;
			/**
			 * Flag indicating whether or not the precision has
			 * been set.
			 */
			private boolean precisionSet = false;
			/*
			 */
			private boolean positionalSpecification=false;
			private int argumentPosition=0;
			private boolean positionalFieldWidth=false;
			private int argumentPositionForFieldWidth=0;
			private boolean positionalPrecision=false;
			private int argumentPositionForPrecision=0;
			/**
			 * Flag specifying that a following d, i, o, u, x,
			 * or X conversion character applies to a type
			 * short int.
			 */
			private boolean optionalh = false;
			/**
			 * Flag specifying that a following d, i, o, u, x,
			 * or X conversion character applies to a type lont
			 * int argument.
			 */
			private boolean optionall = false;
			/**
			 * Flag specifying that a following e, E, f, g, or
			 * G conversion character applies to a type double
			 * argument.  This is a noop in Java.
			 */
			private boolean optionalL = false;
			/** Control string type. */
			private char conversionCharacter = '\0';
			/**
			 * Position within the control string.  Used by
			 * the constructor.
			 */
			private int pos = 0;
			/** Literal or control format string. */
			private String fmt;
		}
		/** Vector of control strings and format literals. */
		private Vector vFmt = new Vector();
		/** Character position.  Used by the constructor. */
		private int cPos=0;
		/** Character position.  Used by the constructor. */
		private DecimalFormatSymbols dfs=null;
	}

}
