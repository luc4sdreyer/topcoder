import java.io.*;
import java.math.*;
import java.util.*;
import java.util.Map.Entry;

//  world-codesprint-11

public class WorldCodeSprint11 {
	public static InputReader in;
	public static PrintWriter out;
	public static Random ra = new Random(0);

	public static void main(String[] args) {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		in = new InputReader(inputStream);
//		out = new PrintWriter(outputStream, true);
		out = new PrintWriter(outputStream, false); // enable this for ludicrous speed
		
//		balancedArray();
//		numericString();
//		simpleFileCommands();
		cityConstruction();
		
		out.close();
	}
	
	public static void cityConstruction() {
		int N = in.nextInt();
		int M = in.nextInt();
		if (N > 5000) {
			return;
		}
		
		HashMap<Integer, HashSet<Integer>> g = new HashMap<>();
		HashMap<Integer, HashSet<Integer>> visits = new HashMap<>();
		
		for (int i = 0; i < N; i++) {
			g.put(i+1, new HashSet<Integer>());
		}
		
		for (int i = 0; i < M; i++) {
			int u = in.nextInt();
			int v = in.nextInt();
			g.get(u).add(v);
		}
		
		int Q = in.nextInt();
		for (int i = 0; i < Q; i++) {
			int type = in.nextInt();
			if (type == 1) {
				int x = in.nextInt();
				int d = in.nextInt();
				N++;
				g.put(N, new HashSet<Integer>());

				if (d == 0) {
					g.get(x).add(N);
				} else {
					g.get(N).add(x);
				}
			} else {
				int x = in.nextInt();
				int y = in.nextInt();
				
				if (visits.containsKey(x) && visits.get(x).contains(y)) {
					System.out.println("Yes");
				} else {
					HashSet<Integer> visited = new HashSet<>();
					Stack<Integer> s = new Stack<>();
					int top = x;
					s.add(top);
					while (!s.isEmpty()) {
						top = s.pop();
						if (visited.contains(top)) {
							continue;
						}
						visited.add(top);
						if (top == y) {
							break;
						}
						if (visits.containsKey(top) && visits.get(top).contains(y)) {
							visited.addAll(visits.get(top));
							break;
						}
						for (Integer n: g.get(top)) {
							if (!visited.contains(n)) {
								s.add(n);
							}
						}
					}
					
					if (visits.containsKey(x)) {
						visited.addAll(visits.get(x));
					}
					visits.put(x, visited);
					if (visited.contains(y)) {
						System.out.println("Yes");
					} else {
						System.out.println("No");
					}
				}
			}
		}
	}
	
	public static void simpleFileCommands() {
		int q = in.nextInt();
		HashMap<String, RangeUnion> map = new HashMap<>();
		
		for (int i = 0; i < q; i++) {
			char[] cmd = in.next().toCharArray();
			if (cmd[0] == 'c') {
				// create
				String name = in.next();
				name = create(map, name);
				System.out.println("+ " + name);
			} else if (cmd[0] == 'd') {
				// delete
				String name = in.next();
				name = delete(map, name);
				System.out.println("- " + name);
			} else {
				// rename
				String oldName = in.next();
				String newName = in.next();
				String oldName2 = delete(map, oldName);
				String newName2 = create(map, newName);
				System.out.println("r " + oldName2 + " -> " + newName2);
			}
		}
	}

	private static String delete(HashMap<String, RangeUnion> map, String name) {
		long id = 0;
		if (name.contains("(")) {
			id = Long.parseLong(name.substring(name.indexOf('(') +1, name.indexOf(')')));
			name = name.substring(0, name.indexOf('('));
		}
		RangeUnion range = map.get(name);
		range.remove(id, id);
		if (id > 0) {
			name += "(" + id + ")";
		}
		return name;
	}

	private static String create(HashMap<String, RangeUnion> map, String name) {
		if (!map.containsKey(name)) {
			map.put(name, new RangeUnion());
		}
		RangeUnion range = map.get(name);
		long id = range.ceilingGap(0);
		range.add(id, id);
		if (id > 0) {
			name += "(" + id + ")";
		}
		return name;
	}
	
	
	
	public static class RangeUnion {
		TreeMap<Long, Long> range;
		
		public RangeUnion() {
			range = new TreeMap<>();
		}
		

