import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import javax.print.attribute.HashAttributeSet;

public class FoxMeeting {
	
	public static class Node implements Comparable<Node> {
		int pos = 0;
		int cost = 0;
		int id = 0;
		public Node(int pos, int cost, int id) {
			super();
			this.pos = pos;
			this.cost = cost;
			this.id = id;
		}

		@Override
		public int compareTo(Node o) {
			Node next = (Node)o;
			return Integer.compare(this.cost, next.cost);
		}
	}

	public int maxDistance(int[] A, int[] B, int[] L, int[] foxes) {
		
		HashMap<Integer, ArrayList<int[]>> map = new HashMap<>();
		int n = 0;
		for (int i = 0; i < A.length; i++) {
			if (!map.containsKey(A[i])) {
				map.put(A[i], new ArrayList<>());
			}
			map.get(A[i]).add(new int[]{B[i], L[i]});
			
			if (!map.containsKey(B[i])) {
				map.put(B[i], new ArrayList<>());
			}
			map.get(B[i]).add(new int[]{A[i], L[i]});
			
			n = Math.max(n, Math.max(A[i], B[i]));
		}
		
		int max = Integer.MAX_VALUE;
		
		for (int meet = 1; meet <= n; meet++) {
			PriorityQueue<Node> q = new PriorityQueue<Node>();
			
			for (int i = 0; i < foxes.length; i++) {
				q.add(new Node(foxes[i], 0, i));
			}
			
			boolean[] home = new boolean[51];
			boolean[] finishedPerID = new boolean[51];
			boolean[][] visited = new boolean[51][51];
			int[] minPerId = new int[51];
			
			while (q.size() != 0) {
				Node top = q.poll();
				
				if (visited[top.id][top.pos]) {
					continue;
				}
				if (finishedPerID[top.id]) {
					continue;
				}
				visited[top.id][top.pos] = true;
				
				if (top.pos == meet) {
					home[top.pos] = true;
					finishedPerID[top.id] = true;
					minPerId[top.id] = top.cost;
					continue;
				}
				
				for (int[] dest: map.get(top.pos)) {
					int nextPos = dest[0];
					if (home[nextPos]) {
						home[top.pos] = true;
						finishedPerID[top.id] = true;
						minPerId[top.id] = top.cost;
						break;
					}
					q.add(new Node(nextPos, top.cost + dest[1], top.id));
				}
			}
			
			int maxDist = 0;
			for (int i = 0; i < minPerId.length; i++) {
				maxDist = Math.max(maxDist, minPerId[i]);
			}
			
			max = Math.min(max, maxDist);
		}
		
		return max;
	}

}
