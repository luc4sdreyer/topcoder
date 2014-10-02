import java.util.Arrays;


public class Ex_11_1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int[] A = {1,4,6,8,8,0,0,0,0};
		int[] B = {1,4,7,8};
		sortArrays(A, B);
		System.out.println(Arrays.toString(A));

	}

	public static void sortArrays(int[] A, int[] B) {
		int aIdx = A.length - 1 - B.length;
		int bIdx = B.length - 1;
		int current = A.length - 1;

		while (aIdx >= 0 && bIdx >= 0) {
			if (A[aIdx] > B[bIdx]) {
				A[current--] = A[aIdx--];
			} else {
				A[current--] = B[bIdx--];
			}
		}

		for (int i = 0; i <= bIdx; i++) {
			A[i] = B[i];
		}
	}

}
