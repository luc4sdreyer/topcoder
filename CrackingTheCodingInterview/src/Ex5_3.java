class Ex5_3 {
	public static void main(String args[]) {
		getNextBinaryNumber(123);
	}

	public static void getNextBinaryNumber(int n) {
		int numOneBits = countOneBits(n);
		int next = -1;
		for (int i = n+1; i <= Integer.MAX_VALUE; i++) {
			if (countOneBits(i) == numOneBits) {
				next = i;
				break;
			}
		}
		if (next == -1) {
			//fail
		}
		System.out.println("next: " + next);
		int prev = -1;
		for (int i = n-1; i >= 0; i--) {
			if (countOneBits(i) == numOneBits) {
				prev = i;
				break;
			}
		}
		if (prev == -1) {
			//fail
		}
		System.out.println("prev: " + prev);
	}

	public static int countOneBits(int n) {
		int c = 0;
		for (int i = 0; i < 32; i++) {
			if ((n & (1 << i)) != 0) {
				c++;
			}
		}
		return c;
	}
}