import java.io.InputStream;
import java.util.HashSet;
import java.util.Scanner;


public class R282 {
	public static void main(String[] args) {
		//System.out.println(digitalCounter(System.in));
		//System.out.println(modularEquations(System.in));
		System.out.println(treasure(System.in));
		//test();
	}
	
	public static void test() {
//		for (int i = 0; i < 10; i++) {
//			modularEquationsF2(1000000000, 1);
//		}
//		System.out.println(modularEquationsF(9435152, 272));
//		System.out.println();
		modularEquationsF2(10, 0);
		for (int i = 0; i <= 1000; i++) {
			for (int j = 0; j <= 1000; j++) {
				//System.out.println(i + ", " + j + ": " + modularEquationsF(i, j));
				String res1 = modularEquationsF(i, j);
				String res2 = modularEquationsF2(i, j);
				if (!res1.equals(res2)) {
					System.out.println("fail: " + i + ", " + j + ": " + res1 +" vs "+res2);
				}
			}
			//System.out.println();
		}	
	}
	
	public static String treasure(InputStream in) {
		Scanner input = new Scanner(in);
		String s = input.next();
		input.close();
		return treasureF(s.toCharArray()) + "";
	}
	
	public static String treasureF(char[] s) {
		StringBuilder sb = new StringBuilder();
		int open = 0;
		int close = 0;
		int empty = 0;
		int lastE = 0;
		for (int i = 0; i < s.length; i++) {
			if (s[i] == '#') {
				empty++;
				lastE = i;
			} else if (s[i] == '(') {
				open++;
			} else {
				close++;
			}
		}
		int have = open - close;
		int best = 0;
		for (int i = 0; i < s.length; i++) {
			if (s[i] == '#') {
				if (have <= 0) {
					return "-1";
				}
				if (i == lastE) {
					sb.append(have);
					sb.append("\n");					
					best -= have;
					have = 0;
				} else {
					sb.append("1\n");
					best--;
					have--;
				}				
			} else if (s[i] == '(') {
				best++;
			} else {		
				best--;
			}
			if (best < 0) {
				return "-1";
			}
		}
		if (best < 0) {
			return "-1";
		}
		return sb.toString();
	}

	public static String modularEquations(InputStream in) {
		Scanner input = new Scanner(in);
		int a = input.nextInt();
		int b = input.nextInt();
		input.close();
		return modularEquationsF2(a, b) + "";
	}
	
	public static String modularEquationsF2(int a, int b) {
		int num = 0;
		if (b == a) {
			return "infinity";
		}
		if (b > a) {
			return 0+"";
		}
		int res = a-b;
		for (int i = 1; i*i <= res; i++) {
			if (res % i == 0) {
				int r2 = res/i;
				if (a % i == b) {
					num++;
				}
				if (r2 != i && a % r2 == b) {
					num++;
				}
			}
		}
		return num+"";
	}
	
	public static String modularEquationsF(int a, int b) {
		int num = 0;
		if (b >= a) {
			return "infinity";
		}
		for (int i = 1; i <= a; i++) {
			if (a % i == b) {
				//System.out.println(i);
				num++;
			}
		}
		return num+"";
	}
	
	public static String digitalCounter(InputStream in) {
		Scanner input = new Scanner(in);
		int n = input.nextInt();
		input.close();
		return digitalCounterF(n) + "";
	}
	
	public static int[][] dmap = {
		{0, 8},
		{1, 0, 3, 4, 7, 8, 9},
		{2, 8},
		{3, 8, 9},
		{4, 8, 9},
		{5, 6, 8, 9},
		{6, 8},
		{7, 0, 3, 8, 9},
		{8},
		{8, 9},
	};
	
	public static int digitalCounterF(int n) {
		int num = 0;
		for (int a = 0; a <= 9; a++) {
			for (int b = 0; b <= 9; b++) {
				int n1 = n / 10;
				int n2 = n % 10;
				boolean valid = false;
				for (int i = 0; i < dmap[n1].length; i++) {
					for (int j = 0; j < dmap[n2].length; j++) {
						if (dmap[n1][i] == a && dmap[n2][j] == b) {
							valid = true;
						}
					}
				}
				if (valid) {
					num++;
				}
			}
		}
		return num;
	}
}
