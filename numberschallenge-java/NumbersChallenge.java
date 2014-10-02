public class NumbersChallenge {

	public int MinNumber(int[] S) {
		boolean[] all = new boolean[2100000];
		int[] t1 = new int[16];
		int[] t2 = new int[16];
		int n = 1 << S.length;
		for (int i = 0; i < n; i++) {
			int sum = 0;
			int sum2 = 0;
			for (int j = 0; j < S.length; j++) {
				if ((i >> j) % 2 == 1) {
					sum += S[j];
				}
				if (((1 << j) & i) > 0) {
					sum2 += S[j];
				}
			}
			t1[i] = sum;
			t2[i] = sum2;
			all[sum] = true;
		}
		
		for (int i = 0; i < all.length; i++) {
			if (all[i] == false) {
				return i;
			}
		}
		return all.length;
	}

}
