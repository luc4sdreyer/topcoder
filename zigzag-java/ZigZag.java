public class ZigZag {

	public int longestZigZag(int[] sequence) {
		int[] longest = new int[sequence.length];
		int[] diff = new int[sequence.length];
		int max = 0;
		for (int n = 0; n < sequence.length; n++) {
			longest[n] = 1;
			for (int t = 0; t < n; t++) {
				if (((diff[t] == 0 && sequence[n] != sequence[t])
						|| (diff[t] == 1 && sequence[n] < sequence[t])
						|| (diff[t] == 2 && sequence[n] > sequence[t]))
						&& longest[t] + 1 > longest[n]) {
					longest[n] = longest[t] + 1;
					if (sequence[n] > sequence[t]) {
						diff[n] = 1;
					} else if (sequence[n] < sequence[t]) {
						diff[n] = 2;
					} else if (sequence[n] == sequence[t]) {
						diff[n] = 3;
					}
				}
			}
			if (longest[n] > max) {
				max = longest[n];
			}
		}
		
		return max;
	}

}
