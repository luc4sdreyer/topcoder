import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class P8XGraphBuilderTest {

    protected P8XGraphBuilder solution;

    @Before
    public void setUp() {
        solution = new P8XGraphBuilder();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        int[] scores = new int[]{1, 3, 0};

        int expected = 8;
        int actual = solution.solve(scores);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int[] scores = new int[]{0, 0, 0, 10};

        int expected = 10;
        int actual = solution.solve(scores);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int[] scores = new int[]{1, 2, 3, 4, 5, 6};

        int expected = 12;
        int actual = solution.solve(scores);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int[] scores = new int[]{5, 0, 0};

        int expected = 15;
        int actual = solution.solve(scores);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        int[] scores = new int[]{1, 3, 2, 5, 3, 7, 5};

        int expected = 20;
        int actual = solution.solve(scores);

        Assert.assertEquals(expected, actual);
    }

}
