import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Cook70 {
	public static void main(String[] args) {
//		P1Z2S(System.in);
		EZNO(System.in);
//		System.out.println(EZNO_slow(2, 2));
//		for (int b = 15; b <= 16; b++) {
//			String out = "{";
//			for (int i = 1; i <= b*3 + 4 - 10; i++) {
//			//for (int i = 1; i <= 5; i++) {
//				out += EZNO_fast(b, i) + ", ";
//			}
//			System.out.println(out + "},");
//		}
//		testENZO();
	}

	public static void EZNO(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int tests = scan.nextInt();
		for (int i = 0; i < tests; i++) {
			int b = scan.nextInt();
			int d = scan.nextInt();
			System.out.println(EZNO(b, d));
		}
	}
	
	public static void testENZO() {
		for (int b = 2; b <= 16; b++) {
			System.out.println(b);
			int zcount = 0;
			for (int i = 1; i <= 100; i++) {
				long r1 = EZNO_slow(b, i);
				long r2 = EZNO_fast(b, i);
				if (r1 == 0) {
					zcount++;
				}
				if (zcount >= 3) {
					break;
				}
//				if (r1 != r2) {
//					r1 = EZNO_slow(b, i);
//					r2 = EZNO_fast(b, i);
//					System.out.println("fail");
//				}
				System.out.println(b + " " + i + " " + r1 + "\t" + r2);
			}
		}
	}


	public static int EZNO_slow(int b, int d) {
		int valid = 0;

//		if (d > b+3) {
//			return 0;
//		}
		
		long s = 1;
		for (int i = 1; i < d; i++) {
			s *= 10L;
		}
		String rep = Long.toString(s);
		s = Long.parseLong(rep, b);
		
		while (rep.length() == d) {
			boolean v = true;
			for (int j = 1; j <= rep.length(); j++) {
				if (Long.parseLong(rep.substring(0, j), b) % (long)j != 0) {
					v = false;
				}
			}
			if (v) {
				valid++;
			}	
			
			s++;
			rep = Long.toString(s, b);
		}
		return valid;
	}
	
	public static int EZNO_better(int b, int d) {
		int[] a = new int[d];
		a[0] = 1;
		int valid = 0;
		if (d > b+3) {
			return 0;
		}
		
		do {
			boolean stop = false;
			for (int i = 0; i < a.length; i++) {
				 if (a[i] >= b) {
					 stop = true;
				 }
			}
			if (stop) {
				break;
			}
			
			boolean v = true;
			long num = 0;
			for (int i = 0; i < a.length; i++) {
				num += (long)a[i];
				if (num % (i+1) != 0) {
					v = false;
					break;
				}
				num *= (long)b;
			}
			if (v) {
				valid++;
			}
		} while (next_number(a, b));
		return valid;
	}

	public static int EZNO_fast(int b, int d) {
		int[] a = new int[d];
		a[0] = 1;
		int valid = 0;
		if (d >= b*3 + 4) {
			return 0;
		}
		
		do {
			boolean stop = false;
			for (int i = 0; i < a.length; i++) {
				 if (a[i] >= b) {
					 stop = true;
				 }
			}
			if (stop) {
				break;
			}
			
			boolean v = true;
			long num = 0;
			for (int i = 0; i < a.length; i++) {
				num += (long)a[i];
				if (num % (i+1) != 0) {
					v = false;
					
					if (i < a.length-1) {
						for (int j = i+1; j < a.length; j++) {
							a[j] = b-1;
						}
					}
					break;
				}
				num *= (long)b;
			}
			if (v) {
				valid++;
			}
		} while (next_number(a, b));
		return valid;
	}
	
	public static boolean next_number(int list[], int base) {
		int i = list.length - 1;
		
		list[i]++;
		
		if (list[i] == base) {
			boolean carry = true;
			while (carry) {
				if (i == 0) {
					return false;
				}
				
				carry = false;
				list[i] = 0;
				list[--i]++;				
				
				if (list[i] == base) {
					carry = true;
				}
			}
		}
		
		return true;
	}

	public static long EZNO(int b, int d) {
		if (d > b) {
			return 0;
		}
		
		long[][] res = { 
				{0, 0, 0, 0, 0, 0, 0, },
				{1, 1, 0, 0, 0, 0, 0, 0, 0, 0, },
				{2, 3, 3, 3, 2, 2, 0, 0, 0, 0, 0, 0, 0, },
				{3, 6, 8, 8, 7, 4, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, },
				{4, 10, 17, 21, 21, 21, 13, 10, 6, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, },
				{5, 15, 30, 45, 54, 54, 49, 46, 21, 3, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, },
				{6, 21, 49, 87, 121, 145, 145, 145, 121, 92, 56, 33, 20, 14, 7, 3, 1, 1, 0, 0, 0, 0, 0, 0, 0, },
				{7, 28, 74, 148, 238, 324, 367, 367, 320, 258, 188, 122, 69, 37, 12, 6, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, },
				{8, 36, 108, 252, 454, 706, 898, 1039, 1039, 1039, 869, 674, 473, 309, 216, 126, 66, 34, 18, 9, 2, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, },
				{9, 45, 150, 375, 750, 1200, 1713, 2227, 2492, 2492, 2225, 2041, 1575, 1132, 770, 571, 335, 180, 90, 42, 22, 14, 7, 3, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, },
				{10, 55, 202, 560, 1232, 2278, 3574, 4959, 6074, 6709, 6709, 6709, 5703, 4493, 3294, 2217, 1468, 872, 517, 291, 154, 67, 34, 12, 6, 4, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, },
				{11, 66, 264, 792, 1900, 3800, 6514, 9772, 13030, 15471, 16799, 16799, 15652, 13418, 10748, 8079, 5688, 3811, 2418, 1481, 845, 451, 242, 109, 50, 23, 5, 5, 3, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, },
				{12, 78, 338, 1105, 2874, 6278, 11660, 19068, 27607, 36136, 42702, 46610, 46610, 46610, 41294, 33796, 25982, 18906, 13030, 8424, 5240, 3212, 1817, 1025, 512, 245, 132, 66, 29, 11, 4, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, },
				{13, 91, 424, 1484, 4154, 9707, 19414, 29121, 42063, 59159, 75615, 88747, 95597, 95597, 89036, 80204, 65789, 51190, 37846, 26526, 17775, 11341, 6899, 4124, 2307, 1252, 648, 332, 150, 70, 28, 10, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, },
				{14, 105, 525, 1995, 5985, 15960, 34199, 64409, 107154, 171563, 233954, 296371, 342124, 368134, 368134, 368134, 324605, 271932, 214774, 162650, 116195, 79621, 51863, 32687, 19716, 11331, 6322, 3354, 1728, 856, 406, 181, 89, 40, 23, 5, 1, 0, 0, },
				{15, 120, 640, 2560, 8192, 21888, 50030, 100060, 178236, 285074, 414662, 554018, 681778, 779164, 831886, 831886, 782677, 696031, 585782, 469023, 357089, 259781, 180335, 120383, 77045, 47354, 28046, 15973, 8899, 4687, 2395, 1155, 565, 277, 120, 52, 21, 8, 3, 2, 0, 0, },
		};
		return res[b-1][d-1];
	}

	public static void P1Z2S(InputStream in) {
		MyScanner scan = new MyScanner(in);
		int n = scan.nextInt();
		int[] a = scan.nextIntArray(n);
		
		long sum = 0;
		for (int i = 0; i < a.length; i++) {
			sum += a[i];
		}
		
		long min = Math.max((long) Math.ceil(sum / 2.0), n);
		System.out.println(min);
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
