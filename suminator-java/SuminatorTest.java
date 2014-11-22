import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SuminatorTest {

    protected Suminator solution;

    @Before
    public void setUp() {
        solution = new Suminator();
    }

    @Test(timeout = 2000000)
    public void testCase0() {
        int[] program = new int[]{7, -1, 0};
        int wantedResult = 10;

        int expected = 3;
        int actual = solution.findMissing(program, wantedResult);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int[] program = new int[]{100, 200, 300, 0, 100, -1};
        int wantedResult = 600;

        int expected = 0;
        int actual = solution.findMissing(program, wantedResult);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int[] program = new int[]{-1, 7, 3, 0, 1, 2, 0, 0};
        int wantedResult = 13;

        int expected = 0;
        int actual = solution.findMissing(program, wantedResult);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int[] program = new int[]{-1, 8, 4, 0, 1, 2, 0, 0};
        int wantedResult = 16;

        int expected = -1;
        int actual = solution.findMissing(program, wantedResult);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        int[] program = new int[]{1000000000, 1000000000, 1000000000, 1000000000, -1, 0, 0, 0, 0};
        int wantedResult = 1000000000;

        int expected = -1;
        int actual = solution.findMissing(program, wantedResult);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase5() {
        int[] program = new int[]{7, -1, 3, 0};
        int wantedResult = 3;

        int expected = -1;
        int actual = solution.findMissing(program, wantedResult);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase6() {
        int[] program = new int[50];
        for (int i = 0; i < program.length; i++) {
        	program[i] = 2000000000;
		}
        int wantedResult = 0;

        int expected = -1;
        int actual = solution.findMissing(program, wantedResult);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 20000000)
    public void testCase7() {
        int[] program = new int[]{90456225, 85312801, -1, 93541849, 90015805, 0, 0, 79829127, 55845796, 0, 0, 14659918, 18832075, 7515373, 0, 60031859, 0, 0, 0, 93327133, 0, 50620889, 66619994, 66426447, 0, 0, 88287780, 13169100, 0, 0, 0, 12158979, 0};
        int wantedResult = 849835476;

        int expected = 38953352;
        int actual = solution.findMissing(program, wantedResult);

        Assert.assertEquals(expected, actual);
    } 

}

