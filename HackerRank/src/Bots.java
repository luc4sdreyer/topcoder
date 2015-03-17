import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;

public class Bots {
	public static void main(String[] args) {
		//packman(System.in);
		botSavesPrincess2(System.in);
	}

	
	public static final int[] dy = {-1, 0, 0, 1};
	//public static final int[] dy = {1, 0, 0, -1};
	public static final int[] dx = {0, -1, 1, 0};
	public static final String[] directions = {"UP", "LEFT", "RIGHT", "DOWN"};
	
	public static void botSavesPrincess2(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int sy = scan.nextInt();
		Pair<Integer, Integer> start = new Pair<>(0, scan.nextInt());
		start.first = scan.nextInt();
		char[][] map = new char[sy][];
		for (int i = 0; i < map.length; i++) {
			map[i] = scan.nextLine().toCharArray();
		}		
		scan.close();
		botSavesPrincess2(start, map[0].length, sy, map);
	}
	
	public static void botSavesPrincess2(Pair<Integer, Integer> start, int sx, int sy, char[][] map) {
		Node top = new Node(start.first, start.second, 0, 0, null);
		Node food = null;
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				if (map[i][j] == 'p') {
					food = new Node(j, i, 0, 0, null);
				}
			}
		}
		Queue<Node> queue = new LinkedList<>();
		queue.add(top);
		LinkedHashSet<Node> visited = new LinkedHashSet<>();
		while (!queue.isEmpty()) {
			top = queue.poll();
			if (visited.contains(top)) {
				continue;
			}
			visited.add(top);
			if (top.equals(food)) {
				break;
			}
			for (int i = 0; i < dx.length; i++) {
				Node next = new Node(top.x + dx[i], top.y + dy[i], top.v, top.steps, top);
				next.direction = directions[i];
				if ((next.x >= 0 && next.x < sx && next.y >= 0 && next.y < sy && map[next.y][next.x] != '%')) {
					queue.add(next);
				}
			}
		}
		Node current = top;
		ArrayList<Node> path = new ArrayList<>();
		while (current != null) {
			path.add(current);
			current = current.parent;
		}
		Collections.reverse(path);
		System.out.println(path.get(1).direction);
		return;
	}

	public static void packman(InputStream in) {
		MyScanner scan = new MyScanner(in);
		Pair<Integer, Integer> packman = new Pair<>(0, scan.nextInt());
		packman.first = scan.nextInt();
		Pair<Integer, Integer> food = new Pair<>(0, scan.nextInt());
		food.first = scan.nextInt();
		
		int sy = scan.nextInt();
		int sx = scan.nextInt();
		char[][] map = new char[sy][];
		for (int i = 0; i < map.length; i++) {
			map[i] = scan.nextLine().toCharArray();
		}		
		scan.close();
		//packmanDfs(packman, food, sx, sy, map);
		//packmanBfs(packman, food, sx, sy, map);
		packmanAStar(packman, food, sx, sy, map);
	}
	
	public static void packmanAStar(Pair<Integer, Integer> packman, Pair<Integer, Integer> f, int sx, int sy, char[][] map) {
		Node top = new Node(packman.first, packman.second, 0, 0, null);
		Node food = new Node(f.first, f.second, 0, 0, null);
		PriorityQueue<Node> queue = new PriorityQueue<>();
		queue.add(top);
		LinkedHashSet<Node> visited = new LinkedHashSet<>();
		while (!queue.isEmpty()) {
			top = queue.poll();
			if (visited.contains(top)) {
				continue;
			}
			visited.add(top);
			if (top.equals(food)) {
				break;
			}
			for (int i = 0; i < dx.length; i++) {
				Node next = new Node(top.x + dx[i], top.y + dy[i], 0, top.steps+1, top);
				if ((next.x >= 0 && next.x < sx && next.y >= 0 && next.y < sy && map[next.y][next.x] != '%')) {
					int distToGo = Math.abs(next.x - food.x) + Math.abs(next.y - food.y);
					next.v = next.steps + distToGo;
					if (distToGo == 0) {
						next.v = 0;
					}
					queue.add(next);
				}
			}
		}
		Node current = top;
		ArrayList<Node> path = new ArrayList<>();
		while (current != null) {
			path.add(current);
			current = current.parent;
		}
		Collections.reverse(path);
		System.out.println(path.size()-1);
		for (int i = 0; i < path.size(); i++) {
			System.out.println(path.get(i));
		}

		return;
	}

	public static void packmanBfs(Pair<Integer, Integer> packman, Pair<Integer, Integer> f, int sx, int sy, char[][] map) {
		Node top = new Node(packman.first, packman.second, 0, 0, null);
		Node food = new Node(f.first, f.second, 0, 0, null);
		Queue<Node> queue = new LinkedList<>();
		queue.add(top);
		LinkedHashSet<Node> visited = new LinkedHashSet<>();
		while (!queue.isEmpty()) {
			top = queue.poll();
			if (visited.contains(top)) {
				continue;
			}
			visited.add(top);
			if (top.equals(food)) {
				break;
			}
			for (int i = 0; i < dx.length; i++) {
				Node next = new Node(top.x + dx[i], top.y + dy[i], top.v, top.steps, top);
				if ((next.x >= 0 && next.x < sx && next.y >= 0 && next.y < sy && map[next.y][next.x] != '%')) {
					queue.add(next);
				}
			}
		}
		System.out.println(visited.size());
		for (Node node : visited) {
			System.out.println(node);
		}
		Node current = top;
		ArrayList<Node> path = new ArrayList<>();
		while (current != null) {
			path.add(current);
			current = current.parent;
		}
		Collections.reverse(path);
		System.out.println(path.size()-1);
		for (int i = 0; i < path.size(); i++) {
			System.out.println(path.get(i));
		}

		return;
	}
	
	public static void packmanDfs(Pair<Integer, Integer> packman, Pair<Integer, Integer> f, int sx, int sy, char[][] map) {
		Node top = new Node(packman.first, packman.second, 0, 0, null);
		Node food = new Node(f.first, f.second, 0, 0, null);
		Stack<Node> stack = new Stack<>();
		stack.push(top);
		LinkedHashSet<Node> visited = new LinkedHashSet<>();
		while (!stack.isEmpty()) {
			top = stack.pop();
			if (visited.contains(top)) {
				continue;
			}
			visited.add(top);
			if (top.equals(food)) {
				break;
			}
			for (int i = 0; i < dx.length; i++) {
				Node next = new Node(top.x + dx[i], top.y + dy[i], top.v, top.steps, top);
				if ((next.x >= 0 && next.x < sx && next.y >= 0 && next.y < sy && map[next.y][next.x] != '%')) {
					stack.push(next);
				}
			}
		}
		System.out.println(visited.size());
		for (Node node : visited) {
			System.out.println(node);
		}
		Node current = top;
		ArrayList<Node> path = new ArrayList<>();
		while (current != null) {
			path.add(current);
			current = current.parent;
		}
		Collections.reverse(path);
		System.out.println(path.size()-1);
		for (int i = 0; i < path.size(); i++) {
			System.out.println(path.get(i));
		}
//		for (int k = 0; k < path.size(); k++) {
//			map[path.get(k).y][path.get(k).x] = 'o';
//			for (int i = 0; i < map.length; i++) {
//				for (int j = 0; j < map[0].length; j++) {
//					System.out.print(map[i][j]);
//				}
//				System.out.println();
//			}
//			System.out.println();
//		}

		System.out.println();
		System.out.println(visited.size());
		System.out.println(path.size()-1);
		return;
	}

	public static class MyScanner {
		BufferedReader br;
		StringTokenizer st;

		public MyScanner(InputStream in) {
			this.br = new BufferedReader(new InputStreamReader(in));
		}

		public void close() {
			try {
				this.br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		String next() {
			while (st == null || !st.hasMoreElements()) {
				try {
					String s = br.readLine();
					if (s != null) {
						st = new StringTokenizer(s);
					} else {
						return null;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return st.nextToken();
		}

		int nextInt() {
			return Integer.parseInt(next());
		}

		long nextLong() {
			return Long.parseLong(next());
		}

		double nextDouble() {
			return Double.parseDouble(next());
		}

		String nextLine(){
			String str = "";
			try {
				str = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return str;
		}
	}

	
	public static class Node implements Comparable<Node> {
		private int x;
		private int y;
		private int v;
		private int steps;
		private Node parent;
		private String direction;
		
		public Node(int x, int y, int v, int steps, Node parent) {
			super();
			this.x = x;
			this.y = y;
			this.v = v;
			this.parent = parent;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + v;
			result = prime * result + x;
			result = prime * result + y;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Node other = (Node) obj;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			return true;
		}

		@Override
		public String toString() {
			return y + " " + x;
		}

		@Override
		public int compareTo(Node o) {
			return Integer.compare(this.v, o.v);
		}
	}
	
	
	public static class Pair<A extends Comparable<A>, B> implements Comparable<Pair<A, B>> {
		private A first;
		private B second;

		public Pair(A first, B second) {
			super();
			this.first = first;
			this.second = second;
		}

		public int hashCode() {
			int hashFirst = first != null ? first.hashCode() : 0;
			int hashSecond = second != null ? second.hashCode() : 0;

			return (hashFirst + hashSecond) * hashSecond + hashFirst;
		}

		public boolean equals(Object other) {
			if (other instanceof Pair) {
				@SuppressWarnings("unchecked")
				Pair<A, B> otherPair = (Pair<A, B>) other;
				return 
					((this.first == otherPair.first ||
						(this.first != null && otherPair.first != null &&
						this.first.equals(otherPair.first))) &&
					(this.second == otherPair.second ||
						(this.second != null && otherPair.second != null &&
						this.second.equals(otherPair.second))));
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
		public int compareTo(Pair<A, B> o) {
			int compareFirst = this.first.compareTo(o.first);
			return compareFirst;
		}
	}
}
