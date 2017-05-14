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
 
public class Main {
    public static InputReader in;
    public static PrintWriter out;
	
	public static final int[] ships = {4, 3, 3, 2, 2, 2, 1, 1, 1, 1};
	//public static final int[] dx = {1, 1, 1, 0, -1, -1, -1, 0,};
	//public static final int[] dy = {1, 0, -1, -1, -1, 0, 1, 1,};
	public static final int[] dx = {1, 0, -1, 0,};
	public static final int[] dy = {0, -1, 0, 1,};
	
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
        testCHEFBATL();
 
        out.close();
    }
    
	public static void testCHEFBATL() {
		Random rand = new Random(0);
		for (int testBig = 0; testBig < 5; testBig++) {
			int numTests = 10000;
			int[] scores = new int[numTests];
			double mean = 0;
			double stdev = 0;
			for (int test = 0; test < numTests; test++) {
				String myMap =    "........#.\n"
						+ ".###......\n"
						+ "..........\n"
						+ "##..#..#..\n"
						+ ".......#..\n"
						+ "..#....#..\n"
						+ "#.#.......\n"
						+ "#.........\n"
						+ "...####..#\n"
						+ ".#........\n";
				
				final int N = 10;
				char[][] seenMap = new char[N][N];
				for (int y = 0; y < N; y++) {
					Arrays.fill(seenMap[y], '-');
				}
				
				char[][] realMap = new char[N][N];
				int idx = 0;
				for (int y = 0; y < N; y++) {
					Arrays.fill(realMap[y], '.');
				}
				for (int y = 0; y < N; y++) {
					for (int x = 0; x < N+1; x++) {
						if (myMap.charAt(y * (N+1) + x) == '#') {
							realMap[idx/N][idx%N] = '#';
						}
						if (x < N) {
							idx++;
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
					
					int[] move = getMove(seenMap, rand);
					int mx = move[0];
					int my = move[1];
					if (realMap[my][mx] != '.') {
						realMap[my][mx] = 'h';
						hp--;
						int nn = 0;
						for (int i = 0; i < dx.length; i++) {
							int tx = mx + dx[i];
							int ty = my + dy[i];
							if (0 <= tx && tx < N && 0 <= ty && ty < N && realMap[ty][tx] == '#') {
								nn++;
							}
						}
						if (nn == 0) {
							// sunk
							seenMap[my][mx] = 'h';
							fillHit(seenMap, mx, my, 'h');
							fillHit(realMap, mx, my, 'h');
						} else {
							// hit
							seenMap[my][mx] = 'h';
						}
					} else {
						seenMap[my][mx] = 'm';
					}
					
					moves += 2;
				}
				int score = (200 - moves) * 100;
				scores[test] = score;
				mean += score;
			}
			mean /= numTests;
			for (int i = 0; i < scores.length; i++) {
				stdev += (scores[i] - mean) * (scores[i] - mean);
			}
			stdev /= numTests;
			stdev = Math.sqrt(stdev);
			
			System.out.println("mean: " + String.format("%.2f", mean) + " stdev: " + String.format("%.2f", stdev));
			
		}
	}
    
	public static void CHEFBATL() {
		Random rand = new Random(0);
		for (int test = 0; test < 100; test++) {
			System.out.println(1);
			//System.out.println(placeShips(rand, me));
			String myMap =    "........#.\n"
							+ ".###......\n"
							+ "..........\n"
							+ "##..#..#..\n"
							+ ".......#..\n"
							+ "..#....#..\n"
							+ "#.#.......\n"
							+ "#.........\n"
							+ "...####..#\n"
							+ ".#........\n";
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
			//System.out.println(placeShips(rand, me));
			System.out.println(myMap);
			GameData game = new GameData();
			final int N = 10;
			char[][] map = new char[N][N];
			for (int y = 0; y < N; y++) {
				for (int x = 0; x < N; x++) {
					map[y][x] = '-';
				}
			}
			
			int[] move = getMove(map, rand);
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
					p1move = false;
				} else if (reply == 2) {
					// hit
					map[move[1]][move[0]] = 'h';
					p1move = true;
				} else if (reply == 3) {
					// sunk
					map[move[1]][move[0]] = 'h';
					fillHit(map, move[0], move[1], 'h');
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
					move = getMove(map, rand);
					System.out.println((move[0]+1) + " " + (move[1]+1));
				}
			}	
		}
		System.out.println("0");
	}
	
	public static void fillHit(char[][] map, int sx, int sy, char marker) {
		int[] top = new int[]{sx, sy};
		int[] mdx = {0, 1, 0, -1};
		int[] mdy = {1, 0, -1, 0};
		int N = 10;
		Stack<int[]> s = new Stack<>();
		s.add(top);
		while (!s.isEmpty()) {
			top = s.pop();
			int tx = top[0];
			int ty = top[1];
			if (0 <= tx && tx < N && 0 <= ty && ty < N && map[ty][tx] == marker) {
				map[ty][tx] = 'd';
				for (int i = 0; i < mdy.length; i++) {
					s.add(new int[]{tx + mdx[i], ty + mdy[i]});
				}
			}
		}
	}

	public static int[] getMove(char[][] map, Random rand) {
//		if (System.currentTimeMillis() != -1) {
//			return new int[]{0, 0};
//		}
		int hits = 0;
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map.length; x++) {
				if (map[y][x] == 'h') {
					hits++;
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

		// Hit every odd square to find the destroyers. Prefer cells with more unknown neighbors.
		ArrayList<int[]> possible = new ArrayList<>();
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
		
		// Submarines have been removed, so hitting every odd cell will finish the game.
//		if (possible.isEmpty()) {
//			for (int y = 0; y < map.length; y++) {
//				for (int x = 0; x < map.length; x++) {
//					if (map[y][x] == '-') {
//						possible.add(new int[]{x, y});
//					}
//				}
//			}
//		}
		return possible.get(rand.nextInt(possible.size()));
	}
	
	public static String placeShips(Random rand, GameData data) {
		ArrayList<String> positions = new ArrayList<>();
		for (int i = 0; i < ships.length; i++) {
			while (true) {
				System.out.println(getStringMap(data));
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
						} else {
							for (int dir = 0; dir < dx.length; dir++) {
								final int ny = y + dy[dir];
								final int nx = x + dx[dir];
								if (!(nx >= 0 && nx < 10 && ny >= 0 && ny < 10 && !data.grid[ny][nx])) {
									canPlace = false;
									break;
								}
							}
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
		return getStringMap(data);
	}

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
  