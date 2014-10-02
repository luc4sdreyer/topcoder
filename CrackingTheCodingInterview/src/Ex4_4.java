import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

class Ex4_4 {
	public static void main(String args[])  {
		BinaryNode root = new BinaryNode();
		
		String test = "test";
		test.contains("est");
	}

	public static int getLists(BinaryNode node, HashMap<Integer, ArrayList<BinaryNode>> listMap) {
		if (node.visited) {
			if (!listMap.containsKey(0)) {
				listMap.put(0, new ArrayList<BinaryNode>());
			}
			listMap.get(0).add(node);
			return 0;
		} else {
			int depth;
			depth = Math.max(getLists(node.left, listMap), getLists(node.right, listMap))+1;
			if (!listMap.containsKey(depth)) {
				listMap.put(depth, new ArrayList<BinaryNode>());
			}
			listMap.get(depth).add(node);
			return depth;
		}
	}

	public static void bfs(BinaryNode root) {
		Stack<BinaryNode> stack = new Stack<BinaryNode>();
		root.depth = 0;
		stack.push(root);
		HashMap<Integer, ArrayList<BinaryNode>> listMap = new HashMap<Integer, ArrayList<BinaryNode>>();
		while (!stack.isEmpty()) {
			BinaryNode top = stack.pop();

			if (top.visited) {
				continue;
			}
			top.visited = true;
			if (!listMap.containsKey(top.depth)) {
				listMap.put(top.depth, new ArrayList<BinaryNode>());
			}
			listMap.get(top.depth).add(top);

			if (top.left != null && top.left.visited == false) {
				top.left.depth = top.depth + 1;
				stack.push(top.left);
			}
			if (top.right != null && top.right.visited == false) {
				top.right.depth = top.depth + 1;
				stack.push(top.right);
			}
		}
	} 

	public static class BinaryNode {
		BinaryNode left;
		BinaryNode right;
		int depth;
		boolean visited = false;
		public BinaryNode() {}
	}
}