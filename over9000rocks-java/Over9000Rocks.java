
public class Over9000Rocks {

	public int countPossibilities(int[] lowerBound, int[] upperBound) {
		short[] a = new short[15000002];
		int N = 1 << lowerBound.length;
		for (int n = 1; n < N; n++) {
			int minLimit = 0;
			int maxLimit = 0;
			for (int i = 0; i < lowerBound.length; i++) {
				if ((n >> i) % 2 == 1) { // Or equally: (((1 << j) & i) > 0)
					minLimit += lowerBound[i];
					maxLimit += upperBound[i];
				}
			}
			minLimit = Math.max(minLimit, 9001);
			a[minLimit]++;
			a[maxLimit+1]--;
		}
		int c = 0;
		int open = 0;
		for (int i = 0; i < a.length; i++) {
			open += a[i];
			if (open > 0) {
				c++;
			}
		}
		return c;
	}

}
