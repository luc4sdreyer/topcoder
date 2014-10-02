import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RollingDiceDivOneTest {

    protected RollingDiceDivOne solution;

    @Before
    public void setUp() {
        solution = new RollingDiceDivOne();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        int[] dice = new int[]{6, 6, 8};

        long expected = 11L;
        long actual = solution.mostLikely(dice);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int[] dice = new int[]{10};

        long expected = 1L;
        long actual = solution.mostLikely(dice);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int[] dice = new int[]{2, 3, 4, 5};

        long expected = 9L;
        long actual = solution.mostLikely(dice);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int[] dice = new int[]{1, 10, 1};

        long expected = 3L;
        long actual = solution.mostLikely(dice);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        int[] dice = new int[]{382828264, 942663792, 291832707, 887961277, 546603677, 545185615, 146267547, 6938117, 167567032, 84232402, 700781193, 452172304, 816532384, 951089120, 448136091, 280899512, 256093435, 39595226, 631504901, 154772240};

        long expected = 4366828428L;
        long actual = solution.mostLikely(dice);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000000)
    public void testCase5() {
        int[] dice = new int[]{5, 5};

        long expected = 6;
        long actual = solution.mostLikely(dice);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase6() {
        int[] dice = new int[]{5, 4};

        long expected = 5;
        long actual = solution.mostLikely(dice);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 200010)
    public void testCase7() {
        int[] dice = new int[]{5, 3};

        long expected = 4;
        long actual = solution.mostLikely(dice);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 200010)
    public void testCase8() {
        int[] dice = new int[]{5, 3, 2};

        long expected = 5;
        long actual = solution.mostLikely(dice);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 200010)
    public void testCase9() {
        int[] dice = new int[]{2, 3, 5};

        long expected = 5;
        long actual = solution.mostLikely(dice);

        Assert.assertEquals(expected, actual);
    }

}
