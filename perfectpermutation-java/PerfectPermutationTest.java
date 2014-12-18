import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PerfectPermutationTest {

    protected PerfectPermutation solution;

    @Before
    public void setUp() {
        solution = new PerfectPermutation();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        int[] P = new int[]{2, 0, 1};

        int expected = 0;
        int actual = solution.reorder(P);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int[] P = new int[]{2, 0, 1, 4, 3};

        int expected = 2;
        int actual = solution.reorder(P);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int[] P = new int[]{2, 3, 0, 1};

        int expected = 2;
        int actual = solution.reorder(P);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int[] P = new int[]{0, 5, 3, 2, 1, 4};

        int expected = 3;
        int actual = solution.reorder(P);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        int[] P = new int[]{4, 2, 6, 0, 3, 5, 9, 7, 8, 1};

        int expected = 5;
        int actual = solution.reorder(P);

        Assert.assertEquals(expected, actual);
    }

}
