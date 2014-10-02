public class P8XGraphBuilder {

	public int solve(int[] scores) {
		return solve(scores, 1, scores.length);
	}
	
	public int solve(int[] scores, int unprocessed, int remaining) {
		int max = -1000000;
		if (unprocessed == 0 && remaining == 0) {
			return 0;
		}
		if (unprocessed == 0 || remaining == 0) {
			return max;
		}
		for (int i = 0; i < remaining; i++) {
			max = Math.max(max, scores[i] + solve(scores, unprocessed+i-1, remaining - i));
		}
		return max;
	}

}
