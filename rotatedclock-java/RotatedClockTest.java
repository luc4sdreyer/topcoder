import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RotatedClockTest {

    protected RotatedClock solution;

    @Before
    public void setUp() {
        solution = new RotatedClock();
    }

    @Test(timeout = 200000)
    public void testCase0() {
        int hourHand = 70;
        int minuteHand = 300;

        String expected = "08:20";
        String actual = solution.getEarliest(hourHand, minuteHand);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int hourHand = 90;
        int minuteHand = 120;

        String expected = "11:00";
        String actual = solution.getEarliest(hourHand, minuteHand);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int hourHand = 240;
        int minuteHand = 36;

        String expected = "";
        String actual = solution.getEarliest(hourHand, minuteHand);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 200000)
    public void testCase3() {
        int hourHand = 19;
        int minuteHand = 19;

        String expected = "";
        String actual = solution.getEarliest(hourHand, minuteHand);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        int hourHand = 1;
        int minuteHand = 12;

        String expected = "00:02";
        String actual = solution.getEarliest(hourHand, minuteHand);

        Assert.assertEquals(expected, actual);
    }

}
