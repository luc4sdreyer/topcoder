package dataStructures;
public interface HashMapImplFrame<K, V> {

	public abstract boolean containsKey(K key);

	public abstract V get(K key);

	public abstract void put(K key, V value);

	public abstract void remove(K key);

}