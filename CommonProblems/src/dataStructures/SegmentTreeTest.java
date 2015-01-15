package dataStructures;

import static org.junit.Assert.*;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Random;

import org.junit.Test;

import dataStructures.SegmentTreeHolder.BinaryOperation;
import dataStructures.SegmentTreeHolder.SegmentTree;
import dataStructures.SegmentTreeHolder.SegmentTreeE;

public class SegmentTreeTest {
	
	public static class SegmentTreeMaximum extends SegmentTree {
		public SegmentTreeMaximum(int[] a) {
			super(a);
			super.IDENTITY = Integer.MIN_VALUE;
		}
		
		protected int function(int a, int b) {
			return Math.max(a, b);
		}
	}
	
	public static class SegmentTreeRangeMinimumQuery extends SegmentTree {
		public SegmentTreeRangeMinimumQuery(int[] a) {
			super(a);
			super.IDENTITY = Integer.MAX_VALUE;
		}
		
		protected int function(int a, int b) {
			return Math.min(a, b);
		}
	}
	
	public static class SegmentTreeGCD extends SegmentTree {
		public SegmentTreeGCD(int[] a) {
			super(a);
			super.IDENTITY = 0;
		}
		
		protected int function(int a, int b) {
			return (int) BigInteger.valueOf(a).gcd(BigInteger.valueOf(b)).longValue();
		}
	}
	
	public static class SegmentTreeBitwiseAnd extends SegmentTree {
		public SegmentTreeBitwiseAnd(int[] a) {
			super(a);
			super.IDENTITY = Integer.MAX_VALUE;
		}
		
		protected int function(int a, int b) {
			return a & b;
		}
	}

	@Test
	public void testAddition() {
		int maxLength = 100;
		int maxValue = 1000000;
		int numTests = 1000;
		Random rand = new Random(0);
		for (int j = 1; j <= numTests; j++) {
			int len = rand.nextInt(maxLength)+1;
			int[] a = new int[len];
			for (int i = 0; i < len; i++) {
				a[i] = rand.nextInt(maxValue) - maxValue/2;
			}
			SegmentTree st = new SegmentTree(a);
			
			for (int i = 0; i < numTests; i++) {
				int low = rand.nextInt(len);
				int high = rand.nextInt(len-low);
				
				int expected = 0;
				for (int k = low; k <= high; k++) {
					expected += a[k];
				}
				
				int actual = st.get(low, high);
				assertEquals(expected, actual);
				
				int change = rand.nextInt(maxValue);
				a[low] = change;
				st.set(low, change);
			}
		}
	}

	@Test
	public void testGcd() {
		int maxLength = 100;
		int maxValue = 1000;
		int numTests = 500;
		Random rand = new Random(0);
		for (int j = 1; j <= numTests; j++) {
			int len = rand.nextInt(maxLength)+1;
			int[] a = new int[len];
			for (int i = 0; i < len; i++) {
				a[i] = rand.nextInt(maxValue);
			}
			SegmentTreeGCD st = new SegmentTreeGCD(a);
			
			for (int i = 0; i < numTests; i++) {
				int low = rand.nextInt(len);
				int high = rand.nextInt(len-low) + low;
				
				int expected = 0;
				for (int k = low; k <= high; k++) {
					expected = gcd(a[k], expected);
				}
				
				int actual = st.get(low, high);
				assertEquals(expected, actual);
				
				int change = rand.nextInt(maxValue);
				a[low] = change;
				st.set(low, change);
			}
		}
	}

	@Test
	public void testBitwiseAnd() {
		int maxLength = 1000;
		int maxValue = 1000000;
		int numTests = 1000;
		Random rand = new Random(0);
		for (int j = 1; j <= numTests; j++) {
			int len = rand.nextInt(maxLength)+1;
			int[] a = new int[len];
			for (int i = 0; i < len; i++) {
				a[i] = rand.nextInt(maxValue);
			}
			SegmentTreeBitwiseAnd st = new SegmentTreeBitwiseAnd(a);
			
			for (int i = 0; i < numTests; i++) {
				int low = rand.nextInt(len);
				int high = rand.nextInt(len-low) + low;
				
				int expected = Integer.MAX_VALUE;
				for (int k = low; k <= high; k++) {
					expected = expected & a[k];
				}
				
				int actual = st.get(low, high);
				assertEquals(expected, actual);
				
				int change = rand.nextInt(maxValue);
				a[low] = change;
				st.set(low, change);
			}
		}
	}

	public static int gcd(int a, int b) {
	    return BigInteger.valueOf(a).gcd(BigInteger.valueOf(b)).intValue();
	}
	
	public static class Node implements BinaryOperation<Node> {
		private int n;
		public Node(int n) {
			this.n = n;
		}
		
		@Override
		public Node function(Node e1, Node e2) {
			return new Node(e1.n + e2.n);
		}

		public static Node zero = new Node(0);
		
		@Override
		public Node identity() {
			return zero;
		}
		
		public String toString() {
			return Integer.toString(n);
		}
	}
	
	@Test
	public void testAdditionE() {
		int maxLength = 100;
		int maxValue = 1000000;
		int numTests = 1000;
		Random rand = new Random(0);
		for (int j = 1; j <= numTests; j++) {
			int len = rand.nextInt(maxLength)+1;
			int[] a = new int[len];
			for (int i = 0; i < len; i++) {
				a[i] = rand.nextInt(maxValue) - maxValue/2;
			}
			Node[] an = new Node[a.length];
			for (int i = 0; i < an.length; i++) {
				an[i] = new Node(a[i]);
			}
			SegmentTreeE<Node> st = new SegmentTreeE<Node>(an);
			
			for (int i = 0; i < numTests; i++) {
				int low = rand.nextInt(len);
				int high = rand.nextInt(len-low);
				
				int expected = 0;
				for (int k = low; k <= high; k++) {
					expected += a[k];
				}
				
				int actual = st.get(low, high).n;
				assertEquals(expected, actual);
				
				int change = rand.nextInt(maxValue);
				a[low] = change;
				st.set(low, new Node(change));
			}
		}
	}

	@Test
	public void testSearch() {
		int maxLength = 10;
		int maxValue = 10;
		int numTests = 100;
		Random rand = new Random(0);
		for (int j = 1; j <= numTests; j++) {
			int len = rand.nextInt(maxLength)+1;
			int[] a = new int[len];
			for (int i = 0; i < len; i++) {
				a[i] = rand.nextInt(maxValue);
			}
			//a = new int[]{2, 1, 3, 0, 0, 0, 1, 1, 3, 3};
			//a = new int[]{1, 1, 1, 1, 1, 1, 1, 1};
			//len = a.length;
			SegmentTree st = new SegmentTree(a);
			
			int[] firstSum = new int[len * maxValue];
			int sum = 0;
			Arrays.fill(firstSum, Integer.MAX_VALUE);
			for (int i = 0; i < a.length; i++) {
				sum += a[i];
				firstSum[sum] = Math.min(firstSum[sum], i);
			}
			
			int last = 0;
			for (int i = 0; i < firstSum.length; i++) {
				if (firstSum[i] != Integer.MAX_VALUE) {
					last = firstSum[i]+1;
				} else {
					firstSum[i] = -last -1;
				}
			}
			
			for (int i = 1; i < firstSum.length; i++) {
				int expected = firstSum[i];
				int actual = st.search2(0, i);
				
				if (expected != actual) {
					System.out.println(i + "\t expected: " + expected + "\t actual: " + actual);
					st.search2(0, i);
				}
				//assertEquals(expected, actual);
			}
		}
	}
}
