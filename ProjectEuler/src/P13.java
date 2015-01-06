import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Scanner;

public class P13 {

	public static void main(String[] args) {
		largeSum(System.in);
	}

	public static void largeSum(InputStream in) {
		Scanner scan = new Scanner(in);

		int n = scan.nextInt();
		BigInteger sum = BigInteger.ZERO;
		for (int i = 0; i < n; i++) {
			sum = sum.add(new BigInteger(scan.next()));
		}
		scan.close();
		
		String s = sum.toString();
		
		System.out.println(s.substring(0, 10));
	}

}
