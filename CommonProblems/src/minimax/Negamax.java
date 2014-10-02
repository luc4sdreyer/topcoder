package minimax;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Negamax {
	final private int negInf = -1000000000;
	//final private int posInf = 1000000000;

	private final Random rand;
	private final int randLimit ;
	private final int branchingFactor;
	private final boolean iterative;

	public Negamax(int rand, int randLimit, int branchingFactor, boolean iterative) {
		super();
		this.rand = new Random(rand);
		this.randLimit = randLimit;
		this.branchingFactor = branchingFactor;
		this.iterative = iterative;
	}

	public int calculate(int depth) {
		if (!iterative) {
			return negaMaxRecursive(depth);
		} else {
			return negaMaxIterative(depth);
		}
	}

	public int negaMaxRecursive(int depth) {
		if (depth == 0) return evaluate();
		int max = negInf;
		for (int i = 0; i < branchingFactor; i++) {			
			int score = -1 * negaMaxRecursive(depth - 1);
			if (score > max)
				max = score;
		}
		return max;
	}

	public static class Node {
		private static int instanceCounter = 0;
		public int depth;
		public ArrayList<Integer> results;
		public ArrayList<Node> children;
		public Node parent;
		private int id;
		public Node(int depth, Node parent) {
			this.id = instanceCounter++;
			this.depth = depth;
			this.results = new ArrayList<>();
			this.children = new ArrayList<>();
			this.parent = parent;
		}
		public String toString() {
			return this.id + "";
		}
	}

	public int negaMaxIterative(int depth) {
		Stack<Node> stack = new Stack<>();
		Node root = new Node(depth, null);
		stack.push(root);

		while (!stack.isEmpty()) {
			Node top = stack.pop();
			if (top.depth == 0) {
				top.parent.results.add(evaluate());
				continue;
			}
			for (int i = 0; i < branchingFactor; i++) {
				Node child = new Node(top.depth - 1, top);
				top.children.add(child);
				stack.push(child);
			}
		}

		Node current = root;
		while (!current.children.isEmpty()) {
			boolean edgeNode = true;
			for (int i = 0; i < current.children.size(); i++) {
				if (!current.children.get(i).children.isEmpty()) {
					edgeNode = false;
					break;
				}
			}
			if (edgeNode) {
				if (current.parent == null) {
					break;
				}
				current.parent.results.add(negaMaxIterativeStep(current.results));
				current.children.clear();
				current = current.parent;
			} else {
				for (int i = 0; i < current.children.size(); i++) {
					if (!current.children.get(i).children.isEmpty()) {
						current = current.children.get(i);
						break;
					}
				}
			}
		}
		return negaMaxIterativeStep(root.results);
	}

	public int negaMaxIterativeStep(ArrayList<Integer> results) {
		int max = negInf;
		for (int i = 0; i < results.size(); i++) {
			int score = -1 * results.get(i);
			if (score > max)
				max = score;
		}
		return max;
	}

	public int evaluate() {
		return rand.nextInt(randLimit) - randLimit/2;
	}

	public static void main(String[] args) {
		Negamax recursive = new Negamax(0, 1000, 3, false);
		Negamax iterative = new Negamax(0, 1000, 3, true);
		for (int i = 1; i < 10; i++) {
			System.out.println("negaMax(" + i + "): " + recursive.calculate(i) + "  \t negaMaxIterative(" + i + "): " + iterative.calculate(i));
		}
	}

}
