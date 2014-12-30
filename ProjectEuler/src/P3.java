import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Scanner;

public class P3 {

	public static void main(String[] args) {
		largestPrimeFactor(System.in);
	}

	public static void largestPrimeFactor(InputStream in) {
		Scanner scan = new Scanner(in);
		StringBuilder sb = new StringBuilder();
		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			long n = scan.nextLong();
			sb.append(largestPrimeFactor(n));
			sb.append("\n");
		}
		scan.close();
		System.out.println(sb.toString());
	}
	
	public static String largestPrimeFactor(long n) {
		ArrayList<Long> pf = getPrimeFactors(n);
		return pf.get(pf.size()-1) + "";
	}
	
	public static ArrayList<Long> getPrimeFactors(long n) {
		ArrayList<Long> factors = new ArrayList<>();
		long d = 2;
		while (n > 1) {
			while (n % d == 0) {
				factors.add(d);
				n /= d;
			}
			d++;
			if (d*d > n) {
				if (n > 1) {
					factors.add(n);
					break;
				}
			}
		}
		return factors;
	}

}
