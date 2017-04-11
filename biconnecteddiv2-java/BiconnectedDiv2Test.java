import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BiconnectedDiv2Test {

    protected BiconnectedDiv2 solution;

    @Before
    public void setUp() {
        solution = new BiconnectedDiv2();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        int[] w1 = new int[]{1, 2};
        int[] w2 = new int[]{3};

        int expected = 6;
        int actual = solution.minimize(w1, w2);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int[] w1 = new int[]{-1, -2, -3};
        int[] w2 = new int[]{-4, -5};

        int expected = -15;
        int actual = solution.minimize(w1, w2);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int[] w1 = new int[]{1, 100, -3, 2};
        int[] w2 = new int[]{-2, 1, 4};

        int expected = 3;
        int actual = solution.minimize(w1, w2);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 20000)
    public void testCase3() {
    	Random ra = new Random(0);
    	for (int N = 3; N <= 8; N++) {
			for (int test = 0; test < 100; test++) {
				int[] w1 = new int[N-1];
		        int[] w2 = new int[N-2];
		        for (int i = 0; i < w1.length; i++) {
					w1[i] = ra.nextInt(20000) - 10000;
				}
		        for (int i = 0; i < w2.length; i++) {
					w2[i] = ra.nextInt(20000) - 10000;
				}
		        
		        int expected = solution.slow(w1, w2);
		        int actual = solution.minimize(w1, w2);
		        if (expected != actual) {
		        	System.out.println("fail");
		        	solution.slow(w1, w2);
		        	solution.minimize(w1, w2);
		        }
			}
		}
    }

	

}
