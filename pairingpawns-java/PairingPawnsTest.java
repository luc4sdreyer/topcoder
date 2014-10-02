import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PairingPawnsTest {

    protected PairingPawns solution;

    @Before
    public void setUp() {
        solution = new PairingPawns();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        int[] start = new int[]{0, 2};

        int expected = 1;
        int actual = solution.savedPawnCount(start);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int[] start = new int[]{10, 3};

        int expected = 11;
        int actual = solution.savedPawnCount(start);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int[] start = new int[]{0, 0, 0, 8};

        int expected = 1;
        int actual = solution.savedPawnCount(start);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int[] start = new int[]{0, 1, 1, 2};

        int expected = 1;
        int actual = solution.savedPawnCount(start);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        int[] start = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 123456};

        int expected = 0;
        int actual = solution.savedPawnCount(start);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase5() {
        int[] start = new int[]{1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000};

        int expected = 3921;
        int actual = solution.savedPawnCount(start);

        Assert.assertEquals(expected, actual);
    }

}
