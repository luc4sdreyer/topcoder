import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AgeEncodingTest {

    protected AgeEncoding solution;

    @Before
    public void setUp() {
        solution = new AgeEncoding();
    }

    public static void assertEquals(double expected, double actual) {
        if (Double.isNaN(expected)) {
            Assert.assertTrue("expected: <NaN> but was: <" + actual + ">", Double.isNaN(actual));
            return;
        }
        double delta = Math.max(1e-9, 1e-9 * Math.abs(expected));
        Assert.assertEquals(expected, actual, delta);
    }

    @Test(timeout = 200000)
    public void testCase0() {
        int age = 10;
        String candlesLine = "00010";

        double expected = 10.0;
        double actual = solution.getRadix(age, candlesLine);

        assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int age = 21;
        String candlesLine = "10101";

        double expected = 2.0;
        double actual = solution.getRadix(age, candlesLine);

        assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int age = 6;
        String candlesLine = "10100";

        double expected = 1.414213562373095;
        double actual = solution.getRadix(age, candlesLine);

        assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int age = 21;
        String candlesLine = "10111111110111101111111100111111110111111111111100";

        double expected = 0.9685012944510603;
        double actual = solution.getRadix(age, candlesLine);

        assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        int age = 16;
        String candlesLine = "1";

        double expected = -1.0;
        double actual = solution.getRadix(age, candlesLine);

        assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase7() {
        int age = 16;
        String candlesLine = "0";

        double expected = -1.0;
        double actual = solution.getRadix(age, candlesLine);

        assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase8() {
        int age = 0;
        String candlesLine = "0";

        double expected = -2.0;
        double actual = solution.getRadix(age, candlesLine);

        assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase5() {
        int age = 1;
        String candlesLine = "1";

        double expected = -2.0;
        double actual = solution.getRadix(age, candlesLine);

        assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase6() {
        int age = 1;
        String candlesLine = "001000";

        double expected = 1.0;
        double actual = solution.getRadix(age, candlesLine);

        assertEquals(expected, actual);
    }

    @Test(timeout = 2000000)
    public void testCase9() {
        int age = 1;
        String candlesLine = "11";

        double expected = -1.0;
        double actual = solution.getRadix(age, candlesLine);

        assertEquals(expected, actual);
    }

}
