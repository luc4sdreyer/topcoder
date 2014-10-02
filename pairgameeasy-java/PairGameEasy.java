public class PairGameEasy {

	public String able(int a, int b, int c, int d) {
		while (c >= a && d >= b) {
			if (c > d) {
				c = c % d;
			} else {
				d = d % c;
			}
			if (c == 0) {
				c = a;
			}
			if (d == 0) {
				d = b;
			}
			if (a == c && b == d) {
				return "Able to generate";
			}
		}
		
		return "Not able to generate";
	}

}
