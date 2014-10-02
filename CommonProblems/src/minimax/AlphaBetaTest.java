package minimax;

import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

public class AlphaBetaTest {
	@Test
	public void test0() {
		Random rand = new Random(0);
		for (int j = 0; j < 10000; j++) {
			int branchingFactor = 1 + rand.nextInt(5);
			int seed = rand.nextInt();
			AlphaBeta recursive = new AlphaBeta(seed, 1000, branchingFactor, false);
			AlphaBeta iterative = new AlphaBeta(seed, 1000, branchingFactor, true);
			for (int i = 1; i < 10; i++) {
				Assert.assertTrue(recursive.calculate(i) == iterative.calculate(i));
			}
		}
	}
}
