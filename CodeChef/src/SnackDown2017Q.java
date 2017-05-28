import java.io.*;
import java.util.*;

public class SnackDown2017Q {
    public static InputReader in;
    public static PrintWriter out;
	public static final boolean debug = false;
 
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        in = new InputReader(inputStream);
        out = new PrintWriter(outputStream, false);
        
        SAMESNAK();
        
        out.close();
    }
    
    public static class Point {
    	int x, y;
    	Point(int x, int y) {
    		this.x = x;
    		this.y = y;
    	}
    	public String toString() {
    		return "(" + x + ", " + y + ")";
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
			Point other = (Point) obj;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			return true;
		}
    }

    public static void SAMESNAK() {
        int tests = in.nextInt();
        for (int t = 0; t < tests; t++) {
        	Point[] p = new Point[4];
        	for (int i = 0; i < p.length; i++) {
        		p[i] = new Point(in.nextInt(), in.nextInt());
			}
        	
        	// compress
        	TreeMap<Integer, Integer> x = new TreeMap<>();
        	TreeMap<Integer, Integer> y = new TreeMap<>();
        	for (int i = 0; i < p.length; i++) {
				x.put(p[i].x, 0);
				y.put(p[i].y, 0);
			}
        	int idx = 0;
        	for (Integer key: x.keySet()) {
        		x.replace(key, idx++);
        	}
        	idx = 0;
        	for (Integer key: y.keySet()) {
        		y.replace(key, idx++);
        	}
        	for (int i = 0; i < p.length; i++) {
        		p[i].x = x.get(p[i].x);
        		p[i].y = y.get(p[i].y);
			}
        	
        	// draw the map
        	int[][] m = new int[6][6];
        	HashSet<Point> s1 = new HashSet<>(); 
        	HashSet<Point> s2 = new HashSet<>();
        	for (int i = 0; i < p.length; i+=2) {
        		if (p[i].x == p[i+1].x) {
        			// horizontal
        			for (int j = Math.min(p[i].y, p[i+1].y); j <= Math.max(p[i].y, p[i+1].y); j++) {
        				m[1 + j][1 + p[i].x] += i/2 +1;
        				Point np = new Point(p[i].y, j);
        				if (i < 2) {
        					s1.add(np);	
        				} else {
        					s2.add(np);
        				}
					}
        		} else {
        			for (int j = Math.min(p[i].x, p[i+1].x); j <= Math.max(p[i].x, p[i+1].x); j++) {
        				m[1 + p[i].y][1 + j] += i/2 +1;
        				Point np = new Point(j, p[i].y);
        				if (i < 2) {
        					s1.add(np);	
        				} else {
        					s2.add(np);
        				}
					}
        		}
        	}
        	
        	int mapSize = 0;
        	int[] typeSize = {0, 0, 0, 0};

        	if (debug) {
	        	for (int ym = 1; ym < m.length-1; ym++) {
	        		for (int xm = 1; xm < m.length-1; xm++) {
	        			System.out.print(m[ym][xm]);
	        		}
	        		System.out.println();
	        	}
	    		System.out.println();
	    		System.out.println();
        	}

        	for (int ym = 1; ym < m.length-1; ym++) {
        		for (int xm = 1; xm < m.length-1; xm++) {
        			if (m[ym][xm] != 0) {
        				mapSize++;
        			}
        			typeSize[m[ym][xm]]++;
        		}
        	}
        	
        	// count max degree 
        	int[] dx = {0, 1, 0, -1};
        	int[] dy = {1, 0, -1, 0};
        	int maxNeighbours = 0;
        	for (int ym = 1; ym < m.length-1; ym++) {
        		for (int xm = 1; xm < m.length-1; xm++) {
        			int numNeighbours = 0;
        			for (int i = 0; i < dy.length; i++) {
        				Point np = new Point(xm + dx[i], ym + dy[i]);
        				if (m[np.y][np.x] != 0) {
        					numNeighbours++;
        				}
					}
        			maxNeighbours = Math.max(maxNeighbours, numNeighbours);
        		}
			}

        	int[][] visited = new int[6][6];
        	
        	// check connectedness
        	Point top = new Point(p[0].x+1, p[0].y+1);
        	int numVisited = 0;
        	Stack<Point> s = new Stack<>();
        	s.push(top);
        	while (!s.isEmpty()) {
        		top = s.pop();
        		if (m[top.y][top.x] == 0 || visited[top.y][top.x] == 1) {
        			continue;
        		}
        		visited[top.y][top.x] = 1;
        		numVisited++;

    			for (int i = 0; i < dy.length; i++) {
    				Point np = new Point(top.x + dx[i], top.y + dy[i]);
    				// may not move from 1 -> 2
    				if (!((m[top.y][top.x] == 1 && m[np.y][np.x] == 2) || (m[top.y][top.x] == 2 && m[np.y][np.x] == 1))) {
    					s.push(np);
    				}
				}
        	}
        	
        	s1.retainAll(s2);
        	
        	if (maxNeighbours <= 2 && numVisited == mapSize) {
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
  