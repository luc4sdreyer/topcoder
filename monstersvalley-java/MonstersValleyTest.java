import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MonstersValleyTest {

    protected MonstersValley solution;

    @Before
    public void setUp() {
        solution = new MonstersValley();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        long[] dread = new long[]{8L, 5L, 10L};
        int[] price = new int[]{1, 1, 2};

        int expected = 2;
        int actual = solution.minimumPrice(dread, price);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        long[] dread = new long[]{1L, 2L, 4L, 1000000000L};
        int[] price = new int[]{1, 1, 1, 2};

        int expected = 5;
        int actual = solution.minimumPrice(dread, price);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        long[] dread = new long[]{200L, 107L, 105L, 206L, 307L, 400L};
        int[] price = new int[]{1, 2, 1, 1, 1, 2};

        int expected = 2;
        int actual = solution.minimumPrice(dread, price);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        long[] dread = new long[]{5216L, 12512L, 613L, 1256L, 66L, 17202L, 30000L, 23512L, 2125L, 33333L};
        int[] price = new int[]{2, 2, 1, 1, 1, 1, 2, 1, 2, 1};

        int expected = 5;
        int actual = solution.minimumPrice(dread, price);

        Assert.assertEquals(expected, actual);
    }

}
