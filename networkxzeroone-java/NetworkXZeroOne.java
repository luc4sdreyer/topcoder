public class NetworkXZeroOne {

	public String reconstruct(String message) {
		char[] m = message.toCharArray();
		int p = 0;
		for (int i = 0; i < m.length; i++) {
			if (m[i] != '?') {
				if ((i % 2 == p && m[i] == 'x') || (i % 2 != p && m[i] == 'o')) {
					break;
				} else {
					p = 1;
					break;
				}
			}
		}
		for (int i = 0; i < m.length; i++) {
			if (m[i] == '?') {
				if (i % 2 == p) {
					m[i] = 'x';
				} else {
					m[i] = 'o';
				}
			}
		}
		return new String(m);
	}

}
