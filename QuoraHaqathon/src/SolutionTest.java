import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;


public class SolutionTest {

	@Test
	public void test() {
		/*
		Random rand = new Random(0);
		for (int k = 0; k < 100; k++) {
			int n = 10000;
			int m = 10000;
			int[] r = new int[n];
			long[][] p1 = new long[m][2];
			long[][] p2 = new long[m][2];
			for (int i = 0; i < n; i++) {
				r[i] = rand.nextInt(1000000);
			}
			for (int i = 0; i < m; i++) {
				for (int j = 0; j < 2; j++) {
					p1[i][j] = rand.nextInt(2000000) - 1000000;				
					p2[i][j] = rand.nextInt(2000000) - 1000000;
				}
			}
			Solution.archeryF(n, r, m, p1, p2);
		}*/
		Solution.upvotes2(1, 1, new int[]{1});
		Random rand = new Random(0);
		for (int i = 0; i < 100000; i++) {
			int n = rand.nextInt(100)+1;
			int k = rand.nextInt(n)+1;
			int[] v = new int[n];
			for (int j = 0; j < n; j++) {
				v[j] = rand.nextInt(3)+1;
			}
			Solution.upvotes2(n, k, v);
			assertEquals(Solution.upvotes(n, k, v), Solution.upvotes2(n, k, v));
		}
	}

}
