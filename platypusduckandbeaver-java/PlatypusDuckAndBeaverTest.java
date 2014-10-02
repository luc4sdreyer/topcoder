import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PlatypusDuckAndBeaverTest {

    protected PlatypusDuckAndBeaver solution;

    @Before
    public void setUp() {
        solution = new PlatypusDuckAndBeaver();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        int webbedFeet = 4;
        int duckBills = 1;
        int beaverTails = 1;

        int expected = 1;
        int actual = solution.minimumAnimals(webbedFeet, duckBills, beaverTails);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int webbedFeet = 0;
        int duckBills = 0;
        int beaverTails = 0;

        int expected = 0;
        int actual = solution.minimumAnimals(webbedFeet, duckBills, beaverTails);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int webbedFeet = 10;
        int duckBills = 2;
        int beaverTails = 2;

        int expected = 3;
        int actual = solution.minimumAnimals(webbedFeet, duckBills, beaverTails);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int webbedFeet = 60;
        int duckBills = 10;
        int beaverTails = 10;

        int expected = 20;
        int actual = solution.minimumAnimals(webbedFeet, duckBills, beaverTails);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        int webbedFeet = 1000;
        int duckBills = 200;
        int beaverTails = 200;

        int expected = 300;
        int actual = solution.minimumAnimals(webbedFeet, duckBills, beaverTails);

        Assert.assertEquals(expected, actual);
    }

}
