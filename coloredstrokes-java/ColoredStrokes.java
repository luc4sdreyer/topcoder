import java.util.Stack;

public class ColoredStrokes {

	public int getLeast(String[] picture) {
		char[][] pic = new char[picture.length][picture[0].length()];
		for (int i = 0; i < pic.length; i++) {
			pic[i] = picture[i].toCharArray();
		}
		
		int strokes = 0;
		for (int i = 0; i < 2; i++) {
			for (int y = 0; y < pic.length; y++) {
				for (int x = 0; x < pic[0].length; x++) {
					if (pic[y][x] != '.') {
						strokes += search(x, y, pic);
					}
				}
			}
		}
		return strokes;
	}
	
	public static class Node {
		int x,y;
		boolean horizontal;
		Node parent;
		public Node(int x, int y, boolean horizontal, Node parent) {
			this.x = x;
			this.y = y;
			this.horizontal = horizontal;
			this.parent = parent;
		}
	}

	private int search(int x, int y, char[][] pic) {
		Stack<Node> s = new Stack<>();
		Node top = null;
		if (pic[y][x] == 'G' || pic[y][x] == 'B') {
			top = new Node(x,y,false,null);
		} else if (pic[y][x] == 'G' || pic[y][x] == 'R') {
			top = new Node(x,y,true,null);
		}
		s.push(top);
		int strokes = 1;
		while (!s.isEmpty()) {
			top = s.pop();
			if (top.y < 0 || top.y >= pic.length || top.x < 0 || top.x >= pic[0].length) {
				continue;
			}
			if (pic[top.y][top.x] == '.') {
				continue;
			}
			if (pic[top.y][top.x] == 'B' && top.horizontal) {
				continue;
			}
			if (pic[top.y][top.x] == 'R' && !top.horizontal) {
				continue;
			}
			if (!top.horizontal) {
				s.add(new Node(top.x, top.y + 1, false, top));
				s.add(new Node(top.x, top.y - 1, false, top));
				if (pic[top.y][top.x] == 'G') {
					pic[top.y][top.x] = 'R';
				} else {
					pic[top.y][top.x] = '.';
				}
			}
			if (top.horizontal) {
				s.add(new Node(top.x + 1, top.y, true, top));
				s.add(new Node(top.x - 1, top.y, true, top));
				if (pic[top.y][top.x] == 'G') {
					pic[top.y][top.x] = 'B';
				} else {
					pic[top.y][top.x] = '.';
				}
			}
		}
		return strokes;
	}

}
