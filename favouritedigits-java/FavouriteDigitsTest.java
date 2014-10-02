import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FavouriteDigitsTest {

    protected FavouriteDigits solution;

    @Before
    public void setUp() {
        solution = new FavouriteDigits();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        long N = 47L;
        int digit1 = 1;
        int count1 = 0;
        int digit2 = 2;
        int count2 = 0;

        long expected = 47L;
        long actual = solution.findNext(N, digit1, count1, digit2, count2);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        long N = 47L;
        int digit1 = 5;
        int count1 = 0;
        int digit2 = 9;
        int count2 = 1;

        long expected = 49L;
        long actual = solution.findNext(N, digit1, count1, digit2, count2);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        long N = 47L;
        int digit1 = 5;
        int count1 = 0;
        int digit2 = 3;
        int count2 = 1;

        long expected = 53L;
        long actual = solution.findNext(N, digit1, count1, digit2, count2);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        long N = 47L;
        int digit1 = 2;
        int count1 = 1;
        int digit2 = 0;
        int count2 = 2;

        long expected = 200L;
        long actual = solution.findNext(N, digit1, count1, digit2, count2);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        long N = 123456789012345L;
        int digit1 = 1;
        int count1 = 2;
        int digit2 = 2;
        int count2 = 4;

        long expected = 123456789012422L;
        long actual = solution.findNext(N, digit1, count1, digit2, count2);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase5() {
        long N = 92L;
        int digit1 = 1;
        int count1 = 1;
        int digit2 = 0;
        int count2 = 0;

        long expected = 100L;
        long actual = solution.findNext(N, digit1, count1, digit2, count2);

        Assert.assertEquals(expected, actual);
    }
    @Test
    public void testCase6() {
        long N = 0L;
        int digit1 = 1;
        int count1 = 9;
        int digit2 = 0;
        int count2 = 0;

      //long expected = 123456789012422L;
        long expected = 111111111L;
        long actual = solution.findNext(N, digit1, count1, digit2, count2);

        Assert.assertEquals(expected, actual);
    }
/*
    @Test
    public void testCase7() {
        long N = 0L;
        int digit1 = 9;
        int count1 = 14;
        int digit2 = 0;
        int count2 = 0;

      //long expected = 123456789012422L;
        long expected = 99999999999999L;
        long actual = solution.findNext(N, digit1, count1, digit2, count2);

        Assert.assertEquals(expected, actual);
    }*/
}
