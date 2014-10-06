import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ThreeTeleportsTest {

    protected ThreeTeleports solution;

    @Before
    public void setUp() {
        solution = new ThreeTeleports();
    }

    @Test(timeout = 200000)
    public void testCase0() {
        int xMe = 3;
        int yMe = 3;
        int xHome = 4;
        int yHome = 5;
        String[] teleports = new String[]{"1000 1001 1000 1002", "1000 1003 1000 1004", "1000 1005 1000 1006"};

        int expected = 3;
        int actual = solution.shortestDistance(xMe, yMe, xHome, yHome, teleports);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int xMe = 0;
        int yMe = 0;
        int xHome = 20;
        int yHome = 20;
        String[] teleports = new String[]{"1 1 18 20", "1000 1003 1000 1004", "1000 1005 1000 1006"};

        int expected = 14;
        int actual = solution.shortestDistance(xMe, yMe, xHome, yHome, teleports);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int xMe = 0;
        int yMe = 0;
        int xHome = 20;
        int yHome = 20;
        String[] teleports = new String[]{"1000 1003 1000 1004", "18 20 1 1", "1000 1005 1000 1006"};

        int expected = 14;
        int actual = solution.shortestDistance(xMe, yMe, xHome, yHome, teleports);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int xMe = 10;
        int yMe = 10;
        int xHome = 10000;
        int yHome = 20000;
        String[] teleports = new String[]{"1000 1003 1000 1004", "3 3 10004 20002", "1000 1005 1000 1006"};

        int expected = 30;
        int actual = solution.shortestDistance(xMe, yMe, xHome, yHome, teleports);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        int xMe = 3;
        int yMe = 7;
        int xHome = 10000;
        int yHome = 30000;
        String[] teleports = new String[]{"3 10 5200 4900", "12212 8699 9999 30011", "12200 8701 5203 4845"};

        int expected = 117;
        int actual = solution.shortestDistance(xMe, yMe, xHome, yHome, teleports);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase5() {
        int xMe = 0;
        int yMe = 0;
        int xHome = 1000000000;
        int yHome = 1000000000;
        String[] teleports = new String[]{"0 1 0 999999999", "1 1000000000 999999999 0", "1000000000 1 1000000000 999999999"};

        int expected = 36;
        int actual = solution.shortestDistance(xMe, yMe, xHome, yHome, teleports);

        Assert.assertEquals(expected, actual);
    }
    


    @Test(timeout = 200000)
    public void testCase6() {
        int xMe = 0;
        int yMe = 0;
        int xHome = 20;
        int yHome = 20;
        String[] teleports = new String[]{"1 1 18 20"};

        int expected = 14;
        int actual = solution.shortestDistance(xMe, yMe, xHome, yHome, teleports);

        Assert.assertEquals(expected, actual);
    }

}
