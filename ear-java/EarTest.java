import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EarTest {

    protected Ear solution;

    @Before
    public void setUp() {
        solution = new Ear();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        String[] redX = new String[]{"3 2 8 7"};
        String[] blueX = new String[]{"5 4"};
        String[] blueY = new String[]{"2 4"};

        long expected = 1L;
        long actual = solution.getCount(redX, blueX, blueY);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        String[] redX = new String[]{"3 2 8 7"};
        String[] blueX = new String[]{"2 8"};
        String[] blueY = new String[]{"3 4"};

        long expected = 0L;
        long actual = solution.getCount(redX, blueX, blueY);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        String[] redX = new String[]{"1 2 6 9"};
        String[] blueX = new String[]{"3 6 8 5"};
        String[] blueY = new String[]{"1 5 4 3"};

        long expected = 4L;
        long actual = solution.getCount(redX, blueX, blueY);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        String[] redX = new String[]{"10000"};
        String[] blueX = new String[]{"10000 9999"};
        String[] blueY = new String[]{"10000 9999"};

        long expected = 0L;
        long actual = solution.getCount(redX, blueX, blueY);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        String[] redX = new String[]{"100 2", "00", " 39", "9", " 800 900 9", "99"};
        String[] blueX = new String[]{"15", "0 250 ", "349"};
        String[] blueY = new String[]{"2 3 1"};

        long expected = 12L;
        long actual = solution.getCount(redX, blueX, blueY);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase5() {
        String[] redX = new String[]{"1", " ", "2", " ", "3", " ", "4 5 6", " 7 8 9"};
        String[] blueX = new String[]{"4", " ", "5", " ", "6", " 7 ", "8"};
        String[] blueY = new String[]{"1", " 2 ", "3 4", " 5"};

        long expected = 204L;
        long actual = solution.getCount(redX, blueX, blueY);

        Assert.assertEquals(expected, actual);
    }

}
