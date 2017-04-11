import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DubsTest {

    protected Dubs solution;

    @Before
    public void setUp() {
        solution = new Dubs();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        long L = 10L;
        long R = 10L;

        long expected = 0L;
        long actual = solution.count(L, R);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 200000)
    public void testCase1() {
        long L = 10L;
        long R = 20L;

        long expected = 1L;
        long actual = solution.count(L, R);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        long L = 49L;
        long R = 101L;

        long expected = 6L;
        long actual = solution.count(L, R);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        long L = 1111111L;
        long R = 111111111L;

        long expected = 11000001L;
        long actual = solution.count(L, R);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        long L = 91750002841L;
        long R = 91751522033L;

        long expected = 151920L;
        long actual = solution.count(L, R);

        Assert.assertEquals(expected, actual);
    }

}
