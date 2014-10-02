import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CutTest {

    protected Cut solution;

    @Before
    public void setUp() {
        solution = new Cut();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        int[] eelLengths = new int[]{13, 20, 13};
        int maxCuts = 2;

        int expected = 3;
        int actual = solution.getMaximum(eelLengths, maxCuts);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int[] eelLengths = new int[]{5, 5, 5, 5};
        int maxCuts = 2;

        int expected = 0;
        int actual = solution.getMaximum(eelLengths, maxCuts);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        int[] eelLengths = new int[]{5, 5, 5, 5, 15};
        int maxCuts = 1;

        int expected = 1;
        int actual = solution.getMaximum(eelLengths, maxCuts);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase5() {
        int[] eelLengths = new int[]{10, 10, 10, 10, 20};
        int maxCuts = 1;

        int expected = 6;
        int actual = solution.getMaximum(eelLengths, maxCuts);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int[] eelLengths = new int[]{34, 10, 48};
        int maxCuts = 4;

        int expected = 5;
        int actual = solution.getMaximum(eelLengths, maxCuts);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int[] eelLengths = new int[]{30, 50, 30, 50};
        int maxCuts = 350;

        int expected = 16;
        int actual = solution.getMaximum(eelLengths, maxCuts);

        Assert.assertEquals(expected, actual);
    }

}
