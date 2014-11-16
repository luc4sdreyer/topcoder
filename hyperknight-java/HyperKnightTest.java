import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HyperKnightTest {

    protected HyperKnight solution;

    @Before
    public void setUp() {
        solution = new HyperKnight();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        int a = 2;
        int b = 1;
        int numRows = 8;
        int numColumns = 8;
        int k = 4;

        long expected = 20L;
        long actual = solution.countCells(a, b, numRows, numColumns, k);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int a = 3;
        int b = 2;
        int numRows = 8;
        int numColumns = 8;
        int k = 2;

        long expected = 16L;
        long actual = solution.countCells(a, b, numRows, numColumns, k);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int a = 1;
        int b = 3;
        int numRows = 7;
        int numColumns = 11;
        int k = 0;

        long expected = 0L;
        long actual = solution.countCells(a, b, numRows, numColumns, k);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int a = 3;
        int b = 2;
        int numRows = 10;
        int numColumns = 20;
        int k = 8;

        long expected = 56L;
        long actual = solution.countCells(a, b, numRows, numColumns, k);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        int a = 1;
        int b = 4;
        int numRows = 100;
        int numColumns = 10;
        int k = 6;

        long expected = 564L;
        long actual = solution.countCells(a, b, numRows, numColumns, k);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase5() {
        int a = 2;
        int b = 3;
        int numRows = 1000000000;
        int numColumns = 1000000000;
        int k = 8;

        long expected = 999999988000000036L;
        long actual = solution.countCells(a, b, numRows, numColumns, k);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase10() {
        int a = 2;
        int b = 1;
        int numRows = 5;
        int numColumns = 5;

        Assert.assertEquals(0, solution.countCells(a, b, numRows, numColumns, 0));
        Assert.assertEquals(0, solution.countCells(a, b, numRows, numColumns, 1));
        Assert.assertEquals(4, solution.countCells(a, b, numRows, numColumns, 2));
        Assert.assertEquals(8, solution.countCells(a, b, numRows, numColumns, 3));
        Assert.assertEquals(8, solution.countCells(a, b, numRows, numColumns, 4));
        Assert.assertEquals(0, solution.countCells(a, b, numRows, numColumns, 5));
        Assert.assertEquals(4, solution.countCells(a, b, numRows, numColumns, 6));
        Assert.assertEquals(0, solution.countCells(a, b, numRows, numColumns, 7));
        Assert.assertEquals(1, solution.countCells(a, b, numRows, numColumns, 8));
    }


    @Test(timeout = 2000)
    public void testCase11() {
        int a = 2;
        int b = 1;
        int numRows = 6;
        int numColumns = 6;

        Assert.assertEquals(0, solution.countCells(a, b, numRows, numColumns, 0));
        Assert.assertEquals(0, solution.countCells(a, b, numRows, numColumns, 1));
        Assert.assertEquals(4, solution.countCells(a, b, numRows, numColumns, 2));
        Assert.assertEquals(8, solution.countCells(a, b, numRows, numColumns, 3));
        Assert.assertEquals(12, solution.countCells(a, b, numRows, numColumns, 4));
        Assert.assertEquals(0, solution.countCells(a, b, numRows, numColumns, 5));
        Assert.assertEquals(8, solution.countCells(a, b, numRows, numColumns, 6));
        Assert.assertEquals(0, solution.countCells(a, b, numRows, numColumns, 7));
        Assert.assertEquals(4, solution.countCells(a, b, numRows, numColumns, 8));
    }
}