		/**
		 * Add the range [a, b]. Overlap is ignored.
		 */
		public void add(long a, long b) {
			// If a range covers [a, b], do nothing
			Entry<Long, Long> lt = range.lowerEntry(a);
			if (lt != null && lt.getValue() > b) {
				return;
			}

			Entry<Long, Long> gte = range.ceilingEntry(a);
	
			// remove ranges that are entirely in [a, b]
			while (gte != null && gte.getValue() <= b) {
				range.remove(gte.getKey());
				gte = range.ceilingEntry(a);
			}
			
			// merge the range that ends in [a-1, b], if it exists
			lt = range.lowerEntry(a);
			if (lt != null && lt.getValue() >= a-1) {
				range.put(lt.getKey(), b);
				a = lt.getKey();
			}
			
			// remove the range that starts in [a, b+1], if it exists
			Entry<Long, Long> gt = range.floorEntry(b+1);
			if (gt != null && gt.getKey() >= a) {
				range.remove(gt.getKey());
				range.put(a, gt.getValue());
			}

			if (!isSet(a)) {
				range.put(a, b);	
			}
		}
		
		/**
		 * Remove the range [a, b].
		 */
		public void remove(long a, long b) {
			Entry<Long, Long> gte = range.ceilingEntry(a);
	
			// remove ranges that are entirely in [a, b]
			while (gte != null && gte.getValue() <= b) {
				range.remove(gte.getKey());
				gte = range.ceilingEntry(a);
			}
			
			// remove the range that covers [a, b], if it exists
			Entry<Long, Long> lt = range.lowerEntry(a);
			if (lt != null && lt.getValue() > b) {
				range.put(lt.getKey(), a-1);
				range.put(b+1, lt.getValue());
			}
			
			// remove the range that ends in [a, b], if it exists
			lt = range.lowerEntry(a);
			if (lt != null && lt.getValue() >= a) {
				range.put(lt.getKey(), a-1);
			}
			
			// remove the range that starts in [a, b], if it exists
			Entry<Long, Long> gt = range.floorEntry(b);
			if (gt != null && gt.getKey() >= a) {
				range.remove(gt.getKey());
				range.put(b+1, gt.getValue());
			}
		}
		
		/**
		 * Get the first gap >= x. That is the first point >= x where there is no range set.
		 */
		public long ceilingGap(long x) {
			Entry<Long, Long> lte = range.floorEntry(x);
			if (lte == null) {
				return x;
			} else {
				return Math.max(lte.getValue()+1, x);
			}
		}
		
		/**
		 * Return whether there is a range [a, b] such that a <= x <= b  
		 */
		public boolean isSet(long x) {
			Entry<Long, Long> lte = range.floorEntry(x);
			if (lte == null) {
				return false;
			} else {
				return x <= lte.getValue();
			}
		}
		
		public long[] getRange(long x) {
			Entry<Long, Long> entry = getRangeEntry(x);
			if (entry == null) {
				return null;
			} else {
				return new long[]{entry.getKey(), entry.getValue()};
			}
		}
		
		private Entry<Long, Long> getRangeEntry(long x) {
			Entry<Long, Long> lte = range.floorEntry(x);
			if (lte != null && x <= lte.getValue()) {
				return lte;
			}
			return null;
		}
		
		public String toString() {
			return range.toString();
		}
	}
	
	public static void numericString() {
		char[] s = in.next().toCharArray();
		int k = in.nextInt();
		int b = in.nextInt();
		int m = in.nextInt();
		
		long total = 0;
		int num = 0;
		int power = 1;
		for (int i = k-1; i >= 0; i--) {
			num = (num + (s[i] - '0') * power) % m;
			power = (power * b) % m;
		}
		
//		System.out.println(num);
		total += num;
		
		for (int i = 0; i < s.length -k; i++) {
			num = (num * b) % m;
			int remove = ((s[i] - '0') * power) % m;
			num = (num + m - remove + (s[i+k] - '0')) % m;
//			System.out.println(num);
			total += num;
		}
		System.out.println(total);
	}
	
	public static void balancedArray() {
		int[] a = in.nextIntArray(in.nextInt());
		int sumA = 0;
		int sumB = 0;
		for (int i = 0; i < a.length; i++) {
			if (i < a.length/2) {
				sumA += a[i];
			} else {
				sumB += a[i];
			}
		}
		System.out.println(Math.abs(sumA - sumB));
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
