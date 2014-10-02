import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MinimalTriangleTest {

    protected MinimalTriangle solution;

    @Before
    public void setUp() {
        solution = new MinimalTriangle();
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
        int length = 5;

        double expected = 10.825317547305485;
        double actual = solution.maximalArea(length);

        assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int length = 10;

        double expected = 43.30127018922194;
        double actual = solution.maximalArea(length);

        assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int length = 100000;

        double expected = 4.330127018922194E9;
        double actual = solution.maximalArea(length);

        assertEquals(expected, actual);
    }

}
