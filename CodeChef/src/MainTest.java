
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Random;

import org.junit.Test;


public class MainTest {

	//@Test
	public void test() {

		Random rand = new Random(0);
		long maxTime = 0;
		for (int n = 0; n <= 12; n++) {
			for (int k = 1; k <= 8; k++) {
				for (int i = 0; i < 100; i++) {
					ArrayList<Long> a = new ArrayList<>();
					for (int j = 0; j < n; j++) {
						//a.add((long) rand.nextInt(10000000));
						a.add((long) rand.nextInt(10));
					}
					@SuppressWarnings("unchecked")
					ArrayList<Long> b = (ArrayList<Long>) a.clone();
					@SuppressWarnings("unchecked")
					ArrayList<Long> c = (ArrayList<Long>) a.clone();
					
					long time = System.nanoTime();
					String res = Long14_12.sanskar7(n, k, a);
					//String res2 = sanskar3(n, k, a);
					String res2 = Long14_12.sanskar8(n, k, b);
					time = (System.nanoTime() - time)/1000000;
					//if (time >= 100) {
					if (!res.equals(res2)) {
					//if (res.equals("yes")) {
						System.out.println(n + "\t " + k + "\t" + c + ": " + res + " vs " + res2);
						System.out.println("   " + res + "\ttime: " + time);
					}
					//}
					maxTime = Math.max(maxTime, time);
				}
			}
		}
		System.out.println("maxTime: " + maxTime);
	}
	
	@Test
	public void test3() {
		int[] a = new int[]{-1, -2, 9, 2, -3, -4, 3, 4, 8, 8, 1};
		assertEquals(12, Long14_12.chefAndBracketPairsFslow(a.length, a));
		
		a = new int[]{-2, -3, -4, 2, 3, 4};
		assertEquals(4, Long14_12.chefAndBracketPairsFslow(a.length, a));
		
		a = new int[]{-2, -3, -4, 3, 2, 4};
		assertEquals(5, Long14_12.chefAndBracketPairsFslow(a.length, a));
		
		a = new int[]{-2, -3, -4, -5, 4, 3, 2, 5};
		assertEquals(9, Long14_12.chefAndBracketPairsFslow(a.length, a));
		
		
		
		a = new int[]{-2, -3, -2, 2, 3, 2};
		assertEquals(10, Long14_12.chefAndBracketPairsFslow(a.length, a));
		
		a = new int[]{-2, -3, -2, 2, 3};
		assertEquals(5, Long14_12.chefAndBracketPairsFslow(a.length, a));
		
		a = new int[]{-3, -2, 2, 3, 2};
		assertEquals(5, Long14_12.chefAndBracketPairsFslow(a.length, a));
		
		a = new int[]{-2, -2, -3, -2, 2, 3, 2};
		assertEquals(16, Long14_12.chefAndBracketPairsFslow(a.length, a));
		
		a = new int[]{-2, -3, -2, 2, 3, 2, 2};
		assertEquals(16, Long14_12.chefAndBracketPairsFslow(a.length, a));
		
		
		
		a = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
		assertEquals(184756, Long14_12.chefAndBracketPairsFslow(a.length, a));
		
		a = new int[]{-2, -2, 2, 2};
		assertEquals(6, Long14_12.chefAndBracketPairsFslow(a.length, a));
		
	}

}
