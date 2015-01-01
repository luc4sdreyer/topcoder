import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Scanner;

public class P7 {

	public static void main(String[] args) {
		primes(System.in);
	}

	public static void primes(InputStream in) {
		Scanner scan = new Scanner(in);
		StringBuilder sb = new StringBuilder();

		LinkedHashSet<Integer> pr = getPrimes(105000);
		ArrayList<Integer> p = new ArrayList<>();
		for (int i: pr) {
			p.add(i);
		}
		//System.out.println(p.size());
		
		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			long n = scan.nextLong();
			sb.append(primes(n, p));
			sb.append("\n");
		}
		scan.close();
		System.out.println(sb.toString());
	}

	public static String primes(long n, ArrayList<Integer> p) {
		return p.get((int)(n-1)) + "";
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
