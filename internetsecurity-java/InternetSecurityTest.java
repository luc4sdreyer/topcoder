import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class InternetSecurityTest {

    protected InternetSecurity solution;

    @Before
    public void setUp() {
        solution = new InternetSecurity();
    }

    @Test(timeout = 200000)
    public void testCase0() {
        String[] address = new String[]{"www.topcoder.com", "www.sindicate_of_evil.com", "www.happy_citizens.com"};
        String[] keyword = new String[]{"hack encryption decryption internet algorithm", "signal interference evil snake poison algorithm", "flower baloon topcoder blue sky sea"};
        String[] dangerous = new String[]{"hack", "encryption", "decryption", "interference", "signal", "internet"};
        int threshold = 3;

        String[] expected = new String[]{"www.topcoder.com", "www.sindicate_of_evil.com"};
        String[] actual = solution.determineWebsite(address, keyword, dangerous, threshold);

        Assert.assertArrayEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase1() {
        String[] address = new String[]{"brokenlink", "flowerpower.net", "purchasedomain.com"};
        String[] keyword = new String[]{"broken", "rose tulips", "cheap free domain biggest greatest"};
        String[] dangerous = new String[]{"biggest", "enemy", "hideout"};
        int threshold = 2;

        String[] expected = new String[]{};
        String[] actual = solution.determineWebsite(address, keyword, dangerous, threshold);

        Assert.assertArrayEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase2() {
        String[] address = new String[]{"a..a.ab.", "...aa.b"};
        String[] keyword = new String[]{"a bc def", "def ghij klmno"};
        String[] dangerous = new String[]{"a", "b", "c", "d", "e"};
        int threshold = 1;

        String[] expected = new String[]{"a..a.ab.", "...aa.b"};
        String[] actual = solution.determineWebsite(address, keyword, dangerous, threshold);

        Assert.assertArrayEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase3() {
        String[] address = new String[]{"www.tsa.gov"};
        String[] keyword = new String[]{"information assurance signal intelligence research"};
        String[] dangerous = new String[]{"signal", "assurance", "penguin"};
        int threshold = 2;

        String[] expected = new String[]{"www.tsa.gov"};
        String[] actual = solution.determineWebsite(address, keyword, dangerous, threshold);

        Assert.assertArrayEquals(expected, actual);
    }

    @Test(timeout = 2000)
    public void testCase4() {
        String[] address = new String[]{""};
        String[] keyword = new String[]{""};
        String[] dangerous = new String[]{""};
        int threshold = 2;

        String[] expected = new String[0];
        String[] actual = solution.determineWebsite(address, keyword, dangerous, threshold);

        Assert.assertArrayEquals(expected, actual);
    }

}
