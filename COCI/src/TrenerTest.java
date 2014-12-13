
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import org.junit.Assert;
import org.junit.Test;
public class TrenerTest {

	@Test(timeout = 2000)
	public void testCase0() {
		String input = "6\nmichael\njordan\nlebron\njames\nkobe\nbryant";
		String output = "PREDAJA";

		Assert.assertEquals(output, Main2013.trener(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8))));
	}

	@Test(timeout = 2000)
	public void testCase1() {
		String input = "18 \nbabic \nkeksic \nboric \nbukic \nsarmic \nbalic \nkruzic \nhrenovkic \nbeslic \nboksic \nkrafnic \npecivic \nklavirkovic \nkukumaric \nsunkic \nkolacic \nkovacic \nprijestolonasljednikovic \n";
		String output = "bk";

		Assert.assertEquals(output, Main2013.trener(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8))));
	}

	@Test(timeout = 2000)
	public void testCase2() {
		String input = "10\nba\nbb\nbc\nbd\nbe\naa\nab\nac\nad\nae\n";
		String output = "ab";

		Assert.assertEquals(output, Main2013.trener(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8))));
	}
}
