import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TomekPhoneTest {

    protected TomekPhone solution;

    @Before
    public void setUp() {
        solution = new TomekPhone();
    }

    @Test(timeout = 200000)
    public void testCase0() {
        int[] frequencies = new int[]{7, 3, 4, 1};
        int[] keySizes = new int[]{2, 2};

        int expected = 19;
        int actual = solution.minKeystrokes(frequencies, keySizes);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int[] frequencies = new int[]{13, 7, 4, 20};
        int[] keySizes = new int[]{2, 1};

        int expected = -1;
        int actual = solution.minKeystrokes(frequencies, keySizes);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int[] frequencies = new int[]{11, 23, 4, 50, 1000, 7, 18};
        int[] keySizes = new int[]{3, 1, 4};

        int expected = 1164;
        int actual = solution.minKeystrokes(frequencies, keySizes);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int[] frequencies = new int[]{100, 1000, 1, 10};
        int[] keySizes = new int[]{50};

        int expected = 1234;
        int actual = solution.minKeystrokes(frequencies, keySizes);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        int[] frequencies = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50};
        int[] keySizes = new int[]{10, 10, 10, 10, 10, 10, 10, 10};

        int expected = 3353;
        int actual = solution.minKeystrokes(frequencies, keySizes);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase5() {
        int[] frequencies = new int[]{1000};
        int[] keySizes = new int[]{10, 10, 10, 10, 10, 10, 10, 10};

        int expected = 1000;
        int actual = solution.minKeystrokes(frequencies, keySizes);

        Assert.assertEquals(expected, actual);
    }

}
