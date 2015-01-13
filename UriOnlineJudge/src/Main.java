import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException {
		//threebonacciSequence(System.in);
		//shuffle(System.in);
		//randomBingoCards(System.in);
		//bitShuffling(System.in);
		lockedInTheCastle(System.in);
	}

	public static void lockedInTheCastle(InputStream in) {
		MyScanner scan = new MyScanner(in);

		String input = null;
		while ((input = scan.next()) != null) {
			int n = scan.nextInt();
			int m = scan.nextInt();
			int[][] paths = new int[m][2];
			for (int i = 0; i < paths.length; i++) {
				paths[i][0] = scan.nextInt();
				paths[i][1] = scan.nextInt();
			}
			int[] keys = new int[n-1];
			for (int i = 0; i < keys.length; i++) {
				keys[i] = scan.nextInt();
			}
			System.out.println(lockedInTheCastle(n, m, paths, keys));
		}
		scan.close();
	}
	
	public static String lockedInTheCastle(int n, int m, int[][] paths, int[] keys) {
		HashMap<Integer, ArrayList<Integer>> map = new HashMap<>();
		HashSet<Integer> keyStore = new HashSet<>();
		ArrayList<Integer> newKeys = new ArrayList<>();
		keyStore.add(1);
		newKeys.add(1);
		for (int i = 0; i < paths.length; i++) {
			for (int j = 0; j < 2; j++) {
				if (!map.containsKey(paths[i][j])) {
					map.put(paths[i][j], new ArrayList<Integer>());
				}
				map.get(paths[i][j]).add(paths[i][(j+1) % 2]);
			}
		}
		HashMap<Integer, Integer> keyMap = new HashMap<>();
		for (int i = 0; i < keys.length; i++) {
			keyMap.put(keys[i], i);
		}

		HashSet<Integer> visited = new HashSet<>();
		Queue<Integer> q = new LinkedList<>();
		int top = 1;
		while (!newKeys.isEmpty()) {
			top = newKeys.remove(newKeys.size()-1);

			q.add(top);
			while (!q.isEmpty()) {
				top = q.poll();
				if (visited.contains(top)) {
					continue;
				}
				visited.add(top);
				if (keyMap.containsKey(top)) {
					keyStore.add(keyMap.get(top));
					newKeys.add(keyMap.get(top));
				}
				ArrayList<Integer> child  = map.get(top);
				if (child != null) {
					for (int i = 0; i < child.size(); i++) {
						if (keyStore.contains(child.get(i))) {
							q.add(child.get(i));
						}
					}
				}
			}
		}
		if (visited.size() == n) {
			return "sim";
		} else {
			return "nao";
		}
	}

	/**
	 * 

#include <map>
#include <set>
#include <list>
#include <cmath>
#include <ctime>
#include <deque>
#include <queue>
#include <stack>
#include <bitset>
#include <cstdio>
#include <vector>
#include <cstdlib>
#include <numeric>
#include <sstream>
#include <iostream>
#include <algorithm>
#include <cstring>

using namespace std;


int getBit(long long n, int i) {
	return (((1L << i) & n) != 0) ? 1 : 0; 
}

long long setBit(long long n, int i, int val) {
	if (val == 1) {
		return n | (1L << i);
	} else {
		return n &= ~(1L << i);
	}
}

int bitShuffling(long long n, int k, int ops[][2]) {
	long long minI = n;
	long long maxI = n;
	long long res = n;
	for (int i = 0; i < k; i++) {
		int a = getBit(res, ops[i][0]);
		int b = getBit(res, ops[i][1]);
		res = setBit(res, ops[i][1], a);
		res = setBit(res, ops[i][0], b);
		minI = min(minI, res);
		maxI = max(maxI, res);
	}
	cout << res << " " << maxI << " " << minI << endl;
}
	

int main() {
	while (true) {
		long long n = 0;
		cin >> n;
		int k = 0;
		cin >> k;
		if (k == 0 && n == 0) {
			break;
		}
		int ops[k][2];
		for (int i = 0; i < k; i++) {
			cin >> ops[i][0];
			cin >> ops[i][1];
		}
		bitShuffling(n, k, ops);
	}
	return 0;
}

	 */

	public static void bitShuffling(InputStream in) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(in));

		String input = null;
		while (true) {
			char[] buffer = new char[100];
			int t = 0;
			buffer[t++] = (char) br.read();
			buffer[t++] = (char) br.read();
			buffer[t++] = (char) br.read();
			if (buffer[0] == '0' && buffer[1] == ' ' && buffer[2] == '0') {
				break;
			}
			
			StringTokenizer st = new StringTokenizer(new String(buffer));
			long n = Long.parseLong(st.nextToken());
			int k = Integer.parseInt(st.nextToken());
			if (k == 0 && n == 0) {
				break;
			}
			int[][] ops = new int[k][2];
			for (int i = 0; i < k; i++) {
				st = new StringTokenizer(br.readLine());
				ops[i][0] = Integer.parseInt(st.nextToken());
				ops[i][1] = Integer.parseInt(st.nextToken());
			}
			System.out.println(bitShuffling(n, k, ops));
		}
		br.close();
	}

	public static String bitShuffling(long n, int k, int[][] ops) {
		long min = n;
		long max = n;
		long res = n;
		for (int i = 0; i < ops.length; i++) {
			int a = getBit(res, ops[i][0]);
			int b = getBit(res, ops[i][1]);
			res = setBit(res, ops[i][1], a);
			res = setBit(res, ops[i][0], b);
			min = Math.min(min, res);
			max = Math.max(max, res);
		}
		return res + " " + max + " " + min;
	}
	
	public static int getBit(long n, int i) {
		return (((1L << i) & n) != 0) ? 1 : 0; 
	}
	
	public static long setBit(long n, int i, int val) {
		if (val == 1) {
			return n | (1L << i);
		} else {
			return n &= ~(1L << i);
		}
	}

	public static void randomBingoCards(InputStream in) {
		MyScanner scan = new MyScanner(in);

		String input = null;
		while ((input = scan.next()) != null) {
			int[] cards = new int[24];
			cards[0] = Integer.parseInt(input);
			for (int i = 1; i < cards.length; i++) {
				cards[i] = scan.nextInt();
			}
			System.out.println(randomBingoCards(cards));
		}
		scan.close();
	}

	public static String randomBingoCards(int[] cards) {
		int[] possible = new int[5];
		for (int i = 0; i < cards.length; i++) {
			if (cards[i] >= 1) {
				if (cards[i] <= 15) {
					possible[0]++;
				} else if (cards[i] <= 30) {
					possible[1]++;
				} else if (cards[i] <= 45) {
					possible[2]++;
				} else if (cards[i] <= 60) {
					possible[3]++;
				} else if (cards[i] <= 75) {
					possible[4]++;
				}
			}
		}
		
		ArrayList<Integer> card = new ArrayList<>();
		for (int i = 0; i < cards.length; i++) {
			card.add(cards[i]);
		}
		card.add(12, 45);
		int[] bingo = new int[5];
		for (int i = 0; i < card.size(); i++) {
			if (i % 5 == 0 && card.get(i) >= 1 && card.get(i) <= 15) {
				bingo[0]++;
			} else if (i % 5 == 1 && card.get(i) >= 16 && card.get(i) <= 30) {
				bingo[1]++;
			} else if (i % 5 == 2 && card.get(i) >= 31 && card.get(i) <= 45) {
				bingo[2]++;
			} else if (i % 5 == 3 && card.get(i) >= 46 && card.get(i) <= 60) {
				bingo[3]++;
			} else if (i % 5 == 4 && card.get(i) >= 61 && card.get(i) <= 75) {
				bingo[4]++;
			} else {
					System.out.print("");
			}
		}
		
		boolean ok = true;
		for (int i = 0; i < bingo.length; i++) {
			if (bingo[i] != 5) {
				ok = false;
			}
		}
		if (ok) {
			return "OK";
		} else {
			boolean permute = true;
			for (int j = 0; j < possible.length; j++) {
				if (j != 2) {
					if (possible[j] != 5) {
						permute = false;
					}
				} else {
					if (possible[j] != 4) {
						permute = false;
					}
				}
			}
			if (permute) {
				return "RECICLAVEL";
			} else {
				return "DESCARTAVEL";
			}
		}
	}

	public static void shuffle(InputStream in) {
		MyScanner scan = new MyScanner(in);

		String input = null;
		while ((input = scan.next()) != null) {
			int m = Integer.parseInt(input);
			int k = scan.nextInt();
			int[] length = new int[m];
			int[] playlist = new int[k];
			for (int i = 0; i < length.length; i++) {
				length[i] = scan.nextInt();
			}
			for (int i = 0; i < playlist.length; i++) {
				playlist[i] = scan.nextInt();
			}
			System.out.println(shuffle(m, k, length, playlist));
		}
		scan.close();
	}

	public static int shuffle(int m, int k, int[] length, int[] playlist) {
		HashSet<Integer> songs = new HashSet<>();
		int time = 0;
		for (int i = 0; i < m; i++) {
			songs.add(i+1);
		}
		for (int i = 0; i < playlist.length; i++) {
			songs.remove(playlist[i]);
			time += length[playlist[i]-1];
			if (songs.isEmpty()) {
				return time;
			}
		}
		return -1;
	}

	public static void threebonacciSequence(InputStream in) {
		MyScanner scan = new MyScanner(in);
		String[] terms = getAllThreebonacciSequence(61);
		
		String input = null;
		while ((input = scan.next()) != null) {
			int n = Integer.parseInt(input);
			System.out.println(terms[n-1]);
		}
		scan.close();
	}
	
	private static String[] getAllThreebonacciSequence(int n) {
		String[] ret = new String[n];
		BigInteger a = BigInteger.ONE;
		BigInteger b = BigInteger.ONE;
		int i = 0; 
		while (i < n) {
			BigInteger t = b;
			b = a.add(b);
			a = t;
			if (b.mod(BigInteger.valueOf(3)).longValue() == 0 || b.toString().contains("3")) {
				ret[i++] = b.toString();
			}
		}
		return ret;
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
				} catch (Exception e) {
					return null;
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
			} catch (Exception e) {
				return null;
			}
			return str;
		}
	}
}
