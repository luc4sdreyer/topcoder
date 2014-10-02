public class P8XMatrixTransformation {

	public String solve(String[] original, String[] target) {
		int n1 = 0;
		for (int i = 0; i < target.length; i++) {
			for (int j = 0; j < target[0].length(); j++) {
				if (target[i].charAt(j) == '1') {
					n1++;
				}
			}
		}
		int n2 = 0;
		for (int i = 0; i < original.length; i++) {
			for (int j = 0; j < original[0].length(); j++) {
				if (original[i].charAt(j) == '1') {
					n2++;
				}
			}
		}
		return n1 == n2 ? "YES" : "NO";
	}

}
