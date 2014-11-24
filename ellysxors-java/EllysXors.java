public class EllysXors {

	public long getXor(long L, long R) {		
		return getX(L-1) ^ getX(R);
	}
	public long getX(long L) {
		long a = L % 4;
		if (a == 0) {
			return L;
		} else if (a == 1) {
			return 1;
		} else if (a == 2) {
			return L+1;
		} else {
			return 0;
		}
	}

}
