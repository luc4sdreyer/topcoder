import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class IncubatorEasyTest {

    protected IncubatorEasy solution;

    @Before
    public void setUp() {
        solution = new IncubatorEasy();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        String[] love = new String[]{"NY", "NN"};

        int expected = 1;
        int actual = solution.maxMagicalGirls(love);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        String[] love = new String[]{"NYN", "NNY", "NNN"};

        int expected = 1;
        int actual = solution.maxMagicalGirls(love);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        String[] love = new String[]{"NNYNN", "NNYNN", "NNNYY", "NNNNN", "NNNNN"};

        int expected = 2;
        int actual = solution.maxMagicalGirls(love);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        String[] love = new String[]{"NNNNN", "NYNNN", "NYNYN", "YNYNN", "NNNNN"};

        int expected = 2;
        int actual = solution.maxMagicalGirls(love);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        String[] love = new String[]{"NNNNN", "NNNNN", "NNNNN", "NNNNN", "NNNNN"};

        int expected = 5;
        int actual = solution.maxMagicalGirls(love);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase5() {
        String[] love = new String[]{"Y"};

        int expected = 0;
        int actual = solution.maxMagicalGirls(love);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 20000)
    public void testCase6() {
        String[] love = new String[]{"YYYYYYYYYY", "YYYYYYYYYY", "YYYYYYYYYY", "YYYYYYYYYY", "YYYYYYYYYY", "YYYYYYYYYY", "YYYYYYYYYY", "YYYYYYYYYY", "YYYYYYYYYY", "YYYYYYYYYY"};

        int expected = 0;
        int actual = solution.maxMagicalGirls(love);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 20000)
    public void testCase7() {
        String[] love = new String[]{"YYNNNYYYYY", "YYYYYYNNNY", "NNYYYNYYYY", "YYYYNNYYYY", "YYYYYNYYYY", "YYYYYYYYYY", "YYYYYYYYYY", "YYYYYYYYYY", "YYYYYYYYYY", "YYYYYYYYYY"};

        int expected = 0;
        int actual = solution.maxMagicalGirls(love);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase8() {
        String[] love = new String[]{"YYNNNYYYYY", "YYYYYYNNNY", "NNYYYNYYYY", "YYYYNNYYYY", "YYNYYNYYYY", "YYYNNNYYYY", "YYYNNYYYYY", "YYYNNYYYYY", "YYYYNNYYYY", "YYNYYYYYYY"};

        int expected = 0;
        int actual = solution.maxMagicalGirls(love);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 200000)
    public void testCase9() {
        String[] love = new String[]{"NNNNNNNNNN", "NNNNNNNNNN", "NNNNNNNNNN", "NNNNNNNNNN", "NNNNNNNNNN", "NNNNNNNNNN", "NNNNNNNNNN", "NNNNNNNNNN", "NNNNNNNNNN", "NNNNNNNNNN"};

        int expected = 10;
        int actual = solution.maxMagicalGirls(love);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase10() {
        String[] love = new String[]{"NNYNYNYYNN", "YNYNNYNNYY", "NNNNNNYNNN", "NNNNNYNNNY", "YNNNYYNYNN", "NNNNNYNNNN", "NNNNYNNNNY", "NNNNYNNNNN", "NNYNNNNNNN", "NNNYNYNNNY"};

        int expected = 1;
        int actual = solution.maxMagicalGirls(love);

        Assert.assertEquals(expected, actual);
    }

}
