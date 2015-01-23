import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;


public class R278 {

	public static void main(String[] args) {
		//System.out.println(giga(System.in));
		System.out.println(candy(System.in));
	}
	
	public static String candy(InputStream in) {
		Scanner input = new Scanner(in);
		int n = input.nextInt();
		int[] candies = new int[n];
		for (int i = 0; i < candies.length; i++) {
			candies[i] = input.nextInt();
		}
		input.close();
		return candyF(n, candies) + "";
	}
	
	public static String candyF(int n, int[] candies) {
		if (n == 0) {
			return "1, 1, 3, 3";
		} else {
			int[] filled = new int[4];
			for (int i = 0; i < candies.length; i++) {
				filled[i] = candies[i];
			}
			int[] idxList = new int[4];
			for (int i = 0; i < idxList.length; i++) {
				idxList[i] = i;
			}
			int[] reconfig = candyReconfig(filled, idxList);
			if (candyCheck(reconfig)) {
				String out = "YES\n";
				for (int i = 0; i < reconfig.length; i++) {
					out += reconfig[i] + "\n";
				}
				return out;
			}
			while (next_permutation(idxList)) {
				reconfig = candyReconfig(filled, idxList);
				if (candyCheck(reconfig)) {
					String out = "YES\n";
					for (int i = 0; i < reconfig.length; i++) {
						out += reconfig[i] + "\n";
					}
					return out;
				}
			}
		}
		return "NO";
	}
	
	public static boolean candyCheck(int[] candies) {
		int missing = 0;
		for (int i = 0; i < candies.length; i++) {
			if (candies[i] == 0) {
				missing++;
			}
		}
		int[] missingIdx = new int[missing];
		int t = 0;
		for (int i = 0; i < candies.length; i++) {
			if (candies[i] == 0) {
				missingIdx[t++] = i;
			}
		}
		
		if (missing == 0) {
			return candyValidate(candies);
		} else {
			if (candies[0] != 0) {
				if (candies[3] == 0) {
					candies[3] = candies[0]*3;
				}
				for (int i = 1; i <= 1000000; i++) {
					if (candies[1] == 0) {
						candies[1] = i;
					}
					if (candies[2] == 0) {
						candies[2] = 4*candies[0] - candies[1];
					}
					if (candyValidate(candies)) {
						return true;
					}
				}
			} else if (candies[1] == 0) {
				for (int i = 1; i <= 1000000; i++) {
					if (candies[2] != 0) {
						candies[2] = i;
					}
					if (candies[0] == 0) {
						candies[0] = candies[1] + candies[2];
					}
					if (candies[3] == 0) {
						candies[3] = candies[0]*3;
					}
					if (candyValidate(candies)) {
						return true;
					}
				}
			} else if (candies[2] != 0) {
				for (int i = 1; i <= 1000000; i++) {
					if (candies[1] == 0) {
						candies[1] = i;
					}
					if (candies[0] == 0) {
						candies[0] = candies[1] + candies[2];
					}	
					if (candies[3] == 0) {
						candies[3] = candies[0]*3;
					}
					if (candyValidate(candies)) {
						return true;
					}
				}
			} else if (candies[3] != 0) {
				for (int i = 1; i <= 1000000; i++) {
					if (candies[0] == 0) {
						candies[0] = candies[3]/3;
					}
					if (candies[3] != candies[0]*3) {
						continue;
					}
					if (candies[1] == 0) {
						candies[1] = i;
					}
					if (candies[2] == 0) {
						candies[2] = 4*candies[0] - candies[1];
					}
					if (candyValidate(candies)) {
						return true;
					}
				}
			} else {
				return false;
			}			
		}
		return false;
	}

	public static boolean candyValidate(int[] x) {
		for (int i = 1; i < x.length; i++) {
			if (x[i-1] > x[i]) {
				return false;
			}
		}
		int y = 0;
		for (int i = 0; i < x.length; i++) {
			if (x[i] < 1 || x[i] > 1000000) {
				return false;
			}
			y += x[i];
		}
		
		if (2*(x[1] + x[2]) != y) {
			return false;
		}
		if (4*(x[3] - x[0]) != y) {
			return false;
		}
		return true;
	}

	public static int[] candyReconfig(int[] filled, int[] idxList) {
		int[] reconfig = new int[idxList.length];
		for (int i = 0; i < reconfig.length; i++) {
			reconfig[i] = filled[idxList[i]];
		}
		return reconfig;
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

	public static String giga(InputStream in) {
		Scanner input = new Scanner(in);
		long n = input.nextLong();
		input.close();
		return gigaF(n) + "";
	}

	public static long gigaF(long n) {
		long a = n+1;
		
		while (!Long.toString(a).contains("8")) {
			a++;
		}
		return a-n;
	}

}
