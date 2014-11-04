import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ColorfulRabbitsTest {

    protected ColorfulRabbits solution;

    @Before
    public void setUp() {
        solution = new ColorfulRabbits();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        int[] replies = new int[]{1, 1, 2, 2};

        int expected = 5;
        int actual = solution.getMinimum(replies);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int[] replies = new int[]{0};

        int expected = 1;
        int actual = solution.getMinimum(replies);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int[] replies = new int[]{2, 2, 44, 2, 2, 2, 444, 2, 2};

        int expected = 499;
        int actual = solution.getMinimum(replies);

        Assert.assertEquals(expected, actual);
    }   


    @Test(timeout = 2000)
    public void testCase10() {
        int[] replies = new int[]{2, 2, 2};

        int expected = 3;
        int actual = solution.getMinimum(replies);

        Assert.assertEquals(expected, actual);
    }  


    @Test(timeout = 2000)
    public void testCase11() {
        int[] replies = new int[]{0, 0, 0};

        int expected = 3;
        int actual = solution.getMinimum(replies);

        Assert.assertEquals(expected, actual);
    }


    @Test(timeout = 2000)
    public void testCase12() {
        int[] replies = new int[]{10, 10, 10};

        int expected = 11;
        int actual = solution.getMinimum(replies);

        Assert.assertEquals(expected, actual);
    }



    @Test(timeout = 2000)
    public void testCase13() {
        int[] replies = new int[]{1, 0, 0};

        int expected = 4;
        int actual = solution.getMinimum(replies);

        Assert.assertEquals(expected, actual);
    }




    @Test(timeout = 2000)
    public void testCase18() {
        int[] replies = new int[]{1, 1};

        int expected = 2;
        int actual = solution.getMinimum(replies);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase19() {
        int[] replies = new int[]{1, 1, 1};

        int expected = 4;
        int actual = solution.getMinimum(replies);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000000)
    public void testCase14() {
        int[] replies = new int[]{1, 1, 1, 1};

        int expected = 4;
        int actual = solution.getMinimum(replies);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase16() {
        int[] replies = new int[]{1, 1, 1, 1, 1};

        int expected = 6;
        int actual = solution.getMinimum(replies);

        Assert.assertEquals(expected, actual);
    }


    @Test(timeout = 2000)
    public void testCase17() {
        int[] replies = new int[]{1, 1, 1, 1, 1, 1};

        int expected = 6;
        int actual = solution.getMinimum(replies);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase15() {
        int[] replies = new int[]{3, 3, 3, 3, 3};

        int expected = 8;
        int actual = solution.getMinimum(replies);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase20() {
        int[] replies = new int[]{3, 3};

        int expected = 4;
        int actual = solution.getMinimum(replies);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase21() {
        int[] replies = new int[]{3, 3, 3};

        int expected = 4;
        int actual = solution.getMinimum(replies);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase22() {
        int[] replies = new int[]{3, 3, 3, 3};

        int expected = 4;
        int actual = solution.getMinimum(replies);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase23() {
        int[] replies = new int[]{3, 3, 3, 3, 3};

        int expected = 8;
        int actual = solution.getMinimum(replies);

        Assert.assertEquals(expected, actual);
    }
}
