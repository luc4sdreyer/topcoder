import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ToastXToastTest {

    protected ToastXToast solution;

    @Before
    public void setUp() {
        solution = new ToastXToast();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        int[] undertoasted = new int[]{2, 4};
        int[] overtoasted = new int[]{5, 6, 3};

        int expected = 2;
        int actual = solution.bake(undertoasted, overtoasted);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int[] undertoasted = new int[]{5};
        int[] overtoasted = new int[]{4};

        int expected = -1;
        int actual = solution.bake(undertoasted, overtoasted);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int[] undertoasted = new int[]{1, 2, 3};
        int[] overtoasted = new int[]{5, 6, 7};

        int expected = 1;
        int actual = solution.bake(undertoasted, overtoasted);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int[] undertoasted = new int[]{1, 3, 5};
        int[] overtoasted = new int[]{2, 4, 6};

        int expected = 2;
        int actual = solution.bake(undertoasted, overtoasted);

        Assert.assertEquals(expected, actual);
    }

}
