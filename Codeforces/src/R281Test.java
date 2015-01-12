import static org.junit.Assert.*;

import org.junit.Test;


public class R281Test {

	@Test
	public void test() {
		int[] p = new int[20000];
		for (int i = 0; i < p.length; i++) {
			if (i % 2 == 0) {
				p[i] = 2000000000;
			} else {
				p[i] = -1000000000;
			}
		}
		assertEquals("first", R281.wrestlingF(p.length, p));
	}

	@Test
	public void test2() {
		int[] p = {1, 2, -3, -4, 3};
		assertEquals("second", R281.wrestlingF(p.length, p));
	}

	@Test
	public void test3() {
		int[] p = {-1, -2, 3};
		assertEquals("first", R281.wrestlingF(p.length, p));
	}

	@Test
	public void test8() {
		int[] p = {1, 2, -3};
		assertEquals("second", R281.wrestlingF(p.length, p));
	}

	@Test
	public void test6() {
		int[] p = {-1, -2, 2, 1};
		assertEquals("first", R281.wrestlingF(p.length, p));
	}

	@Test
	public void test7() {
		int[] p = {-2, -1, 1, 2};
		assertEquals("second", R281.wrestlingF(p.length, p));
	}

	@Test
	public void test4() {
		int[] p = {4, -4};
		assertEquals("second", R281.wrestlingF(p.length, p));
	}

	@Test
	public void test5() {
		int[] p = {-4, 4};
		assertEquals("first", R281.wrestlingF(p.length, p));
	}

}
