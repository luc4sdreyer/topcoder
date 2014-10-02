import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CuttingBitStringTest {

    protected CuttingBitString solution;

    @Before
    public void setUp() {
        solution = new CuttingBitString();
    }

    @Test(timeout = 200000)
    public void testCase0() {
        String S = "101101101";

        int expected = 3;
        int actual = solution.getmin(S);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 200000)
    public void testCase1() {
        String S = "1111101";

        int expected = 1;
        int actual = solution.getmin(S);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 200000)
    public void testCase2() {
        String S = "00000";

        int expected = -1;
        int actual = solution.getmin(S);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        String S = "110011011";

        int expected = 3;
        int actual = solution.getmin(S);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        String S = "1000101011";

        int expected = -1;
        int actual = solution.getmin(S);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase5() {
        String S = "111011100110101100101110111";

        int expected = 5;
        int actual = solution.getmin(S);

        Assert.assertEquals(expected, actual);
    }

}
