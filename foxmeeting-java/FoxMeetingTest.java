import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FoxMeetingTest {

    protected FoxMeeting solution;

    @Before
    public void setUp() {
        solution = new FoxMeeting();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        int[] A = new int[]{1};
        int[] B = new int[]{2};
        int[] L = new int[]{5};
        int[] foxes = new int[]{1, 2};

        int expected = 0;
        int actual = solution.maxDistance(A, B, L, foxes);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int[] A = new int[]{1, 2};
        int[] B = new int[]{2, 3};
        int[] L = new int[]{1, 1};
        int[] foxes = new int[]{1, 3};

        int expected = 1;
        int actual = solution.maxDistance(A, B, L, foxes);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000000)
    public void testCase2() {
        int[] A = new int[]{1, 2};
        int[] B = new int[]{2, 3};
        int[] L = new int[]{1, 1};
        int[] foxes = new int[]{1, 2, 3};

        int expected = 0;
        int actual = solution.maxDistance(A, B, L, foxes);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int[] A = new int[]{10, 8, 3, 7, 2, 6, 9, 1, 4};
        int[] B = new int[]{5, 5, 8, 10, 5, 5, 6, 10, 3};
        int[] L = new int[]{71846, 10951, 42265, 37832, 29439, 95676, 83661, 28186, 21216};
        int[] foxes = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        int expected = 0;
        int actual = solution.maxDistance(A, B, L, foxes);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        int[] A = new int[]{8, 15, 22, 24, 2, 25, 13, 26, 18, 4, 9, 29, 1, 12, 3, 16, 14, 21, 19, 27, 17, 7, 20, 10, 30, 11, 6, 5, 23};
        int[] B = new int[]{28, 28, 8, 8, 28, 28, 25, 2, 13, 24, 24, 22, 22, 29, 4, 22, 8, 4, 1, 24, 21, 14, 18, 16, 13, 21, 14, 1, 25};
        int[] L = new int[]{79374, 40629, 43195, 73589, 24200, 63937, 35339, 7598, 65109, 51764, 11142, 84017, 51078, 58051, 81387, 22035, 34883, 92710, 52283, 57337, 79309, 3383, 41904, 35839, 38242, 43208, 82062, 24676, 71838};
        int[] foxes = new int[]{3, 5, 7, 9, 10, 14, 17, 19, 20, 21, 24, 25, 28};

        int expected = 107013;
        int actual = solution.maxDistance(A, B, L, foxes);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase5() {
        int[] A = new int[]{34, 14, 22, 9, 24, 19, 11, 37, 33, 21, 5, 30, 1, 43, 7, 31, 45, 27, 6, 12, 13, 35, 23, 47, 49, 50, 26, 40, 16, 10, 48, 25, 29, 15, 28, 46, 4, 20, 44, 17, 39, 32, 38, 2, 42, 8, 36, 3, 41};
        int[] B = new int[]{18, 18, 18, 14, 9, 34, 9, 24, 34, 11, 18, 14, 21, 21, 43, 1, 22, 7, 1, 30, 14, 33, 13, 18, 9, 5, 1, 1, 26, 19, 50, 33, 50, 40, 29, 48, 50, 37, 16, 40, 48, 14, 30, 22, 47, 37, 7, 50, 6};
        int[] L = new int[]{22051, 11109, 85275, 6691, 43705, 47374, 27748, 5411, 62549, 84079, 89542, 38006, 82198, 24083, 16847, 66335, 3542, 72495, 37378, 73973, 85703, 51682, 68688, 94295, 31337, 90071, 99317, 63484, 43244, 99079, 55857, 34503, 79709, 82140, 91137, 27033, 91599, 61168, 52345, 49569, 58919, 38133, 43361, 40718, 2115, 79278, 88841, 40966, 42023};
        int[] foxes = new int[]{5, 12, 13, 18, 23, 27, 28, 29, 32, 33, 34, 35, 36, 37, 40, 42, 43, 47, 48, 49, 50};

        int expected = 89342;
        int actual = solution.maxDistance(A, B, L, foxes);

        Assert.assertEquals(expected, actual);
    }

}
