import java.io.InputStream;
import java.util.Scanner;

public class P2 {

	public static void main(String[] args) {
		evenFibonacciNumbers(System.in);
	}

	public static void evenFibonacciNumbers(InputStream in) {
		Scanner scan = new Scanner(in);
		StringBuilder sb = new StringBuilder();
		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			long n = scan.nextLong();
			sb.append(evenFibonacciNumbers(n));
			sb.append("\n");
		}
		scan.close();
		System.out.println(sb.toString());
	}

	public static String evenFibonacciNumbers(long n) {
		long sum = 0;
		long a = 0;
		long b = 1;
		while (a + b <= n) {
			long c = a + b;
			a = b;
			b = c;
			if (c % 2 == 0) {
				sum += c;
				//System.out.println(c);
			}
 		}
		return sum + "";
	}

}
