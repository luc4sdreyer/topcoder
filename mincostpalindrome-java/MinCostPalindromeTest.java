import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MinCostPalindromeTest {

    protected MinCostPalindrome solution;

    @Before
    public void setUp() {
        solution = new MinCostPalindrome();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        String s = "oxo?xox?";
        int oCost = 3;
        int xCost = 5;

        int expected = 8;
        int actual = solution.getMinimum(s, oCost, xCost);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        String s = "x??x";
        int oCost = 9;
        int xCost = 4;

        int expected = 8;
        int actual = solution.getMinimum(s, oCost, xCost);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        String s = "ooooxxxx";
        int oCost = 12;
        int xCost = 34;

        int expected = -1;
        int actual = solution.getMinimum(s, oCost, xCost);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        String s = "oxoxooxxxxooxoxo";
        int oCost = 7;
        int xCost = 4;

        int expected = 0;
        int actual = solution.getMinimum(s, oCost, xCost);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        String s = "?o";
        int oCost = 6;
        int xCost = 2;

        int expected = 6;
        int actual = solution.getMinimum(s, oCost, xCost);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase5() {
        String s = "????????????????????";
        int oCost = 50;
        int xCost = 49;

        int expected = 980;
        int actual = solution.getMinimum(s, oCost, xCost);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase6() {
        String s = "o??oxxo?xoox?ox??x??";
        int oCost = 3;
        int xCost = 10;

        int expected = 38;
        int actual = solution.getMinimum(s, oCost, xCost);

        Assert.assertEquals(expected, actual);
    }

}
