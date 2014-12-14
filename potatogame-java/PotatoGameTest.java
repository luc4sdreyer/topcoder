import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PotatoGameTest {

    protected PotatoGame solution;

    @Before
    public void setUp() {
        solution = new PotatoGame();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        int n = 1;

        String expected = "Taro";
        String actual = solution.theWinner(n);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int n = 2;

        String expected = "Hanako";
        String actual = solution.theWinner(n);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int n = 3;

        String expected = "Taro";
        String actual = solution.theWinner(n);

        Assert.assertEquals(expected, actual);
    }

}
