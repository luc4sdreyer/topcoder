public class BuildingTowers {

	public long maxHeight(int N, int K, int[] x, int[] t) {
		int[] h = new int[t.length];
		h[0] = 0;
		long max = 0; 
		for (int i = 1; i < t.length; i++) {
			int y1 = h[i-1] + K * (x[i] - (i > 0 ? x[i-1] : 0));
			if (y1 <= t[i-1]) {
				h[i] = y1;
			} else {
				int x0 = (i > 0 ? x[i-1] : 0);
				int x1 = x[i];
				int y0 = h[i-1];
				y1 = t[i-1];
				int lo = x0;
				int hi = x1;
				while(lo < hi) {
				    int mid = (lo + hi) >> 1;
				    if(f(mid, x0, x1, y0, y1, K) > f(mid+1, x0, x1, y0, y1, K)) {
				        hi = mid;
				    }
				    else {
				        lo = mid+1;
				    }
				}
				max = Math.max(max, f(lo, x0, x1, y0, y1, K));
			}
		}
		for (int i = 0; i < h.length; i++) {
			max = Math.max(max, h[i]);
		}		
		return max;
	}

	private long f(long mid, long x0, long x1, long y0, long y1, long k) {		
		return Math.min((mid - x0) * k + y0, (x1 - mid) * k + y1);
	}
	

}
