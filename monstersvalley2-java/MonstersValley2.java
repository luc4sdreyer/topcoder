public class MonstersValley2 {
	public final static int MAX = 100;
	public int minimumPrice(int[] dread, int[] price) {
		int min = MAX;
		int N = 1 << dread.length;
		for (int n = 0; n < N; n++) {
			boolean[] bribe = new boolean[dread.length];
			for (int i = 0; i < dread.length; i++) {
				if (((1 << i) & n) != 0) {
					bribe[i] = true;
				}
			}
			min = Math.min(min, cost(bribe, dread, price));
		}
		return min;
	}

	private int cost(boolean[] bribe, int[] dread, int[] price) {
		long power = 0;
		int cost = 0;
		for (int i = 0; i < price.length; i++) {
			if (bribe[i]) {
				cost += price[i];
				power += dread[i];
			} else if (dread[i] > power) {
				return MAX;
			}
		}
		return cost;
	}

}
