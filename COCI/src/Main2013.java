
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;


public class Main2013 {
	public static void main(String[] args) {
		System.out.println(trener(System.in));
		System.out.println(kusac(System.in));
	}

	public static String kusac(InputStream in) {
		Scanner input = new Scanner(in);
		int a = input.nextInt();
		int b = input.nextInt();
		input.close();
		return kusacF(a, b) + "";
	}

	public static int kusacF(int n, int m) {
		
		return 0;
	}

	public static String trener(InputStream in) {
		Scanner input = new Scanner(in);
		int n = input.nextInt();
		ArrayList<String> names = new ArrayList<String>();
		for (int i = 0; i < n; i++) {
			names.add(input.next());
		}
		input.close();
		
		char[] alpha = new char[26];
		for (int i = 0; i < names.size(); i++) {
			alpha[names.get(i).charAt(0)-'a']++;
		}
		String valid = "";
		for (int i = 0; i < alpha.length; i++) {
			if (alpha[i] >= 5) {
				valid += (char)(i+'a');
			}
		}
		if (valid.length() == 0) {
			return "PREDAJA";
		} else {
			return valid;
		}
	}
}
