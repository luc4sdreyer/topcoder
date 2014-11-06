import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ColorfulCardsTest {

    protected ColorfulCards solution;

    @Before
    public void setUp() {
        solution = new ColorfulCards();
    }

    @Test(timeout = 200000)
    public void testCase0() {
        int N = 5;
        String colors = "RRR";

        int[] expected = new int[]{2, 3, 5};
        int[] actual = solution.theCards(N, colors);

        Assert.assertArrayEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int N = 7;
        String colors = "BBB";

        int[] expected = new int[]{1, 4, 6};
        int[] actual = solution.theCards(N, colors);

        Assert.assertArrayEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int N = 6;
        String colors = "RBR";

        int[] expected = new int[]{-1, 4, 5};
        int[] actual = solution.theCards(N, colors);

        Assert.assertArrayEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int N = 58;
        String colors = "RBRRBRBBRBRRBBRRBBBRRBBBRR";

        int[] expected = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, 17, 18, 19, 23, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 47, 53};
        int[] actual = solution.theCards(N, colors);

        Assert.assertArrayEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        int N = 495;
        String colors = "RBRRBRBBRBRRBBRRBBBRRBBBRR";

        int[] expected = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
        int[] actual = solution.theCards(N, colors);

        Assert.assertArrayEquals(expected, actual);
    }

}
