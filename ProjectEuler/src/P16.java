import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Scanner;

public class P16 {

	public static void main(String[] args) {		
		powerDigitSum(System.in);
	}

	public static void powerDigitSum(InputStream in) {
		Scanner scan = new Scanner(in);
		StringBuilder sb = new StringBuilder();
		
		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			int n = scan.nextInt();

			sb.append(powerDigitSum(n));
			sb.append("\n");
		}
		scan.close();
		System.out.println(sb.toString());
	}

	public static String powerDigitSum(int n) {
		char[] a = BigInteger.valueOf(2).pow(n).toString().toCharArray();
		int sum = 0;
		for (int i = 0; i < a.length; i++) {
			sum += a[i] - '0';
		}
		return sum + "";
	}

}
