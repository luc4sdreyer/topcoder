import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PikachuEasyTest {

    protected PikachuEasy solution;

    @Before
    public void setUp() {
        solution = new PikachuEasy();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        String word = "pikapi";

        String expected = "YES";
        String actual = solution.check(word);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        String word = "pipikachu";

        String expected = "YES";
        String actual = solution.check(word);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        String word = "pikaqiu";

        String expected = "NO";
        String actual = solution.check(word);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        String word = "topcoder";

        String expected = "NO";
        String actual = solution.check(word);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        String word = "piika";

        String expected = "NO";
        String actual = solution.check(word);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase5() {
        String word = "chupikachupipichu";

        String expected = "YES";
        String actual = solution.check(word);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase6() {
        String word = "pikapipachu";

        String expected = "NO";
        String actual = solution.check(word);

        Assert.assertEquals(expected, actual);
    }

}
