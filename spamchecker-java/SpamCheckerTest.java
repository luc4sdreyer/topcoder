import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SpamCheckerTest {

    protected SpamChecker solution;

    @Before
    public void setUp() {
        solution = new SpamChecker();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        String judgeLog = "ooooxxxo";
        int good = 2;
        int bad = 3;

        String expected = "SPAM";
        String actual = solution.spamCheck(judgeLog, good, bad);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        String judgeLog = "ooooxxxo";
        int good = 3;
        int bad = 4;

        String expected = "NOT SPAM";
        String actual = solution.spamCheck(judgeLog, good, bad);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        String judgeLog = "xooooooooooooooooooooooooooo";
        int good = 1000;
        int bad = 1;

        String expected = "SPAM";
        String actual = solution.spamCheck(judgeLog, good, bad);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        String judgeLog = "oxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
        int good = 1000;
        int bad = 1;

        String expected = "NOT SPAM";
        String actual = solution.spamCheck(judgeLog, good, bad);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        String judgeLog = "ooxoxoxooxoxxoxoxooxoxoxoxxoxx";
        int good = 15;
        int bad = 17;

        String expected = "SPAM";
        String actual = solution.spamCheck(judgeLog, good, bad);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase5() {
        String judgeLog = "oooxoxoxoxoxoxooxooxoxooxo";
        int good = 16;
        int bad = 18;

        String expected = "NOT SPAM";
        String actual = solution.spamCheck(judgeLog, good, bad);

        Assert.assertEquals(expected, actual);
    }

}
