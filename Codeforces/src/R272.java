import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;


public class R272 {
	public static final long mod = 1000000007;
	public static void main(String[] args) {
		//System.out.println(stairs(System.in));
		//System.out.println(wifi(System.in));
		System.out.println(sums(System.in));
	}

	public static String sums(InputStream in) {
		Scanner input = new Scanner(in);
		int a = input.nextInt();
		int b = input.nextInt();
		input.close();
		for (int i = 1; i <= 10; i++) {
			for (int j = 1; j <= 10; j++) {
				int x = sumsSlow(i, j); 
				int y = sums(i, j);
				if (x != y) {
					System.out.println(i + "\t" + j + "\t" + x + "\t" + y);
				}
			}
		}
		return sums(a , b) + "";
	}
	
	public static int sumsSlow(long a, int b) {
		int[] contrib = new int[b];
		int res = 0;
		for (int x = 1; x <= a*(b+1)*b; x++) {
			if (x % b != 0) {
				int k = (x / b) / (x % b);
				if (k >= 1 && k <= a) {
					//System.out.println(x + "\t" + k);
				}
			}
			if (x % b != 0) {
				int k = (x / b) / (x % b);
				if (k >= 1 && k <= a) {
					contrib[x%b] += x;
					res += x;
				}
			}
		}
		//System.out.println(Arrays.toString(contrib));
		return res;
	}
	public static int sums(long a, int b) {
		
		int[] pContrib = new int[b];
		long sum = 0;
		long es = (a * (a+1L))/2L;
		long es2 = (b * (b-1L))/2L;
		
//		for (long i = 1; i < b; i++) {
//			//int n1 = a/i;
//			//int n2 = a%i;
//			//int i1 = n2 == 0 ? i : i-1;
//			//int i2 = n2 == 0 ? 0 : 1;
//			//pContrib[i] = i1 * (n1)*(n1+1)/2 + i2 * (n1)*(n1+1)/2 + ;
//			//pContrib[i] = (int) (i*(a/(double)i)*((a/(double)i+1))/2);
//			long res = (i * es); 
//			sum = (sum + res) % mod;
//			pContrib[(int) i] = (int) res;
//		}
//		//System.out.println(Arrays.toString(contrib));
//		System.out.println(Arrays.toString(pContrib));
//		//System.out.println(es*es2);
//		sumsSlow(a,b);
		//return (int) sum;
		return BigInteger.valueOf(es).multiply(BigInteger.valueOf(b)).add(BigInteger.valueOf(a)).multiply(BigInteger.valueOf(es2)).mod(BigInteger.valueOf(mod)).intValue();
	}	

	public static String wifi(InputStream in) {
		Scanner input = new Scanner(in);
		String a = input.next();
		String b = input.next();
		input.close();
		return wifi(a.toCharArray(), b.toCharArray()) + "";
	}	

	public static double wifi(char[] sent, char[] recieved) {
		int sentPos = 0;
		for (int i = 0; i < sent.length; i++) {
			if (sent[i] == '+') {
				sentPos++;
			} else if (sent[i] == '-') {
				sentPos--;
			}
		}
		int receivedPos = 0;
		int numGuess = 0;
		for (int i = 0; i < recieved.length; i++) {
			if (recieved[i] == '+') {
				receivedPos++;
			} else if (recieved[i] == '-') {
				receivedPos--;
			} else {
				numGuess++;
			}
		}
		int offset = Math.abs(receivedPos - sentPos);
		
		// Get right side of Pascal's triangle (binomial coefficients)
		int lim = numGuess;
		int[] ptr = new int[lim+2];
		ptr[0] = 1;
		for (int i = 1; i <= lim; i++) {
			int[] newPtr = new int[ptr.length];
			for (int j = i%2; j <= i; j += 2) {
				if (j == 0 ){
					newPtr[j] = ptr[j+1]*2;
				} else {
					newPtr[j] = ptr[j+1] + ptr[j-1];
				}
			}
			ptr = newPtr;
		}
		if (offset < ptr.length) {
			return ptr[offset] / (double)(1 << numGuess);
		}
		return 0;
	}

	public static String stairs(InputStream in) {
		Scanner input = new Scanner(in);
		int a = input.nextInt();
		int b = input.nextInt();
		input.close();
		return stairs(a , b) + "";
	}
	
	public static int stairs(int n, int m) {
		if (n == 0) {
			return 0;
		}
		int low = (int) Math.ceil(n/2.0);
		for (int i = low; i <= n && i <= low+20; i++) {
			if (i % m == 0) {
				return i;
			}
		}
		return -1;
	}

}
