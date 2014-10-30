import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PerfectSequencesTest {

    protected PerfectSequences solution;

    @Before
    public void setUp() {
        solution = new PerfectSequences();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        int[] seq = new int[]{1, 3, 4};

        String expected = "Yes";
        String actual = solution.fixIt(seq);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 200000)
    public void testCase1() {
        int[] seq = new int[]{1, 2, 3};

        String expected = "No";
        String actual = solution.fixIt(seq);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int[] seq = new int[]{1, 4, 2, 4, 2, 4};

        String expected = "No";
        String actual = solution.fixIt(seq);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int[] seq = new int[]{1000000, 1, 1, 1, 1, 2};

        String expected = "Yes";
        String actual = solution.fixIt(seq);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        int[] seq = new int[]{8};

        String expected = "Yes";
        String actual = solution.fixIt(seq);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase5() {
        int[] seq = new int[]{2, 0, 2};

        String expected = "No";
        String actual = solution.fixIt(seq);

        Assert.assertEquals(expected, actual);
    }

}
