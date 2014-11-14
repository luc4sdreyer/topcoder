import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TheCoffeeTimeDivTwoTest {

    protected TheCoffeeTimeDivTwo solution;

    @Before
    public void setUp() {
        solution = new TheCoffeeTimeDivTwo();
    }

    @Test(timeout = 200000)
    public void testCase0() {
        int n = 2;
        int[] tea = new int[]{1};

        int expected = 108;
        int actual = solution.find(n, tea);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int n = 2;
        int[] tea = new int[]{2, 1};

        int expected = 59;
        int actual = solution.find(n, tea);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int n = 15;
        int[] tea = new int[]{1, 2, 3, 4, 5, 6, 7};

        int expected = 261;
        int actual = solution.find(n, tea);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int n = 47;
        int[] tea = new int[]{1, 10, 6, 2, 4};

        int expected = 891;
        int actual = solution.find(n, tea);

        Assert.assertEquals(expected, actual);
    }


    @Test(timeout = 2000)
    public void testCase4() {
        int n = 0;
        int[] tea = new int[0];

        int expected = 0;
        int actual = solution.find(n, tea);

        Assert.assertEquals(expected, actual);
    }


    @Test(timeout = 2000)
    public void testCase5() {
        int n = 1;
        int[] tea = new int[]{1};

        int expected = 53;
        int actual = solution.find(n, tea);

        Assert.assertEquals(expected, actual);
    }
}
