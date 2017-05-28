package dataStructures;

import static org.junit.Assert.*;

import java.util.Map.Entry;
import java.util.Random;
import java.util.TreeMap;

import org.junit.Test;

/**
 * A data structure built for combining and splitting ranges. Ranges may include negative numbers.
 * 
 * All operations are O(log(N)), but they can degrade to O(N). However it will take N time to 
 * create all the small ranges, so N queries will take O(N * log(N)) time.
 */

public class RangeUnionTest {
	public static class RangeUnion {
		TreeMap<Long, Long> range;
		
		public RangeUnion() {
			range = new TreeMap<>();
		}
		
		/**
		 * Add the range [a, b]. Overlap is ignored.
		 */
		public void add(long a, long b) {
			// If a range covers [a, b], do nothing
			Entry<Long, Long> lt = range.lowerEntry(a);
			if (lt != null && lt.getValue() > b) {
				return;
			}

			Entry<Long, Long> gte = range.ceilingEntry(a);
	
			// remove ranges that are entirely in [a, b]
			while (gte != null && gte.getValue() <= b) {
				range.remove(gte.getKey());
				gte = range.ceilingEntry(a);
			}
			
			// merge the range that ends in [a-1, b], if it exists
			lt = range.lowerEntry(a);
			if (lt != null && lt.getValue() >= a-1) {
				range.put(lt.getKey(), b);
				a = lt.getKey();
			}
			
			// remove the range that starts in [a, b+1], if it exists
			Entry<Long, Long> gt = range.floorEntry(b+1);
			if (gt != null && gt.getKey() >= a) {
				range.remove(gt.getKey());
				range.put(a, gt.getValue());
			}

			if (!isSet(a)) {
				range.put(a, b);	
			}
		}
		
		/**
		 * Remove the range [a, b].
		 */
		public void remove(long a, long b) {
			Entry<Long, Long> gte = range.ceilingEntry(a);
	
			// remove ranges that are entirely in [a, b]
			while (gte != null && gte.getValue() <= b) {
				range.remove(gte.getKey());
				gte = range.ceilingEntry(a);
			}
			
			// remove the range that covers [a, b], if it exists
			Entry<Long, Long> lt = range.lowerEntry(a);
			if (lt != null && lt.getValue() > b) {
				range.put(lt.getKey(), a-1);
				range.put(b+1, lt.getValue());
			}
			
			// remove the range that ends in [a, b], if it exists
			lt = range.lowerEntry(a);
			if (lt != null && lt.getValue() >= a) {
				range.put(lt.getKey(), a-1);
			}
			
			// remove the range that starts in [a, b], if it exists
			Entry<Long, Long> gt = range.floorEntry(b);
			if (gt != null && gt.getKey() >= a) {
				range.remove(gt.getKey());
				range.put(b+1, gt.getValue());
			}
		}
		
		/**
		 * Get the first gap >= x. That is the first point >= x where there is no range set.
		 */
		public long ceilingGap(long x) {
			Entry<Long, Long> lte = range.floorEntry(x);
			if (lte == null) {
				return x;
			} else {
				return Math.max(lte.getValue()+1, x);
			}
		}
		
		/**
		 * Return whether there is a range [a, b] such that a <= x <= b  
		 */
		public boolean isSet(long x) {
			Entry<Long, Long> lte = range.floorEntry(x);
			if (lte == null) {
				return false;
			} else {
				return x <= lte.getValue();
			}
		}
		
		public long[] getRange(long x) {
			Entry<Long, Long> entry = getRangeEntry(x);
			if (entry == null) {
				return null;
			} else {
				return new long[]{entry.getKey(), entry.getValue()};
			}
		}
		
		private Entry<Long, Long> getRangeEntry(long x) {
			Entry<Long, Long> lte = range.floorEntry(x);
			if (lte != null && x <= lte.getValue()) {
				return lte;
			}
			return null;
		}
		
		public String toString() {
			return range.toString();
		}
	}
	
	@Test
	public void testRangeUnion() {
		int numTests = 500;
		int maxLength = 500;
		Random r = new Random(0);
		for (int test = 0; test < numTests; test++) {
			boolean[] expected = new boolean[r.nextInt(maxLength)+1];
			RangeUnion range = new RangeUnion();
			int N = expected.length;
			int offset = N/2;
			
			for (int q = 0; q < numTests; q++) {
				int a = r.nextInt(N);
				int b = r.nextInt(N - a) + a;
				boolean state = r.nextBoolean();
				for (int i = a; i <= b; i++) {
					expected[i] = state;
				}
				if (state) {
					range.add(a - offset, b - offset);
				} else {
					range.remove(a - offset, b - offset);	
				}
				
				System.currentTimeMillis();
				for (int i = 0; i < N; i++) {
					assertEquals(expected[i], range.isSet(i - offset));
				}

				int firstGap = a;
				while (firstGap < N && expected[firstGap]) {
					firstGap++;
				}
				assertEquals(firstGap, offset + range.ceilingGap(a - offset));
			}
		}
	}
	
	/**
	 * Run 10^6 queries on ranges of size up to 10^9 in 0.2 seconds
	 */
	@Test
	public void testRangeUnionPerformance() {
		int numRuns = 5;
		int numTests = 1000000;
		int maxRange = 1000000000;
		Random r = new Random(0);
		for (int test = 0; test < numRuns; test++) {
			long time = System.currentTimeMillis();
			RangeUnion range = new RangeUnion();
			
			for (int q = 0; q < numTests; q++) {
				boolean small = r.nextBoolean();
				int a = r.nextInt(maxRange);
				int b = r.nextInt(maxRange - a) + a;
				if (small) {
					b = a;
				}
				boolean add = r.nextBoolean();
				if (add) {
					range.add(a, b);
				} else {
					range.remove(a, b);	
				}
			}

			time = System.currentTimeMillis() - time;
			System.out.println(time);
		}
	}
}
