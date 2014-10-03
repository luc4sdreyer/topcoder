public class RedAndGreen {	
	public int minPaints(String row) {
		int min = Integer.MAX_VALUE;
		for (int i = -1; i <= row.length(); i++) {
			int cost = 0;
			for (int j = 0; j < row.length(); j++) {
				if (j <= i && row.charAt(j) != 'R') {
					cost++;
				}
				if (j > i && row.charAt(j) != 'G') {
					cost++;
				}
			}
			min = Math.min(min, cost);
		}
		return min;
	}
}
