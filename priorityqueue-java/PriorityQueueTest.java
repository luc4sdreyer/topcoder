import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PriorityQueueTest {

    protected PriorityQueue solution;

    @Before
    public void setUp() {
        solution = new PriorityQueue();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        String S = "bbbbb";
        int[] a = new int[]{1, 1, 1, 1, 1};

        int expected = 10;
        int actual = solution.findAnnoyance(S, a);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        String S = "bee";
        int[] a = new int[]{50, 40, 30};

        int expected = 0;
        int actual = solution.findAnnoyance(S, a);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        String S = "ebbe";
        int[] a = new int[]{5, 2, 11, 1};

        int expected = 12;
        int actual = solution.findAnnoyance(S, a);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        String S = "bbeebeebeeeebbb";
        int[] a = new int[]{58, 517, 301, 524, 79, 375, 641, 152, 810, 778, 222, 342, 911, 313, 336};

        int expected = 20485;
        int actual = solution.findAnnoyance(S, a);

        Assert.assertEquals(expected, actual);
    }

}
