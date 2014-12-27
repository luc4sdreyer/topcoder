import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;


public class R279 {
	public static void main (String[] args) {
		//teamOlympiad(System.in);
		//queue(System.in);
		hackingCypher(System.in);
		//testHackingCypher();
	}

	private static void testHackingCypher() {
		StringBuilder big = new StringBuilder();
		Random rand = new Random();
		for (int i = 0; i < 1000; i++) {
			big.append(rand.nextInt(10));
		}
		System.out.println("start");
		hackingCypher(big.toString(), rand.nextInt(100000000), rand.nextInt(100000000));
	}

	public static void hackingCypher(InputStream in) {
		Scanner scan = new Scanner(in);
		String n = scan.next();
		int a = scan.nextInt();
		int b = scan.nextInt();
		scan.close();
		
		hackingCypher(n, a, b);
	}

	public static void hackingCypher2(String n, int a, int b) {
		BigInteger A = BigInteger.valueOf(a);
		BigInteger B = BigInteger.valueOf(b);
		for (int i = 1; i < n.length(); i++) {
			if (n.charAt(i) != '0') {
				BigInteger left = new BigInteger(n.substring(0, i));
				if (left.mod(A).longValue() == 0) {
					BigInteger right = new BigInteger(n.substring(i));
					if (right.mod(B).longValue() == 0) {
						System.out.println("YES");
						System.out.println(left);
						System.out.println(right);
						return;
					}
				}
			}
		}
		System.out.println("NO");
	}


	public static void hackingCypher(String n, long a, long b) {
		if (n.length() < 2) {
			System.out.println("NO");
			return;
		}
		long rem1 = a;
		long rem2 = b;
		boolean[] res1 = new boolean[n.length()+1];
		boolean[] res2 = new boolean[n.length()+1];
		for (int i = 1; i < n.length(); i++) {
			rem1 = (rem1*10 + (n.charAt(i-1) - '0')) % a;
			if (rem1 == 0) {
				res1[i] = true;
			}
		}
		long p = 1;
		for (int i = n.length(); i >= 1; i--) {			
			rem2 = ((n.charAt(i-1) - '0') * p + rem2) % b;
			p = (p * 10) % b;
			if (rem2 == 0) {
				 res2[i-1] = true;
			}
		}

		for (int i = 1; i < n.length(); i++) {
			if (n.charAt(i) != '0' && res1[i] && res2[i]) {
				System.out.println("YES");
				System.out.println(n.substring(0, i));
				System.out.println(n.substring(i));
				return;
			}
		}

		System.out.println("NO");
	}

	public static void queue(InputStream in) {
		Scanner scan = new Scanner(in);
		int n = scan.nextInt();
		int[][] a = new int[n][2];
		for (int i = 0; i < n; i++) {
			a[i][0] = scan.nextInt();
			a[i][1] = scan.nextInt();
		}
		scan.close();
		
		queue(n, a);
	}

	private static void queue(int n, int[][] a) {
		int[][] map = new int[1000001][2];
		for (int i = 0; i < map.length; i++) {
			Arrays.fill(map[i], -1);
		}
		
		for (int i = 0; i < a.length; i++) {
			map[a[i][0]][1] = a[i][1];
			map[a[i][1]][0] = a[i][0];
		}
		
		int[] q = new int[n];
		int start1 = 0;
		int start2 = 0;
		for (int i = 0; i < map.length; i++) {
			if (map[i][0] == -1 && map[i][1] != -1) {
				start1 = i;
			}
			if (map[i][0] == 0) {
				start2 = i;
			}
		}
		
		int pos = start1;
		int i = 0;
		while (pos > 0) {
			q[i] = pos;
			i += 2;
			pos = map[pos][1];
		}
		
		pos = start2;
		i = 1;
		while (pos > 0) {
			q[i] = pos;
			i += 2;
			pos = map[pos][1];
		}
		for (int j = 0; j < q.length; j++) {
			System.out.print(q[j] + " ");
		}
		System.out.println();
	}

	public static void teamOlympiad(InputStream in) {
		Scanner scan = new Scanner(in);
		int n = scan.nextInt();
		int[] t = new int[n];
		for (int i = 0; i < t.length; i++) {
			t[i] = scan.nextInt();
		}
		scan.close();
		
		teamOlympiad(t);
	}

	public static void teamOlympiad(int[] t) {
		ArrayList<ArrayList<Integer>> list = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			list.add(new ArrayList<Integer>());
		}
		for (int i = 0; i < t.length; i++) {
			list.get(t[i]-1).add(i+1);
		}
		int max = list.get(0).size();
		for (int i = 0; i < 3; i++) {
			max = Math.min(max, list.get(i).size());
		}
		
		System.out.println(max);
		for (int i = 0; i < max; i++) {
			for (int j = 0; j < 3; j++) {
				System.out.print(list.get(j).get(i) + " ");
			}
			System.out.println();
		}
	}
	
}

