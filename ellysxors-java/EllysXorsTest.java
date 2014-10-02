import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EllysXorsTest {

    protected EllysXors solution;

    @Before
    public void setUp() {
        solution = new EllysXors();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        long L = 3L;
        long R = 10L;

        long expected = 8L;
        long actual = solution.getXor(L, R);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        long L = 5L;
        long R = 5L;

        long expected = 5L;
        long actual = solution.getXor(L, R);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        long L = 13L;
        long R = 42L;

        long expected = 39L;
        long actual = solution.getXor(L, R);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        long L = 666L;
        long R = 1337L;

        long expected = 0L;
        long actual = solution.getXor(L, R);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        long L = 1234567L;
        long R = 89101112L;

        long expected = 89998783L;
        long actual = solution.getXor(L, R);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 100000)
    public void testCase5() {
        long L = 1L;
        long R = 400000000L;

        long expected = 400000000L;
        long actual = solution.getXor(L, R);

        Assert.assertEquals(expected, actual);
    }

}
