import java.util.ArrayList;

class Ex9_5 {
	public static void main(String args[]) {
		ArrayList<String> perm = getPermutations("abcd");
		for (String p : perm) {
			System.out.println(p);
		}
	}

	public static ArrayList<String> getPermutations(String s) {	
		if (s == null || s.length() == 0) {
			return new ArrayList<String>();
		}
		if (s.length() == 1) {
			ArrayList<String> nextPerm = new ArrayList<String>();
			nextPerm.add(s);
			return nextPerm;
		}
		ArrayList<String> perm = getPermutations(s.substring(0, s.length()-1));
		String nextChar = s.substring(s.length()-1, s.length());
		ArrayList<String> nextPerm = new ArrayList<String>();
		for (int i = 0; i < perm.size(); i++) {
			String current = perm.get(i);
			for (int j = 1; j < current.length(); j++) {
				nextPerm.add(current.substring(0, j) + nextChar + current.substring(j, current.length()));
			}
			nextPerm.add(nextChar + current);
			nextPerm.add(current + nextChar);
		}
		return nextPerm;
	}
}