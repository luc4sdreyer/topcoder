import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class WorkingRabbitsTest {

    protected WorkingRabbits solution;

    @Before
    public void setUp() {
        solution = new WorkingRabbits();
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
        String[] profit = new String[]{"071", "702", "120"};

        double expected = 3.3333333333333335;
        double actual = solution.getEfficiency(profit);

        assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        String[] profit = new String[]{"00", "00"};

        double expected = 0.0;
        double actual = solution.getEfficiency(profit);

        assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        String[] profit = new String[]{"0999", "9099", "9909", "9990"};

        double expected = 9.0;
        double actual = solution.getEfficiency(profit);

        assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        String[] profit = new String[]{"013040", "100010", "300060", "000008", "416000", "000800"};

        double expected = 1.5333333333333334;
        double actual = solution.getEfficiency(profit);

        assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        String[] profit = new String[]{"06390061", "60960062", "39090270", "96900262", "00000212", "00222026", "66761201", "12022610"};

        double expected = 3.2142857142857144;
        double actual = solution.getEfficiency(profit);

        assertEquals(expected, actual);
    }

}
