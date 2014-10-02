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

    @Test(timeout = 200000)
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

    @Test(timeout = 2000)
    public void testCase6() {
        String[] love = new String[]{"YNNNN", "NNNYN", "NNNNN", "YNNNN", "NYNNN"};

        int expected = 2;
        int actual = solution.maxMagicalGirls(love);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase7() {
        String[] love = new String[]{"YNNNNYN", "NYNNNYN", "NNNNYNY", "NYNNNNN", "NYYYNNN", "NNNNNNN", "NNNNNYN"};

        int expected = 2;
        int actual = solution.maxMagicalGirls(love);

        Assert.assertEquals(expected, actual);
    }   

}
