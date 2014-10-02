import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ANDEquationTest {

    protected ANDEquation solution;

    @Before
    public void setUp() {
        solution = new ANDEquation();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        int[] A = new int[]{1, 3, 5};

        int expected = 1;
        int actual = solution.restoreY(A);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int[] A = new int[]{31, 7};

        int expected = -1;
        int actual = solution.restoreY(A);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int[] A = new int[]{31, 7, 7};

        int expected = 7;
        int actual = solution.restoreY(A);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int[] A = new int[]{1, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 1, 0, 0, 0, 1};

        int expected = 0;
        int actual = solution.restoreY(A);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        int[] A = new int[]{191411, 256951, 191411, 191411, 191411, 256951, 195507, 191411, 192435, 191411, 191411, 195511, 191419, 191411, 256947, 191415, 191475, 195579, 191415, 191411, 191483, 191411, 191419, 191475, 256947, 191411, 191411, 191411, 191419, 256947, 191411, 191411, 191411};

        int expected = 191411;
        int actual = solution.restoreY(A);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase5() {
        int[] A = new int[]{1362, 1066, 1659, 2010, 1912, 1720, 1851, 1593, 1799, 1805, 1139, 1493, 1141, 1163, 1211};

        int expected = -1;
        int actual = solution.restoreY(A);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase6() {
        int[] A = new int[]{2, 3, 7, 19};

        int expected = -1;
        int actual = solution.restoreY(A);

        Assert.assertEquals(expected, actual);
    }

}
