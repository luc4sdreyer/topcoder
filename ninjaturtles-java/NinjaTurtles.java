public class NinjaTurtles {

	public int countOpponents(int P, int K) {
		for (int n = 0; n < 10000000; n++) {
			if (n/3 + (int)3*Math.floor((double)n/K) == P) {
				return n;
			}
		}
		return -1;
	}

}
