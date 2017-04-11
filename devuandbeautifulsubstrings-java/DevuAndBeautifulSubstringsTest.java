import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DevuAndBeautifulSubstringsTest {

    protected DevuAndBeautifulSubstrings solution;

    @Before
    public void setUp() {
        solution = new DevuAndBeautifulSubstrings();
    }

    @Test(timeout = 2000000)
    public void testCase0() {
        int n = 2;
        int cnt = 2;

        long expected = 2L;
        long actual = solution.countBeautifulSubstrings(n, cnt);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int n = 2;
        int cnt = 1;

        long expected = 0L;
        long actual = solution.countBeautifulSubstrings(n, cnt);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int n = 3;
        int cnt = 4;

        long expected = 4L;
        long actual = solution.countBeautifulSubstrings(n, cnt);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int n = 15;
        int cnt = 35;

        long expected = 642L;
        long actual = solution.countBeautifulSubstrings(n, cnt);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        int n = 40;
        int cnt = 820;

        long expected = 2L;
        long actual = solution.countBeautifulSubstrings(n, cnt);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase5() {
        int n = 50;
        int cnt = 94;

        long expected = 32357325751902L;
        long actual = solution.countBeautifulSubstrings(n, cnt);

        Assert.assertEquals(expected, actual);
    }

}
