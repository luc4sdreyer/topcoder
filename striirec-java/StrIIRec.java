public class StrIIRec {

	
	public String recovstr(int n, int minInv, String minStr) {
		String s = "";
		for (int i = 0; i < n; i++) {
			s += "" + (char)(i + 'a');
		}
		return minStr(s, minInv, minStr);
	}

	public String minStr(String letters, int minInv, String minStr) {
		if (letters.length() == 0) {
			return "";
		}
		for (int i = 0; i < letters.length(); i++) {
	        if ( (minStr.length() > 0) && (minStr.charAt(0) > letters.charAt(i)) ) {
	            continue;
	        }
			int newN = letters.length()-1;
			int newMin = Math.max(minInv-i, 0);
			if (newN*(newN-1)/2 >= newMin) {
				String sub = letters.substring(0, i) + letters.substring(i+1, letters.length());
				String nminStr = "";
	            if ( (minStr.length() > 0) && (minStr.charAt(0) == letters.charAt(i)) ) {
	                nminStr = minStr.substring(1);
	            }
				return letters.charAt(i) + minStr(sub, newMin, nminStr);	
			}
		}
		return "";
	}

}
