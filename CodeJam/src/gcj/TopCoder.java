package gcj;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

public class TopCoder {

	public static void main(String[] args) {
		new TopCoder();

	}
	
	private TopCoder() {
		bitmap = new boolean[400][600];
		for (boolean[] a:bitmap) {
			Arrays.fill(a, false);
		}

		String Rectangles[] = {"0 192 399 207", "0 392 399 407", "120 0 135 599", "260 0 275 599"};
		//String Rectangles[] = {"0 2 3 2"};

		for(String rectangle:Rectangles) {
			Scanner sc = new Scanner(rectangle);
			int x1 = sc.nextInt();
			int y1 = sc.nextInt();
			int x2 = sc.nextInt();
			int y2 = sc.nextInt();
			for (int i = x1; i <= x2; i++) {
				for (int j = y1; j <= y2; j++) {
					bitmap[i][j] = true;
				}
			}
		}
		
		/*
		for (int i = 0; i < bitmap.length; i++) {
			for (int j = 0; j < bitmap[0].length; j++) {
				System.out.print(bitmap[i][j]+" ");
			}
			System.out.println();
		}*/

		ArrayList<Integer> result = new ArrayList<Integer>();

		for (int i = 0; i < bitmap.length; i++) {
			for (int j = 0; j < bitmap[0].length; j++) {
				if (bitmap[i][j] == false) {
					result.add(doFill(i,j));
				}				
			}
		}
		for (int j = 0; j < result.size(); j++) {
			if (result.get(j) != 0) {
				System.out.println(result.get(j)+", ");				
			}				
		}
		
		
	}

	public static boolean[][] bitmap;

	int doFill(int x, int y) {
		int result = 0;

		// Declare stack of nodes
		Stack<node> s = new Stack<node>();
		s.push(this.new node(x, y));

		while (s.empty() == false) {
			node top = s.pop();

			if (top.x < 0 || top.x >= bitmap.length-1) continue;
			if (top.y < 0 || top.y >= bitmap[0].length) continue;
			if (bitmap[top.x][top.y]) continue;

			bitmap[top.x][top.y] = true; // Record visit

			result++;

			// visit every adjacent node
			s.push(new node(top.x + 1, top.y));
			s.push(new node(top.x - 1, top.y));
			s.push(new node(top.x, top.y + 1));
			s.push(new node(top.x, top.y - 1));
		}
		return result;
	}	

	private class node {public int x;public int y;
	public node(int x, int y) {this.x = x;this.y = y;}
	public String toString() {return String.valueOf(x)+":"+String.valueOf(y);}
	}
}
