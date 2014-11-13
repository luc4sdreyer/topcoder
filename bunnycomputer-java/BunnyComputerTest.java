import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BunnyComputerTest {

    protected BunnyComputer solution;

    @Before
    public void setUp() {
        solution = new BunnyComputer();
    }

    @Test(timeout = 200000)
    public void testCase0() {
        int[] preference = new int[]{3, 1, 4, 1, 5, 9, 2, 6};
        int k = 2;

        int expected = 28;
        int actual = solution.getMaximum(preference, k);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int[] preference = new int[]{3, 1, 4, 1, 5, 9, 2, 6};
        int k = 1;

        int expected = 31;
        int actual = solution.getMaximum(preference, k);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int[] preference = new int[]{1, 2, 3, 4, 5, 6};
        int k = 3;

        int expected = 14;
        int actual = solution.getMaximum(preference, k);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int[] preference = new int[]{487, 2010};
        int k = 2;

        int expected = 0;
        int actual = solution.getMaximum(preference, k);

        Assert.assertEquals(expected, actual);
    }

}
