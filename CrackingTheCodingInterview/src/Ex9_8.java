class Ex9_8 {
	public static void main(String args[]) {
		for (int i = 0; i <= 100; i++) {
			System.out.println("i: " + i + " num: " + findNumWays(i, 25));
		}
	}

	public static int findNumWays(int n, int denom) {
		int nextDenom = -1;
		if (denom == 25) {
			nextDenom = 10;
		} else if (denom == 10) {
			nextDenom = 5;
		} else if (denom == 5) {
			nextDenom = 1;
		} else if (denom == 1) {
			nextDenom = 0;
		}

		if (nextDenom == -1 || n <= 0) {
			return 0;
		}

		int num = 0;
		while (n >= 0) {
			if (n == 0) {
				num++;
			} else {
				num += findNumWays(n, nextDenom);
			}
			n -= denom;
		}
		return num;
	}
}