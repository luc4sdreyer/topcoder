import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GCDLCMEasyTest {

    protected GCDLCMEasy solution;

    @Before
    public void setUp() {
        solution = new GCDLCMEasy();
    }

    @Test(timeout = 200000)
    public void testCase0() {
        int n = 4;
        int[] A = new int[]{0, 1, 2, 3};
        int[] B = new int[]{1, 2, 3, 0};
        int[] G = new int[]{6, 6, 6, 6};
        int[] L = new int[]{12, 12, 12, 12};

        String expected = "Solution exists";
        String actual = solution.possible(n, A, B, G, L);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int n = 5;
        int[] A = new int[]{0, 1, 2, 3, 4};
        int[] B = new int[]{1, 2, 3, 4, 0};
        int[] G = new int[]{6, 6, 6, 6, 6};
        int[] L = new int[]{12, 12, 12, 12, 12};

        String expected = "Solution does not exist";
        String actual = solution.possible(n, A, B, G, L);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int n = 2;
        int[] A = new int[]{0, 0};
        int[] B = new int[]{1, 1};
        int[] G = new int[]{123, 123};
        int[] L = new int[]{456, 789};

        String expected = "Solution does not exist";
        String actual = solution.possible(n, A, B, G, L);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 200000)
    public void testCase3() {
        int n = 2;
        int[] A = new int[]{0};
        int[] B = new int[]{1};
        int[] G = new int[]{1234};
        int[] L = new int[]{5678};

        String expected = "Solution does not exist";
        String actual = solution.possible(n, A, B, G, L);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 200000)
    public void testCase4() {
        int n = 6;
        int[] A = new int[]{0, 0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 3, 3, 4};
        int[] B = new int[]{1, 2, 3, 4, 5, 2, 3, 4, 5, 3, 4, 5, 4, 5, 5};
        int[] G = new int[]{2, 2, 3, 3, 1, 2, 5, 1, 5, 1, 7, 7, 3, 5, 7};
        int[] L = new int[]{30, 42, 30, 42, 210, 70, 30, 210, 70, 210, 42, 70, 105, 105, 105};

        String expected = "Solution exists";
        String actual = solution.possible(n, A, B, G, L);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase5() {
        int n = 500;
        int[] A = new int[]{0};
        int[] B = new int[]{1};
        int[] G = new int[]{10000};
        int[] L = new int[]{10000};

        String expected = "Solution exists";
        String actual = solution.possible(n, A, B, G, L);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase6() {
        int n = 500;
        int[] A = new int[500];
        int[] B = new int[500];
        int[] G = new int[500];
        int[] L = new int[500];
        
        Arrays.fill(A, 0);
        Arrays.fill(B, 1);
        Arrays.fill(G, 10000);
        Arrays.fill(L, 10000);        

        String expected = "Solution does not exists";
        String actual = solution.possible(n, A, B, G, L);

        Assert.assertEquals(expected, actual);
    }

}
