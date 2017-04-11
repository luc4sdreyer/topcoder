import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UnrelatedPathsTest {

    protected UnrelatedPaths solution;

    @Before
    public void setUp() {
        solution = new UnrelatedPaths();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        int[] parent = new int[]{0, 1, 1, 2, 3};

        int expected = 2;
        int actual = solution.maxUnrelatedPaths(parent);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int[] parent = new int[]{0, 0, 1, 1, 2, 2};

        int expected = 4;
        int actual = solution.maxUnrelatedPaths(parent);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int[] parent = new int[]{0, 1, 2, 3};

        int expected = 1;
        int actual = solution.maxUnrelatedPaths(parent);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int[] parent = new int[]{0, 1, 1, 2, 2, 2, 4, 6, 5, 0, 10, 5, 12, 12, 10, 4, 16, 12, 5, 3, 20, 12, 11, 21, 9, 5, 1, 20, 15, 24, 6, 8, 15};

        int expected = 17;
        int actual = solution.maxUnrelatedPaths(parent);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        int[] parent = new int[]{0, 1, 1, 1, 1, 0, 2, 5, 1, 6, 7, 10, 5, 10, 8, 5, 16, 14, 8, 14, 4, 14, 15, 21, 0, 24, 11, 1, 9, 18, 13, 20, 6, 28, 19, 28, 14, 11, 38, 26, 25, 10, 23, 43};

        int expected = 19;
        int actual = solution.maxUnrelatedPaths(parent);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase5() {
        int[] parent = new int[]{0};

        int expected = 1;
        int actual = solution.maxUnrelatedPaths(parent);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase6() {
        int[] parent = new int[]{0,0};

        int expected = 2;
        int actual = solution.maxUnrelatedPaths(parent);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase7() {
        int[] parent = new int[]{0,1};

        int expected = 1;
        int actual = solution.maxUnrelatedPaths(parent);

        Assert.assertEquals(expected, actual);
    }

}
