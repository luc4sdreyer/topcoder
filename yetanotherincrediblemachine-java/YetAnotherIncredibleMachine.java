public class YetAnotherIncredibleMachine {

	public int countWays(int[] platformMount, int[] platformLength, int[] balls) {
		int[] configurations = new int[platformLength.length];
		for (int p = 0; p < configurations.length; p++) {
			int low = platformMount[p] - platformLength[p];
			int high = platformMount[p] + platformLength[p];
			for (int b = 0; b < balls.length; b++) {
				if (balls[b] < platformMount[p] && balls[b]+1 > low) {
					low = balls[b]+1;
				}
				if (balls[b] > platformMount[p] && balls[b]-1 < high) {
					high = balls[b]-1;
				}
				if (balls[b] == platformMount[p]) {
					return 0;
				}
			}
			configurations[p] = high - low - platformLength[p] + 1;
			if (configurations[p] <= 0) {
				return 0;
			}
		}
		long numCon = 1;
		for (int p = 0; p < configurations.length; p++) {
			numCon = (numCon * configurations[p]) % 1000000009;
		}
		return (int)numCon;
	}

}
