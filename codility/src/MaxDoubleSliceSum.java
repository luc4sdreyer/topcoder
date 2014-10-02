import java.util.ArrayList;
import java.util.Collections;


public class MaxDoubleSliceSum {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		test1();
		test2();
		test3();
		testAll();
	}
	
	public static void test1() {
		Solution s = new Solution();
		int[] A = {3, 2, 6, -1, 4, 5, -1, 2};
		System.out.println("17 = " + s.solution(A));
	}
	
	public static void test2() {
		Solution s = new Solution();
		int[] A = {5, -7, 3, 5, -2, 4, -1};
		System.out.println("12 = " + s.solution(A));
	}
	
	public static void test3() {
		Solution s = new Solution();
		int[] A = {0, 5, -7, 3, 5, -2, 4, -1};
		System.out.println("15 = " + s.solution(A));
	}
	
	public static void testAll() {
		Solution s = new Solution();
		int n = 9;
		int size = (int) Math.pow(10, n);
		for (int i = 0; i < size; i++) {
			int[] A = new int[n];
			for (int j = 0; j < A.length; j++) {
				A[j] = ((i / (int)Math.pow(10, j)) % 10) - 5;
			}
			int realMax = longSolution(A);
			int myMax = s.solution(A);
			if (realMax != myMax) {
				System.out.print("");
				s.solution(A);
			}
		}
	}
	
	public static int longSolution(int[] A) {
		int max = Integer.MIN_VALUE;
		for (int x = 0; x < A.length; x++) {
			for (int y = x+1; y < A.length; y++) {
				for (int z = y+1; z < A.length; z++) {
					int sum = 0;
					for (int i = x+1; i < z; i++) {
						if (i != y) {
							sum += A[i];
						}
					}
					max = Math.max(max, sum);
				}
			}
		}
		return max;
	}
	
	static class Solution {
	    public int solution(int[] A) {
	    	int start = 1;
	    	int end = A.length-1;
			int maxEnding = 0;
			int maxSlice = 0;
			int maxStart = start;
			int maxEnd = maxStart;
			int newMaxStart = maxStart;
			//int minInMaxSeq = Integer.MAX_VALUE;
	    	for (int i = start; i < end; i++) {
				if (maxEnding == 0) {
					//minInMaxSeq = Integer.MAX_VALUE;
					newMaxStart = i;
				} else {
					//minInMaxSeq = Math.min(minInMaxSeq, A[i]);
				}
				maxEnding = Math.max(0, maxEnding + A[i]);
				if (maxEnding > maxSlice) {
					maxStart = newMaxStart;
					maxSlice = maxEnding;
					maxEnd = i+1;
				}
			}
	    	while (maxStart-1 >= start && A[maxStart] > A[maxStart-1] && A[maxStart-1] >= 0) {
	    		maxStart--;
	    	}
	    	while (maxEnd < end && A[maxEnd-1] > A[maxEnd] && A[maxEnd] >= 0) {
	    		maxEnd++;
	    	}
	    	int minInMaxSeq = Integer.MAX_VALUE;
	    	for (int i = maxStart; i < maxEnd; i++) {
	    		minInMaxSeq = Math.min(minInMaxSeq, A[i]);
	    	}
	    	ArrayList<Integer> best = new ArrayList<Integer>();
	    	best.add(maxSlice - minInMaxSeq);
	    	
	    	int newMaxEnding = 0;
			int newMaxSlice = 0;
			for (int i = maxStart-2; i >= start; i--) {
				newMaxEnding = newMaxEnding + A[i];
				newMaxSlice = Math.max(newMaxSlice, newMaxEnding);
			}
			
	    	best.add(maxSlice + newMaxSlice);
	    	
	    	newMaxEnding = 0;
			newMaxSlice = 0;
			for (int i = maxEnd+1; i < end; i++) {
				newMaxEnding = newMaxEnding + A[i];
				newMaxSlice = Math.max(newMaxSlice, newMaxEnding);
			}
			
	    	best.add(maxSlice + newMaxSlice);
	    	Collections.sort(best);
	    	
	        return best.get(best.size()-1);
	    }
		
		public int maximumSlice(int[] S, int start, int end) {
			int maxEnding = 0;
			int maxSlice = 0;
			
			for (int i = start; i < end; i++) {
				maxEnding = Math.max(0, maxEnding + S[i]);
				maxSlice = Math.max(maxSlice, maxEnding);
			}
			
			return maxSlice;
		}
	}


}
