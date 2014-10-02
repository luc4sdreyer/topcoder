import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BlockTowerTest {

    protected BlockTower solution;

    @Before
    public void setUp() {
        solution = new BlockTower();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        int[] blockHeights = new int[]{4, 7};

        int expected = 11;
        int actual = solution.getTallest(blockHeights);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int[] blockHeights = new int[]{7, 4};

        int expected = 7;
        int actual = solution.getTallest(blockHeights);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int[] blockHeights = new int[]{7};

        int expected = 7;
        int actual = solution.getTallest(blockHeights);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int[] blockHeights = new int[]{4};

        int expected = 4;
        int actual = solution.getTallest(blockHeights);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 200000)
    public void testCase4() {
        int[] blockHeights = new int[]{48, 1, 50, 1, 50, 1, 48};

        int expected = 196;
        int actual = solution.getTallest(blockHeights);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase5() {
        int[] blockHeights = new int[]{49, 2, 49, 2, 49};

        int expected = 147;
        int actual = solution.getTallest(blockHeights);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase6() {
        int[] blockHeights = new int[]{44, 3, 44, 3, 44, 47, 2, 47, 2, 47, 2};

        int expected = 273;
        int actual = solution.getTallest(blockHeights);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 200000)
    public void testCase7() {
        int[] blockHeights = new int[]{44, 3, 44, 3, 44, 47, 2, 47, 46, 46, 46, 47, 2};

        int expected = 273;
        int actual = solution.getTallest(blockHeights);

        Assert.assertEquals(expected, actual);
    }

}
