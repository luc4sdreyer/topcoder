import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RandomColoringDiv2Test {

    protected RandomColoringDiv2 solution;

    @Before
    public void setUp() {
        solution = new RandomColoringDiv2();
    }

    @Test(timeout = 200000)
    public void testCase0() {
        int maxR = 5;
        int maxG = 1;
        int maxB = 1;
        int startR = 2;
        int startG = 0;
        int startB = 0;
        int d1 = 0;
        int d2 = 1;

        int expected = 3;
        int actual = solution.getCount(maxR, maxG, maxB, startR, startG, startB, d1, d2);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 200000)
    public void testCase1() {
        int maxR = 4;
        int maxG = 2;
        int maxB = 2;
        int startR = 0;
        int startG = 0;
        int startB = 0;
        int d1 = 3;
        int d2 = 3;

        int expected = 4;
        int actual = solution.getCount(maxR, maxG, maxB, startR, startG, startB, d1, d2);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int maxR = 4;
        int maxG = 2;
        int maxB = 2;
        int startR = 0;
        int startG = 0;
        int startB = 0;
        int d1 = 5;
        int d2 = 5;

        int expected = 0;
        int actual = solution.getCount(maxR, maxG, maxB, startR, startG, startB, d1, d2);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int maxR = 6;
        int maxG = 9;
        int maxB = 10;
        int startR = 1;
        int startG = 2;
        int startB = 3;
        int d1 = 0;
        int d2 = 10;

        int expected = 540;
        int actual = solution.getCount(maxR, maxG, maxB, startR, startG, startB, d1, d2);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        int maxR = 6;
        int maxG = 9;
        int maxB = 10;
        int startR = 1;
        int startG = 2;
        int startB = 3;
        int d1 = 4;
        int d2 = 10;

        int expected = 330;
        int actual = solution.getCount(maxR, maxG, maxB, startR, startG, startB, d1, d2);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase5() {
        int maxR = 49;
        int maxG = 59;
        int maxB = 53;
        int startR = 12;
        int startG = 23;
        int startB = 13;
        int d1 = 11;
        int d2 = 22;

        int expected = 47439;
        int actual = solution.getCount(maxR, maxG, maxB, startR, startG, startB, d1, d2);

        Assert.assertEquals(expected, actual);
    }

}
