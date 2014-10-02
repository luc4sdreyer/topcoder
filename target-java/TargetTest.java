import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TargetTest {

    protected Target solution;

    @Before
    public void setUp() {
        solution = new Target();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        int n = 5;

        String[] expected = new String[]{"#####", "#   #", "# # #", "#   #", "#####"};
        String[] actual = solution.draw(n);

        Assert.assertArrayEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int n = 9;

        String[] expected = new String[]{"#########", "#       #", "# ##### #", "# #   # #", "# # # # #", "# #   # #", "# ##### #", "#       #", "#########"};
        String[] actual = solution.draw(n);

        Assert.assertArrayEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int n = 13;

        String[] expected = new String[]{"#############", "#           #", "# ######### #", "# #       # #", "# # ##### # #", "# # #   # # #", "# # # # # # #", "# # #   # # #", "# # ##### # #", "# #       # #", "# ######### #", "#           #", "#############"};
        String[] actual = solution.draw(n);

        Assert.assertArrayEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int n = 17;

        String[] expected = new String[]{"#################", "#               #", "# ############# #", "# #           # #", "# # ######### # #", "# # #       # # #", "# # # ##### # # #", "# # # #   # # # #", "# # # # # # # # #", "# # # #   # # # #", "# # # ##### # # #", "# # #       # # #", "# # ######### # #", "# #           # #", "# ############# #", "#               #", "#################"};
        String[] actual = solution.draw(n);

        Assert.assertArrayEquals(expected, actual);
    }

}
