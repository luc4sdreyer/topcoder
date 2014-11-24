import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class IslandsTest {

    protected Islands solution;

    @Before
    public void setUp() {
        solution = new Islands();
    }

    @Test(timeout = 2000000)
    public void testCase0() {
        String[] kingdom = new String[]{".#...#.."};

        int expected = 4;
        int actual = solution.beachLength(kingdom);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000000)
    public void testCase1() {
        String[] kingdom = new String[]{"..#.##", ".##.#.", "#.#..."};

        int expected = 19;
        int actual = solution.beachLength(kingdom);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        String[] kingdom = new String[]{"#...#.....", "##..#...#."};

        int expected = 15;
        int actual = solution.beachLength(kingdom);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        String[] kingdom = new String[]{"....#.", ".#....", "..#..#", "####.."};

        int expected = 24;
        int actual = solution.beachLength(kingdom);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        Assert.assertEquals(0, solution.beachLength(new String[]{"."}));
        Assert.assertEquals(0, solution.beachLength(new String[]{"#"}));
    }

}
