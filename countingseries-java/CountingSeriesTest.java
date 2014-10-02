import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CountingSeriesTest {

    protected CountingSeries solution;

    @Before
    public void setUp() {
        solution = new CountingSeries();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        long a = 1L;
        long b = 1L;
        long c = 1L;
        long d = 2L;
        long upperBound = 1000L;

        long expected = 1000L;
        long actual = solution.countThem(a, b, c, d, upperBound);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 200000)
    public void testCase1() {
        long a = 3L;
        long b = 3L;
        long c = 1L;
        long d = 2L;
        long upperBound = 1000L;

        long expected = 343L;
        long actual = solution.countThem(a, b, c, d, upperBound);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 200000)
    public void testCase2() {
        long a = 40L;
        long b = 77L;
        long c = 40L;
        long d = 100000L;
        long upperBound = 40L;

        long expected = 1L;
        long actual = solution.countThem(a, b, c, d, upperBound);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 200000)
    public void testCase3() {
        long a = 452L;
        long b = 24L;
        long c = 4L;
        long d = 5L;
        long upperBound = 600L;

        long expected = 10L;
        long actual = solution.countThem(a, b, c, d, upperBound);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        long a = 234L;
        long b = 24L;
        long c = 377L;
        long d = 1L;
        long upperBound = 10000L;

        long expected = 408L;
        long actual = solution.countThem(a, b, c, d, upperBound);

        Assert.assertEquals(expected, actual);
    }

}
