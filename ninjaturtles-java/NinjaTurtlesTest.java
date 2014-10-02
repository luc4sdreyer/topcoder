import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class NinjaTurtlesTest {

    protected NinjaTurtles solution;

    @Before
    public void setUp() {
        solution = new NinjaTurtles();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        int P = 5;
        int K = 4;

        int expected = 6;
        int actual = solution.countOpponents(P, K);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int P = 1;
        int K = 4;

        int expected = 3;
        int actual = solution.countOpponents(P, K);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int P = 13;
        int K = 6;

        int expected = -1;
        int actual = solution.countOpponents(P, K);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int P = 13;
        int K = 17;

        int expected = 30;
        int actual = solution.countOpponents(P, K);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        int P = 122;
        int K = 21;

        int expected = 258;
        int actual = solution.countOpponents(P, K);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase5() {
        int P = 1000000;
        int K = 99;

        int expected = 258;
        int actual = solution.countOpponents(P, K);

        Assert.assertEquals(expected, actual);
    }


}
