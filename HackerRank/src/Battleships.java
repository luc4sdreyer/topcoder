import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.StringTokenizer;

public class Battleships {
	public static void main(String[] args) {
		//battleShips(System.in);
		//test();
		//testGame(new Random());
		//selectMap(new Random());
	}

	//public static final int[] ships = {5, 4, 3, 2, 2, 1, 1};
	public static class GameData {
		boolean[][] grid = new boolean[10][10];
		int[][] pos = new int[ships.length][4];
		int[] hits = new int[ships.length];
		public String desc;
		public GameData() {
			
		}
		public GameData(boolean[][] grid, int[][] pos, int[] hits) {
			super();
			this.grid = grid;
			this.pos = pos;
			this.hits = hits;
		}
		public GameData clone() {
			return new GameData(this.grid.clone(), this.pos.clone(), this.hits.clone());
		}
	}
	public static final int[] ships = {5, 4, 3, 2, 2, 1, 1};

	public static boolean[][] testGame(Random rand) {
		int numGames = 1000;
		int length = 0;
		ArrayList<Pair<Integer, GameData>> time = new ArrayList<>();
		for (int k = 0; k < numGames; k++) {
			GameData data = new GameData();
			placeShips(rand, data);
			int moves = playGame(data, rand);
			length += moves;
			time.add(new Pair<Integer, GameData>(moves, data));
		}
		System.out.println("avg length: " + length/((double)numGames));
		Collections.sort(time);
		Collections.reverse(time);
		while (time.size() > 100) {
			time.remove(time.size()-1);
		}

		for (int j = 0; j < time.size(); j++) {
			time.get(j).first = 0;
		}
		for (int i = 0; i < 100; i++) {
			Random rand2 = new Random(rand.nextLong());
			for (int j = 0; j < time.size(); j++) {
				GameData data = time.get(j).second;
				data.hits = new int[ships.length];
				int moves = playGame(data, rand2);
				time.get(j).first += moves; 
			}
		}
		
		Collections.sort(time);
		Collections.reverse(time);
		for (int i = 0; i < 1; i++) {
			GameData data = time.get(i).second;
			boolean[][] g = data.grid;
			System.out.println(time.get(i).first);
			for (int j = 0; j < g.length; j++) {
				for (int k = 0; k < g[0].length; k++) {
					System.out.print(g[j][k] ? "{}" : "  ");
				}
				System.out.println("|");
			}
			System.out.println();
		}
		return time.get(0).second.grid;
	}
	
	public static GameData selectMap(Random rand) {
		int numGames = 1000;
		int length = 0;
		ArrayList<Pair<Integer, GameData>> time = new ArrayList<>();
		for (int k = 0; k < numGames; k++) {
			GameData data = new GameData();
			placeShips(rand, data);
			int moves = 0;
			for (int i = 0; i < 10; i++) {
				moves += playGame(data.clone(), rand);
			}
			length += moves;
			time.add(new Pair<Integer, GameData>(moves, data));
		}
		//System.out.println("avg length: " + length/((double)numGames));
		Collections.sort(time);
		Collections.reverse(time);
		
//		for (int i = 0; i < 1; i++) {
//			GameData data = time.get(i).second;
//			boolean[][] g = data.grid;
//			System.out.println(time.get(i).first);
//			for (int j = 0; j < g.length; j++) {
//				for (int k = 0; k < g[0].length; k++) {
//					System.out.print(g[j][k] ? "{}" : "  ");
//				}
//				System.out.println("|");
//			}
//			System.out.println();
//		}
		return time.get(0).second;
	}

	private static int playGame(GameData data, Random rand) {		
		char[][] map = new char[10][10];
		boolean[][] grid = data.grid;
		int[] hits = data.hits;
		int[][] pos = data.pos;
		for (int i = 0; i < map.length; i++) {
			 Arrays.fill(map[i], '-');
		}
		int sunk = 0;
		int moves = 0;
		while (true) {
			int[] move = getMove(map, rand);
			//int[] move = getMoveRandom(map, rand);
			if (grid[move[1]][move[0]]) {
				map[move[1]][move[0]] = 'h';
				for (int i = 0; i < pos.length; i++) {
					if (pos[i][0] <= move[0] && move[0] <= pos[i][2] && pos[i][1] <= move[1] && move[1] <= pos[i][3]) {
						hits[i]++;
						if (hits[i] == ships[i]) {
							sunk++;
							for (int y = pos[i][1]; y <= pos[i][3]; y++) {
								for (int x = pos[i][0]; x <= pos[i][2]; x++) {
									map[y][x] = 'd';
								}
							}
						}
					}
				}
			} else {
				map[move[1]][move[0]] = 'm';
			}
			if (sunk >= ships.length) {
				break;
			}
			moves++;
		}
		return moves;
	}

