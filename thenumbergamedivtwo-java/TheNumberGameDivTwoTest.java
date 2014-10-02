import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TheNumberGameDivTwoTest {

    protected TheNumberGameDivTwo solution;

    @Before
    public void setUp() {
        solution = new TheNumberGameDivTwo();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        int n = 6;

        String expected = "John";
        String actual = solution.find(n);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int n = 2;

        String expected = "Brus";
        String actual = solution.find(n);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int n = 747;

        String expected = "Brus";
        String actual = solution.find(n);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int n = 128;

        String expected = "Brus";
        String actual = solution.find(n);

        Assert.assertEquals(expected, actual);
    }

}
