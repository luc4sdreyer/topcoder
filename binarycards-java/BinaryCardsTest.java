import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BinaryCardsTest {

    protected BinaryCards solution;

    @Before
    public void setUp() {
        solution = new BinaryCards();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        long A = 6L;
        long B = 6L;

        long expected = 6L;
        long actual = solution.largestNumber(A, B);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        long A = 6L;
        long B = 7L;

        long expected = 7L;
        long actual = solution.largestNumber(A, B);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        long A = 6L;
        long B = 8L;

        long expected = 15L;
        long actual = solution.largestNumber(A, B);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        long A = 1L;
        long B = 11L;

        long expected = 15L;
        long actual = solution.largestNumber(A, B);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        long A = 35L;
        long B = 38L;

        long expected = 39L;
        long actual = solution.largestNumber(A, B);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase5() {
        long A = 1125899906842630L;
        long B = 1125899906842632L;

        long expected = 1125899906842639L;
        long actual = solution.largestNumber(A, B);

        Assert.assertEquals(expected, actual);
    }

}
