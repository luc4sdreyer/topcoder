import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StrIIRecTest {

    protected StrIIRec solution;

    @Before
    public void setUp() {
        solution = new StrIIRec();
    }

    @Test(timeout = 2000000)
    public void testCase0() {
        int n = 2;
        int minInv = 1;
        String minStr = "ab";

        String expected = "ba";
        String actual = solution.recovstr(n, minInv, minStr);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000000)
    public void testCase1() {
        int n = 9;
        int minInv = 1;
        String minStr = "efcdgab";

        String expected = "efcdgabhi";
        String actual = solution.recovstr(n, minInv, minStr);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int n = 11;
        int minInv = 55;
        String minStr = "debgikjfc";

        String expected = "kjihgfedcba";
        String actual = solution.recovstr(n, minInv, minStr);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int n = 15;
        int minInv = 0;
        String minStr = "e";

        String expected = "eabcdfghijklmno";
        String actual = solution.recovstr(n, minInv, minStr);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        int n = 9;
        int minInv = 20;
        String minStr = "fcdebiha";

        String expected = "fcdehigba";
        String actual = solution.recovstr(n, minInv, minStr);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase5() {
        int n = 9;
        int minInv = 21;
        String minStr = "efcdgabhi";

        String expected = "efhdgabci";
        String actual = solution.recovstr(n, minInv, minStr);

        Assert.assertEquals(expected, actual);
    }

}
