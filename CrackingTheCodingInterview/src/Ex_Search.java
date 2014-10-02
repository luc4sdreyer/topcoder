import java.util.ArrayList;
import java.util.Collections;

class Ex11_Search {
	public static void main(String[] args) {
		test();
	}

	private static void test() {
		boolean preset = false;
		int[] a = null;
		ArrayList<Integer> t = new ArrayList<Integer>();
		if (preset) {
			int[] temp = {6, 2, 1, 5, 3, 4};
			a = temp;
			for (int i = 0; i < a.length; i++) {
				t.add(a[i]);
			}
		} else {
			a = new int[10000000 + (int)(Math.random()*2)];
			for (int i = 0; i < a.length; i++) {
				t.add(i + (int)(Math.random()*2 - a.length/2));
			}
			Collections.sort(t);
			for (int i = 0; i < a.length; i++) {
				a[i] = t.get(i);
			}
		}
		
		for (int i = 0; i < a.length; i++) {
			if (a[binarySearch(a, a[i])] != a[i]) {
				System.out.println("binarySearch failed");
				return;
			}
		}
		System.out.println("binarySearch 100% success rate!");
	}
	
	public static int binarySearch(int[] array, int target) {
		int left = 0;
		int right = array.length-1;
		while (left <= right) {
			int mid = (left + right)/2;
			if (array[mid] < target) {
				left = mid + 1;
			} else if (array[mid] > target) {
				right = mid - 1;
			} else {
				return mid;
			}
		}
		return -1; // not found
	}
}