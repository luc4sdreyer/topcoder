public class TheBoringStoreDivTwo {
	
	public int best = 0;
	

	public String find(String J, String B) {
		int max = 0;
		for (int i = 0; i < J.length(); i++) {
			for (int j = i+1; j < J.length(); j++) {
				if (J.charAt(i) == J.charAt(j)) {
					max = Math.max(max, 
						search(J.toCharArray(), B.toCharArray(), i, j, -1, -1, 0));
				}
			}
		}
		System.out.println(max);
		return null;
	}

	private int search(char[] J, char[] B, int a, int b, int c, int d, int max) {
		char ac = '.';
		if (a >= 0 && a < J.length) {
			ac = J[a];
		} else if (c >= 0 && c < B.length) {
			ac = B[c];
		}
		char bc = ',';
		if (b >= 0 && b < J.length) {
			bc = J[b];
		} else if (d >= 0 && d < B.length) {
			bc = B[d];
		}
		if (ac != bc || (b >= 0 && a >= b) || (d >= 0 && c >= d)) {
			return max;
		}
		
		int m = 0;
		if (a >= 0 && b >= 0) {
			for (int i = 0; i < B.length; i++) {
				for (int j = i+1; j < B.length; j++) {
					m = Math.max(m, search(J, B, -1, -1, i, j, max+1));
				}
			}
			m = Math.max(m, search(J, B, a+1, b+1, -1, -1, max+1));
		}
		if ((a >= 0 && b >= 0) || a >= 0) {
			for (int i = 0; i < B.length; i++) {
				m = Math.max(m, search(J, B, a+1, -1, -1, i, max+1));
			}
		}
		if ((a >= 0 && b >= 0) || b >= 0) {
			for (int i = 0; i < B.length; i++) {
				m = Math.max(m, search(J, B, -1, b+1, i, -1, max+1));
			}
		}
		if (a == -1 && b == -1) {
			m = Math.max(m, search(J, B, a, b, c+1, d+1, max+1));
		}
		if (a == -1 && b != -1) {
			m = Math.max(m, search(J, B, a, b+1, c+1, d, max+1));
		}
		if (a != -1 && b == -1) {
			m = Math.max(m, search(J, B, a+1, b, c, d+1, max+1));
		}
		return m;
	}

}
