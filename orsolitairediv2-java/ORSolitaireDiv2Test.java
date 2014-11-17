import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ORSolitaireDiv2Test {

    protected ORSolitaireDiv2 solution;

    @Before
    public void setUp() {
        solution = new ORSolitaireDiv2();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        int[] numbers = new int[]{1, 2, 4};
        int goal = 7;

        int expected = 1;
        int actual = solution.getMinimum(numbers, goal);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int[] numbers = new int[]{1, 2, 4, 7, 8};
        int goal = 7;

        int expected = 2;
        int actual = solution.getMinimum(numbers, goal);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int[] numbers = new int[]{12571295, 2174218, 2015120};
        int goal = 1;

        int expected = 0;
        int actual = solution.getMinimum(numbers, goal);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int[] numbers = new int[]{5, 2, 4, 52, 62, 9, 8, 3, 1, 11, 6};
        int goal = 11;

        int expected = 3;
        int actual = solution.getMinimum(numbers, goal);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        int[] numbers = new int[]{503, 505, 152, 435, 491, 512, 1023, 355, 510, 500, 502, 255, 63, 508, 509, 511, 60, 250, 254, 346};
        int goal = 510;

        int expected = 5;
        int actual = solution.getMinimum(numbers, goal);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase5() {
        int[] numbers = new int[]{1};
        int goal = 1;

        int expected = 1;
        int actual = solution.getMinimum(numbers, goal);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase6() {
        int[] numbers = new int[]{1, 2};
        int goal = 1;

        int expected = 1;
        int actual = solution.getMinimum(numbers, goal);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase7() {
        int[] numbers = new int[]{2, 3};
        int goal = 2;

        int expected = 1;
        int actual = solution.getMinimum(numbers, goal);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase8() {
        int[] numbers = new int[]{0b10110, 0b01000, 0b00000, 0b11111, 0b00110, 0b10101, 0b11111, 0b00000, 0b01111};
        int goal = 13;

        int expected = 0;
        int actual = solution.getMinimum(numbers, goal);

        Assert.assertEquals(expected, actual);
    }

}
