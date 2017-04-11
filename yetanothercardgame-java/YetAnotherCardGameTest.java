import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class YetAnotherCardGameTest {

    protected YetAnotherCardGame solution;

    @Before
    public void setUp() {
        solution = new YetAnotherCardGame();
    }

    @Test(timeout = 200000)
    public void testCase0() {
        int[] petr = new int[]{2, 5};
        int[] snuke = new int[]{3, 1};

        int expected = 3;
        int actual = solution.maxCards(petr, snuke);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 200000)
    public void testCase1() {
        int[] petr = new int[]{1, 1, 1, 1, 1};
        int[] snuke = new int[]{1, 1, 1, 1, 1};

        int expected = 1;
        int actual = solution.maxCards(petr, snuke);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 20000000)
    public void testCase2() {
        int[] petr = new int[]{1, 4, 6, 7, 3};
        int[] snuke = new int[]{1, 7, 1, 5, 7};

        int expected = 6;
        int actual = solution.maxCards(petr, snuke);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int[] petr = new int[]{19, 99, 86, 30, 98, 68, 73, 92, 37, 69, 93, 28, 58, 36, 86, 32, 46, 34, 71, 29};
        int[] snuke = new int[]{28, 29, 22, 75, 78, 75, 39, 41, 5, 14, 100, 28, 51, 42, 9, 25, 12, 59, 98, 83};

        int expected = 28;
        int actual = solution.maxCards(petr, snuke);

        Assert.assertEquals(expected, actual);
    }

}
