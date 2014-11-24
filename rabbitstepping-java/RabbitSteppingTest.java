import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RabbitSteppingTest {

    protected RabbitStepping solution;

    @Before
    public void setUp() {
        solution = new RabbitStepping();
    }

    public static void assertEquals(double expected, double actual) {
        if (Double.isNaN(expected)) {
            Assert.assertTrue("expected: <NaN> but was: <" + actual + ">", Double.isNaN(actual));
            return;
        }
        double delta = Math.max(1e-9, 1e-9 * Math.abs(expected));
        Assert.assertEquals(expected, actual, delta);
    }

    @Test(timeout = 2000000)
    public void testCase0() {
        String field = "WRBRW";
        int r = 4;

        double expected = 0.8;
        double actual = solution.getExpected(field, r);

        assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        String field = "WWB";
        int r = 2;

        double expected = 1.3333333333333333;
        double actual = solution.getExpected(field, r);

        assertEquals(expected, actual);
    }

    @Test(timeout = 200000)
    public void testCase2() {
        String field = "WW";
        int r = 1;

        double expected = 1.0;
        double actual = solution.getExpected(field, r);

        assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        String field = "BBBBBBBBBB";
        int r = 4;

        double expected = 0.9523809523809523;
        double actual = solution.getExpected(field, r);

        assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        String field = "RRBRRWRRBRRW";
        int r = 8;

        double expected = 0.9696969696969697;
        double actual = solution.getExpected(field, r);

        assertEquals(expected, actual);
    }

}
