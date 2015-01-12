import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LateProfessorTest {

    protected LateProfessor solution;

    @Before
    public void setUp() {
        solution = new LateProfessor();
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
        int waitTime = 20;
        int walkTime = 30;
        int lateTime = 10;
        int bestArrival = 0;
        int worstArrival = 50;

        double expected = 0.4;
        double actual = solution.getProbability(waitTime, walkTime, lateTime, bestArrival, worstArrival);

        assertEquals(expected, actual);
    }

    @Test(timeout = 2000000)
    public void testCase1() {
        int waitTime = 20;
        int walkTime = 30;
        int lateTime = 10;
        int bestArrival = 30;
        int worstArrival = 100;

        double expected = 0.42857142857142855;
        double actual = solution.getProbability(waitTime, walkTime, lateTime, bestArrival, worstArrival);

        assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int waitTime = 20;
        int walkTime = 40;
        int lateTime = 50;
        int bestArrival = 0;
        int worstArrival = 300;

        double expected = 0.0;
        double actual = solution.getProbability(waitTime, walkTime, lateTime, bestArrival, worstArrival);

        assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int waitTime = 101;
        int walkTime = 230;
        int lateTime = 10;
        int bestArrival = 654;
        int worstArrival = 17890;

        double expected = 0.6637270828498492;
        double actual = solution.getProbability(waitTime, walkTime, lateTime, bestArrival, worstArrival);

        assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        int waitTime = 20;
        int walkTime = 30;
        int lateTime = 10;
        int bestArrival = 90;
        int worstArrival = 90;

        double expected = 1.0;
        double actual = solution.getProbability(waitTime, walkTime, lateTime, bestArrival, worstArrival);

        assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase5() {
        int waitTime = 1000;
        int walkTime = 600;
        int lateTime = 1;
        int bestArrival = 17000;
        int worstArrival = 17000;

        double expected = 0.0;
        double actual = solution.getProbability(waitTime, walkTime, lateTime, bestArrival, worstArrival);

        assertEquals(expected, actual);
    }

}
