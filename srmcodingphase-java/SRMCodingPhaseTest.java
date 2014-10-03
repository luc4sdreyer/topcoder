import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SRMCodingPhaseTest {

    protected SRMCodingPhase solution;

    @Before
    public void setUp() {
        solution = new SRMCodingPhase();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        int[] points = new int[]{250, 500, 1000};
        int[] skills = new int[]{10, 25, 40};
        int luck = 0;

        int expected = 1310;
        int actual = solution.countScore(points, skills, luck);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int[] points = new int[]{300, 600, 900};
        int[] skills = new int[]{30, 65, 90};
        int luck = 25;

        int expected = 680;
        int actual = solution.countScore(points, skills, luck);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int[] points = new int[]{250, 550, 950};
        int[] skills = new int[]{10, 25, 40};
        int luck = 75;

        int expected = 1736;
        int actual = solution.countScore(points, skills, luck);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int[] points = new int[]{256, 512, 1024};
        int[] skills = new int[]{35, 30, 25};
        int luck = 0;

        int expected = 1216;
        int actual = solution.countScore(points, skills, luck);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        int[] points = new int[]{300, 600, 1100};
        int[] skills = new int[]{80, 90, 100};
        int luck = 4;

        int expected = 0;
        int actual = solution.countScore(points, skills, luck);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase5() {
        int[] points = new int[]{253, 517, 1033};
        int[] skills = new int[]{33, 79, 58};
        int luck = 80;

        int expected = 1318;
        int actual = solution.countScore(points, skills, luck);

        Assert.assertEquals(expected, actual);
    }

}
