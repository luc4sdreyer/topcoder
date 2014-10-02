public class PairingPawns {

	public int savedPawnCount(int[] start) {
		long[] s = new long[start.length];
		for (int i = 0; i < s.length; i++) {
			s[i] = start[i];
		}
		for (int i = s.length -1; i >= 1; i--) {
			s[i - 1] += s[i]/2L;
		}
		return (int) s[0];
	}

}
