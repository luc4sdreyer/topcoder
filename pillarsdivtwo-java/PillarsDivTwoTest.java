import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PillarsDivTwoTest {

    protected PillarsDivTwo solution;

    @Before
    public void setUp() {
        solution = new PillarsDivTwo();
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
        int[] height = new int[]{3, 3, 3};
        int w = 2;

        double expected = 5.656854249492381;
        double actual = solution.maximalLength(height, w);

        assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int[] height = new int[]{1, 1, 1, 1};
        int w = 100;

        double expected = 300.0;
        double actual = solution.maximalLength(height, w);

        assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int[] height = new int[]{100, 2, 100, 2, 100};
        int w = 4;

        double expected = 396.32310051270036;
        double actual = solution.maximalLength(height, w);

        assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int[] height = new int[]{2, 1, 1, 2};
        int w = 1;

        double expected = 3.82842712474619;
        double actual = solution.maximalLength(height, w);

        assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        int[] height = new int[]{2};
        int w = 1;

        double expected = 0;
        double actual = solution.maximalLength(height, w);

        assertEquals(expected, actual);
    }

}
