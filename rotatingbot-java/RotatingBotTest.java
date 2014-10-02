import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RotatingBotTest {

    protected RotatingBot solution;

    @Before
    public void setUp() {
        solution = new RotatingBot();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        int[] moves = new int[]{15};

        int expected = 16;
        int actual = solution.minArea(moves);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int[] moves = new int[]{3, 10};

        int expected = 44;
        int actual = solution.minArea(moves);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int[] moves = new int[]{1, 1, 1, 1};

        int expected = -1;
        int actual = solution.minArea(moves);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 200000)
    public void testCase3() {
        int[] moves = new int[]{9, 5, 11, 10, 11, 4, 10};

        int expected = 132;
        int actual = solution.minArea(moves);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        int[] moves = new int[]{12, 1, 27, 14, 27, 12, 26, 11, 25, 10, 24, 9, 23, 8, 22, 7, 21, 6, 20, 5, 19, 4, 18, 3, 17, 2, 16, 1, 15};

        int expected = 420;
        int actual = solution.minArea(moves);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase5() {
        int[] moves = new int[]{8, 6, 6, 1};

        int expected = -1;
        int actual = solution.minArea(moves);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 200000)
    public void testCase6() {
        int[] moves = new int[]{8, 6, 6};

        int expected = 63;
        int actual = solution.minArea(moves);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase7() {
        int[] moves = new int[]{5, 4, 5, 3, 3};

        int expected = 30;
        int actual = solution.minArea(moves);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 200000)
    public void testCase8() {
        int[] moves = new int[]{4, 3, 5, 4, 6, 1};

        int expected = -1;
        int actual = solution.minArea(moves);

        Assert.assertEquals(expected, actual);
    }
    
    

}
