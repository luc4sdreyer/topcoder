import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class ColorfulChocolates {


	class Node {
		public int swapsLeft, spread;
		public String value;
		
		public Node(int swapsLeft, int spread, String value) {
			this.swapsLeft = swapsLeft;
			this.value = value;
			this.spread = spread;
		}		
		
		public String toString() {
			return "(" + swapsLeft + ", "+ value + ")";
		}
	}
	
	public int maximumSpread(String chocolates, int maxSwaps) {
		if (chocolates.length() == 1) {
			return 1;
		}
		Queue<Node> q = new LinkedList<Node>();
		q.add(new Node(maxSwaps, 0, chocolates));
		HashSet<String> visited = new HashSet<String>();
		int maxSpread = 0;
		while(!q.isEmpty()) {
			Node top = q.poll();
			if (visited.contains(top.value)) {
				continue;
			}
			visited.add(top.value);
			

			if (top.spread > maxSpread) {
				maxSpread = top.spread;
			}
			
			for (int i = 0; i < chocolates.length(); i++) {
				for (int j = i+1; j < chocolates.length(); j++) {
					if (i != j && Math.abs(i-j) <= top.swapsLeft) {
						char[] temp = top.value.toCharArray();
						char t = temp[i];
						temp[i] = temp[j];
						temp[j] = t;
						int bestSpread = 0;
						int spread = 1;
						char oldChar = temp[0];
						for (int k = 1; k < chocolates.length(); k++) {
							if (temp[k] == oldChar) {
								spread++;
							} else {
								if (spread > bestSpread) {
									bestSpread = spread;
								}
								spread = 1;
								oldChar = temp[k];
							}
						}
						if (bestSpread > top.spread) {
							Node newNode = new Node(top.swapsLeft - Math.abs(i-j), bestSpread, new String(temp));
							if (!visited.contains(newNode.value)) {
								q.add(newNode);
							}
						}
					}
				}
			}
		}
		
		return maxSpread;
	}
}
