import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class OneRegister {
	
	public static class Node {
		StringBuilder program;
		long value;
		public Node(StringBuilder program, long value) {
			this.program = program;
			this.value = value;
		}
	}

	public String getProgram(int s, int t) {
		StringBuilder a = new StringBuilder();
		Node top = new Node(a, s);
		Queue<Node> q = new LinkedList<>();
		HashSet<Integer> visited = new HashSet<>();
		q.add(top);
		while (!q.isEmpty()) {
			top = q.poll();
			if (top.value < 0 || top.value > 1000000000 || visited.contains((int)top.value)) {
				continue;
			}
			long val = top.value;
			visited.add((int) val);
			
			if (val == t) {
				return top.program.toString();
			}
			
			StringBuilder child = new StringBuilder(top.program);
			child.append("*");
			q.add(new Node(child, val*val));
			
			child = new StringBuilder(top.program);
			child.append("+");
			q.add(new Node(child, val+val));
			
			child = new StringBuilder(top.program);
			child.append("-");
			q.add(new Node(child, val-val));
			
			if (val != 0) {
				child = new StringBuilder(top.program);
				child.append("/");
				q.add(new Node(child, val/val));
			}
		}
		return ":-(";
	}

}
