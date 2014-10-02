import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TheSwapsDivTwoTest {

    protected TheSwapsDivTwo solution;

    @Before
    public void setUp() {
        solution = new TheSwapsDivTwo();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        int[] sequence = new int[]{4, 7, 4};

        int expected = 3;
        int actual = solution.find(sequence);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int[] sequence = new int[]{1, 47};

        int expected = 1;
        int actual = solution.find(sequence);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int[] sequence = new int[]{9, 9, 9, 9};

        int expected = 1;
        int actual = solution.find(sequence);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int[] sequence = new int[]{22, 16, 36, 35, 14, 9, 33, 6, 28, 12, 18, 14, 47, 46, 29, 22, 14, 17, 4, 15, 28, 6, 39, 24, 47, 37};

        int expected = 319;
        int actual = solution.find(sequence);

        Assert.assertEquals(expected, actual);
    }

}
