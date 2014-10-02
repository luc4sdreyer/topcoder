import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HawaiianTest {

    protected Hawaiian solution;

    @Before
    public void setUp() {
        solution = new Hawaiian();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        String sentence = "Hawaii is an island";

        String[] expected = new String[]{"Hawaii", "an"};
        String[] actual = solution.getWords(sentence);

        Assert.assertArrayEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        String sentence = "Mauna Kea and Mauna Koa are two mountains";

        String[] expected = new String[]{"Mauna", "Kea", "Mauna", "Koa"};
        String[] actual = solution.getWords(sentence);

        Assert.assertArrayEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        String sentence = "Pineapple grows in Hawaii";

        String[] expected = new String[]{"Pineapple", "in", "Hawaii"};
        String[] actual = solution.getWords(sentence);

        Assert.assertArrayEquals(expected, actual);
    }

}
