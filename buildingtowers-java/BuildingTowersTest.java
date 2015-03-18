import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BuildingTowersTest {

    protected BuildingTowers solution;

    @Before
    public void setUp() {
        solution = new BuildingTowers();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        int N = 10;
        int K = 1;
        int[] x = new int[]{3, 8};
        int[] t = new int[]{1, 1};

        long expected = 3L;
        long actual = solution.maxHeight(N, K, x, t);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int N = 1000000000;
        int K = 1000000000;
        int[] x = new int[]{};
        int[] t = new int[]{};

        long expected = 999999999000000000L;
        long actual = solution.maxHeight(N, K, x, t);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 200000)
    public void testCase2() {
        int N = 20;
        int K = 3;
        int[] x = new int[]{4, 7, 13, 15, 18};
        int[] t = new int[]{8, 22, 1, 55, 42};

        long expected = 22L;
        long actual = solution.maxHeight(N, K, x, t);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int N = 780;
        int K = 547990606;
        int[] x = new int[]{34, 35, 48, 86, 110, 170, 275, 288, 313, 321, 344, 373, 390, 410, 412, 441, 499, 525, 538, 568, 585, 627, 630, 671, 692, 699, 719, 752, 755, 764, 772};
        int[] t = new int[]{89, 81, 88, 42, 55, 92, 19, 91, 71, 42, 72, 18, 86, 89, 88, 75, 29, 98, 63, 74, 45, 11, 68, 34, 94, 20, 69, 33, 50, 69, 54};

        long expected = 28495511604L;
        long actual = solution.maxHeight(N, K, x, t);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        int N = 7824078;
        int K = 2374;
        int[] x = new int[]{134668, 488112, 558756, 590300, 787884, 868112, 1550116, 1681439, 1790994, 1796091, 1906637, 2005485, 2152813, 2171716, 2255697, 2332732, 2516853, 2749024, 2922558, 2930163, 3568190, 3882735, 4264888, 5080550, 5167938, 5249191, 5574341, 5866912, 5936121, 6142348, 6164177, 6176113, 6434368, 6552905, 6588059, 6628843, 6744163, 6760794, 6982172, 7080464, 7175493, 7249044};
        int[] t = new int[]{8, 9, 171315129, 52304509, 1090062, 476157338, 245, 6, 253638067, 37, 500, 29060, 106246500, 129, 22402, 939993108, 7375, 2365707, 40098, 10200444, 3193547, 55597, 24920935, 905027, 1374, 12396141, 525886, 41, 33, 3692, 11502, 180, 3186, 5560, 778988, 42449532, 269666, 10982579, 48, 3994, 1, 9};

        long expected = 1365130725L;
        long actual = solution.maxHeight(N, K, x, t);

        Assert.assertEquals(expected, actual);
    }

}
