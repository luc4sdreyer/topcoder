import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UnsortedSequenceTest {

    protected UnsortedSequence solution;

    @Before
    public void setUp() {
        solution = new UnsortedSequence();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        int[] s = new int[]{1, 2};

        int[] expected = new int[]{2, 1};
        int[] actual = solution.getUnsorted(s);

        Assert.assertArrayEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int[] s = new int[]{1, 2, 3};

        int[] expected = new int[]{1, 3, 2};
        int[] actual = solution.getUnsorted(s);

        Assert.assertArrayEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int[] s = new int[]{7, 2, 2};

        int[] expected = new int[]{2, 7, 2};
        int[] actual = solution.getUnsorted(s);

        Assert.assertArrayEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int[] s = new int[]{1000};

        int[] expected = new int[]{};
        int[] actual = solution.getUnsorted(s);

        Assert.assertArrayEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        int[] s = new int[]{1, 1};

        int[] expected = new int[]{};
        int[] actual = solution.getUnsorted(s);

        Assert.assertArrayEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase5() {
        int[] s = new int[]{1, 2, 4, 3};

        int[] expected = new int[]{1, 2, 4, 3};
        int[] actual = solution.getUnsorted(s);

        Assert.assertArrayEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase6() {
        int[] s = new int[]{2, 8, 5, 1, 10, 5, 9, 9, 3, 10, 5, 6, 6, 2, 8, 2, 10};

        int[] expected = new int[]{1, 2, 2, 2, 3, 5, 5, 5, 6, 6, 8, 8, 9, 10, 9, 10, 10};
        int[] actual = solution.getUnsorted(s);

        Assert.assertArrayEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase7() {
        int[] s = new int[]{0, 0, 0, 0, 0};

        int[] expected = new int[]{};
        int[] actual = solution.getUnsorted(s);

        Assert.assertArrayEquals(expected, actual);
    }

}
