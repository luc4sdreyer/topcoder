import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FoxPaintingBallsTest {

    protected FoxPaintingBalls solution;

    @Before
    public void setUp() {
        solution = new FoxPaintingBalls();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        long R = 2L;
        long G = 2L;
        long B = 2L;
        int N = 3;

        long expected = 1L;
        long actual = solution.theMax(R, G, B, N);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 200000)
    public void testCase1() {
        long R = 1L;
        long G = 2L;
        long B = 3L;
        int N = 3;

        long expected = 0L;
        long actual = solution.theMax(R, G, B, N);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        long R = 8L;
        long G = 6L;
        long B = 6L;
        int N = 4;

        long expected = 2L;
        long actual = solution.theMax(R, G, B, N);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        long R = 7L;
        long G = 6L;
        long B = 7L;
        int N = 4;

        long expected = 2L;
        long actual = solution.theMax(R, G, B, N);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        long R = 100L;
        long G = 100L;
        long B = 100L;
        int N = 4;

        long expected = 30L;
        long actual = solution.theMax(R, G, B, N);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase5() {
        long R = 19330428391852493L;
        long G = 48815737582834113L;
        long B = 11451481019198930L;
        int N = 3456;

        long expected = 5750952686L;
        long actual = solution.theMax(R, G, B, N);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 200000)
    public void testCase6() {
        long R = 1L;
        long G = 1L;
        long B = 1L;
        int N = 1;

        long expected = 3L;
        long actual = solution.theMax(R, G, B, N);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase7() {
        long R = 19330428391852493L;
        long G = 48815737582834113L;
        long B = 11451481019198930L;
        int N = 3457;

        long expected = 5750952686L;
        long actual = solution.theMax(R, G, B, N);

        Assert.assertEquals(expected, actual);
    }

}
