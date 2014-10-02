import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PenguinTilesTest {

    protected PenguinTiles solution;

    @Before
    public void setUp() {
        solution = new PenguinTiles();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        String[] tiles = new String[]{"PP", "P."};

        int expected = 0;
        int actual = solution.minMoves(tiles);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        String[] tiles = new String[]{"PPPPPPPP", ".PPPPPPP"};

        int expected = 1;
        int actual = solution.minMoves(tiles);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        String[] tiles = new String[]{"PPP", "P.P", "PPP"};

        int expected = 2;
        int actual = solution.minMoves(tiles);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        String[] tiles = new String[]{"P.", "PP", "PP", "PP"};

        int expected = 1;
        int actual = solution.minMoves(tiles);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        String[] tiles = new String[]{".PPP", "PPPP", "PPPP", "PPPP"};

        int expected = 2;
        int actual = solution.minMoves(tiles);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase5() {
        String[] tiles = new String[]{"PPP.", "PPPP", "PPPP", "PPPP"};

        int expected = 1;
        int actual = solution.minMoves(tiles);

        Assert.assertEquals(expected, actual);
    }

}
