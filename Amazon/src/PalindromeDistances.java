public class PalindromeDistances {
	final static boolean debug = false;
	final static long[] comp = new long[32];

	public static void main(String[] args) {
		for (int i = 1; i < 8; i++) {
			System.out.println(String.format("%-20s%-20s", slow(i), fast(i)));
		}
	}

	private static long fast(int power) {
		long sum = 0;
		long c  = 0;
		long a = 1;
		long b = 1;
		long contrib = 0;
		long total = 0;
		for (int L = 1; L < power; L++) {
			if (L == 1) {
				sum = 8 * 55 + 1;
				a = 11;
			} else {
				if (L % 2 == 1) {
					a *= 10;
					b = a * 81 / 110;
					contrib = (a * (a-1)) / 2 * b - contrib;
				} else {
					b = a * 81 / 11;
					c = a * 10 / 11;
					contrib = (c * (c-1)) / 2 * b;
				}
				sum += contrib;
			}
			total += sum;
//			System.out.println(a + "\t" + b + "\t" + c + "\t" + sum + "\t");
//			System.out.println(sum);
		}
		return total;
	}

	private static long slow(int pow) {
		long limit = (long) Math.pow(10, pow);
		long prevPal = 1;
		long sum = 0;
		long[] sum2 = new long[9];
		long[] ten_sum = new long[9];
		int power = 0;
		for (long i = 1; i < limit; i++) {
			if (isPal(i)) {
				sum += ((i - prevPal-1)*(i - prevPal )) / 2;
//				if (((int) Math.log10(i)) > power) {
//					c = 0;
//				}
				power = (int) Math.log10(i); 
//				if (c >= 1 && c <= 10) {
////					System.out.println(power + "\t" + (i - prevPal)  +"\t" + ((i - prevPal-1)*(i - prevPal )) / 2);
//					ten_sum[power] += ((i - prevPal-1)*(i - prevPal )) / 2;
//				}
//				c++;
				
				sum2[power] += ((i - prevPal-1)*(i - prevPal )) / 2;
//				System.out.println(i + "\t" +  ((i - prevPal-1)*(i - prevPal )) / 2 + "\t" + (i - prevPal));
				prevPal = i;
			}
		}
//		System.out.println(Arrays.toString(sum2));
//		System.out.println(Arrays.toString(ten_sum));
		return sum;
//		System.out.println();
	}

	public static boolean isPal(long i) {
		int len = 0;
		while (i > 0) {
			comp[len++] = i % 10;
			i /= 10;
		}
		boolean pal = true;
		for (int j = 0; j < len/2; j++) {
			if (comp[j] != comp[len - j - 1]) {
				pal = false;
			}
		}
		return pal;
	}
}
