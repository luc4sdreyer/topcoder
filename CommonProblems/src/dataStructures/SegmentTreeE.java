package dataStructures;

import java.util.ArrayList;

/**
 * Based on paladin8's segment tree: http://codeforces.com/blog/entry/3327
 */

public class SegmentTreeE<E extends BinaryOperation<E>> {

	private ArrayList<ArrayList<E>> t;
	private ArrayList<E> a;
	private int N;
	private int n;
	private E identity;

	/**
	 * A segment tree can perform a binary associative function (like sum, min, max) over an interval in O(log n) time. 
	 * Updates are relatively slow, O(log n)
	 */

	/**
	 * This function can be any associative binary function. For example sum, min, max, bitwise and, gcd. 
	 */
	protected E function(E e1, E e2) {
		return e1.function(e1, e2);
	}

	public SegmentTreeE(E[] b) {
		n = (int) (Math.log10(b.length)/Math.log10(2))+1;
		N = 1 << n;
		this.a = new ArrayList<E>(N);
		this.identity = b[0].identity();
		for (int i = 0; i < N; i++) {
			if (i < b.length) {
				this.a.add(b[i]);
			} else {
				this.a.add(identity);
			}
		}
		t = new ArrayList<ArrayList<E>>(N);
		for (int x = 0; x < N; x++) {
			ArrayList<E> list = new ArrayList<E>();
			t.add(list);
			for (int i = 0; i < n+1; i++) {
				list.add(identity);
			}
			t.get(x).set(0, a.get(x));
		}
		for (int y = 1; y <= n; y++) {
			for (int x = 0; x < N; x+=(1<<y)) {
				t.get(x).set(y, function(t.get(x).get(y-1), t.get(x+(1<<(y-1))).get(y-1)));
			}
		}
	}

	public void set(int i, E v) {
		this.a.set(i, v);
		t.get(i).set(0, v);
		for (int y = 1; y <= n; y++) {
			int x = i-(i&((1<<y)-1));
			t.get(x).set(y, function(t.get(x).get(y-1), t.get(x+(1<<(y-1))).get(y-1)));
		}
	}

	/**
	 * Get the function over the interval [a, b].
	 */
	public E get(int i, int j) {
		E res = identity;
		int h = 0; j++;
		while (i+(1<<h) <= j) {
			while ((i&((1<<(h+1))-1)) == 0 && i+(1<<(h+1)) <= j) h++;
			res = function(res, t.get(i).get(h));
			i += (1<<h);
		}
		while (i < j) {
			while (i+(1<<h) > j) h--;
			res = function(res, t.get(i).get(h));
			i += (1<<h);
		}
		return res;
	}
}
