import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Random;

import org.junit.Test;


public class VariousAlgorithmsTest {

	@Test
	public void testPermutation() {
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j <= i; j++) {
				long a = VariousAlgorithms.permutation(i, j).longValue();
				long b = VariousAlgorithms.nPr(i, j);
				assertEquals(a, b);
				assertTrue(a >= 0);
			}			
		}
	}
	
	@Test
	public void testCombination() {
		for (int i = 0; i < 30; i++) {
			for (int j = 0; j <= i; j++) {
				long a = VariousAlgorithms.combination(i, j).longValue();
				long b = VariousAlgorithms.nCr(i, j);
				if (a != b) {
					System.out.println(i + " C " + j + "\t" + a + "\t" + b);
				}				
				assertEquals(a, b);
				assertTrue(a >= 0);
			}		
		}		
	}
	
	@Test
	public void testGetPrimeFactors() {
		int limit = 1000;
		LinkedHashSet<Integer> primes = VariousAlgorithms.getPrimes(limit);
		for (int i = 2; i < 1000; i++) {
			ArrayList<Long> pf = VariousAlgorithms.getPrimeFactors(i);
			long a = 1;
			for (int j = 0; j < pf.size(); j++) {
				assertTrue(primes.contains((int)(long)pf.get(j)));
				a *= pf.get(j);
			}
			assertEquals(i, a);
		}
		VariousAlgorithms.getPrimeFactors(214);
	}
	
	@Test
	public void testCombinationWithRepetition() {
		assertEquals(92378, VariousAlgorithms.nCrWithRep(10, 10));
		assertEquals(2002, VariousAlgorithms.nCrWithRep(10, 5));
		assertEquals(1, VariousAlgorithms.nCrWithRep(10, 0));
		assertEquals(10, VariousAlgorithms.nCrWithRep(3, 3));
		assertEquals(6, VariousAlgorithms.nCrWithRep(3, 2));
		assertEquals(3, VariousAlgorithms.nCrWithRep(3, 1));
		assertEquals(1, VariousAlgorithms.nCrWithRep(3, 0));
	}
	
	@Test
	public void testKmp() {
		int tests = 10000;
		int maxLength = 1000;
		Random rand = new Random(0);
		for (int i = 0; i < tests; i++) {
			int length = rand.nextInt(maxLength) + 1;
			char[] s = new char[length];
			int alphabetSize = rand.nextInt(26) + 1;
			for (int j = 0; j < s.length; j++) {
				s[j] = (char) (rand.nextInt(alphabetSize) + 'a');
			}
			int subLength = rand.nextInt(length) + 1;
			char[] k = new char[subLength];
			for (int j = 0; j < k.length; j++) {
				k[j] = (char) (rand.nextInt(alphabetSize) + 'a');
			}
			
			ArrayList<Integer> expected = new ArrayList<>();
			for (int m = 0; m <= s.length - k.length; m++) {
				boolean match = true;
				for (int n = 0; n < k.length; n++) {
					if (s[m + n] != k[n]) {
						match = false;
					}
				}
				if (match) {
					expected.add(m);
				}
			}
			
			ArrayList<Integer> actual = VariousAlgorithms.kmp(new String(s), new String(k));
			if (!actual.equals(expected)) {
				System.nanoTime();
			}
			assertEquals(expected, actual);
		}
	}
	
	@Test
	public void testTernarySearch() {
		int tests = 1000000;
		int maxLength = 20;
		Random rand = new Random(0);
		for (int i = 0; i < tests; i++) {
			int length = rand.nextInt(maxLength) + 1;
			int mid = rand.nextInt(length);
			int midLength = rand.nextInt(length/4 + 1);
			int start = rand.nextInt(100) - 50;
			int[] data = new int[length];
			for (int j = mid; j < Math.min(mid + midLength, length); j++) {
				data[j] = start;
			}
			for (int j = Math.min(mid + midLength, length); j < length; j++) {
				if (j > 0) {
					data[j] = data[j-1] + rand.nextInt(10)+1;
				}
			}
			for (int j = mid; j >= 1; j--) {
				data[j-1] = data[j] + rand.nextInt(10)+1;
			}
			
			int expected = 0;
			int min = Integer.MAX_VALUE;
			for (int j = 0; j < data.length; j++) {
				if (data[j] < min) {
					min = data[j];
					expected = j;
				}
			}
			
			int actual = VariousAlgorithms.ternarySearch(data);
			if (actual != expected) {
				System.currentTimeMillis();
			}
			assertEquals(expected, actual);
			//System.out.println(Arrays.toString(data));
		}
	}
	
	@Test
	public void testLCS() {
		int numTests = 100000;
		int maxLength = 5;
		int range = 4;		
		Random rand = new Random(0);
		
		for (int t = 0; t < numTests; t++) {
			char[] a = new char[rand.nextInt(maxLength) + 1];
			for (int i = 0; i < a.length; i++) {
				a[i] = (char) (rand.nextInt(range) + 'a');
			}
			char[] b = new char[rand.nextInt(maxLength) + 1];
			for (int i = 0; i < b.length; i++) {
				b[i] = (char) (rand.nextInt(range) + 'a');
			}
			
			String exp = "";

			int N = 1 << a.length;
			for (int n = 0; n < N; n++) {
				String s1 = "";
				for (int i = 0; i < a.length; i++) {
					if (((1 << i) & n) != 0) {
						s1 += a[i];
					}
				}
				
				int M = 1 << b.length;
				for (int m = 0; m < M; m++) {
					String s2 = "";
					for (int i = 0; i < b.length; i++) {
						if (((1 << i) & m) != 0) {
							s2 += b[i];
						}
					}
					
					if (s2.equals(s1)) {
						exp = s1.length() > exp.length() ? s1 : exp;
					}
				}
			}
			
			String act = VariousAlgorithms.longestCommonSubsequence(a, b);
			if (act.length() != exp.length()) {
				System.nanoTime();
				VariousAlgorithms.longestCommonSubsequence(a, b);
			}
			assertEquals(exp.length(), act.length());
		}
	}

}
