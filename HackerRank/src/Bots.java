import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;
import java.util.StringTokenizer;

public class Bots {
	public static void main(String[] args) {
//		packman(System.in);
//		botSavesPrincess(System.in);
//		botClean(System.in);
//		botCleanLarge(System.in);
		testBotCleanLarge();
//		nPuzzle(System.in);
	}

	public static void nPuzzle(InputStream in) {
		MyScanner scan = new MyScanner(in);
		
		int sy = scan.nextInt();
		int sx = sy;
		Pair<Integer, Integer> start = null;
		int[][] map = new int[sy][sx];
		for (int i = 0; i < sy; i++) {
			for (int j = 0; j < sx; j++) {
				map[i][j] = scan.nextInt();
				if (map[i][j] == 0) {
					start = new Pair<>(j, i);
				}
			}
		}
		nPuzzleAStar(start, sx, sy, map);
	}
	
	public static class PuzzleNode implements Comparable<PuzzleNode> {
		private int food;
		private int x;
		private int y;
		private int v;
		private int steps;
		private PuzzleNode parent;
		private String direction;
		private int[][] state;
		private int hashCode;
		private int heuristic;
		
		public PuzzleNode(int x, int y, int v, int steps, PuzzleNode parent, final int[][] state, String direction) {
			super();
			this.x = x;
			this.y = y;
			this.parent = parent;
			this.steps = steps;
			this.state = state;
			this.direction = direction;

	        int result = 1;
	        for (int[] i: state)
	        	for (int j: i)
	        		result = 31 * result + j;
			this.hashCode = result;
			
			this.heuristic = 0;
			for (int Y = 0; Y < state.length; Y++) {
				for (int X = 0; X < state.length; X++) {
					int tY = state[Y][X] / state.length;
					int tX = state[Y][X] % state.length;
					this.heuristic += Math.abs(tY - Y) + Math.abs(tX - X);
				}
			}
			
			this.v = this.steps + this.heuristic;
		}
		
		public int[][] cloneState() {
			int[][] newState = new int[state.length][];
			for (int i = 0; i < newState.length; i++) {
				newState[i] = Arrays.copyOf(state[i], state[i].length);
			}
			return newState;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			PuzzleNode other = (PuzzleNode) obj;
			for (int i = 0; i < state.length; i++) {
				for (int j = 0; j < state.length; j++) {
					if (this.state[i][j] != other.state[i][j]) {
						return false;
					}
				}
			}
			return true;
		}

		@Override
		public int hashCode() {
			return hashCode;
		}

		@Override
		public String toString() {
			String s = "";
			for (int i = 0; i < state.length; i++) {
				for (int j = 0; j < state.length; j++) {
					s += state[i][j] + " ";
				}
				s += "\n";
			}
			return s;
		}

		@Override
		public int compareTo(PuzzleNode o) {
			return Integer.compare(this.v, o.v);
		}
	}
	
	public static void nPuzzleAStar(Pair<Integer, Integer> start, int sx, int sy, int[][] map) {
		PuzzleNode top = new PuzzleNode(start.first, start.second, 0, 0, null, map, "");
		PriorityQueue<PuzzleNode> queue = new PriorityQueue<>();
		queue.add(top);
		HashSet<PuzzleNode> visited = new HashSet<>();
		while (!queue.isEmpty()) {
			top = queue.poll();
			if (visited.contains(top)) {
				continue;
			}
			visited.add(top);
			
			if (top.heuristic == 0) {
				break;
			}
			
			for (int i = 0; i < dx.length; i++) {
				int nx = top.x + dx[i];
				int ny = top.y + dy[i];
				
				if ((nx >= 0 && nx < sx && ny >= 0 && ny < sy)) {
					int[][] ns = top.cloneState();
					ns[top.y][top.x] = ns[ny][nx];
					ns[ny][nx] = 0;
					
					PuzzleNode next = new PuzzleNode(nx, ny, top.v, top.steps+1, top, ns, directions[i]);
					queue.add(next);
				}
			}
		}
		
		if (top.heuristic != 0) {
			System.out.println("Failed! " + top.heuristic);
		}
		
		PuzzleNode current = top;
		ArrayList<PuzzleNode> path = new ArrayList<>();
		while (current != null) {
			path.add(current);
			current = current.parent;
		}
		
		Collections.reverse(path);
		
		System.out.println(path.size()-1);
		for (int i = 1; i < path.size(); i++) {
			System.out.println(path.get(i).direction);
//			System.out.println(path.get(i));
		}

		return;
	}
	
