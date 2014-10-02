import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;


public class Magnesium2014 {
	public static class Node {
		int attractiveness;
		int steps;
		int intersection;
		public Node(int attractiveness, int steps, int intersection) {
			this.attractiveness = attractiveness;
			this.steps = steps;
			this.intersection = intersection;
		}
	}
	
    public int solution(int N, int[] A, int[] B, int[] C) {
    	@SuppressWarnings("unchecked")
		ArrayList<Integer>[] intersections = new ArrayList[N];
    	for (int i = 0; i < A.length; i++) {
			if (intersections[A[i]] == null) {
				intersections[A[i]] = new ArrayList<Integer>();
			}
			intersections[A[i]].add(i);
			
			if (intersections[B[i]] == null) {
				intersections[B[i]] = new ArrayList<Integer>();
			}
			intersections[B[i]].add(i);
		}
    	
    	Stack<Node> q = new Stack<Node>();
    	for (int i = 0; i < N; i++) {
			q.add(new Node(0, 0, i));
		}
    	
    	int[] bestStepsPerIntersection = new int[N+1];
    	Arrays.fill(bestStepsPerIntersection, -1);
    	int max = -1;
    	while (!q.isEmpty()) {
    		Node top = q.pop();
    		if (top.steps < bestStepsPerIntersection[top.intersection]) {
    			//continue;
    		}
    		bestStepsPerIntersection[top.intersection] = top.steps;
    		
    		if (top.steps > max) {
    			max = top.steps;
    			System.out.println("New max: " + max);
    		}
    		
    		if (intersections[top.intersection] != null) {
	    		for (int i = 0; i < intersections[top.intersection].size(); i++) {
	    			int road = intersections[top.intersection].get(i); 
					if (C[road] > top.attractiveness) {
						int newIntersection = A[road];
						if (newIntersection == top.intersection) {
							newIntersection = B[road];
						}
						Node newN = new Node(C[road], top.steps + 1, newIntersection);
						if (newN.steps >= bestStepsPerIntersection[newN.intersection]) {
							q.add(newN);
						}
					}
				}
    		}
    	}
    	
		return max;
        
    }
    
    public static void main(String args[]) {
    	Magnesium2014 ex = new Magnesium2014();
    	test1(ex);
    	test3(ex);
    	test4(ex);
    	test2(ex);
    }

	private static void test1(Magnesium2014 ex) {
		int N = 6;
		int[] A = {0, 1, 1, 2, 3, 4, 5, 3};
		int[] B = {1, 2, 3, 3, 4, 5, 0, 2};
		int[] C = {4, 3, 2, 5, 6, 6, 8, 7};
    	System.out.println(ex.solution(N, A, B, C));
		
	}

	private static void test3(Magnesium2014 ex) {
		int N = 6;
		int[] A = {0, 0, 1, 1, 1, 0, 1, 1};
		int[] B = {1, 0, 0, 0, 0, 0, 1, 1};
		int[] C = {4, 3, 2, 5, 6, 6, 8, 7};
    	System.out.println(ex.solution(N, A, B, C));
		
	}

	private static void test4(Magnesium2014 ex) {
		int N = 6;
		int[] A = {};
		int[] B = {};
		int[] C = {};
    	System.out.println(ex.solution(N, A, B, C));
		
	}

	private static void test2(Magnesium2014 ex) {
		int N = 2;
		int M = 200000;
		int[] A = new int[M];
		int[] B = new int[M];
		int[] C = new int[M];
		for (int i = 0; i < M; i++) {
			A[i] = (int) (Math.random()*N);
			B[i] = (int) (Math.random()*N);
			C[i] = (int) (Math.random()*N);
		}
    	System.out.println(ex.solution(N, A, B, C));
		
	}

}
