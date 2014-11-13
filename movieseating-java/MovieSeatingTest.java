import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MovieSeatingTest {

    protected MovieSeating solution;

    @Before
    public void setUp() {
        solution = new MovieSeating();
    }

    @Test(timeout = 200000)
    public void testCase0() {
        int numFriends = 2;
        String[] hall = new String[]{".#..", ".##.", "...."};

        long expected = 34L;
        long actual = solution.getSeatings(numFriends, hall);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int numFriends = 2;
        String[] hall = new String[]{"..#", ".##", "..."};

        long expected = 16L;
        long actual = solution.getSeatings(numFriends, hall);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int numFriends = 5;
        String[] hall = new String[]{"..####..", ".###.##.", ".######.", "#.#.#.#."};

        long expected = 0L;
        long actual = solution.getSeatings(numFriends, hall);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int numFriends = 8;
        String[] hall = new String[]{"........"};

        long expected = 40320L;
        long actual = solution.getSeatings(numFriends, hall);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 200000)
    public void testCase5() {
        int numFriends = 1;
        String[] hall = new String[]{".#..", ".##.", "...."};

        long expected = 9L;
        long actual = solution.getSeatings(numFriends, hall);

        Assert.assertEquals(expected, actual);
    }
}
