import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EllysTSPTest {

    protected EllysTSP solution;

    @Before
    public void setUp() {
        solution = new EllysTSP();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        String places = "CVVVC";

        int expected = 5;
        int actual = solution.getMax(places);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        String places = "VVV";

        int expected = 1;
        int actual = solution.getMax(places);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        String places = "VVVVCVV";

        int expected = 3;
        int actual = solution.getMax(places);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        String places = "CVCVCVC";

        int expected = 7;
        int actual = solution.getMax(places);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        String places = "V";

        int expected = 1;
        int actual = solution.getMax(places);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase5() {
        String places = "VV";

        int expected = 1;
        int actual = solution.getMax(places);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase6() {
        String places = "VVCC";

        int expected = 4;
        int actual = solution.getMax(places);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase7() {
        String places = "VVVCC";

        int expected = 5;
        int actual = solution.getMax(places);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase8() {
        String places = "VVVVCC";

        int expected = 5;
        int actual = solution.getMax(places);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase9() {
        String places = "VVVCCC";

        int expected = 6;
        int actual = solution.getMax(places);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase10() {
        String places = "VVVVCCC";

        int expected = 7;
        int actual = solution.getMax(places);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase11() {
        String places = "VVVVVCCC";

        int expected = 7;
        int actual = solution.getMax(places);

        Assert.assertEquals(expected, actual);
    }

}
