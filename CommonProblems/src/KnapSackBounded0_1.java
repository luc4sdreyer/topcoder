class KnapSackBounded0_1 {
	public static void main(String[] args) {
		int[][] boxes = new int[1000][2];
		int maxW = 100000;
		for (int i = 0; i < boxes.length; i++) {
			boxes[i][0] = (int)(Math.random()*1000); //value
			boxes[i][1] = (int)(Math.random()*1000); //weight
		}
		findMaxByWeight(boxes, maxW);
	}

	/**
	 * The Knapsack problem can be solved in O(nW) time if the weights are non-negative integers!
	 * n = number of boxes
	 * W = the maximum weight
	 * 
	 * Define M(i, j): the optimal value for filling exactly a capacity j knapsack with subset of items [1,i]
	 * M(i, j) = max(M(i-1, j), M(i-1, j - si) + vi)
	 * Optimal objective value: max(M(n, j))
	 * 
	 * @param boxes
	 * @param maxW
	 */
	public static void findMaxByWeight(int[][] boxes, int maxW) {
		long[][] bestValue = new long[boxes.length][maxW+1];
		long max = 0;
		for (int w = 0; w < bestValue.length; w++) {
			for (int i = 0; i < boxes.length; i++) {
				if (w - boxes[i][1] >= 0) {
					bestValue[i][w] = Math.max(
							i > 0 ? bestValue[i-1][w] : 0,
							i > 0 ? bestValue[i-1][w - boxes[i][1]] + boxes[i][0] : 0);
				}
				max = Math.max(max, bestValue[i][w]);
			}
		}
		System.out.println(max);
	}
}