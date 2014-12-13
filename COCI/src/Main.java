
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;


public class Main {
	public static void main(String[] args) {
		//System.out.println(strojopis(System.in));
		//System.out.println(dom(System.in));
		//System.out.println(silueta(System.in));
		System.out.println(coci(System.in));
	}
	
	public static String coci(InputStream in) {
		Scanner input = new Scanner(in);
		int n = input.nextInt();
		int[][] scores = new int[n][2];
		for (int i = 0; i < n; i++) {
			scores[i][0] = input.nextInt();
			scores[i][1] = input.nextInt();
		}
		input.close();
		return cociF(n, scores) + "";
	}
	
	public static class Person implements Comparable<Person> {
		int s1, s2, idx;
		public Person(int s1, int s2, int idx) {
			this.s1 =s1;
			this.s2 =s2;
			this.idx =idx;
		}
		@Override
		public int compareTo(Person o) {
			if (this.s1 < o.s1 && this.s2 < o.s2) {
				return -1;
			} else if (this.s1 > o.s1 && this.s2 > o.s2) {
				return 1;
			}
			return 0;
		}
		@Override
		public String toString() {
			return "Person [s1=" + s1 + ", s2=" + s2 + ", idx=" + idx + "]";
		}
	} 

	public static String cociF(int n, int[][] scores) {
		int[] better = new int[n];
		int[] worse = new int[n];
		ArrayList<Person> people = new ArrayList<Main.Person>();
		for (int i = 0; i < scores.length; i++) {
			people.add(new Person(scores[i][0], scores[i][1], i));
		}
		Collections.sort(people);
		for (int i = 0; i < people.size(); i++) {
			System.out.println(people.get(i));
		}
		
		
		return null;
	}

	public static String silueta(InputStream in) {
		Scanner input = new Scanner(in);
		int n = input.nextInt();
		int[][] dims = new int[n][3];
		for (int i = 0; i < n; i++) {
			dims[i][0] = input.nextInt();
			dims[i][1] = input.nextInt();
			dims[i][2] = input.nextInt();
		}
		input.close();
		return siluetaF(n, dims) + "";
	}

	public static String siluetaF(int n, int[][] dims) { //x1, x2, y
		int maxY = 0;
		int maxX = 0;
		int minX = Integer.MAX_VALUE;
		for (int i = 0; i < dims.length; i++) {
			for (int x = dims[i][0]; x <= dims[i][1]; x++) {
				maxY = Math.max(maxY, dims[i][2]);
				maxX = Math.max(maxX, dims[i][1]);
				minX = Math.min(minX, dims[i][0]);
			}
		}
		int[] map = new int[maxX+2];
		for (int i = 0; i < dims.length; i++) {
			for (int x = dims[i][0]; x < dims[i][1]; x++) {
				map[x] = Math.max(map[x], dims[i][2]);
			}
		}
		int per = 0;
		int currentHeight = 0;
		int oldHeight = 0;
		char[][] paint = new char[maxY][maxX];
		for (int i = 0; i < paint.length; i++) {
			Arrays.fill(paint[i], '.');
		}
		//System.out.println(Arrays.toString(map));
		for (int i = 1; i < map.length; i++) {
			if (map[i] != map[i-1]) {
				per += Math.abs(map[i] - map[i-1]);
				oldHeight = currentHeight;				
				currentHeight = map[i];
				if (currentHeight > oldHeight) {
					for (int j = oldHeight; j < currentHeight; j++) {
						if (j-1 >= 0) {
							paint[j-1][i] = '#';
						}
					}					
				} else {
					for (int j = currentHeight; j < oldHeight; j++) {
						if (j-1 >= 0) {
							paint[j-1][i-1] = '#';
						}
					}					
				}
			}
			if (map[i] > 0) {
				per++;
				if (currentHeight-1 >= 0) {
					paint[currentHeight-1][i] = '#';
				}
			}
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append(per);
		sb.append("\n");
		for (int i = paint.length-1; i >= 0; i--) {
			for (int j = minX; j < paint[i].length; j++) {
				sb.append(paint[i][j]);				
			}
			sb.append("\n");
		}
		for (int i = minX; i < maxX; i++) {
			sb.append("*");
		}
		sb.append("\n");
		
		return sb.toString();
	}

	public static String dom(InputStream in) {
		Scanner input = new Scanner(in);
		int pen = input.nextInt();
		int chan = input.nextInt();
		int p = input.nextInt();
		int[][] prefs = new int[pen+1][2]; //fav, hated
		for (int i = 0; i < pen; i++) {
			prefs[i][0] = input.nextInt();
			prefs[i][1] = input.nextInt();
		}
		input.close();
		return domF(pen, chan, p, prefs) + "";
	}
	
	public static int domF(int pen, int chan, int p, int[][] prefs) {		
		int[] res = new int[chan+1];
		boolean[] visited = new boolean[chan+1];
		for (int i = prefs.length-1; i >= 0; i--) {
			res[prefs[i][1]] = prefs[i][0];
		}
		int currentChan = p;
		int changes = 0;
		while (true) {
			if (visited[currentChan]) {
				return -1;
			}
			if (res[currentChan] == 0) {
				return changes;
			}
			visited[currentChan] = true;
			currentChan = res[currentChan];
			changes++;
		}
	}

	public static String strojopis(InputStream in) {
		Scanner input = new Scanner(in);
		String s = input.next();
		input.close();
		return strojopisF(s);
	}

	public static String strojopisF(String s) {
		String[] def = new String[]{
				"1QAZ",
				"2WSX",
				"3EDC",
				"4RFV5TGB",
				"6YHN7UJM",
				"8IK,",
				"9OL.",
				"0P;/-['=]",
		};
		
		int[] count = new int[def.length];
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			for (int j = 0; j < def.length; j++) {
				for (int k = 0; k < def[j].length(); k++) {
					if (def[j].charAt(k) == c) {
						count[j]++;
					}
				}
			}
		}
		String out = "";
		for (int i = 0; i < count.length; i++) {
			out += count[i] + "\n";
		}
		return out;
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
