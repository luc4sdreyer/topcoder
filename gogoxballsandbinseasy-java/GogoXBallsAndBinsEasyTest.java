import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GogoXBallsAndBinsEasyTest {

    protected GogoXBallsAndBinsEasy solution;

    @Before
    public void setUp() {
        solution = new GogoXBallsAndBinsEasy();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        int[] T = new int[]{0, 2, 5};

        int expected = 5;
        int actual = solution.solve(T);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int[] T = new int[]{5, 6, 7, 8};

        int expected = 4;
        int actual = solution.solve(T);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int[] T = new int[]{0, 1, 2, 10};

        int expected = 11;
        int actual = solution.solve(T);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int[] T = new int[]{1, 2, 3, 4, 5, 6, 7, 8};

        int expected = 16;
        int actual = solution.solve(T);

        Assert.assertEquals(expected, actual);
    }

}
