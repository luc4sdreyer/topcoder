import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MafiaGameTest {

    protected MafiaGame solution;

    @Before
    public void setUp() {
        solution = new MafiaGame();
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
        int N = 3;
        int[] decisions = new int[]{1, 1, 1};

        double expected = 1.0;
        double actual = solution.probabilityToLose(N, decisions);

        assertEquals(expected, actual);
    }

    @Test(timeout = 200000)
    public void testCase1() {
        int N = 5;
        int[] decisions = new int[]{1, 2, 3};

        double expected = 0.0;
        double actual = solution.probabilityToLose(N, decisions);

        assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int N = 20;
        int[] decisions = new int[]{1, 2, 3, 4, 5, 6, 7, 1, 2, 3, 4, 5, 6, 7, 18, 19, 0};

        double expected = 0.0;
        double actual = solution.probabilityToLose(N, decisions);

        assertEquals(expected, actual);
    }

    @Test(timeout = 200000)
    public void testCase3() {
        int N = 23;
        int[] decisions = new int[]{17, 10, 3, 14, 22, 5, 11, 10, 22, 3, 14, 5, 11, 17};

        double expected = 0.14285714285714285;
        double actual = solution.probabilityToLose(N, decisions);

        assertEquals(expected, actual);
    }

}
