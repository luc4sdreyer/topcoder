import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FoxAndGCDLCMTest {

    protected FoxAndGCDLCM solution;

    @Before
    public void setUp() {
        solution = new FoxAndGCDLCM();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        long G = 2L;
        long L = 20L;

        long expected = 14L;
        long actual = solution.get(G, L);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        long G = 5L;
        long L = 8L;

        long expected = -1L;
        long actual = solution.get(G, L);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        long G = 1000L;
        long L = 100L;

        long expected = -1L;
        long actual = solution.get(G, L);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        long G = 100L;
        long L = 1000L;

        long expected = 700L;
        long actual = solution.get(G, L);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        long G = 10L;
        long L = 950863963000L;

        long expected = 6298430L;
        long actual = solution.get(G, L);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 200000)
    public void testCase5() {
        long G = 1000000000000L ;
        long L = 1000000000000L;

        long expected = 2000000000000L;
        long actual = solution.get(G, L);

        Assert.assertEquals(expected, actual);
    }

}
