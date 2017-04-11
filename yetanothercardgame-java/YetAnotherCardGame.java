import java.util.ArrayList;
import java.util.Arrays;

public class YetAnotherCardGame {

	public int maxCards(int[] petr, int[] snuke) {
		Arrays.sort(petr);
		Arrays.sort(snuke);
		ArrayList<Integer> p = new ArrayList<>();
		for (int i = 0; i < snuke.length; i++) {
			p.add(petr[i]);
		}
		ArrayList<Integer> s = new ArrayList<>();
		for (int i = 0; i < snuke.length; i++) {
			s.add(snuke[i]);
		}
		int prev = -1;
		int n = 0;
		int turn = 0;
		boolean first = true;
		for (int j = 0; j < snuke.length*2; j++) {
			if (turn % 2 == 0) {
				if (first) {
					first = false;
					prev = p.remove(0);
					n++;
				} else {
					boolean found = false;
					int next = 0;
					for (int i = 0; i < s.size(); i++) {
						if (s.get(i) > prev) {
							next = s.get(i);
							break;
						}
					}
					for (int i = 0; i < s.size(); i++) {
						if (p.get(i) > prev && (s.isEmpty() || p.get(i) < next)) {
							found = true;
							prev = p.remove(0);
							n++;
							break;
						}
					}
					if (!found) {
						p.remove(0);
					}
				}
			} else {
				boolean found = false;
				int next = 0;
				for (int i = 0; i < p.size(); i++) {
					if (p.get(i) > prev) {
						next = p.get(i);
						break;
					}
				}
				for (int i = 0; i < s.size(); i++) {
					if (s.get(i) > prev && (p.isEmpty() || s.get(i) < next)) {
						found = true;
						prev = s.remove(0);
						n++;
						break;
					}
				}
				if (!found) {
					s.remove(0);
				}
			}
			turn++;
			System.out.println(prev);
		}
		return n;
	}

	public int maxCards3(int[] petr, int[] snuke) {
		Arrays.sort(petr);
		Arrays.sort(snuke);
		ArrayList<Integer> p = new ArrayList<>();
		for (int i = 0; i < snuke.length; i++) {
			p.add(petr[i]);
		}
		ArrayList<Integer> s = new ArrayList<>();
		for (int i = 0; i < snuke.length; i++) {
			s.add(snuke[i]);
		}
		int prev = p.remove(0);
		int n = 1;
		int turn = 1;
		while (!p.isEmpty() || !s.isEmpty()) {
			if (turn % 2 == 0) {
				while (!p.isEmpty() && p.get(0) <= prev) {
					p.remove(0);
				}
				if (!s.isEmpty()) {
					if (!p.isEmpty() && p.get(0) < s.get(0)) {
						prev = p.remove(0);
						n++;
					}
				} else {
					if (!p.isEmpty()) {
						prev = p.remove(0);
						n++;
					}
				}
			} else {
				while (!s.isEmpty() && s.get(0) <= prev) {
					s.remove(0);
				}
				if (!p.isEmpty()) {
					if (!s.isEmpty() && s.get(0) < p.get(0)) {
						prev = s.remove(0);
						n++;
					}
				} else {
					if (!s.isEmpty()) {
						prev = s.remove(0);
						n++;
					}
				}
			}
			turn++;
		}
		return n;
	}


	public int maxCards2(int[] petr, int[] snuke) {
		int[] a = new int[petr.length*2];
		Arrays.sort(petr);
		Arrays.sort(snuke);
		for (int i = 0; i < petr.length; i++) {
			a[i*2] = petr[i];
			a[i*2 + 1] = snuke[i];
		}
		int prev = a[0];
		int n = 1;
		for (int i = 1; i < a.length-1; i++) {
			if (i == a.length - 1) {
				if (a[i] > prev) {
					prev = a[i];
					n++;
				}
			}
			if (a[i] > prev && a[i] < a[i+1]) {
				prev = a[i];
				n++;
				System.out.println(prev);
			}
		}
		return n;
	}

}
