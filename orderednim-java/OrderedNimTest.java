import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OrderedNimTest {

    protected OrderedNim solution;

    @Before
    public void setUp() {
        solution = new OrderedNim();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        int[] layout = new int[]{5};

        String expected = "Alice";
        String actual = solution.winner(layout);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int[] layout = new int[]{1, 2};

        String expected = "Bob";
        String actual = solution.winner(layout);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int[] layout = new int[]{2, 1};

        String expected = "Alice";
        String actual = solution.winner(layout);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int[] layout = new int[]{10, 9, 8, 7, 6, 5, 4, 3, 2, 1};

        String expected = "Alice";
        String actual = solution.winner(layout);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        int[] layout = new int[]{1, 1, 1, 1};

        String expected = "Bob";
        String actual = solution.winner(layout);

        Assert.assertEquals(expected, actual);
    }

}
