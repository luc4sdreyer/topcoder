public class Cut {

	public int getMaximum(int[] eelLengths, int maxCuts) {
		int numSegments = 0;
		while (true) {
			int bestIdx = nextBestIdx(eelLengths);
			if (eelLengths[bestIdx] >= 10) {
				if (eelLengths[bestIdx] != 10) {
					if (maxCuts < 1) {
						break;
					}
					maxCuts--;
				}
				numSegments++;
				eelLengths[bestIdx] -= 10;
			} else {
				break;
			}
		}
		return numSegments;
	}
	
	public int nextBestIdx(int[] eelLengths) {
		boolean has10 = false;
		for (int i = 0; i < eelLengths.length; i++) {
			if (eelLengths[i] >= 10 && eelLengths[i] % 10 == 0) {
				has10 = true;
				break;
			}
		}
		int best = 0;
		int bestIdx = 0;
		if (has10) {
			best = Integer.MAX_VALUE;
			for (int i = 0; i < eelLengths.length; i++) {
				if (eelLengths[i] >= 10 && eelLengths[i] % 10 == 0 && eelLengths[i] < best) {
					best = eelLengths[i];
					bestIdx = i;
				}
			}
		} else {
			for (int i = 0; i < eelLengths.length; i++) {
				if (eelLengths[i] > best) {
					best = eelLengths[i];
					bestIdx = i;
				}
			}
		}
		return bestIdx;
	} 

}