	public static void testBotCleanLarge() {
		int sizeX = 20;
		int sizeY = 10;
		int numDirt = 20;
		Random rand = new Random(9);
		
		for (int t = 0; t < 100000; t++) {
			System.out.println(t);
			char[][] map = new char[sizeY][sizeX];
			ArrayList<int[]> dirt = new ArrayList<>();
			for (int i = 0; i < map.length; i++) {
				for (int j = 0; j < map[0].length; j++) {
					map[i][j] = '-';
					dirt.add(new int[]{j, i});
				}
			}
			
			int[] bot = {rand.nextInt(sizeX), rand.nextInt(sizeY)};
			map[bot[1]][bot[0]] = 'b';
			
			Collections.shuffle(dirt, rand);
			for (int i = 0; i < numDirt; i++) {
				map[dirt.get(i)[1]][dirt.get(i)[0]] = 'd';
			}
			int cleaned = 0;
			
			while (cleaned < numDirt) {
				Pair<Integer, Integer> start = new Pair<>(bot[0], bot[1]);
				String res = botCleanLargeSmart(start, map[0].length, map.length, map, rand, 10);
	//			System.out.println(res);
				if (res == "CLEAN") {
					if (map[bot[1]][bot[0]] == 'd') {
						map[bot[1]][bot[0]] = 'b';
						cleaned++;
					} else {
						System.err.println("Invalid move");
					}
				} else {
					map[bot[1]][bot[0]] = '-';
					for (int i = 0; i < dx.length; i++) {
						if (res.equals(directions[i])) {
							bot[0] += dx[i];
							bot[1] += dy[i];
							break;
						}
					}
					if (map[bot[1]][bot[0]] != 'd') {
						map[bot[1]][bot[0]] = 'b';
					}
				}
				
//				char[][] m = new char[map.length][map[0].length];
//				for (int y = 0; y < map.length; y++) {
//					for (int x = 0; x < map[0].length; x++) {
//						m[y][x] = map[y][x];
//					}
//				}
//				for (int y = 0; y < map.length; y++) {
//					for (int x = 0; x < map[0].length; x++) {
//						System.out.print(m[y][x]);
//					}
//					System.out.println("|");
//				}
//				System.out.println();
			}
		}
	}

	public static final boolean debug = true;
	
	/**
	 * This problem was to find the shortest path visiting all "dirty" cells on a 2D gird.
	 * This is essentially the TSP without having to return, or the shortest Hamiltonian path.
	 * The number of dirty cells are between 9 and 45. The problem is NP-hard. 
	 * 
	 * First I tried a greedy solution that picks the closest path and breaks ties randomly,
	 * it got to within about 20% of the (theorized) shortest path on the big map. At the end of 
	 * the search time (3 seconds) the algorithm would start considering paths that are
	 * second closest, again picking a random one for the set of options. 
	 * 
	 * Then I tried A*, it turned out to search about half of all paths, so the runtime
	 * on the big map will be around 2^35 seconds.
	 * 
	 * Then I tried simulated annealing, but the shortest path on the big map was about double
	 * the shortest path.
	 * 
	 * I revisited the greedy solution and added a 2% chance per jump to consider paths that are
	 * second closest, picking a random one for the set of options. This was the secret sauce that
	 * got me to the top of the leader board.
	 * 
	 * The next step is to try Branch and Bound (increasingly constraining the lower bound of possible
	 * solutions reminds me of Alpha-Beta pruning): http://lcm.csa.iisc.ernet.in/dsa/node187.html
	 */
	public static void botCleanLarge(InputStream in) {
		MyScanner scan = new MyScanner(in);
		Random rand = new Random(1);
		
		String s = scan.next();
		while (s != null) {
			int py = Integer.parseInt(s);
			int px = scan.nextInt();
			int sy = scan.nextInt();
			int sx = scan.nextInt();
			char[][] map = new char[sy][sx];
			for (int i = 0; i < map.length; i++) {
				map[i] = scan.nextLine().toCharArray();
			}
			
			Pair<Integer, Integer> start = new Pair<>(px, py);
			System.out.println(botCleanLargeSmart(start, map[0].length, map.length, map, rand, 300000));
			s = scan.next();
		}
	}
	
