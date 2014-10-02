package dataStructures;
import java.util.ArrayList;

public final class HashMapImpl<K, V> implements HashMapImplFrame<K, V> {
	private int size = 0;
	private float resizeThreshold = 0.75f;
	@SuppressWarnings("rawtypes")
	private ArrayList[] back; // array of Entry<K, V>

	private static class Entry<K, V> {
		private K key;
		private V value;
		Entry(K key, V value) {
			this.key = key;
			this.value = value;
		}
	}

	public HashMapImpl() {
		this.back = new ArrayList[16];
	}

	public HashMapImpl(int initialCapacity) {
		this.back = new ArrayList[initialCapacity];
	}

	public int size() {
		return this.size;
	}

	/* (non-Javadoc)
	 * @see HashMapImplFrame#containsKey(K)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public boolean containsKey(K key) {
		int hash = getIndex(key);
		if (back[hash] != null) {
			if (back[hash].size() == 0) {
				return false;
			} else if (back[hash].size() == 1) {
				return true;
			} else {
				for (int i = 0; i < back[hash].size(); i++) {
					if (((Entry<K, V>)back[hash].get(i)).key.equals(key)) {
						return true;
					}
				}
				return false;
			}			
		} else {
			return false;
		}
	}
	
	/* (non-Javadoc)
	 * @see HashMapImplFrame#get(K)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public V get(K key) {
		int hash = getIndex(key);
		if (back[hash] != null) {
			if (back[hash].size() == 0) {
				return null;
			} else if (back[hash].size() == 1) {
				return ((Entry<K, V>)back[hash].get(0)).value;
				//return null;
			} else {
				for (int i = 0; i < back[hash].size(); i++) {
					if (((Entry<K, V>)back[hash].get(i)).key.equals(key)) {
						return ((Entry<K, V>)back[hash].get(i)).value;
					}
				}
				return null;
			}			
		} else {
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see HashMapImplFrame#put(K, V)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void put(K key, V value) {
		int hash = getIndex(key);
		if (!containsKey(key)) {
			if (back[hash] == null) {
				back[hash] = new ArrayList<Entry<K, V>>();
			}
			back[hash].add(new Entry<K, V>(key, value));
			this.size++;
		} else {
			for (int i = 0; i < back[hash].size(); i++) {
				if (((Entry<K, V>)back[hash].get(i)).key.equals(key)) {
					back[hash].set(i, new Entry<K, V>(key, value));
					break;
				}
			}
			back[hash].add(new Entry<K, V>(key, value));
		}

		if (size() > back.length * resizeThreshold) {
			rebuild();
		}
	}

	private int getIndex(K key) {
		int hash = key.hashCode() & (back.length-1);
		return hash;
	}

	/* (non-Javadoc)
	 * @see HashMapImplFrame#remove(K)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void remove(K key) {	
		int hash = getIndex(key);
		if (containsKey(key)) {
			for (int i = 0; i < back[hash].size(); i++) {
				if (((Entry<K, V>)back[hash].get(i)).key.equals(key)) {
					back[hash].remove(i--);
					size--;
					break;
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Entry<K, V>>[] getBack() {
		return back;
	}

	private void rebuild() {
		HashMapImpl<K, V> newBack = new HashMapImpl<K, V>(back.length * 2);
		for (int i = 0; i < back.length; i++) {
			if (back[i] != null) {
				for (int j = 0; j < back[i].size(); j++) {
					@SuppressWarnings("unchecked")
					Entry<K, V> e = (Entry<K, V>)back[i].get(j);
					newBack.put(e.key, e.value);
				}
			}
		}
		this.back = newBack.getBack();
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append('{');
		boolean first = true;
		for (int i = 0; i < back.length; i++) {
			if (back[i] != null) {
				for (int j = 0; j < back[i].size(); j++) {
					if (!first) {
						sb.append(", ");
					}
					first = false;
					@SuppressWarnings("unchecked")
					Entry<K, V> e = (Entry<K, V>)back[i].get(j);
					sb.append(e.key);
					sb.append('=');
					sb.append(e.value);
				}
			}
		}
		sb.append('}');
		return sb.toString();
	}
	
}