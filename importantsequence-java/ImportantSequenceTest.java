import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ImportantSequenceTest {

    protected ImportantSequence solution;

    @Before
    public void setUp() {
        solution = new ImportantSequence();
    }

    @Test(timeout = 200000)
    public void testCase0() {
        int[] B = new int[]{3, -1, 7};
        String operators = "+-+";

        int expected = 2;
        int actual = solution.getCount(B, operators);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 200000)
    public void testCase1() {
        int[] B = new int[]{1};
        String operators = "-";

        int expected = -1;
        int actual = solution.getCount(B, operators);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int[] B = new int[]{1};
        String operators = "+";

        int expected = 0;
        int actual = solution.getCount(B, operators);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int[] B = new int[]{10};
        String operators = "+";

        int expected = 9;
        int actual = solution.getCount(B, operators);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 200000)
    public void testCase4() {
        int[] B = new int[]{540, 2012, 540, 2012, 540, 2012, 540};
        String operators = "-+-+-+-";

        int expected = 1471;
        int actual = solution.getCount(B, operators);

        Assert.assertEquals(expected, actual);
    }
    @Test(timeout = 200000)
    public void testCase5() {
        int[] B = new int[]{-1000000000, -1000000000, -1000000000, -1000000000, -1000000000, -1000000000, -1000000000, -1000000000, -1000000000, -1000000000, -1000000000, -1000000000, -1000000000, -1000000000, -1000000000, -1000000000, -1000000000, -1000000000, -1000000000, -1000000000, -1000000000, -1000000000, -1000000000, -1000000000, -1000000000, -1000000000, -1000000000, -1000000000, -1000000000, -1000000000, -1000000000, -1000000000, -1000000000, -1000000000, -1000000000, -1000000000, -1000000000, -1000000000, -1000000000, -1000000000, -1000000000, -1000000000, -1000000000, -1000000000, -1000000000, -1000000000, -1000000000, -1000000000, -1000000000, -1000000000};
        String operators = "++++++++++++++++++++++++++++++++++++++++++++++++++";

        int expected = 0;
        int actual = solution.getCount(B, operators);

        Assert.assertEquals(expected, actual);
    }
}
