import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Captcha {
	public static void main(String[] args) {
		//detectingValidLatitudeAndLongitudePairs();
		theCaptchaCrackerDetect();
	}

	public static char[] glyph = {'D', 'E', 'F', 'G', 'A', 'B', 'C', 'L', 'M', 'N', 'O', 'H', 'I', 'J', 'K', 'U', 'T', 'W',
		'V', 'Q', 'P', 'S', 'R', 'Y', 'X', 'Z', '3', '2', '1', '0', '7', '6', '5', '4', '9', '8'};
	public static long[][] signature = {
		{7039364176855796862L, 16565955},
		{108306156750376190L, 33293827},
		{108306156750376446L, 787971},
		{7041589536750246136L, 32607427},
		{9201091997944836144L, 51218115},
		{7032590609703079038L, 16565955},
		{108297910480476408L, 32607363},
		{108297910413167622L, 33293827},
		{7041056331724987782L, 51218115},
		{8770438585388834182L, 51234531},
		{7039364176855799928L, 15781059},
		{7039372423193103750L, 51218115},
		{866383283305341180L, 33042456},
		{3465533133221363952L, 7367778},
		{973835119533591942L, 51168819},
		{7039364176855895430L, 15781059},
		{866383283305341438L, 6303768},
		{7905747453706177926L, 51236607},
		{2168919479300066694L, 6303804},
		{7904055305310935160L, 49335539},
		{108315004483865854L, 787971},
		{6931057144638409980L, 33130176},
		{1839667586231110910L, 51218019},
		{866383293010087302L, 6303768},
		{2163419985692790150L, 51218022},
		{217020518514131198L, 33293827},
		{6924292149534100604L, 16303808},
		{868082073957144696L, 66849804},
		{866383283308490800L, 33042456},
		{7039364176806998064L, 6322278},
		{434041037028262398L, 787974},
		{7032871509087066232L, 15781059},
		{6931053998574669054L, 15781059},
		{9194323793336713408L, 25215072},
		{6933041814766721144L, 15781058},
		{7032801304424061048L, 15781059},
	};

	public static void theCaptchaCrackerDetect() {
		HashMap<BitSet, Character> map = new HashMap<>();
		for (int i = 0; i < glyph.length; i++) {
			map.put(BitSet.valueOf(signature[i]), glyph[i]);
		}
		Scanner scan = new Scanner(System.in);
		int r = scan.nextInt();
		int c = scan.nextInt();
		int[][][] raw = new int[r][c][3];
		for (int j = 0; j < r; j++) {
			for (int k = 0; k < c; k++) {
				StringTokenizer st = new StringTokenizer(scan.next(), ",");
				for (int m = 0; m < 3; m++) {
					raw[j][k][m] = Integer.parseInt(st.nextToken());
				}
			}
		}

		int[] freq = new int[256];
		boolean[][] grey = new boolean[r][c];
		for (int j = 0; j < r; j++) {
			for (int k = 0; k < c; k++) {
				int sum = 0;
				for (int m = 0; m < 3; m++) {
					sum += raw[j][k][m];
				}
				if (sum/3 < 100) {
					grey[j][k] = true;			
				}
				freq[sum/3]++;
			}
		}

		char[] out = new char[5];
		int sX = 4;
		int sY = 11;
		for (int m = 0; m < 5; m++) {
			BitSet b = new BitSet();
			int t = 0;
			for (int y = sY; y < sY + 10; y++) {
				for (int x = sX; x < sX + 9; x++) {
					if (grey[y][x]) {
						b.set(t++);
					} else {
						b.clear(t++);
					}
				}
			}
			if (map.containsKey(b)) {
				out[m] = map.get(b);
			} else {
				System.out.println("failed");
			}
			sX += 9;
		}
		System.out.println(new String(out));
	}

	private static void theCaptchaCracker() {
		HashMap<Character, BitSet> map = new HashMap<>();
		for (int i = 0; i < 25; i++) {
			Scanner scan = null;
			try {
				String num = i < 10 ? ("0" + i) : (i + "");
				scan = new Scanner(new FileReader("C:/Users/Lucas/Downloads/sampleCaptchas/input/input" + num + ".txt"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			int r = scan.nextInt();
			int c = scan.nextInt();
			int[][][] raw = new int[r][c][3];
			for (int j = 0; j < r; j++) {
				for (int k = 0; k < c; k++) {
					StringTokenizer st = new StringTokenizer(scan.next(), ",");
					for (int m = 0; m < 3; m++) {
						raw[j][k][m] = Integer.parseInt(st.nextToken());
					}
				}
			}

			int[] freq = new int[256];
			boolean[][] grey = new boolean[r][c];
			for (int j = 0; j < r; j++) {
				for (int k = 0; k < c; k++) {
					int sum = 0;
					for (int m = 0; m < 3; m++) {
						sum += raw[j][k][m];
					}
					if (sum/3 < 100) {
						grey[j][k] = true;			
					}
					freq[sum/3]++;
				}
			}

			try {
				String num = i < 10 ? ("0" + i) : (i + "");
				scan = new Scanner(new FileReader("C:/Users/Lucas/Downloads/sampleCaptchas/output/output" + num + ".txt"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			char[] out = scan.next().toCharArray();

			int sX = 4;
			int sY = 11;
			for (int m = 0; m < 5; m++) {
				BitSet b = new BitSet();
				int t = 0;
				for (int y = sY; y < sY + 10; y++) {
					for (int x = sX; x < sX + 9; x++) {
						if (grey[y][x]) {
							b.set(t++);
						} else {
							b.clear(t++);
						}
					}
				}
				if (map.containsKey(out[m])) {
					if (!map.get(out[m]).equals(b)) {
						System.out.println("failed");
					}
				} else {
					map.put(out[m], b);
				}
				sX += 9;
			}
		}
		for (char c: map.keySet()) {
			System.out.println(c + ", ");
		}
		for (char c: map.keySet()) {
			System.out.println(Arrays.toString(map.get(c).toLongArray()));
		}
	}

	public static void detectingValidLatitudeAndLongitudePairs() {
		MyScanner scan = new MyScanner(System.in);
		int t = scan.nextInt();
		for (int i = 0; i < t; i++) {
			detectingValidLatitudeAndLongitudePairs(scan.nextLine());
		}
	}

	public static void detectingValidLatitudeAndLongitudePairs(String s) {
		if (s.matches("^([(](([+]|[-])?)((90([.][0]+)?)|([1-8][0-9]|[0-9])([.]\\d+)?), (([+]|[-])?)((180([.][0]+)?)|((1[0-7][0-9]|[1-9][0-9]|[0-9])([.]\\d+)?))[)])$")) {
			System.out.println("Valid");
		} else {
			System.out.println("Invalid");
		}
	}

	public static class MyScanner {
		BufferedReader br;
		StringTokenizer st;

		public MyScanner(InputStream in) {
			this.br = new BufferedReader(new InputStreamReader(in));
		}

		public void close() {
			try {
				this.br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		String next() {
			while (st == null || !st.hasMoreElements()) {
				try {
					st = new StringTokenizer(br.readLine());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return st.nextToken();
		}

		int nextInt() {
			return Integer.parseInt(next());
		}

		long nextLong() {
			return Long.parseLong(next());
		}

		double nextDouble() {
			return Double.parseDouble(next());
		}

		String nextLine(){
			String str = "";
			try {
				str = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return str;
		}
	}
}
