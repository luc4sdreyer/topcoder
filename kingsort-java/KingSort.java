import java.util.Arrays;
import java.util.Comparator;

public class KingSort {

	public String[] getSortedList(String[] kings) {	
		Arrays.sort(kings, new Comparator<String>() {
			@Override
			public int compare(String s1, String s2) {
				// TODO Auto-generated method stub
				String[] k1 = s1.split(" ");
				String[] k2 = s2.split(" ");
				int c1 = k1[0].compareTo(k2[0]);
				if (c1 != 0) {
					return c1;
				}
				int n1 = parseRomanNum(k1[1]);
				int n2 = parseRomanNum(k2[1]);				
				return Integer.compare(n1, n2);
			}
		});
		return kings;
	}
	
	public int parseRomanNum(String num) {
		if (num.indexOf("XXX") == 0) {	return 30 + parseRomanNum(num.substring(3));	}
		else if (num.indexOf("XX") == 0) {	return 20 + parseRomanNum(num.substring(2));	}
		else if (num.indexOf("XL") == 0) {	return 40 + parseRomanNum(num.substring(2));	}
		else if (num.indexOf("X") == 0) {	return 10 + parseRomanNum(num.substring(1));	}
		else if (num.indexOf("L") == 0) {	return 50 + parseRomanNum(num.substring(1));	}
		else if (num.indexOf("III") == 0) {	return 3;	}
		else if (num.indexOf("II") == 0) {	return 2;	}
		else if (num.indexOf("IV") == 0) {	return 4;	}
		else if (num.indexOf("IX") == 0) {	return 9;	}
		else if (num.indexOf("I") == 0) {	return 1;	}
		else if (num.indexOf("VIII") == 0) {	return 8;	}
		else if (num.indexOf("VII") == 0) {	return 7;	}
		else if (num.indexOf("VI") == 0) {	return 6;	}
		else if (num.indexOf("V") == 0) {	return 5;	}
		else if (num.length() == 0) { return 0; }
		else {
			System.err.println("FAILED: " + num);
			return 0;
		}
	}

}
