import java.util.Arrays;
import java.util.Random;

public class PeacefulLine {

	public String makeLine(int[] x) {
		int[] f = new int[26];
		int max = 0;
		for (int i = 0; i < x.length; i++) {
			f[x[i]]++;
		}
		for (int i = 0; i < f.length; i++) {
			max = Math.max(max, f[i]);
		}
		if (max <= (int)Math.ceil(x.length/2.0)) {
			return "possible";
		} else {
			return "impossible";
		}
	}
	
	public static void main (String[] args) {
		Random rand = new Random(0);
		PeacefulLine peacefulLine = new PeacefulLine();
		System.out.println(peacefulLine.makeLine2(new int[]{2, 3, 4, 3, 8, 3, 9, 14, 15, 21, 21}));
				
		for (int i = 0; i < 1000000; i++) {
			int[] x = new int[rand.nextInt(26)];
			for (int j = 0; j < x.length; j++) {
				x[j] = rand.nextInt(26);
			}
			String exp = peacefulLine.makeLine2(x);
			String act = peacefulLine.makeLine(x);
			if (!exp.equals(act)) {
				System.out.println(Arrays.toString(x));
			}
		}
	}

	public String makeLine2(int[] x) {
		Arrays.sort(x);
		for (int i = 0; i < x.length-1; i++) {
			for (int j = i+1; j < x.length; j++) {
				if (x[i] == x[j]) {
					for (int k = j+1; k < x.length; k++) {
						if (x[j] != x[k]) {
							int temp = x[j];
							x[j] = x[k];
							x[k]  = temp;
							break;
						}
					}
					break;
				} else {
					break;
				}
			}
		}
		for (int i = 0; i < x.length-1; i++) {
			if (x[i] == x[i+1]) {
				return "impossible";
			}
		}
		return "possible";
	}
	
}
