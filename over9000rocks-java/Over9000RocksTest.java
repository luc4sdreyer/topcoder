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

    @Test(timeout = 2000)
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

    @Test(timeout = 2000)
    public void testCase5() {
        int[] lowerBound = new int[15];
        int[] upperBound = new int[15];
        for (int i = 0; i < upperBound.length; i++) {
			upperBound[i] = 1000000;
			lowerBound[i] = 1;
		}

        int expected = 14991000;
        int actual = solution.countPossibilities(lowerBound, upperBound);

        Assert.assertEquals(expected, actual);
    }

}
