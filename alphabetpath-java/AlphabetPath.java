public class AlphabetPath {

	public String doesItExist(String[] letterMaze) {
		for (int i = 0; i < letterMaze.length; i++) {
			for (int j = 0; j < letterMaze[0].length(); j++) {
				char c = letterMaze[i].charAt(j);
				char n = (char) (c+1);
				if (c != 'Z' && c != '.') {
					if (!((i >= 1 && letterMaze[i-1].charAt(j) == n) 
						|| (i < letterMaze.length-1 && letterMaze[i+1].charAt(j) == n)
						|| (j >= 1 && letterMaze[i].charAt(j-1) == n)
						|| (j < letterMaze[0].length()-1 && letterMaze[i].charAt(j+1) == n))) {
						return "NO";
					}
				}
			}
		}
		return "YES";
	}

}
