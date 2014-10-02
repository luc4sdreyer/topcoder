import java.util.Arrays;

public class UnsortedSequence {

	public int[] getUnsorted(int[] s) {
		Arrays.sort(s);
		boolean valid = false;
		for (int i = s.length - 2; i >= 0; i--) {
			if (s[i] < s[i+1]) {
				int temp = s[i];
				s[i] = s[i+1];
				s[i+1] = temp;
				valid = true;
				break;
			}
		}
		if (valid) {
			return s;
		} else {
			return new int[0];
		}
	}

}
