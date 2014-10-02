import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ContestWinnerTest {

    protected ContestWinner solution;

    @Before
    public void setUp() {
        solution = new ContestWinner();
    }

    @Test(timeout = 200000)
    public void testCase0() {
        int[] events = new int[]{4,7,4,1};

        int expected = 4;
        int actual = solution.getWinner(events);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int[] events = new int[]{10, 20, 30, 40, 50};

        int expected = 10;
        int actual = solution.getWinner(events);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int[] events = new int[]{123, 123, 456, 456, 456, 123};

        int expected = 456;
        int actual = solution.getWinner(events);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int[] events = new int[]{1, 2, 2, 3, 3, 3, 4, 4, 4, 4};

        int expected = 4;
        int actual = solution.getWinner(events);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        int[] events = new int[]{1, 1, 2, 2, 1, 2, 1, 2, 1, 2};

        int expected = 1;
        int actual = solution.getWinner(events);

        Assert.assertEquals(expected, actual);
    }
    @Test(timeout = 2000)
    public void testCase5() {
        int[] events = new int[]{9};

        int expected = 9;
        int actual = solution.getWinner(events);

        Assert.assertEquals(expected, actual);
    }

}
