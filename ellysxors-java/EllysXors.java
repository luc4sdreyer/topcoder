public class EllysXors {

	public long getXor(long L, long R) {
		boolean[] left = new boolean[64];
		for (int i = 0; i < left.length; i++) {
			if (((L >> (long)i) & 1) != 0) {
				left[i] = true;
			}
		}
		boolean[] right = new boolean[64];
		for (int i = 0; i < right.length; i++) {
			if (((R >> (long)i) & 1) != 0) {
				right[i] = true;
			}
		}
		
		boolean[] sum = new boolean[64];
		for (int i = 0; i < left.length; i++) {
			if (i == 0) {
				long dist = (R-L);
				if (L % 2 == 1) {
					dist++;
				}
				dist = dist % 4;
				if (dist == 1 || dist == 2) {
					sum[i] = true;
				}
			} else {
				if (!left[i] && !right[i]) {
					sum[i] = false;
				} else if (!left[i] && right[i]) {
					sum[i] = (R+1) % 2 == 1 ? true : false;
				} else if (left[i] && !right[i]) {
					if (R % 2 == 0) {
						sum[i] = false;
					} else {
						sum[i] = (R+1) % 2 == 1 ? true : false;
					}
					if (L % 2 == 1) {
						sum[i] = sum[i] ? false: true; 
					}
				} else {
					sum[i] = (R+1) % 2 == 1 ? true : false;
					if (L % 2 == 1) {
						sum[i] = sum[i] ? false: true;
					}
				}
			}
		}
		
		long s = 0;
		for (int i = 0; i < right.length; i++) {
			if (sum[i]) {
				s = (s | (1L << (long)i)); 
			}
		}
		return s;
	}
	
	public boolean isBitSet(long x, int n) {
		if (((x >> n) & 1) != 0) {
			return true;
		}
		return false;
	}
	

}
