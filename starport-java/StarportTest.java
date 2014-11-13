import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StarportTest {

    protected Starport solution;

    @Before
    public void setUp() {
        solution = new Starport();
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
        int N = 4;
        int M = 2;

        double expected = 1.0;
        double actual = solution.getExpectedTime(N, M);

        assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int N = 5;
        int M = 3;

        double expected = 2.0;
        double actual = solution.getExpectedTime(N, M);

        assertEquals(expected, actual);
    }

    @Test(timeout = 2000000)
    public void testCase2() {
        int N = 6;
        int M = 1;

        double expected = 2.5;
        double actual = solution.getExpectedTime(N, M);

        assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int N = 12345;
        int M = 2345;

        double expected = 6170.0;
        double actual = solution.getExpectedTime(N, M);

        assertEquals(expected, actual);
    }

}
