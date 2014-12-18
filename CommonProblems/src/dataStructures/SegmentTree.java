package dataStructures;

public class SegmentTree {
	private int[][] t;
	private int[] a;
	private int N;
	private int n;
	
	/**
	 * A segment tree can perform a binary associative function (like sum, min, max) over an interval in O(log n) time. 
	 * Updates are relatively slow, O(log n)
	 */

	/**
	 * This function can be any associative binary function. For example sum, min, max, bitwise and, gcd. 
	 */
	protected int function(int a, int b) {
		return a + b;
	}
	
	/**
	 * The value of IDENTITY should be such that f(IDENTITY, x) = x, e.g. 0 for sum, + ∞ for min, - ∞ for max, and 0 for gcd.
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

	public void set(int x, int v) {
		t[x][0] = a[x] = v;
		for (int y = 1; y <= n; y++) {
			int xx = x-(x&((1<<y)-1));
			t[xx][y] = function(t[xx][y-1], t[xx+(1<<(y-1))][y-1]);
		}
	}

	/**
	 * Get the function over the interval [a, b].
	 */
	public int get(int i, int j) {
		int res = IDENTITY, h = 0; j++;
		while (i+(1<<h) <= j) {
			while ((i&((1<<(h+1))-1)) == 0 && i+(1<<(h+1)) <= j) h++;
			res = function(res, t[i][h]);
			i += (1<<h);
		}
		while (i < j) {
			while (i+(1<<h) > j) h--;
			res = function(res, t[i][h]);
			i += (1<<h);
		}
		return res;
	}
}
