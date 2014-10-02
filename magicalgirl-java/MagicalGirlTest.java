import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MagicalGirlTest {

    protected MagicalGirl solution;

    @Before
    public void setUp() {
        solution = new MagicalGirl();
    }

    public static void assertEquals(double expected, double actual) {
        if (Double.isNaN(expected)) {
            Assert.assertTrue("expected: <NaN> but was: <" + actual + ">", Double.isNaN(actual));
            return;
        }
        double delta = Math.max(1e-9, 1e-9 * Math.abs(expected));
        Assert.assertEquals(expected, actual, delta);
    }

    @Test(timeout = 2000)
    public void testCase0() {
        int M = 2;
        int[] day = new int[]{1};
        int[] win = new int[]{75};
        int[] gain = new int[]{1};

        double expected = 2.5;
        double actual = solution.maxExpectation(M, day, win, gain);

        assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int M = 10;
        int[] day = new int[]{5, 5, 5, 5};
        int[] win = new int[]{100, 100, 100, 100};
        int[] gain = new int[]{1, 1, 1, 1};

        double expected = 14.0;
        double actual = solution.maxExpectation(M, day, win, gain);

        assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int M = 10;
        int[] day = new int[]{5, 5, 5, 5, 5};
        int[] win = new int[]{40, 80, 60, 20, 0};
        int[] gain = new int[]{10, 10, 10, 10, 10};

        double expected = 13.0;
        double actual = solution.maxExpectation(M, day, win, gain);

        assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int M = 2;
        int[] day = new int[]{2};
        int[] win = new int[]{100};
        int[] gain = new int[]{2};

        double expected = 2.0;
        double actual = solution.maxExpectation(M, day, win, gain);

        assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        int M = 20;
        int[] day = new int[]{2, 13, 8, 7, 9, 4, 6, 21};
        int[] win = new int[]{18, 83, 75, 23, 45, 23, 10, 98};
        int[] gain = new int[]{10, 9, 8, 10, 7, 16, 13, 20};

        double expected = 35.908;
        double actual = solution.maxExpectation(M, day, win, gain);

        assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase5() {
        int M = 11;
        int[] day = new int[]{10, 20, 30, 40, 50, 60, 70, 80, 90};
        int[] win = new int[]{100, 100, 100, 100, 100, 100, 100, 100, 100};
        int[] gain = new int[]{10, 10, 10, 10, 10, 10, 10, 10, 10};

        double expected = 101.0;
        double actual = solution.maxExpectation(M, day, win, gain);

        assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase6() {
        int M = 100000;
        int[] day = new int[]{1000000};
        int[] win = new int[]{100};
        int[] gain = new int[]{100000};

        double expected = 100000.0;
        double actual = solution.maxExpectation(M, day, win, gain);

        assertEquals(expected, actual);
    }

}
