package dataStructures;

import java.util.Random;

public class BinaryIndexedTreeVerifier {
	private int[] test;

	public BinaryIndexedTreeVerifier(int size) {
		this.test = new int[size];
	}

	/**
	 * Get the sum up to and including the specified index.
	 */
	public int getSum(int index) {
		int sum = 0;
		for (int i = 0; i <= index; i++) {
			sum += test[i];
		}
		return sum;
	}

	/**
	 * Get the value (not the sum) at this index.
	 */
	public int get(int index) {
		return test[index];
	}

	/**
	 * Set the value at a given index.
	 */
	public void set(int index, int val) {
		test[index] = val;
	}

	/**
	 * Increment the value at a given index.
	 */
	public void increment(int index, int val){
		test[index] += val;
	}

	public int length() {
		return test.length;
	}
	
	
	public static void main(String args[]) {
		Random random = new Random();
		
		for (int s = 1; s <= 100; s++) {
			int size = s;
			BinaryIndexedTree bitTree = new BinaryIndexedTree(size);
			BinaryIndexedTreeVerifier bitTreeVerifier = new BinaryIndexedTreeVerifier(size);
			
			for (int i = 0; i < 10000; i++) {
				int rand = random.nextInt(10);
				int randIndex = random.nextInt(size);
				int randValue = random.nextInt();
				if (rand < 5) {
					bitTree.set(randIndex, randValue);
					bitTreeVerifier.set(randIndex, randValue);
				} else {
					bitTree.increment(randIndex, randValue);
					bitTreeVerifier.increment(randIndex, randValue);
				}
				if (!compare(bitTree, bitTreeVerifier)) {
					System.out.println("Test failed");
					compare(bitTree, bitTreeVerifier);
				}
			}
		}
		
		System.out.println("Tests passed!");
	}
	
	public static boolean compare(BinaryIndexedTree bitTree, BinaryIndexedTreeVerifier bitTreeVerifier) {
		if (bitTree.length() != bitTreeVerifier.length()) {
			return false;
		}
		for (int i = 0; i < bitTree.length(); i++) {
			if (bitTree.get(i) != bitTreeVerifier.get(i)) {
				return false;
			}
		}
		for (int i = 0; i < bitTree.length(); i++) {
			if (bitTree.getSum(i) != bitTreeVerifier.getSum(i)) {
				return false;
			}
		}
		return true;
	}

}
