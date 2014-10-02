import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EllysPairsTest {

    protected EllysPairs solution;

    @Before
    public void setUp() {
        solution = new EllysPairs();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        int[] knowledge = new int[]{2, 6, 4, 3};

        int expected = 1;
        int actual = solution.getDifference(knowledge);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int[] knowledge = new int[]{1, 1, 1, 1, 1, 1};

        int expected = 0;
        int actual = solution.getDifference(knowledge);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int[] knowledge = new int[]{4, 2, 4, 2, 1, 3, 3, 7};

        int expected = 2;
        int actual = solution.getDifference(knowledge);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int[] knowledge = new int[]{5, 1, 8, 8, 13, 7, 6, 2, 1, 9, 5, 11, 3, 4};

        int expected = 3;
        int actual = solution.getDifference(knowledge);

        Assert.assertEquals(expected, actual);
    }

}
