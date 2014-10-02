public class TeamBuilder {

	public int[] specialLocations(String[] paths) {		
		int[][] shortestPaths = new int[paths.length][paths.length];
		
		for (int i = 0; i < shortestPaths.length; i++) {
			for (int j = 0; j < shortestPaths.length; j++) {
				if (paths[i].charAt(j) == '1') {
					shortestPaths[i][j] = 0;
				} else {
					shortestPaths[i][j] = 1;
				}
			}
		}
		
		for (int k = 0; k < shortestPaths.length; k++) {
			for (int i = 0; i < shortestPaths.length; i++) {
				for (int j = 0; j < shortestPaths.length; j++) {
					shortestPaths[i][j] = Math.min(shortestPaths[i][j], shortestPaths[i][k] + shortestPaths[k][j]);
				}
			}
		}
		
		int numGotoAll = 0;
		for (int i = 0; i < shortestPaths.length; i++) {
			boolean gotoAll = true;
			for (int j = 0; j < shortestPaths.length; j++) {
				if (i != j && shortestPaths[i][j] > 0) {
					gotoAll = false;
				} 
			}
			if (gotoAll) {
				numGotoAll++;
			}
		}
		
		int numComeFromAll = 0;
		for (int i = 0; i < shortestPaths.length; i++) {
			boolean comeFromAll = true;
			for (int j = 0; j < shortestPaths.length; j++) {
				if (i != j && shortestPaths[j][i] > 0) {
					comeFromAll = false;
				} 
			}
			if (comeFromAll) {
				numComeFromAll++;
			}
		}
		
		int[] a = {numGotoAll, numComeFromAll};
		return a;
	}

}
