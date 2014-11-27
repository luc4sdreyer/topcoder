public class FoxPaintingBalls {

	public long theMax(long R, long G, long B, int N_) {
		long N = N_;
		long size = N*(N+1L)/2L;
		if (N == 1) {
			return R+G+B;
		} else if (size % 3 == 0) {
			return Math.min(R,Math.min(G,B))*3 / (size);
		} else {
			long low = 0;
			long high = (R+G+B) / size;
			long max = 0;
			while (low <= high) {
				long mid = (low+high)/2;
				if (valid(mid, size, N, R, G, B)) {
					low = mid+1;
					max = Math.max(max, mid);
				} else {
					high = mid-1;
				}
			}
			return max;
		}
	}

	private boolean valid(long sets, long size, long n, long r, long g, long b) {
		long minContrib = (size/3) * sets;
		long rR = r - minContrib;
		long rG = g - minContrib;
		long rB = b - minContrib;
		if (rR >= 0 && rG >= 0 && rB >= 0 && (rR+rG+rB >= sets)) {
			return true;
		}
		return false;
	}

}
