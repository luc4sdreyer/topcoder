import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Long16_04_2 {
	public static void main(String[] args) {
		// COLOR(System.in);
		// CHBLLNS(System.in);
		// CHEFPATH(System.in);
//		 BIPIN3(System.in);
		 FIBQ(System.in);
//		test_FIBQ();
//		test_FIBQ2();
//		test_FIBQ3();
//		test_FIBQ4();
//		perf_SNAKGAME();
//		test_gsort();
//		SNAKGAME(System.in);
	}

	public static void SNAKGAME(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		int m = scan.nextInt();
		int blocked = scan.nextInt();
		grid = new int[n][m];
		for (int i = 0; i < blocked; i++) {
			int y = scan.nextInt()-1;
			int x = scan.nextInt()-1;
			grid[y][x] = 9;
		}
		SnakeGame game = new SnakeGame(n, m);
		
		while (true) {
			int[] dest = {-1, -1, -1};
			game.nextMove(dest);
			if (dest[0] != -1) {
				grid[dest[1]][dest[0]] = 1;
			} else {
				break;
			}
			String cmd = scan.next();
			if (cmd.equals("NEW")) {
				int ny = scan.nextInt()-1;
				int nx = scan.nextInt()-1;
				grid[ny][nx] = 1;
				game.open.remove(new Pair(nx, ny));
				
			} else if (cmd.equals("MOVE")) {
				int oy = scan.nextInt()-1;
				int ox = scan.nextInt()-1;
				int ny = scan.nextInt()-1;
				int nx = scan.nextInt()-1;
				grid[ny][nx] = 1;
				game.open.remove(new Pair(nx, ny));
				
			}  else if (cmd.equals("FREEZE")) {
				scan.nextInt();
				
			} else if (cmd.equals("EXIT")) {
				break;
			}
		}
	}
	
	public static class SnakeGame {
		
		boolean no_moves = false;
		int[] head = { -2, -2, 0 };
		int[] tail = { -2, -2, 0 };
		int moveCounter = 0;
		int last_respawn = 0;
		int longest = 0;
		int gravity = 0;
		State state = State.Cut;
		boolean headMoving = true;
		TreeSet<Pair> open = new TreeSet<>();
		int n;
		int m;
		
		public SnakeGame(int n2, int m2) {
			n = n2;
			m = m2;
			for (int y = 0; y < n; y++) {
				for (int x = 0; x < m; x++) {
					open.add(new Pair(x, y));
				}
			}
		}
		
		public void nextMove(int[] dest) {

//			System.out.println(printMap(head, tail));
			int[] move = null;
			int[] prev_move = new int[3];
			boolean respawn = false;
			
//			if (moveCounter >= 2304700 && moveCounter % 1 == 0) {
//				System.out.println(printMap(head, tail));
//				System.out.println();
//			}
//			if (!headMoving && moveCounter > 0 && getMoves2(tail[0], tail[1]).isEmpty()) {
//				System.out.println(printMap(head, tail));
//				System.out.println();
//			}
			
			if (moveCounter > 0 && headMoving && getMoves2(head[0], head[1]).isEmpty()) {
				headMoving = false;
				state = State.Fill;
				gravity = 1;
			}
			if (state == State.Cut) {
				if (headMoving && head[1] == grid.length-1) {
					gravity = 1;
					state = State.PostCut;
				} else if (!headMoving && tail[1] == grid.length-1) {
					gravity = 3;
					state = State.PostCut;
				}
			} else if (state == State.PostCut) {
				gravity = 3;
				state = State.Fill;
			} else if (state == State.Fill) {
				if ((headMoving && head[0] == (int)(grid[0].length*0.95)) || (!headMoving && tail[0] == (int)(grid[0].length*0.05))) {
					int[] cols = new int[4];
					if (!headMoving) {
						for (int i = 0; i < cols.length; i++) {
							cols[i] = (int)(grid[0].length*0.05) + 25 * (i+1); 
						}
					} else {
						for (int i = 0; i < cols.length; i++) {
							cols[i] = (int)(grid[0].length*0.95) - 25 * (i+1); 
						}
					}
					int[] ratio = new int[2];
					for (int i = 0; i < cols.length; i++) {
						int x = cols[i];
						for (int y = 0; y < grid.length; y+=5) {
							if (y >= 0 && x >= 0 && x < grid[0].length && grid[y][x] == 0) {
								ratio[y / (grid.length/2 + 1)]++;
							}
						}
					}
					if (ratio[0] > ratio[1]) {
						gravity = 0;
					} else {
						gravity = 2;
					}
//					System.out.println(printMap(head, tail));
					state = State.FillRem;
				}
			}
			
			move = getMove(open, head, gravity);
			if (move == null) {
				ArrayList<int[]> m = getMoves2(head[0], head[1]);
				if (!m.isEmpty()) {
					move = m.get(0);
				} 
			}
			
			if (move != null) {
				arrayCopy(head, prev_move);
				arrayCopy(move, head);
			} else {
				move = getMove(open, tail, gravity);
				if (move == null) {
					ArrayList<int[]> m = getMoves2(tail[0], tail[1]);
					if (!m.isEmpty()) {
						move = m.get(0);
					} 
				}
				if (move != null) {
					arrayCopy(tail, prev_move);
					arrayCopy(move, tail);
				} else {
					move = respawn(open);
					respawn = true;
					if (moveCounter - last_respawn > longest) {
						longest = moveCounter - last_respawn;
					}
					last_respawn = moveCounter;
					if (move == null) {
						no_moves = true;
					} else {
						arrayCopy(move, prev_move);
					}
				}
			}
			
			if (no_moves) {
				System.out.println("EXIT");
			}
			else if (!respawn) {
				open.remove(new Pair(move[0], move[1]));
				arrayCopy(move, dest);
				System.out.println("MOVE " + (prev_move[1]+1) + " " + (prev_move[0]+1) + " " + (move[1]+1) + " " + (move[0]+1));
			} else {
				state = State.Cut;
				headMoving = true;
				gravity = 0;

				arrayCopy(move, head);
				arrayCopy(move, tail);
				arrayCopy(move, dest);
				System.out.println("NEW " + (move[1]+1) + " " + (move[0]+1));
			}
			moveCounter++;
		}
		
	}

	public static void test_gsort() {
		Random rand = new Random(0);
		for (int i = 0; i < 1000000; i++) {
			ArrayList<int[]> a = new ArrayList<>();
			for (int j = 0; j < 100; j++) {
				a.add(new int[]{0, 0, rand.nextInt(4)});
			} 
			for (int j = 0; j < 4; j++) {
				Collections.sort(a, gravitySort[j]);
				
				if (a.get(0)[2] != j) {
					System.out.println("fail");
				}
			}
		}
	}

	public static void perf_SNAKGAME() {
		Random rand = new Random(0);
		ArrayList<Double> s1 = new ArrayList<>();
		ArrayList<Double> s2 = new ArrayList<>();

		for (int noise = 2; noise <= 2; noise++) {
			System.out.println("noise: " + noise);
			for (int type = 1; type <= 1; type++) {
				System.out.println("type: " + type + "\n");
				for (int games = 0; games < 10; games++) {
					int respawns = 0;

					int n = 0;
					int m = 0;
					if (type == 0) {
						n = 200 + rand.nextInt(101);
						m = 200 + rand.nextInt(101);
					} else if (type == 1) {
						n = 500 + rand.nextInt(101);
						m = 500 + rand.nextInt(101);
					} else if (type == -1) {
						n = 20;
						m = 80;
					}
					grid = new int[n][m];
					if (noise >= 1) {
						for (int y = 0; y < grid.length; y++) {
							for (int x = 0; x < grid[0].length; x++) {
								if (noise == 1) {
									if (rand.nextInt(100) == 0) {
										grid[y][x] = 9;
									}
								} else if (noise == 2) {
									if (rand.nextInt(25) == 0) {
										grid[y][x] = 9;
									}
								}
							}
						}
					}
					
					//
					// playSnakeGame(n, m, grid);
					//
					
//					int empty = n * m;
//
//					while (empty > 0) {
//						grid[move[1]][move[0]] = 1;
//						empty--;
//						moveCounter++;
//					}
//					if (moveCounter - last_respawn > longest) {
//						longest = moveCounter - last_respawn;
//					}
//
//					double area_not_longest = (n * m - longest) * 100.0 / (n * m);
//					if (noise == 2) {
//						if (type == 0) {
//							s1.add(area_not_longest);
//						} else if (type == 1) {
//							s2.add(area_not_longest);
//						}
//					}
//					System.out.print(padRight(empty + " / " + (n * m), 16));
//					System.out.print(padRight(respawns + "", 7));
//					System.out.print(padRight(String.format("%.3f%%", (empty * 100.0 / (n * m))), 10));
//					System.out.print(padRight(String.format("%.3f%%", area_not_longest), 10));
//					System.out.print(padRight(String.format("%.3f%%", (empty * 100.0 / (n * m - longest))), 10));
//					System.out.println();
				}
			}
		}
		System.out.println();
		System.out.println("Type1 area_not_longest: " + getStats(s1));
		System.out.println("Type2 area_not_longest: " + getStats(s2));
	}

	public static String getStats(ArrayList<Double> a) {
		double mean = 0;
		for (double i : a) {
			mean += i;
		}
		mean /= a.size();

		double stdev = 0;
		for (double i : a) {
			stdev += (i - mean)*(i - mean);
		}
		stdev = Math.sqrt(stdev/a.size());
		
		return padRight(String.format("%.3f", mean), 10) + padRight(String.format("%.3f", stdev), 10);
	}

	public static String padRight(String s, int n) {
		return String.format("%1$-" + n + "s", s);
	}

	public static String padLeft(String s, int n) {
		return String.format("%1$" + n + "s", s);
	}

	public static String printMap(int[] head, int[] tail) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (head[0] == j && head[1] == i) {
					sb.append('@');
				} else if (tail[0] == j && tail[1] == i) {
					sb.append('O');
				} else { 
					sb.append(grid[i][j] == 0 ? ' ' : (grid[i][j] == 9 ? '#' : 'x'));
				}
			}
			sb.append("|\n");
		}
		for (int j = 0; j < grid[0].length; j++) {
			sb.append('-');
		}
		sb.append("/\n");
		return sb.toString();
	}

	public static void arrayCopy(final int[] source, int[] dest) {
		for (int i = 0; i < source.length; i++) {
			dest[i] = source[i];
		}
	}

	public static int[] getMove(TreeSet<Pair> open, int[] pos, int g) {
		/**
		 * Try to follow gravity, else hug the wall, return null if no moves available.
		 */
		int[] move = new int[3];
		int ne = numEdges(pos[0], pos[1]);
		if (ne == 0) {
			return null;
		} else if (ne == 1) {
			ArrayList<int[]> head_moves = getMoves2(pos[0], pos[1]);
			move = head_moves.get(0);
		} else if (g == -1) {
			ArrayList<int[]> head_moves = getMoves2(pos[0], pos[1]);
			int min = 11;
			int minIdx = 10;
			int headingMin = 10;
			int headingIdx = 10;
			for (int i = 0; i < head_moves.size(); i++) {
				ne = numEdges(head_moves.get(i)[0], head_moves.get(i)[1]);
				if (ne < 1) {
					head_moves.remove(i--);
				} else {
					if (ne < min) {
						min = ne;
						minIdx = i;
					}
					if (head_moves.get(i)[2] == pos[2]) {
						headingMin = ne;
						headingIdx = i;
					}
				}
			}
			if (min == 11) {
				head_moves = getMoves2(pos[0], pos[1]);
				move = head_moves.get(0);
			} else {
				if (min == headingMin) {
					minIdx = headingIdx;
				}
				move[0] = head_moves.get(minIdx)[0];
				move[1] = head_moves.get(minIdx)[1];
				move[2] = head_moves.get(minIdx)[2];
			}
		} else {
			ArrayList<int[]> head_moves = getMoves2(pos[0], pos[1]);

			int max = 0;
			for (int i = 0; i < head_moves.size(); i++) {
				ne = numEdges(head_moves.get(i)[0], head_moves.get(i)[1]);
				if (ne < 1) {
					head_moves.remove(i--);
				}
				max = Math.max(max, ne);
			}
			if (head_moves.isEmpty()) {
				return null;
			}

			Collections.sort(head_moves, gravitySort[g]);
			int[] best = head_moves.get(0);
			if (head_moves.size() == 1) {
				return best;
			} else if (isInflectionPoint(pos)) {
				return tunnelPick(pos, g);
			} else {
				return best;
			}
			
		}
		return move;
	}
	
	public static boolean isInflectionPoint(final int[] pos) {
		final int[] dy2 = {1, 1, 1, 0, -1, -1, -1, 0};
		final int[] dx2 = {1, 0, -1, -1, -1, 0, 1, 1};
		final boolean[] round = new boolean[8];
		for (int i = 0; i < round.length; i++) {
			int x = pos[0] + dx2[i];
			int y = pos[1] + dy2[i];
			if (x < 0 || x >= grid[0].length || y < 0 || y >= grid.length || grid[y][x] != 0) {
				round[i] = false;
			} else {
				round[i] = true;
			}
		}
		for (int i = 1; i < round.length; i+=2) {
			if (!round[(i) % 8] && !round[(i + 4) % 8]) {
				return true;
			}
		}
		for (int i = 0; i < round.length; i++) {
			if (!round[(i) % 8] && (!round[(i + 2) % 8] || !round[(i + 5) % 8])) {
				return true;
			}
		}
		return false;
	}
	
	public static int[] tunnelPick(int[] pos, int g) {
		int limit = 25;
	
		ArrayList<int[]> moves = getMoves2(pos[0], pos[1]);
		ArrayList<int[]> best = new ArrayList<>();
		int max = 0;
		for (int i = 0; i < moves.size(); i++) {
			int len = 0;
			len = bfs(moves.get(i), limit);
			best.add(new int[]{len, i});
		}
		Collections.sort(best, (a, b) -> Integer.compare(b[0], a[0]));
		ArrayList<int[]> moves2 = new ArrayList<>();
		for (int i = 0; i < best.size(); i++) {
			if (best.get(i)[0] == (best.get(0)[0])) {
				moves2.add(moves.get(best.get(i)[1]));
			}
		}
		
		Collections.sort(moves2, gravitySort[g]);
		return moves2.get(0);
	}
	
	public static final class Node {
		private static HashMap<Integer, Node> cache = new HashMap<>();
		
		public int x;
		public int y;
		public int hc;

		private Node(int x2, int y2) {
			x = x2;
			y = y2;
			hc = hashCode(x, y);
		}

		public static Node getNode(int x, int y) {
			Integer hash = hashCode(x, y);
			if (!cache.containsKey(hash)) {
				Node n = new Node(x, y);
				cache.put(hash, n);
				return n;
			} else {
				return cache.get(hash);
			}
		}
		
		public static int hashCode(int x, int y) {
			final int prime = 10007;
			int result = 1;
			result = prime * result + x;
			result = prime * result + y;
			return result;
		}
		
		@Override
		public int hashCode() {
			return hc;
		}

		@Override
		public boolean equals(Object obj) {
			return this == obj;
		}
	}
	
	private static int bfs(int[] pos, int limit) {
		Queue<Node> q = new LinkedList<Node>();
		int c = 0;
		HashSet<Node> visited = new HashSet<Node>(limit);
		
		Node top = null;
		q.add(Node.getNode(pos[0], pos[1]));
		
		while (!q.isEmpty() && c < limit) {
			top = q.poll();
			if (visited.contains(top)) {
				continue;
			}
			final int ny = top.y;
			final int nx = top.x;
			if (!(ny >= 0 && ny < grid.length && nx >= 0 && nx < grid[0].length && grid[ny][nx] == 0)) {
				continue;
			}
			visited.add(top);
			c++;
			ArrayList<int[]> moves = getMoves2(top.x, top.y);
			for (int[] p2: moves) {
				q.add(Node.getNode(p2[0], p2[1]));
			}
		}
		return c;
	}

	public static int[] tunnelPick_old(int[] pos, int g) {
		Queue<int[]> q = new LinkedList<int[]>();
		q.add(pos);
		int limit = 30;
		int c = 0;
		
		ArrayList<int[]> primordial_moves = getMoves2(pos[0], pos[1]);
		HashSet<List<Integer>> visited = new HashSet<List<Integer>>();
		int[] p = null;
		for (int i = 0; i < primordial_moves.size(); i++) {
			p = primordial_moves.get(i);
			q.add(new int[]{p[0], p[1], p[2], i});
		}
		int[] top = null;
		int[] last = new int[4];
		int[] tunnel_length = new int[4];
		
		while (!q.isEmpty() && c < limit) {
			top = q.poll();
			final int ny = top[1];
			final int nx = top[0];
			if (!(ny >= 0 && ny < grid.length && nx >= 0 && nx < grid[0].length && grid[ny][nx] == 0)) {
				continue;
			}
			ArrayList<Integer> li = new ArrayList<>();
			for (int i = 0; i < top.length; i++) {
				li.add(top[i]);
			}
			if (visited.contains(li)) {
				continue;
			}
			arrayCopy(top, last);
			visited.add(li);
			c++;
			tunnel_length[top[3]]++;
			ArrayList<int[]> moves = getMoves2(top[0], top[1]);
			for (int[] p2: moves) {
				q.add(new int[]{p2[0], p2[1], p2[2], top[3]});
			}
		}
		
		if (q.isEmpty()) {
			// Pick longest tunnel
			int max = 0;
			int maxIdx = 0;
			for (int i = 0; i < tunnel_length.length; i++) {
				if (tunnel_length[i] > max) {
					max = tunnel_length[i];
					maxIdx = i;
				}
			}
			return primordial_moves.get(maxIdx);
		} else {
			// Sort all starting positions left according to G
			boolean[] valid = new boolean[4];
			for (int[] e: q) {
				valid[e[3]] = true;				
			}

			int max = 0;
			for (int i = 0; i < tunnel_length.length; i++) {
				if (valid[i] && tunnel_length[i] > max) {
					max = tunnel_length[i];
				}
			}
			
			for (int i = 0; i < tunnel_length.length; i++) {
				if (valid[i] && max > 3 * tunnel_length[i]) {
					valid[i] = false;
				}
			}
			
			ArrayList<int[]> moves = new ArrayList<>();
			for (int i = 0; i < primordial_moves.size(); i++) {
				if (valid[i]) {
					moves.add(primordial_moves.get(i));
				}
			}
			Collections.sort(moves, gravitySort[g]);
			return moves.get(0);
		}
	}

	public static GravitySort[] gravitySort = {new GravitySort(0), new GravitySort(1), new GravitySort(2), new GravitySort(3)};
	public static int[][] grid;
	
	public static class GravitySort implements Comparator<int[]> {
		private int g;
		public GravitySort(int g) {
			this.g = g;
		}
	    public int compare(int[] o1, int[] o2) {
	    	int d1 = Math.min(Math.max(o1[2], g) - Math.min(o1[2], g), 4 + Math.min(o1[2], g) - Math.max(o1[2], g));
	    	int d2 = Math.min(Math.max(o2[2], g) - Math.min(o2[2], g), 4 + Math.min(o2[2], g) - Math.max(o2[2], g));
	        return Integer.compare(d1, d2);
	    }
	}

	public static int[] respawn(TreeSet<Pair> open) {
		final Pair target = new Pair(grid[0].length / 2, 0);
		//final Pair target = new Pair(77, 0);
		
		Pair move = open.ceiling(target);
		if (move == null) {
			move = open.floor(target);
		}
		while (open.size() > 0 && numEdges(move.first, move.second) == 0) {
			open.remove(move);
			move = open.ceiling(target);
			if (move == null) {
				move = open.floor(target);
			}
		}
		if (open.isEmpty()) {
			move = null;
			return null;
		} else {
			open.remove(move);
			return new int[] { move.first, move.second, -1 };
		}
	}

	public static int numEdges(final int px, final int py) {
		int num = 0;
		for (int i = 0; i < dy.length; i++) {
			final int ny = py + dy[i];
			final int nx = px + dx[i];
			if (ny >= 0 && ny < grid.length && nx >= 0 && nx < grid[0].length
					&& grid[ny][nx] == 0) {
				num++;
			}
		}
		return num;
	}

	public static ArrayList<int[]> moveStraight(final int[][] grid,
			final int px, final int py, final int heading) {
		ArrayList<int[]> moves = new ArrayList<>();
		for (int i = 0; i < dy.length; i++) {
			final int ny = py + dy[i];
			final int nx = px + dx[i];
			if (ny >= 0 && ny < grid.length && nx >= 0 && nx < grid[0].length
					&& grid[ny][nx] == 0 && i == heading) {
				moves.add(new int[] { nx, ny, i });
			}
		}
		return moves;
	}

	public static ArrayList<int[]> getMoves2(final int px, final int py) {
		ArrayList<int[]> moves = new ArrayList<>();
		for (int i = 0; i < dy.length; i++) {
			final int ny = py + dy[i];
			final int nx = px + dx[i];
			if (ny >= 0 && ny < grid.length && nx >= 0 && nx < grid[0].length && grid[ny][nx] == 0) {
				moves.add(new int[] { nx, ny, i });
			}
		}
		return moves;
	}

	public static int[][] getMoves(final int px, final int py) {
		int[][] moves = new int[5][3];
		int k = 0;
		for (int i = 0; i < dy.length; i++) {
			final int ny = py + dy[i];
			final int nx = px + dx[i];
			if (ny >= 0 && ny < grid.length && nx >= 0 && nx < grid[0].length && grid[ny][nx] == 0) {
				moves[k][0] = nx;
				moves[k][1] = ny;
				moves[k++][2] = i;
			}
		}
		moves[k][0] = -1;
		return moves;
	}

	// S E N W
	public static final int[] dx = { 0, 1, 0, -1 };
	public static final int[] dy = { 1, 0, -1, 0 };
	enum State {Cut, Fill, Hug, PostCut, FillRem}; 

	public static class Pair implements Comparable<Pair> {
		public int first;
		public int second;

		public Pair(int first, int second) {
			super();
			this.first = first;
			this.second = second;
		}

		public int hashCode() {
			int hashFirst = Integer.hashCode(first);
			int hashSecond = Integer.hashCode(second);

			return (hashFirst + hashSecond) * hashSecond + hashFirst;
		}

		public boolean equals(Object other) {
			if (other instanceof Pair) {
				@SuppressWarnings("unchecked")
				Pair otherPair = (Pair) other;
				return ((this.first == otherPair.first && this.second == otherPair.second));
			}

			return false;
		}

		public String toString() {
			return "(" + first + ", " + second + ")";
		}

		@Override
		public int compareTo(Pair o) {
			if (this.first == o.first) {
				return Integer.compare(this.second, o.second);
			} else {
				return Integer.compare(this.first, o.first);
			}
		}
	}

	public static int eulerTotientFunction(int n) {
		int result = n;
		for (int i = 2; i * i <= n; i++) {
			if (n % i == 0) {
				result -= result / i;
			}
			while (n % i == 0) {
				n /= i;
			}
		}

		if (n > 1) {
			result -= result / n;
		}
		return result;
	}
	
	public static class SegmentTree {
		private long[][][] t;
		private long[][] a;
		private int N;
		private int n;

		/**
		 * A segment tree can perform a binary associative function (like sum, min, max) over an interval in O(log n) time. 
		 * Updates are relatively slow, O(log n)
		 * 
		 * t[width == N][height == n] is essentially a binary tree structure with the root at t[0][n], children at t[0][n-1] and t[N/2][n-1], and so forth. 
		 */

		/**
		 * This function can be any associative binary function. For example sum, min, max, bitwise and, gcd. 
		 */
		protected long[] function(long[] a, long[] b) {
			long[] dest = new long[3];
			dest[0] = (a[0] + b[0] + a[0] * b[2] + a[1] * b[0]) % mod;
			dest[1] = (a[1] + b[1] + a[0] * b[0] + a[1] * b[1]) % mod;
			dest[2] = (a[2] + b[2] + a[2] * b[2] + a[0] * b[0]) % mod;
			return dest;
		}

		/**
		 * The value of IDENTITY should be such that f(IDENTITY, x) = x, e.g. 0 for sum, +infinity for min, -infinity for max, and 0 for gcd.
		 */
		protected long[] IDENTITY = {0, 0, 0};

		public SegmentTree(long[][] b) {
			n = (int) (Math.log10(b.length)/Math.log10(2))+1;
			N = 1 << n;
			this.a = new long[N][3];
			for (int i = 0; i < b.length; i++) {
				this.a[i] = b[i];
			}
			t = new long[N][n+1][3];
			for (int x = 0; x < N; x++) {
				t[x][0] = a[x];
			}
			for (int y = 1; y <= n; y++) {
				for (int x = 0; x < N; x+=(1<<y)) {
					t[x][y] = function(t[x][y-1], t[x+(1<<(y-1))][y-1]);
				}
			}
		} 

		/**
		 * Set position i to v. Time: O(log n)
		 */
		public void set(int i, long[] v) {
			t[i][0] = a[i] = v;
			for (int y = 1; y <= n; y++) {
				int x = i-(i&((1<<y)-1));
				t[x][y] = function(t[x][y-1], t[x+(1<<(y-1))][y-1]);
			}
		}

		/**
		 * Get the function over the interval [a, b]. Time: O(log n)
		 */
		public long[] get(int i, int j) {
			long[] res = Arrays.copyOf(IDENTITY, IDENTITY.length);
			int height = 0; j++;
			while (i+(1<<height) <= j) {
				while ((i&((1<<(height+1))-1)) == 0 && i+(1<<(height+1)) <= j) height++;
				res = function(res, t[i][height]);
				i += (1<<height);
			}
			while (i < j) {
				while (i+(1<<height) > j) height--;
				res = function(res, t[i][height]);
				i += (1<<height);
			}
			return res;
		}
	}

	public static int FIBQ_fast(int[] a, int l, int r, long[] fib_prev,
			long[] fib, long[] luc) {
		long lsum = 2;
		long fsum = 0;
		long fsum_prev = 0;

		for (int i = 0; i < r - l + 1; i++) {
			fsum_prev = fsum;
			// System.out.println(lsum);
			fsum = (fsum + (fib_prev[l + i] * fsum) + ((fib[l + i])
					* (fsum + lsum) % mod)
					* inv_2)
					% mod;
			lsum = (lsum + (luc[l + i] * lsum + 5 * (fib[l + i]) * (fsum_prev)
					% mod)
					% mod * inv_2)
					% mod;

			// fsum = (fsum + (fib[l + i] * lsum % mod + luc[l + i] * fsum %
			// mod) * inv_2) % mod;
			// lsum = (lsum + (luc[l + i] * lsum + 5 * (fib[l + i]) *
			// (fsum_prev) % mod) % mod * inv_2) % mod;
		}

		return (int) fsum;
	}

	public static int FIBQ_slow(int[] a, int l, int r) {
		int sum = 0;

		int N = 1 << (r - l + 1);
		for (int b = 1; b < N; b++) {
			long fibsum = 0;
			for (int j = 0; j < (r - l + 1); j++) {
				if (((1 << j) & b) != 0) {
					fibsum += a[l + j];
				}
			}
			sum = (int) ((sum + fib(fibsum)) % mod);
		}
		return sum;
	}

	public static void buildArray(long[] fib_prev, long[] fib, long[] luc, SegmentTree st, int i, int[] a) {
		fib_prev[i] = fib(a[i] - 1);
		fib[i] = fib(a[i]);
		st.set(i, new long[]{fib[i], fib_prev[i], (fib_prev[i] + fib[i]) % mod}); 
	}

	public static void FIBQ(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		int m = scan.nextInt();
		int[] a = scan.nextIntArray(n);

		int[][] q = new int[m][3];
		for (int t = 0; t < m; t++) {
			char q1 = scan.next().toCharArray()[0];
			q[t][0] = q1;
			if (q1 == 'C') {
				int x = scan.nextInt() - 1;
				int y = scan.nextInt();
				q[t][1] = x;
				q[t][2] = y;
			} else {
				int l = scan.nextInt() - 1;
				int r = scan.nextInt() - 1;
				q[t][1] = l;
				q[t][2] = r;
			}
		}

		get_FIBQ_fast(a, q);
	}

	// public static long mod = 1007;
	// static long inv_2 = fastModularExponent(2, eulerTotientFunction( (int)mod
	// ) - 1, (int) mod);
	public static final long mod = 1000000007;
	public static final long inv_2 = 500000004;

	public static void test_FIBQ2() {

		int[] a = { 3 ,4, 1, 2 };
		// int[] a = new int[100];
		// for (int i = 0; i < a.length; i++) {
		// a[i] = 1;
		// }
		long[] fib_prev = new long[a.length];
		long[] fib = new long[a.length];
		long[] luc = new long[a.length];

		for (int i = 0; i < luc.length; i++) {
			buildArray(fib_prev, fib, luc, null, i, a);
		}
		
		System.out.println(" " + FIBQ_fast(a, 0, 1, fib_prev, fib, luc));
		System.out.println(" " + FIBQ_fast(a, 2, 3, fib_prev, fib, luc));

		for (int i = 1; i < 3; i++) {
//			a[3] = i;
//			buildArray(fib_prev, fib, luc, 3, a);
			System.out.println(Arrays.toString(a));
			for (int j = 0; j < luc.length; j++) {
				System.out.print(" " + FIBQ_fast(a, 0, j, fib_prev, fib, luc));
			}
			System.out.println("\n");
		}

		a = new int[] { 1, 4, 1, 1, 1, 1, 1 };
		fib_prev = new long[a.length];
		fib = new long[a.length];
		luc = new long[a.length];

		for (int i = 0; i < luc.length; i++) {
			buildArray(fib_prev, fib, luc, null, i, a);
		}

		for (int i = 1; i < 3; i++) {
			a[3] = i;
			buildArray(fib_prev, fib, luc, null, 3, a);
			System.out.println(Arrays.toString(a));
			for (int j = 0; j < luc.length; j++) {
				System.out.print(" " + FIBQ_fast(a, 0, j, fib_prev, fib, luc));
			}
			System.out.println("\n");
		}
	}

	public static void test_FIBQ4() {
		Random rand = new Random(1);
		int max_a = 1000000000;
		long t1 = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			fastModularExponent(2,
					eulerTotientFunction(2 + rand.nextInt(max_a)) - 1,
					(int) mod);
		}
		System.out.println(System.currentTimeMillis() - t1);
	}

	public static void test_FIBQ3() {
		Random rand = new Random(1);
		int max_a = 1000000000;
		for (int i = 0; i < 4; i++) {
			long t1 = System.currentTimeMillis();
			int[] a = new int[50000];
			int[][] b = new int[50000][3];
			for (int j = 0; j < a.length; j++) {
				a[j] = 1 + rand.nextInt(max_a);
				b[j][0] = rand.nextBoolean() ? 'C' : 'Q';
				if (b[j][0] == 'C') {
					b[j][1] = rand.nextInt(a.length);
					b[j][2] = 1 + rand.nextInt(max_a);
				} else {
					// b[j][1] = rand.nextInt(a.length);
					// b[j][2] = b[j][1] + rand.nextInt(a.length - b[j][1]);
					b[j][1] = 0;
					b[j][2] = a.length - 1;
				}
			}
			System.out.println("start");
			// a = new int[]{9, 9, 1};
			// b = new int[][]{{81, 0, 2}};
			get_FIBQ_fast(a, b);
			System.out.println(System.currentTimeMillis() - t1);
		}
	}

	public static void test_FIBQ() {
		Random rand = new Random(1);
		int max_a = 1000000000;
		for (int i = 0; i < 10; i++) {
			// System.out.println("\t" + i);
			int[] a = new int[100000];
			int[][] b = new int[100000][3];
			for (int j = 0; j < a.length; j++) {
				a[j] = 1 + rand.nextInt(max_a);
				b[j][0] = rand.nextBoolean() ? 'C' : 'Q';
				if (b[j][0] == 'C') {
					b[j][1] = rand.nextInt(a.length);
					b[j][2] = 1 + rand.nextInt(max_a);
				} else {
					b[j][1] = rand.nextInt(a.length);
					b[j][2] = b[j][1] + rand.nextInt(a.length - b[j][1]);
				}
			}
			// a = new int[]{9, 9, 1};
			// b = new int[][]{{81, 0, 2}};
			long t1 = System.currentTimeMillis();
			get_FIBQ_fast(a, b);
			System.out.println(System.currentTimeMillis() - t1);
		}
	}

	public static void get_FIBQ_fast(int[] a, int[][] q) {
		
		long[][] init = new long[a.length][3];
		
		for (int i = 0; i < init.length; i++) {
			long fm1 = fib(a[i] - 1);
			long f = fib(a[i]);
//			st.set(i, new long[]{f, fm1, (fm1 + f) % mod}); 
			init[i] = new long[]{f, fm1, (f + fm1) % mod};  
		}
		SegmentTree st = new SegmentTree(init);
		StringBuilder sb = new StringBuilder();

		for (int t = 0; t < q.length; t++) {
			if (q[t][0] == 'C') {
				a[q[t][1]] = q[t][2];

				int i = q[t][1];
				long fm1 = fib(a[i] - 1);
				long f = fib(a[i]);
				st.set(i, new long[]{f, fm1, (fm1 + f) % mod}); 
			} else {
				int r1 = (int) st.get(q[t][1], q[t][2])[0];
				if (r1 == -1) {
					System.out.println();
				}
				sb.append(r1);
				sb.append("\n");
//				System.out.println(r1);
//				 int r2 = FIBQ_fast(a, q[t][1], q[t][2], fib_prev, fib, luc);
//				 if (r1 != r2) {
//					 System.out.println("fail");
//					 FIBQ_fast(a, q[t][1], q[t][2], fib_prev, fib, luc);
//					 FIBQ_slow(a, q[t][1], q[t][2]);
//				 }
			}
		}
		System.out.println(sb.toString());

	}

	/*******************************************************************************************************************************
	 * Simple matrix multiplication. O(n^3)
	 */
	public static int[][] multiplyMatrix(int[][] A, int[][] B, int mod) {
		int[][] C = new int[A.length][B[0].length];
		for (int i = 0; i < C.length; i++) {
			for (int j = 0; j < C[0].length; j++) {
				for (int k = 0; k < B.length; k++) {
					C[i][j] = (int) ((C[i][j] + ((long) A[i][k] * B[k][j])) % mod);
				}
			}
		}
		return C;
	}

	/*******************************************************************************************************************************
	 * Fast matrix exponentiation. O(m^2 * log n), where m = A.length
	 */
	public static int[][] fastMatrixExponent(int[][] A, long n, int mod) {
		if (n == 1) {
			return A;
		} else {
			if (n % 2 == 0) {
				int[][] T = fastMatrixExponent(A, n / 2, mod);
				return multiplyMatrix(T, T, mod);
			} else {
				return multiplyMatrix(A, fastMatrixExponent(A, n - 1, mod), mod);
			}
		}
	}

	public static int fib(long n) {
		if (n == 0) {
			return 0;
		}
		int[][] a = { { 1, 1 }, { 1, 0 } };
		return fastMatrixExponent(a, n, (int) mod)[0][1];
	}

	public static int luc(long n) {
		return (int) ((2 * fib(n + 1) - fib(n) + mod) % mod);
	}

	public static void BIPIN3(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int tests = scan.nextInt();
		for (int t = 0; t < tests; t++) {
			int n = scan.nextInt();
			int k = scan.nextInt();
			long ret = ((long) k * (long) fastModularExponent(k - 1, n - 1,
					(int) mod)) % mod;
			System.out.println(ret);
		}
	}

	public static int fastModularExponent(int a, int exp, int mod) {
		long[] results = new long[65];
		long m = mod;
		int power = 1;
		long res = 1;
		while (exp > 0) {
			if (power == 1) {
				results[power] = a % m;
			} else {
				results[power] = (results[power - 1] * results[power - 1]) % m;
			}
			if (exp % 2 == 1) {
				res = (res * results[power]) % m;
			}
			exp /= 2;
			power++;
		}
		return (int) (res % m);
	}

	public static void CHEFPATH(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int tests = scan.nextInt();
		for (int t = 0; t < tests; t++) {
			long[] c = scan.nextLongArray(2);
			Arrays.sort(c);
			long a = c[0];
			long b = c[1];

			boolean valid = false;
			if (a == 1) {
				if (b == 2) {
					valid = true;
				}
			} else {
				if ((a % 2 == 1) && (b % 2 == 1)) {
					valid = false;
				} else {
					valid = true;
				}
			}
			System.out.println((!valid) ? "No" : "Yes");
		}
	}

	public static void COLOR(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int tests = scan.nextInt();
		for (int t = 0; t < tests; t++) {
			scan.nextInt();
			char[] a = scan.next().toCharArray();
			int[] b = new int[a.length];
			for (int i = 0; i < a.length; i++) {
				b[i] = a[i] == 'R' ? 0 : (a[i] == 'G' ? 1 : 2);
			}
			int min = Integer.MAX_VALUE;
			for (int i = 0; i < 3; i++) {
				int sum = 0;
				for (int j = 0; j < b.length; j++) {
					sum += i == b[j] ? 0 : 1;
				}
				min = Math.min(sum, min);
			}
			System.out.println(min);
		}
	}

	public static void CHBLLNS(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int tests = scan.nextInt();
		for (int t = 0; t < tests; t++) {
			long[] a = scan.nextLongArray(3);
			Arrays.sort(a);
			int k = scan.nextInt() - 1;
			long min = 1;
			if (k >= 1) {
				min += Math.min(a[0], k) * 3L;
				k -= a[0];
			}
			if (k > 0) {
				min += Math.min(a[1] - a[0], k) * 2L;
				k -= a[1] - a[0];
			}
			if (k > 0) {
				min += k;
			}
			System.out.println(min);
		}
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

		long[] nextLongArray(int n) {
			long[] a = new long[n];
			for (int i = 0; i < a.length; i++) {
				a[i] = this.nextLong();
			}
			return a;
		}

		int[] nextIntArray(int n) {
			int[] a = new int[n];
			for (int i = 0; i < a.length; i++) {
				a[i] = this.nextInt();
			}
			return a;
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

		String nextLine() {
			String str = "";
			try {
				str = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return str;
		}
	}
}
