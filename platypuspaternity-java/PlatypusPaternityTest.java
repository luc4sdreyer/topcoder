import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PlatypusPaternityTest {

    protected PlatypusPaternity solution;

    @Before
    public void setUp() {
        solution = new PlatypusPaternity();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        String[] femaleCompatibility = new String[]{"YYYY", "YYYY"};
        String[] maleCompatibility = new String[]{"NNYN", "YYYN"};
        String[] siblingGroups = new String[]{"YYYN", "NNNY"};

        int expected = 5;
        int actual = solution.maxFamily(femaleCompatibility, maleCompatibility, siblingGroups);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        String[] femaleCompatibility = new String[]{"NNYYY"};
        String[] maleCompatibility = new String[]{"YYNNN"};
        String[] siblingGroups = new String[]{"YYNNN", "NNYYY"};

        int expected = 0;
        int actual = solution.maxFamily(femaleCompatibility, maleCompatibility, siblingGroups);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        String[] femaleCompatibility = new String[]{"YYYYYYYYN"};
        String[] maleCompatibility = new String[]{"YYYYYYYYY"};
        String[] siblingGroups = new String[]{"YNYNYNYNY", "NNNYNYNNN", "NYNNNNNYN"};

        int expected = 4;
        int actual = solution.maxFamily(femaleCompatibility, maleCompatibility, siblingGroups);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        String[] femaleCompatibility = new String[]{"YY"};
        String[] maleCompatibility = new String[]{"YY"};
        String[] siblingGroups = new String[]{"YN", "NY"};

        int expected = 3;
        int actual = solution.maxFamily(femaleCompatibility, maleCompatibility, siblingGroups);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        String[] femaleCompatibility = new String[]{"YYNNYYNNYYNN", "YNYNYNYNYNYN", "YYYNNNYYYNNN"};
        String[] maleCompatibility = new String[]{"NYYNNYYNNYYN", "YYNYYYNYYYNY", "NNNNNNYYYYYY"};
        String[] siblingGroups = new String[]{"NYNNNYNNNNNN", "NNNNNNNNYNNN", "NNYNNNNNNNYN", "YNNNNNNYNNNN", "NNNNNNNNNYNY", "NNNYYNYNNNNN"};

        int expected = 4;
        int actual = solution.maxFamily(femaleCompatibility, maleCompatibility, siblingGroups);

        Assert.assertEquals(expected, actual);
    }

}
