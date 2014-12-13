public class CandyMaking {
	
	public static double EPS = 1e-20;

	public double findSuitableDensity(int[] containerVolume, int[] desiredWeight) {
		double low = 0.0;
		double high = Long.MAX_VALUE;
		double best = low;
		for (int i = 0; i < 1000000; i++) {
			double step = (high-low)/3;
			double[] dens = {low+step, low+step*2};
			double[] diff = {0,0};
			for (int j = 0; j < diff.length; j++) {
				diff[j] = sumOfDiff(containerVolume, desiredWeight, dens[j]);
			}
			if (Math.abs(diff[0] - diff[1]) < EPS) {
				best = (dens[0]+dens[1])/2;
			} else if (diff[0] > diff[1]) {
				low = dens[0];
			} else {
				high = dens[1];
			}
		}
		
		return sumOfDiff(containerVolume, desiredWeight, best);
	}

	private double sumOfDiff(int[] containerVolume, int[] desiredWeight, double density) {
		double sum = 0;
		for (int i = 0; i < desiredWeight.length; i++) {
			sum += Math.abs(containerVolume[i] * density - desiredWeight[i]);
		}
		return sum;
	}

}