	public static class FoodComp implements Comparator<Food>{
		public Food ref;
		public FoodComp(Food ref) {
			this.ref = ref;
		}
		@Override
		public int compare(Food o1, Food o2) {
			int d1 = Math.abs(o1.x - ref.x) + Math.abs(o1.y - ref.y);
			int d2 = Math.abs(o2.x - ref.x) + Math.abs(o2.y - ref.y);
			return Integer.compare(d1, d2);
		}
	}
	
	public static long setBit(final long x, final int i) {
		return (x | (1 << i));
	}
	
	public static boolean getBit(final long x, final int i) {
		return (x & (1 << i)) != 0;
	}
	
	public static String botCleanLargeSmart(Pair<Integer, Integer> start, final int sx, final int sy, final char[][] map, Random rand, long timeLimit) {		
		if (map[start.second][start.first] == 'd') {
			return "CLEAN";
		}
		
//		int[][] food = new int[sy*sx][2];
//		int k = 0;
//		food[k++] = new int[]{start.first, start.second};
//		
//		for (int i = 0; i < map.length; i++) {
//			for (int j = 0; j < map[0].length; j++) {
//				if (map[i][j] == 'd') {
//					food[k++] = new int[]{j, i};
//				}
//			}
//		}
//		if (k > 40) {
//			return "nope";
//		}
		
		
		
		long astarTime = 200;
		int[] move = null;
//		try {
//			move = botCleanAStar(start, sx, sy, map, rand, astarTime);
//		} catch (Exception ex) {
//			
//		}
		
		int[] move2 = botCleanRandomSearch(start, sx, sy, map, rand, timeLimit - astarTime);
		if (move == null) {
			move = move2;
		}
		
//		int[] move3 = botCleanSimulatedAnnealing(start, sx, sy, map, rand, 100);
//		if (move == null) {
//			move = move3;
//		}
		
		return directions[getHeading(start.first, start.second, move[0], move[1])];
	}
	
