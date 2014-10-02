import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AlphabetPathTest {

    protected AlphabetPath solution;

    @Before
    public void setUp() {
        solution = new AlphabetPath();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        String[] letterMaze = new String[]{"ABCDEFGHIJKLMNOPQRSTUVWXYZ"};

        String expected = "YES";
        String actual = solution.doesItExist(letterMaze);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        String[] letterMaze = new String[]{"ADEHI..Z", "BCFGJK.Y", ".PONML.X", ".QRSTUVW"};

        String expected = "YES";
        String actual = solution.doesItExist(letterMaze);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        String[] letterMaze = new String[]{"ACBDEFGHIJKLMNOPQRSTUVWXYZ"};

        String expected = "NO";
        String actual = solution.doesItExist(letterMaze);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        String[] letterMaze = new String[]{"ABC.......", "...DEFGHIJ", "TSRQPONMLK", "UVWXYZ...."};

        String expected = "NO";
        String actual = solution.doesItExist(letterMaze);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        String[] letterMaze = new String[]{"..............", "..............", "..............", "...DEFGHIJK...", "...C......L...", "...B......M...", "...A......N...", "..........O...", "..ZY..TSRQP...", "...XWVU.......", ".............."};

        String expected = "YES";
        String actual = solution.doesItExist(letterMaze);

        Assert.assertEquals(expected, actual);
    }

}
