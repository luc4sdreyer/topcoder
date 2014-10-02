import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MagicDiamondsTest {

    protected MagicDiamonds solution;

    @Before
    public void setUp() {
        solution = new MagicDiamonds();
    }

    @Test(timeout = 200000)
    public void testCase0() {
        long n = 2L;

        long expected = 2L;
        long actual = solution.minimalTransfer(n);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        long n = 4294967297L;

        long expected = 1L;
        long actual = solution.minimalTransfer(n);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        long n = 2147483647L;

        long expected = 2L;
        long actual = solution.minimalTransfer(n);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        long n = 1L;

        long expected = 1L;
        long actual = solution.minimalTransfer(n);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        long n = 999999999989L;

        long expected = 2L;
        long actual = solution.minimalTransfer(n);

        Assert.assertEquals(expected, actual);
    }

}
