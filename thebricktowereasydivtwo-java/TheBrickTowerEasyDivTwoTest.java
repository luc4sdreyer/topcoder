import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TheBrickTowerEasyDivTwoTest {

    protected TheBrickTowerEasyDivTwo solution;

    @Before
    public void setUp() {
        solution = new TheBrickTowerEasyDivTwo();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        int redCount = 1;
        int redHeight = 2;
        int blueCount = 3;
        int blueHeight = 4;

        int expected = 4;
        int actual = solution.find(redCount, redHeight, blueCount, blueHeight);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int redCount = 4;
        int redHeight = 4;
        int blueCount = 4;
        int blueHeight = 7;

        int expected = 12;
        int actual = solution.find(redCount, redHeight, blueCount, blueHeight);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int redCount = 7;
        int redHeight = 7;
        int blueCount = 4;
        int blueHeight = 4;

        int expected = 13;
        int actual = solution.find(redCount, redHeight, blueCount, blueHeight);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int redCount = 47;
        int redHeight = 47;
        int blueCount = 47;
        int blueHeight = 47;

        int expected = 94;
        int actual = solution.find(redCount, redHeight, blueCount, blueHeight);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        int redCount = 1;
        int redHeight = 2;
        int blueCount = 3;
        int blueHeight = 3;

        int expected = 4;
        int actual = solution.find(redCount, redHeight, blueCount, blueHeight);

        Assert.assertEquals(expected, actual);
    }

}
