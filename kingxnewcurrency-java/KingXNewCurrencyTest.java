import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class KingXNewCurrencyTest {

    protected KingXNewCurrency solution;

    @Before
    public void setUp() {
        solution = new KingXNewCurrency();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        int A = 5;
        int B = 8;
        int X = 5;

        int expected = 5;
        int actual = solution.howMany(A, B, X);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int A = 8;
        int B = 4;
        int X = 2;

        int expected = -1;
        int actual = solution.howMany(A, B, X);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int A = 7;
        int B = 4;
        int X = 13;

        int expected = 1;
        int actual = solution.howMany(A, B, X);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 200000)
    public void testCase3() {
        int A = 47;
        int B = 74;
        int X = 44;

        int expected = 2;
        int actual = solution.howMany(A, B, X);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        int A = 128;
        int B = 96;
        int X = 3;

        int expected = 65;
        int actual = solution.howMany(A, B, X);

        Assert.assertEquals(expected, actual);
    }

}
