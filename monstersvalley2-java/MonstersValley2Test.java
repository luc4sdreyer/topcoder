import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MonstersValley2Test {

    protected MonstersValley2 solution;

    @Before
    public void setUp() {
        solution = new MonstersValley2();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        int[] dread = new int[]{8, 5, 10};
        int[] price = new int[]{1, 1, 2};

        int expected = 2;
        int actual = solution.minimumPrice(dread, price);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int[] dread = new int[]{1, 2, 4, 1000000000};
        int[] price = new int[]{1, 1, 1, 2};

        int expected = 5;
        int actual = solution.minimumPrice(dread, price);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int[] dread = new int[]{200, 107, 105, 206, 307, 400};
        int[] price = new int[]{1, 2, 1, 1, 1, 2};

        int expected = 2;
        int actual = solution.minimumPrice(dread, price);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000000)
    public void testCase3() {
        int[] dread = new int[]{5216, 12512, 613, 1256, 66, 17202, 30000, 23512, 2125, 33333};
        int[] price = new int[]{2, 2, 1, 1, 1, 1, 2, 1, 2, 1};

        int expected = 5;
        int actual = solution.minimumPrice(dread, price);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000000)
    public void testCase4() {
        int[] dread = new int[]{1, 2, 3, 4, 5, 5, 6, 10, 20, 30,50, 100, 150, 200, 500, 1000, 2000, 5000, 8000, 10000};
        int[] price = new int[]{2, 2, 1, 1, 1, 1, 2, 1, 2, 1,2, 2, 1, 1, 1, 1, 2, 1, 2, 1};

        int expected = 5;
        int actual = solution.minimumPrice(dread, price);

        Assert.assertEquals(expected, actual);
    }

}
