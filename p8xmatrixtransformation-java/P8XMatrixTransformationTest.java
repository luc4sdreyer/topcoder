import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class P8XMatrixTransformationTest {

    protected P8XMatrixTransformation solution;

    @Before
    public void setUp() {
        solution = new P8XMatrixTransformation();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        String[] original = new String[]{"01", "11"};
        String[] target = new String[]{"11", "10"};

        String expected = "YES";
        String actual = solution.solve(original, target);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        String[] original = new String[]{"0111", "0011"};
        String[] target = new String[]{"1011", "1100"};

        String expected = "YES";
        String actual = solution.solve(original, target);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        String[] original = new String[]{"0"};
        String[] target = new String[]{"1"};

        String expected = "NO";
        String actual = solution.solve(original, target);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        String[] original = new String[]{"1111", "1111", "0000", "0000"};
        String[] target = new String[]{"1111", "1111", "0000", "0000"};

        String expected = "YES";
        String actual = solution.solve(original, target);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        String[] original = new String[]{"0110", "1001", "0110"};
        String[] target = new String[]{"1111", "0110", "0000"};

        String expected = "YES";
        String actual = solution.solve(original, target);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase5() {
        String[] original = new String[]{"0000", "1111", "0000"};
        String[] target = new String[]{"1111", "0000", "1111"};

        String expected = "NO";
        String actual = solution.solve(original, target);

        Assert.assertEquals(expected, actual);
    }

}
