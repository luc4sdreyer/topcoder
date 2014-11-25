import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SequenceOfCommandsTest {

    protected SequenceOfCommands solution;

    @Before
    public void setUp() {
        solution = new SequenceOfCommands();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        String[] commands = new String[]{"L"};

        String expected = "bounded";
        String actual = solution.whatHappens(commands);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        String[] commands = new String[]{"SRSL"};

        String expected = "unbounded";
        String actual = solution.whatHappens(commands);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        String[] commands = new String[]{"SSSS", "R"};

        String expected = "bounded";
        String actual = solution.whatHappens(commands);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        String[] commands = new String[]{"SRSL", "LLSSSSSSL", "SSSSSS", "L"};

        String expected = "unbounded";
        String actual = solution.whatHappens(commands);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        Assert.assertEquals("unbounded", solution.whatHappens(new String[]{"SSSS", "RRRR"}));
        Assert.assertEquals("bounded", solution.whatHappens(new String[]{"SSSS", "RRR"}));
        Assert.assertEquals("bounded", solution.whatHappens(new String[]{"SSSS", "RR"}));
        Assert.assertEquals("bounded", solution.whatHappens(new String[]{"SSSS", "R"}));
        Assert.assertEquals("bounded", solution.whatHappens(new String[]{"SSSS", "LL"}));
        Assert.assertEquals("bounded", solution.whatHappens(new String[]{"SSSS", "L"}));
    }

}
