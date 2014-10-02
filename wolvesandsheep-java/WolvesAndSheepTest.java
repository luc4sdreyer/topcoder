import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class WolvesAndSheepTest {

    protected WolvesAndSheep solution;

    @Before
    public void setUp() {
        solution = new WolvesAndSheep();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        String[] field = new String[]{"W.WSS", "WW.S.", ".SSS.", "SSS.S", ".SS.S"};

        int expected = 2;
        int actual = solution.getmin(field);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        String[] field = new String[]{".....", ".....", "....."};

        int expected = 0;
        int actual = solution.getmin(field);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 200000)
    public void testCase2() {
        String[] field = new String[]{".SS", "...", "S..", "W.W"};

        int expected = 1;
        int actual = solution.getmin(field);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        String[] field = new String[]{"WWWSWWSSWWW", "WWSWW.SSWWW", "WS.WSWWWWS."};

        int expected = 10;
        int actual = solution.getmin(field);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000000)
    public void testCase4() {
        String[] field = new String[]{".W.S.W.W", "W.W.S.W.", ".S.S.W.W", "S.S.S.W.", ".S.W.W.S", "S.S.W.W.", ".W.W.W.S", "W.W.S.S."};

        int expected = 7;
        int actual = solution.getmin(field);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 200000)
    public void testCase5() {
        String[] field = new String[]{"W.SSWWSSSW.SS", ".SSSSW.SSWWWW", ".WWWWS.WSSWWS", "SS.WSS..W.WWS", "WSSS.SSWS.W.S", "WSS.WS...WWWS", "S.WW.S.SWWWSW", "WSSSS.SSW...S", "S.WWSW.WWSWSW", ".WSSS.WWSWWWS", "..SSSS.WWWSSS", "SSWSWWS.W.SSW", "S.WSWS..WSSS.", "WS....W..WSS."};

        int expected = 24;
        int actual = solution.getmin(field);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase6() {
        String[] field = new String[]{"WW..SS", "WW..SS", "......", "......", "SS..WW", "SS..WW"};

        int expected = 2;
        int actual = solution.getmin(field);

        Assert.assertEquals(expected, actual);
    }

}
