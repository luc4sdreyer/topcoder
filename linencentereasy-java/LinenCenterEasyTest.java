import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LinenCenterEasyTest {

    protected LinenCenterEasy solution;

    @Before
    public void setUp() {
        solution = new LinenCenterEasy();
    }

    @Test(timeout = 200000)
    public void testCase0() {
        String S = "xy";
        int N = 2;
        int K = 1;

        int expected = 2079;
        int actual = solution.countStrings(S, N, K);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 200000)
    public void testCase1() {
        String S = "q";
        int N = 2;
        int K = 1;

        int expected = 1926;
        int actual = solution.countStrings(S, N, K);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        String S = "ababab";
        int N = 5;
        int K = 4;

        int expected = 527166180;
        int actual = solution.countStrings(S, N, K);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        String S = "fgcdx";
        int N = 10;
        int K = 3;

        int expected = 586649223;
        int actual = solution.countStrings(S, N, K);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        String S = "ghjhhhgjhjhgjhghghjhjg";
        int N = 8;
        int K = 10;

        int expected = 192161304;
        int actual = solution.countStrings(S, N, K);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase5() {
        String S = "pdpfrpfdfdpfdpfdpfpdfldfpfldpfe";
        int N = 49;
        int K = 25;

        int expected = 164673990;
        int actual = solution.countStrings(S, N, K);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase6() {
        String S = "a";
        int N = 7;
        int K = 0;

        int expected = 357828722;
        int actual = solution.countStrings(S, N, K);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase7() {
        String S = "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee";
        int N = 50;
        int K = 50;

        int expected = 960113657;
        int actual = solution.countStrings(S, N, K);

        Assert.assertEquals(expected, actual);
    }

}
