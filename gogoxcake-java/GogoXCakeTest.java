import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GogoXCakeTest {

    protected GogoXCake solution;

    @Before
    public void setUp() {
        solution = new GogoXCake();
    }

    @Test(timeout = 200000)
    public void testCase0() {
        String[] cake = new String[]{"X.X", "...", "...", "X.X"};
        String[] cutter = new String[]{".X", "..", "X."};

        String expected = "YES";
        String actual = solution.solve(cake, cutter);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        String[] cake = new String[]{"..XX", "...X", "X...", "XX.."};
        String[] cutter = new String[]{"..", ".."};

        String expected = "NO";
        String actual = solution.solve(cake, cutter);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        String[] cake = new String[]{"...X..."};
        String[] cutter = new String[]{"..."};

        String expected = "YES";
        String actual = solution.solve(cake, cutter);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        String[] cake = new String[]{".X.", "X.X", ".X."};
        String[] cutter = new String[]{"."};

        String expected = "YES";
        String actual = solution.solve(cake, cutter);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        String[] cake = new String[]{"XXXXXXX", "X.....X", "X.....X", "X.....X", "XXXXXXX"};
        String[] cutter = new String[]{".X.", "XXX", ".X."};

        String expected = "NO";
        String actual = solution.solve(cake, cutter);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase5() {
        String[] cake = new String[]{"..", "X.", ".X"};
        String[] cutter = new String[]{"..", ".X", "X."};

        String expected = "NO";
        String actual = solution.solve(cake, cutter);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase6() {
        String[] cake = new String[]{"X..", ".XX", ".XX"};
        String[] cutter = new String[]{".XX", ".XX", "X.."};

        String expected = "NO";
        String actual = solution.solve(cake, cutter);

        Assert.assertEquals(expected, actual);
    }
    

    @Test(timeout = 200000)
    public void testCase7() {
        String[] cake = new String[]{"XX", "X.", "XX", "X."};
        String[] cutter = new String[]{"XX", "X."};

        String expected = "YES";
        String actual = solution.solve(cake, cutter);

        Assert.assertEquals(expected, actual);
    }

}
