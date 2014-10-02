import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ShippingCubesTest {

    protected ShippingCubes solution;

    @Before
    public void setUp() {
        solution = new ShippingCubes();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        int N = 1;

        int expected = 3;
        int actual = solution.minimalCost(N);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 200000)
    public void testCase1() {
        int N = 6;

        int expected = 6;
        int actual = solution.minimalCost(N);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int N = 7;

        int expected = 9;
        int actual = solution.minimalCost(N);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int N = 200;

        int expected = 18;
        int actual = solution.minimalCost(N);

        Assert.assertEquals(expected, actual);
    }

}
