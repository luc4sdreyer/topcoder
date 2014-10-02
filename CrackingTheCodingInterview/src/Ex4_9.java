import java.util.Arrays;

class Ex4_9 {
	public static void main(String args[])  {
		BNode root = new BNode(2);
		root.left = new BNode(2);
		root.right = new BNode(2);
		findSum(root, 6);
	}

	public static void findSum(BNode node, int sum, int[] path, int level) {
		if (node == null) {
			return;
		}

		path[level] = node.data;

		int temp = 0;
		for (int i = level; i >= 0; i--) {
			temp += path[i];
			if (temp == sum) {
				System.out.println(Arrays.toString(path));
			}
		}

		findSum(node.left, sum, path, level+1);
		findSum(node.right, sum, path, level+1);
	}

	public static void findSum(BNode root, int sum) {
		int[] path = new int[depth(root)];
		findSum(root, sum, path, 0);
	}

	public static int depth(BNode node) {
		if (node == null) {
			return 0;
		}
		return Math.max(depth(node.left), depth(node.right))+1;
	}

	public static class BNode {
		BNode left;
		BNode right;
		int data;
		public BNode(int data) {
			this.data = data;
		}
	}
}