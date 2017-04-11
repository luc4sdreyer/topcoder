import java.util.ArrayList;

public class ModModMod {

	public long findSum(int[] m, int R) {
		ArrayList<Integer> m2 = new ArrayList<>();
		m2.add(m[0]);
		for (int i = 0; i < m.length; i++) {
			if (m[i] < m2.get(m2.size()-1)) {
				m2.add(m[i]);
			}
		}
		
		int rem = R % m[0];
		int div = R / m[0];
		
		long remSum = 0;
		long divSum = 0;
		int cursor = m2.size()-1;
		for (int f = 0; f < m[0]; f++) {
			int thisRem = f;
			while (cursor > 0 && f > m2.get(cursor)) {
				cursor--;
			}
			for (int i = cursor; i < m2.size(); i++) {
			//for (int i = 0; i < m2.size(); i++) {
				thisRem = thisRem % m2.get(i);
			}
			if (f <= rem) {
				remSum += thisRem;
			}
			divSum += thisRem;
		}
		return divSum * div + remSum;
	}

}
