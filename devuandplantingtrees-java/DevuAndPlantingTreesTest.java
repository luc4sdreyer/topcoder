import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DevuAndPlantingTreesTest {

    protected DevuAndPlantingTrees solution;

    @Before
    public void setUp() {
        solution = new DevuAndPlantingTrees();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        String[] garden = new String[]{"..", ".."};

        int expected = 1;
        int actual = solution.maximumTreesDevuCanGrow(garden);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        String[] garden = new String[]{"..", ".*"};

        int expected = 1;
        int actual = solution.maximumTreesDevuCanGrow(garden);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 200000)
    public void testCase2() {
        String[] garden = new String[]{"...", "..*"};

        int expected = 2;
        int actual = solution.maximumTreesDevuCanGrow(garden);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        String[] garden = new String[]{".....*..........", ".*.......*.*..*."};

        int expected = 7;
        int actual = solution.maximumTreesDevuCanGrow(garden);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        String[] garden = new String[]{"....*.*.*...........*........", "*..........*..*.*.*....*...*."};

        int expected = 13;
        int actual = solution.maximumTreesDevuCanGrow(garden);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase5() {
        String[] garden = new String[]{".....*..*..........*............................*", "*..*.............*...*.*.*.*..*.....*.*...*...*.."};

        int expected = 23;
        int actual = solution.maximumTreesDevuCanGrow(garden);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase6() {
        String[] garden = new String[]{".", "."};

        int expected = 1;
        int actual = solution.maximumTreesDevuCanGrow(garden);

        Assert.assertEquals(expected, actual);
    }

}
