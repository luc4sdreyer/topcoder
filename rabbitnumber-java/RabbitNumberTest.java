import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RabbitNumberTest {

    protected RabbitNumber solution;

    @Before
    public void setUp() {
        solution = new RabbitNumber();
    }

    @Test(timeout = 200000)
    public void testCase0() {
        int low = 22;
        int high = 22;

        int expected = 1;
        int actual = solution.theCount(low, high);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int low = 484;
        int high = 484;

        int expected = 0;
        int actual = solution.theCount(low, high);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int low = 1;
        int high = 58;

        int expected = 12;
        int actual = solution.theCount(low, high);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int low = 58;
        int high = 484;

        int expected = 24;
        int actual = solution.theCount(low, high);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        int low = 1000000000;
        int high = 1000000000;

        int expected = 1;
        int actual = solution.theCount(low, high);

        Assert.assertEquals(expected, actual);
    }

}
