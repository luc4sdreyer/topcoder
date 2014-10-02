import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PenguinPalsTest {

    protected PenguinPals solution;

    @Before
    public void setUp() {
        solution = new PenguinPals();
    }

    @Test(timeout = 2000000)
    public void testCase0() {
        String colors = "RRBRBRBB";

        int expected = 3;
        int actual = solution.findMaximumMatching(colors);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 20000000)
    public void testCase1() {
        String colors = "RRRR";

        int expected = 2;
        int actual = solution.findMaximumMatching(colors);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 20000000)
    public void testCase2() {
        String colors = "BBBBB";

        int expected = 2;
        int actual = solution.findMaximumMatching(colors);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 200000000)
    public void testCase3() {
        String colors = "RBRBRBRBR";

        int expected = 4;
        int actual = solution.findMaximumMatching(colors);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        String colors = "RRRBRBRBRBRB";

        int expected = 5;
        int actual = solution.findMaximumMatching(colors);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase5() {
        String colors = "R";

        int expected = 0;
        int actual = solution.findMaximumMatching(colors);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase6() {
        String colors = "RBRRBBRB";

        int expected = 3;
        int actual = solution.findMaximumMatching(colors);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase7() {
        String colors = "RBRBBRBRB";

        int expected = 4;
        int actual = solution.findMaximumMatching(colors);

        Assert.assertEquals(expected, actual);
    }

}
