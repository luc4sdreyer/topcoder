import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MissingParenthesesTest {

    protected MissingParentheses solution;

    @Before
    public void setUp() {
        solution = new MissingParentheses();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        String par = "(()(()";

        int expected = 2;
        int actual = solution.countCorrections(par);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        String par = "()()(()";

        int expected = 1;
        int actual = solution.countCorrections(par);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        String par = "(())(()())";

        int expected = 0;
        int actual = solution.countCorrections(par);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        String par = "())(())((()))))()((())))()())())())()()()";

        int expected = 7;
        int actual = solution.countCorrections(par);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 200000)
    public void testCase4() {
        String par = "())(";

        int expected = 2;
        int actual = solution.countCorrections(par);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase5() {
        String par = ")";

        int expected = 1;
        int actual = solution.countCorrections(par);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 200000)
    public void testCase6() {
        String par = "))((";

        int expected = 4;
        int actual = solution.countCorrections(par);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 200000)
    public void testCase7() {
        String par = "))))";

        int expected = 4;
        int actual = solution.countCorrections(par);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 200000)
    public void testCase8() {
        String par = "((((";

        int expected = 4;
        int actual = solution.countCorrections(par);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 200000)
    public void testCase9() {
        String par = "()(";

        int expected = 1;
        int actual = solution.countCorrections(par);

        Assert.assertEquals(expected, actual);
    }
}
