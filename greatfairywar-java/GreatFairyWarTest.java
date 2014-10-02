import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GreatFairyWarTest {

    protected GreatFairyWar solution;

    @Before
    public void setUp() {
        solution = new GreatFairyWar();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        int[] dps = new int[]{1, 2};
        int[] hp = new int[]{3, 4};

        int expected = 17;
        int actual = solution.minHP(dps, hp);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int[] dps = new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        int[] hp = new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1};

        int expected = 55;
        int actual = solution.minHP(dps, hp);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int[] dps = new int[]{20, 12, 10, 10, 23, 10};
        int[] hp = new int[]{5, 7, 7, 5, 7, 7};

        int expected = 1767;
        int actual = solution.minHP(dps, hp);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int[] dps = new int[]{5, 7, 7, 5, 7, 7};
        int[] hp = new int[]{20, 12, 10, 10, 23, 10};

        int expected = 1998;
        int actual = solution.minHP(dps, hp);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        int[] dps = new int[]{30, 2, 7, 4, 7, 8, 21, 14, 19, 12};
        int[] hp = new int[]{2, 27, 18, 19, 14, 8, 25, 13, 21, 30};

        int expected = 11029;
        int actual = solution.minHP(dps, hp);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase5() {
        int[] dps = new int[]{1};
        int[] hp = new int[]{1};

        int expected = 1;
        int actual = solution.minHP(dps, hp);

        Assert.assertEquals(expected, actual);
    }

}
