import java.util.HashSet;

public class FortunateNumbers {

	public int getFortunate(int[] a, int[] b, int[] c) {
		HashSet<Integer> num = new HashSet<>();
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < b.length; j++) {
				for (int k = 0; k < c.length; k++) {
					int n = a[i]+b[j]+c[k];					
					if (!num.contains(n)) {
						String s = String.valueOf(n);
						boolean valid = true;
						for (int l = 0; l < s.length(); l++) {
							if (s.charAt(l) != '5' && s.charAt(l) != '8') {
								valid = false;
								break;
							}
						}
						if (valid) {
							num.add(n);
						}
					}
				}
			}
		}
		return num.size();
	}

}
