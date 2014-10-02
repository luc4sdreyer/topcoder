class Ex9_1 {
	public static void main(String args[]) {
		for (int i = 0; i < 15; i++) {
			System.out.println(countNumWays(i));
		}
	}

	public static int countNumWays(int n) {
		int[] positions = new int[Math.max(3, n+1)];
		positions[0] = 1;
		positions[1] = 1;
		positions[2] = 2;

		for (int i = 3; i <= n; i++) {
			positions[i] = positions[i-1] + positions[i-2] + positions[i-3];
		}

		return positions[n];
	}
}