	public static int[] botCleanAStar(Pair<Integer, Integer> start, final int sx, final int sy, final char[][] map, Random rand, long timeLimit) {
		long t1 = System.currentTimeMillis();
		int[][] food = new int[sy*sx][2];
		
		int k = 0;
		food[k++] = new int[]{start.first, start.second};
		
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				if (map[i][j] == 'd') {
					food[k++] = new int[]{j, i};
				}
			}
		}
		food = Arrays.copyOf(food, k);
		int[][] fdist = new int[k][k];
		for (int i = 0; i < food.length; i++) {
			for (int j = 0; j < food.length; j++) {
				fdist[i][j] = Math.abs(food[i][0] - food[j][0]) + Math.abs(food[i][1] - food[j][1]); 
				fdist[j][i] = Math.abs(food[i][0] - food[j][0]) + Math.abs(food[i][1] - food[j][1]);
			}
		}
		
		ArrayList<ArrayList<Food>> fclosest = new ArrayList<>();
		for (int i = 0; i < food.length; i++) {
			ArrayList<Food> nf = new ArrayList<>();
			for (int j = 1; j < food.length; j++) {
				if (i != j) {
					int dist = Math.abs(food[i][0] - food[j][0]) + Math.abs(food[i][1] - food[j][1]);
					nf.add(new Food(j, food[j][0], food[j][1], dist));
				}
			}
			Collections.sort(nf);
			fclosest.add(nf);
		}
		
		PriorityQueue<Node4> queue = new PriorityQueue<>();
		Node4 top = new Node4(0, start.first, start.second, 0, 1, null, 0, 1);
		queue.add(top);
		HashSet<Node4> visited = new HashSet<>();
		int visit = 0;

		while (!queue.isEmpty() && System.currentTimeMillis() - t1 < timeLimit) {
			top = queue.poll();
			if (visited.contains(top)) {
				continue;
			}
			visit++;
			visited.add(top);
			if (top.steps == food.length) {
				break;
			} 
			for (int i = 0; i < food.length; i++) {
				if (!getBit(top.state, i)) {
					Node4 next = new Node4(i, food[i][0], food[i][1], top.travelled + fdist[top.i][i], setBit(top.state, i), top, 0, top.steps+1);
					// total min dist might be good enough
					int minDist = Integer.MAX_VALUE;
					final ArrayList<Food> nf = fclosest.get(i);
					for (int j = 0; j < nf.size(); j++) {
						if (!getBit(next.state, nf.get(j).i)) {
							minDist = nf.get(j).dist;
							break;
						}
					}
					if (minDist == Integer.MAX_VALUE && next.steps != food.length) {
						System.err.println("SHOULD NEVER HAPPEN");
					}
							
					next.v = next.travelled + minDist;
					queue.add(next);
				}
			}
		}
		
		if (top.steps < food.length) {
			// time out!
			if (debug) {
				System.out.println("time out!");
			}
			return null;
		}
		
		Node4 current = top;
		ArrayList<Node4> path = new ArrayList<>();
		while (current != null) {
			path.add(current);
			current = current.parent;
		}
		Collections.reverse(path);

		if (debug) {
			System.out.println("A* found shortest path: " + top.travelled + "\t visited: " + visit);
			System.out.println("time: " + (System.currentTimeMillis() - t1));
			System.out.println();
		}
		return new int[]{path.get(1).x, path.get(1).y};
	}
	
	public static int[] botCleanSimulatedAnnealing(Pair<Integer, Integer> start, final int sx, final int sy, final char[][] map, Random rand, long timeLimit) {
		int[][] food = new int[sy*sx][2];
		int k = 0;
		food[k++] = new int[]{start.first, start.second};
		
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				if (map[i][j] == 'd') {
					food[k++] = new int[]{j, i};
				}
			}
		}
		food = Arrays.copyOf(food, k);
		
		int[][] fdist = new int[k][k];
		for (int i = 0; i < food.length; i++) {
			for (int j = 0; j < food.length; j++) {
				fdist[i][j] = Math.abs(food[i][0] - food[j][0]) + Math.abs(food[i][1] - food[j][1]); 
				fdist[j][i] = Math.abs(food[i][0] - food[j][0]) + Math.abs(food[i][1] - food[j][1]);
			}
		}
		
		int[] path = new int[k];
		for (int i = 0; i < path.length; i++) {
			path[i] = i;
		}
		
		int totalMaxStep = 100000000;
		int numRestarts = 10;
		int maxStep = totalMaxStep / numRestarts;
		
		final int maxEnergyDifference = sx * sy * k;
		final double gradient = -1.0/maxEnergyDifference;
		double P = 0;
		double T = 0;
		
		int[] bestPath = Arrays.copyOf(path, path.length);
		int minDist = maxEnergyDifference;
		
		for (int restart = 0; restart < numRestarts; restart++) {
			path = Arrays.copyOf(bestPath, path.length);
			int energy = 0;
			for (int i = 1; i < path.length; i++) {
				energy += fdist[path[i-1]][path[i]];
			}
			
			for (int step = 0; step <= maxStep; step++) {
				T = (double)(maxStep - step) / maxStep;
				boolean move = false;
				
				// Starting position is fixed and last position has no one to swap with.
				int swapPos = 1 + rand.nextInt(k - 2);
				int newEnergy = energy - fdist[path[swapPos-1]][path[swapPos]] + fdist[path[swapPos-1]][path[swapPos+1]];
				if (swapPos < k - 2) {
					newEnergy += fdist[path[swapPos+2]][path[swapPos]] - fdist[path[swapPos+2]][path[swapPos+1]];
				}
				if (debug) {
					int checkEnergy = 0;
					for (int i = 1; i < path.length; i++) {
						checkEnergy += fdist[path[i-1]][path[i]];
					}
					if (checkEnergy != energy) {
						System.out.println("You fail");
					}
				}
				if (newEnergy < energy) {
					move = true;
					if (debug && newEnergy < minDist) {
						minDist = newEnergy;
						bestPath = Arrays.copyOf(path, path.length);
						System.out.println("Simulated Annealing new Record! Distance:  " + newEnergy);
					}
				} else {
					P = gradient * T * (energy - newEnergy) + 1.0;
					if (P > rand.nextDouble()) {
						move = true;
					}
				}
				if (move) {
					int temp = path[swapPos];
					path[swapPos] = path[swapPos+1];
					path[swapPos+1] = temp;
					energy = newEnergy;
				}
			}
		}
		return food[path[1]];
	}
	
	public static int[] botCleanRandomSearch(Pair<Integer, Integer> start, final int sx, final int sy, final char[][] map, Random rand, long timeLimit) {
		long t3 = System.currentTimeMillis();
		int[][] food = new int[sy*sx][2];

		int k = 0;
		food[k++] = new int[]{start.first, start.second};
		
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				if (map[i][j] == 'd') {
					food[k++] = new int[]{j, i};
				}
			}
		}
		food = Arrays.copyOf(food, k);
		
		ArrayList<ArrayList<Food>> fclosest = new ArrayList<>();
		for (int i = 0; i < food.length; i++) {
			ArrayList<Food> nf = new ArrayList<>();
			for (int j = 1; j < food.length; j++) {
				if (i != j) {
					int dist = Math.abs(food[i][0] - food[j][0]) + Math.abs(food[i][1] - food[j][1]);
					nf.add(new Food(j, food[j][0], food[j][1], dist));
				}
			}
			Collections.sort(nf);
			fclosest.add(nf);
		}
		
		Food begin = new Food(0, food[0][0], food[0][1], 0);
		int minDist = Integer.MAX_VALUE;
		int[] bestPath = null;

		int maxBoundSteps = 0;
		
		t3 = System.currentTimeMillis() - t3;
		
		if (debug) {
			System.out.println("Random search setup time: " + t3);
		}
		for (long t1 = System.currentTimeMillis(); (System.currentTimeMillis() - t1) < timeLimit - t3; ) {
			maxBoundSteps++;
			int runCounter = 0;
			long t2 = System.currentTimeMillis();
			while (true) {
				if (runCounter % 1000 == 0) {
					if (!((System.currentTimeMillis() - t2) < (timeLimit * 0.9) - t3 && (System.currentTimeMillis() - t1) < timeLimit - t3)) {
						break;
					}
				}
				runCounter++;
				int[] path = new int[k];
				boolean[] visited = new boolean[k];
				Food current = begin;
				int totalDist = 0;
				
				for (int fidx = 0; fidx < food.length-1; fidx++) {
					path[fidx] = current.i;
					visited[current.i] = true;
					
					int tempMaxBoundSteps = maxBoundSteps;
					int rInt = rand.nextInt(100);
					if (rInt < 2) {
						tempMaxBoundSteps += 1;
					}
					
					final ArrayList<Food> nf = fclosest.get(current.i);
					
					int lastMin = Integer.MAX_VALUE;
					int boundSteps = 0;
					
					ArrayList<Food> possible = new ArrayList<>();
					for (int i = 0; i < nf.size(); i++) {
						if (!visited[nf.get(i).i]) {
							if (nf.get(i).dist != lastMin) {
								if (boundSteps < tempMaxBoundSteps) {
									boundSteps++;
									lastMin = nf.get(i).dist;
								}
							}
							if (nf.get(i).dist <= lastMin) {
								possible.add(nf.get(i));	
							} else {
								break;
							}
						}
					}
					current = possible.get(rand.nextInt(possible.size()));
					totalDist += current.dist;
				}

				if (totalDist < minDist) {
					minDist = totalDist;
					bestPath = path;

					if (debug) {
						System.out.println("Random search new record! Distance:   " + totalDist + "\t\t maxBoundSteps: " + maxBoundSteps);
					}
				}
			}
			if (debug) {
				System.out.println("runCounter: " + runCounter + "\t maxBoundSteps: " + maxBoundSteps);
			}
		}
		
		ArrayList<Food> path = new ArrayList<>();
		for (int i = 0; i < bestPath.length; i++) {
			path.add(new Food(bestPath[i], food[bestPath[i]][0], food[bestPath[i]][1], 0));
		}
		
		// Optimize
