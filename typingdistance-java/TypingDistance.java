import java.util.HashMap;

public class TypingDistance {

	public int minDistance(String keyboard, String word) {
		
		HashMap<Character, Integer> keyb = new HashMap<Character, Integer>(); 
		for (int i = 0; i < keyboard.length(); i++) {
			keyb.put(keyboard.charAt(i), i);
		}
		int dist = 0;
		for (int i = 1; i < word.length(); i++) {
			dist += Math.abs(keyb.get(word.charAt(i)) - keyb.get(word.charAt(i-1)));
		}
		return dist;
	}

}
