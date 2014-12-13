import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DoorsGameTest {

    protected DoorsGame solution;

    @Before
    public void setUp() {
        solution = new DoorsGame();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        String doors = "ABCD";
        int trophy = 2;

        int expected = 3;
        int actual = solution.determineOutcome(doors, trophy);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        String doors = "ABCC";
        int trophy = 2;

        int expected = -2;
        int actual = solution.determineOutcome(doors, trophy);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        String doors = "ABABAB";
        int trophy = 3;

        int expected = 0;
        int actual = solution.determineOutcome(doors, trophy);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        String doors = "ABAPDCAA";
        int trophy = 5;

        int expected = -4;
        int actual = solution.determineOutcome(doors, trophy);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        String doors = "MOCFDCE";
        int trophy = 3;

        int expected = 5;
        int actual = solution.determineOutcome(doors, trophy);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase5() {
        String doors = "ABCCDE";
        int trophy = 3;

        int expected = 0;
        int actual = solution.determineOutcome(doors, trophy);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase6() {
        String doors = "ABCCD";
        int trophy = 3;

        int expected = 0;
        int actual = solution.determineOutcome(doors, trophy);

        Assert.assertEquals(expected, actual);
    }

}
