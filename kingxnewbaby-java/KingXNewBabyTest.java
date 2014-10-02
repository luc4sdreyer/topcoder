import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class KingXNewBabyTest {

    protected KingXNewBaby solution;

    @Before
    public void setUp() {
        solution = new KingXNewBaby();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        String name = "dengklek";

        String expected = "YES";
        String actual = solution.isValid(name);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        String name = "gofushar";

        String expected = "NO";
        String actual = solution.isValid(name);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        String name = "dolphinigle";

        String expected = "NO";
        String actual = solution.isValid(name);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        String name = "mystictc";

        String expected = "NO";
        String actual = solution.isValid(name);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        String name = "rngringo";

        String expected = "NO";
        String actual = solution.isValid(name);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase5() {
        String name = "misof";

        String expected = "NO";
        String actual = solution.isValid(name);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase6() {
        String name = "metelsky";

        String expected = "YES";
        String actual = solution.isValid(name);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase7() {
        String name = "a";

        String expected = "NO";
        String actual = solution.isValid(name);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase8() {
        String name = "aa";

        String expected = "NO";
        String actual = solution.isValid(name);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase9() {
        String name = "ab";

        String expected = "NO";
        String actual = solution.isValid(name);

        Assert.assertEquals(expected, actual);
    }


}
