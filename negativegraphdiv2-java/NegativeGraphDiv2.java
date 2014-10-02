import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

public class NegativeGraphDiv2 {
	
	public long findMin(int N, int[] s, int[] t, int[] weight, int charges) {
		ArrayList<ArrayList<ListNode>> list = new ArrayList<ArrayList<ListNode>>();
		for (int i = 0; i < N; i++) {
			list.add(new ArrayList<ListNode>());
		}
		for (int i = 0; i < s.length; i++) {;
			ListNode temp = new ListNode(t[i]-1, weight[i]);
			list.get(s[i]-1).add(temp);
		}
		long[][] d = new long[charges+1][N];
		for (int i = 0; i < d.length; i++) {
			Arrays.fill(d[i], Long.MAX_VALUE);
			d[i][0] = 0;
		}
		boolean updated = false;
		//for (int n = 0; n < N-1; n++) { // yes, NOT n!! at most n-1 edges on the shortest path
//		while(true) {
		for (int n = 0; n <= N + charges; n++) { // yes, NOT n!! at most n-1 edges on the shortest path
			updated = false;
			for (int c = 1; c <= charges; c++) {
				for (int i = 0; i < list.size(); i++) {
					for (int j = 0; j < list.get(i).size(); j++) {
//						if (d[c][list.get(i).get(j).node] > d[c][i] + list.get(i).get(j).weight) {
//							d[c][list.get(i).get(j).node] = d[c][i] + list.get(i).get(j).weight;
//							updated = true;
//						}
						if (c > 0 && d[c-1][list.get(i).get(j).node] > d[c][i] - list.get(i).get(j).weight) {
									 d[c-1][list.get(i).get(j).node] = d[c][i] - list.get(i).get(j).weight;
							updated = true;
						}
					}
				}
			}
			if (!updated) {
				break;
			}
		}
		long min = Long.MAX_VALUE;
		for (int i = 0; i < d.length; i++) {
			min = Math.min(min, d[i][N-1]);
		}
		return min;
	}
	
	public static class Node implements Comparable<Node> {
		public int totalcost;
		public int position;
		public int charges;
		public Node (int totalcost, int position, int charges) {
			this.totalcost = totalcost;
			this.position = position;
			this.charges = charges;
		}

		public int compareTo(Node n) {
			if (this.totalcost < n.totalcost) return -1;
			if (this.totalcost > n.totalcost) return 1;
			return 0;
		}

		public String toString() {
			return "[c: " + this.totalcost + ", p: " + this.position + ", ch: " + this.charges + "]";
		}
	}

	public static class ListNode {
		public int node;
		public int weight;
		public ListNode(int node, int weight) {
			this.node = node;
			this.weight = weight;
		}
		public String toString() {
			return "[n: " + this.node + ", w: " + this.weight + "]";
		}
	}

	public long findMin2(int N, int[] s, int[] t, int[] weight, int charges) {
		int min = Integer.MAX_VALUE;

		ArrayList<ArrayList<ListNode>> adjLists = new ArrayList<ArrayList<ListNode>>();
		for (int i = 0; i < N; i++) {
			adjLists.add(new ArrayList<ListNode>());
		}
		for (int i = 0; i < s.length; i++) {;
			ListNode temp = new ListNode(t[i]-1, weight[i]);
			adjLists.get(s[i]-1).add(temp);
		}

		PriorityQueue<Node> q = new PriorityQueue<Node>();
		q.add(new Node(0, 0, charges));
		int[][] visited = new int[N][charges+1];
		for (int i = 0; i < visited.length; i++) {
			Arrays.fill(visited[i], Integer.MAX_VALUE);
		}
		while (!q.isEmpty()) {
			Node top = q.poll();
			if (visited[top.position][top.charges] <= top.totalcost) {
				continue;
			}
			visited[top.position][top.charges] = top.totalcost;
			if (top.position == N-1) {
				min = Math.min(min, top.totalcost);
				//return min;
				//System.out.println(top);
			}
			for (int i = 0; i < adjLists.get(top.position).size(); i++) {
				if (top.charges > 0) {
					q.add(new Node(top.totalcost + adjLists.get(top.position).get(i).weight*-1, 
							adjLists.get(top.position).get(i).node, top.charges-1));
				}
				q.add(new Node(top.totalcost + adjLists.get(top.position).get(i).weight, 
						adjLists.get(top.position).get(i).node, top.charges));
			}
		}
		return min;
	}
}
