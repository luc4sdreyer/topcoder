public class ShippingCubes {

	public int minimalCost(int N) {
		int min = Integer.MAX_VALUE;
		for (int a = 1; a <= N; a++) {
			if (N % a == 0) {
				int B = N/a;
				for (int b = 1; b <= B; b++) {
					if (B % b == 0) {
						int C = B/b;
						for (int c = 1; c <= C; c++) {
							if (C % c == 0 && a*b*c == N) {
								min = Math.min(min, a+b+c);
							}
						}
					}
				}
			}
		}
		return min;
	}

}
