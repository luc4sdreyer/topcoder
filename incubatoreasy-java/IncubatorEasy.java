import java.util.ArrayList;
import java.util.Stack;

public class IncubatorEasy {

	public int maxMagicalGirls2(String[] love) {
		Node[] nodes = new Node[love.length];
		for (int i = 0; i < love.length; i++) {
			nodes[i] = new Node(i);
		}
		for (int i = 0; i < love.length; i++) {
			for (int j = 0; j < love[0].length(); j++) {
				if (love[i].charAt(j) == 'Y') {
					nodes[i].outgoing.add(nodes[j]);
					nodes[j].incoming.add(nodes[i]);
				}
			}
		}
		
		boolean[] visited = new boolean[nodes.length];
		int group = 0;
		for (int i = 0; i < nodes.length; i++) {
			Stack<Node> s = new Stack<Node>();
			s.push(nodes[i]);
			while (!s.isEmpty()) {
				Node top = s.pop();
				if (visited[top.id]) {
					continue;
				}
				top.group = group;
				visited[top.id] = true;
				for (Node node : top.outgoing) {
					s.push(node);
				}
				for (Node node : top.incoming) {
					s.push(node);
				}
			}
			group++;
		}
		
		int[] numStartPerGroup = new int[nodes.length];
		int[] numEndPerGroup = new int[nodes.length];

		for (int i = 0; i < nodes.length; i++) {
			if (nodes[i].incoming.size() == 0) {
				numStartPerGroup[nodes[i].group]++;
			}
			if (nodes[i].outgoing.size() == 0) {
				numEndPerGroup[nodes[i].group]++;
			}
		}
		
		int max = 0;

		for (int i = 0; i < nodes.length; i++) {
			max += Math.max(numStartPerGroup[i], numEndPerGroup[i]);
		}
		
		return max;
	}
	
	private class Node {
		public Integer id;
		public Integer group;
		public ArrayList<Node> incoming;
		public ArrayList<Node> outgoing;
		Node(int id) {
			this.id = id;
			this.group = -1;
			incoming = new ArrayList<Node>();
			outgoing = new ArrayList<Node>();
		}
		public String toString() {
			return "(" + id + ", " + group + ")";
		}
	} 
	
	public int maxMagicalGirls(String[] love) {
		int n = love.length;
		int[][] sp = new int[n][n];
		int[][] t = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (love[i].charAt(j) == 'Y') {
					sp[i][j] = 1;
					t[i][j] = 1;
				} else {
					sp[i][j] = Integer.MAX_VALUE;
					t[i][j] = 0;
				}
			}
		}
		for (int k = 0; k < n; k++) {
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (sp[i][k] != Integer.MAX_VALUE && sp[k][j] != Integer.MAX_VALUE) {
						sp[i][j] = Math.min(sp[i][j], sp[i][k] + sp[k][j]);
					}
					if (t[i][k] == 1 && t[k][j] == 1) {
						t[i][j] = 1;
					}
				}
			}
		}
		
		for (int i = 0; i < 1 << n; i++) {
			// try every possible scenario
		}
		return 0;
	}

}
