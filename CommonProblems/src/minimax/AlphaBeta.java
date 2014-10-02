package minimax;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class AlphaBeta {
	final private boolean recursiveCalculationNegamax = true; 

	final private int negInf = -1000000000;
	final private int posInf = 1000000000;

	private final Random rand;
	private final int randLimit ;
	private final int branchingFactor;
	private final boolean iterative;
	
	private int alphaCutoffs = 0;
	private int betaCutoffs = 0;
	private int branchingPoints = 0;
	private int branches = 0;

	public AlphaBeta(int rand, int randLimit, int branchingFactor, boolean iterative) {
		super();
		this.rand = new Random(rand);
		this.randLimit = randLimit;
		this.branchingFactor = branchingFactor;
		this.iterative = iterative;
	}

	public int calculate(int depth) {
		if (!iterative) {
			if (recursiveCalculationNegamax) {
				return alphaBeta(negInf, posInf, depth);
			} else {
				return alphaBetaMax(negInf, posInf, depth);
			}
		} else {
			return alphaBetaIterative(depth);
		}
	}

	int alphaBeta(int alpha, int beta, int depthleft) {
		if (depthleft == 0) return evaluate();
		branchingPoints++;
		for (int i = 0; i < branchingFactor; i++) {
			branches++;
			int score = -1 * alphaBeta(-beta, -alpha, depthleft - 1);
			if (score >= beta) {
				betaCutoffs++;
				return beta;   //  fail hard beta-cutoff
			}
			if (score > alpha) {
				alphaCutoffs++;
				alpha = score; // alpha acts like max in MiniMax
			}
		}
		return alpha;
	}

	public int alphaBetaMax(int alpha, int beta, int depthleft) {
		if (depthleft == 0) return evaluate();
		for (int i = 0; i < branchingFactor; i++) {
			int score = alphaBetaMin(alpha, beta, depthleft - 1);
			if (score >= beta)
				return beta;   // fail hard beta-cutoff
			if (score > alpha)
				alpha = score; // alpha acts like max in MiniMax
		}
		return alpha;
	}

	public int alphaBetaMin(int alpha, int beta, int depthleft) {
		if (depthleft == 0) return evaluate();
		for (int i = 0; i < branchingFactor; i++) {
			int score = alphaBetaMax(alpha, beta, depthleft - 1);
			if (score <= alpha)
				return alpha; // fail hard alpha-cutoff
			if (score < beta)
				beta = score; // beta acts like min in MiniMax
		}
		return beta;
	}

	public static class Node {
		private static int instanceCounter = 0;
		public int depth;
		public ArrayList<Integer> results;
		public ArrayList<Node> children;
		public Node parent;
		private int id;
		public int alpha;
		public int beta;
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

	public int alphaBetaIterative(int depth) {
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
				current.parent.results.add(alphaBetaIterativeStep(current));
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
		return alphaBetaIterativeStep(root);
	}

	public int alphaBetaIterativeStep(Node node) {
		branchingPoints++;
		for (int i = 0; i < node.results.size(); i++) {
			branches++;
			int score = -1 * node.results.get(i); //alphaBeta(-beta, -alpha, depthleft - 1);
			if (score >= node.beta) {
				betaCutoffs++;
				return node.beta;   //  fail hard beta-cutoff
			}
			if (score > node.alpha) {
				alphaCutoffs++;
				node.alpha = score; // alpha acts like max in MiniMax
			}
		}
		return node.alpha;
	}

	public int evaluate() {
		return rand.nextInt(randLimit) - randLimit/2;
	}
	
	public String getStats() {
		return "alphaCutoffs: " + alphaCutoffs + "\t betaCutoffs: " + betaCutoffs + "\t branching factor: " + branches/(double)branchingPoints;
	}

	public static void main(String[] args) {
		AlphaBeta recursive = new AlphaBeta(0, 1000, 5, false);
		AlphaBeta iterative = new AlphaBeta(0, 1000, 5, true);
		for (int i = 9; i < 10; i++) {
			System.out.println("AlphaBeta(" + i + "): " + recursive.calculate(i) + "  \t AlphaBeta Iterative(" + i + "): " + iterative.calculate(i));
		}
		System.out.println("recursive.getStats(): " + recursive.getStats());
		System.out.println("iterative.getStats(): " + iterative.getStats());
	}
}
