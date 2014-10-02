import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;

class Ex11_Sort {
	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			test();
		}
	}

	public static void test() {

		LinkedHashMap<String, Double> times = new LinkedHashMap<String, Double>();
		for (int i = 0; i < SorterFactory.NUM_SORTERS; i++) {
			times.put(SorterFactory.getSorter(i).getClass().getSimpleName(), 0.0);
		}
		for (int j = 0; j < 1000; j++) {
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
				a = new int[1000 + (int)(Math.random()*2)];
				for (int i = 0; i < a.length; i++) {
					t.add(i + (int)(Math.random()*2 - a.length/2));
				}
				Collections.shuffle(t);
				for (int i = 0; i < a.length; i++) {
					a[i] = t.get(i);
				}
			}
			int[] sorted = new int[a.length];
			Collections.sort(t);
			for (int i = 0; i < a.length; i++) {
				sorted[i] = t.get(i);
			}

			for (int i = 0; i < SorterFactory.NUM_SORTERS; i++) {
				Sorter sorter = SorterFactory.getSorter(i);
				long time = System.nanoTime();
				sorter.sort(a.clone());
				sorter.test(sorted);
				times.put(sorter.getName(), times.get(sorter.getName()) + (System.nanoTime() - time));
			}

		}
		double min = Double.MAX_VALUE;
		for (double d : times.values()) {
			min = Math.min(min, d);
		}
		for (String key : times.keySet()) {
			times.put(key, times.get(key) / min);
		}
		System.out.println(times.toString());
	}

	public static class SorterFactory {
		public static final int NUM_SORTERS = 4; 
		public static Sorter getSorter(int type) {
			if (type == 0) {
				return new BucketSort();
			} else if (type == 1) {
				return new RadixSort();
			} else if (type == 2) {
				return new MergeSort();
			} else if (type == 3) {
				return new QuickSort();
			} else if (type == 4) {
				return new BubbleSort();
			} else if (type == 5) {
				return new SelectionSort();
			}
			return null;
		}
	}

	public abstract static class Sorter {
		int[] inputArray;
		public void test(int[] sorted) {
			for (int i = 0; i < sorted.length; i++) {
				if (inputArray[i] != sorted[i]) {
					System.out.println(getName() + " failed");
					break;
				}
			}
		}
		public abstract void sort(int[] clone);
		public String getName() {
			return this.getClass().getSimpleName();
		}
	}

	public static class BubbleSort extends Sorter {
		public void sort(int[] array) {
			this.inputArray = array;
			boolean sorted = false;
			while (!sorted) {
				sorted = true;
				for (int i = 1; i < array.length; i++) {
					if (array[i-1] > array[i]) {
						sorted = false;
						int temp = array[i-1];
						array[i-1] = array[i];
						array[i] = temp;
					}
				}
			}
		}
	}

	public static class SelectionSort extends Sorter {
		public void sort(int[] array) {
			this.inputArray = array;
			for (int i = 0; i < array.length; i++) {
				int min = Integer.MAX_VALUE;
				int idx = -1;
				for (int j = i; j < array.length; j++) {
					if (array[j] < min) {
						min = array[j];
						idx = j;
					}
				}
				if (idx != -1) {
					int temp = array[idx];
					array[idx] = array[i];
					array[i] = temp;
				}
			}
		}
	}



	public static class MergeSort extends Sorter {
//		public void sort(int[] array) {
//			int[] helper = new int[array.length];
//			mergeSort(array, helper, 0, array.length-1);
//		}
//
//		public static void mergeSort(int[] array, int[] helper, int low, int high) {
//			if (low < high) {
//				int mid = (low + high)/2;
//				mergeSort(array, helper, low, mid);
//				mergeSort(array, helper, mid+1, high);
//				merge(array, helper, low, mid, high);
//			}
//		}
//
//		public static void merge(int[] array, int[] helper, int low, int mid, int high) {
//			for (int i = low; i <= high; i++) {
//				helper[i] = array[i];
//			}
//
//			int helperLow = low;
//			int helperHigh = mid+1;
//			int current = low;
//
//			while (helperLow <= mid && helperHigh <= high) {
//				if (helper[helperLow] <= helper[helperHigh]) {
//					array[current++] = helper[helperLow++];
//				} else {
//					array[current++] = helper[helperHigh++];
//				}
//			}
//
//			int remaining = mid - helperLow;
//			for (int i = 0; i <= remaining; i++) {
//				array[current + i] = helper[helperLow + i];
//			}
//		}


		public void sort(int[] array) {
			this.inputArray = array;
			int[] helper = new int[array.length];
			mergeSort(array, helper, 0, array.length-1);
		}

		public static void mergeSort(int[] array, int[] helper, int left, int right) {
			if (left < right) {
				int mid = (left + right)/2;
				mergeSort(array, helper, left, mid);
				mergeSort(array, helper, mid+1, right);
				merge(array, helper, left, mid, right);
			}
		}

		public static void merge(int[] array, int[] helper, int left, int mid, int right) {
			for (int i = left; i <= right; i++) {
				helper[i] = array[i];
			}

			int helperLeft = left;
			int helperRight = mid+1;
			int current = left;

			while (helperLeft <= mid && helperRight <= right) {
				if (helper[helperLeft] <= helper[helperRight]) {
					array[current++] = helper[helperLeft++];
				} else {
					array[current++] = helper[helperRight++];
				}
			}

			int remaining = mid - helperLeft;

			for (int i = 0; i <= remaining; i++) {
				array[current + i] = helper[helperLeft + i];
			}
		}
	}

	public static class QuickSort extends Sorter {
		public void sort(int[] array) {
			this.inputArray = array;
			quickSort(array, 0, array.length-1);
		}

//		public static void quickSort(int[] array, int left, int right) {
//			int index = partition(array, left, right);
//			if (left < index - 1) {
//				quickSort(array, left, index - 1);
//			}
//			if (index < right) {
//				quickSort(array, index, right);
//			}
//		}
//
//		public static int partition(int[] array, int left, int right) {
//			int pivot = array[(left + right)/2];
//			while (left <= right) {
//				while (array[left] < pivot) {
//					left++;
//				}
//				while (array[right] > pivot) {
//					right--;
//				}
//				if (left <= right) {
//					int temp = array[left];
//					array[left] = array[right];
//					array[right] = temp;
//					left++;
//					right--;
//				}
//			}
//
//			return left;
//		}

		public static void quickSort(int[] array, int left, int right) {
			int index = partition(array, left, right);
			if (left < index - 1) {
				quickSort(array, left, index - 1);
			}
			if (index < right) {
				quickSort(array, index, right);
			}
		}

		public static int partition(int[] array, int left, int right) {
			int pivot = array[(left + right) / 2];

			while (left <= right) {
				while (array[left] < pivot) {
					left++;
				}
				while (array[right] > pivot) {
					right--;
				}
				if (left <= right) {
					int temp = array[left];
					array[left++] = array[right];
					array[right--] = temp;
				}
			}

			return left;
		}
	}

	public static class BucketSort extends Sorter {
		/**
		 * Sorts in O(n + N) time! n = number of entries, N = max value in list
		 */
		public void sort(int[] array) {
			this.inputArray = array;
			int max = Integer.MIN_VALUE;
			int min = Integer.MAX_VALUE;
			for (int i = 0; i < array.length; i++) {
				min = Math.min(min, array[i]);
				max = Math.max(max, array[i]);
			}
			@SuppressWarnings("unchecked")
			ArrayList<Integer>[] buckets = new ArrayList[max - min+1];
			for (int i = 0; i < array.length; i++) {
				if (buckets[array[i] - min] == null) {
					buckets[array[i] - min] = new ArrayList<Integer>();
				}
				buckets[array[i] - min].add(array[i] - min);
			}
			int index = 0;
			for (int i = 0; i < buckets.length; i++) {
				if (buckets[i] != null) {
					for (int j = 0; j < buckets[i].size(); j++) {
						array[index++] = buckets[i].get(j) + min;
					}
				}
			}
		}
	}

	public static class RadixSort extends Sorter {
		public void sort(int[] array) {
			this.inputArray = array;
			int max = Integer.MIN_VALUE;
			for (int j = 0; j < array.length; j++) {
				max = Math.max(max, array[j]);
			}
			for (int i = 0; i < 32; i++) {
				int mask = 1 << i;
				if (mask > max) {
					i = 30;
					continue;
				}

				int[] helper = array.clone();
				int current = 0;
				if (mask >= 0) {
					for (int j = 0; j < helper.length; j++) {
						if ((mask & helper[j]) == 0) {
							array[current++] = helper[j]; 
						}
					}
					for (int j = 0; j < helper.length; j++) {
						if ((mask & helper[j]) != 0) {
							array[current++] = helper[j]; 
						}
					}
				} else {
					for (int j = 0; j < helper.length; j++) {
						if ((mask & helper[j]) != 0) {
							array[current++] = helper[j]; 
						}
					}
					for (int j = 0; j < helper.length; j++) {
						if ((mask & helper[j]) == 0) {
							array[current++] = helper[j]; 
						}
					}
				}
			}
		}
	}
}