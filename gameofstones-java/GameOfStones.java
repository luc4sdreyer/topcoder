public class GameOfStones {

	public int count(int[] stones) {
		int sum = 0;
		for (int i = 0; i < stones.length; i++) {
			sum += stones[i];
		}
		if (sum % stones.length != 0) {
			return -1;
		}
		int avg = sum / stones.length;
		int steps = 0;
		for (int i = 0; i < stones.length; i++) {
			if (Math.abs(stones[i] - avg) % 2 != 0) {
				return -1;
			}
			steps += Math.abs(stones[i] - avg) / 2;
		}
		return steps/2;
	}

}
