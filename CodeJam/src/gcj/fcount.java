package gcj;

import java.util.HashMap;

public class fcount {

	public static void main(String[] args) {
		String[] data = {"hello", "sdf", "sdf", "fg", "asas", "tgrrgt", "fg", "sdf"};
		HashMap<String, Integer> map = new HashMap<String,Integer>();
		for (int i = 0; i < data.length; i++) {
			if (map.containsKey(data[i])) {
				map.put(data[i],map.get(data[i])+1);
			} else {
				map.put(data[i],1);
			}
		}
		for (String string : map.keySet()) {
			System.out.println(string+":"+map.get(string));
		}
	}

}
