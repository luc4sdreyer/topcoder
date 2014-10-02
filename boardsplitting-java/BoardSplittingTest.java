import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BoardSplittingTest {

    protected BoardSplitting solution;

    @Before
    public void setUp() {
        solution = new BoardSplitting();
    }

    @Test(timeout = 200000)
    public void testCase0() {
        int desiredLength = 5;
        int desiredCount = 4;
        int actualLength = 4;

        int expected = 3;
        int actual = solution.minimumCuts(desiredLength, desiredCount, actualLength);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int desiredLength = 6;
        int desiredCount = 100;
        int actualLength = 3;

        int expected = 0;
        int actual = solution.minimumCuts(desiredLength, desiredCount, actualLength);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int desiredLength = 500;
        int desiredCount = 5;
        int actualLength = 1000;

        int expected = 3;
        int actual = solution.minimumCuts(desiredLength, desiredCount, actualLength);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int desiredLength = 314;
        int desiredCount = 159;
        int actualLength = 26;

        int expected = 147;
        int actual = solution.minimumCuts(desiredLength, desiredCount, actualLength);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        int desiredLength = 5;
        int desiredCount = 3;
        int actualLength = 7;

        int expected = 3;
        int actual = solution.minimumCuts(desiredLength, desiredCount, actualLength);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase5() {
        int desiredLength = 5;
        int desiredCount = 3;
        int actualLength = 8;

        int expected = 3;
        int actual = solution.minimumCuts(desiredLength, desiredCount, actualLength);

        Assert.assertEquals(expected, actual);
    }

}
