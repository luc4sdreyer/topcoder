import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

class Ex9_6 {
	public static void main(String args[]) {
		getParenthesis(4);
	}

	public static class Node {
		int position;
		int height;
		ArrayList<Boolean> path;
		public Node(int position, int height, ArrayList<Boolean> path) {
			this.position = position;
			this.height = height;
			this.path = new ArrayList<Boolean>();
			for (boolean b : path) {
				this.path.add(b);
			}
		}
	}

	public static void getParenthesis(int n) {
		Queue<Node> q = new LinkedList<Node>();
		ArrayList<Boolean> temp = new ArrayList<Boolean>();
		temp.add(true);
		q.add(new Node(0, 1, temp));

		while (!q.isEmpty()) {
			Node top = q.poll();
			if (top.position > n*2) {
				continue;
			}
			if (top.position == n*2 - 1  && top.height == 0) {
				StringBuilder sb = new StringBuilder();
				for (boolean b : top.path) {
					if (b) {
						sb.append("(");
					} else {
						sb.append(")");
					}
				}
				System.out.println(sb.toString());
			}

			if (top.height > 0) {
				Node nextNode = new Node(top.position+1, top.height-1, top.path);
				nextNode.path.add(false);
				q.add(nextNode);
			}
			if (top.height < n) {
				Node nextNode = new Node(top.position+1, top.height+1, top.path);
				nextNode.path.add(true);
				q.add(nextNode);
			}
		}
	}
}