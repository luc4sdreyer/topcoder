import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DengklekPaintingSquaresTest {

    protected DengklekPaintingSquares solution;

    @Before
    public void setUp() {
        solution = new DengklekPaintingSquares();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        int N = 1;
        int M = 1;

        int expected = 2;
        int actual = solution.numSolutions(N, M);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int N = 2;
        int M = 2;

        int expected = 8;
        int actual = solution.numSolutions(N, M);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int N = 1;
        int M = 3;

        int expected = 5;
        int actual = solution.numSolutions(N, M);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int N = 47;
        int M = 7;

        int expected = 944149920;
        int actual = solution.numSolutions(N, M);

        Assert.assertEquals(expected, actual);
    }

}
