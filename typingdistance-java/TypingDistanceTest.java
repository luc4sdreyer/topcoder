import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TypingDistanceTest {

    protected TypingDistance solution;

    @Before
    public void setUp() {
        solution = new TypingDistance();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        String keyboard = "qwertyuiop";
        String word = "potter";

        int expected = 8;
        int actual = solution.minDistance(keyboard, word);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        String keyboard = "tc";
        String word = "tctcttccctccccttc";

        int expected = 9;
        int actual = solution.minDistance(keyboard, word);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        String keyboard = "a";
        String word = "aaaaaaaaaaa";

        int expected = 0;
        int actual = solution.minDistance(keyboard, word);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        String keyboard = "kwadrutove";
        String word = "rowerowe";

        int expected = 39;
        int actual = solution.minDistance(keyboard, word);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        String keyboard = "qwertyuiopasdfghjklzxcvbnm";
        String word = "topcodersingleroundmatchgoodluckhavefun";

        int expected = 322;
        int actual = solution.minDistance(keyboard, word);

        Assert.assertEquals(expected, actual);
    }

}
