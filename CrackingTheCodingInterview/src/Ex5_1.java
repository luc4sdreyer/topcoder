class Ex5_1 {
	public static void main(String args[])  {
		//System.out.println(Integer.toBinaryString(insertNum(Integer.parseInt("10000000000", 2), Integer.parseInt("10011", 2), 2, 6)));
		testBitOperations();
	}

	public static int insertNum(int n, int m, int i, int j) {
		for (int k = i; k <= j; k++) {
			n = updateBit(n, k, getBit(m, k-i) ? 1 : 0);
		}
		return n;
	}


	public static void testBitOperations() {
		int numTests = 10000000;
		int failed = 0;
		for (int i = 0; i < numTests; i++) {
			int randOp = 0;//(int)(Math.random()*4);
			int randNum = (int) ((Math.random()*2 - 1) * Integer.MAX_VALUE);
			int randBitPos = (int)(Math.random()*31);
			int randBit = (int)(Math.random()*2);
			if (randOp == 0) {
				if (getBit(randNum, randBitPos) != getBitAlt(randNum, randBitPos)) {
					failed++;
				}
			} else if (randOp == 1) {
				if (setBit(randNum, randBitPos) != setBitAlt(randNum, randBitPos)) {
					failed++;
				}
			} else if (randOp == 2) {
				if (clearBit(randNum, randBitPos) != clearBitAlt(randNum, randBitPos)) {
					failed++;
				}
			} else if (randOp == 3) {
				if (updateBit(randNum, randBitPos, randBit) != updateBitAlt(randNum, randBitPos, randBit)) {
					failed++;
				}
			}
		}
		System.out.println("failed: " + failed);
	}

	/*public int insertNum(int n, int m, int i, int j) {
		for (int k = i; k <= j; k++) {
			if (getBit())
		}
	}*/

	public static boolean getBitAlt(int x, int n) {
		String s = Integer.toBinaryString(x);
		while (s.length() < 31) {
			s = "0" + s;  
		}
		if (s.charAt(s.length() - n - 1) == '1') {
			return true;
		} else {
			return false;
		}
	}

	public static int setBitAlt(int x, int n) {
		return updateBitAlt(x, n, 1);
	}

	public static int clearBitAlt(int x, int n) {
		return updateBitAlt(x, n, 0);
	}

	public static int updateBitAlt(int x, int n, int v) {
		String temp = Integer.toBinaryString(x);
		while (temp.length() < 31) {
			temp = "0" + temp;  
		}
		char[] s = temp.toCharArray();
		s[s.length - n - 1] = (char)(v + '0');
		if (x < 0) {
			s[0] = '0';
		}
		temp = new String(s);
		if (x < 0) {
			temp = "-" + temp;
		}
		return Integer.parseInt(temp, 2);
	}

	public static boolean getBit(int x, int n) {
		return ((x & (1 << n)) != 0);
	}

	public static int setBit(int x, int n) {
		return (x | (1 << n));
	}

	public static int clearBit(int x, int n) {
		int mask = ~(1 << n);
		return (mask & x);
	}

	public static int updateBit(int x, int n, int v) {
		int mask = ~(1 << n);
		return ((x & mask) | (v << n));
	}
}