import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TreeAndCycleTest {

    protected TreeAndCycle solution;

    @Before
    public void setUp() {
        solution = new TreeAndCycle();
    }

    @Test(timeout = 2000000)
    public void testCase0() {
        int[] costV = new int[]{7, 2, 5, 8};
        int[] pre = new int[]{0, 1, 2};
        int[] costE = new int[]{6, 4, 3};

        int expected = 15;
        int actual = solution.minimize(costV, pre, costE);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int[] costV = new int[]{100, 5, 9, 8};
        int[] pre = new int[]{0, 0, 0};
        int[] costE = new int[]{6, 2, 2};

        int expected = 32;
        int actual = solution.minimize(costV, pre, costE);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int[] costV = new int[]{10, 20, 30, 40, 50, 60, 70, 80, 90};
        int[] pre = new int[]{0, 1, 2, 2, 3, 4, 5, 7};
        int[] costE = new int[]{5, 15, 25, 35, 45, 55, 65, 75};

        int expected = 205;
        int actual = solution.minimize(costV, pre, costE);

        Assert.assertEquals(expected, actual);
    }

}
