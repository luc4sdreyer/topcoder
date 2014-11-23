import java.util.HashSet;

public class StrIIRec {

	public String recovstr(int n, int minInv, String minStr) {
		String min = minStr;
		for (int i = 0; i < n - minStr.length(); i++) {
			int j = 0;
			String nextLetter = "" + (char)('a' + j);
			while (min.contains(nextLetter)) {
				nextLetter = "" + (char)('a' + j);
				j++;
			}
			min += nextLetter;
		}

		char[] m = min.toCharArray();
		while (true) {
			int need = minInv - countInv(m);
			int total = 0;
			boolean restart = false;
			for (int i = 0; i < m.length; i++) {
				total += i;
				if (total >= need) {
					if (i == 0) {
						return new String(m);
					}
					buildNewString(m, i);
					// restart
					i = m.length;
					restart = true;
					break;
				}
			}
			if (!restart) {
				if (total >= need) {
					buildNewString(m, m.length-1);
				}
				break;
			}
		}
		if (countInv(m) >= minInv) {
			return new String(m);
		}
		return "";
	}

	private void buildNewString(char[] m, int i) {
		HashSet<Character> set = new HashSet<>();
		int backIdx = m.length - i - 1;
		for (int j = backIdx; j < i; j++) {
			set.add(m[j]);
		}
		m[backIdx]++;
		while (set.contains(m[backIdx])) {
			m[backIdx]++;
		}
		set = new HashSet<>();
		for (int j = backIdx; j <= i; j++) {
			set.add(m[j]);
		}
		char next = 'a';
		for (int j = backIdx+1; j < m.length; j++) {
			while (set.contains(next)) {
				next++;
			}
			m[j] = next;
		}
	}

	private int countInv(char[] m) {
		int inv = 0;
		for (int i = 0; i < m.length; i++) {
			for (int j = i+1; j < m.length; j++) {
				if (m[j] < m[i]) {
					inv++;
				}
			}
		}
		return inv;
	}

}
