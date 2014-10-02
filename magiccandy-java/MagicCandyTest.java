import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MagicCandyTest {

    protected MagicCandy solution;

    @Before
    public void setUp() {
        solution = new MagicCandy();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        int n = 5;

        int expected = 5;
        int actual = solution.whichOne(n);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int n = 9;

        int expected = 7;
        int actual = solution.whichOne(n);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int n = 20;

        int expected = 17;
        int actual = solution.whichOne(n);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int n = 5265;

        int expected = 5257;
        int actual = solution.whichOne(n);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        int n = 20111223;

        int expected = 20110741;
        int actual = solution.whichOne(n);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase5() {
        int n = 1;

        int expected = 1;
        int actual = solution.whichOne(n);

        Assert.assertEquals(expected, actual);
    }

}
