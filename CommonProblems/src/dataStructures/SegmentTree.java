package dataStructures;

/**
 * Based on paladin8's segment tree: http://codeforces.com/blog/entry/3327
 */

public class SegmentTree {
	private int[][] t;
	private int[] a;
	private int N;
	private int n;
	
	/**
	 * A segment tree can perform a binary associative function (like sum, min, max) over an interval in O(log n) time. 
	 * Updates are relatively slow, O(log n)
	 * 
	 * t[width == N][height == n] is essentially a binary tree structure with the root at t[0][n], children at t[0][n-1] and t[N/2][n-1], and so forth. 
	 */

	/**
	 * This function can be any associative binary function. For example sum, min, max, bitwise and, gcd. 
	 */
	protected int function(int a, int b) {
		return a + b;
	}
	
	/**
	 * The value of IDENTITY should be such that f(IDENTITY, x) = x, e.g. 0 for sum, +infinity for min, -infinity for max, and 0 for gcd.
	 */
	protected int IDENTITY = 0;

	public SegmentTree(int[] b) {
		n = (int) (Math.log10(b.length)/Math.log10(2))+1;
		N = 1 << n;
		this.a = new int[N];
		for (int i = 0; i < b.length; i++) {
			this.a[i] = b[i];
		}
		t = new int[N][n+1];
		for (int x = 0; x < N; x++) {
			t[x][0] = a[x];
		}
		for (int y = 1; y <= n; y++) {
			for (int x = 0; x < N; x+=(1<<y)) {
				t[x][y] = function(t[x][y-1], t[x+(1<<(y-1))][y-1]);
			}
		}
	}

	/**
	 * Set position i to v. Time: O(log n)
	 */
	public void set(int i, int v) {
		t[i][0] = a[i] = v;
		for (int y = 1; y <= n; y++) {
			int x = i-(i&((1<<y)-1));
			int old = t[x][y]; 
			t[x][y] = function(t[x][y-1], t[x+(1<<(y-1))][y-1]);
		}
	}

	/**
	 * Get the function over the interval [a, b]. Time: O(log n)
	 */
	public int get(int i, int j) {
		int res = IDENTITY, height = 0; j++;
		while (i+(1<<height) <= j) {
			while ((i&((1<<(height+1))-1)) == 0 && i+(1<<(height+1)) <= j) height++;
			res = function(res, t[i][height]);
			i += (1<<height);
		}
		while (i < j) {
			while (i+(1<<height) > j) height--;
			res = function(res, t[i][height]);
			i += (1<<height);
		}
		return res;
	}

	/**
	 * Find the point x where the function over [0, x] == v, assuming the function (not the underlying array!) is nondecreasing. Time: O(log n)
	 */
	public int search(int v) {
		int x = 0;
		int value = v;
		if (value > t[0][n]) {
			return -(search(t[0][n]) + 1) -1;
		}
		int height = n-1;
		while (height >= 0 && x >= 0 && x < N) {
			int left = t[x][height];
			int right = left + (x + (1 << (height)) < N ? t[x + (1 << (height))][height] : 0);
			if (value <= left) {
				// go left
				if (height == 0) {
					value -= left;
				}
			} else {
				// go right
				if (height == 0) {
					value -= right;
				} else {
					value -= left;
				}
				x += (1 << (height));
			} 
			height--;
		}
		if (value == 0) {
			return x;
		} else {
			return -1 * x - 1;
		}
	}

	/**
	 * Find the first point b where the function over [a, b] == v, assuming the function (not the underlying array!) is nondecreasing. Time: O(log n)^2
	 */
	public int search2(int a, int v) {
		int left = a;
		int right = N-1;
		int mid = 0;
		int f = 0;
		int best = -1;
		if (v > t[0][n]) {
			return -(search2(0, t[0][n]) + 1) -1;
		}
		boolean found = false;
		while (left <= right) {
			mid = (left + right) / 2;
			f = get(a, mid);
			if (f == v) {
				found = true;
				best = mid;
				right = mid-1;
			} else if (f < v) {
				left = mid+1;
			} else {
				if (best+1 <= mid) {
					if (get(best+1, mid) != 0) {
						best = mid;
					}
				} else {
					best = mid;
				}
				right = mid-1;
			}
		}
		if (found) {
			return best;
		} else {
			return -best - 1;
		}
	}
}
