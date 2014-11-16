public class FoxPaintingBalls {

	public long theMax(long R, long G, long B, int N) {
		if (N == 1) {
			return R+G+B;
		}
		long size = N*(N+1L)/6L;
		if (N % 3 != 1) {
			long min = min(R,G,B) / size;
			return min;
		} else {
			long low = 0;
			long high = R+G+B;
			long mid = 0;
			long best = 0;
			while (low <= high) {
				mid = (low + high)/2L;
				if (check(mid, R, G, B, N, size)) {
					best = mid;
					low = mid+1L;
				} else {
					high = mid-1L;
				}
			}
			return best;
		}
	}
	
	public boolean check(long pyramids, long R, long G, long B, long N, long size) {
		if ((R+G+B)/(1L+3L*size) >= pyramids && min(R, G, B) / size >= pyramids) {
			return true;
		}
		return false;
	}
	
	
	public long min(long a, long b, long c) {
		return Math.min(Math.min(a, b), c);
	}
	public long max(long a, long b, long c) {
		return Math.max(Math.max(a, b), c);
	}
	public long mid(long a, long b, long c) {
		return Math.min(Math.max(a, b), c);
	}

}
