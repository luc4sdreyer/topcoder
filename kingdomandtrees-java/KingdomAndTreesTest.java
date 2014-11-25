import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class KingdomAndTreesTest {

    protected KingdomAndTrees solution;

    @Before
    public void setUp() {
        solution = new KingdomAndTrees();
    }

    @Test(timeout = 2000000)
    public void testCase0() {
        int[] heights = new int[]{9, 5, 11};

        int expected = 3;
        int actual = solution.minLevel(heights);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int[] heights = new int[]{5, 8};

        int expected = 0;
        int actual = solution.minLevel(heights);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int[] heights = new int[]{1, 1, 1, 1, 1};

        int expected = 4;
        int actual = solution.minLevel(heights);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int[] heights = new int[]{548, 47, 58, 250, 2012};

        int expected = 251;
        int actual = solution.minLevel(heights);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        int[] heights = new int[]{4,2,3,1,8};

        int expected = 3;
        int actual = solution.minLevel(heights);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase5() {
        int[] heights = new int[]{4,4,4,8,8};

        int expected = 1;
        int actual = solution.minLevel(heights);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase6() {
        int[] heights = new int[]{4,4,8,8,4};

        int expected = 3;
        int actual = solution.minLevel(heights);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase7() {
        int[] heights = new int[50];
        for (int i = 0; i < heights.length; i++) {
			heights[i] = 1000000000;
		}

        int expected = 25;
        int actual = solution.minLevel(heights);

        Assert.assertEquals(expected, actual);
    }

}
