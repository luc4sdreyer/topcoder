
public class Ex11_3 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int[] a = {15, 16, 19, 20, 25, 1, 3, 4, 5, 7, 10, 14};
		for (int i = 0; i < a.length; i++) 
			System.out.println(a[i] + "==" + a[rotatedBinarySearch(a, a[i])]);{
			
		}
		
	}

	public static int rotatedBinarySearch(int[] a, int target) {
		int left = 0;
		int right = a.length - 1;
		int rotationIdx = 0;
		while (left <= right) {
			int mid = (left + right)/2;
			if (a[mid] > a[(mid+1) % a.length]) {
				rotationIdx = (mid+1) % a.length;
				break;
			} else if (a[mid] <= a[right] && a[left] <= a[mid]) {
				left = mid + 1;
			} else if (a[mid] >= a[right]) {
				left = mid + 1;
			} else {
				right = mid - 1;
			}
		}

		left = rotationIdx;
		right = a.length - 1 + rotationIdx;
		while (left <= right) {
			int mid = (left + right)/2;
			if (a[mid % a.length] > target) {
				right = mid - 1;
			} else if (a[mid % a.length] < target) {
				left = mid + 1;
			} else {
				return mid % a.length;
			}
		}

		return -1;
	}

}
