public class ManySquares {

	public int howManySquares(int[] sticks) {
		int sum = 0;
		int[] numSticks = new int[1001];
		for (int i = 0; i < sticks.length; i++) {
			numSticks[sticks[i]]++;
		}
		for (int i = 0; i < numSticks.length; i++) {
			sum += numSticks[i]/4;
		}
		return sum;
	}

}
