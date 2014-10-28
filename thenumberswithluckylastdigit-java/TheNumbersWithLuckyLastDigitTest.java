import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TheNumbersWithLuckyLastDigitTest {

    protected TheNumbersWithLuckyLastDigit solution;

    @Before
    public void setUp() {
        solution = new TheNumbersWithLuckyLastDigit();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        int n = 99;

        int expected = 4;
        int actual = solution.find(n);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int n = 11;

        int expected = 2;
        int actual = solution.find(n);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int n = 13;

        int expected = -1;
        int actual = solution.find(n);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int n = 1234567;

        int expected = 1;
        int actual = solution.find(n);

        Assert.assertEquals(expected, actual);
    }

}
