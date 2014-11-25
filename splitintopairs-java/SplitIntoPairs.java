public class SplitIntoPairs {

	public int makepairs(int[] in, int X) {
		long[] A = new long[in.length];
		for (int i = 0; i < A.length; i++) {
			A[i] = in[i];
		}
		int numPos = 0;
		for (int i = 0; i < A.length; i++) {
			if (A[i] >= 0) {
				numPos++;
			}			
		}
		if (numPos % 2 == 0) {
			return A.length/2;
		} else {
			boolean valid = false;
			for (int i = 0; i < A.length; i++) {
				for (int j = 0; j < A.length; j++) {
					if (i != j && A[i] >= 0 && A[j] < 0 && A[i]*A[j] >= (long)X) {
						valid = true;
					}
				}
			}
			if (valid) {
				return A.length/2;
			} else {
				return A.length/2 - 1;
			}
		}
	}

}
