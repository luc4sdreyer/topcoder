import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RainyRoadTest {

    protected RainyRoad solution;

    @Before
    public void setUp() {
        solution = new RainyRoad();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        String[] road = new String[]{".W..", "...."};

        String expected = "YES";
        String actual = solution.isReachable(road);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        String[] road = new String[]{".W..", "..W."};

        String expected = "YES";
        String actual = solution.isReachable(road);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 200000)
    public void testCase2() {
        String[] road = new String[]{".W..W..", "...WWW."};

        String expected = "NO";
        String actual = solution.isReachable(road);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        String[] road = new String[]{"..", "WW"};

        String expected = "YES";
        String actual = solution.isReachable(road);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        String[] road = new String[]{".WWWW.", "WWWWWW"};

        String expected = "NO";
        String actual = solution.isReachable(road);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase5() {
        String[] road = new String[]{".W.W.W.", "W.W.W.W"};

        String expected = "YES";
        String actual = solution.isReachable(road);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase6() {
        String[] road = new String[]{".............................................W.", ".............................................W."};

        String expected = "NO";
        String actual = solution.isReachable(road);

        Assert.assertEquals(expected, actual);
    }

}
