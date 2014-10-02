import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CollectingUsualPostmarksTest {

    protected CollectingUsualPostmarks solution;

    @Before
    public void setUp() {
        solution = new CollectingUsualPostmarks();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        int[] prices = new int[]{13, 10, 14, 20};
        int[] have = new int[]{3, 0, 2, 1};

        int expected = 4;
        int actual = solution.numberOfPostmarks(prices, have);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int[] prices = new int[]{7, 5, 9, 7};
        int[] have = new int[]{};

        int expected = 0;
        int actual = solution.numberOfPostmarks(prices, have);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int[] prices = new int[]{4, 13, 9, 1, 5};
        int[] have = new int[]{1, 3, 2};

        int expected = 4;
        int actual = solution.numberOfPostmarks(prices, have);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int[] prices = new int[]{16, 32, 13, 2, 17, 10, 8, 8, 20, 17};
        int[] have = new int[]{7, 0, 4, 1, 6, 8};

        int expected = 8;
        int actual = solution.numberOfPostmarks(prices, have);

        Assert.assertEquals(expected, actual);
    }

}
