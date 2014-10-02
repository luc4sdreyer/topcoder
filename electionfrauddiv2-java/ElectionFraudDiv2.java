public class ElectionFraudDiv2 {
	public String IsFraudulent(int[] percentages) {
		if (sum(percentages) == 100) {
			return "NO";
		} else {
			if (sum(percentages) < 100) {
				if (sum(percentages) + percentages.length*0.5 > 100) {
					return "NO";
				} else {
					return "YES";
				}
			} else {
				if (sum(percentages) - count(percentages)*0.5 > 100) {
					return "YES";
				} else {
					return "NO";
				}
			}
		}
	}
	
	int sum(int[] s) {
		int sum = 0;
		for (int i = 0; i < s.length; i++) {
			sum += s[i];
		}
		return sum;
	}
	
	int count(int[] s) {
		int c = 0;
		for (int i = 0; i < s.length; i++) {
			if (s[i] > 0) {
				c++;
			}
		}
		return c;
	}

}
