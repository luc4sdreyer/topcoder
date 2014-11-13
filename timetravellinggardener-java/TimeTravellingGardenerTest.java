import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TimeTravellingGardenerTest {

    protected TimeTravellingGardener solution;

    @Before
    public void setUp() {
        solution = new TimeTravellingGardener();
    }

    @Test(timeout = 200000)
    public void testCase0() {
        int[] distance = new int[]{2, 2};
        int[] height = new int[]{1, 3, 10};

        int expected = 1;
        int actual = solution.determineUsage(distance, height);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 200000)
    public void testCase1() {
        int[] distance = new int[]{3, 3};
        int[] height = new int[]{3, 1, 3};

        int expected = 2;
        int actual = solution.determineUsage(distance, height);

        Assert.assertEquals(expected, actual);
    }
    


    @Test(timeout = 200000)
    public void testCase7() {
        int[] distance = new int[]{3, 3, 3};
        int[] height = new int[]{2, 2, 3, 4};

        int expected = 1;
        int actual = solution.determineUsage(distance, height);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 200000)
    public void testCase2() {
        int[] distance = new int[]{1, 3};
        int[] height = new int[]{4, 4, 4};

        int expected = 0;
        int actual = solution.determineUsage(distance, height);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int[] distance = new int[]{4, 2};
        int[] height = new int[]{9, 8, 5};

        int expected = 1;
        int actual = solution.determineUsage(distance, height);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        int[] distance = new int[]{476, 465, 260, 484};
        int[] height = new int[]{39, 13, 8, 72, 80};

        int expected = 3;
        int actual = solution.determineUsage(distance, height);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase5() {
        int[] distance = new int[]{173, 36, 668, 79, 26, 544};
        int[] height = new int[]{488, 743, 203, 446, 444, 91, 453};

        int expected = 5;
        int actual = solution.determineUsage(distance, height);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase6() {
        int[] distance = new int[]{2, 4, 2, 2, 4, 2, 4, 2, 2, 4};
        int[] height = new int[]{2, 2, 10, 10, 10, 16, 16, 22, 22, 28, 28};

        int expected = 6;
        int actual = solution.determineUsage(distance, height);

        Assert.assertEquals(expected, actual);
    }

}
