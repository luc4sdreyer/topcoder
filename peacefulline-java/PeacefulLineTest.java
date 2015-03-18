import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PeacefulLineTest {

    protected PeacefulLine solution;

    @Before
    public void setUp() {
        solution = new PeacefulLine();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        int[] x = new int[]{1, 2, 3, 4};

        String expected = "possible";
        String actual = solution.makeLine(x);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 200000)
    public void testCase1() {
        int[] x = new int[]{1, 1, 1, 2};

        String expected = "impossible";
        String actual = solution.makeLine(x);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int[] x = new int[]{1, 1, 2, 2, 3, 3, 4, 4};

        String expected = "possible";
        String actual = solution.makeLine(x);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int[] x = new int[]{3, 3, 3, 3, 13, 13, 13, 13};

        String expected = "possible";
        String actual = solution.makeLine(x);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        int[] x = new int[]{3, 7, 7, 7, 3, 7, 7, 7, 3};

        String expected = "impossible";
        String actual = solution.makeLine(x);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase5() {
        int[] x = new int[]{25, 12, 3, 25, 25, 12, 12, 12, 12, 3, 25};

        String expected = "possible";
        String actual = solution.makeLine(x);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase6() {
        int[] x = new int[]{3, 3, 3, 3, 13, 13, 13, 13, 3};

        String expected = "possible";
        String actual = solution.makeLine(x);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 200000)
    public void testCase7() {
        int[] x = new int[]{1, 1, 1, 2, 2};

        String expected = "possible";
        String actual = solution.makeLine(x);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 200000)
    public void testCase8() {
        int[] x = new int[]{1, 1, 2, 2};

        String expected = "possible";
        String actual = solution.makeLine(x);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 200000)
    public void testCase9() {
        int[] x = new int[]{2, 3, 4, 3, 8, 3, 9, 14, 15, 21, 21};

        String expected = "possible";
        String actual = solution.makeLine(x);

        Assert.assertEquals(expected, actual);
    }

}
