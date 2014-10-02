package dataStructures;

/**
 * It is often used for storing frequencies and manipulating cumulative frequency tables.
 *
 * Real power is: O(1) insert and O(log n) sum over n cells.
 * 
 * The internal array is not zero based but that is hidden by the implementation.
 */

public class BinaryIndexedTree {

	private int[] binaryIndexedTree;
	
	public BinaryIndexedTree(int size) {
		binaryIndexedTree = new int[size+1];
	}

	/**
	 * Get the sum up to and including the specified index. O(log n) time!
	 */
	public int getSum(int index) {
		index++;
		int sum = 0;
		while (index > 0){
			sum += binaryIndexedTree[index];
			index -= (index & -index);
		}
		return sum;
	}

	/**
	 * Get the value (not the sum) at this index.
	 */
	public int get(int index) {
		index++;
		int sum = binaryIndexedTree[index];
		if (index > 0) {
			int z = index - (index & -index);
			index--;
			while (index != z) {
				sum -= binaryIndexedTree[index];
				index -= (index & -index);
			}
		}
		return sum;
	}

	/**
	 * Set the value at a given index.
	 */
	public void set(int index, int val){
		this.increment(index, val - get(index));		
	}

	/**
	 * Increment the value at a given index.
	 */
	public void increment(int index, int val){
		index++;
		if (index == 0) {
			binaryIndexedTree[0] = 0;
		} else {
			while (index < binaryIndexedTree.length){
				binaryIndexedTree[index] += val;
				index += (index & -index);
			}
		}
	}
	
	public int length() {
		return binaryIndexedTree.length-1;
	}

	
	public static void main(String args[]) {
		BinaryIndexedTree b = new BinaryIndexedTree(10);
		for (int i = 0; i < b.length(); i++) {
			b.set(i, i+1);
		}
		for (int i = 0; i < b.length(); i++) {
			System.out.println("Tree[" + i + "]: " + b.get(i) + "\t Sum up to " +  i + " = " + b.getSum(i) + "\t Predicted sum: " + ((i+1)+1)*(i+1)/2);
		}
	}
	

}
