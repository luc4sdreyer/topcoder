import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ChickenOracleTest {

    protected ChickenOracle solution;

    @Before
    public void setUp() {
        solution = new ChickenOracle();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        int n = 10;
        int eggCount = 10;
        int lieCount = 0;
        int liarCount = 0;

        String expected = "The egg";
        String actual = solution.theTruth(n, eggCount, lieCount, liarCount);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int n = 60;
        int eggCount = 40;
        int lieCount = 0;
        int liarCount = 30;

        String expected = "The oracle is a lie";
        String actual = solution.theTruth(n, eggCount, lieCount, liarCount);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int n = 60;
        int eggCount = 20;
        int lieCount = 5;
        int liarCount = 25;

        String expected = "The chicken";
        String actual = solution.theTruth(n, eggCount, lieCount, liarCount);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int n = 1000;
        int eggCount = 500;
        int lieCount = 250;
        int liarCount = 250;

        String expected = "Ambiguous";
        String actual = solution.theTruth(n, eggCount, lieCount, liarCount);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        int n = 9;
        int eggCount = 2;
        int lieCount = 0;
        int liarCount = 2;

        String expected = "The chicken";
        String actual = solution.theTruth(n, eggCount, lieCount, liarCount);

        Assert.assertEquals(expected, actual);
    }


    @Test(timeout = 2000)
    public void testCase5() {
        int n = 1000000;
        int eggCount = 500000;
        int lieCount = 250000;
        int liarCount = 250000;

        String expected = "Ambiguous";
        String actual = solution.theTruth(n, eggCount, lieCount, liarCount);

        Assert.assertEquals(expected, actual);
    }


    @Test(timeout = 200000)
    public void testCase6() {
        int n = 7;
        int eggCount = 6;
        int lieCount = 5;
        int liarCount = 5;

        String expected = "The oracle is a lie";
        String actual = solution.theTruth(n, eggCount, lieCount, liarCount);

        Assert.assertEquals(expected, actual);
    }
}
