public class MissingParentheses {

	public int countCorrections(String par) {
		int height = 0;
		int min = Integer.MAX_VALUE;
		char[] p = par.toCharArray();
		for (int i = 0; i < p.length; i++) {
			if (p[i] == '(') {
				height++;
			} else {
				height--;
			}
			min = Math.min(min, height);
		}
		int cost = 0;
		if (min < 0) {
			cost = Math.abs(min);
		}
		
		height += cost;
		
		return Math.abs(height) + cost;
	}

}
