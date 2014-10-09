public class LargestSubsequence {

	public String getLargest(String s) {
		String max = "";
		
		for (int i = 0; i < s.length(); i++) {
			int nextIdx = getHighestIdx(s.substring(i));
			max += s.charAt(i + nextIdx);
			i += nextIdx;
		}
		return max;
	}
	
	public int getHighestIdx(String s) {
		char max = 'a';
		int idx = 0;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) > max) {
				max = s.charAt(i);
				idx = i;
			}
		}
		return idx;
	}

}
