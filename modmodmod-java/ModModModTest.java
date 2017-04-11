import java.util.Arrays;
import java.util.Random;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ModModModTest {

    protected ModModMod solution;

    @Before
    public void setUp() {
        solution = new ModModMod();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        int[] m = new int[]{5, 3, 2};
        int R = 10;

        long expected = 4L;
        long actual = solution.findSum(m, R);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int[] m = new int[]{33, 15, 7, 10, 100, 9, 5};
        int R = 64;

        long expected = 92L;
        long actual = solution.findSum(m, R);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int[] m = new int[]{995, 149, 28, 265, 275, 107, 555, 241, 702, 462, 519, 212, 362, 478, 783, 381, 602, 546, 183, 886, 59, 317, 977, 612, 328, 91, 771, 131};
        int R = 992363;

        long expected = 12792005L;
        long actual = solution.findSum(m, R);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int[] m = new int[]{100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100};
        int R = 100;

        long expected = 4950L;
        long actual = solution.findSum(m, R);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        int[] m = new int[]{2934};
        int R = 10000000;

        long expected = 14664070144L;
        long actual = solution.findSum(m, R);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000000)
    public void testCase5() {
    	Random rand = new Random(0);
    	for (int j = 0; j < 100000; j++) {

            int[] m = new int[50];
        	for (int i = 0; i < m.length; i++) {
    			m[i] = rand.nextInt(1000) +1 ;
    		}
            int R = 1000;

            long expected = 0;//solution.findSum2(m, R);
            long actual = solution.findSum(m, R);
            Assert.assertEquals(expected, actual);

		}
    }

    @Test(timeout = 2000000)
    public void testCase6() {
    	int[] m = new int[5000];
    	for (int i = 0; i < m.length; i++) {
			m[i] = 10000000;
		}
    	System.out.println(Arrays.toString(m));
        int R = 10000000;

        long expected = 49999995000000L;//solution.findSum2(m, R);
        long actual = solution.findSum(m, R);
        Assert.assertEquals(expected, actual);
    }

}
