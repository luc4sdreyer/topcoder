import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Scanner;

public class P10 {

	public static void main(String[] args) {
		summationOfPrimes(System.in);
	}

	public static void summationOfPrimes(InputStream in) {
		Scanner scan = new Scanner(in);
		StringBuilder sb = new StringBuilder();

		LinkedHashSet<Integer> pr = getPrimes(1000000);
		//long last = 0;
		long[][] p = new long[pr.size()][2];
		int j = 0;
		for (int i: pr) {
			p[j][0] = i;
			p[j][1] = p[j][0];
			if (j > 0) {
				p[j][1] += p[j-1][1];   
			}
			//last = i;
			j++;
		}
		//System.out.println(last);
		//System.out.println(p.size());
		
		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			long n = scan.nextLong();
			sb.append(summationOfPrimes(n, p));
			sb.append("\n");
		}
		scan.close();
		System.out.println(sb.toString());
	}

	public static String summationOfPrimes(long n, long[][] p) {
		long low = 0;
		long high = p.length-1;
		long mid = (low + high)/2;
		long best = 0;
		while (low <= high) {
			mid = (low + high)/2;
			if (p[(int) mid][0] <= n) {
				best = Math.max(best, mid);
				low = mid+1;
			} else {
				high = mid-1;
			}
		}
		return p[(int) best][1] + "";
	}

	public static LinkedHashSet<Integer> getPrimes(long limit) {
		LinkedHashSet<Integer> primes = new LinkedHashSet<Integer>();
		LinkedHashSet<Integer> notPrimes = new LinkedHashSet<Integer>();
		boolean prime = true;
		for (int i = 2; i <= limit; i++) {
			prime = true;
			if (notPrimes.contains(i)) {
				prime = false;
			}
			if (prime) {
				primes.add(i);
				for (int j = 2; j*i <= limit; j++) {
					notPrimes.add(j*i);
				}
			}
		}
		return primes;
	}

}
