package dataStructures;

import static org.junit.Assert.*;

import java.math.BigInteger;
import java.util.Random;

import org.junit.Test;

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
				a[i] = rand.nextInt(maxValue);
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
		int numTests = 100;
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

}
