public class ANDEquation {

	public int restoreY(int[] A) {
		for (int i = 0; i < A.length; i++) {
			int sum = A[0];
			if (i == 0) {
				sum = A[1];
			}
			for (int j = 1; j < A.length; j++) {
				if (i != j) {
					sum = sum & A[j];
				}
			}
			if (sum == A[i]) {
				return sum; 
			}
		}
		return -1;
	}

}
