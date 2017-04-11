import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TrianglePaintingTest {

    protected TrianglePainting solution;

    @Before
    public void setUp() {
        solution = new TrianglePainting();
    }

    public static void assertEquals(double expected, double actual) {
        if (Double.isNaN(expected)) {
            Assert.assertTrue("expected: <NaN> but was: <" + actual + ">", Double.isNaN(actual));
            return;
        }
        double delta = Math.max(1e-9, 1e-9 * Math.abs(expected));
        Assert.assertEquals(expected, actual, delta);
    }

    @Test(timeout = 2000)
    public void testCase0() {
        int[] x1 = new int[]{1, -2, -4};
        int[] y1 = new int[]{2, 3, -1};
        int[] x2 = new int[]{2, 2, -2};
        int[] y2 = new int[]{-1, -1, 1};
        int[] prob = new int[]{100, 100, 100};

        double expected = 52.5;
        double actual = solution.expectedArea(x1, y1, x2, y2, prob);

        assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int[] x1 = new int[]{1, -2, -4};
        int[] y1 = new int[]{2, 3, -1};
        int[] x2 = new int[]{2, 2, -2};
        int[] y2 = new int[]{-1, -1, 1};
        int[] prob = new int[]{50, 50, 50};

        double expected = 15.0;
        double actual = solution.expectedArea(x1, y1, x2, y2, prob);

        assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int[] x1 = new int[]{1};
        int[] y1 = new int[]{1};
        int[] x2 = new int[]{1};
        int[] y2 = new int[]{-1};
        int[] prob = new int[]{1};

        double expected = 0.01;
        double actual = solution.expectedArea(x1, y1, x2, y2, prob);

        assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int[] x1 = new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        int[] y1 = new int[]{-1, 1, -1, 1, -1, 1, -1, 1, -1, 1};
        int[] x2 = new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        int[] y2 = new int[]{1, -1, 1, -1, 1, -1, 1, -1, 1, -1};
        int[] prob = new int[]{10, 20, 30, 40, 50, 60, 70, 80, 90, 100};

        double expected = 31.899999999999995;
        double actual = solution.expectedArea(x1, y1, x2, y2, prob);

        assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        int[] x1 = new int[]{-6, -2, -10, 9, 8, -1, 10, 5, 7, 3};
        int[] y1 = new int[]{-5, 2, -5, 6, 6, -10, 8, 7, -4, -5};
        int[] x2 = new int[]{5, -1, -1, -8, 6, 7, 10, -6, 6, 3};
        int[] y2 = new int[]{0, -5, -7, 4, 10, 0, 10, -3, -3, -4};
        int[] prob = new int[]{71, 100, 43, 59, 51, 41, 11, 53, 3, 27};

        double expected = 940.1964999999999;
        double actual = solution.expectedArea(x1, y1, x2, y2, prob);

        assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase5() {
        int[] x1 = new int[]{34, -71, 19, 78, 69, -73, 27, 64, -100, 70, -87, 50, 8, -97, 46, -46, -30, -40, -30, -23, 77, 81, 48, 93, -40, 70, 37, -66, 53, -87, -85, 38, -90, 63, -16, 24, -2, -60, -88, 67, -56, -8, -80, -19, -84, 35, 95, -24, -26, -15};
        int[] y1 = new int[]{58, -24, -80, 33, -98, 61, 99, 79, -34, 29, -1, -70, 70, 90, 43, 25, -50, -54, 73, 18, 36, 8, 41, 3, 26, -6, -80, -22, 65, 33, -100, -1, 80, -19, -6, -8, -4, -86, -43, -34, 0, -93, -61, 92, 74, -77, 32, -78, -56, -21};
        int[] x2 = new int[]{-78, -100, -1, 27, 67, -31, -82, -24, 44, -26, 12, 36, -36, -71, -84, 3, 59, 28, -26, -79, -47, 56, -75, -44, -85, -72, 56, 53, -27, 53, -19, -65, 14, 62, 96, -44, 12, -20, -57, 83, 59, 71, 85, -62, 21, 24, -38, 20, 52, -64};
        int[] y2 = new int[]{90, -41, 79, 18, 7, -85, -88, -16, 12, 38, -7, 12, -27, -43, -30, -93, 48, -19, 58, 54, 70, 73, 81, 89, -35, -75, 63, -73, 66, -90, -25, 44, -53, 59, -14, 83, 18, -35, -81, 49, -11, -63, -87, -92, -83, -43, 60, -42, 5, -96};
        int[] prob = new int[]{9, 61, 1, 16, 78, 4, 12, 1, 17, 4, 30, 28, 13, 6, 4, 14, 11, 6, 55, 9, 66, 5, 14, 8, 70, 3, 2, 6, 3, 15, 8, 1, 2, 12, 1, 20, 37, 1, 3, 66, 3, 11, 2, 1, 21, 2, 1, 1, 27, 11};

        double expected = 306025.109;
        double actual = solution.expectedArea(x1, y1, x2, y2, prob);

        assertEquals(expected, actual);
    }

}
