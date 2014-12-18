import static org.junit.Assert.*;

import java.math.BigInteger;
import java.util.Arrays;

import org.junit.Test;


public class SolutionTest {

	@Test
	public void test() {		
		for (int a = 0; a < 10; a++) {
			for (int exp = 0; exp < 10; exp++) {
				for (int mod = 1; mod < 10; mod++) {
					long res1 = Solution.fastModularExponent(a, exp, mod);
					long res2 = BigInteger.valueOf(a).pow(exp).mod(BigInteger.valueOf(mod)).longValue();
					assertEquals(res2, res1);
//					if (res1 != res2) {
//						System.out.println("fail: " + a +", "  + exp +", "  + mod + ": "+ res1 + " vs " + res2);
//					}
				}
			}
		}
	}
	
	@Test
	public void test2() {		
		for (int a = 0; a < 10; a++) {
			for (int mod = 1; mod < 10; mod++) {
				String res1 = Solution.eulersCriterionF(a, mod);
				String res2 = Solution.eulersCriterionF2(a, mod);
				assertEquals(res2, res1);
			}
		}
	}
	
	@Test
	public void test3() {
		System.out.println(Solution.eulersCriterionF2(1000000000, 1000000000));
		System.out.println(Solution.eulersCriterionF2(0, 1000000000));
		System.out.println(Solution.eulersCriterionF2(0, 1));
		System.out.println(Solution.eulersCriterionF2(1, 1));
		System.out.println(Solution.eulersCriterionF2(0, 3));
	}

	@Test
	public void test4() {
//		for (int p = -100; p < 100; p++) {
//			for (int d = -5; d <= 5; d++) {
//				System.out.print(differenceAndProductF(d, p) + "\t");
//			}
//			System.out.println();
//		}
		Solution.differenceAndProductF(-1000, 0); 
		int[] c = new int[10];
		int limit = 100;
		int count = 0;
		int fail = 0;
		for (int p = -limit; p < limit; p++) {
			for (int d = -limit; d <= limit; d++) {
				int res = Solution.differenceAndProductF3(d, p); 
				c[res]++;
				int res2 = Solution.differenceAndProductF4(d, p);
				if (res != 0) {
					count++;
				}				
				if (res != res2) {
					System.out.println("\t\tfail: " + d +", "  + p +": "+ res + " vs " + res2);
					Solution.differenceAndProductF4(d, p);
					if (res != 0) {
						fail++;
					}				
				}
				//assertEquals(res, res2);
			}
		}
		System.out.println(fail*100.0/count);
		System.out.println(Arrays.toString(c));
	}
	
	@Test
	public void test5() {
		for (int i = 0; i < 20000; i++) {
			Solution.differenceAndProductF4(1000000000, 1000000000);
		}
	}

}
