public class FixedDiceGameDiv2 {

	public double getExpectation(int a, int b) {
		long totalWonEvents = 0;
		long totalDiceValue = 0;
		for (int i = 1; i <= a; i++) {
			for (int j = 1; j <= b; j++) {
				totalDiceValue += i * Math.min(i - 1, b);
				totalWonEvents += Math.min(i - 1, b);
			}
		}
		return totalDiceValue/(double)totalWonEvents;
	}

}
