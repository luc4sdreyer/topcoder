import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SkewedPerspectivesTest {

    protected SkewedPerspectives solution;

    @Before
    public void setUp() {
        solution = new SkewedPerspectives();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        int[] cubes = new int[]{1, 1, 1};
        int B = 1;
        int w = 2;
        String[] views = new String[]{"012", "012bb", "bb0", "21bb", "21b", "1bb20"};

        String[] expected = new String[]{"valid", "valid", "valid", "valid", "valid", "valid"};
        String[] actual = solution.areTheyPossible(cubes, B, w, views);

        Assert.assertArrayEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int[] cubes = new int[]{0, 1, 0};
        int B = 3;
        int w = 2;
        String[] views = new String[]{"b", "bb", "bbb", "bbbb", "bbbbb", "bbbbbb", "1", "1b", "1bb", "1bbb", "1bbbb", "1bbbbb", "1bbbbbb", "b1", "b1b", "b1bb", "b1bbb", "b1bbbb", "b1bbbbb", "bb1", "bb1b", "bb1bb", "bb1bbb", "bb1bbbb", "bbb1", "bbb1b", "bbb1bb", "bbb1bbb", "bbbb1", "bbbb1b", "bbbb1bb", "bbbbb1", "bbbbb1b", "bbbbbb1"};

        String[] expected = new String[]{"invalid", "valid", "valid", "valid", "valid", "valid", "valid", "valid", "valid", "valid", "valid", "valid", "valid", "invalid", "invalid", "invalid", "invalid", "invalid", "invalid", "valid", "valid", "valid", "invalid", "valid", "invalid", "invalid", "invalid", "invalid", "valid", "invalid", "valid", "invalid", "invalid", "valid"};
        String[] actual = solution.areTheyPossible(cubes, B, w, views);

        Assert.assertArrayEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int[] cubes = new int[]{100, 0, 0};
        int B = 20;
        int w = 3;
        String[] views = new String[]{"00000000000000000000000000000000000000000000000000", "00000000000000000000000000000000000b00000000000000", "0000000000000000000000000000000000000000000000000b", "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb0000000000000"};

        String[] expected = new String[]{"valid", "valid", "valid", "valid"};
        String[] actual = solution.areTheyPossible(cubes, B, w, views);

        Assert.assertArrayEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int[] cubes = new int[]{5, 6, 3};
        int B = 0;
        int w = 1;
        String[] views = new String[]{"00000111111222", "0000111111222", "0000011111222", "000001111111222", "0000011111122", "b"};

        String[] expected = new String[]{"valid", "valid", "valid", "invalid", "valid", "invalid"};
        String[] actual = solution.areTheyPossible(cubes, B, w, views);

        Assert.assertArrayEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        int[] cubes = new int[]{100, 100, 100};
        int B = 100;
        int w = 50;
        String[] views = new String[]{"2", "bb1b012012012012012012012", "bb1b012012012012012012012b", "bb1b012012012b012b012b012012b", "bb1b0b1b2b0b1b2b0b1b2bb012b012b012012b", "b1b0b1b2b0b1b2b0b1b2bb012b012b012012b", "bbb1b012012012012012012012"};

        String[] expected = new String[]{"valid", "valid", "valid", "valid", "valid", "invalid", "valid"};
        String[] actual = solution.areTheyPossible(cubes, B, w, views);

        Assert.assertArrayEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase5() {
        int[] cubes = new int[]{0, 0, 0};
        int B = 0;
        int w = 1;
        String[] views = new String[]{"0", "bb"};

        String[] expected = new String[]{"invalid", "invalid"};
        String[] actual = solution.areTheyPossible(cubes, B, w, views);

        Assert.assertArrayEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase6() {
        int[] cubes = new int[]{3, 0, 0};
        int B = 4;
        int w = 3;
        String[] views = new String[]{"00b0b", "bbbbbbbb", "000b"};

        String[] expected = new String[]{"invalid", "valid", "valid"};
        String[] actual = solution.areTheyPossible(cubes, B, w, views);

        Assert.assertArrayEquals(expected, actual);
    }

}
