import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ColorfulChocolatesTest {

    protected ColorfulChocolates solution;

    @Before
    public void setUp() {
        solution = new ColorfulChocolates();
    }

    @Test
    public void testCase0() {
        String chocolates = "ABCDCBC";
        int maxSwaps = 1;

        int expected = 2;
        int actual = solution.maximumSpread(chocolates, maxSwaps);

        Assert.assertEquals(expected, actual);
    }

    @Test//(timeout = 2000)
    public void testCase1() {
        String chocolates = "ABCDCBC";
        int maxSwaps = 2;

        int expected = 3;
        int actual = solution.maximumSpread(chocolates, maxSwaps);

        Assert.assertEquals(expected, actual);
    }

    @Test//(timeout = 2000)
    public void testCase2() {
        String chocolates = "ABBABABBA";
        int maxSwaps = 3;

        int expected = 4;
        int actual = solution.maximumSpread(chocolates, maxSwaps);

        Assert.assertEquals(expected, actual);
    }

    @Test//(timeout = 2000)
    public void testCase3() {
        String chocolates = "ABBABABBA";
        int maxSwaps = 4;

        int expected = 5;
        int actual = solution.maximumSpread(chocolates, maxSwaps);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        String chocolates = "QASOKZNHWNFODOQNHGQKGLIHTPJUVGKLHFZTGPDCEKSJYIWFOO";
        int maxSwaps = 77;

        int expected = 5;
        int actual = solution.maximumSpread(chocolates, maxSwaps);

        Assert.assertEquals(expected, actual);
    }
    @Test(timeout = 2000)
    public void testCase5() {
        String chocolates = "Q";
        int maxSwaps = 2500;

        int expected = 1;
        int actual = solution.maximumSpread(chocolates, maxSwaps);

        Assert.assertEquals(expected, actual);
    }
    @Test(timeout = 2000)
    public void testCase6() {
        String chocolates = "QASOKZNHWNFODOQNHGQKGLIHTPJUVGKLHFZTGPDCEKSJYIWFOOQASOKZNHWNFODOQNHGQKGLIHTPJUVGKLHFZTGPDCEKSJYIWFOO";
        int maxSwaps = 2500;

        int expected = 10;
        int actual = solution.maximumSpread(chocolates, maxSwaps);

        Assert.assertEquals(expected, actual);
    }
    @Test(timeout = 2000)
    public void testCase7() {
        String chocolates = "QA";
        int maxSwaps = 2500;

        int expected = 1;
        int actual = solution.maximumSpread(chocolates, maxSwaps);

        Assert.assertEquals(expected, actual);
    }


}