	public static final int[] dx = {1, 0, -1, 0};
	public static final int[] dy = {0, 1, 0, -1};
	
	public static int[] getMove(char[][] map, Random rand) {
		int hits = 0;
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map.length; x++) {
				if (map[y][x] == 'h') {
					hits++;
				}
			}
		}
		
		if (hits > 0) {
			for (int y = 0; y < map.length; y++) {
				for (int x = 0; x < map.length; x++) {
					if (map[y][x] == 'h') {
						for (int dir = 0; dir < 4; dir++) {
							int py = y + dy[dir];
							int px = x + dx[dir];
							if (px >= 0 && px < map.length && py >= 0 && py < map.length && map[py][px] == 'h') {
								py = py + dy[dir];
								px = px + dx[dir];
								if (px >= 0 && px < map.length && py >= 0 && py < map.length && map[py][px] == '-') {
									return new int[]{px, py};
								}
							}
						}
					}
				}
			}
	
			for (int y = 0; y < map.length; y++) {
				for (int x = 0; x < map.length; x++) {
					if (map[y][x] == 'h') {
						for (int dir = 0; dir < 4; dir++) {
							int py = y + dy[dir];
							int px = x + dx[dir];
							if (px >= 0 && px < map.length && py >= 0 && py < map.length && map[py][px] == '-') {
								return new int[]{px, py};
							}
						}
					}
				}
			}
		}
		
		ArrayList<int[]> possible = new ArrayList<>();
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map.length; x++) {
				if ((x + y) % 2 == 0 && map[y][x] == '-') {
					possible.add(new int[]{x, y});
				}
			}
		}
		if (possible.isEmpty()) {
			for (int y = 0; y < map.length; y++) {
				for (int x = 0; x < map.length; x++) {
					if (map[y][x] == '-') {
						possible.add(new int[]{x, y});
					}
				}
			}
		}
		return possible.get(rand.nextInt(possible.size()));
	}

	public static int[] getMoveRandom(char[][] map, Random rand) {		
		ArrayList<int[]> possible = new ArrayList<>();
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map.length; x++) {
				if (map[y][x] == '-') {
					possible.add(new int[]{x, y});
				}
			}
		}
		return possible.get(rand.nextInt(possible.size()));
	}

	public static void test() {
		Random rand = new Random(0);
		for (int i = 0; i < 1000000; i++) {
			GameData data = new GameData();
			placeShips(rand, data);
		}
	}

	public static void battleShips(InputStream in) {
		Random rand = new Random();
		MyScanner scan = new MyScanner(in);
		String s = scan.nextLine();
		while (s != null) {
			if (s.equals("INIT")) {
				System.out.println(selectMap(rand).desc);
			} else if (s.equals("10")) {
				char[][] map = new char[10][10];
				for (int i = 0; i < map.length; i++) {
					map[i] = scan.nextLine().toCharArray();
				}
				int[] move = getMove(map, rand);
				System.out.println(move[1] + " " + move[0]);
			}
			s = scan.nextLine();
		}
		
		scan.close();
	}
	
	public static String placeShips(Random rand, GameData data) {
		ArrayList<String> positions = new ArrayList<>();
		for (int i = 0; i < ships.length; i++) {
			while (true) {
				int px = rand.nextInt(10);
				int py = rand.nextInt(10 - ships[i] + 1);
				int height = ships[i];
				int width = 1;
				int horizontal = rand.nextInt(2);
				if (horizontal == 0) {
					int t = px;
					px = py;
					py = t;
					
					height = 1;
					width = ships[i];
				}
				boolean canPlace = true;
				for (int y = py; y < py + height; y++) {
					for (int x = px; x < px + width; x++) {
						if (data.grid[y][x]) {
							canPlace = false;
						}
					}
				}
				if (canPlace) {
					for (int y = py; y < py + height; y++) {
						for (int x = px; x < px + width; x++) {
							data.grid[y][x] = true;
						}
					}
					String desc = "";
					if (ships[i] > 1) {
						desc = px + " " + py + ":" + (px + width - 1) + " " + (py + height - 1);
					} else {
						desc = px + " " + py;
					}
					positions.add(desc);
					data.pos[i] = new int[]{px, py, (px + width - 1), (py + height - 1)};
					break;
				}
			}
		}
		Collections.reverse(positions);
		String out = "";
		for (int i = 0; i < positions.size(); i++) {
			out += positions.get(i) + "\n";
		}
		int sum = 0;
		for (int y = 0; y < 10; y++) {
			for (int x = 0; x < 10; x++) {
				if (data.grid[y][x]) {
					sum++;
				}
			}
		}
		int shipsum = 0;
		for (int i = 0; i < ships.length; i++) {
			shipsum += ships[i];
		}
		if (sum != shipsum) {
			System.out.println("fail");
		}
		data.desc = out;
		return out;
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
