public class SilverbachConjecture {

	public int[] solve(int n) {
		for (int i = 4; i < n/2; i++) {
			int a = i;
			int b = n-a;
			boolean comp1 = false;
			for (int j = 2; j < a; j++) {
				if (a % j == 0) {
					comp1 = true;
					break;
				}
			}
			boolean comp2 = false;
			for (int j = 2; j < b; j++) {
				if (b % j == 0) {
					comp2 = true;
					break;
				}
			}
			if (comp1 && comp2) {
				int[] r = new int[2];
				r[0] = a;
				r[1] = b;
				System.out.println(r[0] + " " + r[1]);
				return r;
			}
		}
		return null;
	}

}
