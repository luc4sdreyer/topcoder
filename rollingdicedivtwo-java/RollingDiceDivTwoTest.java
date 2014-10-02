import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RollingDiceDivTwoTest {

    protected RollingDiceDivTwo solution;

    @Before
    public void setUp() {
        solution = new RollingDiceDivTwo();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        String[] rolls = new String[]{"137", "364", "115", "724"};

        int expected = 14;
        int actual = solution.minimumFaces(rolls);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        String[] rolls = new String[]{"1112", "1111", "1211", "1111"};

        int expected = 5;
        int actual = solution.minimumFaces(rolls);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        String[] rolls = new String[]{"24412", "56316", "66666", "45625"};

        int expected = 30;
        int actual = solution.minimumFaces(rolls);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        String[] rolls = new String[]{"931", "821", "156", "512", "129", "358", "555"};

        int expected = 19;
        int actual = solution.minimumFaces(rolls);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        String[] rolls = new String[]{"3", "7", "4", "2", "4"};

        int expected = 7;
        int actual = solution.minimumFaces(rolls);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase5() {
        String[] rolls = new String[]{"281868247265686571829977999522", "611464285871136563343229916655", "716739845311113736768779647392", "779122814312329463718383927626", "571573431548647653632439431183", "547362375338962625957869719518", "539263489892486347713288936885", "417131347396232733384379841536"};

        int expected = 176;
        int actual = solution.minimumFaces(rolls);

        Assert.assertEquals(expected, actual);
    }

}
