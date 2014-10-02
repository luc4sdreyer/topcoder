import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EllysFiveFriendsTest {

    protected EllysFiveFriends solution;

    @Before
    public void setUp() {
        solution = new EllysFiveFriends();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        int[] numbers = new int[]{5, 1, 1, 2, 3};

        int expected = 10;
        int actual = solution.getZero(numbers);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int[] numbers = new int[]{2, 1, 1, 1, 2};

        int expected = 0;
        int actual = solution.getZero(numbers);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int[] numbers = new int[]{3, 4, 1, 4, 5};

        int expected = 520;
        int actual = solution.getZero(numbers);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int[] numbers = new int[]{42, 666, 1337, 666, 42};

        int expected = 549119981;
        int actual = solution.getZero(numbers);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        int[] numbers = new int[]{8792, 9483, 6787, 9856, 6205};

        int expected = 165501353;
        int actual = solution.getZero(numbers);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase5() {
        int[] numbers = new int[]{10000, 10000, 10000, 10000, 10000};

        int expected = 952019520;
        int actual = solution.getZero(numbers);

        Assert.assertEquals(expected, actual);
    }

}
