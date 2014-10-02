import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EllysFigurinesTest {

    protected EllysFigurines solution;

    @Before
    public void setUp() {
        solution = new EllysFigurines();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        String[] board = new String[]{".X.X.", "XX..X", ".XXX.", "...X.", ".X.XX"};
        int R = 1;
        int C = 2;

        int expected = 3;
        int actual = solution.getMoves(board, R, C);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        String[] board = new String[]{".X.X.", "XX..X", ".X.X.", "...X.", ".X.XX"};
        int R = 2;
        int C = 2;

        int expected = 2;
        int actual = solution.getMoves(board, R, C);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        String[] board = new String[]{"XXXXXXX"};
        int R = 2;
        int C = 3;

        int expected = 1;
        int actual = solution.getMoves(board, R, C);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        String[] board = new String[]{"XXXXX", "X....", "XXX..", "X....", "XXXXX"};
        int R = 1;
        int C = 1;

        int expected = 4;
        int actual = solution.getMoves(board, R, C);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000000)
    public void testCase4() {
        String[] board = new String[]{"XXX..XXX..XXX.", ".X..X....X...X", ".X..X....X...X", ".X..X....X...X", ".X...XXX..XXX.", "..............", "...XX...XXX...", "....X......X..", "....X....XXX..", "....X......X..", "...XXX..XXX..."};
        int R = 1;
        int C = 2;

        int expected = 7;
        int actual = solution.getMoves(board, R, C);

        Assert.assertEquals(expected, actual);
    }

}
