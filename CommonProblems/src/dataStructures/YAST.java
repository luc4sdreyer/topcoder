package dataStructures;

public class YAST {

	public static interface BinaryOperation<E> {
		public E function(E e1, E e2);
	}
	
	public static class YASTIN<E extends BinaryOperation<E>> {
		
		/**
		 * Yet Another Segment Tree Implementation's Node
		 */
		
		private E value;
				
		public YASTIN() {
			this.value = null;
		}
		
		/**
		 * Assign a leaf node with data of type T  
		 */
		void assignLeaf(E value) {
			this.value = value;
		}
		
		/**
		 * Combine or summarize the data contained in the left and right children 
		 */
		void merge(YASTIN<E> left, YASTIN<E> right) {
			this.value = left.getValue().function(left.getValue(), right.getValue());
		}
		
		/**
		 * @return The aggregate statistic contained in this node
		 */
		E getValue() {
			return value;
		}

		public String toString() {
			return getValue().toString();
		}
	}
	
	public static class YASTI<E extends BinaryOperation<E>> {
		/**
		 * Yet Another Segment Tree Implementation
		 */
		public YASTIN<E>[] tree;
		public final int N;
		
		@SuppressWarnings("unchecked")
		public YASTI(E[] data) {
			int n = (int) (Math.log10(data.length)/Math.log10(2))+2;
			int treeSize = 1 << n;
			this.N = data.length;
			this.tree = new YASTIN[treeSize];
			for (int i = 0; i < tree.length; i++) {
				this.tree[i] = new YASTIN<>();
			}
			buildTree(data, 1, 0, N-1);
		}
	
		private void buildTree(E[] data, int treeIndex, int dataIndexLow, int dataIndexHigh) {
			if (dataIndexLow == dataIndexHigh) {
				// We're at a leaf (data) node
				if (dataIndexLow < data.length) {
					tree[treeIndex].assignLeaf(data[dataIndexLow]);
				}
				return;
			}
			
			int treeIndexLeftChild = treeIndex * 2;
			int treeIndexRightChild = treeIndexLeftChild + 1;
			int dataIndexMid = (dataIndexHigh + dataIndexLow) / 2;
			
			buildTree(data, treeIndexLeftChild, dataIndexLow, dataIndexMid);
			buildTree(data, treeIndexRightChild, dataIndexMid + 1, dataIndexHigh);
			this.tree[treeIndex].merge(this.tree[treeIndexLeftChild], this.tree[treeIndexRightChild]);
		}
		
		/**
		 * Query the range [queryIndexLow, queryIndexHigh]
		 */
		public E getValue(int queryIndexLow, int queryIndexHigh) {
			YASTIN<E> result = getValue(1, 0, N - 1, queryIndexLow, queryIndexHigh);
			return result.getValue();
		}
		
		private YASTIN<E> getValue(int treeIndex, int dataIndexLow, int dataIndexHigh, int queryIndexLow, int queryIndexHigh) {
			if (dataIndexLow == queryIndexLow && dataIndexHigh == queryIndexHigh) {
				return this.tree[treeIndex];
			}
	
			int treeIndexLeftChild = treeIndex * 2;
			int treeIndexRightChild = treeIndex * 2 + 1;
			int dataIndexMid = (dataIndexHigh + dataIndexLow) / 2;
			
			if (queryIndexLow > dataIndexMid) {
				
				// Look at right child only
				return getValue(treeIndexRightChild, dataIndexMid + 1, dataIndexHigh, queryIndexLow, queryIndexHigh);
			} else if (queryIndexHigh <= dataIndexMid) {
				
				// Look at left child only
				return getValue(treeIndexLeftChild, dataIndexLow, dataIndexMid, queryIndexLow, queryIndexHigh);
			}
			
			// Else look at both children
			YASTIN<E> leftResult = getValue(treeIndexLeftChild, dataIndexLow, dataIndexMid, queryIndexLow, dataIndexMid);
			YASTIN<E> rightResult = getValue(treeIndexRightChild, dataIndexMid + 1, dataIndexHigh, dataIndexMid + 1, queryIndexHigh);
			YASTIN<E> result = new YASTIN<E>();
			result.merge(leftResult, rightResult);
	
			return result;
		}
		
		public void update(int index, E value) {
			update(1, 0, N - 1, index, value);
		}
		
		private void update(int treeIndex, int dataIndexLow, int dataIndexHigh, int updateIndex, E value) {
			if (dataIndexLow == dataIndexHigh) {
				// We're at a leaf (data) node
				tree[treeIndex].assignLeaf(value);
				return;
			}
			
			int treeIndexLeftChild = treeIndex * 2;
			int treeIndexRightChild = treeIndexLeftChild + 1;
			int dataIndexMid = (dataIndexHigh + dataIndexLow) / 2;
			
			if (updateIndex <= dataIndexMid) {
				update(treeIndexLeftChild, dataIndexLow, dataIndexMid, updateIndex, value);
			} else {
				update(treeIndexRightChild, dataIndexMid + 1, dataIndexHigh, updateIndex, value);
			}
			
			this.tree[treeIndex].merge(this.tree[treeIndexLeftChild], this.tree[treeIndexRightChild]);
		}
	}
}
