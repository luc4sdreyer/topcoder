import java.awt.geom.Area;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class R275 {
	
	public static void main(String[] args) {
		//System.out.println(diversePermutation(System.in));
		System.out.println(interestingArray(System.in));
	}
	
	public static String interestingArray(InputStream in) {
		Scanner scan = new Scanner(in);
		int n = scan.nextInt();
		int m = scan.nextInt();
		int[][] limits = new int[m][3];
		for (int i = 0; i < limits.length; i++) {
			for (int j = 0; j < 3; j++) {
				limits[i][j] = scan.nextInt();
			}			
		}
		scan.close();
		return interestingArray(n, m, limits);
	}
	
	public static String interestingArray(int n, int m, int[][] lim) {
		int[] interest = new int[n];
		ArrayList<Pair<Integer, Integer, Integer>> limits = new ArrayList<>();
		for (int i = 0; i < lim.length; i++) {
			limits.add(new Pair<Integer, Integer, Integer>(lim[i][0], lim[i][1], lim[i][2]));
		}
		Collections.sort(limits);
		ArrayList<ArrayList<Pair<Integer, Integer, Integer>>> start = new ArrayList<>(n+1);
		ArrayList<ArrayList<Pair<Integer, Integer, Integer>>> end = new ArrayList<>(n+1);
		for (int i = 0; i < limits.size(); i++) {
			if (start.get(limits.get(i).first) == null) {
				start.set(limits.get(i).first, new ArrayList<Pair<Integer, Integer, Integer>>());
			}
			ArrayList<Pair<Integer, Integer, Integer>> here = start.get(limits.get(i).first);
			here.add(limits.get(i));
		}
		for (int i = 0; i < limits.size(); i++) {
			if (end.get(limits.get(i).second) == null) {
				end.set(limits.get(i).second, new ArrayList<Pair<Integer, Integer, Integer>>());
			}
			ArrayList<Pair<Integer, Integer, Integer>> here = end.get(limits.get(i).second);
			here.add(limits.get(i));
		}
		
		for (int i = 0; i < interest.length; i++) {
			if (start.get(i) != null) {
				for (Pair<Integer, Integer, Integer> p: start.get(i)) {
					
				}
			}
		}
		return null;
	}

	public static String diversePermutation(InputStream in) {
		Scanner input = new Scanner(in);
		int n = input.nextInt();
		int k = input.nextInt();
		input.close();
		return "";
		//int[] a = new int
	}

	public static class Pair<A extends Comparable<A>, B extends Comparable<B>, C>	implements Comparable<Pair<A, B, C>> {
		private A first;
		private B second;
		private C q;

		public Pair(A first, B second, C q) {
			super();
			this.first = first;
			this.second = second;
			this.q = q;
		}

		public int hashCode() {
			int hashFirst = first != null ? first.hashCode() : 0;
			int hashSecond = second != null ? second.hashCode() : 0;
			int hashThird = q != null ? q.hashCode() : 0;

			return ((hashFirst + hashSecond) * hashSecond + hashFirst) * hashThird + hashFirst;
		}

		public boolean equals(Object other) {
			if (other instanceof Pair) {
				@SuppressWarnings("unchecked")
				Pair<A, B, C> otherPair = (Pair<A, B, C>) other;
				return 
					((this.first == otherPair.first ||
						(this.first != null && otherPair.first != null &&
						this.first.equals(otherPair.first))) &&
					(this.second == otherPair.second ||
						(this.second != null && otherPair.second != null &&
						this.second.equals(otherPair.second))) &&
					(this.q == otherPair.q ||
						(this.q != null && otherPair.q != null &&
						this.q.equals(otherPair.q))));
			}

			return false;
		}

		public String toString()
		{ 
			return "(" + first + ", " + second + ")"; 
		}

		public A getFirst() {
			return first;
		}

		public void setFirst(A first) {
			this.first = first;
		}

		public B getSecond() {
			return second;
		}

		public void setSecond(B second) {
			this.second = second;
		}

		@Override
		public int compareTo(Pair<A, B, C> o) {
			int compareFirst = this.first.compareTo(o.first);
			if (compareFirst != 0) {
				return compareFirst;
			} else {
				return this.second.compareTo(o.second);
			}
		}
	}
}
