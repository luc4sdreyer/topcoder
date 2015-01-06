import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;


public class R268 {
	public static final long mod = 1000000007;
	public static void main(String[] args) {
		//System.out.println(betheguy(System.in));
		//System.out.println(chatonline(System.in));
		System.out.println(game(System.in));
	}

	public static String game(InputStream in) {
		Scanner input = new Scanner(in);
		int n = input.nextInt();
		input.close();
//		for (int i = 0; i < 100; i++) {
//			System.out.println(i + "\t" + gameF(i));
//		}
		return gameF(n) + "";
	}
	public static final String[] operands = {" + ", " - ", " * "};
	public static class Node {
		Node parent;
		int op;
		int[] list;
		String desc;
		public Node(Node parent, int op, int[] list, String desc) {
			super();
			this.parent = parent;
			this.op = op;
			this.list = list;
			this.desc = desc;
		}
	}
	public static String gameF(int n) {
		if (n <= 3) {
			return "NO";
		}
		StringBuilder ret = new StringBuilder();
		ret.append("YES\n");
		int extra = 0;
		while (n+4 > 7+4) {
			ret.append(n + " - " + (n-1) + " = 1\n");
			ret.append((n-3) + " - " + (n-2) + " = -1\n");
			n -= 4;
			extra++;
		}
		
		
		int[] initial = new int[n];
		for (int i = 0; i < initial.length; i++) {
			initial[i] = i+1;
		}
		//Queue<Node> q = new LinkedList<>();
		Stack<Node> q = new Stack<>();
		Node top = new Node(null, -1, initial, "");
		q.add(top);
		//boolean valid = false;
		while (!q.isEmpty()) {
			//top = q.poll();
			top = q.pop();
			if (top.list.length == 1) {
				if (top.list[0] == 24) {
					//valid = true;
					break;
				}
			}
			for (int i = 0; i < top.list.length; i++) {
				for (int j = i+1; j < top.list.length; j++) {
					for (int op = 0; op < 3; op++) {
						int[] newList = new int[top.list.length-1];
						int t = 0;
						for (int k = 0; k < top.list.length; k++) {
							if (k != i && k != j) {
								newList[t++] = top.list[k];
							}
						}
						if (op == 0) {
							newList[t++] = top.list[i] + top.list[j]; 
						} else if (op == 1) {
							newList[t++] = top.list[i] - top.list[j]; 
						} else if (op == 2) {
							newList[t++] = top.list[i] * top.list[j]; 
						} 
						String desc = top.list[i] + operands[op] + top.list[j] + " = " + newList[t-1];
						q.add(new Node(top, op, newList, desc));
					}
				}
			}
		}
		ArrayList<String> all = new ArrayList<>();
		Node current = top;
		while (current.parent != null) {
			all.add(current.desc);
			current = current.parent;
		}
		for (int i = all.size()-1; i >= 0; i--) {
			ret.append(all.get(i) + "\n");
		}
		for (int i = 0; i < extra; i++) {
			ret.append("24 + 1 = 25\n");
			ret.append("25 + -1 = 24\n");
		}
		return ret.toString();
	}

	public static String chatonline(InputStream in) {
		Scanner input = new Scanner(in);
		int p = input.nextInt();
		int q = input.nextInt();
		int l = input.nextInt();
		int r = input.nextInt();
		int[][] s1 = new int[p][2];
		int[][] s2 = new int[q][2];
		for (int i = 0; i < p; i++) {
			s1[i][0] = input.nextInt();
			s1[i][1] = input.nextInt();
		}
		for (int i = 0; i < q; i++) {
			s2[i][0] = input.nextInt();
			s2[i][1] = input.nextInt();
		}
		input.close();
		return chatonlineF(p, q, l, r, s1, s2) + "";
	}

	private static int chatonlineF(int p, int q, int l, int r, int[][] s1, int[][] s2) {
		int ret = 0;
		HashSet<Integer> set1 = new HashSet<>();
		for (int i = 0; i < s1.length; i++) {
			for (int j = s1[i][0]; j <= s1[i][1]; j++) {
				set1.add(j);
			}
		}
		for (int t = l; t <= r; t++) {
			HashSet<Integer> set2 = new HashSet<>();
			for (int i = 0; i < s2.length; i++) {
				for (int j = s2[i][0]; j <= s2[i][1]; j++) {
					set2.add(j+t);
				}
			}
			boolean valid = false;
			for (Integer i : set2) {
				if (set1.contains(i)) {
					valid = true;
					break;
				}
			}
			if (valid) {
				ret++;
			}
		}
		return ret;
	}

	public static String betheguy(InputStream in) {
		Scanner input = new Scanner(in);
		int n = input.nextInt();
		int p = input.nextInt();
		boolean[] pass = new boolean[n+1];
		for (int i = 0; i < p; i++) {
			pass[input.nextInt()] = true;
		}
		int q = input.nextInt();
		for (int i = 0; i < q; i++) {
			pass[input.nextInt()] = true;
		}
		input.close();
		boolean can = true;
		for (int i = 1; i < pass.length; i++) {
			if (!pass[i]) {
				can = false;
				break;
			}
		}
		return can ? "I become the guy." : "Oh, my keyboard!";
	}
	

}
