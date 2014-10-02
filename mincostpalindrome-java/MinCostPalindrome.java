public class MinCostPalindrome {

	public int getMinimum(String str, int oCost, int xCost) {
		char[] s = str.toCharArray();
		int len = str.length() - 1;
		
		int minCost = 0;
		for (int i = 0; i < s.length/2; i++) {
			if (s[i] == '?' && s[len - i] == '?') {
				minCost += Math.min(oCost, xCost)*2;
				if (oCost <= xCost) {
					s[i] = 'o';
					s[len - i] = 'o';
				} else {
					s[i] = 'x';
					s[len - i] = 'x';					
				}
			} else if (s[i] == '?') {
				char c = s[len - i];
				if (c == 'o') {
					minCost += oCost;
					s[i] = 'o';
				} else {
					minCost += xCost;
					s[i] = 'x';
				}
			} else if (s[len - i] == '?') {
				char c = s[i];
				if (c == 'o') {
					minCost += oCost;
					s[len - i] = 'o';
				} else {
					minCost += xCost;
					s[len - i] = 'x';
				}
			}
		}
		
		boolean isPal = true;
		for (int i = 0; i < s.length/2; i++) {
			if (s[i] != s[len - i]) {
				isPal = false;
			}
		}
		
		if (!isPal) {
			return -1;
		}
		
		return minCost;
	}

}
