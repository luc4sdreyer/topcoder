import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BallAndHatsTest {

    protected BallAndHats solution;

    @Before
    public void setUp() {
        solution = new BallAndHats();
    }

    @Test(timeout = 200000)
    public void testCase0() {
        String hats = ".o.";
        int numSwaps = 1;

        int expected = 0;
        int actual = solution.getHat(hats, numSwaps);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        String hats = "..o";
        int numSwaps = 0;

        int expected = 2;
        int actual = solution.getHat(hats, numSwaps);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        String hats = "o..";
        int numSwaps = 1;

        int expected = 1;
        int actual = solution.getHat(hats, numSwaps);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        String hats = "..o";
        int numSwaps = 2;

        int expected = 0;
        int actual = solution.getHat(hats, numSwaps);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        String hats = "o..";
        int numSwaps = 101;

        int expected = 1;
        int actual = solution.getHat(hats, numSwaps);

        Assert.assertEquals(expected, actual);
    }

}
