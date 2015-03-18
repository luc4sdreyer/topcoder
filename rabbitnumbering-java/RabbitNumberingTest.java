import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RabbitNumberingTest {

    protected RabbitNumbering solution;

    @Before
    public void setUp() {
        solution = new RabbitNumbering();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        int[] maxNumber = new int[]{5};

        int expected = 5;
        int actual = solution.theCount(maxNumber);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int[] maxNumber = new int[]{4, 4, 4, 4};

        int expected = 24;
        int actual = solution.theCount(maxNumber);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int[] maxNumber = new int[]{5, 8};

        int expected = 35;
        int actual = solution.theCount(maxNumber);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int[] maxNumber = new int[]{2, 1, 2};

        int expected = 0;
        int actual = solution.theCount(maxNumber);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        int[] maxNumber = new int[]{25, 489, 76, 98, 704, 98, 768, 39, 697, 8, 56, 74, 36, 95, 87, 2, 968, 4, 920, 54, 873, 90};

        int expected = 676780400;
        int actual = solution.theCount(maxNumber);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase5() {
        int[] maxNumber = new int[]{4, 3};

        int expected = 9;
        int actual = solution.theCount(maxNumber);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase6() {
        int[] maxNumber = new int[]{4, 3, 4};

        int expected = 18;
        int actual = solution.theCount(maxNumber);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase7() {
        int[] maxNumber = new int[]{3, 4, 5, 5};

        int expected = 54;
        int actual = solution.theCount(maxNumber);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase8() {
        int[] maxNumber = new int[]{3, 4, 5, 5, 5};

        int expected = 54;
        int actual = solution.theCount(maxNumber);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase9() {
        int[] maxNumber = new int[]{3, 4, 5, 5, 5, 5};

        int expected = 0;
        int actual = solution.theCount(maxNumber);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase10() {
        int[] maxNumber = new int[]{3, 4, 5, 5, 5, 5, 5};

        int expected = 0;
        int actual = solution.theCount(maxNumber);

        Assert.assertEquals(expected, actual);
    }

}
