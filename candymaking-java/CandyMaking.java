public class CandyMaking {

	public double findSuitableDensity(int[] containerVolume, int[] desiredWeight) {
		double min = desiredWeight[0]/containerVolume[0];
		double max = desiredWeight[0]/containerVolume[0];
		for (int i = 0; i < desiredWeight.length; i++) {
			min = Math.min(min, desiredWeight[i]/containerVolume[i]);
			max = Math.max(max, desiredWeight[i]/containerVolume[i]);
		}
		max *= 10;
		
		System.out.println(max);
		System.out.println(min);
		
		int intervals = 100;
		double step = (max - min)/intervals;
		double ret = 0;
		
		for (int j = 0; j < 1000; j++) {
			
			double lastStep = min;
			double best = getSumOfDiff(lastStep, containerVolume, desiredWeight);
			for (int i = 0; i <= intervals; i++) {
				lastStep = i*step + min;
				if (getSumOfDiff(lastStep, containerVolume, desiredWeight) > best) {
					break;
				}
			}
			
			max = lastStep;
			min = lastStep - step;
			step = (max - min)/intervals;
			ret = (max + min)/2;
		}
		
		return getSumOfDiff(ret, containerVolume, desiredWeight);
	}
	
	public double getSumOfDiff(double density, int[] containerVolume, int[] desiredWeight) {
		double diff = 0;
		for (int i = 0; i < desiredWeight.length; i++) {
			diff += Math.abs(containerVolume[i] * density - desiredWeight[i]);
		}
		return diff;
	}
	
}
