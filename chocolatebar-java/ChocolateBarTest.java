import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ChocolateBarTest {

    protected ChocolateBar solution;

    @Before
    public void setUp() {
        solution = new ChocolateBar();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        String letters = "srm";

        int expected = 3;
        int actual = solution.maxLength(letters);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        String letters = "dengklek";

        int expected = 6;
        int actual = solution.maxLength(letters);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        String letters = "haha";

        int expected = 2;
        int actual = solution.maxLength(letters);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        String letters = "www";

        int expected = 1;
        int actual = solution.maxLength(letters);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        String letters = "thisisansrmbeforetopcoderopenfinals";

        int expected = 9;
        int actual = solution.maxLength(letters);

        Assert.assertEquals(expected, actual);
    }

}
