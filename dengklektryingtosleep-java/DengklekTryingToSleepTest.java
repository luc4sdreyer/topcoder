import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DengklekTryingToSleepTest {

    protected DengklekTryingToSleep solution;

    @Before
    public void setUp() {
        solution = new DengklekTryingToSleep();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        int[] ducks = new int[]{5, 3, 2};

        int expected = 1;
        int actual = solution.minDucks(ducks);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int[] ducks = new int[]{58};

        int expected = 0;
        int actual = solution.minDucks(ducks);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int[] ducks = new int[]{9, 3, 6, 4};

        int expected = 3;
        int actual = solution.minDucks(ducks);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int[] ducks = new int[]{7, 4, 77, 47, 74, 44};

        int expected = 68;
        int actual = solution.minDucks(ducks);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        int[] ducks = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        int expected = 0;
        int actual = solution.minDucks(ducks);

        Assert.assertEquals(expected, actual);
    }

}
