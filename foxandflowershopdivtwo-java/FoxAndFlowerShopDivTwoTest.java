import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FoxAndFlowerShopDivTwoTest {

    protected FoxAndFlowerShopDivTwo solution;

    @Before
    public void setUp() {
        solution = new FoxAndFlowerShopDivTwo();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        String[] flowers = new String[]{"F.F", ".F.", ".F."};
        int r = 1;
        int c = 1;

        int expected = 2;
        int actual = solution.theMaxFlowers(flowers, r, c);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        String[] flowers = new String[]{"F..", "...", "..."};
        int r = 0;
        int c = 0;

        int expected = 0;
        int actual = solution.theMaxFlowers(flowers, r, c);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        String[] flowers = new String[]{".FF.F.F", "FF...F.", "..FF.FF"};
        int r = 1;
        int c = 2;

        int expected = 6;
        int actual = solution.theMaxFlowers(flowers, r, c);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        String[] flowers = new String[]{"F", ".", "F", "F", "F", ".", "F", "F"};
        int r = 4;
        int c = 0;

        int expected = 3;
        int actual = solution.theMaxFlowers(flowers, r, c);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        String[] flowers = new String[]{".FFF..F...", "FFF...FF.F", "..F.F.F.FF", "FF..F.FFF.", "..FFFFF...", "F....FF...", ".FF.FF..FF", "..F.F.FFF.", ".FF..F.F.F", "F.FFF.FF.F"};
        int r = 4;
        int c = 3;

        int expected = 32;
        int actual = solution.theMaxFlowers(flowers, r, c);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase5() {
        String[] flowers = new String[]{".FFF..F...", "FFF...FF.F", "..F.F.F.FF", "FF..F.FFF.", "..FFFFF...", "F....FF...", ".FF.FF..FF", "..F.F.FFF.", ".FF..F.F.F", "F.FFF.FF.F"};
        int r = 2;
        int c = 1;

        int expected = 5;
        int actual = solution.theMaxFlowers(flowers, r, c);

        Assert.assertEquals(expected, actual);
    }
    
    

}
