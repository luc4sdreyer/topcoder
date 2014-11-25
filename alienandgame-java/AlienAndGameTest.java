import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AlienAndGameTest {

    protected AlienAndGame solution;

    @Before
    public void setUp() {
        solution = new AlienAndGame();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        String[] board = new String[]{"BB", "WW"};

        int expected = 4;
        int actual = solution.getNumber(board);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        String[] board = new String[]{"W"};

        int expected = 1;
        int actual = solution.getNumber(board);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        String[] board = new String[]{"WBBB", "WBBB", "WWWW"};

        int expected = 9;
        int actual = solution.getNumber(board);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        String[] board = new String[]{"W", "B", "W", "W", "W"};

        int expected = 1;
        int actual = solution.getNumber(board);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        String[] board = new String[]{"BWBBWBB", "WWBWWBW", "BBBBBBW", "WBBBBWB", "BBWWWWB", "WWWWWWW", "BBWWBBB"};

        int expected = 9;
        int actual = solution.getNumber(board);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase5() {
        String[] board = new String[]{"BBB", "BBB", "BBB"};

        int expected = 9;
        int actual = solution.getNumber(board);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase6() {
        String[] board = new String[]{"WWW", "WWW", "WWW"};

        int expected = 9;
        int actual = solution.getNumber(board);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase7() {
        String[] board = new String[50];
        for (int i = 0; i < board.length; i++) {
			board[i] = "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW";
		}		

        int expected = 50*50;
        int actual = solution.getNumber(board);

        Assert.assertEquals(expected, actual);
    }


}
