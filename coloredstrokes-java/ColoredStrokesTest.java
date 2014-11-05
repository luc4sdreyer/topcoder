import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ColoredStrokesTest {

    protected ColoredStrokes solution;

    @Before
    public void setUp() {
        solution = new ColoredStrokes();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        String[] picture = new String[]{"...", "..."};

        int expected = 0;
        int actual = solution.getLeast(picture);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 200000)
    public void testCase1() {
        String[] picture = new String[]{"..B.", "..B."};

        int expected = 1;
        int actual = solution.getLeast(picture);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        String[] picture = new String[]{".BB."};

        int expected = 2;
        int actual = solution.getLeast(picture);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        String[] picture = new String[]{"...B..", ".BRGRR", ".B.B.."};

        int expected = 3;
        int actual = solution.getLeast(picture);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        String[] picture = new String[]{"...B..", ".BRBRR", ".B.B.."};

        int expected = 4;
        int actual = solution.getLeast(picture);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase5() {
        String[] picture = new String[]{"GR", "BG"};

        int expected = 4;
        int actual = solution.getLeast(picture);

        Assert.assertEquals(expected, actual);
    }


    @Test(timeout = 2000)
    public void testCase6() {
        String[] picture = new String[]{"GGG", "GGG"};
        int expected = 5;
        int actual = solution.getLeast(picture);

        Assert.assertEquals(expected, actual);
    }


    @Test(timeout = 2000)
    public void testCase7() {
        String[] picture = new String[]{"GGG", "GGG", "GGG"};
        int expected = 6;
        int actual = solution.getLeast(picture);

        Assert.assertEquals(expected, actual);
    }
}
