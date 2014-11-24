import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MonstersValley2Test {

    protected MonstersValley2 solution;

    @Before
    public void setUp() {
        solution = new MonstersValley2();
    }

    @Test(timeout = 2000)
    public void testCase0() {
        int[] dread = new int[]{8, 5, 10};
        int[] price = new int[]{1, 1, 2};

        int expected = 2;
        int actual = solution.minimumPrice(dread, price);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        int[] dread = new int[]{1, 2, 4, 1000000000};
        int[] price = new int[]{1, 1, 1, 2};

        int expected = 5;
        int actual = solution.minimumPrice(dread, price);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        int[] dread = new int[]{200, 107, 105, 206, 307, 400};
        int[] price = new int[]{1, 2, 1, 1, 1, 2};

        int expected = 2;
        int actual = solution.minimumPrice(dread, price);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        int[] dread = new int[]{5216, 12512, 613, 1256, 66, 17202, 30000, 23512, 2125, 33333};
        int[] price = new int[]{2, 2, 1, 1, 1, 1, 2, 1, 2, 1};

        int expected = 5;
        int actual = solution.minimumPrice(dread, price);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        int[] dread = new int[20];
        for (int i = 0; i < dread.length; i++) {
        	dread[i] = 2000000000;
		}
        int[] price = new int[20];
        for (int i = 0; i < price.length; i++) {
        	price[i] = 2;
		}

        int expected = 2;
        int actual = solution.minimumPrice(dread, price);

        Assert.assertEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase5() {
        int[] dread = new int[20];
        dread[0] = 1;
        dread[1] = 2;
        for (int i = 2; i < dread.length; i++) {
        	dread[i] = 1 << i;
		}
        int[] price = new int[20];
        for (int i = 0; i < price.length; i++) {
        	price[i] = 2;
		}

        int expected = 40;
        int actual = solution.minimumPrice(dread, price);

        Assert.assertEquals(expected, actual);
    }

}