//		int batch = 10;
//		ArrayList<Food> opt = new ArrayList<>();
//		for (int i = 0; i < path.size() - batch; i++) {
//			int totalDist = 0;
//			for (int j = 0; j < batch-1; j++) {
//				totalDist += dist(path.get(i+j), path.get(i+j+1)); 
//			}
//			for (int j = 0; j < 1000000; j++) {
//				for (int p = 1; p < batch-1; p++) {
//					opt.add(path.get(i+p));
//				}
//				Collections.shuffle(opt, rand);
//				int dst = dist(path.get(0), opt.get(0)) + dist(opt.get(opt.size()-1), path.get(i+batch-1));
//				for (int p = 0; p < opt.size()-1; p++) {
//					dst += dist(opt.get(p), opt.get(p+1)); 
//				}
//				if (dst < totalDist) {
//					for (int p = 0; p < opt.size()-1; p++) {
//						path.set(i+1, opt.get(p));
//					}
//				}
//				opt.clear();
//			}
//		}
		
		int d2 = 0;
		for (int i = 0; i < path.size()-1; i++) {
			d2 += dist(path.get(i), path.get(i+1));
		}
		if (debug) {
			System.out.println("new dist " + d2);
		}
		
		int fidx = 0;
		for (Food f : path) {
			if (fidx  == 1) {
				return new int[]{f.x, f.y};
			}
			fidx++;
		}
		
