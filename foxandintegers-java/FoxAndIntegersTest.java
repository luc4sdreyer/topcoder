import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FoxAndIntegersTest {

    protected FoxAndIntegers solution;

    @Before
    public void setUp() {
        solution = new FoxAndIntegers();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        int AminusB = 1;
        int BminusC = -2;
        int AplusB = 3;
        int BplusC = 4;

        int[] expected = new int[]{2, 1, 3};
        int[] actual = solution.get(AminusB, BminusC, AplusB, BplusC);

        Assert.assertArrayEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int AminusB = 0;
        int BminusC = 0;
        int AplusB = 5;
        int BplusC = 5;

        int[] expected = new int[]{};
        int[] actual = solution.get(AminusB, BminusC, AplusB, BplusC);

        Assert.assertArrayEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int AminusB = 10;
        int BminusC = -23;
        int AplusB = -10;
        int BplusC = 3;

        int[] expected = new int[]{0, -10, 13};
        int[] actual = solution.get(AminusB, BminusC, AplusB, BplusC);

        Assert.assertArrayEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int AminusB = -27;
        int BminusC = 14;
        int AplusB = 13;
        int BplusC = 22;

        int[] expected = new int[]{};
        int[] actual = solution.get(AminusB, BminusC, AplusB, BplusC);

        Assert.assertArrayEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        int AminusB = 30;
        int BminusC = 30;
        int AplusB = 30;
        int BplusC = -30;

        int[] expected = new int[]{30, 0, -30};
        int[] actual = solution.get(AminusB, BminusC, AplusB, BplusC);

        Assert.assertArrayEquals(expected, actual);
    }

}
