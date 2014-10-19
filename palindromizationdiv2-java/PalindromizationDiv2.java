public class PalindromizationDiv2 {

	public int getMinimumCost(int X) {
		int i = 0;
		while (!isPal(X + i) && !isPal(X - i)) {
			i++;
		}
		return i;
	}

	private boolean isPal(int i) {
		if (i < 0) {
			return false;
		}
		String s = String.valueOf(i);
		for (int j = 0; j <= s.length()/2; j++) {
			if (s.charAt(j) != s.charAt(s.length()-1-j)) {
				return false;
			}
		}
		return true;
	}

}
