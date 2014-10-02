class Ex9_2 {
	public static void main(String args[]) {
		for (int i = 0; i < 15; i++) {
			System.out.println(countNumWays(i, i) + " " + (factorial(i*2)/(factorial(i)*factorial(i))));
		}
	}
	
	public static long factorial(int n) {
		if (n == 0) {
			return 1;
		} else if (n == 1) {
			return 1;
		} else {
			return factorial(n-1)*n;
		}
	}

	public static int countNumWays(int xSize, int ySize) {
		if (xSize <= 0 || ySize <= 0) {
			return 0;
		}
		int[][] d = new int[ySize][xSize];

		for (int y = 0; y < ySize; y++) {
			for (int x = 0; x < xSize; x++) {
				if (x == 0 && y == 0) {
					d[y][x] = 1;
				} else if (x == 0) {
					d[y][x] = d[y-1][x];
				} else if (y == 0) {
					d[y][x] = d[y][x-1];
				} else {
					d[y][x] = d[y][x-1] + d[y-1][x];
				}
			}
		}

		return d[ySize-1][xSize-1];
	}
}