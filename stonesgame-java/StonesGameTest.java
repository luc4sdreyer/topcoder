import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StonesGameTest {

    protected StonesGame solution;

    @Before
    public void setUp() {
        solution = new StonesGame();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        int N = 3;
        int M = 1;
        int K = 1;
        int L = 2;

        String expected = "Draw";
        String actual = solution.winner(N, M, K, L);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int N = 5;
        int M = 1;
        int K = 2;
        int L = 2;

        String expected = "Romeo";
        String actual = solution.winner(N, M, K, L);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 200000)
    public void testCase2() {
        int N = 5;
        int M = 5;
        int K = 2;
        int L = 3;

        String expected = "Strangelet";
        String actual = solution.winner(N, M, K, L);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int N = 5;
        int M = 5;
        int K = 2;
        int L = 2;

        String expected = "Draw";
        String actual = solution.winner(N, M, K, L);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        int N = 1000000;
        int M = 804588;
        int K = 705444;
        int L = 292263;

        String expected = "Romeo";
        String actual = solution.winner(N, M, K, L);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 200000)
    public void testCase5() {
        int N = 1000000;
        int M = 100000;
        int K = 500000;
        int L = 600000;

        String expected = "Strangelet";
        String actual = solution.winner(N, M, K, L);

        Assert.assertEquals(expected, actual);
    }

}
