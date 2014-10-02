import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PatrolRouteTest {

    protected PatrolRoute solution;

    @Before
    public void setUp() {
        solution = new PatrolRoute();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        int X = 3;
        int Y = 3;
        int minT = 1;
        int maxT = 20000;

        int expected = 6;
        int actual = solution.countRoutes(X, Y, minT, maxT);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int X = 3;
        int Y = 3;
        int minT = 4;
        int maxT = 7;

        int expected = 0;
        int actual = solution.countRoutes(X, Y, minT, maxT);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int X = 4;
        int Y = 6;
        int minT = 9;
        int maxT = 12;

        int expected = 264;
        int actual = solution.countRoutes(X, Y, minT, maxT);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int X = 7;
        int Y = 5;
        int minT = 13;
        int maxT = 18;

        int expected = 1212;
        int actual = solution.countRoutes(X, Y, minT, maxT);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        int X = 4000;
        int Y = 4000;
        int minT = 4000;
        int maxT = 14000;

        int expected = 859690013;
        int actual = solution.countRoutes(X, Y, minT, maxT);

        Assert.assertEquals(expected, actual);
    }

}
