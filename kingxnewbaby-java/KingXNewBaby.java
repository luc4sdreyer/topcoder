public class KingXNewBaby {

	public String isValid(String name) {
		int numCon = 0;
		int numVow = 0;
		char v1 = 'x';
		char v2 = 'x';
		for (int i = 0; i < name.length(); i++) {
			char c = name.charAt(i); 
			if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
				numVow++;
				if (numVow == 1) {
					v1 = c;
				} else if (numVow == 2) {
					v2 = c;
				}
			} else {
				numCon++;
			}
		}
		if (numVow == 2 && v1 == v2 && numCon == 6) {
			return "YES";
		} else {
			return "NO";
		}
	}

}
