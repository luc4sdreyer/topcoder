import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DropCoinsTest {

    protected DropCoins solution;

    @Before
    public void setUp() {
        solution = new DropCoins();
    }

    @Test(timeout = 200000)
    public void testCase0() {
        String[] board = new String[]{".o..", "oooo", "..o."};
        int K = 3;

        int expected = 2;
        int actual = solution.getMinimum(board, K);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 200000)
    public void testCase1() {
        String[] board = new String[]{".....o", "......", "oooooo", "oooooo", "......", "o....."};
        int K = 12;

        int expected = 3;
        int actual = solution.getMinimum(board, K);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        String[] board = new String[]{"....", ".oo.", ".oo.", "...."};
        int K = 3;

        int expected = -1;
        int actual = solution.getMinimum(board, K);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        String[] board = new String[]{".......", "..ooo..", "ooooooo", ".oo.oo.", "oo...oo"};
        int K = 12;

        int expected = 4;
        int actual = solution.getMinimum(board, K);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        String[] board = new String[]{".................", ".ooooooo...oooo..", ".ooooooo..oooooo.", ".oo.......oo..oo.", ".oo.......oo..oo.", ".ooooo.....oooo..", ".ooooooo...oooo..", ".....ooo..oo..oo.", "......oo..oo..oo.", ".ooooooo..oooooo.", ".oooooo....oooo..", "................."};
        int K = 58;

        int expected = 6;
        int actual = solution.getMinimum(board, K);

        Assert.assertEquals(expected, actual);
    }

}
