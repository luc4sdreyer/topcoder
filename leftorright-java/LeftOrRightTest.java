import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LeftOrRightTest {

    protected LeftOrRight solution;

    @Before
    public void setUp() {
        solution = new LeftOrRight();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        String program = "LLLRLRRR";

        int expected = 3;
        int actual = solution.maxDistance(program);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testCase1() {
        String program = "R???L";

        int expected = 4;
        int actual = solution.maxDistance(program);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        String program = "??????";

        int expected = 6;
        int actual = solution.maxDistance(program);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        String program = "LL???RRRRRRR???";

        int expected = 11;
        int actual = solution.maxDistance(program);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        String program = "L?L?";

        int expected = 4;
        int actual = solution.maxDistance(program);

        Assert.assertEquals(expected, actual);
    }

}
