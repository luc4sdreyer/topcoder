package dataStructures;

import static org.junit.Assert.*;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Random;

import org.junit.Test;

import dataStructures.SegmentTreeLazyHolder.SegmentTreeLazy;
import dataStructures.SegmentTreeLazyHolder.SegmentTreeLazySum;

public class SegmentTreeLazyHolderTest {	
	/**
	 * Tests fast range updates (O(log n))
	 */
	@Test
	public void testMaxUpdate() {
		int maxLength = 50;
		int maxValue = 100000;
		int numTests = 500;
		Random rand = new Random(0);
		for (int j = 1; j <= numTests; j++) {
			int len = rand.nextInt(maxLength)+1;
			int[] a = new int[len];
			for (int i = 0; i < len; i++) {
				a[i] = rand.nextInt(maxValue) - maxValue/2;
				a[i] = rand.nextInt(maxValue);
			}
			SegmentTreeLazy st = new SegmentTreeLazy(a);
			
			for (int i = 0; i < numTests; i++) {
				int low = rand.nextInt(len);
				int high = rand.nextInt(len-low) + low;
				
				int change = rand.nextInt(maxValue) - maxValue/2;
				for (int k = low; k <= high; k++) {
					a[k] += change;
				}
				st.update_tree(low, high, change);
				
				for (int m = 0; m < a.length; m++) {
					low = rand.nextInt(len);
					high = rand.nextInt(len-low) + low;
					int expected = Integer.MIN_VALUE;
					for (int k = low; k <= high; k++) {
						expected = Math.max(expected, a[k]);
					}
					long actual = st.query_tree(low, high);
					
					assertEquals(expected, actual);
				}
			}
		}
	}
	
	/**
	 * Tests fast range sets (as opposed to updates) (O(log n))
	 */
	@Test
	public void testMaxSet() {
		int maxLength = 50;
		int maxValue = 100000;
		int numTests = 500;
		Random rand = new Random(0);
		for (int j = 1; j <= numTests; j++) {
			int len = rand.nextInt(maxLength)+1;
			int[] a = new int[len];
			for (int i = 0; i < len; i++) {
				a[i] = rand.nextInt(maxValue) - maxValue/2;
				a[i] = rand.nextInt(maxValue);
			}
			SegmentTreeLazy st = new SegmentTreeLazy(a);
			
			for (int i = 0; i < numTests; i++) {
				int low = rand.nextInt(len);
				int high = rand.nextInt(len-low) + low;
				
				int change = rand.nextInt(maxValue) - maxValue/2;
				for (int k = low; k <= high; k++) {
					a[k] = change;
				}
				st.set_tree(low, high, change);
				
				for (int m = 0; m < a.length; m++) {
					low = rand.nextInt(len);
					high = rand.nextInt(len-low) + low;
					int expected = Integer.MIN_VALUE;
					for (int k = low; k <= high; k++) {
						expected = Math.max(expected, a[k]);
					}
					long actual = st.query_tree(low, high);
					
					if (actual != expected) {
						st.query_tree(low, high);
					}
					
					assertEquals(expected, actual);
				}
			}
		}
	}

	@Test
	public void testFastSearch() {
		int maxLength = 30;
		int maxValue = 50;
		int numTests = 10000;
		Random rand = new Random(0);
		for (int j = 1; j <= numTests; j++) {
			int len = rand.nextInt(maxLength)+1;
			int[] a = new int[len];
			for (int i = 0; i < len; i++) {
				a[i] = rand.nextInt(maxValue)+1;
			}
			
			SegmentTreeLazySum st = new SegmentTreeLazySum(a);
			
			int[] firstSum = new int[len * maxValue + 1];
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
				long actual = st.search(0, i);
				
				if (expected != actual) {
					System.out.println(i + "\t expected: " + expected + "\t actual: " + actual);
					st.search(0, i);
				}
				assertEquals(expected, actual);
			}
		}
	}
	
	@Test
	public void testSumUpdate() {
		int maxLength = 50;
		int maxValue = 100000;
		int numTests = 500;
		
		Random rand = new Random(0);
		for (int j = 1; j <= numTests; j++) {
			int len = rand.nextInt(maxLength)+1;
			int[] a = new int[len];
			for (int i = 0; i < len; i++) {
				a[i] = rand.nextInt(maxValue) - maxValue/2;
				a[i] = rand.nextInt(maxValue);
			}
			SegmentTreeLazySum st = new SegmentTreeLazySum(a);
			
			for (int i = 0; i < numTests; i++) {
				int low = rand.nextInt(len);
				int high = rand.nextInt(len-low) + low;
				
				int change = rand.nextInt(maxValue) - maxValue/2;
				for (int k = low; k <= high; k++) {
					a[k] += change;
				}
				st.update_tree(low, high, change);

				for (int m = 0; m < a.length; m++) {
					low = rand.nextInt(len);
					high = rand.nextInt(len-low) + low;
					
					int expected = 0;
					for (int k = low; k <= high; k++) {
						expected += a[k];
					}
					long actual = st.query_tree(low, high);
					
					if (expected != actual) {
						System.out.println(i + "\t expected: " + expected + "\t actual: " + actual);
						st.query_tree(low, high);
					}
					assertEquals(expected, actual);
				}
				
			}
		}
	}

	public static class SegmentTreeLazySumNoRangeUpdate extends SegmentTreeLazy {
		public SegmentTreeLazySumNoRangeUpdate(int[] a) {
			super(a);
			this.neg_inf = 0;
		}

		@Override
		protected long function(long a, long b) {
			return a + b;
		}
	}

	/**
	 * Test the ability to do point updates to avoid lazy updates.
	 */
	@Test
	public void testSumUpdateNoLazy() {
		int maxLength = 50;
		int maxValue = 100000;
		int numTests = 500;
		
		Random rand = new Random(0);
		for (int j = 1; j <= numTests; j++) {
			int len = rand.nextInt(maxLength)+1;
			int[] a = new int[len];
			for (int i = 0; i < len; i++) {
				a[i] = rand.nextInt(maxValue) - maxValue/2;
				a[i] = rand.nextInt(maxValue);
			}
			SegmentTreeLazySumNoRangeUpdate st = new SegmentTreeLazySumNoRangeUpdate(a);
			
			for (int i = 0; i < numTests; i++) {
				int low = rand.nextInt(len);
				int high = rand.nextInt(len-low) + low;
				
				int change = rand.nextInt(maxValue) - maxValue/2;
				for (int k = low; k <= high; k++) {
					a[k] += change;
					
					// Do point updates to avoid lazy updates.
					st.update_tree(k, k, change);
				}

				for (int m = 0; m < a.length; m++) {
					low = rand.nextInt(len);
					high = rand.nextInt(len-low) + low;
					
					int expected = 0;
					for (int k = low; k <= high; k++) {
						expected += a[k];
					}
					long actual = st.query_tree(low, high);
					
					if (expected != actual) {
						System.out.println(i + "\t expected: " + expected + "\t actual: " + actual);
						st.query_tree(low, high);
					}
					assertEquals(expected, actual);
				}
				
			}
		}
	}
}
