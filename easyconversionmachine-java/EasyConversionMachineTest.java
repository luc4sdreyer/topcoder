import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EasyConversionMachineTest {

    protected EasyConversionMachine solution;

    @Before
    public void setUp() {
        solution = new EasyConversionMachine();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        String originalWord = "aababba";
        String finalWord = "bbbbbbb";
        int k = 2;

        String expected = "IMPOSSIBLE";
        String actual = solution.isItPossible(originalWord, finalWord, k);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        String originalWord = "aabb";
        String finalWord = "aabb";
        int k = 1;

        String expected = "IMPOSSIBLE";
        String actual = solution.isItPossible(originalWord, finalWord, k);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        String originalWord = "aaaaabaa";
        String finalWord = "bbbbbabb";
        int k = 8;

        String expected = "POSSIBLE";
        String actual = solution.isItPossible(originalWord, finalWord, k);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        String originalWord = "aaa";
        String finalWord = "bab";
        int k = 4;

        String expected = "POSSIBLE";
        String actual = solution.isItPossible(originalWord, finalWord, k);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        String originalWord = "aababbabaa";
        String finalWord = "abbbbaabab";
        int k = 9;

        String expected = "IMPOSSIBLE";
        String actual = solution.isItPossible(originalWord, finalWord, k);

        Assert.assertEquals(expected, actual);
    }

}
