import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedHashSet;

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

}
