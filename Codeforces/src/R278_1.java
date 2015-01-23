import java.io.InputStream;
import java.util.HashSet;
import java.util.Scanner;


public class R278_1 {
	public static void main(String[] args) {
		//System.out.println(fightTheMonster(System.in));
		System.out.println(strip(System.in));
	}
	public static String strip(InputStream in) {
		Scanner input = new Scanner(in);
		int n = input.nextInt();
		int s = input.nextInt();
		int l = input.nextInt();
		int[] a = new int[n];
		for (int i = 0; i < a.length; i++) {
			a[i] = input.nextInt();
		}
		input.close();
		return strip(n, s, l, a) + "";
	}
		
	public static int strip(int n, long s, int l, int[] a) {
		if (l == 1) {
			return a.length;
		}
		int[] best = new int[a.length];
		for (int i = 0; i < a.length; i++) {
			long min = Integer.MAX_VALUE;
			long max = Integer.MIN_VALUE;
			for (int j = i; j < a.length; j++) {
				min = Math.min(min, a[j]);
				max = Math.max(max, a[j]);
				if (max - min <= s && j - i +1 >= l) {
					best[j] = Math.max(best[j], j - i +1);
				} else if (max - min > s) {
					break;
				}
			}
		}
		
		int num = 1;
		for (int i = best.length-1; i >= 0; i--) {
			if (best[i] == 0) {
				return -1;
			}
			i -= best[i];
			num++;
		}
		return num;
	}
	public static String fightTheMonster(InputStream in) {
		Scanner input = new Scanner(in);
		int[] yStats = new int[3];
		int[] mStats = new int[3];
		int[] cost = new int[3];
		for (int i = 0; i < yStats.length; i++) {
			yStats[i] = input.nextInt();
		}
		for (int i = 0; i < yStats.length; i++) {
			mStats[i] = input.nextInt();
		}
		for (int i = 0; i < yStats.length; i++) {
			cost[i] = input.nextInt();
		}
		input.close();
		return fightTheMonster(yStats, mStats, cost) + "";
	}
	
	private static int fightTheMonster(int[] yStats, int[] mStats, int[] cost) {
		int score = -1*(mStats[0]*(mStats[1]-yStats[2]) - yStats[0]*(yStats[1]-mStats[2]));
		int[][] spend = new int[1000000][4];
		for (int i = 0; i < spend.length; i++) {
			spend[i][3] = Integer.MIN_VALUE;
		}
		spend[0][3] = score;
		int i = 0;
		while (spend[i][3] <= 0) {
			for (int k = 0; k < 3; k++) {
				int[] modStats = new int[3];
				for (int j = 0; j < modStats.length; j++) {
					modStats[j] = spend[i][j] + yStats[j];
				}
				modStats[k]++;
				int newScore = -1*(mStats[0]*(mStats[1]-modStats[2]) - modStats[0]*(modStats[1]-mStats[2]));
				if (newScore > spend[i + cost[k]][3]) {
					spend[i + cost[k]][3] = newScore;
					spend[i + cost[k]][0] = spend[i][0];
					spend[i + cost[k]][1] = spend[i][1];
					spend[i + cost[k]][2] = spend[i][2];
					spend[i + cost[k]][k]++;
				}
			}
			i++;
		}
		return i;
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
