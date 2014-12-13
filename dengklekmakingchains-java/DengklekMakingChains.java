import java.util.ArrayList;

public class DengklekMakingChains {

	public int maxBeauty(String[] chains) {
		ArrayList<char[]> c = new ArrayList<>();
		for (int i = 0; i < chains.length; i++) {
			c.add(chains[i].toCharArray());
		}
		int[] idx = {-1, -1, -1};
		int[] best = {0, 0, 0};
		int max = 0;
		for (int i = 0; i < c.size(); i++) {
			char[] ch = c.get(i);
			int type = getType(ch);
			int value = 0;
			if (type == 2) {
				value = getValueMid(ch);
				idx[1] = i;
				best[1] += value;
				max = Math.max(max, value);
			} else if (type == 1) {
				value = getValueStart(ch);
				max = Math.max(max, value);
				if (value > best[0]) {
					idx[0] = i;
					best[0] = value;
				}
			} else if (type == 3) {
				value = getValueEnd(ch);
				max = Math.max(max, value);
				if (value > best[2]) {
					idx[2] = i;
					best[2] = value;
				}
			} else if (type == 4) {
				value = getValueStart(ch);
				max = Math.max(max, value);
				if (value > best[0]) {
					idx[0] = i;
					best[0] = value;
				}
				value = getValueEnd(ch);
				if (value > best[2]) {
					idx[2] = i;
					best[2] = value;
				}
			} else {
				value = getValueMid(ch);
				max = Math.max(max, value);
			}
		}
		if (idx[0] == idx[2] && idx[0] != -1) {
			char[] start = c.get(idx[0]);
			start[0] = '.';
			String[] chains1 = chains.clone();
			chains1[idx[0]] = new String(start);
			char[] end = c.get(idx[2]);
			end[2] = '.';
			String[] chains2 = chains.clone();
			chains2[idx[0]] = new String(end);
			max = Math.max(max, Math.max(maxBeauty(chains1), maxBeauty(chains2)));
		} else {
			max = Math.max(max, best[0] + best[1] + best[2]);
		}
		return max;
	}
	
	

	private int getType(char[] c) {
		if (c[0] != '.' && c[1] != '.' && c[2] != '.') {
			return 2;
		} else if (c[0] == '.' && c[1] != '.' && c[2] != '.') {
			return 1;               
		} else if (c[0] == '.' && c[1] == '.' && c[2] != '.') {
			return 1;               
		} else if (c[0] != '.' && c[1] != '.' && c[2] == '.') {
			return 3;              
		} else if (c[0] != '.' && c[1] == '.' && c[2] == '.') {
			return 3;              
		} else if (c[0] == '.' && c[1] == '.' && c[2] == '.') {
			return 0;               
		} else if (c[0] != '.' && c[1] == '.' && c[2] != '.') {
			return 4;
		} else {
			return 0;
		}		
	}
	
	private int getValueStart(char[] c) {
		int sum = 0;
		int i = 1;
		while (c[i] == '.') {
			i++;
		}
		while (i < c.length) {
			sum += c[i] - '0';
			i++;
		}
		return sum;
	}
	private int getValueMid(char[] c) {
		int sum = 0;
		int i = 0;
		while (i < c.length && c[i] == '.') {
			i++;
		}
		while (i < c.length) {
			if (c[i] != '.') {
				sum += c[i] - '0';
			}
			i++;
		}
		return sum;
	}
	
	private int getValueEnd(char[] c) {
		int sum = 0;
		for (int i = 0; i < c.length && c[i] != '.'; i++) {
			sum += c[i] - '0';
		}
		return sum;
	}

}
