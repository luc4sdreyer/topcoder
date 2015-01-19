import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Random;
import java.util.StringTokenizer;


public class Jan15Short {
	public static void main(String[] args) {
		//surajGoesShopping(System.in);
		//tojoHatesProbabilities(System.in);
		//randomRudreshwarTest();
		abhijeetAndRectangles(System.in);
	}
	
	public static class Node {
		private ArrayList<Integer> idx;
		private int v;
		public Node(ArrayList<Integer> idx, int v) {
			super();
			this.idx = idx;
			this.v = v;
		}
	}
	
	public static void abhijeetAndRectangles(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int t = scan.nextInt();
		
		for (int i = 0; i < t; i++) {
			int n = scan.nextInt();
			int m = scan.nextInt();
			
			int[][] rect = new int[n][2];
			for (int j = 0; j < n; j++) {
				for (int k = 0; k < rect[0].length; k++) {
					rect[j][k] = scan.nextInt();
				}
			}
			
			int[][] temp = new int[n][2];
			for (int j = 0; j < n; j++) {
				temp[j][0] = rect[j][0];
				temp[j][1] = j;
			}
			Arrays.sort(temp, new Comparator<int[]>(){
				@Override
				public int compare(int[] o1, int[] o2) {
					return Integer.compare(o1[0], o2[0]);
				}
			});
			ArrayList<Node> compX = new ArrayList<>();
			for (int j = 0; j < n; j++) {
				int xv = temp[j][0];
				Node next = new Node(new ArrayList<Integer>(), xv);
				while (j < n && xv == temp[j][0]) {
					next.idx.add(j);
					j++;
				}
				j--;
				compX.add(next);
			}
			
			temp = new int[n][2];
			for (int j = 0; j < n; j++) {
				temp[j][0] = rect[j][1];
				temp[j][1] = j;
			}
			Arrays.sort(temp, new Comparator<int[]>(){
				@Override
				public int compare(int[] o1, int[] o2) {
					return Integer.compare(o1[0], o2[0]);
				}
			});
			ArrayList<Node> compY = new ArrayList<>();
			for (int j = 0; j < n; j++) {
				int xv = temp[j][0];
				Node next = new Node(new ArrayList<Integer>(), xv);
				while (j < n && xv == temp[j][0]) {
					next.idx.add(j);
					j++;
				}
				j--;
				compY.add(next);
			}
			
			long area = compX.get(0).v * compY.get(0).v;
			int x = 0;
			int y = 0;
			while (true) {
				if (x < compX.size() && y < compY.size()) {
					if (Math.min(compX.get(x).idx.size(), compX.get(x).idx.size()) <= m) {
						if (compX.get(x).idx.size() < compY.get(y).idx.size()) {
							x++;
							m -= compX.get(x).idx.size();
						} else if (compX.get(x).idx.size() > compY.get(y).idx.size()) {
							x++;
							m -= compX.get(x).idx.size();
						}
					}
				}
			} 
			
			//System.out.println(area);
		}
	}

	public static void randomRudreshwarTest() {
		int[] b = new int[4];
		for (int i = 0; i < b.length; i++) {
			b[i] = i+1;
		}
		Random rand = new Random();
		do {
			HashSet<Integer> set = new HashSet<>();
			ArrayList<Integer> intervals = new ArrayList<>();
			ArrayList<int[]> arrays = new ArrayList<>();
			int interval = 0;
			for (int k = 0; k < 10000000; k++) {
				interval++;
				int[] a = new int[b.length];
				boolean valid = true;
				while (valid) {
					for (int i = 0; i < a.length; i++) {
						if (a[i] > b[i]) {
							valid = false;
						}
					}
					if (!valid) {
						int key = 0;
						int pow = 1;
						for (int i = 0; i < a.length; i++) {
							key += pow * a[i];
							pow *= b.length;
						}
						if (!set.contains(key)) {
							intervals.add(interval);
							interval = 0;
							set.add(key);
							arrays.add(a);
						}
					}
					int i = rand.nextInt(b.length);
					int j = rand.nextInt(b.length - i) + i;
					for (int m = i; m <= j; m++) {
						a[m]++;
					}
				}
			}
			Collections.reverse(intervals);
			System.out.println(intervals);
//			for (int i = 0; i < arrays.size(); i++) {
//				System.out.println(Arrays.toString(arrays.get(i)));
//			}
			
			System.out.println("size: " + set.size());
		} while (next_permutation(b));
	}
	
	public static boolean next_permutation(int str[]) {
		int temp;
		int a,b,l;
		for(int x = str.length-2, y = 0; x >= 0; x--) {
			if (str[x+1] > str[x]) {
				for(y = str.length-1; str[y] <= str[x]; y--);
				if (x != y) {
					temp = str[x];
					str[x] = str[y];
					str[y] = temp;
				}
				l = (str.length-x)/2;
				for(y = 1; y <= l; y++) {
					a = x+y;
					b = str.length-y;
					if (a != b) {
						temp = str[a];
						str[a] = str[b];
						str[b] = temp;
					}
				}
				return true;
			}
		}
		return false;
	}

	public static void tojoHatesProbabilities(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int t = scan.nextInt();
		
		for (int i = 0; i < t; i++) {
			int n = scan.nextInt();
			int m = scan.nextInt();
			double p = n + m - 1;
			System.out.println(p);
		}
	}
	
	public static void surajGoesShopping(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			int n = scan.nextInt();
			ArrayList<Integer> items = new ArrayList<>();
			for (int j = 0; j < n; j++) {
				items.add(scan.nextInt());
			}
			Collections.sort(items);
			int cost = 0;
			while (!items.isEmpty()) {
				if (items.size() < 4) {
					int j = 0;
					while (!items.isEmpty()) {
						if (j < 2) {
							cost += items.remove(items.size()-1);
						} else {
							items.remove(items.size()-1);
						}
						j++;
					}
				} else {
					cost += items.remove(items.size()-1);
					cost += items.remove(items.size()-1);
					items.remove(items.size()-1);
					items.remove(items.size()-1);
				}
			}
			System.out.println(cost);
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
					st = new StringTokenizer(br.readLine());
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
}
