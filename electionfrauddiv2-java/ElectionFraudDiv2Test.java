import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ElectionFraudDiv2Test {

    protected ElectionFraudDiv2 solution;

    @Before
    public void setUp() {
        solution = new ElectionFraudDiv2();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        int[] percentages = new int[]{100};

        String expected = "NO";
        String actual = solution.IsFraudulent(percentages);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int[] percentages = new int[]{34, 34, 34};

        String expected = "YES";
        String actual = solution.IsFraudulent(percentages);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 200000)
    public void testCase2() {
        int[] percentages = new int[]{12, 12, 12, 12, 12, 12, 12, 12};

        String expected = "YES";
        String actual = solution.IsFraudulent(percentages);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 200000)
    public void testCase3() {
        int[] percentages = new int[]{13, 13, 13, 13, 13, 13, 13, 13};

        String expected = "NO";
        String actual = solution.IsFraudulent(percentages);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        int[] percentages = new int[]{0, 1, 100};

        String expected = "NO";
        String actual = solution.IsFraudulent(percentages);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase5() {
        int[] percentages = new int[]{3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5, 8, 9, 7, 9, 3, 2, 3, 8};

        String expected = "NO";
        String actual = solution.IsFraudulent(percentages);

        Assert.assertEquals(expected, actual);
    }

}
