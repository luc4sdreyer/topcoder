import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CubeStickersTest {

    protected CubeStickers solution;

    @Before
    public void setUp() {
        solution = new CubeStickers();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        String[] sticker = new String[]{"cyan", "magenta", "yellow", "purple", "black", "white", "purple"};

        String expected = "YES";
        String actual = solution.isPossible(sticker);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        String[] sticker = new String[]{"blue", "blue", "blue", "blue", "blue", "blue", "blue", "blue", "blue", "blue"};

        String expected = "NO";
        String actual = solution.isPossible(sticker);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        String[] sticker = new String[]{"red", "yellow", "blue", "red", "yellow", "blue", "red", "yellow", "blue"};

        String expected = "YES";
        String actual = solution.isPossible(sticker);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        String[] sticker = new String[]{"purple", "orange", "orange", "purple", "black", "orange", "purple"};

        String expected = "NO";
        String actual = solution.isPossible(sticker);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        String[] sticker = new String[]{"white", "gray", "green", "blue", "yellow", "red", "target", "admin"};

        String expected = "YES";
        String actual = solution.isPossible(sticker);

        Assert.assertEquals(expected, actual);
    }

}
