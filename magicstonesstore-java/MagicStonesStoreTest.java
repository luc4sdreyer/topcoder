import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MagicStonesStoreTest {

    protected MagicStonesStore solution;

    @Before
    public void setUp() {
        solution = new MagicStonesStore();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        int n = 1;

        String expected = "NO";
        String actual = solution.ableToDivide(n);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int n = 2;

        String expected = "YES";
        String actual = solution.ableToDivide(n);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int n = 3;

        String expected = "YES";
        String actual = solution.ableToDivide(n);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int n = 5;

        String expected = "YES";
        String actual = solution.ableToDivide(n);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        int n = 4;

        String expected = "YES";
        String actual = solution.ableToDivide(n);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase5() {
        int n = 6;

        String expected = "YES";
        String actual = solution.ableToDivide(n);

        Assert.assertEquals(expected, actual);
    }    


    @Test(timeout = 2000)
    public void testCase6() {
        int n = 7;

        String expected = "YES";
        String actual = solution.ableToDivide(n);

        Assert.assertEquals(expected, actual);
    }
    
    @Test(timeout = 2000)
    public void testCase7() {
    	for (int i = 2; i <= 1000; i++) {

            String expected = "YES";
            String actual = solution.ableToDivide(i);

            Assert.assertEquals(expected, actual);
		}

    }

}
