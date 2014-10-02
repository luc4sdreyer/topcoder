import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class XorBoardDivTwoTest {

    protected XorBoardDivTwo solution;

    @Before
    public void setUp() {
        solution = new XorBoardDivTwo();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        String[] board = new String[]{"101", "010", "101"};

        int expected = 9;
        int actual = solution.theMax(board);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        String[] board = new String[]{"111", "111", "111"};

        int expected = 5;
        int actual = solution.theMax(board);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        String[] board = new String[]{"0101001", "1101011"};

        int expected = 9;
        int actual = solution.theMax(board);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        String[] board = new String[]{"000", "001", "010", "011", "100", "101", "110", "111"};

        int expected = 15;
        int actual = solution.theMax(board);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        String[] board = new String[]{"000000000000000000000000", "011111100111111001111110", "010000000100000001000000", "010000000100000001000000", "010000000100000001000000", "011111100111111001111110", "000000100000001000000010", "000000100000001000000010", "000000100000001000000010", "011111100111111001111110", "000000000000000000000000"};

        int expected = 105;
        int actual = solution.theMax(board);

        Assert.assertEquals(expected, actual);
    }

}
