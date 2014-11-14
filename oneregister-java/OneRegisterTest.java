import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OneRegisterTest {

    protected OneRegister solution;

    @Before
    public void setUp() {
        solution = new OneRegister();
    }

    @Test(timeout = 200000)
    public void testCase0() {
        int s = 7;
        int t = 392;

        String expected = "+*+";
        String actual = solution.getProgram(s, t);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int s = 7;
        int t = 256;

        String expected = "/+***";
        String actual = solution.getProgram(s, t);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int s = 4;
        int t = 256;

        String expected = "**";
        String actual = solution.getProgram(s, t);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int s = 7;
        int t = 7;

        String expected = "";
        String actual = solution.getProgram(s, t);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        int s = 7;
        int t = 9;

        String expected = ":-(";
        String actual = solution.getProgram(s, t);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase5() {
        int s = 10;
        int t = 1;

        String expected = "/";
        String actual = solution.getProgram(s, t);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase6() {
        int s = 999999999;
        int t = 536870912;

        String expected = "/";
        String actual = solution.getProgram(s, t);

        Assert.assertEquals(expected, actual);
    }

}
