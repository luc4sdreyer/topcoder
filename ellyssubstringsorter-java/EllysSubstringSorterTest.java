import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EllysSubstringSorterTest {

    protected EllysSubstringSorter solution;

    @Before
    public void setUp() {
        solution = new EllysSubstringSorter();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        String S = "TOPCODER";
        int L = 4;

        String expected = "COPTODER";
        String actual = solution.getMin(S, L);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        String S = "ESPRIT";
        int L = 3;

        String expected = "EPRSIT";
        String actual = solution.getMin(S, L);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        String S = "AAAAAAAAA";
        int L = 2;

        String expected = "AAAAAAAAA";
        String actual = solution.getMin(S, L);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        String S = "ABRACADABRA";
        int L = 5;

        String expected = "AAABCRDABRA";
        String actual = solution.getMin(S, L);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        String S = "BAZINGA";
        int L = 6;

        String expected = "ABGINZA";
        String actual = solution.getMin(S, L);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase5() {
        String S = "AAAWDIUAOIWDESBEAIWODJAWDBPOAWDUISAWDOOPAWD";
        int L = 21;

        String expected = "AAAAAABDDDEEIIIJOOSUWWWWDBPOAWDUISAWDOOPAWD";
        String actual = solution.getMin(S, L);

        Assert.assertEquals(expected, actual);
    }

}
