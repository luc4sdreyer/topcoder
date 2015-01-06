import java.io.InputStream;
import java.util.Scanner;

public class P1 {

	public static void main(String[] args) {
		multiplesOf3and5(System.in);
	}

	public static void multiplesOf3and5(InputStream in) {
		Scanner scan = new Scanner(in);
		StringBuilder sb = new StringBuilder();
		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			int n = scan.nextInt();
			sb.append(multiplesOf3and5(n));
			sb.append("\n");
		}
		scan.close();
		System.out.println(sb.toString());
	}

	public static String multiplesOf3and5(int n) {
		long sum = 0;
		long a = (n-1)/3;
		long b = (n-1)/5;
		long c = (n-1)/(15);
		sum += 3 * a*(a+1)/2;
		sum += 5 * b*(b+1)/2;
		sum -= 15 * c*(c+1)/2;
		return sum + "";
	}

}
