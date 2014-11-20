import java.util.Arrays;

public class EllysSubstringSorter {

	public String getMin(String S, int L) {
		String best = null;
		for (int i = 0; i < S.length()-L+1; i++) {
			char[] t = S.substring(i, i+L).toCharArray();
			Arrays.sort(t);
			String temp = S.substring(0, i) + new String(t) + S.substring(i+L);
			if (best == null) {
				best = temp;
			} else if (temp.compareTo(best) < 0) {
				best = temp;
			}
		}
		return best;
	}

}
