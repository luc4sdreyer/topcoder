package dataStructures;

import static org.junit.Assert.*;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;

import org.junit.Test;

import dataStructures.SegmentTreePersistentHolder.SegmentTree;

public class SegmentTreePersistentHolderTest {
	
	public static void shuffle(int[] a, Random rand) {
        for (int i = a.length; i > 1; i--) {
        	int r = rand.nextInt(i);
        	int temp = a[i-1];
        	a[i-1] = a[r];
        	a[r] = temp;
        }
	}

	@Test
	public void testKthNumber() {
		int maxLength = 100;
		int numTests = 1000;
		int maxValue = 1000000000;
		Random rand = new Random(0);
		for (int j = 1; j <= numTests; j++) {
			int len = rand.nextInt(maxLength)+1;
			int[] data = new int[len];
			HashSet<Integer> chosen = new HashSet<>();
			
			for (int i = 0; i < data.length; i++) {
				int r = rand.nextInt(maxValue) - maxValue/2;
				while (chosen.contains(r)) {
					r = rand.nextInt(maxValue) - maxValue/2;
				}
				data[i] = r;
			}
			
			SegmentTree st = new SegmentTree(data, true);
			
			for (int i = 0; i < numTests; i++) {
				int low = rand.nextInt(len);
				int high = rand.nextInt(len-low) + low;
				
				int[] sorted = new int[high - low + 1];
				for (int k = 0; k < sorted.length; k++) {
					sorted[k] = data[low + k];
				}
				Arrays.sort(sorted);
				
				for (int k = 0; k < sorted.length; k++) {
					int expected = sorted[k];
					
					int actual = st.query(low, high, k+1);
					assertEquals(expected, actual);
				}
			}
		}
	}
	
	@Test
	public void testQuerySpeed1() {
		// 10^5 size, 10^5 queries
		int len = 100000;
		int numTests = 1;
		Random rand = new Random(0);
		int sum = 0;
		for (int j = 1; j <= numTests; j++) {
			int[] data = new int[len];
			
			for (int i = 0; i < data.length; i++) {
				data[i] = i;
			}
			shuffle(data, rand);
			
			SegmentTree st = new SegmentTree(data, true);
			
			for (int i = 0; i < len; i++) {
				int low = rand.nextInt(len);
				int high = rand.nextInt(len-low) + low;
				
				int actual = st.query(low, high, 0);
				sum += actual;
			}
		}
		assertTrue(sum >= 0);
	}
	
	@Test
	public void testQuerySpeed2() {
		// 10 size, 10 queries, 10^5 tests
		int len = 10;
		int numTests = 10000;
		Random rand = new Random(0);
		int sum = 0;
		for (int j = 1; j <= numTests; j++) {
			int[] data = new int[len];
			
			for (int i = 0; i < data.length; i++) {
				data[i] = i;
			}
			shuffle(data, rand);
			
			SegmentTree st = new SegmentTree(data, true);
			
			for (int i = 0; i < len; i++) {
				int low = rand.nextInt(len);
				int high = rand.nextInt(len-low) + low;
				
				int actual = st.query(low, high, 0);
				sum += actual;
			}
		}
		assertTrue(sum >= 0);
	}

	@Test
	public void testKthNumberWithDuplicates() {
		int maxLength = 100;
		int numTests = 1000;
		int maxValue = maxLength;
		Random rand = new Random(0);
		for (int j = 1; j <= numTests; j++) {
			int len = rand.nextInt(maxLength)+1;
			int[] data = new int[len];
			
			for (int i = 0; i < data.length; i++) {
				int r = rand.nextInt(maxValue) - maxValue/2;
				data[i] = r;
			}
			Arrays.sort(data);
			
			SegmentTree st = new SegmentTree(data, true);
			
			for (int i = 0; i < numTests; i++) {
				int low = rand.nextInt(len);
				int high = rand.nextInt(len-low) + low;
				
				int[] sorted = new int[high - low + 1];
				for (int k = 0; k < sorted.length; k++) {
					sorted[k] = data[low + k];
				}
				Arrays.sort(sorted);
				int[] rank = new int[sorted.length];
				int count = 0;
				for (int k = 1; k < rank.length; k++) {
					if (sorted[k] != sorted[k-1]) {
						count++;
					}
					rank[k] = count;
				}
				
				for (int k = 0; k < sorted.length; k++) {
					int expected = rank[k];
					
					int actual = st.query(low, high, k+1);
					assertEquals(expected, actual);
				}
			}
		}
	}
	
}
