import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TwiceStringTest {

    protected TwiceString solution;

    @Before
    public void setUp() {
        solution = new TwiceString();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        String s = "aba";

        String expected = "ababa";
        String actual = solution.getShortest(s);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        String s = "xxxxx";

        String expected = "xxxxxx";
        String actual = solution.getShortest(s);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 200000)
    public void testCase2() {
        String s = "topcoder";

        String expected = "topcodertopcoder";
        String actual = solution.getShortest(s);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        String s = "abracadabra";

        String expected = "abracadabracadabra";
        String actual = solution.getShortest(s);

        Assert.assertEquals(expected, actual);
    }


    @Test(timeout = 200000)
    public void testCase4() {
        String s = "ababa";

        String expected = "abababa";
        String actual = solution.getShortest(s);

        Assert.assertEquals(expected, actual);
    }
}
