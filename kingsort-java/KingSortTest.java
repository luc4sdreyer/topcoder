import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class KingSortTest {

    protected KingSort solution;

    @Before
    public void setUp() {
        solution = new KingSort();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        String[] kings = new String[]{"Louis IX", "Louis VIII"};

        String[] expected = new String[]{"Louis VIII", "Louis IX"};
        String[] actual = solution.getSortedList(kings);

        Assert.assertArrayEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        String[] kings = new String[]{"Louis IX", "Philippe II"};

        String[] expected = new String[]{"Louis IX", "Philippe II"};
        String[] actual = solution.getSortedList(kings);

        Assert.assertArrayEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        String[] kings = new String[]{"Richard III", "Richard I", "Richard II"};

        String[] expected = new String[]{"Richard I", "Richard II", "Richard III"};
        String[] actual = solution.getSortedList(kings);

        Assert.assertArrayEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        String[] kings = new String[]{"John X", "John I", "John L", "John V"};

        String[] expected = new String[]{"John I", "John V", "John X", "John L"};
        String[] actual = solution.getSortedList(kings);

        Assert.assertArrayEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        String[] kings = new String[]{"Philippe VI", "Jean II", "Charles V", "Charles VI", "Charles VII", "Louis XI"};

        String[] expected = new String[]{"Charles V", "Charles VI", "Charles VII", "Jean II", "Louis XI", "Philippe VI"};
        String[] actual = solution.getSortedList(kings);

        Assert.assertArrayEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase5() {
        String[] kings = new String[]{"Philippe II", "Philip II"};

        String[] expected = new String[]{"Philip II", "Philippe II"};
        String[] actual = solution.getSortedList(kings);

        Assert.assertArrayEquals(expected, actual);
    }

}
