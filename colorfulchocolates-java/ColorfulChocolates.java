import java.util.HashSet;

public class ColorfulChocolates {

	public int maximumSpread(String c, int maxSwaps) {
		HashSet<Character> colors = new HashSet<Character>();
		char[] chocolates = c.toCharArray();
		for (int i = 0; i < chocolates.length; i++) {
			colors.add(chocolates[i]);
		}
		
		int max = 0;
		for (char color : chocolates) {
			for (int start = 0; start <= chocolates.length; start++) {
				int[] cost = new int[chocolates.length];
				int value = 0;
				for (int i = start; i < cost.length; i++) {
					cost[i] = value;
					if (chocolates[i] != color) {
						value++;
					}
				}
				value = 0;
				for (int i = start-1; i >= 0; i--) {
					cost[i] = value;
					if (chocolates[i] != color) {
						value++;
					}
				}
				int left = start-1;
				int right = start;
				int swaps = maxSwaps;
				int spread = 0;
				while (true) {
					int leftVal = Integer.MAX_VALUE;
					int rightVal = Integer.MAX_VALUE;
					while (left >= 0 && chocolates[left] != color) {
						left--;
					}
					if (left >= 0) {
						leftVal = cost[left];
					}
					while (right < cost.length && chocolates[right] != color) {
						right++;
					}
					if (right < cost.length) {
						rightVal = cost[right];
					}
					
					if (leftVal == rightVal && leftVal == Integer.MAX_VALUE) {
						break;
					}
					
					int best = 0;
					if (rightVal <= leftVal) {
						best = rightVal;
						right++;
					} else {
						best = leftVal;
						left--;
					}
					if (best > swaps) {
						break;
					}
					spread++;
					swaps -= best;					
				}
				max = Math.max(max, spread);
			}
		}
		return max;
	} 
}