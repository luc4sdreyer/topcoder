import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class WhichDayTest {

    protected WhichDay solution;

    @Before
    public void setUp() {
        solution = new WhichDay();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        String[] notOnThisDay = new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};

        String expected = "Saturday";
        String actual = solution.getDay(notOnThisDay);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        String[] notOnThisDay = new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Friday", "Thursday"};

        String expected = "Saturday";
        String actual = solution.getDay(notOnThisDay);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        String[] notOnThisDay = new String[]{"Sunday", "Monday", "Tuesday", "Thursday", "Friday", "Saturday"};

        String expected = "Wednesday";
        String actual = solution.getDay(notOnThisDay);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        String[] notOnThisDay = new String[]{"Sunday", "Friday", "Tuesday", "Wednesday", "Monday", "Saturday"};

        String expected = "Thursday";
        String actual = solution.getDay(notOnThisDay);

        Assert.assertEquals(expected, actual);
    }

}
