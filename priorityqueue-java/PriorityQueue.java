public class PriorityQueue {

	public int findAnnoyance(String S, int[] a) {
		int sum = 0;
		for (int i = 0; i < a.length; i++) {
			if (S.charAt(i) == 'b') {
				for (int j = 0; j < i; j++) {
					sum += a[j];
				}
			}
		}
		return sum;
	}

}
