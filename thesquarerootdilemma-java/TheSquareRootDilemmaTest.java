import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TheSquareRootDilemmaTest {

    protected TheSquareRootDilemma solution;

    @Before
    public void setUp() {
        solution = new TheSquareRootDilemma();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        int N = 2;
        int M = 2;

        int expected = 2;
        int actual = solution.countPairs(N, M);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int N = 10;
        int M = 1;

        int expected = 3;
        int actual = solution.countPairs(N, M);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int N = 3;
        int M = 8;

        int expected = 5;
        int actual = solution.countPairs(N, M);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int N = 100;
        int M = 100;

        int expected = 310;
        int actual = solution.countPairs(N, M);

        Assert.assertEquals(expected, actual);
    }

}
