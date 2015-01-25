class Knapsack {
	public static void main(String[] args) {
		int[][] boxes = new int[1000][2];
		for (int i = 0; i < boxes.length; i++) {
			boxes[i][0] = (int)(Math.random()*1000000); //value
			boxes[i][1] = (int)(Math.random()*100);		//weight
		}
		int maxW = 1000000;
		findMaxByWeight(boxes, maxW);
	}

	/**
	 * The Knapsack problem can be solved in O(nW) time if the weights are non-negative integers!
	 * n = number of boxes
	 * W = the maximum weight
	 * 
	 * Define M(j): the maximum value one can pack into a knapsack of capacity j.
	 * M(j) = max(M(j-1), max[over i items](M(j - si) + vi))
	 * 
	 * @param boxes
	 * @param maxW
	 */
	public static void findMaxByWeight(int[][] boxes, int maxW) {
		long[] bestValue = new long[maxW+1];
		for (int w = 0; w < bestValue.length; w++) {
			for (int i = 0; i < boxes.length; i++) {
				if (w - boxes[i][1] >= 0) {
					bestValue[w] = Math.max(w > 0 ? bestValue[w-1] : 0, bestValue[w - boxes[i][1]] + boxes[i][0]);
				}
			}
		}
		System.out.println(bestValue[bestValue.length-1]);
	}
	
	public static void findMaxByValue(int[][] boxes, int maxW) {
		int maxValue = 0;
		for (int i = 0; i < boxes.length; i++) {
			maxValue = Math.max(maxValue, boxes[i][0]);
		}
		long[] bestValue = new long[maxW+1];
		for (int w = 0; w < bestValue.length; w++) {
			for (int i = 0; i < boxes.length; i++) {
				if (w - boxes[i][1] >= 0 && bestValue[w - boxes[i][1]] + boxes[i][0] > bestValue[w]) {
					bestValue[w] = bestValue[w - boxes[i][1]] + boxes[i][0];
				}
			}
		}
		System.out.println(bestValue[bestValue.length-1]);
	}
}