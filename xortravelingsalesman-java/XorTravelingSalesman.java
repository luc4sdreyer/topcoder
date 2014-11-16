import java.util.Stack;

public class XorTravelingSalesman {
	
	public static class Node {
		int city, profit;
		public Node(int city, int profit) {
			this.city = city;
			this.profit = profit;
		}
	}

	public int maxProfit(int[] cityValues, String[] r) {
		char[][] roads = new char[r.length][r.length];
		for (int i = 0; i < roads.length; i++) {
			roads[i] = r[i].toCharArray();
		}
		boolean[][] visited = new boolean[cityValues.length][1024]; 
		Node top = new Node( 0, cityValues[0]);
		Stack<Node> s = new Stack<>();
		s.push(top);
		int max = 0;
		while (!s.isEmpty()) {
			top = s.pop();
			int city = top.city;
			int profit = top.profit;
			if (visited[city][profit]) {
				continue;
			}
			max = Math.max(max, profit);
			visited[city][profit] = true;
			for (int i = 0; i < roads[city].length; i++) {
				if (roads[city][i] == 'Y') {
					s.push(new Node(i, profit ^ cityValues[i]));
				}
			}
		}
		return max;
	}

}
