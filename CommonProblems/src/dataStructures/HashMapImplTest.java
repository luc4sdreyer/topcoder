package dataStructures;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;


public class HashMapImplTest {

	int limit = 10000;

	@Test
	public void test0() {
		HashMap<Integer, Integer> reference = new HashMap<>();
		HashMapImplFrame<Integer, Integer> subject = new HashMapImpl<>();
		Random rand = new Random(0);
		for (int i = 0; i < limit; i++) {
			if (Math.random() < 0.75) {
				int k = rand.nextInt(limit);
				int v = rand.nextInt(limit);
				subject.put(k, v);
				reference.put(k, v);
			} else {
				for (Integer k : reference.keySet()) {
					subject.remove(k);
					reference.remove(k);
					break;
				}
			}
			for (Integer k : reference.keySet()) {
				if (!reference.get(k).equals(subject.get(k))) {
					fail();
				}
			}
		}
		Assert.assertTrue(true);
	}

	@Test
	public void test1() {
		HashMap<Integer, Integer> reference = new HashMap<>();
		HashMapImplFrame<Integer, Integer> subject = new HashMapImpl<>();
		Random rand = new Random(1);
		for (int i = 0; i < limit; i++) {
			if (Math.random() < 0.75) {
				int k = rand.nextInt(limit);
				int v = rand.nextInt(limit);
				subject.put(k, v);
				reference.put(k, v);
			} else {
				for (Integer k : reference.keySet()) {
					subject.remove(k);
					reference.remove(k);
					break;
				}
			}
			for (Integer k : reference.keySet()) {
				if (!reference.get(k).equals(subject.get(k))) {
					fail();
				}
			}
		}
		Assert.assertTrue(true);
	}

	@Test
	public void test2() {
		HashMap<Integer, Integer> reference = new HashMap<>();
		HashMapImplFrame<Integer, Integer> subject = new HashMapImpl<>();
		Random rand = new Random(2);
		for (int i = 0; i < limit; i++) {
			if (Math.random() < 0.75) {
				int k = rand.nextInt(limit);
				int v = rand.nextInt(limit);
				subject.put(k, v);
				reference.put(k, v);
			} else {
				for (Integer k : reference.keySet()) {
					subject.remove(k);
					reference.remove(k);
					break;
				}
			}
			for (Integer k : reference.keySet()) {
				if (!reference.get(k).equals(subject.get(k))) {
					fail();
				}
			}
		}
		Assert.assertTrue(true);
	}

	@Test
	public void test3() {
		HashMap<Integer, Integer> reference = new HashMap<>();
		HashMapImplFrame<Integer, Integer> subject = new HashMapImpl<>();
		Random rand = new Random(3);
		for (int i = 0; i < limit; i++) {
			if (Math.random() < 0.75) {
				int k = rand.nextInt(limit);
				int v = rand.nextInt(limit);
				subject.put(k, v);
				reference.put(k, v);
			} else {
				for (Integer k : reference.keySet()) {
					subject.remove(k);
					reference.remove(k);
					break;
				}
			}
			for (Integer k : reference.keySet()) {
				if (!reference.get(k).equals(subject.get(k))) {
					fail();
				}
			}
		}
		Assert.assertTrue(true);
	}
	
	int speedtestLimit = 1000000;
	@Test //(timeout = 1000)
	public void test4() {
		long time = 0;
		

		time = System.nanoTime();
		HashMap<Integer, Integer> reference = new HashMap<>();
		for (int i = 0; i < speedtestLimit; i++) {
			reference.put(i, i);
		}
		long a = System.nanoTime() - time;

		// 2 warm-up loops
		for (int j = 0; j < 3; j++) {
			time = System.nanoTime();
			HashMapImplFrame<Integer, Integer> subject = new HashMapImpl<>();
			for (int i = 0; i < speedtestLimit; i++) {
				subject.put(i, i);
			}			
		}
		long b = System.nanoTime() - time;
		
		System.out.println("put: My  impl: " + b/1000000);
		System.out.println("put: Ref impl: " + a/1000000);
		System.out.println();
		Assert.assertTrue(b < 3*a);
	}
	
	@Test //(timeout = 1000)
	public void test5() {
		long time = 0;
		
		
		HashMap<Integer, Integer> reference = new HashMap<>();
		for (int i = 0; i < speedtestLimit; i++) {
			reference.put(i, i);
		}
		time = System.nanoTime();
		for (int i = 0; i < speedtestLimit; i++) {
			reference.get(i);
		}
		long a = System.nanoTime() - time;

		HashMapImplFrame<Integer, Integer> subject = new HashMapImpl<>();
		for (int i = 0; i < speedtestLimit; i++) {
			subject.put(i, i);
		}
		time = System.nanoTime();
		for (int i = 0; i < speedtestLimit; i++) {
			subject.get(i);
		}
		long b = System.nanoTime() - time;
		
		System.out.println("get: My  impl: " + b/1000000);
		System.out.println("get: Ref impl: " + a/1000000);
		System.out.println();
		Assert.assertTrue(b < 3*a);
	}
	
	@Test //(timeout = 1000)
	public void test6() {
		long time = 0;
		

		HashMap<Integer, Integer> reference = new HashMap<>();
		for (int i = 0; i < speedtestLimit; i++) {
			reference.put(i, i);
		}
		time = System.nanoTime();
		for (int i = 0; i < speedtestLimit; i++) {
			reference.remove(i);
		}
		long a = System.nanoTime() - time;
		

		HashMapImplFrame<Integer, Integer> subject = new HashMapImpl<>();
		for (int i = 0; i < speedtestLimit; i++) {
			subject.put(i, i);
		}
		time = System.nanoTime();
		for (int i = 0; i < speedtestLimit; i++) {
			subject.remove(i);
		}
		long b = System.nanoTime() - time;
		
		
		System.out.println("remove: My  impl: " + b/1000000);
		System.out.println("remove: Ref impl: " + a/1000000);
		System.out.println();
		Assert.assertTrue(b < 3*a);
	}
}
