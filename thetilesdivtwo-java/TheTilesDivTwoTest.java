import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TheTilesDivTwoTest {

    protected TheTilesDivTwo solution;

    @Before
    public void setUp() {
        solution = new TheTilesDivTwo();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        String[] board = new String[]{"X.X", "...", "X.X"};

        int expected = 1;
        int actual = solution.find(board);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        String[] board = new String[]{"...", "...", "..."};

        int expected = 2;
        int actual = solution.find(board);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        String[] board = new String[]{"......X.X.XXX.X.XX."};

        int expected = 0;
        int actual = solution.find(board);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        String[] board = new String[]{"X.....XXX.XX..XXXXXXXXX...X.XX.XX....X", ".XXXX..X..XXXXXXXX....XX.X.X.X.....XXX", "....XX....X.XX..X.X...XX.X..XXXXXXX..X", "XX.XXXXX.X.X..X..XX.XXX..XX...XXX.X..."};

        int expected = 13;
        int actual = solution.find(board);

        Assert.assertEquals(expected, actual);
    }

}
