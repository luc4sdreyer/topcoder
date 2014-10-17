import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MysteriousRestaurantTest {

    protected MysteriousRestaurant solution;

    @Before
    public void setUp() {
        solution = new MysteriousRestaurant();
    }

    @Test(timeout = 200000)
    public void testCase0() {
        String[] prices = new String[]{"26", "14", "72", "39", "32", "85", "06"};
        int budget = 13;

        int expected = 5;
        int actual = solution.maxDays(prices, budget);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 200000)
    public void testCase1() {
        String[] prices = new String[]{"26", "14", "72", "39", "32", "85", "06", "91"};
        int budget = 20;

        int expected = 8;
        int actual = solution.maxDays(prices, budget);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 200000)
    public void testCase2() {
        String[] prices = new String[]{"SRM", "512"};
        int budget = 4;

        int expected = 0;
        int actual = solution.maxDays(prices, budget);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 200000)
    public void testCase3() {
        String[] prices = new String[]{"Dear", "Code", "rsHa", "veFu", "nInT", "heCh", "alle", "ngeP", "hase", "andb", "ecar", "eful"};
        int budget = 256;

        int expected = 10;
        int actual = solution.maxDays(prices, budget);

        Assert.assertEquals(expected, actual);
    }   


    @Test(timeout = 200000)
    public void testCase4() {
        String[] prices = new String[]{"00", "00", "00"};
        int budget = 0;

        int expected = 3;
        int actual = solution.maxDays(prices, budget);

        Assert.assertEquals(expected, actual);
    }

}
