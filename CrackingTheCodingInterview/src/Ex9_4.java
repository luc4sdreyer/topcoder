import java.util.Arrays;

class Ex9_4 {
	public static void main(String args[]) {
		int[] a = {1,2,3,4,5};
		getAllSubsets(a);
	}

	public static void getAllSubsets(int[] set) {
		int n = 1 << set.length;
		for (int i = 0; i < n; i++) {
			int[] subset = new int[set.length];
			for (int j = 0; j < set.length; j++) {
				if ((i & (1 << j)) != 0) {
					subset[j] = set[j];
				} else {
					subset[j] = 0;
				}
			}
			System.out.println(Arrays.toString(subset));
		}
	}
}