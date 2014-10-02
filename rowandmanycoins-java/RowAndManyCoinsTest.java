import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RowAndManyCoinsTest {

    protected RowAndManyCoins solution;

    @Before
    public void setUp() {
        solution = new RowAndManyCoins();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        String cells = "ABBB";

        String expected = "Alice";
        String actual = solution.getWinner(cells);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        String cells = "BBBB";

        String expected = "Bob";
        String actual = solution.getWinner(cells);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        String cells = "BA";

        String expected = "Alice";
        String actual = solution.getWinner(cells);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        String cells = "BABBABBB";

        String expected = "Bob";
        String actual = solution.getWinner(cells);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        String cells = "ABABBBABAABBAA";

        String expected = "Alice";
        String actual = solution.getWinner(cells);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase5() {
        String cells = "BBBAAABBAAABBB";

        String expected = "Bob";
        String actual = solution.getWinner(cells);

        Assert.assertEquals(expected, actual);
    }

}
