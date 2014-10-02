class Ex5_7 {
	public static void main(String args[]) {
		int[] a = new int[22];
		int offset = 0;
		for (int i = 0; i < a.length; i++) {
			if (i == 9) {
				offset = 1;
			}
			a[i] = i + offset;
			
		}
		System.out.println(findMissing(a));
		
	}

	public static int findMissing(int[] a) {
		int n = a.length;
		int targetSum = n*(n+1)/2;

		int missing = 0;

		for (int j = 1; j <= n; j = j << 1) {
			int sum = 0;
			for (int i = missing; i < a.length; i += j) {
				sum += a[i] & j;
			}
			
			if (((targetSum & j) != 0)
			 != ((sum       & j) != 0)) {
				missing = missing | j;
			}
		}

		return missing;
	}
}