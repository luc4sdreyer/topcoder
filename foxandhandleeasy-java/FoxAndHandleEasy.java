public class FoxAndHandleEasy {

	public String isPossible(String S, String T) {
		for (int i = 0; i < T.length(); i++) {
			String sub = T.substring(i);
			if (sub.indexOf(S) >= 0) {
				sub = sub.replaceFirst(S, "");
				String original = T.substring(0,i)+sub;
				//System.out.println("original: "+original);
				if (original.equals(S)) {
					return "Yes";
				}
			}
		}
		return "No";
	}

}
