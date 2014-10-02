import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AkariDaisukiDiv2Test {

    protected AkariDaisukiDiv2 solution;

    @Before
    public void setUp() {
        solution = new AkariDaisukiDiv2();
    }

    @Test(timeout = 200000)
    public void testCase0() {
        String S = "topcoderdivtwo";

        int expected = 2;
        int actual = solution.countTuples(S);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        String S = "foxciel";

        int expected = 0;
        int actual = solution.countTuples(S);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        String S = "magicalgirl";

        int expected = 4;
        int actual = solution.countTuples(S);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        String S = "waaiusushioakariusushiodaisuki";

        int expected = 75;
        int actual = solution.countTuples(S);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        String S = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";

        int expected = 8924;
        int actual = solution.countTuples(S);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase5() {
        String S = "a";

        int expected = 0;
        int actual = solution.countTuples(S);

        Assert.assertEquals(expected, actual);
    }

}
