import java.util.Arrays;
import java.util.HashSet;


public class PerfectPermutation {
	
	public static void main (String[] args) {
		PerfectPermutation p = new PerfectPermutation();
		System.out.println(p.reorderSlow(new int[]{0,1,2,3}));
		System.out.println(p.reorder(new int[]{0,1,2,3}));
	}

	public int reorder(int[] P) {
		int min = P.length;
		for (int k = 0; k < P.length; k++) {
			for (int j = k; j < P.length; j++) {
				int[] Q = P.clone();
				int temp = Q[k];
				Q[k] = Q[j];
				Q[j] = temp;

				int[] child = new int[P.length];
				child[0] = 0;
				for (int i = 1; i < child.length; i++) {
					child[i] = Q[child[i-1]];
				}
				HashSet<Integer> set = new HashSet<>();
				for (int i = 0; i < child.length; i++) {
					set.add(child[i]);
				}
				if (set.size() == P.length) {
					System.out.println(Arrays.toString(Q));
					int diff = 0;
					for (int i = 0; i < Q.length; i++) {
						if (Q[i] != P[i]) {
							diff++;
						}
					}
					min = Math.min(min, diff);
				}
			}
		}
		return min;
	}
	
	public int reorderSlow(int[] P) {
		int[] Q = new int[P.length];
		for (int i = 0; i < Q.length; i++) {
			Q[i] = i;
		}
		int min = P.length;
		do {
			int[] child = new int[P.length];
			child[0] = 0;
			for (int i = 1; i < child.length; i++) {
				child[i] = Q[child[i-1]];
			}
			HashSet<Integer> set = new HashSet<>();
			for (int i = 0; i < child.length; i++) {
				set.add(child[i]);
			}
			if (set.size() == P.length) {
				System.out.println(Arrays.toString(Q));
				int diff = 0;
				for (int i = 0; i < Q.length; i++) {
					if (Q[i] != P[i]) {
						diff++;
					}
				}
				min = Math.min(min, diff);
			}
		} while (next_permutation(Q));
		return min;
	}
	
	public boolean next_permutation(int str[]) {
		int temp;
		int a,b,l;
		for(int x = str.length-2, y = 0; x >= 0; x--) {
			if (str[x+1] > str[x]) {
				for(y = str.length-1; str[y] <= str[x]; y--);
				if (x != y) {
					temp = str[x];
					str[x] = str[y];
					str[y] = temp;
				}
				l = (str.length-x)/2;
				for(y = 1; y <= l; y++) {
					a = x+y;
					b = str.length-y;
					if (a != b) {
						temp = str[a];
						str[a] = str[b];
						str[b] = temp;
					}
				}
				return true;
			}
		}
		return false;
	}

}
