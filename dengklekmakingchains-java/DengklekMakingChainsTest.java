import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DengklekMakingChainsTest {

    protected DengklekMakingChains solution;

    @Before
    public void setUp() {
        solution = new DengklekMakingChains();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        String[] chains = new String[]{".15", "7..", "402", "..3"};

        int expected = 19;
        int actual = solution.maxBeauty(chains);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        String[] chains = new String[]{"..1", "7..", "567", "24.", "8..", "234"};

        int expected = 36;
        int actual = solution.maxBeauty(chains);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        String[] chains = new String[]{"...", "..."};

        int expected = 0;
        int actual = solution.maxBeauty(chains);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        String[] chains = new String[]{"16.", "9.8", ".24", "52.", "3.1", "532", "4.4", "111"};

        int expected = 28;
        int actual = solution.maxBeauty(chains);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        String[] chains = new String[]{"..1", "3..", "2..", ".7."};

        int expected = 7;
        int actual = solution.maxBeauty(chains);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000000)
    public void testCase5() {
        String[] chains = new String[]{"412", "..7", ".58", "7.8", "32.", "6..", "351", "3.9", "985", "...", ".46"};

        int expected = 58;
        int actual = solution.maxBeauty(chains);

        Assert.assertEquals(expected, actual);
    }

}
