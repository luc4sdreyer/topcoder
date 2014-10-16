import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MagicalGirlLevelTwoDivTwoTest {

    protected MagicalGirlLevelTwoDivTwo solution;

    @Before
    public void setUp() {
        solution = new MagicalGirlLevelTwoDivTwo();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        int[] jumpTypes = new int[]{2};
        int x = 5;
        int y = 4;

        String expected = "YES";
        String actual = solution.isReachable(jumpTypes, x, y);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int[] jumpTypes = new int[]{3};
        int x = 5;
        int y = 4;

        String expected = "NO";
        String actual = solution.isReachable(jumpTypes, x, y);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int[] jumpTypes = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int x = -30;
        int y = 27;

        String expected = "YES";
        String actual = solution.isReachable(jumpTypes, x, y);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int[] jumpTypes = new int[]{29};
        int x = 29;
        int y = 0;

        String expected = "NO";
        String actual = solution.isReachable(jumpTypes, x, y);

        Assert.assertEquals(expected, actual);
    }


    @Test(timeout = 2000)
    public void testCase4() {
        int[] jumpTypes = new int[]{1, 29};
        int x = 30;
        int y = 29;

        String expected = "NO";
        String actual = solution.isReachable(jumpTypes, x, y);

        Assert.assertEquals(expected, actual);
    }
}
