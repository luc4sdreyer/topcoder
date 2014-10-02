public class BadNeighbors {

	public int maxDonations(int[] donations) {
		int[] bestSolution = new int[donations.length];
		int[] minIdx = new int[donations.length];
		int[] maxIdx = new int[donations.length];
		int max = 0;
		
		for (int n = 0; n < donations.length; n++) {
			minIdx[n] = donations.length-1;
		}
		for (int n = 0; n < donations.length; n++) {
			bestSolution[n] = donations[n];
			//minIdx[n] = n;
			//maxIdx[n] = n;
			for (int t = 0; t < n; t++) {
				if (n > maxIdx[t]+1
						&& !(n == donations.length-1 && minIdx[t] == 0)
						&& bestSolution[t] + donations[n] > bestSolution[n]) {
					bestSolution[n] = bestSolution[t] + donations[n];
					minIdx[n] = Math.min(minIdx[n], t);
					maxIdx[n] = Math.max(maxIdx[n], n);
				}
			}
			if (bestSolution[n] > max) {
				max = bestSolution[n];
			}
		}
		return max;
	}

}
