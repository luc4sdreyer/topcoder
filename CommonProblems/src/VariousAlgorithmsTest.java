import static org.junit.Assert.*;

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
