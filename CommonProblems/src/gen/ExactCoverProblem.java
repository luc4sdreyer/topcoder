package gen;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.HashMap;

public class ExactCoverProblem {
	public static void main(String[] args) {
		testAlgorithmX();
	}
	
	public static void testAlgorithmX() {
		/**
		 * In mathematics, given a collection S of subsets of a set X, an exact cover is a subcollection S* of S
		 * such that each element in X is contained in exactly one subset in S*. One says that each element in X
		 * is covered by exactly one subset in S*. An exact cover is a kind of cover.
		 * 
		 * In computer science, the exact cover problem is a decision problem to determine if an exact cover exists.
		 * The exact cover problem is NP-complete and is one of Karp's 21 NP-complete problems. The exact cover
		 * problem is a kind of constraint satisfaction problem.
		 * 
		 * Knuth's Algorithm X is an algorithm that finds all solutions to an exact cover problem. Dancing Links,
		 * commonly known as DLX, is the technique suggested by Donald Knuth to efficiently implement his Algorithm X
		 * on a computer.
		 * 
		 * Credit to Ali Assaf (http://www.cs.mcgill.ca/~aassaf9/python/algorithm_x.html), I converted his Python
		 * implementation to java.
		 * 
		 * Example: Determine if some selection of the following subsets covers the set of numbers 
		 * up to 7: [0, 1, 2, 3, 4, 5, 6, 7]
		 */
		
		ArrayList<int[]> s = new ArrayList<>();
		
		int n = 7;
		s.add(new int[]{1, 4, 7});
		s.add(new int[]{0, 1, 4});
		s.add(new int[]{4, 5, 7});
		s.add(new int[]{3, 5, 6});
		s.add(new int[]{2, 3, 6, 7});
		s.add(new int[]{2, 7});
		
		ArrayList<Integer> solution = algorithmX(n, s);
		for (int i = 0; i < solution.size(); i++) {
			System.out.println(Arrays.toString(s.get(solution.get(i))));
		}
	}
	
	public static ArrayList<Integer> algorithmX(int n, ArrayList<int[]> s) {
		HashMap<Integer, HashSet<Integer>> X = new HashMap<>();
		for (int i = 0; i <= n; i++) {
			X.put(i, new HashSet<Integer>());
		}
		for (int i = 0; i < s.size(); i++) {
			for (int j = 0; j < s.get(i).length; j++) {
				HashSet<Integer> set = X.get(s.get(i)[j]);
				set.add(i);
			}
		}
		ArrayList<Integer> solution = new ArrayList<>();
		solve(X, s, solution, n);
		return solution;
	}

	public static ArrayList<Integer> solve(HashMap<Integer, HashSet<Integer>> X,  ArrayList<int[]> s, 
			ArrayList<Integer> solution, int n) {
		if (X.isEmpty()) {
			return solution;
		} else {
			int c = Integer.MAX_VALUE;
			int cIdx = 0;
			for (int key: X.keySet()) {
				if (X.get(key).size() < c) {
					cIdx = key;
					c = X.get(key).size();
				}
			}
			ArrayList<Integer> set = new ArrayList<>(X.get(cIdx));
			Collections.shuffle(set);
			for (int r : set) {
				solution.add(r);
				ArrayList<HashSet<Integer>> cols = select(X, s, r);
				ArrayList<Integer> sol = solve(X, s, solution, n);
				if (sol != null && !sol.isEmpty()) {
					return sol;
				}
				select(X, s, r, cols);
				solution.remove(solution.size()-1);
			}
			return null;
		}
	}
	
	public static ArrayList<HashSet<Integer>> select(HashMap<Integer, HashSet<Integer>> X,  ArrayList<int[]> s, int r) {
		ArrayList<HashSet<Integer>> cols = new ArrayList<>();
		for (int j : s.get(r)) {
			for (int i : X.get(j)) {
				for (int k : s.get(i)) { 
					if (k != j) {
						X.get(k).remove(i);
					}
				}
			}
			cols.add(X.remove(j));
		}
		return cols;		
	}

	public static void select(HashMap<Integer, HashSet<Integer>> X,  ArrayList<int[]> s, int r, 
			ArrayList<HashSet<Integer>> cols) {
		for (int m = s.get(r).length-1; m >= 0; m--) {
			int j = s.get(r)[m];
			X.put(j, cols.remove(cols.size()-1));
			for (int i : X.get(j)) {
				for (int k: s.get(i)) {
					if (k != j) {
						X.get(k).add(i);
					}
				}
			}
		}
	}
}
