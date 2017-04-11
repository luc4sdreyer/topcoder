import java.util.HashSet;



public class UnrelatedPaths {

	public int maxUnrelatedPaths(int[] parent) {
		HashSet<Integer> hasChild = new HashSet<>();
		for (int i = 0; i < parent.length; i++) {
			hasChild.add(parent[i]);
		}
		int numVert = parent.length + 1;
		
		return numVert - hasChild.size();
	}

}
