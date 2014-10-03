import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SRMRoomAssignmentPhaseTest {

    protected SRMRoomAssignmentPhase solution;

    @Before
    public void setUp() {
        solution = new SRMRoomAssignmentPhase();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        int[] ratings = new int[]{491, 981, 1199, 763, 994, 879, 888};
        int K = 3;

        int expected = 2;
        int actual = solution.countCompetitors(ratings, K);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int[] ratings = new int[]{1024, 1000, 600};
        int K = 1;

        int expected = 0;
        int actual = solution.countCompetitors(ratings, K);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int[] ratings = new int[]{505, 679, 900, 1022};
        int K = 2;

        int expected = 1;
        int actual = solution.countCompetitors(ratings, K);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int[] ratings = new int[]{716, 58, 1000, 1004, 912, 822, 453, 1100, 558};
        int K = 3;

        int expected = 1;
        int actual = solution.countCompetitors(ratings, K);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        int[] ratings = new int[]{422, 623, 1023, 941, 882, 776, 852, 495, 803, 622, 618, 532, 751, 500};
        int K = 4;

        int expected = 3;
        int actual = solution.countCompetitors(ratings, K);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase5() {
        int[] ratings = new int[]{1197, 1198, 1196, 1195, 1199};
        int K = 1;

        int expected = 2;
        int actual = solution.countCompetitors(ratings, K);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase6() {
        int[] ratings = new int[]{21, 14, 16, 20, 24, 26, 5, 7};
        int K = 3;

        int expected = 1;
        int actual = solution.countCompetitors(ratings, K);

        Assert.assertEquals(expected, actual);
    }

}
