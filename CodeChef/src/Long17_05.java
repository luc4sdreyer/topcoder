import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.TreeSet; 

public class Long17_05 {
    public static InputReader in;
    public static PrintWriter out;
	
	public static final int[] ships = {4, 3, 3, 2, 2, 2, 1, 1, 1, 1};
	public static final int[] dx8 = {1, 1, 1, 0, -1, -1, -1, 0,};
	public static final int[] dy8 = {1, 0, -1, -1, -1, 0, 1, 1,};
//	public static final int[] dx = {1, 0, -1, 0,};
//	public static final int[] dy = {0, -1, 0, 1,};
	public static final int[] dx = {1, 0, -1, 0};
	public static final int[] dy = {0, 1, 0, -1};
	
	public static final boolean debug = false;
 
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        in = new InputReader(inputStream);
//        out = new PrintWriter(outputStream, true);
        out = new PrintWriter(outputStream, false); // enable this for ludicrous speed
 
//        CHEFROUT();
//        UNICOURS();
//        MXMEDIAN();
//        CHEFSUBA();
//        CHEFCODE4();
//        testCHEFCODE();
//        CHEFCODE3();
//        CHEFCODE2();
//        WSITES01();
//        GPD();
//        CHEFBATL();
//        testCHEFBATL();
//        testCHEFBATL2();
        SANDWICH();
//        testSANDWICH();
        out.close();
    }
    
    public static void SANDWICH() {
    	int tests = in.nextInt();
    	for (int test = 0; test < tests; test++) {
			long n = in.nextLong();
			long k = in.nextLong();
			long m = in.nextLong();
			long[] ret = SANDWICH2(n, k, m);
			System.out.println(ret[0] + " " + ret[1]);
		}
    }
    
    public static void testSANDWICH() {
		for (int k = 1; k < 50; k++) {
			for (int n = 1; n < 50; n++) {
//				System.out.println("\t\t test k: " + k + " n: " + n);
				long[] r1 = SANDWICH(n, k, 1000007);
				long[] r2 = SANDWICH2(n, k, 1000007);
				
//				System.out.println(r1[1]);
//				System.out.print(r1[1] + "\t");
				assertEquals(r1[0], r2[0]);
				assertEquals(r1[1], r2[1]);
			}
			System.out.println();
		}
    }
    
	public static long[] SANDWICH2(long n, long k, long m) {
		long b = (n-1)/k;
		long a = k - ((n-1) % k);
		long pieces = (long) Math.ceil((double)n/k);
		long ret = 0;
		if (n <= 1000000 && k <= 1000000) {
			return SANDWICH(n, k, m);
		} else {
			ret = nCrBig(a + b-1, b, (int)m);
		}
		return new long[]{pieces, ret};
	}
	
	public static long nCrBig(long n, long r, int modulo) {
		if (n < r) {
			return 0;
		}
		
		// symmetry
		if (n - r < r) {
			r = n - r;
		}

		long a = 1;
		long b = 1;
		long mod = modulo;
		
		for (long i = n - r + 1; i <= n; i++) {
			a = (a * i) % mod;
		}
		for (long i = 2; i <= r; i++) {
			b = (b * i) % mod;
		}

		long bInv = modularMultiplicativeInverse((int) b, modulo);
		long ret = (a * bInv) % mod;
		
		return (int) ret;
	}
	
	public static int modularMultiplicativeInverse(int b, int mod) {
		return fastModularExponent(b, eulerTotientFunction(mod) - 1, mod) % mod;
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
				results[power] = (results[power-1] * results[power-1]) % m;
			}
			if (exp % 2 == 1) {
				res = (res * results[power]) % m;
			}
			exp /= 2;
			power++;
		}
		return (int) (res % m);
	}
	
	public static int eulerTotientFunction(int n) {
		int result = n;
		for (int i = 2; i*i <= n; i++) {
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
    
	public static long[] SANDWICH(long n, long k, long m) {
		long pieces = (long) Math.ceil((double)n/k);
		long maxLen = k;
		
		long[] dp = new long[(int) n];
		for (int i = 0; i < maxLen && i < n; i++) {
			dp[i] = 1;
		}
		if (debug) {
			System.out.println(Arrays.toString(dp));
		}
		for (int i = 1; i < pieces; i++) {
			long[] dp2 = new long[(int) n];
			long slide = 0;
			for (int j = 1; j < dp2.length; j++) {
				slide += dp[j-1];
				if (j-1-maxLen >= 0) {
					slide -= dp[(int) (j-1-maxLen)];
				}
				dp2[j] = slide;
			}
			dp = dp2;
			if (debug) {
				System.out.println(Arrays.toString(dp));
			}
		}
		return new long[]{pieces, dp[(int) (n-1)] % m};
	}
	
	public static long nCr(int n, int r) {
		if (n < r) {
			return 0;
		}
		if (n == 0) {
			return 1;
		}
//		System.out.println("n: " + n + " r: " + r);
		long ret = 1;
		//
		// symmetry
		//
		if (n - r < r) {
			r = n - r;
		}
		for (long i = n - r + 1; i <= n; i++) {
			ret *= i;
		}
		for (long i = 2; i <= r; i++) {
			ret /= i;
		}
		return ret;
	}

	public static void testCHEFBATL2() {
		Random rand = new Random(0);
		int N = 10;
		int numRuns = 1000000;
		double shipSum = 0;
		for (int i = 0; i < ships.length; i++) {
			shipSum += ships[i];
		}
		for (int type = 0; type < 2; type++) {
			double avgDist = 0;
			double[] avgWeight = new double[2];
			double[][] hits = new double[N][N];
			int[][] map = new int[N][N];
			for (int run = 0; run < numRuns; run++) {
				GameData2 game = new GameData2();
				game.known.add(new ShipPosition(3, true, 3, 3));
				if (type == 0) {
					map = generateM1(rand, N, game);
				} else {
					map = generateM2(rand, N, game);
				}
				
				for (int y = 0; y < map.length; y++) {
					for (int x = 0; x < map.length; x++) {
						if (map[y][x] > 1) {
							avgWeight[0] += probs[0][y][x];
							avgWeight[1] += probs[1][y][x];
						}
					}
				}
				
				double avgDis = avgDist(map);
				
				avgDist += avgDis;
				
				for (int y = 0; y < N; y++) {
					for (int x = 0; x < N; x++) {
						if (map[y][x] != 0) {
							hits[y][x]++;
						}
					}
				}
				
				if (debug) {
					for (int i = 0; i < N; i++) {
						for (int j = 0; j < N; j++) {
							if (map[i][j] != 0) {
								System.out.print(map[i][j] + " ");
							} else {
								System.out.print("  ");
							}
						}
						System.out.println();
					}
					System.out.println("####################");
				}
				
			}
			avgDist /= numRuns;
			avgWeight[0] /= numRuns * (shipSum -4);
			avgWeight[1] /= numRuns * (shipSum -4);
			for (int y = 0; y < N; y++) {
				for (int x = 0; x < N; x++) {
					hits[y][x] = (hits[y][x]) / numRuns / (shipSum / N / N);
				}
			}

			for (int y = 0; y < N; y++) {
				for (int x = 0; x < N; x++) {
					System.out.print(String.format("%.3f\t", hits[y][x]));
				}
				System.out.println();
			}
			System.out.println("avgDist: " + avgDist);
			System.out.println("avgWeight: " + Arrays.toString(avgWeight) + "\t mid: " + (avgWeight[0] + avgWeight[1])/2);
			System.out.println();
		}
	}

	private static double avgDist(int[][] map) {
		ArrayList<int[]> hitList = new ArrayList<>();
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map.length; x++) {
				if (map[y][x] > 1) {
					hitList.add(new int[]{x,y});
					if ((y == 0) || (y > 0 && map[y-1][x] == 0)) {
						if ((x == 0) || (x > 0 && map[y][x-1] == 0)) {
							hitList.add(new int[]{x,y});
						}
					}
				}
			}
		}

		double avgDis = 0;
		int numDis = 0;
		for (int i = 0; i < hitList.size(); i++) {
			for (int j = i+1; j < hitList.size(); j++) {
				//avgDis += Math.pow(hitList.get(i)[0] - hitList.get(j)[0], 2) + Math.pow(hitList.get(i)[1] - hitList.get(j)[1], 2);
				avgDis += Math.abs(hitList.get(i)[0] - hitList.get(j)[0]) + Math.abs(hitList.get(i)[1] - hitList.get(j)[1]);
				numDis++;
			}
		}
		avgDis /= numDis;
		return avgDis;
	}

	private static int[][] generateM2(Random rand, int N, GameData2 game) {
		int[][] map;
		map = new int[N][N];
		boolean restart = false;
		boolean[] rotate = new boolean[ships.length];
		int[] order = new int[ships.length];
		ArrayList<Integer> remainingShips = new ArrayList<>();
		
		while (true) {
			restart = false;
			int A = rand.nextInt(5) + 6;
			int B = rand.nextInt(5) + 6;
			int rx = rand.nextInt(N - A + 1);
			int ry = rand.nextInt(N - B + 1);
			map = new int[N][N];
			
			// set missed
			for (int[] miss: game.missed) {
				map[miss[1]][miss[0]] = -1;
			}
			// set known ships
			ArrayList<ShipPosition> known = new ArrayList<>();
			for (int i = 0; i < game.known.size(); i++) {
				known.add(game.known.get(i).clone());
			}
			remainingShips = new ArrayList<>();
			for (int i = 0; i < ships.length; i++) {
				int idx = -1;
				ShipPosition ship = null;
				for (int j = 0; j < known.size(); j++) {
					if (known.get(j).length == ships[i]) {
						idx = j;
						ship = known.get(idx);
						known.remove(idx);
						break;
					}
				}
				if (idx >= 0) {
					placeShip(map, ship);
				} else {
					remainingShips.add(ships[i]);
				}
			}
			
			rotate = new boolean[remainingShips.size()];
			for (int i = 0; i < rotate.length; i++) {
				rotate[i] = rand.nextBoolean();
			}
			
			order = new int[remainingShips.size()];
			for (int i = 0; i < order.length; i++) {
				order[i] = i;
			}
			
			for (int i = 0; i < remainingShips.size(); i++) {
				boolean down = rotate[order[i]];
				int length = remainingShips.get(order[i]);
				ArrayList<int[]> valid = new ArrayList<>();
				
				if (length == 1) {
					continue;
				}
				
				for (int y = ry; y < ry + B; y++) {
					for (int x = rx; x < rx + A; x++) {
						if (canPlace(map, x, y, length, down)) {
							valid.add(new int[]{x, y});
						}
					}
				}
				
				if (valid.isEmpty()) {
					restart = true;
					break;
				}
				
				int[] pos = valid.get(rand.nextInt(valid.size()));
				placeShip(map, new ShipPosition(length, down, pos[0], pos[1]));
			}
			if (!restart) {
				break;
			}
		}
		
		for (int i = 0; i < remainingShips.size(); i++) {
			boolean down = rotate[order[i]];
			int length = remainingShips.get(order[i]);
			ArrayList<int[]> valid = new ArrayList<>();
			
			if (length != 1) {
				continue;
			}
			
			for (int y = 0; y < N; y++) {
				for (int x = 0; x < N; x++) {
					if (canPlace(map, x, y, length, down)) {
						valid.add(new int[]{x, y});
					}
				}
			}
			
			int[] pos = valid.get(rand.nextInt(valid.size()));
			placeShip(map, new ShipPosition(length, down, pos[0], pos[1]));
		}
		return map;
	}

	private static int[][] generateM1(Random rand, int N, GameData2 game) {
		int[][] map;
		map = new int[N][N];
		boolean restart = false;
		boolean[] rotate = new boolean[ships.length];
		int[] order = new int[ships.length];
		
		while (true) {
			restart = false;
			
			map = new int[N][N];
			// set missed
			for (int[] miss: game.missed) {
				map[miss[1]][miss[0]] = -1;
			}
			// set known ships
			ArrayList<ShipPosition> known = new ArrayList<>();
			for (int i = 0; i < game.known.size(); i++) {
				known.add(game.known.get(i).clone());
			}
			ArrayList<Integer> remainingShips = new ArrayList<>();
			for (int i = 0; i < ships.length; i++) {
				int idx = -1;
				ShipPosition ship = null;
				for (int j = 0; j < known.size(); j++) {
					if (known.get(j).length == ships[i]) {
						idx = j;
						ship = known.get(idx);
						known.remove(idx);
						break;
					}
				}
				if (idx >= 0) {
					placeShip(map, ship);
				} else {
					remainingShips.add(ships[i]);
				}
			}
			
			rotate = new boolean[remainingShips.size()];
			for (int i = 0; i < rotate.length; i++) {
				rotate[i] = rand.nextBoolean();
			}
			
			order = new int[remainingShips.size()];
			for (int i = 0; i < order.length; i++) {
				order[i] = i;
			}
			shuffle(order, rand);
			
			for (int i = 0; i < remainingShips.size(); i++) {
				boolean down = rotate[order[i]];
				int length = remainingShips.get(order[i]);
				ArrayList<int[]> valid = new ArrayList<>();
				
				for (int y = 0; y < N; y++) {
					for (int x = 0; x < N; x++) {
						if (canPlace(map, x, y, length, down)) {
							valid.add(new int[]{x, y});
						}
					}
				}
				
				if (valid.isEmpty()) {
					restart = true;
					break;
				}
				
				int[] pos = valid.get(rand.nextInt(valid.size()));
				placeShip(map, new ShipPosition(length, down, pos[0], pos[1]));
			}
			if (!restart) {
				break;
			}
		}
		return map;
	}

	private static void placeShip(int[][] map, ShipPosition ship) {
		for (int j = 0; j < ship.length; j++) {
			int nx = ship.x;
			int ny = ship.y + j;
			if (!ship.down) {
				nx = ship.x + j;
				ny = ship.y;
			}
			map[ny][nx] = ship.length;
		}
	}
	
	private static boolean canPlace(int[][] map, int x, int y, int length, boolean down) {
		int N = map.length;
		for (int j = 0; j < length; j++) {
			int nx = x;
			int ny = y + j;
			if (!down) {
				nx = x + j;
				ny = y;
			}
			if (!(0 <= nx && nx < N && 0 <= ny && ny < N && map[ny][nx] == 0)) {
				return false;
			}
			for (int i = 0; i < dx8.length; i++) {
				int nx2 = nx + dx8[i];
				int ny2 = ny + dy8[i];
				if (0 <= nx2 && nx2 < N && 0 <= ny2 && ny2 < N && map[ny2][nx2] > 0) {
					return false;
				}
			}
		}
		return true;
	}

	public static void shuffle(int[] a, Random rand) {
        for (int i = a.length; i > 1; i--) {
        	int r = rand.nextInt(i);
        	int temp = a[i-1];
        	a[i-1] = a[r];
        	a[r] = temp;
        }
	}
    
	public static void testCHEFBATL() {
		Random rand = new Random(0);
		int numBigTest = 8;
		final int N = 10;
		for (int testBig = 0; testBig < numBigTest; testBig++) {
			int numTests = 10000;
			int[] scores = new int[numTests];
			double mean = 0;
			double meanTurns = 0;
			double stdev = 0;
			for (int test = 0; test < numTests; test++) {
				int[][] refMap = new int[N][N];
				if (testBig < numBigTest/2) {
					refMap = generateM1(rand, N, new GameData2());
				} else {
					refMap = generateM2(rand, N, new GameData2());
				}
//				refMap = generateM2(rand, N, new GameData2());
				
				char[][] seenMap = new char[N][N];
				for (int y = 0; y < N; y++) {
					Arrays.fill(seenMap[y], '-');
				}
				
				char[][] realMap = new char[N][N];
				for (int y = 0; y < N; y++) {
					Arrays.fill(realMap[y], '.');
				}
				for (int y = 0; y < N; y++) {
					for (int x = 0; x < N; x++) {
						if (refMap[y][x] != 0) {
							realMap[y][x] = '#';
						}
					}
				}
				
				int hp = 0;
				for (int y = 0; y < N; y++) {
					for (int x = 0; x < N; x++) {
						if (realMap[y][x] == '#') {
							hp++;
						}
					}
				}

				GameData2 game = new GameData2();
				int moves = 0;
				while (hp > 0) {
		
					if (debug) {
						for (int i = 0; i < N; i++) {
							System.out.println(new String(seenMap[i]));
						}
						System.out.println();
						
						for (int i = 0; i < N; i++) {
							System.out.println(new String(realMap[i]));
						}
						System.out.println();
					}
					
					int[] move = getMove(seenMap, rand, game);
					int mx = move[0];
					int my = move[1];
					if (realMap[my][mx] != '.') {
						realMap[my][mx] = 'h';
						hp--;

						if (isSunk(realMap, mx, my, '#')) {
							// sunk
							seenMap[my][mx] = 'h';
							fillHit(seenMap, mx, my, 'h', new GameData2());
							fillHit(realMap, mx, my, 'h', game);
							markReserved(seenMap, 'x');
						} else {
							// hit
							seenMap[my][mx] = 'h';
						}
					} else {
						seenMap[my][mx] = 'm';
						game.missed.add(new int[]{mx, my});
					}
					
					moves += 2;
				}
				int score = (200 - moves) * 100;
				scores[test] = score;
				mean += score;
				meanTurns += moves/2;
			}
			mean /= numTests;
			meanTurns /= numTests;
			for (int i = 0; i < scores.length; i++) {
				stdev += (scores[i] - mean) * (scores[i] - mean);
			}
			stdev /= numTests;
			stdev = Math.sqrt(stdev);
			
			System.out.println("mean: " + String.format("%.2f", mean) + "\t stdev: " + String.format("%.2f", stdev) + "\t meanTurns: " + String.format("%.2f", meanTurns));
			
		}
	}
    
	public static void CHEFBATL() {
		Random rand = new Random(2);
		for (int test = 0; test < 100; test++) {
			System.out.println(1);
//			String myMap =    "........#.\n"
//							+ ".###......\n"
//							+ "..........\n"
//							+ "##..#..#..\n"
//							+ ".......#..\n"
//							+ "..#....#..\n"
//							+ "#.#.......\n"
//							+ "#.........\n"
//							+ "...####..#\n"
//							+ ".#........\n";
//			String myMap =    "##.####.##\n"
//							+ "..........\n"
//							+ "###.##.###\n"
//							+ "..........\n"
//							+ "#.#.#.#...\n"
//							+ "..........\n"
//							+ "..........\n"
//							+ "..........\n"
//							+ "..........\n"
//							+ "..........\n";
//			System.out.println(myMap);
			int[][] myMap = generateM1(rand, 10, new GameData2());
			for (int y = 0; y < myMap.length; y++) {
				for (int x = 0; x < myMap.length; x++) {
					if (myMap[y][x] == 0) {
						System.out.print('.');
					} else {
						System.out.print('#');
					}
				}
				System.out.println();
			}
			final int N = 10;
			char[][] map = new char[N][N];
			for (int y = 0; y < N; y++) {
				for (int x = 0; x < N; x++) {
					map[y][x] = '-';
				}
			}

			GameData2 game = new GameData2();
			int[] move = getMove(map, rand, game);
			System.out.println((move[0]+1) + " " + (move[1]+1));
			boolean p1move = true;
			
			while (true) {
				if (debug) {
					for (int i = 0; i < N; i++) {
						System.out.println(new String(map[i]));
					}
					System.out.println();
				}
				int reply = in.nextInt();
				if (reply == 1) {
					// miss
					map[move[1]][move[0]] = 'm';
					game.missed.add(new int[]{move[0], move[1]});
					p1move = false;
				} else if (reply == 2) {
					// hit
					map[move[1]][move[0]] = 'h';
					p1move = true;
				} else if (reply == 3) {
					// sunk
					map[move[1]][move[0]] = 'h';
					fillHit(map, move[0], move[1], 'h', game);
					markReserved(map, 'x');
					p1move = true;
				} else if (reply == 4) {
					// won
					break;
				} else if (reply == 5) {
					// lost
					p1move = false;
				}
				if (!p1move) {
					int numEnemyMoves = in.nextInt();
					for (int i = 0; i < numEnemyMoves; i++) {
						in.nextInt();
						in.nextInt();
					}
					p1move = true;
					if (reply == 5) {
						// lost
						break;
					}
				}
				if (p1move) {
					move = getMove(map, rand, game);
					System.out.println((move[0]+1) + " " + (move[1]+1));
				}
			}	
		}
		System.out.println("0");
	}
	
	public static void markReserved(char[][] map, char marker) {
		int[] mdx = {1, 1, 1, 0, -1, -1, -1, 0};
		int[] mdy = {1, 0, -1, -1, -1, 0, 1, 1};
		int N = 10;
		for (int y = 0; y < N; y++) {
			for (int x = 0; x < N; x++) {
				if (map[y][x] == 'd') {
					for (int i = 0; i < mdy.length; i++) {
						int tx = x + mdx[i];
						int ty = y + mdy[i];
						if (0 <= tx && tx < N && 0 <= ty && ty < N && map[ty][tx] != 'd') {
							map[ty][tx] = marker;
						}
					}
				}
			}
		}
	}
	
	public static boolean isSunk(char[][] map, int sx, int sy, char marker) {
		int[] top = new int[]{sx, sy};
		int[] mdx = {0, 1, 0, -1};
		int[] mdy = {1, 0, -1, 0};
	
		int N = 10;
		boolean[][] visited = new boolean[N][N];
		Stack<int[]> s = new Stack<>();
		s.add(top);
		while (!s.isEmpty()) {
			top = s.pop();
			int tx = top[0];
			int ty = top[1];
			if (0 <= tx && tx < N && 0 <= ty && ty < N && !visited[ty][tx] && (map[ty][tx] == marker || map[ty][tx] == 'h')) {
				if (map[ty][tx] == marker) {
					return false;
				}
				visited[ty][tx] = true;
				for (int i = 0; i < mdy.length; i++) {
					s.add(new int[]{tx + mdx[i], ty + mdy[i]});
				}
			}
		}
		return true;
	}
	
	public static void fillHit(char[][] map, int sx, int sy, char marker, GameData2 game) {
		int[] top = new int[]{sx, sy};
		int[] mdx = {0, 1, 0, -1};
		int[] mdy = {1, 0, -1, 0};
	
		int N = 10;
		boolean[][] visited = new boolean[N][N];
		Stack<int[]> s = new Stack<>();
		s.add(top);
		int length = 0;
		int minX = N;
		int maxX = 0;
		int minY = N;
		int maxY = 0;
		while (!s.isEmpty()) {
			top = s.pop();
			int tx = top[0];
			int ty = top[1];
			if (0 <= tx && tx < N && 0 <= ty && ty < N && !visited[ty][tx] && (map[ty][tx] == marker || map[ty][tx] == 'd')) {
				visited[ty][tx] = true;
				length++;
				minX = Math.min(minX, tx);
				maxX = Math.max(maxX, tx);
				minY = Math.min(minY, ty);
				maxY = Math.max(maxY, ty);
				map[ty][tx] = 'd';
				for (int i = 0; i < mdy.length; i++) {
					s.add(new int[]{tx + mdx[i], ty + mdy[i]});
				}
			}
		}
		boolean down = false;
		if (minX == maxX) {
			down = true;
		}
		game.known.add(new ShipPosition(length, down, minX, minY));
	}

	public static int[] getMove(char[][] map, Random rand, GameData2 game) {
//		if (System.currentTimeMillis() != -1) {
//			return new int[]{0, 0};
//		}
		ArrayList<int[]> hitList = new ArrayList<>();
		int hits = 0;
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map.length; x++) {
				if (map[y][x] == 'h') {
					hits++;
				}
				if (map[y][x] == 'h' || map[y][x] == 'd') {
					// don't count subs 
					int nn = 0;
					for (int dir = 0; dir < dx.length; dir++) {
						int py = y + dy[dir];
						int px = x + dx[dir];
						if (px >= 0 && px < map.length && py >= 0 && py < map.length && map[py][px] == 'd') {
							nn++;
						}
					}
					if (nn > 0) {
						hitList.add(new int[]{x,y});
					}
				}
			}
		}
		
		int moves = 0;
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map.length; x++) {
				if (map[y][x] != '.') {
					moves++;
				}
			}
		}
		
		// Busy attacking a known ship, hit around it until the
		// orientation is known and then hit along that axis.
		if (hits > 0) {
			for (int y = 0; y < map.length; y++) {
				for (int x = 0; x < map.length; x++) {
					if (map[y][x] == 'h') {
						for (int dir = 0; dir < dx.length; dir++) {
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
						ArrayList<int[]> possible = new ArrayList<>();
						for (int dir = 0; dir < dx.length; dir++) {
							final int py = y + dy[dir];
							final int px = x + dx[dir];
							if (px >= 0 && px < map.length && py >= 0 && py < map.length && map[py][px] == '-') {
								possible.add(new int[]{px, py});
							}
						}
						//return possible.get(rand.nextInt(possible.size()));
						if (rand.nextBoolean()) {
							return possible.get(0);
						} else {
							return possible.get(possible.size()-1);
						}
					}
				}
			}
		}

		ArrayList<int[]> possible = new ArrayList<>();
		// Hit every odd square to find the destroyers. Prefer cells with more unknown neighbours.
		for (int hitSubs = 0; hitSubs < 2; hitSubs++) {
			for (int nn = 4; nn >= 0; nn--) {
				int preferOpposite = 1;
				if (nn == 2) {
					preferOpposite = 2;
				}
				for (int po = preferOpposite-1; po >= 0; po--) {
					if (po == 1) {
						boolean[] neigh = new boolean[4];
						for (int y = 0; y < map.length; y++) {
							for (int x = 0; x < map.length; x++) {
								if (((x + y) % 2 == 0 || hitSubs == 1) && map[y][x] == '-') {
									neigh = new boolean[4];
									
									for (int dir = 0; dir < dx.length; dir++) {
										final int py = y + dy[dir];
										final int px = x + dx[dir];
										if (px >= 0 && px < map.length && py >= 0 && py < map.length && map[py][px] == '-') {
											neigh[dir] = true;
										}
									}
									if ((neigh[0] && neigh[2] && !neigh[1] && !neigh[3]) || (!neigh[0] && !neigh[2] && neigh[1] && neigh[3])) {
										possible.add(new int[]{x, y});
									}
								}
							}
						}
					} else {
						for (int y = 0; y < map.length; y++) {
							for (int x = 0; x < map.length; x++) {
								if (((x + y) % 2 == 0 || hitSubs == 1) && map[y][x] == '-') {
									int openNeighbors = 0;
									for (int dir = 0; dir < dx.length; dir++) {
										final int py = y + dy[dir];
										final int px = x + dx[dir];
										if (px >= 0 && px < map.length && py >= 0 && py < map.length && map[py][px] == '-') {
											openNeighbors++;
										}
									}
									if (openNeighbors == nn) {
										possible.add(new int[]{x, y});
									}
								}
							}
						}
					}
					if (possible.size() > 0) {
						nn = -1;
						hitSubs = 2;
						break;
					}
				}
			}
		}
//		possible = new ArrayList<>();
//		for (int y = 0; y < map.length; y++) {
//			for (int x = 0; x < map.length; x++) {
//				if ((x + y) % 2 == 0 && map[y][x] == '-') {
//					possible.add(new int[]{x, y});
//				}
//			}
//		}
//		if (possible.isEmpty()) {
//			for (int y = 0; y < map.length; y++) {
//				for (int x = 0; x < map.length; x++) {
//					if (map[y][x] == '-') {
//						possible.add(new int[]{x, y});
//					}
//				}
//			}
//		}
		int numBigKnown = 0;
		for (int i = 0; i < game.known.size(); i++) {
			if (game.known.get(i).length > 1) {
				numBigKnown++;
			}
		}
		if (numBigKnown >= 3) {
			Collections.sort(possible, new Comparator<int[]>() {
				@Override
				public int compare(int[] o1, int[] o2) {
					int sqDist1 = 0;
					for (int[] hit: hitList) {
						int dist1 = hit[0] + o1[0];
						int dist2 = hit[1] + o1[1];
						sqDist1 += dist1 * dist1 + dist2 * dist2;
					}
					int sqDist2 = 0;
					for (int[] hit: hitList) {
						int dist1 = hit[0] + o2[0];
						int dist2 = hit[1] + o2[1];
						sqDist2 += dist1 * dist1 + dist2 * dist2;
					}
					if (sqDist1 == sqDist2) {
						return Double.compare(probs[1][o2[1]][o2[0]], probs[1][o1[1]][o1[0]]);
					}
					return Integer.compare(sqDist1, sqDist2);
				}
			});
		} else {
			Collections.sort(possible, new Comparator<int[]>() {
				@Override
				public int compare(int[] o1, int[] o2) {
					return Double.compare(probs[1][o2[1]][o2[0]], probs[1][o1[1]][o1[0]]);
				}
			});
		}
		return possible.get(0);
//		return possible.get(rand.nextInt(possible.size()));
	}
	
	public static double probs[][][] = {
		{
			{0.943, 1.038, 1.124, 1.121, 1.086, 1.088, 1.122, 1.121, 1.037, 0.948},
			{1.032, 0.926, 0.954, 0.939, 0.92, 0.922, 0.94, 0.955, 0.925, 1.036},
			{1.121, 0.957, 0.998, 0.988, 0.972, 0.97, 0.99, 0.998, 0.957, 1.121},
			{1.12, 0.943, 0.989, 0.981, 0.963, 0.962, 0.98, 0.988, 0.941, 1.121},
			{1.088, 0.92, 0.969, 0.962, 0.943, 0.942, 0.961, 0.971, 0.919, 1.086},
			{1.085, 0.919, 0.968, 0.962, 0.945, 0.945, 0.964, 0.971, 0.919, 1.086},
			{1.122, 0.941, 0.989, 0.982, 0.961, 0.96, 0.98, 0.99, 0.942, 1.121},
			{1.122, 0.955, 1, 0.991, 0.969, 0.972, 0.989, 0.998, 0.956, 1.123},
			{1.035, 0.922, 0.956, 0.941, 0.919, 0.919, 0.94, 0.954, 0.928, 1.035},
			{0.946, 1.035, 1.122, 1.121, 1.087, 1.089, 1.124, 1.121, 1.036, 0.946},
		},
		{
			{0.664, 0.709, 0.79, 0.842, 0.858, 0.86, 0.881, 0.874, 0.809, 0.725},
			{0.707, 0.806, 0.92, 0.975, 0.98, 0.977, 1.002, 1, 0.916, 0.778},
			{0.79, 0.923, 1.048, 1.105, 1.103, 1.094, 1.121, 1.122, 1.033, 0.865},
			{0.841, 0.978, 1.107, 1.161, 1.152, 1.146, 1.175, 1.176, 1.087, 0.917},
			{0.856, 0.983, 1.107, 1.155, 1.152, 1.144, 1.173, 1.171, 1.085, 0.926},
			{0.864, 0.975, 1.095, 1.143, 1.143, 1.135, 1.17, 1.16, 1.075, 0.931},
			{0.881, 0.999, 1.123, 1.174, 1.176, 1.167, 1.195, 1.184, 1.093, 0.948},
			{0.878, 0.999, 1.123, 1.178, 1.175, 1.163, 1.184, 1.172, 1.081, 0.935},
			{0.812, 0.916, 1.03, 1.084, 1.082, 1.074, 1.093, 1.081, 0.995, 0.867},
			{0.727, 0.779, 0.866, 0.919, 0.928, 0.929, 0.949, 0.935, 0.866, 0.781},
		},
		{
			{0.8035, 0.8735, 0.957, 0.9815, 0.972, 0.974, 1.0015, 0.9975, 0.923, 0.8365},
			{0.8695, 0.866, 0.937, 0.957, 0.95, 0.9495, 0.971, 0.9775, 0.9205, 0.907},
			{0.9555, 0.94, 1.023, 1.0465, 1.0375, 1.032, 1.0555, 1.06, 0.995, 0.993},
			{0.9805, 0.9605, 1.048, 1.071, 1.0575, 1.054, 1.0775, 1.082, 1.014, 1.019},
			{0.972, 0.9515, 1.038, 1.0585, 1.0475, 1.043, 1.067, 1.071, 1.002, 1.006},
			{0.9745, 0.947, 1.0315, 1.0525, 1.044, 1.04, 1.067, 1.0655, 0.997, 1.0085},
			{1.0015, 0.97, 1.056, 1.078, 1.0685, 1.0635, 1.0875, 1.087, 1.0175, 1.0345},
			{1, 0.977, 1.0615, 1.0845, 1.072, 1.0675, 1.0865, 1.085, 1.0185, 1.029},
			{0.9235, 0.919, 0.993, 1.0125, 1.0005, 0.9965, 1.0165, 1.0175, 0.9615, 0.951},
			{0.8365, 0.907, 0.994, 1.02, 1.0075, 1.009, 1.0365, 1.028, 0.951, 0.8635},
		}
	};

	private static String getStringMap(GameData data) {
		StringBuilder mapOut = new StringBuilder();
		for (int y = 0; y < data.grid.length; y++) {
			for (int x = 0; x < data.grid.length; x++) {
				if (data.grid[y][x]) {
					mapOut.append('#');
				} else {
					mapOut.append('.');
				}
			}
			mapOut.append('\n');
		}
		return mapOut.toString();
	}
	
	public static class ShipPosition {
		int length;
		boolean down;
		int x; // topLeft
		int y;
		public ShipPosition(int length, boolean down, int x, int y) {
			super();
			this.length = length;
			this.down = down;
			this.x = x;
			this.y = y;
		}
		
		@Override
		public ShipPosition clone() {
			return new ShipPosition(length, down, x, y);
		}
	}
	
	public static class GameData2 {
		ArrayList<ShipPosition> known;
		ArrayList<int[]> missed;
		public GameData2() {
			super();
			this.known = new ArrayList<>();
			this.missed = new ArrayList<>();
		}
	}
	
	public static class GameData {
		boolean[][] grid = new boolean[10][10];
		int[][] pos = new int[ships.length][4];
		int[] hits = new int[ships.length];
		public String desc;
		public GameData() {}
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
    
    public static void GPD() {
    	int n = in.nextInt();
    	int nq = in.nextInt();
    	
    	int R = in.nextInt();
    	int key = in.nextInt();
    	int lastAnswer = 0;
    	
    	HashMap<Integer, Tree> treeList = new HashMap<>();
    	treeList.put(R, new Tree(R, key));
    	
    	for (int i = 0; i < n-1; i++) {
			int u = in.nextInt();
			int v = in.nextInt();
			int k = in.nextInt();
			Tree child = treeList.get(v).addChild(u, k);
	    	treeList.put(u, child);
    	}
    	
    	for (int i = 0; i < nq; i++) {
    		int type = in.nextInt() ^ lastAnswer;
    		if (type == 0) {
    			// new station
    			int v = in.nextInt() ^ lastAnswer;
    			int u = in.nextInt() ^ lastAnswer;
    			int k = in.nextInt() ^ lastAnswer;
//				System.out.println("q " + type + " v: " + v + " u: " + u + " k: " + k);
				Tree child = treeList.get(v).addChild(u, k);
		    	treeList.put(u, child);
    		} else {
    			int v = in.nextInt() ^ lastAnswer;
    			int k = in.nextInt() ^ lastAnswer;
//				System.out.println("q " + type + " v: " + v + " k: " + k);
    			Tree ref = treeList.get(v);
    			int a = ref.k;
    			int min = ref.k ^ k;
    			int b = ref.k;
    			int max = ref.k ^ k;
    			while(ref != null) {
//    				System.out.println("at idx " + ref.id);
    				if ((ref.k ^ k) < min) {
    					min = ref.k ^ k;
    					a = k;
    				}
    				if ((ref.k ^ k) > max) {
    					max = ref.k ^ k;
    					b = k;
    				}
    				ref = ref.parent;
    			}
    			lastAnswer = min ^ max;
    			System.out.println(min + " " + max);
    		}
		}
    }
    
    public static class Tree {
    	Tree parent;
    	ArrayList<Tree> children;
    	int id, k;
    	public Tree(int id, int k) {
    		children = new ArrayList<>();
    		this.id = id;
    		this.k = k;
    		parent = null;
    	}
    	
    	public Tree addChild(int id, int k) {
    		Tree child = new Tree(id, k);
    		children.add(child);
    		child.parent = this;
    		return child;
    	}
    }
 
    public static void WSITES01() {
        int n = in.nextInt();
        ArrayList<char[]> allowed = new ArrayList<>();
        ArrayList<char[]> blocked = new ArrayList<>();
        Trie root = new Trie('.');
        for (int i = 0; i < n; i++) {
            char s = in.next().charAt(0);
            if (s == '+') {
                allowed.add(in.next().toCharArray());
            } else {
                blocked.add(in.next().toCharArray());
            }
        }
        for (char[] a: allowed) {
            root.add(a);
        }
 
        TreeSet<String> rules = new TreeSet<>();
        boolean invalid = false;
        for (char[] a: blocked) {
            int idx = root.longestSubstring(a);
            if (idx == a.length) {
                invalid = true;
                break;
            }
            rules.add(new String(Arrays.copyOf(a, idx+1)));
        }
        if (invalid) {
            System.out.println(-1);
        } else {
            System.out.println(rules.size());
            for (String r: rules) {
                System.out.println(r);
            }
        }
    }
 
    public static class Trie {
        char value;
        Trie[] children;
 
        public Trie(char value) {
            this.value = value;
            children = new Trie[26];
        }
 
        public void add(char[] word) {
            this.add(word, 0);
        }
 
        private void add(char[] word, int idx) {
            if (idx == word.length) {
                return;
            }
            // System.out.println("adding " + word[idx]);
            if (children[word[idx] - 'a'] == null) {
                children[word[idx] - 'a'] = new Trie(word[idx]);
            }
            children[word[idx] - 'a'].add(word, idx +1);
        }
 
        public int longestSubstring(char[] word) {
            // return the first idx not found in tree, otherwise return word.length
            return longestSubstring(word, 0);
        }
 
        private int longestSubstring(char[] word, int idx) {
            if (idx == word.length) {
                return idx;
            }
 
            // System.out.println("searching " + word[idx]);
            if (children[word[idx] - 'a'] == null) {
                return idx;
            }
            return children[word[idx] - 'a'].longestSubstring(word, idx +1);
        }
    }
 
    static double EPS = 1E-6;
 
    public static long binarySearchWithDuplicates(double[] a, double target, int minBits) {
        long low = 1L << minBits;
        long high = (1L << (minBits +1)) -1;
        long lastValid = -1;
        while (low <= high) {
            long mid = (low + high) >>> 1;
            if (f(a, mid) <= target) {
                low = mid + 1;
                lastValid = mid;
            } else {
                high = mid - 1;
            }
        }
        return lastValid;
    }
 
    public static double f(double[] a, long mask) {
        double ret = 0;
        for (int i = 0; i < a.length; i++) {
            if (getBitL(mask, i)) {
                ret += a[i];
            }
        }
        return ret;
    }
 
    public static long clearBitL(long value, int idx) {
        return (value & ~(1L << idx));
    }
 
    public static long setBitL(long value, int idx) {
        return (value | (1L << idx));
    }
 
    public static boolean getBitL(long value, int idx) {
        return (value & (1L << idx)) != 0;
    }
 
    public static void CHEFCODE4() {
        int n = in.nextInt();
        long k = in.nextLong();
        long[] a = in.nextLongArray(n);
        
        System.out.println(CHEFCODE4(n, k, a));
    }

    public static long CHEFCODE4(int n, long k, long[] a) {
        double[] b = new double[n];
        double[] sumB = new double[n];
        Arrays.sort(b);
        for (int i = 0; i < b.length; i++) {
            b[i] = Math.log(a[i]);
            sumB[i] = b[i] + (i > 0 ? sumB[i-1] : 0);
        }
 
        double p = Math.log(k);
        long ret = 0;
        if (sumB[n-1] < p + EPS) {
            ret = (1L << n) -1;
        } else {
            ret += dfs2(0, 0, b, p, sumB);
        }
        return ret;
    }
 
    public static long dfs2(double sum, int idx, double[] b, double p, double[] sumB) {
        long ret = 0;
 
//        System.out.println("depth: " + idx + "\t sum:" + (long)Math.round(Math.exp(sum)));
        if (sum >= p - EPS) {
            // more than K: invalid
            return 0;
        }
        if (idx == b.length) {
            return 0;
        }
        if (sum + b[idx] <= p + EPS) {
            ret = 1;
        }
        // all future options are possible in the range [idx+1, n]
        long sx = 0;
        if (sum > EPS && idx < b.length -1 && sumB[b.length-1] - sumB[idx] + sum + b[idx] < p + EPS) {
            sx = 2L * ((1L << (b.length-1 - idx)) -1L);
            return sx + ret;
        }
 
        long s1 = dfs2(sum, idx+1, b, p, sumB);
        long s2 = 0;
        if (s1 != 0) {
//            System.out.println("                              branching at depth: " + idx + "\t sum:" + (long)Math.round(Math.exp(sum)));
            s2 = dfs2(sum + b[idx], idx+1, b, p, sumB);
        }
        if (sx != 0) {
        	if (sx != s1 + s2) {
        		System.out.println("fail");
        		System.currentTimeMillis();
        	}
        }
 
        return ret + s1 + s2;
    }
 
    public static void CHEFSUBA() {
        int n = in.nextInt();
        int k = in.nextInt();
        in.nextInt();
        int[] a = in.nextIntArray(n);
        char[] q = in.next().toCharArray();
 
        k = Math.min(k, n);
 
        int[] total = new int[n];
        int start = 0;
        for (int i = 0; i < k; i++) {
            start += a[i];
        }
        total[0] = start;
        for (int i = 1; i < n; i++) {
            total[i] = total[i-1] - a[i-1] + a[(i + k -1) % n];
        }
        int left = 0;
        int right = n-k;
        SegmentTree st = new SegmentTree(total);
 
        for (int i = 0; i < q.length; i++) {
            if (q[i] == '?') {
                int max = 0;
                if (left < right) {
                    max = st.get(left, right);
                } else {
                    max = Math.max(st.get(0, right), st.get(left, n-1));
                }
                System.out.println(max);
//				System.out.println(left + " " + right + " " + max);
            } else {
                left = (left - 1 + n) % n;
                right = (right - 1 + n) % n;
            }
        }
    }
 
    public static class SegmentTree {
        private int[][] t;
        private int[] a;
        private int N;
        private int n;
 
        protected int function(int a, int b) {
            return Math.max(a, b);
        }
 
        protected int IDENTITY = 0;
 
        public SegmentTree(int[] b) {
            n = (int) (Math.log10(b.length)/Math.log10(2))+1;
            N = 1 << n;
            this.a = new int[N];
            for (int i = 0; i < b.length; i++) {
                this.a[i] = b[i];
            }
            t = new int[N][n+1];
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
        public void set(int i, int v) {
            t[i][0] = a[i] = v;
            for (int y = 1; y <= n; y++) {
                int x = i-(i&((1<<y)-1));
                t[x][y] = function(t[x][y-1], t[x+(1<<(y-1))][y-1]);
            }
        }
 
        /**
         * Get the function over the interval [a, b]. Time: O(log n)
         */
        public int get(int i, int j) {
            int res = IDENTITY, height = 0; j++;
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
 
    public static void MXMEDIAN() {
        int tests = in.nextInt();
        for (int t = 0; t < tests; t++) {
            int N = in.nextInt();
            int[] a = in.nextIntArray(N*2);
            Arrays.sort(a);
            int[] best = new int[2*N];
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < N*2; i+=2) {
                best[i] = a[i/2];
                best[i+1] = a[N+i/2];
                sb.append(best[i] + " " + best[i+1] + " ");
            }
            int max = Math.max(best[N], best[N-1]);
            System.out.println(max);
            System.out.println(sb);
        }
    }
 
    public static void UNICOURS() {
        int tests = in.nextInt();
        for (int t = 0; t < tests; t++) {
            int N = in.nextInt();
            int[] a = in.nextIntArray(N);
            int max = 0;
            for (int i = 0; i < a.length; i++) {
                max = Math.max(max, a[i]);
            }
            System.out.println(N - max);
        }
    }
 
    public static void CHEFROUT() {
        int tests = in.nextInt();
        for (int t = 0; t < tests; t++) {
            char[] s = in.next().toCharArray();
            int[] a = new int[s.length];
            for (int i = 0; i < s.length; i++) {
                if (s[i] == 'C') {
                    a[i] = 0;
                } else if (s[i] == 'E') {
                    a[i] = 1;
                } else {
                    a[i] = 2;
                }
            }
            boolean valid = true;
            for (int i = 1; i < a.length; i++) {
                if (a[i-1] > a[i]) {
                    valid = false;
                }
            }
            if (valid) {
                System.out.println("yes");
            } else {
                System.out.println("no");
            }
        }
    }
 
    public static class InputReader {
        public BufferedReader r;
        public StringTokenizer st;
        public InputReader(InputStream s) {r = new BufferedReader(new InputStreamReader(s), 32768); st = null;}
        public String next() {while (st == null || !st.hasMoreTokens()) {try {st = new StringTokenizer(r.readLine());}
        catch (IOException e) {throw new RuntimeException(e);}} return st.nextToken();}
        public int nextInt() {return Integer.parseInt(next());}
        public long nextLong() {return Long.parseLong(next());}
        public double nextDouble() {return Double.parseDouble(next());}
        public long[] nextLongArray(int n) {long[] a = new long[n]; for (int i = 0; i < a.length; i++) {a[i] = this.nextLong();} return a;}
        public int[] nextIntArray(int n) {int[] a = new int[n];	for (int i = 0; i < a.length; i++) {a[i] = this.nextInt();} return a;}
    }
}
  