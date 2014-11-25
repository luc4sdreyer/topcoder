import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EllysXorsTest {

    protected EllysXors solution;

    @Before
    public void setUp() {
        solution = new EllysXors();
    }

    @Test(timeout = 2000000)
    public void testCase0() {
        long L = 3L;
        long R = 10L;

        long expected = 8L;
        long actual = solution.getXor(L, R);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        long L = 5L;
        long R = 5L;

        long expected = 5L;
        long actual = solution.getXor(L, R);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        long L = 13L;
        long R = 42L;

        long expected = 39L;
        long actual = solution.getXor(L, R);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        long L = 666L;
        long R = 1337L;

        long expected = 0L;
        long actual = solution.getXor(L, R);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        long L = 1234567L;
        long R = 89101112L;

        long expected = 89998783L;
        long actual = solution.getXor(L, R);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 20000000)
    public void testCase5() {
    	for (int i = 1; i < 1000; i++) {
			for (int j = i; j < 1000; j++) {
				long exp = getAnswer(i, j);
				long act = solution.getXor(i, j);
				//System.out.println(i + "\t" + j + "\t" + exp + "\t" + (exp%2) + "\t" +(act%2));
				Assert.assertEquals(exp, act);
			}
			System.out.println();
		}
    }

    @Test(timeout = 2000)
    public void testCase6() {
        long L = 1;
        long R = 4000000000L;

        long expected = 4000000000L;
        long actual = solution.getXor(L, R);

        Assert.assertEquals(expected, actual);
    }
    
    public long getAnswer(long L, long R) {
    	long sum = L;
    	for (long i = L+1; i <= R; i++) {
			sum = sum ^ i;
		}
    	return sum;
    }

}
