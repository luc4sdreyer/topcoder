import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.ReadOnlyBufferException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.TreeSet;


public class R300 {
	public static InputReader in;
	public static PrintWriter out;
	public static Random ra = new Random(0);

	public static void main(String[] args) {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		in = new InputReader(inputStream);
		out = new PrintWriter(outputStream, true);

//		CuttingBanner();
//		QuasiBinary();
//		TouristsNotes();
		WeirdChess();
//		WeirdChess2();
//		testWeirdChess();

		out.close();
	}

	public static boolean WeirdChess2(int N, char[][] map) {
		
		ArrayList<int[]> agents = new ArrayList<>();
		boolean[][] threat = new boolean[N][N];
		boolean[][] impossible = new boolean[N][N];
		boolean[][] moveSet = new boolean[2*N-1][2*N-1];
		for (int i = 0; i < moveSet.length; i++) {
			Arrays.fill(moveSet[i], true);
		}
		boolean invalid = false;
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map.length; x++) {
				if (map[y][x] == 'o') {
					agents.add(new int[]{x, y});
				}
				if (map[y][x] != '.') {
					threat[y][x] = true;
				}
			}
		}
		
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map.length; x++) {
				if (!threat[y][x]) {
					for (int[] agent2: agents) {
						boolean impossibl = false;
						int dx = x - agent2[0];
						int dy = y - agent2[1];
						for (int[] agent: agents) {
							int nx = agent[0] + dx;
							int ny = agent[1] + dy;
							if (nx >= 0 && nx < N && ny >= 0 && ny < N) {
								if (!threat[ny][nx]) {
									impossibl = true;
								}
							}
						}
						if (impossibl) {
							moveSet[N-1 + dy][N-1 + dx] = false;
						}
					}
				}
			}
		}
		
		char[][] newMap = new char[N][N];
		for (int y = 0; y < newMap.length; y++) {
			for (int x = 0; x < newMap.length; x++) {
				newMap[y][x] = '.';
			}
		}
		for (int[] a: agents) {
			for (int y = 0; y < moveSet.length; y++) {
				for (int x = 0; x < moveSet.length; x++) {
					if (moveSet[y][x]) {
						int nx = a[0] + x - (N-1);
						int ny = a[1] + y - (N-1);
						if (nx >= 0 && nx < N && ny >= 0 && ny < N) {
							newMap[ny][nx] = 'x';
						}
					}
				}
			}
		}
		for (int[] a: agents) {
			int nx = a[0];
			int ny = a[1];
			newMap[ny][nx] = 'o';
		}
		
		
		for (int i = 0; i < newMap.length; i++) {
//			System.out.println(new String(newMap[i]));
			if (!Arrays.equals(newMap[i], map[i])) {
				invalid = true;
			} 
		}
		
		if (invalid) {
			System.out.println("NO");
		} else {
			System.out.println("YES");
			char[][] out = new char[moveSet.length][moveSet.length]; 
			for (int y = 0; y < moveSet.length; y++) {
				for (int x = 0; x < moveSet.length; x++) {
					if (moveSet[y][x]) {
						out[y][x] = 'x';
					} else {
						out[y][x] = '.';
					}
				}
			}
			out[N-1][N-1] = 'o';
			for (int y = 0; y < out.length; y++) {
				System.out.println(new String(out[y]));
			}
		}
		return !invalid;
	}
	
	public static void testWeirdChess() {
		Random rand = new Random(0);
		for (int test = 0; test < 10000; test++) {
			int N = rand.nextInt(10)+1;
			int moves = rand.nextInt(N*N)+1;
			int numAgents = rand.nextInt(N*N)+1;
			int[][] mS = new int[moves][2];
			
			for (int i = 0; i < moves; i++) {
				mS[i][0] = rand.nextInt(N*2 - 1) - (N-1);
				mS[i][1] = rand.nextInt(N*2 - 1) - (N-1);
			}
			int[][] agents = new int[numAgents][2];
			for (int i = 0; i < numAgents; i++) {
				agents[i][0] = rand.nextInt(N);
				agents[i][1] = rand.nextInt(N);
			}
			
			char[][] m = new char[N][N];
			for (int y = 0; y < N; y++) {
				for (int x = 0; x < N; x++) {
					m[y][x] = '.';
				}
			}
			
			for (int i = 0; i < agents.length; i++) {
				for (int j = 0; j < mS.length; j++) {
					int nx = agents[i][0] + mS[j][0];
					int ny = agents[i][1] + mS[j][1];
					if (nx >= 0 && nx < N && ny >= 0 && ny < N) {
						m[ny][nx] = 'x';
					}
				}
			}
			for (int i = 0; i < agents.length; i++) {
				int nx = agents[i][0];
				int ny = agents[i][1];
				m[ny][nx] = 'o';
			}
			boolean res = WeirdChess(N, m);
			if (!res) {
				System.currentTimeMillis();
			}
		}
	}
	
	public static void WeirdChess() {
		int N = in.nextInt();
		char[][] map = new char[N][];
		for (int i = 0; i < N; i++) {
			map[i] = in.next().toCharArray();
		}
		WeirdChess2(N, map);
	}
	
	public static boolean WeirdChess(int N, char[][] map) {
		
		ArrayList<int[]> agents = new ArrayList<>();
		boolean[][] threat = new boolean[N][N];
		boolean[][] explained = new boolean[N][N];
		boolean[][] moveSet = new boolean[2*N-1][2*N-1];
		boolean invalid = false;
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map.length; x++) {
				if (map[y][x] == 'o') {
					agents.add(new int[]{x, y});
				}
				if (map[y][x] != '.') {
					threat[y][x] = true;
				}
			}
		}
		
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map.length; x++) {
				if (threat[y][x]) {
					if (explained[y][x]) {
						continue;
					}
					boolean someValid = false;
					
					for (int[] agent2: agents) {
						boolean agentInvalid = false;
						int dx = x - agent2[0];
						int dy = y - agent2[1];
						boolean subInvalid = false;
	
						if (explained[y][x]) {
							for (int[] agent: agents) {
								int nx = agent[0] + dx;
								int ny = agent[1] + dy;
								if (nx >= 0 && nx < N && ny >= 0 && ny < N) {
									if (!threat[ny][nx]) {
										subInvalid = true;
									}
								}
							}
						}
						
						if (!subInvalid) {
							for (int[] agent: agents) {
								int nx = agent[0] + dx;
								int ny = agent[1] + dy;
								if (nx >= 0 && nx < N && ny >= 0 && ny < N) {
									if (!threat[ny][nx]) {
										agentInvalid = true;
									}
								}
							}
							
							if (!agentInvalid) {
								someValid = true;
								for (int[] agent: agents) {
									int nx = agent[0] + dx;
									int ny = agent[1] + dy;
									if (nx >= 0 && nx < N && ny >= 0 && ny < N) {
										if (threat[ny][nx]) {
											explained[ny][nx] = true;
										}
									}
								}
								moveSet[N-1 + dy][N-1 + dx] = true;
								break;
							}
						}
					}
					
					if (!someValid) {
						invalid = true;
					}
				}
			}
		}
		
		if (invalid) {
			System.out.println("NO");
		} else {
			System.out.println("YES");
			char[][] out = new char[moveSet.length][moveSet.length]; 
			for (int y = 0; y < moveSet.length; y++) {
				for (int x = 0; x < moveSet.length; x++) {
					if (moveSet[y][x]) {
						out[y][x] = 'x';
					} else {
						out[y][x] = '.';
					}
				}
			}
			out[N-1][N-1] = 'o';
			for (int y = 0; y < out.length; y++) {
				System.out.println(new String(out[y]));
			}
		}
		return !invalid;
	}
	
	public static void TouristsNotes() {
		long days = in.nextInt();
		int m = in.nextInt();
		long[] d = new long[m];
		long[] h = new long[m];
		for (int i = 0; i < m; i++) {
			d[i] = in.nextLong();
			h[i] = in.nextLong();
		}
		
		boolean valid = true;
		long max = 0;
		for (int i = 1; i < m; i++) {
			long xa = d[i-1];
			long xb = d[i];
			long ca = h[i-1] - xa;
			long cb = h[i] + xb;
			long maxX = (cb - ca) / 2;
			if (xa <= maxX && maxX <= xb) {
				// valid
				long maxY = maxX + ca;
				max = Math.max(max, maxY);
			} else {
				valid = false;
			}
		}
		// start and end
		max = Math.max(max, h[0] + d[0] - 1);
		max = Math.max(max, h[m-1] + (days-d[m-1]));
		
		if (valid) {
			System.out.println(max);
		} else {
			System.out.println("IMPOSSIBLE");
		}
	}
	
	public static void QuasiBinary() {
		int a = in.nextInt();
		char[] x = (a + "").toCharArray();
		ArrayList<Integer> nums = new ArrayList<>(); 
		while (true) {
			char[] out = new char[x.length];
			int size = 0;
			for (int i = 0; i < x.length; i++) {
				if (x[i] >= '1') {
					out[i] = '1';
					x[i]--;
					size++;
				} else {
					out[i] = '0';	
				}
			}
			if (size == 0) {
				break;
			}
			nums.add(Integer.parseInt(new String(out)));
		}
		
		System.out.println(nums.size());
		for (int i = 0; i < nums.size(); i++) {
			System.out.print(nums.get(i) + " ");
		}
	}

	public static void CuttingBanner() {
		char[] code = "CODEFORCES".toCharArray();
		char[] s = in.next().toCharArray();
		boolean valid = false;
		
		for (int start = 0; start < s.length; start++) {
			for (int end = start; end < s.length; end++) {
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < s.length; i++) {
					if (i < start || i > end) {
						sb.append(s[i]);
					}
				}
//				System.out.println(sb.toString());
				if (sb.toString().equals(new String(code))) {
					valid = true;
				}
			}
		}
		
		if (valid) {
			System.out.println("YES");
		} else {
			System.out.println("NO");
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