//		ArrayList<Food> path = new ArrayList<>();
//		while (current != null) {
//			path.add(current);
//			current = current.parent;
//		}
//		Collections.reverse(path);
//
//		debug = false;
//		if (debug) {
//			char[][] m = new char[map.length][map[0].length];
//			for (int y = 0; y < map.length; y++) {
//				for (int x = 0; x < map[0].length; x++) {
//					m[y][x] = map[y][x];
//				}
//			}
//			for (int i = 1; i < path.size(); i++) {
//				char dir = 'O';
//				for (int j = 0; j < directions.length; j++) {
//					if (path.get(i).direction.equals(directions[j])) {
//						dir = directionsShort[j];
//						break;
//					}
//				}
//				m[path.get(i).y][path.get(i).x] = dir;
//			}
//			for (int y = 0; y < map.length; y++) {
//				for (int x = 0; x < map[0].length; x++) {
//					System.out.print(m[y][x]);
//				}
//				System.out.println("|");
//			}
//			System.out.println();
//		}
//		
//		if (!path.isEmpty() && food.contains(path.get(0))) {
//			System.out.println("CLEAN");
//		} else {
//			for (int i = 1; (!debug && i < 2) || (debug && i < path.size()); i++) {
//				System.out.println(path.get(i).direction);
//			}
//		}
//		if (path.size() <= 1) {
//			System.err.println("NO PATH FOUND!");
//		}
		return null;
	}
	
	public static int dist(Food a, Food b) {
		return Math.abs(a.x - b.x) + Math.abs(a.y - b.y); 
	}
	
	public static int getHeading(int startX, int startY, int targetX, int targetY) {
		int y = targetY - startY;
		if (y != 0) {
			y /= Math.abs(targetY - startY);
		}
		int x = targetX - startX;
		if (x != 0) {
			x /= Math.abs(targetX - startX);
		}
		for (int i = 0; i < dx.length; i++) {
			if (y != 0 && dy[i] == y) {
				return i;
			}
			if (x != 0 && dx[i] == x) {
				return i;
			}
		}
		return -1;
	}
	
	public static void botClean(InputStream in) {
		MyScanner scan = new MyScanner(in);
		
		String s = scan.next();
		while (s != null) {
			int py = Integer.parseInt(s);
			int px = scan.nextInt();
			char[][] map = new char[5][];
			for (int i = 0; i < map.length; i++) {
				map[i] = scan.nextLine().toCharArray();
			}
			
			Pair<Integer, Integer> start = new Pair<>(px, py);
			botClean(start, map[0].length, map.length, map);
			s = scan.next();
		}
	}

	public static void botClean(Pair<Integer, Integer> start, final int sx, final int sy, final char[][] map) {
		Node2 top = new Node2(start.first, start.second, 0, 0, null, 0);
		HashSet<Node2> food = new HashSet<>();
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				if (map[i][j] == 'd') {
					food.add(new Node2(j, i, 0, 0, null, 0));
				}
			}
		}
		PriorityQueue<Node2> queue = new PriorityQueue<>();
		queue.add(top);
		LinkedHashSet<Node2> visited = new LinkedHashSet<>();
		int maxFood = 0;
		while (!queue.isEmpty()) {
			top = queue.poll();
//			System.out.println("visit [" + top + "]");
			if (top.food > maxFood) {
				maxFood = top.food;
				System.out.println("New food max! " + top.food);
			}
			if (!visited.contains(top)) {
				visited.add(top);
			} else {
				continue;
			}
			if (food.contains(top)) {
				break;
			}
			for (int i = 0; i < dx.length; i++) {
				Node2 next = new Node2(top.x + dx[i], top.y + dy[i], 0, top.steps+1, top, top.food);
				if ((next.x >= 0 && next.x < sx && next.y >= 0 && next.y < sy && map[next.y][next.x] != '%')) {
					next.direction = directions[i];
					int minDist = Integer.MAX_VALUE;
					for (Node2 f : food) {
						minDist = Math.min(minDist, Math.abs(next.x - f.x) + Math.abs(next.y - f.y));
					}
					
					// Estimation must always be <= actual cost!
					next.v = next.steps + minDist;
//					if (minDist == 0) {
//						next.v = 0;
//					}
					queue.add(next);
				}
			}
		}
		Node2 current = top;
		ArrayList<Node2> path = new ArrayList<>();
		while (current != null) {
			path.add(current);
			current = current.parent;
		}
		Collections.reverse(path);
		

		boolean debug = false;
		debug = false;
		if (debug) {
			char[][] m = new char[map.length][map[0].length];
			for (int y = 0; y < map.length; y++) {
				for (int x = 0; x < map[0].length; x++) {
					m[y][x] = map[y][x];
				}
			}
			for (int i = 1; i < path.size(); i++) {
				char dir = 'O';
				for (int j = 0; j < directions.length; j++) {
					if (path.get(i).direction.equals(directions[j])) {
						dir = directionsShort[j];
						break;
					}
				}
				m[path.get(i).y][path.get(i).x] = dir;
			}
			for (int y = 0; y < map.length; y++) {
				for (int x = 0; x < map[0].length; x++) {
					System.out.print(m[y][x]);
				}
				System.out.println("|");
			}
			System.out.println();
		}
		
		if (!path.isEmpty() && food.contains(path.get(0))) {
			System.out.println("CLEAN");
		} else {
			for (int i = 1; (!debug && i < 2) || (debug && i < path.size()); i++) {
				System.out.println(path.get(i).direction);
			}
		}
		if (path.size() <= 1) {
			System.err.println("NO PATH FOUND!");
		}
		return;
	}
	
	public static final int[] dy = {-1, 0, 0, 1};
	//public static final int[] dy = {1, 0, 0, -1};
	public static final int[] dx = {0, -1, 1, 0};
	public static final String[] directions = {"UP", "LEFT", "RIGHT", "DOWN"};
	public static final char[] directionsShort = {'^', '<', '>', 'v'};
	
	public static void botSavesPrincess(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int sy = scan.nextInt();
		char[][] map = new char[sy][];
		for (int i = 0; i < map.length; i++) {
			map[i] = scan.nextLine().toCharArray();
		}
		
		Pair<Integer, Integer> start = null;
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map[0].length; x++) {
				if (map[y][x] == 'm') {
					start = new Pair<>(x, y);
				}
			}
		}
		
		botSavesPrincess(start, map[0].length, sy, map);
	}
	
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
	
	public static void botSavesPrincess(Pair<Integer, Integer> start, int sx, int sy, char[][] map) {
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
		for (int i = 1; i < path.size(); i++) {
			System.out.println(path.get(i).direction);
		}
		return;
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
		//packmanAStar(packman, food, sx, sy, map);
		packmanUCS(packman, food, sx, sy, map);
	}
	
	public static void packmanUCS(Pair<Integer, Integer> packman, Pair<Integer, Integer> f, int sx, int sy, char[][] map) {
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
					next.v = next.steps;
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

//		System.out.println();
//		System.out.println(visited.size());
//		System.out.println(path.size()-1);
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
	
	public static class Node4 implements Comparable<Node4> {
		private int i;
		private int x;
		private int y;
		private int travelled;
		private long state;
		private Node4 parent;
		private int v;
		private int steps;
		
		public Node4(int i, int x, int y, int travelled, long state, Node4 parent, int v, int steps) {
			super();
			this.i = i;
			this.x = x;
			this.y = y;
			this.travelled = travelled;
			this.state = state;
			this.parent = parent;
			this.v = v;
			this.steps = steps;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + (int)(state / Integer.MAX_VALUE);
			result = prime * result + (int)(state % Integer.MAX_VALUE);
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
			Node4 other = (Node4) obj;
			if (state != other.state)
				return false;
			return true;
		}
		
		public String toString() {
			return x + ":" + y;
		}

		@Override
		public int compareTo(Node4 o) {
			return Integer.compare(this.v, o.v);
		}
	}
	
	public static class Food implements Comparable<Food> {
		private int i;
		private int x;
		private int y;
		private int dist;
		
		public Food(int i, int x, int y, int dist) {
			super();
			this.i = i;
			this.x = x;
			this.y = y;
			this.dist = dist;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
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
			Food other = (Food) obj;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			return true;
		}

		@Override
		public int compareTo(Food o) {
			return Integer.compare(this.dist, o.dist);
		}
		
		public String toString() {
			return x + ":" + y;
		}
	}
	
	public static class Node3 implements Comparable<Node3> {
		private int i;
		private int v;
		private int steps;
		private Node3 parent;
		
		public Node3(int i, int v, int steps, Node3 parent) {
			super();
			this.i = i;
			this.steps = steps;
			this.v = v;
			this.parent = parent;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + i;
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
			Node3 other = (Node3) obj;
			if (i != other.i)
				return false;
			return true;
		}

		@Override
		public String toString() {
			return i + "";
		}

		@Override
		public int compareTo(Node3 o) {
			return Integer.compare(this.v, o.v);
		}
	}
	
	public static class Node2 implements Comparable<Node2> {
		private int food;
		private int x;
		private int y;
		private int v;
		private int steps;
		private Node2 parent;
		private String direction;
		
		public Node2(int x, int y, int v, int steps, Node2 parent, int food) {
			super();
			this.x = x;
			this.y = y;
			this.v = v;
			this.food = food;
			this.parent = parent;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
//			result = prime * result + food;
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
			Node2 other = (Node2) obj;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
//			if (food != other.food)
//				return false;
			return true;
		}

		@Override
		public String toString() {
			return x + " " + y;
		}

		@Override
		public int compareTo(Node2 o) {
			return Integer.compare(this.v, o.v);
		}
	}
	
	public static class Node implements Comparable<Node> {
		private int food;
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
