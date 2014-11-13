import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TheBoringStoreDivTwoTest {

    protected TheBoringStoreDivTwo solution;

    @Before
    public void setUp() {
        solution = new TheBoringStoreDivTwo();
    }

    @Test(timeout = 200000)
    public void testCase0() {
        String J = "StoreOfJohn";
        String B = "StoreOfBrus";

        String expected = "or";
        String actual = solution.find(J, B);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        String J = "JohnAndJohn";
        String B = "John";

        String expected = "";
        String actual = solution.find(J, B);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        String J = "JohnPlaysGames";
        String B = "BrusAlsoPlays";

        String expected = "ays";
        String actual = solution.find(J, B);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        String J = "abacaba";
        String B = "abacabadabacaba";

        String expected = "abaabacaba";
        String actual = solution.find(J, B);

        Assert.assertEquals(expected, actual);
    }

}
