import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MountainsEasyTest {

    protected MountainsEasy solution;

    @Before
    public void setUp() {
        solution = new MountainsEasy();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        String[] picture = new String[]{"X.", "XX"};
        int N = 1;

        int expected = 1;
        int actual = solution.countPlacements(picture, N);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        String[] picture = new String[]{"X.", "XX"};
        int N = 2;

        int expected = 5;
        int actual = solution.countPlacements(picture, N);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        String[] picture = new String[]{"X.X", "XXX"};
        int N = 2;

        int expected = 2;
        int actual = solution.countPlacements(picture, N);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        String[] picture = new String[]{"X.X", "XXX"};
        int N = 3;

        int expected = 24;
        int actual = solution.countPlacements(picture, N);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        String[] picture = new String[]{"......", "X..X..", "XXXXXX", "XXXXXX"};
        int N = 3;

        int expected = 6;
        int actual = solution.countPlacements(picture, N);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase5() {
        String[] picture = new String[]{"......", "X..X..", "XXXXXX", "XXXXXX"};
        int N = 4;

        int expected = 300;
        int actual = solution.countPlacements(picture, N);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase6() {
        String[] picture = new String[]{"....X...X..", "...XXX.XXX.", "XXXXXXXXXXX"};
        int N = 10;

        int expected = 764632413;
        int actual = solution.countPlacements(picture, N);

        Assert.assertEquals(expected, actual);
    }

}
