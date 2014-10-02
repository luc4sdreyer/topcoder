import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MultiNumberTest {

    protected MultiNumber solution;

    @Before
    public void setUp() {
        solution = new MultiNumber();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        int number = 1;

        String expected = "NO";
        String actual = solution.check(number);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int number = 1221;

        String expected = "YES";
        String actual = solution.check(number);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int number = 1236;

        String expected = "YES";
        String actual = solution.check(number);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int number = 4729382;

        String expected = "NO";
        String actual = solution.check(number);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        int number = 42393338;

        String expected = "YES";
        String actual = solution.check(number);

        Assert.assertEquals(expected, actual);
    }

}
