package dataStructures;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Random;
import java.util.Stack;

import org.junit.Test;

import dataStructures.HeavyLightDecomposition.HLD;

public class HeavyLightDecompositionTest {
	@Test
	public void testQueries() {
		Random ra = new Random(0);
		for (int size = 2; size <= 20; size++) {
			for (int tests = 0; tests < 100; tests++) {
				ArrayList<ArrayList<Integer>> g = new ArrayList<>();
				ArrayList<ArrayList<Integer>> cost = new ArrayList<>();
				for (int i = 0; i < size; i++) {
					g.add(new ArrayList<Integer>());
					cost.add(new ArrayList<Integer>());
				}
				for (int i = 1; i < size; i++) {
					int oldNode = ra.nextInt(i);
					int newNode = i;
					int c = ra.nextInt(100);
					g.get(oldNode).add(newNode);
					g.get(newNode).add(oldNode);
					cost.get(oldNode).add(c);
					cost.get(newNode).add(c);
				}
				
				HLD hld = new HLD(g, cost, -1);
				
				for (int i = 0; i < size; i++) {
					boolean query = ra.nextBoolean();
					if (query) {
						for (int start = 0; start < size; start++) {
							for (int end = 0; end < size; end++) {
								Stack<int[]> s = new Stack<>();
								int[] top = {start, 0};
								s.add(top);
								BitSet visited = new BitSet();
								int totalCost = -1;
								while (!s.isEmpty()) {
									top = s.pop();
									if (visited.get(top[0])) {
										continue;
									}
									if (top[0] == end) {
										totalCost = top[1];
										break;
									}
									visited.set(top[0]);
									ArrayList<Integer> children = g.get(top[0]); 
									for (int j = 0; j < children.size(); j++) {
										s.add(new int[]{children.get(j), top[1] + cost.get(top[0]).get(j)});
									}
								}
								if (hld.query(start, end) != totalCost) {
									hld.query(start, end);
								}
								assertEquals(hld.query(start, end), totalCost);
							}
						}
					} else {
						// update random cost
						int a = ra.nextInt(size);
						int bIdx = ra.nextInt(g.get(a).size());
						int c = ra.nextInt(100);
						cost.get(a).set(bIdx, c);
						boolean done = false;
						int b = g.get(a).get(bIdx);
						for (int j = 0; j < g.get(b).size(); j++) {
							if (g.get(b).get(j) == a) {
								cost.get(b).set(j, c);
								done = true;
								break;
							}
						}
						assertTrue(done);
						
						hld.set_tree(a, b, c);
					}
				}
			}
		}
	}

	@Test
	public void testPerformance() {
		Random ra = new Random(0);
		int size = 10000;
		for (int tests = 0; tests < 20; tests++) {
			ArrayList<ArrayList<Integer>> g = new ArrayList<>();
			ArrayList<ArrayList<Integer>> cost = new ArrayList<>();
			for (int i = 0; i < size; i++) {
				g.add(new ArrayList<Integer>());
				cost.add(new ArrayList<Integer>());
			}
			for (int i = 1; i < size; i++) {
				int oldNode = ra.nextInt(i);
				int newNode = i;
				int c = ra.nextInt(100);
				g.get(oldNode).add(newNode);
				g.get(newNode).add(oldNode);
				cost.get(oldNode).add(c);
				cost.get(newNode).add(c);
			}
			
			HLD hld = new HLD(g, cost, -1);
			for (int i = 0; i < size*10; i++) {
				boolean query = ra.nextBoolean();
				if (query) {
					int start = ra.nextInt(size);
					int end = ra.nextInt(size);
					long res = hld.query(start, end);
					assertNotEquals(res, -1);
				} else {
					// update random cost
					int a = ra.nextInt(size);
					int bIdx = ra.nextInt(g.get(a).size());
					int c = ra.nextInt(100);
					int b = g.get(a).get(bIdx);
					hld.set_tree(a, b, c);
				}
			}
		}
	}
	
}
