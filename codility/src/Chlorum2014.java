import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.TreeMap;


public class Chlorum2014 {

	public static void main(String[] args) {
		Chlorum2014 c = new Chlorum2014();
		for (int i = 7; i <= 7; i++) {
			//System.out.println(i + " " + c.solution(i, new int[]{1,3,0,3,2,4,4}, new int[]{6,2,7,5,6,5,2}));
			System.out.println(i + " " + c.solution(i, new int[]{1,3,0,3,2,4,4}, new int[]{5,7,7,7,6,5,2}));
		}
		
	}
	
	public int solution(int K, int[] C, int[] D) {
		TreeMap<Integer, Integer> insideAttract = new TreeMap<>();
		TreeMap<Integer, Integer> outsideAttract = new TreeMap<>();
		int max = 0;
		for (int i = 0; i < D.length; i++) {
			outsideAttract.put(D[i], outsideAttract.containsKey(D[i]) ? outsideAttract.get(D[i]) + 1 : 1);
			max = Math.max(max, D[i]);
		}
		
		ArrayList<Integer> maxAttract = new ArrayList<>();
		for (int i = 0; i < D.length; i++) {
			if (D[i] == max) {
				maxAttract.add(i);
			}
		}

		HashMap<Integer, ArrayList<Integer>> g = new HashMap<>();
		for (int i = 0; i < C.length; i++) {
			int[] a = {i, C[i]};
			if (a[0] != a[1]) {
				for (int j = 0; j < a.length; j++) {
					if (!g.containsKey(a[j])) {
						g.put(a[j], new ArrayList<Integer>());
					}
					g.get(a[j]).add(a[(j + 1) % 2]);
				}
			}
		}
		
		int best = 0;
		for (int i = 0; i < maxAttract.size(); i++) {
			PriorityQueue<int[]> queue = new PriorityQueue<>(16, new IntComparator());
			HashSet<Integer> openSet = new HashSet<>();
			HashSet<Integer> closedSet = new HashSet<>();
			HashSet<Integer> visited = new HashSet<>();
			int[] top = {maxAttract.get(i), D[maxAttract.get(i)]};

			//System.out.println("  start: " + top[0]);
			queue.add(top);
			openSet.add(top[0]);
			
			int size = 0;
			
			while (!queue.isEmpty()) {
				top = queue.poll();
				openSet.remove(top[0]);
				if (visited.contains(top)) {
					continue;
				}
				int highestOutside = outsideAttract.lastKey();
				if (top[1] < highestOutside) {
					continue;
				}
				
				closedSet.add(top[0]);
				visited.add(top[0]);
				//System.out.println("visit: " + top[0]);
				
				incrementMap(insideAttract, D[top[0]], 1);
				decrementMap(outsideAttract, D[top[0]], 1);
				
				size++;
				best = Math.max(best, size);
				if (size == K) {
					break;
				}
				
				ArrayList<Integer> neigh = g.get(top[0]);
				for (int j = 0; j < neigh.size(); j++) {
					int next[] = {neigh.get(j), D[neigh.get(j)]};
					if (closedSet.contains(next[0])) {
						continue;
					}
					//if (next[1] >= highestOutside) {
					if (!openSet.contains(next[0])) {
						queue.add(next);
						openSet.add(next[0]);
					}
				}
			}
			
			for (int key: insideAttract.keySet()) {
				incrementMap(outsideAttract, key, insideAttract.get(key));
			}
			insideAttract.clear();
		}
		
        return best;
    }
	
	public static class IntComparator implements Comparator<int[]> {
		@Override
		public int compare(int[] o1, int[] o2) {
			return Integer.compare(o2[1], o1[1]);
		}
	} 
	
	public void incrementMap(Map<Integer, Integer> map, int key, int amount) {
		if (!map.containsKey(key)) {
			map.put(key, amount);
		} else {
			map.put(key, map.get(key) + amount);
		}
	}
	
	public void decrementMap(Map<Integer, Integer> map, int key, int amount) {
		if (map.get(key) == amount) {
			map.remove(key);
		} else {
			map.put(key, map.get(key) - amount);
		}
	}

}
