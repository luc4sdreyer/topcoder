import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TwoRectanglesTest {

    protected TwoRectangles solution;

    @Before
    public void setUp() {
        solution = new TwoRectangles();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        int[] A = new int[]{0, 0, 3, 2};
        int[] B = new int[]{1, 1, 5, 3};

        String expected = "rectangle";
        String actual = solution.describeIntersection(A, B);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int[] A = new int[]{0, 0, 5, 3};
        int[] B = new int[]{1, 2, 2, 3};

        String expected = "rectangle";
        String actual = solution.describeIntersection(A, B);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int[] A = new int[]{1, 1, 6, 2};
        int[] B = new int[]{3, 2, 5, 4};

        String expected = "segment";
        String actual = solution.describeIntersection(A, B);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int[] A = new int[]{0, 1, 2, 3};
        int[] B = new int[]{2, 0, 5, 2};

        String expected = "segment";
        String actual = solution.describeIntersection(A, B);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        int[] A = new int[]{0, 0, 1, 1};
        int[] B = new int[]{1, 1, 5, 2};

        String expected = "point";
        String actual = solution.describeIntersection(A, B);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase5() {
        int[] A = new int[]{1, 1, 2, 2};
        int[] B = new int[]{3, 1, 4, 2};

        String expected = "none";
        String actual = solution.describeIntersection(A, B);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase6() {
        int[] A = new int[]{0, 0, 1000, 1000};
        int[] B = new int[]{0, 0, 1000, 1000};

        String expected = "rectangle";
        String actual = solution.describeIntersection(A, B);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase7() {
        int[] A = new int[]{0, 0, 1000, 1000};
        int[] B = new int[]{0, 0, 1000, 1000};

        String expected = "rectangle";
        String actual = solution.describeIntersection(A, B);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase8() {
        int[] A = new int[]{1, 1, 1, 1};
        int[] B = new int[]{1, 1, 1, 1};

        String expected = "point";
        String actual = solution.describeIntersection(A, B);

        Assert.assertEquals(expected, actual);
    }

}
