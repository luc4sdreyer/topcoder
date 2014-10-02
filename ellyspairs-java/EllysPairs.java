import java.util.ArrayList;
import java.util.Collections;

public class EllysPairs {

	public int getDifference(int[] knowledge) {
		ArrayList<Integer> k = new ArrayList<Integer>();
		for (int i = 0; i < knowledge.length; i++) {
			k.add(knowledge[i]);
		}
		Collections.sort(k);
		ArrayList<Integer> p = new ArrayList<Integer>();
		for (int i = 0; i < knowledge.length/2; i++) {
			p.add(k.get(i)+k.get(knowledge.length-i-1));
		}
		Collections.sort(p);
		
		return Collections.max(p)-Collections.min(p);
	}

}
