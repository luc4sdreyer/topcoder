import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class Over9000RocksTest {

    protected Over9000Rocks solution;

    @Before
    public void setUp() {
        solution = new Over9000Rocks();
    }

    @Test(timeout = 200000)
    public void testCase0() {
        int[] lowerBound = new int[]{9000};
        int[] upperBound = new int[]{9001};

        int expected = 1;
        int actual = solution.countPossibilities(lowerBound, upperBound);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 200000)
    public void testCase1() {
        int[] lowerBound = new int[]{9000, 1, 10};
        int[] upperBound = new int[]{9000, 2, 20};

        int expected = 15;
        int actual = solution.countPossibilities(lowerBound, upperBound);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int[] lowerBound = new int[]{1001, 2001, 3001, 3001};
        int[] upperBound = new int[]{1003, 2003, 3003, 3003};

        int expected = 9;
        int actual = solution.countPossibilities(lowerBound, upperBound);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int[] lowerBound = new int[]{9000, 90000, 1, 10};
        int[] upperBound = new int[]{9000, 90000, 3, 15};

        int expected = 38;
        int actual = solution.countPossibilities(lowerBound, upperBound);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        int[] lowerBound = new int[]{1, 1, 1, 1, 1, 1};
        int[] upperBound = new int[]{3, 4, 5, 6, 7, 8};

        int expected = 0;
        int actual = solution.countPossibilities(lowerBound, upperBound);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 200000)
    public void testCase6() {
        int[] lowerBound = new int[]{177258, 439976, 996972, 281567, 782547, 998375, 491481, 707710, 146177, 889410};
        int[] upperBound = new int[]{293261, 559909, 997946, 410135, 784021, 998581, 925665, 743314, 626775, 943659};

        int expected = 7137090;
        int actual = solution.countPossibilities(lowerBound, upperBound);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase7() {
        int[] lowerBound = new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        int[] upperBound = new int[]{1000000, 1000000, 1000000, 1000000, 1000000, 1000000, 1000000, 1000000, 1000000, 1000000, 1000000, 1000000, 1000000, 1000000, 1000000};

        int expected = 0;
        int actual = solution.countPossibilities(lowerBound, upperBound);

        Assert.assertEquals(expected, actual);
    }

}
