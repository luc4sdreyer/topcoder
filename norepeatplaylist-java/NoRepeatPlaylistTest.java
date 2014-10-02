import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class NoRepeatPlaylistTest {

    protected NoRepeatPlaylist solution;

    @Before
    public void setUp() {
        solution = new NoRepeatPlaylist();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        int N = 1;
        int M = 0;
        int P = 3;

        int expected = 1;
        int actual = solution.numPlaylists(N, M, P);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int N = 1;
        int M = 1;
        int P = 3;

        int expected = 0;
        int actual = solution.numPlaylists(N, M, P);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int N = 2;
        int M = 0;
        int P = 3;

        int expected = 6;
        int actual = solution.numPlaylists(N, M, P);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int N = 4;
        int M = 0;
        int P = 4;

        int expected = 24;
        int actual = solution.numPlaylists(N, M, P);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        int N = 2;
        int M = 1;
        int P = 4;

        int expected = 2;
        int actual = solution.numPlaylists(N, M, P);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase5() {
        int N = 50;
        int M = 5;
        int P = 100;

        int expected = 222288991;
        int actual = solution.numPlaylists(N, M, P);

        Assert.assertEquals(expected, actual);
    }

}
