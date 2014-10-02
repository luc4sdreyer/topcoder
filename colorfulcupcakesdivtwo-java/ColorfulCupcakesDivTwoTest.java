import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ColorfulCupcakesDivTwoTest {

    protected ColorfulCupcakesDivTwo solution;

    @Before
    public void setUp() {
        solution = new ColorfulCupcakesDivTwo();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        String cupcakes = "ABAB";

        int expected = 2;
        int actual = solution.countArrangements(cupcakes);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        String cupcakes = "ABABA";

        int expected = 0;
        int actual = solution.countArrangements(cupcakes);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        String cupcakes = "ABC";

        int expected = 6;
        int actual = solution.countArrangements(cupcakes);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        String cupcakes = "ABABABABABABABABABABABABABABABABABABABABABABABABAB";

        int expected = 2;
        int actual = solution.countArrangements(cupcakes);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        String cupcakes = "BCBABBACBABABCCCCCAABBAACBBBBCBCAAA";

        int expected = 741380640;
        int actual = solution.countArrangements(cupcakes);

        Assert.assertEquals(expected, actual);
    }

}
